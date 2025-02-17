package br.faesa.javaclinic.repository;

import br.faesa.javaclinic.model.Especialidade;
import br.faesa.javaclinic.model.Medico;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MedicoRepository {
    private static final String PATH_MEDICOS = "C:" + File.separator + "Users" + File.separator + "luana" + File.separator + "Persistencia" + File.separator + "medicos.txt";

    public static Medico buscarMedicoPorCrm(String crm) {
        List<Medico> medicos = carregarMedicos();
        for (Medico medico : medicos) {
            if (medico.getCrm().equals(crm)) { // Verifica se o CRM do médico é o informado
                return medico; // Retorna o médico encontrado
            }
        }
        return null; // Retorna null caso não encontre
    }

    public static Medico buscarMedicoPorEmail(String email) {
        List<Medico> medicos = carregarMedicos();
        for (Medico medico : medicos) {
            if (medico.getEmail().equals(email)) { // Verifica se o email do médico é o informado
                return medico; // Retorna o médico encontrado
            }
        }
        return null; // Retorna null caso não encontre
    }

    public static Especialidade getEspecialidadeDoMedico(String nomeMedico) {
        List<Medico> medicos = carregarMedicos();
        for (Medico medico : medicos) {
            if (medico.getNome().equalsIgnoreCase(nomeMedico)) { // Verifica o nome do médico
                return medico.getEspecialidade(); // Retorna a especialidade do médico encontrado
            }
        }
        return null; // Retorna null se o médico não for encontrado
    }

    public static void salvarMedicos(List<Medico> medicos) {
        // Remove médicos duplicados a partir do método equals e hashCode, mantendo apenas instâncias únicas na lista
        List<Medico> medicosUnicos = medicos.stream()
                .distinct()
                .collect(Collectors.toList());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_MEDICOS))) {
            for (Medico m : medicosUnicos) {
                // Escreve as informações do médico no arquivo, separadas por ponto e vírgula
                writer.write(m.getNome() + ";" +
                        m.getEmail() + ";" +
                        m.getEndereco() + ";" +
                        m.getTelefone() + ";" +
                        m.getCrm() + ";" +
                        m.getEspecialidade().name());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar médicos: " + e.getMessage());
        }
    }

    public static List<Medico> carregarMedicos() {
        // Lista para armazenar os médicos carregados do arquivo
        List<Medico> medicos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_MEDICOS))) {
            String linha;

            // Lê cada linha do arquivo enquanto houver conteúdo
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";\\s*"); // Expressão regular para encontrar ';' seguidos de possíveis espaços em branco
                if (dados.length == 6) { // Verifica se há exatamente 6 partes
                    String nome = dados[0].trim();
                    String email = dados[1].trim();
                    String endereco = dados[2].trim();
                    String telefone = dados[3].trim();
                    String crm = dados[4].trim();
                    Especialidade especialidade = Especialidade.valueOf(dados[5].trim().toUpperCase()); // Converte dados[5] para o tipo enum Especialidade

                    // Adiciona um novo objeto Medico com os valores convertidos
                    medicos.add(new Medico(nome, email, endereco, telefone, crm, especialidade));
                } else {
                    System.out.println("Linha inválida ignorada: " + linha); // Ignora linhas com formato inválido
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Erro ao carregar médicos: " + e.getMessage());
        }
        return medicos; // Retorna a lista de médicos carregados
    }

    public static void atualizarMedico(String crm, String nome, String telefone, String endereco) {
        List<Medico> medicos = carregarMedicos();
        Medico medico = null;

        // Procura o médico na lista pelo CRM
        for (Medico m : medicos) {
            if (m.getCrm().equals(crm)) {
                medico = m;  // Atualiza a referência do médico encontrado
                break;  // Encontra o médico e sai do loop
            }
        }

        if (medico != null) {
            System.out.println("Antes da atualização: " + medico);
            medico.atualizar(nome, telefone, endereco); // Atualiza os dados do médico
            System.out.println("Depois da atualização: " + medico);
            salvarMedicos(medicos); // Salva os médicos restantes no arquivo
        }
    }

    // Método para excluir um médico
    public static void excluirMedico(String crm) {
        List<Medico> medicos = carregarMedicos();
        Medico medico = buscarMedicoPorCrm(crm); // Busca o médico pelo CRM

        if (medico != null) {
            medicos.remove(medico); // Remove o médico da lista
            salvarMedicos(medicos); // Salva os médicos restantes no arquivo
        } else {
            System.out.println("Médico com CRM " + crm + " não encontrado."); // Mensagem de erro caso o médico não seja encontrado
        }
    }
}