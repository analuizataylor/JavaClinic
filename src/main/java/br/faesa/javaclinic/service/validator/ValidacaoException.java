package br.faesa.javaclinic.service.validator;

public class ValidacaoException extends RuntimeException {
    public ValidacaoException(String mensagem) {
        super(mensagem); // Construtor que passa a mensagem de erro para a classe pai RuntimeException
    }
}
