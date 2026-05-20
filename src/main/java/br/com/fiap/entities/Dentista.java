package br.com.fiap.entities;

public class Dentista {

    private int id;
    private String nome;
    private String cro;
    private String especialidade;
    private String cep;

    public Dentista() {}

    public Dentista(int id, String nome, String cro, String especialidade, String cep) {
        this.id = id;
        this.nome = nome;
        this.cro = cro;
        this.especialidade = especialidade;
        this.cep = cep;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCro() { return cro; }
    public void setCro(String cro) { this.cro = cro; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    @Override
    public String toString() {
        return "Dentista{id=" + id + ", nome='" + nome + "', cro='" + cro +
                "', especialidade='" + especialidade + "', cep='" + cep + "'}";
    }
}
