package br.com.fiap.main;

import br.com.fiap.bo.DoadorBO;
import br.com.fiap.entities.Doador;
import br.com.fiap.exceptions.ErroNegocioException;

import java.sql.SQLException;
import java.util.List;

public class TesteDoador {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("    TESTE DE DOADOR - TURMA DO BEM");
        System.out.println("========================================\n");

        try {
            DoadorBO bo = new DoadorBO();

            System.out.println(">> METODO LOGICO: Verificar se possui contato...");
            Doador comTelefone = new Doador(0, "Lucas ", "529.982.247-25", "11999999999", "joao@email.com");
            Doador semTelefone = new Doador(0, "Paulo", "529.982.247-25", "", "maria@email.com");
            System.out.println("  Com telefone: " + bo.classificarPerfil(comTelefone));
            System.out.println("  Sem telefone: " + bo.classificarPerfil(semTelefone));


            System.out.println("\n>> CRUD: Inserindo doador...");
            Doador d = new Doador(0, "Rafael", "529.982.247-25", "11988887777", "roberto@email.com");
            bo.cadastrarComValidacao(d);
            System.out.println("  Doador inserido com sucesso!");

            System.out.println("\n>> CRUD: Listando todos os doadores...");
            List<Doador> lista = bo.listarTodos();
            for (Doador doa : lista) {
                System.out.println("  " + doa);
            }

            if (!lista.isEmpty()) {
                int idTeste = lista.get(0).getId();

                System.out.println("\n>> CRUD: Buscando por ID = " + idTeste + "...");
                Doador encontrado = bo.buscarPorId(idTeste);
                System.out.println("  Encontrado: " + encontrado);

                System.out.println("\n>> CRUD: Atualizando telefone...");
                encontrado.setTelefone("11977776666");
                bo.atualizar(encontrado);
                System.out.println("  Atualizado: " + bo.buscarPorId(idTeste));

                System.out.println("\n>> CRUD: Excluindo doador ID " + idTeste + "...");
                bo.deletar(idTeste);
                System.out.println("  Excluido com sucesso!");
            }

            System.out.println("\n>> TESTE de Excecao: Nome vazio...");
            try {
                Doador invalido = new Doador(0, "", "529.982.247-25", "11900000000", "x@x.com");
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