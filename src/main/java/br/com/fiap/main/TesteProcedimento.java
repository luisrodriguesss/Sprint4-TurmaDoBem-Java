package br.com.fiap.main;

import br.com.fiap.bo.ProcedimentoBO;
import br.com.fiap.entities.Procedimento;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TesteProcedimento {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println(" TESTE DE PROCEDIMENTO - TURMA DO BEM");
        System.out.println("========================================\n");

        try {
            ProcedimentoBO bo = new ProcedimentoBO();

            int idAtendimento = 1;

            System.out.println(">> METODO LOGICO: Verificar se possui relatorio...");
            Procedimento comRelatorio = new Procedimento(0, "Extracao", LocalDate.now(), "Extracao realizada sem complicacoes", idAtendimento);
            Procedimento semRelatorio = new Procedimento(0, "Limpeza", LocalDate.now(), "", idAtendimento);
            System.out.println("  Com relatorio: " + bo.possuiRelatorio(comRelatorio));
            System.out.println("  Sem relatorio: " + bo.possuiRelatorio(semRelatorio));

            System.out.println("\n>> CRUD: Inserindo procedimento...");
            Procedimento p = new Procedimento(0, "Tratamento de Canal", LocalDate.now(), "Canal do dente tratado com sucesso", idAtendimento);
            bo.cadastrarComValidacao(p);
            System.out.println("  Procedimento inserido com sucesso!");

            System.out.println("\n>> CRUD: Listando todos os procedimentos...");
            List<Procedimento> lista = bo.listarTodos();
            for (Procedimento pr : lista) {
                System.out.println("  " + pr);
            }

            if (!lista.isEmpty()) {
                int idTeste = lista.get(0).getId();

                System.out.println("\n>> CRUD: Buscando por ID = " + idTeste + "...");
                Procedimento encontrado = bo.buscarPorId(idTeste);
                System.out.println("  Encontrado: " + encontrado);

                System.out.println("\n>> CRUD: Atualizando relatorio...");
                encontrado.setRelatorio("Procedimento revisado e concluido");
                bo.atualizar(encontrado);
                System.out.println("  Atualizado: " + bo.buscarPorId(idTeste));

                System.out.println("\n>> CRUD: Excluindo procedimento ID " + idTeste + "...");
                bo.deletar(idTeste);
                System.out.println("  Excluido com sucesso!");
            }

            System.out.println("\n>> TESTE de Excecao: Relatorio vazio...");
            try {
                Procedimento invalido = new Procedimento(0, "Extracao", LocalDate.now(), "", idAtendimento);
                bo.cadastrarComValidacao(invalido);
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