package br.com.fiap.main;

import br.com.fiap.bo.BeneficiarioBO;
import br.com.fiap.entities.Beneficiario;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TesteBeneficiario {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   TESTE DE BENEFICIARIO - TURMA DO BEM");
        System.out.println("========================================\n");

        try {
            BeneficiarioBO bo = new BeneficiarioBO();

            System.out.println(">> METODO LOGICO: Validacao de CPF");
            System.out.println("CPF 111.111.111-11 (invalido): " + bo.validarCpf("111.111.111-11"));
            System.out.println("CPF 529.982.247-25 (valido):   " + bo.validarCpf("529.982.247-25"));

            System.out.println("\n>> CRUD: Inserindo beneficiario...");
            Beneficiario b = new Beneficiario(
                    0,
                    "Gabriel Rocha",
                    "529.982.247-25",
                    LocalDate.of(1990, 5, 20),
                    "01310-100",
                    "dor intensa nos dentes"
            );
            bo.cadastrarComValidacao(b);
            System.out.println("Beneficiario inserido com sucesso!");

            System.out.println("\n>> CRUD: Listando todos os beneficiarios...");
            List<Beneficiario> lista = bo.listarTodos();
            for (Beneficiario ben : lista) {
                System.out.println("  " + ben);
            }

            System.out.println("\n>> METODO LOGICO: Verificando elegibilidade prioritaria...");
            if (!lista.isEmpty()) {
                String resultado = bo.verificarElegibilidade(lista.get(0));
                System.out.println("  " + lista.get(0).getNome() + " -> " + resultado);
            }


            System.out.println("\n>> METODO LOGICO: Listando casos urgentes...");
            List<Beneficiario> urgentes = bo.listarCasosUrgentes();
            if (urgentes.isEmpty()) {
                System.out.println("  Nenhum caso urgente no momento.");
            } else {
                for (Beneficiario u : urgentes) {
                    System.out.println("  URGENTE -> " + u.getNome() + " | Problema: " + u.getProblema());
                }
            }


            if (!lista.isEmpty()) {
                int idTeste = lista.get(0).getId();
                System.out.println("\n>> CRUD: Buscando por ID = " + idTeste + "...");
                Beneficiario encontrado = bo.buscarPorId(idTeste);
                System.out.println("  Encontrado: " + encontrado);

                System.out.println("\n>> CRUD: Atualizando beneficiario ID " + idTeste + "...");
                encontrado.setProblema("Tratamento de canal necessario");
                bo.atualizar(encontrado);
                System.out.println("  Atualizado: " + bo.buscarPorId(idTeste));


                System.out.println("\n>> CRUD: Excluindo beneficiario ID " + idTeste + "...");
                bo.deletar(idTeste);
                System.out.println("  Excluido com sucesso!");
                System.out.println("  Total apos exclusao: " + bo.listarTodos().size() + " beneficiario(s).");
            }

            System.out.println("\n>> TESTE de Excecao: CPF invalido...");
            try {
                Beneficiario invalido = new Beneficiario(0, "Teste", "000.000.000-00",
                        LocalDate.of(1995, 1, 1), "00000-000", "problema");
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