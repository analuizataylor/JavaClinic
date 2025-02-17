package br.faesa.javaclinic.repository;

import br.faesa.javaclinic.model.Paciente;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PacienteRepository {
    private static final String PATH_PACIENTES = "C:" + File.separator + "Users" + File.separator + "luana" + File.separator + "Persistencia" + File.separator + "pacientes.txt";

    public static Paciente buscarPacientePorNome(String nomePaciente){
        List<Paciente> pacientes = carregarPacientes();
        for (Paciente paciente : pacientes) {
            if (paciente.getNome().equalsIgnoreCase(nomePaciente)) { // Verifica se o nome do paciente bate com o informado
                return paciente; // Retorna o paciente encontrado
            }
        }
        return null; // Retorna null caso não encontre
    }

    public static Paciente buscarPacientePorEmail(String email) {
        List<Paciente> pacientes = carregarPacientes();
        for (Paciente paciente : pacientes) {
            if (paciente.getEmail().equals(email)) { // Verifica se o email do paciente bate com o informado
                return paciente; // Retorna o paciente encontrado
            }
        }
        return null; // Retorna null caso não encontre
    }

    public static Paciente buscarPacientePorCpf(String cpf) {
        List<Paciente> pacientes = carregarPacientes();
        for (Paciente paciente : pacientes) {
            if (paciente.getCpf().equals(cpf)) { // Verifica se o CPF do paciente bate com o informado
                return paciente; // Retorna o paciente encontrado
            }
        }
        return null; // Retorna null caso não encontre
    }

    public static void salvarPacientes(List<Paciente> pacientes) {
        // Remove pacientes duplicados a partir do método equals e hashCode, mantendo apenas instâncias únicas na lista
        List<Paciente> pacientesUnicos = pacientes.stream()
                .distinct()
                .collect(Collectors.toList());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_PACIENTES))) {
            for (Paciente p : pacientesUnicos) {
                // Escreve as informações do paciente no arquivo, separadas por ponto e vírgula
                writer.write(p.getNome() + ";" +
                        p.getEmail() + ";" +
                        p.getEndereco() + ";" +
                        p.getTelefone() + ";" +
                        p.getCpf());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar pacientes: " + e.getMessage());
        }
    }

    public static List<Paciente> carregarPacientes() {
        // Lista para armazenar os pacientes carregados do arquivo
        List<Paciente> pacientes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_PACIENTES))) {
            String linha;

            // Lê cada linha do arquivo enquanto houver conteúdo
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";\\s*"); // Expressão regular para encontrar ';' seguidos de possíveis espaços em branco
                if (dados.length == 5) { // Verifica se há exatamente 5 partes
                    String nome = dados[0].trim();
                    String email = dados[1].trim();
                    String endereco = dados[2].trim();
                    String telefone = dados[3].trim();
                    String cpf = dados[4].trim();

                    // Adiciona um novo objeto Paciente com os valores convertidos
                    pacientes.add(new Paciente(nome, email, endereco, telefone, cpf));
                } else {
                    System.out.println("Linha inválida ignorada: " + linha); // Ignora linhas com formato inválido
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar pacientes: " + e.getMessage());
        }
        return pacientes; // Retorna a lista de pacientes carregados
    }

    public static void atualizarPaciente(String cpf, String nome, String telefone, String endereco) {
        List<Paciente> pacientes = carregarPacientes();
        Paciente paciente = null;

        // Procura o paciente pelo CPF na lista
        for (Paciente p : pacientes) {
            if (p.getCpf().equals(cpf)) {
                paciente = p;  // Atualiza a referência para o paciente encontrado
                break;  // Sai do loop assim que o paciente é encontrado
            }
        }

        if (paciente != null) {
            System.out.println("Antes da atualização: " + paciente);
            paciente.atualizar(nome, telefone, endereco); // Atualiza os dados do paciente
            System.out.println("Depois da atualização: " + paciente);
            salvarPacientes(pacientes); // Salva a lista atualizada de pacientes
        }
    }

    public static void excluirPaciente(String cpf) {
        List<Paciente> pacientes = carregarPacientes();
        Paciente paciente = buscarPacientePorCpf(cpf); // Encontra o paciente pelo CPF

        if (paciente != null) {
            pacientes.remove(paciente); // Remove o paciente da lista
            salvarPacientes(pacientes); // Salva a lista atualizada
        } else {
            System.out.println("Paciente com CPF " + cpf + " não encontrado."); // Exibe uma mensagem caso o paciente não seja encontrado
        }
    }
}
