package br.com.fiap.main;

import br.com.fiap.bo.DentistaBO;
import br.com.fiap.entities.Dentista;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.util.List;

public class TesteDentista {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("    TESTE DE DENTISTA - TURMA DO BEM");
        System.out.println("========================================\n");

        try {
            DentistaBO bo = new DentistaBO();

            System.out.println(">> METODO LOGICO: Validacao de CRO");
            System.out.println("CRO '123' (invalido): " + bo.validarCro("123"));
            System.out.println("CRO '12345' (valido):  " + bo.validarCro("12345"));

            System.out.println("\n>> CRUD: Inserindo dentista...");
            Dentista d = new Dentista(0, "Dr. Luis ", "54321", "Ortodontia", "01310-100");
            bo.cadastrarComValidacao(d);
            System.out.println("Dentista inserido com sucesso!");

            System.out.println("\n>> CRUD: Listando todos os dentistas...");
            List<Dentista> lista = bo.listarTodos();
            for (Dentista den : lista) {
                System.out.println("  " + den);
            }

            System.out.println("\n>> METODO LOGICO: Buscando por especialidade 'Ortodontia'...");
            List<Dentista> ortodontistas = bo.buscarPorEspecialidade("Ortodontia");
            if (ortodontistas.isEmpty()) {
                System.out.println("  Nenhum dentista encontrado.");
            } else {
                for (Dentista den : ortodontistas) {
                    System.out.println("  " + den.getNome() + " - " + den.getEspecialidade());
                }
            }

            if (!lista.isEmpty()) {
                int idTeste = lista.get(0).getId();
                System.out.println("\n>> CRUD: Buscando por ID = " + idTeste + "...");
                Dentista encontrado = bo.buscarPorId(idTeste);
                System.out.println("  Encontrado: " + encontrado);

                System.out.println("\n>> CRUD: Atualizando especialidade...");
                encontrado.setEspecialidade("Endodontia");
                bo.atualizar(encontrado);
                System.out.println("  Atualizado: " + bo.buscarPorId(idTeste));

                System.out.println("\n>> CRUD: Excluindo dentista ID " + idTeste + "...");
                bo.deletar(idTeste);
                System.out.println("  Excluido com sucesso!");
            }

            System.out.println("\n>> TESTE de Excecao: CRO invalido...");
            try {
                Dentista invalido = new Dentista(0, "Dr. X", "12", "Ortodontia", "00000-000");
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