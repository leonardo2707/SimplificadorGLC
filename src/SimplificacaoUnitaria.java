
import java.util.Vector;
import javax.swing.JOptionPane;

////Casse que deve retornar a gramatica sem os simbolos unitarios
public class SimplificacaoUnitaria {

    public Vector Simplificado(Vector n, Vector t, Vector p, String s) {
        int unitarias = verificarUnitarias(n, t, p);

        while (unitarias != 0) {
            
            p = removerProducaoUnit(n, t, p);
            unitarias = verificarUnitarias(n, t, p);

        }

        return p;
    }

    public int verificarUnitarias(Vector n, Vector t, Vector p) {
        int unitarias = 0;
        Vector Producoes = p;
        String NaoTerminais;
        String elementoDireito;

        for (int i = 0; i < p.size(); i++) {

            elementoDireito = getLadoDireito(Producoes.elementAt(i).toString());
            for (int j = 0; j < n.size(); j++) {

                NaoTerminais = n.elementAt(j).toString();
                if (elementoDireito.equals(NaoTerminais)) {
                    unitarias++;
                }
            }
        }

        return unitarias;
    }

    public Vector removerProducaoUnit(Vector n, Vector t, Vector p) {
        Vector novaProducao = new Vector();

        Vector Producoes = organizaProducoes(p);
        String NaoTerminais;
        String elementoDireito;
        String ladoEsquerdo;
        String unitaria = new String();

        //PEGA PRODUCAO UNITARIA
        for (int i = 0; i < Producoes.size(); i++) {

            elementoDireito = getLadoDireito(Producoes.elementAt(i).toString());
            for (int j = 0; j < n.size(); j++) {

                NaoTerminais = n.elementAt(j).toString();
                if (elementoDireito.equals(NaoTerminais)) {
                    unitaria = elementoDireito;

                }
            }
        }

        String producoesUnitaria = "";
        boolean flag = false;
        //pega regras do producao que tem unitaria
        for (int i = 0; i < p.size(); i++) {
            ladoEsquerdo = getLadoEsquerdo(p.elementAt(i).toString());
            if (ladoEsquerdo.equals(unitaria)) {
                elementoDireito = getLadoDireito(p.elementAt(i).toString());
                if(ladoEsquerdo.equals(unitaria))
                {
                    if (flag) {
                        producoesUnitaria += "|" + elementoDireito;

                    } else {
                        producoesUnitaria = elementoDireito;
                        flag = true;
                    }
                }
            }
        }
        
        //Substitui unitaria com a producao pegada no for anterior
       for (int i = 0; i < Producoes.size(); i++) {

            elementoDireito = getLadoDireito(Producoes.elementAt(i).toString());

            if(elementoDireito.equals(unitaria))
            {
                ladoEsquerdo = getLadoEsquerdo(Producoes.elementAt(i).toString());
                
                novaProducao.add(ladoEsquerdo + "=" +producoesUnitaria);
            }else
            {
                if(!elementoDireito.equals(unitaria))
                {
                  novaProducao.add(p.elementAt(i).toString());
                }
            }
        }
        
        
       novaProducao = organizaProducoes(novaProducao);
        
        return novaProducao;
        
    }

    public String getLadoDireito(String Producao) {
        String a[] = Producao.split("=");

        return a[1];
    }

    public String getLadoEsquerdo(String Producao) {
        String a[] = Producao.split("=");

        return a[0];
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
    
    public Vector separaVirgula(String producaoCompleta)
    {
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

}
