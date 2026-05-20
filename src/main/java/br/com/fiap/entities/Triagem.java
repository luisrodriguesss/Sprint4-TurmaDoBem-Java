package br.com.fiap.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Triagem {

    private int id;
    private LocalDate data;
    private String local;
    private String criterios;
    private int idBeneficiario;
    private int idDentista;

    public Triagem() {}

    public Triagem(int id, LocalDate data, String local, String criterios, int idBeneficiario, int idDentista) {
        this.id = id;
        this.data = data;
        this.local = local;
        this.criterios = criterios;
        this.idBeneficiario = idBeneficiario;
        this.idDentista = idDentista;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }
    public String getCriterios() { return criterios; }
    public void setCriterios(String criterios) { this.criterios = criterios; }
    public int getIdBeneficiario() { return idBeneficiario; }
    public void setIdBeneficiario(int idBeneficiario) { this.idBeneficiario = idBeneficiario; }
    public int getIdDentista() { return idDentista; }
    public void setIdDentista(int idDentista) { this.idDentista = idDentista; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "Triagem{id=" + id +
                ", data=" + (data != null ? data.format(fmt) : "N/A") +
                ", local='" + local + "', criterios='" + criterios +
                "', idBeneficiario=" + idBeneficiario + ", idDentista=" + idDentista + '}';
    }
}