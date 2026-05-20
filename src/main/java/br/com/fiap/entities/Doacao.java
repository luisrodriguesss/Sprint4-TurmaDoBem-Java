package br.com.fiap.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Doacao {

    private int id;
    private String nomeDoador;
    private String tipo;
    private double valor;
    private LocalDate data;
    private int idDoador;

    public Doacao() {}

    public Doacao(int id, String nomeDoador, String tipo, double valor, LocalDate data, int idDoador) {
        this.id = id;
        this.nomeDoador = nomeDoador;
        this.tipo = tipo;
        this.valor = valor;
        this.data = data;
        this.idDoador = idDoador;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNomeDoador() { return nomeDoador; }
    public void setNomeDoador(String nomeDoador) { this.nomeDoador = nomeDoador; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public int getIdDoador() { return idDoador; }
    public void setIdDoador(int idDoador) { this.idDoador = idDoador; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Doacao{id=" + id + ", nomeDoador='" + nomeDoador + "', tipo='" + tipo +
                "', valor=" + valor +
                ", data=" + (data != null ? data.format(fmt) : "N/A") +
                ", idDoador=" + idDoador + '}';
    }
}