package br.faesa.javaclinic.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public class Medico extends Pessoa{
    @NotBlank(message = "CRM é obrigatório") // Garante que o CRM não seja vazio
    @Pattern(regexp = "^\\d{4,6}$", message = "Formato do CRM é inválido") //a expressão regular \d{4,6} corresponde a sequências de 4 a 6 dígitos consecutivos
    private String crm;

    @NotNull(message = "Especialidade é obrigatória") // Garante que a especialidade não seja nula
    private Especialidade especialidade;

    // Construtor com todos os parâmetros
    public Medico(String nome, String email, String endereco, String telefone, String crm, Especialidade especialidade) {
        super(nome, email, endereco, telefone);
        this.crm = crm;
        this.especialidade = especialidade;
    }

    // Construtor sem argumentos, útil para a criação de instâncias sem dados iniciais
    public Medico() {
        super("", "", "", "");
        this.crm = "";
        this.especialidade = null;
    }

    // Métodos getters e setters
    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    // Método equals para comparar dois médicos com base no CRM e o atributo email da classe pai
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Medico medico = (Medico) o;
        return Objects.equals(crm, medico.crm);
    }

    // Método hashCode para gerar o código hash baseado no CRM e o atributo email da classe pai
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), crm);
    }

    // Método toString para representar o médico como uma string
    @Override
    public String toString() {
        return "---------------------" + "\n"
                + super.toString() +
                "CRM: " + crm + "\n" +
                "Especialidade: " + especialidade;
    }
}
