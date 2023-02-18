package br.ufla.gac106;

import java.util.Scanner;

public class Ui {

    private Scanner s;
    private Administracao a;

    public Ui() {
        s = new Scanner(System.in);
        String usuario = pedirString("Digite o nome do usuario");
        int senha = pedirSenha("Digite a senha");
        a = new Administracao(usuario, senha);
    }

    private int pedirInt(String pergunta) {
        try {
            int retorno = Integer.valueOf(pedirString(pergunta));
            s.nextLine();
            return retorno;
        } catch (Exception e) {
            System.out.println("Digite um numero inteiro, por favor");
            return pedirInt(pergunta);
        }
    }

    private String pedirString(String pergunta) {
        System.out.println(pergunta);
        String retorno = s.nextLine();
        return retorno;
    }

    private boolean cadastrarUsuario() {
        String nome = pedirString("Digite o login do usuario");
        int senha = pedirSenha("Digite a senha, um numero entre 0 e 999");
        String tipo = pedirString("Digite o tipo de usuario");
        return a.cadastraUsuario(nome, senha, tipo);
    }

    private int pedirSenha(String pergunta) {
        int senha = -1;
        senha = pedirInt(pergunta);
        if (senha > 999 || senha < 0) {
            System.out.println("Senha invalida, precisa ser um numero entre 0 e 999");
            return pedirSenha(pergunta);
        } else {
            return senha;
        }
    }

    public Carreira cadastraCarreira(String tipo) {
        String nome = pedirString("Digite o nome do " + tipo);
        String descricao = Carreira.getDescricao(nome);
        // String descricao = "teste";
        System.out.println(descricao);
        String dataInicio = pedirString("Digite a data do inicio da Carreira");
        String datafim = pedirString("Digte a data de fim");
        return new Carreira(nome, descricao, dataInicio, datafim, tipo);
    }

    public boolean cadastraSolista() {
        Carreira c = cadastraCarreira("SOLO");
        int nBandasPassadas = pedirInt("Digite o numero de bandas passadas");
        String[] bandasPassadas = new String[nBandasPassadas];
        for (int i = 0; i < nBandasPassadas; i++) {
            String bandaPassada = pedirString("Digite uma banda passada");
            bandasPassadas[i] = bandaPassada;
        }
        return a.cadastrarSolo(c, nBandasPassadas, bandasPassadas);
    }

    public Boolean cadastraGrupo() {
        Carreira c = cadastraCarreira("GRUPO");
        int nIntegrantes = Integer.valueOf(pedirString("Digite o numero de integrantes do grupo"));
        String[] integrantes = new String[nIntegrantes];
        for (int i = 0; i < nIntegrantes; i++) {
            String integrante = pedirString("Digite um integrante do grupo");
            integrantes[i] = integrante;
        }
        return a.cadastrarGrupo(c, nIntegrantes, integrantes);
    }

    private boolean buscarCarreira(String tipo) {
        String nome = pedirString("Digite o nome do " + tipo + " que deseja procurar");
        if (a.imprimeCarreira(nome)) {
        } else {
            System.out.println(tipo + " nao encontrado");
        }
        return true;
    }

    public boolean buscarSolista() {
        return buscarCarreira("SOLO");
    }

    public boolean buscarGrupo() {
        return buscarCarreira("GRUPO");
    }

    public boolean deletaCarreira(String nome) {
        return a.deletaCarreira(nome);
    }

    public boolean AtualizarSolista() {
        String nome = pedirString("Digite o nome do Solista que deseja atualizar");
        if (deletaCarreira(nome)) {
            return cadastraSolista();
        }
        return false;
    }

    public boolean AtualizarGrupo() {
        String nome = pedirString("Digite o nome do grupo que deseja atualizar");
        if (deletaCarreira(nome)) {
            return cadastraGrupo();
        }
        return false;
    }

    public boolean deletarSolista() {
        String nome = pedirString("Digite o nome do solista que deseja deletar");
        return deletaCarreira(nome);
    }

    public boolean deletarGrupo() {
        String nome = pedirString("Digite o nome do grupo que deseja deletar");
        return deletaCarreira(nome);
    }

    public void imprimirMenu() {
        System.out.println("O que deseja fazer?");
        System.out.println("1- Criar usuario");
        System.out.println("2- Cadastrar solista");
        System.out.println("3- Cadastrar grupo");
        System.out.println("4- Buscar solista");
        System.out.println("5- Buscar grupo");
        System.out.println("6 -Atualizar solista");
        System.out.println("7 -Atualizar grupo");
        System.out.println("8- Deletar solista");
        System.out.println("9- Deletar grupo");
        System.out.println("10- Avaliar carreira");
        System.out.println("11- Sair");
    }

    public boolean menu() {
        imprimirMenu();

        int opcao = pedirInt("");
        boolean operacaoBemSucedida = false;
        switch (opcao) {
            case 1:
                operacaoBemSucedida = cadastrarUsuario();
                break;

            case 2:
                operacaoBemSucedida = cadastraSolista();
                break;

            case 3:
                operacaoBemSucedida = cadastraGrupo();
                break;

            case 4:
                operacaoBemSucedida = buscarSolista();
                break;

            case 5:
                operacaoBemSucedida = buscarGrupo();
                break;

            case 6:
                operacaoBemSucedida = AtualizarSolista();
                break;

            case 7:
                operacaoBemSucedida = AtualizarGrupo();
                break;

            case 8:
                operacaoBemSucedida = deletarSolista();
                break;

            case 9:
                operacaoBemSucedida = deletarGrupo();
                break;

            case 10:
                operacaoBemSucedida = false;
                System.out.println("Operacao ainda nao implementada");
                break;

            case 11:
                return false;
        }
        if (operacaoBemSucedida) {
            System.out.println("Operacao realizada com sucesso");
        } else {
            System.out.println("Operacao nao realzada");
        }
        return true;
    }

    public void executar() {
        boolean sair;
        do {
            sair = menu();
        } while (sair);
    }
}
