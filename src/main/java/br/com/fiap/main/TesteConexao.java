package br.com.fiap.main;

import br.com.fiap.conexoes.ConexaoFactory;

import java.sql.Connection;
import java.sql.SQLException;


public class TesteConexao {

    public static void main(String[] args) {
        System.out.println(">> Testando conexão com o banco Oracle FIAP...");
        try {
            Connection cn = new ConexaoFactory().conexao();
            System.out.println("[OK] Conectado com sucesso ao banco de dados!");
            cn.close();
            System.out.println("[OK] Conexão encerrada corretamente.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("[ERRO] Falha na conexão: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
