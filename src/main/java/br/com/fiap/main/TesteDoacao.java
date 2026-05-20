package br.com.fiap.main;

import br.com.fiap.bo.DoacaoBO;
import br.com.fiap.entities.Doacao;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TesteDoacao {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("     TESTE DE DOACAO - TURMA DO BEM");
        System.out.println("========================================\n");

        try {
            DoacaoBO bo = new DoacaoBO();


            System.out.println(">> CRUD: Inserindo doacoes...");
            Doacao d1 = new Doacao(0, "Murilo", "Dinheiro", 250.00, LocalDate.now(), 1);
            Doacao d2 = new Doacao(0, "Matheus", "Material", 80.00, LocalDate.now(), 2);
            bo.registrarComValidacao(d1);
            bo.registrarComValidacao(d2);
            System.out.println("  2 doacoes inseridas com sucesso!");


            System.out.println("\n>> CRUD: Listando todas as doacoes...");
            List<Doacao> lista = bo.listarTodas();
            for (Doacao d : lista) {
                System.out.println("  " + d);
            }


            System.out.println("\n>> METODO LOGICO: Total por tipo 'Dinheiro'...");
            double totalDinheiro = bo.calcularTotalPorTipo("Dinheiro");
            System.out.printf("  Total em Dinheiro: R$ %.2f%n", totalDinheiro);

            System.out.println("\n>> METODO LOGICO: Total por tipo 'Material'...");
            double totalMaterial = bo.calcularTotalPorTipo("Material");
            System.out.printf("  Total em Material: R$ %.2f%n", totalMaterial);


            if (!lista.isEmpty()) {
                int idTeste = lista.get(0).getId();
                System.out.println("\n>> CRUD: Buscando doacao ID = " + idTeste + "...");
                Doacao encontrada = bo.buscarPorId(idTeste);
                System.out.println("  Encontrada: " + encontrada);

                System.out.println("\n>> CRUD: Atualizando valor...");
                encontrada.setValor(300.00);
                bo.atualizar(encontrada);
                System.out.println("  Valor atualizado: R$ " + bo.buscarPorId(idTeste).getValor());

                System.out.println("\n>> CRUD: Excluindo doacao ID " + idTeste + "...");
                bo.deletar(idTeste);
                System.out.println("  Excluida com sucesso!");
            }


            System.out.println("\n>> TESTE de Excecao: Tipo invalido...");
            try {
                Doacao invalida = new Doacao(0, "X", "Cripto", -10.0, LocalDate.now(), 1);
                bo.registrarComValidacao(invalida);
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