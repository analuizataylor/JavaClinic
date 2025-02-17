package br.faesa.javaclinic.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public class Paciente extends Pessoa{
    @NotBlank(message = "CPF é obrigatório") // Garante que o CPF não seja vazio
    @Pattern(regexp = "^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$", message = "Formato do CPF é inválido") // Valida o formato do CPF
    private String cpf;

    // Construtor com todos os parâmetros
    public Paciente(String nome, String email, String endereco, String telefone, String cpf) {
        super(nome, email, endereco, telefone);
        this.cpf = cpf;
    }

    // Construtor sem argumentos, útil para a criação de instâncias sem dados iniciais
    public Paciente() {
        super("", "", "", "");
        this.cpf = "";
    }

    // Métodos getters e setters
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    // Método equals para comparar dois pacientes com base no CPF e o atributo email da classe pai
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Paciente paciente = (Paciente) o;
        return Objects.equals(cpf, paciente.cpf);
    }

    // Método hashCode para gerar o código hash baseado no CPF e o atributo email da classe pai
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cpf);
    }

    // Método toString para representar o paciente como uma string
    @Override
    public String toString() {
        return "---------------------" + "\n"
                + super.toString() +
                "CPF: " + cpf;
    }
}
