package br.ufla.gac106;

import br.ufla.gac106.javaWikiAPI.*;

public class Solista extends Carreira {
    private int nBandasPassadas;
    private String[] bandasPassadas;

    public Solista(String nome, String descricao, String dataDeInicio, String dataDeFim, int nBandasPassadas,
            String[] bandasPassadas) {
        super(nome, descricao, dataDeInicio, dataDeFim, "SOLO");
        this.nBandasPassadas = nBandasPassadas;
        this.bandasPassadas = bandasPassadas;
    }

    public Solista(Carreira c, int nbandas, String[] bandasPassadas) {
        super(c);
        this.nBandasPassadas = nbandas;
        this.bandasPassadas = bandasPassadas;

    }

    public Solista(String linha) {
        super(Carreira.getCampos(linha)[0], Carreira.getCampos(linha)[1], Carreira.getCampos(linha)[6],
                Carreira.getCampos(linha)[8], "SOLO");
        String[] bandasPassadas = Carreira.getCampos(linha)[9].split(",");
        nBandasPassadas = bandasPassadas.length;
        this.bandasPassadas = bandasPassadas;
    }

    @Override
    public String toString() {
        String objeto = super.toString();
        for (String banda : bandasPassadas) {
            objeto += banda;
            objeto += ",";
        }
        objeto += "&&&\n";

        return objeto;
    }
}
