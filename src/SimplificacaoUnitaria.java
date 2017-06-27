
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
        
        for (int i = 0; i < p.size(); i++) {
            ladoEsquerdo = getLadoEsquerdo(p.elementAt(i).toString());
            if (ladoEsquerdo.equals(unitaria)) {
                elementoDireito = getLadoDireito(p.elementAt(i).toString());
                if (flag) {
                    producoesUnitaria += "|" + elementoDireito;

                } else {
                    producoesUnitaria = elementoDireito;
                    flag = true;
                }
            }
        }
        
        String producaoSemUnitario;
            
        for (int i = 0; i < p.size(); i++) {

            elementoDireito = getLadoDireito(p.elementAt(i).toString());

            if(elementoDireito.equals(unitaria))
            {
                producaoSemUnitario = p.elementAt(i).toString().replace(unitaria,producoesUnitaria);
                novaProducao.insertElementAt(producaoSemUnitario, i);
            }else
            {
                novaProducao.add(p.elementAt(i).toString());
            }
            

        }
        
       System.out.println("O QUE TA SAINDO AQUI DIMITRY!!!"); 
       for (int i = 0; i < novaProducao.size(); i++) {
            
                System.out.println(novaProducao.elementAt(i));
            
        }

       novaProducao = organizaProducoes(novaProducao);
        
       System.out.println("\n\n\nE O QUE TA SAINDO AQUI DIMITRY!!!"); 
       for (int i = 0; i < novaProducao.size(); i++) {
            
                System.out.println(novaProducao.elementAt(i));
            
        }

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

    public Vector separaPipe(String texto) {
        Vector v = new Vector();
        String a[] = texto.split("=");
        String letraInicial = a[0];
        String producoesComPipe = a[1];

        String b[] = producoesComPipe.split("\\|");
        String producao = "";

        for (int i = 0; i < b.length; i++) {
            producao = b[i];
            v.add(letraInicial + "=" + producao);
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
        p = separaPipe(producaoComleta);

        return p;
    }

}
