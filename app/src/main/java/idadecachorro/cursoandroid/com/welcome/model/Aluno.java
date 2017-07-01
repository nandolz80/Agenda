package idadecachorro.cursoandroid.com.welcome.model;

import java.io.Serializable;

/**
 * Created by Fernando on 27/06/2017.
 */

public class Aluno implements Serializable{

    private Long id;
    private String nome;
    private String site;
    private String endereco;
    private String telefone;
    private String foto;
    private Double nota;

    @Override
    public String toString() {
        return getId() + " - " + getNome();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
}
