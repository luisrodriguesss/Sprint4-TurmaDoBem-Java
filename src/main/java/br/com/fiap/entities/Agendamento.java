package br.com.fiap.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Agendamento {

    private int id;
    private LocalDate data;
    private LocalDateTime horario;
    private String urgencia;
    private int idDentista;
    private int idBeneficiario;

    public Agendamento() {}

    public Agendamento(int id, LocalDate data, LocalDateTime horario, String urgencia, int idDentista, int idBeneficiario) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.urgencia = urgencia;
        this.idDentista = idDentista;
        this.idBeneficiario = idBeneficiario;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public LocalDateTime getHorario() { return horario; }
    public void setHorario(LocalDateTime horario) { this.horario = horario; }
    public String getUrgencia() { return urgencia; }
    public void setUrgencia(String urgencia) { this.urgencia = urgencia; }
    public int getIdDentista() { return idDentista; }
    public void setIdDentista(int idDentista) { this.idDentista = idDentista; }
    public int getIdBeneficiario() { return idBeneficiario; }
    public void setIdBeneficiario(int idBeneficiario) { this.idBeneficiario = idBeneficiario; }

    @Override
    public String toString() {
        DateTimeFormatter fmtData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter fmtHorario = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "Agendamento{id=" + id +
                ", data=" + (data != null ? data.format(fmtData) : "N/A") +
                ", horario=" + (horario != null ? horario.format(fmtHorario) : "N/A") +
                ", urgencia='" + urgencia + "', idDentista=" + idDentista +
                ", idBeneficiario=" + idBeneficiario + '}';
    }
}