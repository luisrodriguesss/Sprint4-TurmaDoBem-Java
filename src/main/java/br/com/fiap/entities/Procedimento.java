package br.com.fiap.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Procedimento {

    private int id;
    private String nome;
    private LocalDate data;
    private String relatorio;
    private int idAtendimento;

    public Procedimento() {}

    public Procedimento(int id, String nome, LocalDate data, String relatorio, int idAtendimento) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.relatorio = relatorio;
        this.idAtendimento = idAtendimento;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public String getRelatorio() { return relatorio; }
    public void setRelatorio(String relatorio) { this.relatorio = relatorio; }
    public int getIdAtendimento() { return idAtendimento; }
    public void setIdAtendimento(int idAtendimento) { this.idAtendimento = idAtendimento; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Procedimento{id=" + id + ", nome='" + nome + "', data=" +
                (data != null ? data.format(fmt) : "N/A") +
                ", relatorio='" + relatorio + "', idAtendimento=" + idAtendimento + "}";
    }
}