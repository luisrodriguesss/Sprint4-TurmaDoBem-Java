package br.com.fiap.main;

import br.com.fiap.bo.TriagemBO;
import br.com.fiap.entities.Triagem;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TesteTriagem {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("    TESTE DE TRIAGEM - TURMA DO BEM");
        System.out.println("========================================\n");

        try {
            TriagemBO bo = new TriagemBO();

            int idDentista = 6;
            int idBeneficiario = 1;

            System.out.println(">> METODO LOGICO: Classificar risco...");

            Triagem alto = new Triagem(
                    0,
                    LocalDate.now(),
                    "UBS Centro",
                    "dor intensa no molar",
                    idBeneficiario,
                    idDentista
            );

            Triagem medio = new Triagem(
                    0,
                    LocalDate.now(),
                    "UBS Norte",
                    "carie profunda",
                    idBeneficiario,
                    idDentista
            );

            Triagem baixo = new Triagem(
                    0,
                    LocalDate.now(),
                    "UBS Sul",
                    "limpeza preventiva",
                    idBeneficiario,
                    idDentista
            );

            System.out.println("  Risco alto:  " + bo.classificarRisco(alto));
            System.out.println("  Risco medio: " + bo.classificarRisco(medio));
            System.out.println("  Risco baixo: " + bo.classificarRisco(baixo));

            System.out.println("\n>> CRUD: Inserindo triagem...");

            Triagem t = new Triagem(
                    0,
                    LocalDate.now(),
                    "UBS Centro",
                    "paciente com abscesso dentario",
                    idBeneficiario,
                    idDentista
            );

            bo.cadastrarComValidacao(t);

            System.out.println("  Triagem inserida com sucesso!");

            System.out.println("\n>> CRUD: Listando todas as triagens...");

            List<Triagem> lista = bo.listarTodas();

            for (Triagem tr : lista) {
                System.out.println("  " + tr);
            }

            if (!lista.isEmpty()) {

                int idTeste = lista.get(0).getId();

                System.out.println("\n>> CRUD: Buscando por ID = " + idTeste + "...");

                Triagem encontrada = bo.buscarPorId(idTeste);

                System.out.println("  Encontrada: " + encontrada);

                System.out.println("\n>> CRUD: Atualizando criterios...");

                encontrada.setCriterios("sensibilidade ao frio e calor");

                bo.atualizar(encontrada);

                System.out.println("  Atualizada: " + bo.buscarPorId(idTeste));

                System.out.println("\n>> CRUD: Excluindo triagem ID " + idTeste + "...");

                bo.deletar(idTeste);

                System.out.println("  Excluida com sucesso!");
            }

            System.out.println("\n>> TESTE de Excecao: Local vazio...");

            try {

                Triagem invalida = new Triagem(
                        0,
                        LocalDate.now(),
                        "",
                        "criterio qualquer",
                        idBeneficiario,
                        idDentista
                );

                bo.cadastrarComValidacao(invalida);

            } catch (ErroNegocioException e) {

                System.out.println("  Excecao capturada: " + e.getMessage());
            }

        } catch (SQLException | ClassNotFoundException | ErroNegocioException e) {

            System.out.println("Erro: " + e.getMessage());
        }

        System.out.println("\n========================================");
        System.out.println("   TESTE CONCLUIDO");
        System.out.println("========================================");
    }
}