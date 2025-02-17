package br.faesa.javaclinic.model;

public enum Especialidade {
    ORTOPEDIA,   // Especialidade de ortopedia
    PEDIATRIA,   // Especialidade de pediatria
    CARDIOLOGIA, // Especialidade de cardiologia
    GINECOLOGIA, // Especialidade de ginecologia
    DERMATOLOGIA, // Especialidade de dermatologia
    PSIQUIATRIA, // Especialidade de psiquiatria
    OFTALMOLOGIA; // Especialidade de oftalmologia

    // Método estático para converter uma String para o valor correspondente do enum
    public static Especialidade fromString(String value) {
        try {
            // Converte o valor para maiúsculas e tenta obter o valor correspondente do enum
            return Especialidade.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Lança uma exceção caso o valor fornecido não seja válido
            throw new IllegalArgumentException("Especialidade inválida.");
        }
    }
}
