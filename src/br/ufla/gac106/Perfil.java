package br.ufla.gac106;

import br.ufla.gac106.s2022_2.base.Avaliacoes;

public class Perfil {
    private String nome;
    private String tipo;
    private int nAvaliacoes;
    private double mediaAvaliacao;

    public Perfil(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
        this.nAvaliacoes = 0;
        this.mediaAvaliacao = 0;
    }

    public Perfil(String linha) {
        String[] campos = linha.split("##");
        this.nome = campos[0];
        this.tipo = campos[1];
        this.nAvaliacoes = Integer.parseInt(campos[2]);
        this.mediaAvaliacao = Double.parseDouble(campos[3]);
    }

    public Perfil(Carreira c) {
        this.nome = c.nome;
        this.tipo = c.tipo;
        this.nAvaliacoes = c.numeroAvaliacoes;
        this.mediaAvaliacao = c.mediaAvaliacao;
    }

    @Override
    public String toString() {
        String objeto = "";
        objeto += this.nome;
        objeto += "##";
        objeto += this.tipo;
        objeto += "##";
        objeto += String.valueOf(nAvaliacoes);
        objeto += "##";
        objeto += String.valueOf(mediaAvaliacao);
        objeto += "## \n";
        return objeto;
    }

    public double getMedia() {
        return this.mediaAvaliacao;
    }

    public void setMedia(double novaMedia) {
        this.mediaAvaliacao = novaMedia;
    }

    public int getNAvaliacoes() {
        return this.nAvaliacoes;
    }

    public void incAvaliacoes() {
        this.nAvaliacoes++;
    }

    public String getNome() {
        return this.nome;
    }
}
