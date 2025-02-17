package br.faesa.javaclinic.repository;

import br.faesa.javaclinic.model.Consulta;
import br.faesa.javaclinic.model.Especialidade;
import br.faesa.javaclinic.model.Medico;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConsultaRepository {
    private static final String PATH_CONSULTAS = "C:" + File.separator + "Users" + File.separator + "luana" + File.separator + "Persistencia" + File.separator + "consultas.txt";
    private static long ultimoId = 0; // Controla o maior ID atribuído às consultas

    public List<Consulta> listarConsultasDoPaciente(String nomePaciente) {
        List<Consulta> consultas = carregarConsultas();

        // Lista para armazenar as consultas do paciente específico
        List<Consulta> consultasDoPaciente = new ArrayList<>();

        // Itera sobre todas as consultas para filtrar as que pertencem ao paciente
        for (Consulta consulta : consultas) {
            if (consulta.getNomePaciente().trim().equalsIgnoreCase(nomePaciente.trim())) {
                consultasDoPaciente.add(consulta);
            }
        }
        // Retorna a lista de consultas do paciente
        return consultasDoPaciente;
    }

    public boolean medicoPossuiOutraConsulta(String nomeMedico, LocalDateTime data) {
        List<Consulta> consultas = carregarConsultas();

        // Verifica se algum médico já tem consulta marcada para a mesma data
        return consultas.stream()
                .anyMatch(consulta -> consulta.getNomeMedico().equalsIgnoreCase(nomeMedico) && consulta.getData().equals(data));
    }

    public Consulta buscarConsultaPorId(Long id) {
        List<Consulta> consultas = carregarConsultas();

        // Itera sobre as consultas e busca a consulta com o ID fornecido
        for (Consulta consulta : consultas) {
            if (consulta.getId() == id) {
                // Retorna a consulta se o ID for encontrado
                return consulta;
            }
        }
        return null; // Retorna null caso não encontre
    }

    public static List<String> buscarMedicosPorEspecialidade(Especialidade especialidade, LocalDateTime data) {
        List<Medico> medicos = MedicoRepository.carregarMedicos();
        List<Consulta> consultas = carregarConsultas();

        // Lista para armazenar médicos disponíveis
        List<String> medicosDisponiveis = new ArrayList<>();

        // Itera sobre todos os médicos
        for (Medico medico : medicos) {
            if (medico.getEspecialidade() == especialidade) {
                // Verifica se o médico está ocupado na data fornecida
                boolean ocupado = consultas.stream()
                        .anyMatch(consulta -> consulta.getNomeMedico().equalsIgnoreCase(medico.getNome())
                                && consulta.getData().equals(data));
                // Se o médico não estiver ocupado, adiciona à lista de médicos disponíveis
                if (!ocupado) {
                    medicosDisponiveis.add(medico.getNome());
                }
            }
        }

        // Retorna a lista de médicos disponíveis para a especialidade e data fornecidas
        return medicosDisponiveis;
    }

    public static void agendarConsultas(List<Consulta> consultas) {
        // Remove consultas duplicadas a partir do método equals e hashCode, mantendo apenas instâncias únicas na lista
        List<Consulta> consultasUnicas = consultas.stream()
                .distinct()
                .collect(Collectors.toList());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_CONSULTAS))) {
            for (Consulta c : consultasUnicas) {
                // Verifica se a consulta ainda não tem um ID
                if (c.getId() == 0) {
                    ultimoId++; // Incrementa o ultimoId para gerar um novo ID único
                    c.setId(ultimoId); // Atribui o novo ID à consulta
                }
                writer.write(
                        c.getId() + ";" +
                                c.getNomeMedico() + ";" +
                                c.getNomePaciente() + ";" +
                                c.getEspecialidade().name() + ";" +
                                c.getData());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar consultas: " + e.getMessage());
        }
    }

    public static List<Consulta> carregarConsultas() {
        // Lista para armazenar as consultas carregadas do arquivo
        List<Consulta> consultas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_CONSULTAS))) {
            String linha;

            // Lê cada linha do arquivo enquanto houver conteúdo
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";\\s*"); // Expressão regular para encontrar ';' seguidos de possíveis espaços em branco
                if (dados.length == 5) { // Verifica se há exatamente 5 partes
                    long id = Long.parseLong(dados[0].trim());
                    String nomeMedico = dados[1].trim();
                    String nomePaciente = dados[2].trim();
                    Especialidade especialidade = Especialidade.valueOf(dados[3].trim().toUpperCase());
                    LocalDateTime data = LocalDateTime.parse(dados[4].trim());

                    // Cria uma nova consulta a partir dos dados e adiciona à lista
                    consultas.add(new Consulta(id, nomeMedico, nomePaciente, especialidade, data));

                    // Verifica se o ID da consulta lida do arquivo é maior que o ultimoId atual
                    if (id > ultimoId) {
                        ultimoId = id; // Atualiza o ultimoId para ser igual ao maior ID encontrado no arquivo
                    }
                } else {
                    System.out.println("Linha inválida ignorada: " + linha); // Ignora linhas com formato inválido
                }
            }
        } catch (IOException | IllegalArgumentException | DateTimeParseException e) {
            System.out.println("Erro ao carregar consultas: " + e.getMessage());
        }
        return consultas; // Retorna a lista de consultas carregadas
    }

    public static void cancelarConsulta(Long id) {
        // Carrega as consultas existentes
        List<Consulta> consultas = carregarConsultas();

        // Verifica e remove a consulta
        boolean consultaRemovida = consultas.removeIf(consulta -> consulta.getId() == id);

        if (!consultaRemovida) {
            System.out.println("Consulta com ID " + id + " não encontrada.");
            return;
        }
        // Regrava as consultas no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_CONSULTAS))) {
            for (Consulta consulta : consultas) {
                writer.write(
                        consulta.getId() + ";" +
                                consulta.getNomeMedico() + ";" +
                                consulta.getNomePaciente() + ";" +
                                consulta.getEspecialidade().name() + ";" +
                                consulta.getData() + "\n"
                );
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar consultas: " + e.getMessage());
        }
    }
}
