package br.faesa.javaclinic.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

public class Consulta {
    private long id; // ID único da consulta

    @NotBlank(message = "O nome do médico é obrigatório.") // Garante que o nome do médico não seja vazio
    private String nomeMedico;

    @NotBlank(message = "O nome do paciente é obrigatório.") // Garante que o nome do paciente não seja vazio
    private String nomePaciente;

    @NotNull(message = "Especialidade é obrigatória") // Garante que a especialidade não seja nula
    private Especialidade especialidade;

    @Future(message = "A data da consulta deve ser no futuro.") // Garante que a data e hora da consulta seja no futuro
    private LocalDateTime data;

    // Construtor com todos os parâmetros
    public Consulta(long id, String nomeMedico, String nomePaciente, Especialidade especialidade, LocalDateTime data) {
        this.id = id;
        this.nomeMedico = nomeMedico;
        this.nomePaciente = nomePaciente;
        this.especialidade = especialidade;
        this.data = data;
    }

    // Construtor sem argumentos, útil para a criação de instâncias sem dados iniciais
    public Consulta() {
        this.id = 0;
        this.nomeMedico = "";
        this.nomePaciente = "";
        this.especialidade = null;
        this.data = null;
    }

    // Métodos getters e setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    // Método equals para comparar duas consultas com base no ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consulta consulta = (Consulta) o;
        return Objects.equals(id, consulta.id);
    }

    // Método hashCode para garantir que o hash seja consistente com o método equals
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    // Método toString para exibir informações sobre a consulta de forma legível
    @Override
    public String toString() {
        return "---------------------" +
                "\n ID: " + id +
                "\n Nome do médico: " + nomeMedico +
                "\n Nome do paciente: " + nomePaciente  +
                "\n Especialidade: " + especialidade.name() +
                "\n Data da consulta: " + data;
    }
}
