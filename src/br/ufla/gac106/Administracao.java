package br.ufla.gac106;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Administracao {
    private Usuario usuario;

    public Administracao(String nome, int senha) {
        try {
            FileWriter escritor;
            File carreiras = new File("carreiras.txt");
            if (!carreiras.exists()) {
                carreiras.createNewFile();
                try {
                    escritor = new FileWriter("carreiras.txt", false);
                    escritor.write("0\n");
                    escritor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            File usuarios = new File("usuarios.txt");
            if (!usuarios.exists()) {
                usuarios.createNewFile();
                escritor = new FileWriter("usuarios.txt", false);
                escritor.write("2\n");
                escritor.write(new Usuario("admin", 127, "administrador").toString());
                escritor.write(new Usuario("Anonimo", 000, "comum").toString());
                escritor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Usuario usuario = buscaUsuario(nome, senha);
        if (usuario == null) {
            try {
                usuario = new Usuario("Anonimo", 000, "comum");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.usuario = usuario;
    }

    private int leTamanho(String nomeArquivo) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(nomeArquivo));
            int retorno = Integer.valueOf(br.readLine());
            return retorno;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int buscaBinariaUsuario(String nome, int inicio, int fim) {
        if (inicio > fim) {
            return -1;
        } else {
            int meio = (inicio + fim) / 2;
            try {

                BufferedReader leitor = new BufferedReader(new FileReader("usuarios.txt"));
                String linha = "";
                for (int i = 0; i <= meio; i++) {
                    linha = leitor.readLine();
                }
                leitor.close();
                String[] campos = linha.split("&&&");
                campos = campos[0].split("##");
                String usuario = campos[0];
                if (usuario.equals(nome)) {
                    return meio;
                } else if (usuario.compareTo(nome) < 0) {
                    return buscaBinariaUsuario(nome, inicio, meio - 1);
                } else {
                    return buscaBinariaUsuario(nome, meio + 1, fim);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public boolean deletaCarreira(String nome) {
        int pos = buscaBinariaCarreira(nome, 1, leTamanho("carreiras.txt"));
        if (pos >= 0) {
            deletaLinha("carreiras.txt", pos);
            deletaArquivo(nome + ".txt");
            return true;
        }
        return false;
    }

    public boolean deletaLinha(String nomeArquivo, int numeroDalinha) {
        try {
            BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo));
            int tamanho = Integer.valueOf(leitor.readLine());
            String linha = String.valueOf(tamanho - 1);
            for (int i = 1; i < numeroDalinha; i++) {
                linha += leitor.readLine() + "\n";
            }
            leitor.readLine();
            for (int i = numeroDalinha + 1; i < tamanho; i++) {
                linha += leitor.readLine() + "\n";
            }
            FileWriter escritor = new FileWriter(nomeArquivo);
            escritor.write(linha);
            escritor.close();
            leitor.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Usuario buscaUsuario(String nome, int senha) {
        try {
            BufferedReader leitor = new BufferedReader(new FileReader("usuarios.txt"));
            int tamanho = Integer.valueOf(leitor.readLine());
            if (tamanho > 0) {
                int pos = buscaBinariaUsuario(nome, 1, tamanho);
                if (pos > 0) {
                    String linha = "";
                    for (int i = 1; i <= pos; i++) {
                        linha = leitor.readLine();
                    }
                    leitor.close();
                    Usuario retorno = new Usuario(linha);
                    if (retorno.veficacao(nome, senha)) {
                        return retorno;
                    }
                }
            }
            leitor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int buscaBinariaCarreira(String nome, int inicio, int fim) {
        if (inicio > fim) {
            return -1;
        } else {
            int meio = (inicio + fim) / 2;
            try {

                BufferedReader leitor = new BufferedReader(new FileReader("carreiras.txt"));
                String linha = "";
                leitor.readLine();
                for (int i = 1; i <= meio; i++) {
                    linha = leitor.readLine();
                }
                leitor.close();
                String[] campos = linha.split("&&&");
                campos = campos[0].split("##");
                String carreira = campos[0];
                // System.out.println(carreira);
                if (carreira.equals(nome)) {
                    return meio;
                } else if (carreira.compareTo(nome) < 0) {
                    return buscaBinariaCarreira(nome, inicio, meio - 1);
                } else {
                    return buscaBinariaCarreira(nome, meio + 1, fim);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    public boolean deletaArquivo(String nomeArquivo) {
        File arquivo = new File(nomeArquivo);
        if (arquivo.exists()) {
            arquivo.delete();
            return true;
        }
        return false;
    }

    public Perfil buscaCarreira(String nome) {
        try {
            BufferedReader leitor = new BufferedReader(new FileReader("carreiras.txt"));
            int tamanho = Integer.valueOf(leitor.readLine());
            int pos = buscaBinariaCarreira(nome, 1, tamanho);
            if (pos >= 0) {
                String linha = "";
                for (int i = 1; i <= pos; i++) {
                    linha = leitor.readLine();
                }
                leitor.close();
                Perfil retorno = new Perfil(linha);
                return retorno;
            }
            leitor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean merge(String nomeArquivo, int inicio, int meio, int fim) {
        try {
            // System.out.println("Merge de inicio em " + inicio + " com meio em " + meio +
            // " e fim em " + fim);
            BufferedReader r1 = new BufferedReader(new FileReader(nomeArquivo));
            BufferedReader r2 = new BufferedReader(new FileReader(nomeArquivo));
            FileWriter escritor = new FileWriter("aux.txt", false);
            String linha1 = r1.readLine();
            int tamanho = Integer.valueOf(linha1);
            String linha2 = r2.readLine();
            int i = 0;
            int j = 0;
            linha1 = r1.readLine();
            while (i < inicio - 1) {
                // System.out.println("i = " + i + " indo até " + inicio);
                linha1 = r1.readLine();
                i++;
                // System.out.println(linha1);
            }
            // System.out.println("Ultima linha lida por i= " + linha1);
            while (j <= meio) {
                // System.out.println("j = " + j + " indo até " + meio);
                linha2 = r2.readLine();
                j++;
                // System.out.println(linha2);
            }
            // System.out.flush();
            while (i < meio && j <= fim) {
                // System.out.flush();
                // System.out.println(" while i= " + i + " < " + meio + " j= " + j + " <= " +
                // fim);
                String[] campos1 = linha1.split("$$$");
                // System.out.println("linha1 = " + linha1);
                campos1 = campos1[0].split("##");
                // System.out.println("linha2 = " + linha2);
                String[] campos2 = linha2.split("&&&");
                campos2 = campos2[0].split("##");
                if (campos1[0].compareTo(campos2[0]) < 0) {
                    escritor.write(linha1 + "\n");
                    linha1 = r1.readLine();
                    // System.out.println("ESCREVEU LINHA 1");
                    i++;
                } else {
                    escritor.write(linha2 + "\n");
                    // System.out.println("ESCREVEU LINHA 2");
                    j++;
                    linha2 = r2.readLine();

                }
            }
            while (i < meio) {
                // System.out.flush();
                // System.out.println("while i= " + i + " < meio= " + meio);
                escritor.write(linha1 + "\n");
                // System.out.println("ESCREVEU LINHA 1");
                i++;
                linha1 = r1.readLine();
            }
            while (j <= fim) {
                // System.out.flush();
                // System.out.println("while j= " + j + " <= fim = " + fim);
                escritor.write(linha2 + "\n");
                // System.out.println("ESCREVEU LINHA 2");
                j++;
                linha2 = r2.readLine();
            }
            r1.close();
            r2.close();
            escritor.close();
            r1 = new BufferedReader(new FileReader("aux.txt"));
            r2 = new BufferedReader(new FileReader(nomeArquivo));
            String merge = r2.readLine() + "\n";
            int k = 0;
            while (k < inicio - 1) {
                // System.out.flush();
                // System.out.println("while " + k + " < inicio = " + inicio + " MERGE1 = " +
                // merge);

                merge += r2.readLine() + "\n";
                k++;
            }
            // System.out.println("MERGE1 final " + merge);
            while (k < fim) {
                // System.out.flush();
                // System.out.println("while k = " + k + " < fim = " + fim + " MERGE2 = " +
                // merge);
                merge += r1.readLine() + "\n";
                r2.readLine();
                k++;
            }
            // System.out.println("MERGE2 final " + merge);
            while (k < tamanho) {
                // System.out.flush();
                // System.out.println("while k= " + k + " < tamanho = " + tamanho + " MERGE3 = "
                // + merge);
                merge += r2.readLine() + "\n";
                // System.out.println("k = " + k + " MERGE3 = " + merge);
                k++;
            }
            // System.out.println("MERGE FINAL " + merge);
            r1.close();
            deletaArquivo("aux.txt");
            r2.close();
            escritor = new FileWriter(nomeArquivo);
            escritor.write(merge);
            escritor.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private boolean mergeSort(String nomeArquivo, int inicio, int fim) {
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;
            // System.out.flush();
            // System.out.print("mergeSort ");
            // System.out.print("inicio ´= " + inicio + " ");
            // System.out.print("meio = " + meio + " ");
            // System.out.println("fim =" + fim);
            mergeSort(nomeArquivo, inicio, meio);
            mergeSort(nomeArquivo, meio + 1, fim);
            return merge(nomeArquivo, inicio, meio, fim);
        }
        return true;
    }

    public boolean imprimeCarreira(String nome) {
        Perfil p = this.buscaCarreira(nome);
        if (p != null) {
            try {
                BufferedReader leitor = new BufferedReader(new FileReader(p.getNome() + ".txt"));
                boolean sentinela = true;
                while (sentinela) {
                    String linha = leitor.readLine();
                    if (linha == null) {
                        sentinela = false;
                    } else {
                        System.out.println(linha);
                    }
                }
                leitor.close();
                return true;
            } catch (Exception e) {
                System.out.println("Erro ao procurar perfil");
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean ordenaArquivo(String nomeArquivo) {
        try {
            BufferedReader leitorDeTamanho = new BufferedReader(new FileReader(nomeArquivo));
            String tam = leitorDeTamanho.readLine();
            // System.out.println(tam);
            int tamanho = Integer.valueOf(tam);
            // System.out.println("tamanho = " + tamanho);
            leitorDeTamanho.close();
            return mergeSort(nomeArquivo, 0, tamanho);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean escreveCarreira(Perfil p) {
        if (buscaCarreira(p.getNome()) == null) {
            try {
                BufferedReader leitor = new BufferedReader(new FileReader("carreiras.txt"));
                int tamanho = Integer.valueOf(leitor.readLine());
                String arquivo = String.valueOf(tamanho + 1);
                arquivo += "\n";
                for (int i = 0; i < tamanho; i++) {
                    arquivo += leitor.readLine() + "\n";
                }
                leitor.close();
                arquivo += p.toString();
                FileWriter escritor = new FileWriter("carreiras.txt", false);
                escritor.write(arquivo);
                escritor.close();
                return ordenaArquivo("carreiras.txt");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean cadastrarSolo(Carreira c, int nbandas, String[] bandas) {
        if (buscaCarreira(c.getNome()) == null) {
            Solista solista = new Solista(c, nbandas, bandas);
            Perfil perfil = new Perfil(solista);
            if (escreveCarreira(perfil)) {
                if (criaArquivo(solista)) {
                    return true;
                } else {
                    // System.out.println("Erro ao criar pagina");
                }
            } else {
                // System.out.println("Erro ao cadastrar perfil");
            }
        }
        return false;
    }

    public boolean cadastrarGrupo(Carreira c, int nIntegrantes,
            String[] integrantes) {
        if (buscaCarreira(c.getNome()) == null) {
            Grupo grupo = new Grupo(c, nIntegrantes, integrantes);
            if (escreveCarreira(new Perfil(grupo)))
                if (criaArquivo(grupo)) {
                    return true;
                }
        }
        return false;
    }

    public boolean criaArquivo(Carreira c) {
        try {
            // System.out.println("Nome da carreira no criaarquivo = " + c.getNome());
            File f = new File(c.getNome() + ".txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            FileWriter fw = new FileWriter(c.getNome() + ".txt", false);
            fw.write(c.toString());
            fw.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            // System.out.println("Erro no criaArquivo");
        }
        return false;
    }

    /*
     * public boolean adicionaPerfil(Perfil c) {
     * return false;
     * }
     */

    public boolean atualizaPerfil(Perfil c) {
        try {
            if (this.usuario.getTipo().equals("administrador") || this.usuario.getTipo().equals("moderador")) {
                BufferedReader br = new BufferedReader(new FileReader("carreiras.txt"));
                int tamanho = Integer.valueOf(br.readLine());
                int pos = buscaBinariaUsuario(c.getNome(), 0, tamanho);
                String perfis = "";
                for (int i = 1; i < pos; i++) {
                    perfis += br.readLine() + "\n";
                }
                br.readLine();
                perfis += c.toString();
                for (int i = pos + 1; i < tamanho; i++) {
                    perfis += br.readLine() + "\n";
                }
                FileWriter f = new FileWriter("carreiras.txt", false);
                f.write(perfis);
                ordenaArquivo("carreiras.txt");
                f.close();
                br.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean avaliar(String nome, double media) {
        Perfil c = buscaCarreira(nome);
        if (c == null) {
            // System.out.println("Erro");
            return false;
        } else {
            c.setMedia(((c.getMedia() * c.getNAvaliacoes()) + media) / (c.getNAvaliacoes() + 1));
            c.incAvaliacoes();
            return atualizaPerfil(c);
        }
    }

    public boolean cadastraUsuario(String nome, int senha, String tipo) {
        if (tipo.equals("moderador") || tipo.equals("administrador")) {
            if (!this.usuario.getTipo().equals("administrador")) {
                return false;
            }
        }
        try {
            BufferedReader leitor = new BufferedReader(new FileReader("usuarios.txt"));
            int tamanho = Integer.valueOf(leitor.readLine());
            String arquivo = String.valueOf(tamanho + 1);
            arquivo += "\n";
            for (int i = 1; i <= tamanho; i++) {
                arquivo += leitor.readLine() + "\n";
            }
            arquivo += new Usuario(nome, senha, tipo).toString();
            FileWriter escritor = new FileWriter("usuarios.txt");
            escritor.write(arquivo);
            escritor.close();
            leitor.close();
            return ordenaArquivo("usuarios.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
