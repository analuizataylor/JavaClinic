package br.faesa.javaclinic.repository;

import br.faesa.javaclinic.model.Medico;
import br.faesa.javaclinic.service.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioRepository {
    private static final String PATH_USUARIOS = "C:" + File.separator + "Users" + File.separator + "luana" + File.separator + "Persistencia" + File.separator + "usuarios.txt";

    public static Usuario buscarUsuarioPorNomeDeUsuario(String nomeUsuario) {
        List<Usuario> usuarios = carregarUsuarios();
        for (Usuario usuario : usuarios) {
            if (usuario.getUsuario().equals(nomeUsuario)) { // Verifica se o nome de usuário é o informado
                return usuario; // Retorna o usuário encontrado
            }
        }
        return null; // Retorna null caso não encontre
    }

    public static void salvarUsuarios(List<Usuario> usuarios) {
        // Remove usuários duplicados a partir do método equals e hashCode, mantendo apenas instâncias únicas na lista
        List<Usuario> usuariosUnicos = usuarios.stream()
                .distinct()
                .collect(Collectors.toList());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_USUARIOS))) {
            for (Usuario u : usuariosUnicos) {
                // Escreve as informações do usuário no arquivo, separadas por ponto e vírgula
                writer.write(u.getUsuario() + ";"
                        + u.getSenha() + ";"
                        + u.getTipo());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    public static List<Usuario> carregarUsuarios() {
        // Lista para armazenar os usuários carregados do arquivo
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_USUARIOS))) {
            String linha;

            // Lê cada linha do arquivo enquanto houver conteúdo
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";\\s*"); // Expressão regular para encontrar ';' seguidos de possíveis espaços em branco
                if (dados.length == 3) { // Verifica se há exatamente 3 partes
                    String usuario = dados[0].trim();
                    String senha = dados[1].trim();
                    char tipo = dados[2].charAt(0);

                    // Cria um novo usuário a partir dos dados e adiciona à lista
                    usuarios.add(new Usuario(usuario, senha, tipo));
                } else {
                    System.out.println("Linha inválida ignorada: " + linha); // Ignora linhas com formato inválido
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }
        return usuarios; // Retorna a lista de usuários carregados
    }
}
