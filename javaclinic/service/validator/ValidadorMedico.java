package br.faesa.javaclinic.service.validator;

import br.faesa.javaclinic.repository.ConsultaRepository;

import java.time.LocalDateTime;

public class ValidadorMedico {
    private ConsultaRepository consultaRepository; // Dependência para acesso aos dados de consultas

    public ValidadorMedico(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository; // Injeta o repositório no validador
    }

    public void validarAgendamento(String nomeMedico, LocalDateTime data) {
        // Verifica se o médico já tem uma consulta no horário informado
        if (consultaRepository.medicoPossuiOutraConsulta(nomeMedico, data)) {
            throw new ValidacaoException("Médico já possui outra consulta nesse horário!"); // Lança exceção caso a validação falhe
        }
    }
}
