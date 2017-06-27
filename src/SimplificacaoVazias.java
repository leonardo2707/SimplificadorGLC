
import java.util.Vector;

//Casse que deve retornar a gramatica sem o simbolos vazios
public class SimplificacaoVazias {

    public Vector Simplificado(Vector n, Vector t, Vector p, String s) {
        int vazias = verificarVazias(n, t, p);

        while (vazias != 0) {

            p = removerVazio(n, t, p);
            vazias = verificarVazias(n, t, p);
        }

        return p;
    }

    public int verificarVazias(Vector n, Vector t, Vector p) {
        int vazias = 0;
        Vector Producoes = p;
        String elementoDireito;

        for (int i = 0; i < p.size(); i++) {

            elementoDireito = getLadoDireito(Producoes.elementAt(i).toString());

            if (elementoDireito.equals("&")) {
                vazias++;
            }
        }

        return vazias;
    }

    public Vector removerVazio(Vector n, Vector t, Vector p) {
        Vector Producoes = organizaProducoes(p);
        Vector novaProducao = new Vector();

        String elementoDireito;
        String ladoEsquerdo;
        String NaoTerminalGeraVazio = new String();

        //Pega produção que gera vazio
        for (int i = 0; i < p.size(); i++) {

            elementoDireito = getLadoDireito(Producoes.elementAt(i).toString());
            ladoEsquerdo = getLadoEsquerdo(Producoes.elementAt(i).toString());

            if (elementoDireito.equals("&")) {
                NaoTerminalGeraVazio = ladoEsquerdo;
            }
        }

        //Deleta Vazio da producao 
        for (int i = 0; i < p.size(); i++) {

            elementoDireito = getLadoDireito(Producoes.elementAt(i).toString());
            ladoEsquerdo = getLadoEsquerdo(Producoes.elementAt(i).toString());
            if (ladoEsquerdo.equals(NaoTerminalGeraVazio)) {
                if (elementoDireito.equals("&")) {
                    Producoes.remove(i);
                }
            }
        }

        String producoesPossiveis = "";
        
        //trocar producções que possuim o não terminal que tinha o vazio
        for (int i = 0; i < p.size(); i++) {

            elementoDireito = getLadoDireito(Producoes.elementAt(i).toString());
            ladoEsquerdo = getLadoEsquerdo(Producoes.elementAt(i).toString());
            if(elementoDireito.contains(NaoTerminalGeraVazio))
            {
                producoesPossiveis = geraTodasProducoes(elementoDireito, NaoTerminalGeraVazio);
                novaProducao.add(ladoEsquerdo + "=" + producoesPossiveis );
                
            }else
            {
                novaProducao.add(Producoes.elementAt(i));
            }
        }
        
        novaProducao = organizaProducoes(novaProducao);

        return novaProducao;
    }
    
    public String geraTodasProducoes(String producao, String NaoTerminal)
    {
        return "";
    }

    public Vector organizaProducoes(Vector p) {
        String producaoComleta = "";
        String ladoEsquerdo;
        String LadoDireito;
        String producaoAnterior = "";

        for (int i = 0; i < p.size(); i++) {
            ladoEsquerdo = getLadoEsquerdo(p.elementAt(i).toString());
            LadoDireito = getLadoDireito(p.elementAt(i).toString());
            if (i == 0) {
                producaoComleta = ladoEsquerdo + "=" + LadoDireito;
                producaoAnterior = ladoEsquerdo;

            } else {
                if (producaoAnterior.equals(ladoEsquerdo)) {
                    producaoComleta += "|" + LadoDireito;
                } else {
                    producaoComleta += "," + ladoEsquerdo + "=" + LadoDireito;
                    producaoAnterior = ladoEsquerdo;
                }
            }
        }

        p = separaVirgula(producaoComleta);

        return p;
    }

    public Vector separaVirgula(String producaoCompleta) {
        Vector v = new Vector();
        String Producoes = "";

        String a[] = producaoCompleta.split(",");

        for (int i = 0; i < a.length; i++) {
            if (a[i].contains("|")) {
                separaPipe(v, a[i]);
            } else {
                v.add(a[i]);
            }
        }

        return v;
    }

    public void separaPipe(Vector v, String texto) {
        String a[] = texto.split("=");
        String letraInicial = a[0];
        String producoesComPipe = a[1];

        String b[] = producoesComPipe.split("\\|");
        String producao = "";

        for (int i = 0; i < b.length; i++) {
            producao = b[i];
            v.add(letraInicial + "=" + producao);
        }
    }

    public String getLadoDireito(String Producao) {
        String a[] = Producao.split("=");

        return a[1];
    }

    public String getLadoEsquerdo(String Producao) {
        String a[] = Producao.split("=");

        return a[0];
    }
}
