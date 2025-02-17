package br.faesa.javaclinic.service;

import br.faesa.javaclinic.repository.UsuarioRepository;

import java.util.List;
import java.util.Scanner;

public class LoginService {
    private static Scanner scanner = new Scanner(System.in);

    public static Usuario fazerLogin() {
        // Carrega a lista de usuários do repositório
        List<Usuario> usuariosList = UsuarioRepository.carregarUsuarios();
        System.out.print("Digite seu nome de usuário: ");
        String usuario = scanner.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();

        // Itera pelos usuários e autentica com base no nome de usuário e senha
        for (Usuario u : usuariosList) {
            if (u.autenticar(usuario, senha)) {
                System.out.println("Login bem-sucedido!"); // Mensagem de sucesso no login
                return u; // Retorna o usuário autenticado
            }
        }
        System.out.println("Usuário ou senha inválidos!"); // Mensagem de erro ao falhar na autenticação
        return null; // Retorna null caso a autenticação falhe
    }
}
