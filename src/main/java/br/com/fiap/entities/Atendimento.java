package br.com.fiap.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Atendimento {

    private int id;
    private LocalDate data;
    private String receita;
    private String descricao;
    private int idBeneficiario;
    private int idDentista;

    public Atendimento() {}

    public Atendimento(int id, LocalDate data, String receita, String descricao, int idBeneficiario, int idDentista) {
        this.id = id;
        this.data = data;
        this.receita = receita;
        this.descricao = descricao;
        this.idBeneficiario = idBeneficiario;
        this.idDentista = idDentista;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public String getReceita() { return receita; }
    public void setReceita(String receita) { this.receita = receita; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public int getIdBeneficiario() { return idBeneficiario; }
    public void setIdBeneficiario(int idBeneficiario) { this.idBeneficiario = idBeneficiario; }
    public int getIdDentista() { return idDentista; }
    public void setIdDentista(int idDentista) { this.idDentista = idDentista; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Atendimento{id=" + id +
                ", data=" + (data != null ? data.format(fmt) : "N/A") +
                ", receita='" + receita + "', descricao='" + descricao +
                "', idBeneficiario=" + idBeneficiario + ", idDentista=" + idDentista + '}';
    }
}