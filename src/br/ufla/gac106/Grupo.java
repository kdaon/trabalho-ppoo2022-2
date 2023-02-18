package br.ufla.gac106;

import br.ufla.gac106.javaWikiAPI.*;

public class Grupo extends Carreira {
    private int nIntegrantes;
    private String[] integrantes;

    public Grupo(String nome, String descricao, String dataInicio, String dataFim, int nIntegrantes,
            String[] integrantes) {
        super(nome, descricao, dataInicio, dataFim, "GRUPO");
        this.nIntegrantes = nIntegrantes;
        this.integrantes = integrantes;
    }

    public Grupo(String linha) {
        super(Carreira.getCampos(linha)[0], Carreira.getCampos(linha)[1], Carreira.getCampos(linha)[6],
                Carreira.getCampos(linha)[8], "GRUPO");
        String[] integrantes = Carreira.getCampos(linha)[9].split(",");
        nIntegrantes = integrantes.length;
        this.integrantes = integrantes;
    }

    public Grupo(Carreira c, int nIntegrantes, String[] integrantes) {
        super(c);
        this.nIntegrantes = nIntegrantes;
        this.integrantes = integrantes;

    }

    @Override
    public String toString() {
        String objeto = super.toString();
        for (String integrante : integrantes) {
            objeto += integrante;
            objeto += ",";
        }
        objeto += "&&&\n";

        return objeto;
    }
}
