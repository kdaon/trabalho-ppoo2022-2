package br.ufla.gac106;

import br.ufla.gac106.javaWikiAPI.Wiki.*;
import br.ufla.gac106.javaWikiAPI.*;

public class Carreira {
    protected String nome;
    protected int numeroAvaliacoes;
    protected double mediaAvaliacao;
    protected String descricao;
    protected String dataDeInicio;
    protected String dataDeFim;
    protected String tipo;

    public String getNome() {
        return this.nome;
    }

    public Carreira(String nome, String descricao, String dataIncio, String dataFim, String tipo) {
        this.nome = nome;
        this.dataDeInicio = dataIncio;
        this.dataDeFim = dataFim;
        this.tipo = tipo;
        this.descricao = descricao;
        this.numeroAvaliacoes = 0;
        this.mediaAvaliacao = 0;
    }

    public Carreira(String nome, String dataIncio, String dataFim, String tipo, int nAvaliacoes, double media) {
        this.nome = nome;
        this.dataDeInicio = dataIncio;
        this.dataDeFim = dataFim;
        this.tipo = tipo;
        this.descricao = Carreira.getDescricao(nome);
        this.numeroAvaliacoes = nAvaliacoes;
        this.mediaAvaliacao = media;
    }

    public Carreira(Carreira c) {
        this.nome = c.nome;
        this.dataDeFim = c.dataDeFim;
        this.dataDeInicio = c.dataDeInicio;
        this.descricao = c.descricao;
        this.dataDeInicio = c.dataDeInicio;
        this.dataDeFim = c.dataDeFim;
        this.tipo = c.tipo;
        this.numeroAvaliacoes = c.numeroAvaliacoes;
        this.mediaAvaliacao = c.mediaAvaliacao;
    }

    public static String getDescricao(String nome) {
        try {
            Wiki wiki = new Wiki();
            br.ufla.gac106.javaWikiAPI.PaginaWiki paginaCarreira = wiki.consultarPagina(nome);
            wiki.close();
            String descricao = paginaCarreira.getResumo().replace("\n", " ");
            return descricao;
        } catch (Exception e) {
            System.out.println("Erro ao tentar extrair da wiki");
            e.getCause();
            e.printStackTrace();
            return "ERRO";
        }
    }

    public static String[] getCampos(String linha) {
        String[] campos = linha.split("&&&");
        campos = campos[1].split("##");
        return campos;
    }

    @Override
    public String toString() {
        String objeto = "&&&";
        objeto += nome += "##";

        objeto += String.valueOf(mediaAvaliacao);
        objeto += "##";
        objeto += String.valueOf(numeroAvaliacoes);
        objeto += "##";
        objeto += tipo;
        objeto += "##";
        objeto += descricao;
        objeto += "##";

        objeto += dataDeInicio += "##";

        objeto += dataDeFim += "##";
        return objeto;
    }
}