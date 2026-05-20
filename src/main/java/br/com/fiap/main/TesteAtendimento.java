package br.com.fiap.main;

import br.com.fiap.bo.AtendimentoBO;
import br.com.fiap.entities.Atendimento;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TesteAtendimento {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  TESTE DE ATENDIMENTO - TURMA DO BEM");
        System.out.println("========================================\n");

        try {
            AtendimentoBO bo = new AtendimentoBO();

            System.out.println(">> METODO LOGICO: Verificar se possui receita...");
            Atendimento comReceita = new Atendimento(0, LocalDate.now(), "Remdio", "Extracao", 1, 1);
            Atendimento semReceita = new Atendimento(0, LocalDate.now(), "", "Limpeza", 1, 1);
            System.out.println("  Com receita: " + bo.possuiReceita(comReceita));
            System.out.println("  Sem receita: " + bo.possuiReceita(semReceita));


            System.out.println("\n>> CRUD: Inserindo atendimento...");
            Atendimento a = new Atendimento(0, LocalDate.now(), "Remedio",
                    "Extracao do dente de trás", 1, 1);
            bo.cadastrarComValidacao(a);
            System.out.println("  Atendimento inserido com sucesso!");

            System.out.println("\n>> CRUD: Listando todos os atendimentos...");
            List<Atendimento> lista = bo.listarTodos();
            for (Atendimento at : lista) {
                System.out.println("  " + at);
            }

            if (!lista.isEmpty()) {
                int idTeste = lista.get(0).getId();

                System.out.println("\n>> CRUD: Buscando por ID = " + idTeste + "...");
                Atendimento encontrado = bo.buscarPorId(idTeste);
                System.out.println("  Encontrado: " + encontrado);

                System.out.println("\n>> CRUD: Atualizando descricao...");
                encontrado.setDescricao("Tratamento de canal realizado");
                bo.atualizar(encontrado);
                System.out.println("  Atualizado: " + bo.buscarPorId(idTeste));

                System.out.println("\n>> CRUD: Excluindo atendimento ID " + idTeste + "...");
                bo.deletar(idTeste);
                System.out.println("  Excluido com sucesso!");
            }


            System.out.println("\n>> TESTE de Excecao: Descricao vazia...");
            try {
                Atendimento invalido = new Atendimento(0, LocalDate.now(), "", "", 1, 1);
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