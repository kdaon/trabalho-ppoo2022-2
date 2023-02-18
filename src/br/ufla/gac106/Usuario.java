package br.ufla.gac106;

public class Usuario {

    private String nome;
    private int hashsenha;
    private int nAvaliacoes;
    private String tipo;

    public Usuario(String nome, int senha, String tipo) throws Exception {
        if (senha >= 1000 && senha < 0) {
            throw new Exception("Senha invalida");
        } else {
            this.nome = nome;
            this.hashsenha = senha % 127;
            this.nAvaliacoes = 0;
            this.tipo = tipo;
        }
    }

    public Usuario(String linha) {
        String[] campos = linha.split("##");
        this.nome = campos[0];
        this.hashsenha = Integer.parseInt(campos[1]);
        this.tipo = campos[3];
    }

    public boolean veficacao(String nome, int senha) {
        return this.nome.equals(nome) && senha % 127 == this.hashsenha;
    }

    public void incrementaAvaliacao() {
        this.nAvaliacoes++;
    }

    public String getTipo() {
        return this.tipo;
    }

    @Override
    public String toString() {
        String objeto = "";
        objeto += this.nome;
        objeto += "##";
        objeto += String.valueOf(this.hashsenha);
        objeto += "##";
        objeto += String.valueOf(this.nAvaliacoes);
        objeto += "##";
        objeto += this.tipo;
        objeto += "\n";
        return objeto;
    }
}
