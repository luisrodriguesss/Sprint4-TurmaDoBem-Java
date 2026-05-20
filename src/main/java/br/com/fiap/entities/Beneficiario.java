package br.com.fiap.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Beneficiario {

    private int id;
    private String nome;
    private String cpf;
    private LocalDate nascimento;
    private String cep;
    private String problema;

    public Beneficiario() {}

    public Beneficiario(int id, String nome, String cpf, LocalDate nascimento, String cep, String problema) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.nascimento = nascimento;
        this.cep = cep;
        this.problema = problema;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public LocalDate getNascimento() { return nascimento; }
    public void setNascimento(LocalDate nascimento) { this.nascimento = nascimento; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }
    public String getProblema() { return problema; }
    public void setProblema(String problema) { this.problema = problema; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Beneficiario{id=" + id + ", nome='" + nome + "', cpf='" + cpf +
                "', nascimento=" + (nascimento != null ? nascimento.format(fmt) : "N/A") +
                ", cep='" + cep + "', problema='" + problema + "'}";
    }
}