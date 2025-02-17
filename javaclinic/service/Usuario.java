package br.faesa.javaclinic.service;

import java.util.Objects;

public class Usuario {
    private String usuario; // Nome de usuário para login
    private String senha; // Senha para autenticação
    private char tipo; // Tipo do usuário: 'F' (Funcionário) ou 'P' (Paciente)

    // Construtor com todos os parâmetros
    public Usuario(String usuario, String senha, char tipo) {
        this.usuario = usuario;
        this.senha = senha;
        this.tipo = tipo;
    }

    // Construtor sem argumentos, útil para a criação de instâncias sem dados iniciais
    public Usuario(){
        this.usuario = "";
        this.senha = "";
        this.tipo = ' ';
    }

    // Getters e Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    // Valida credenciais de login
    public boolean autenticar(String usuario, String senha) {
        return this.usuario.equals(usuario) && this.senha.equals(senha);
    }

    // Método equals para comparar dois usuários com base no nome de usuário
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario1 = (Usuario) o;
        return Objects.equals(usuario, usuario1.usuario);
    }

    // Gera hash baseado no nome de usuário
    @Override
    public int hashCode() {
        return Objects.hashCode(usuario);
    }

    // Método toString para representar o usuário como uma string
    @Override
    public String toString() {
        /*Operador ternário:
        condição ? valor_se_verdadeiro : valor_se_falso;*/
        return "Usuario: " + usuario + " | Tipo: " + (tipo == 'F' ? "Funcionário" : "Paciente");
    }
}
