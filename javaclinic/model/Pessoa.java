package br.faesa.javaclinic.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.Objects;

public class Pessoa {
    @NotBlank(message = "Nome é obrigatório") // Garante que o nome não seja vazio
    private String nome;

    @NotBlank(message = "Email é obrigatório") // Garante que o email não seja vazio
    @Email(message = "Formato do email é inválido") // Valida o formato do email
    private String email;

    @NotBlank(message = "Dados do endereço são obrigatórios") // Garante que o endereço não seja vazio
    @Pattern(regexp = "^[A-Za-z\\s]+-\\d+[A-Za-z]?-[A-Za-z\\s]+-[A-Za-z\\s]+-[A-Z]{2}$", message = "Formato do endereço é inválido") // Valida o formato do endereço
    private String endereco;

    @NotBlank(message = "Telefone é obrigatório") // Garante que o telefone não seja vazio
    @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$", message = "Formato do telefone é inválido") // Valida o formato do telefone
    private String telefone;

    // Construtor com todos os parâmetros
    public Pessoa(String nome, String email, String endereco, String telefone) {
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    // Construtor sem argumentos, útil para a criação de instâncias sem dados iniciais
    public Pessoa() {
        this.nome = "";
        this.email = "";
        this.endereco = "";
        this.telefone = "";
    }

    // Métodos getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    // Método que atualiza os dados da pessoa (nome, telefone e endereço)
    public void atualizar(String nome, String telefone, String endereco) {
        if (!nome.isEmpty()) {
            this.setNome(nome);
        }
        if (!telefone.isEmpty()) {
            this.setTelefone(telefone);
        }
        if (!endereco.isEmpty()) {
            this.setEndereco(endereco);
        }
    }

    // Método equals que compara duas pessoas, considerando o email como critério único de comparação
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(email, pessoa.email);
    }

    // Método hashCode que gera o código hash da pessoa com base no email
    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    // Método toString que gera uma representação textual da pessoa
    @Override
    public String toString() {
        return "Nome: " + nome + "\n" +
                "Email: " + email + "\n" +
                "Endereço: " + endereco + "\n" +
                "Telefone: " + telefone + "\n";
    }
}