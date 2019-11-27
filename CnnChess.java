import javax.swing.*;
public class CnnChess {
     static String tablero[][]={
        {"t","c","a","d","r","a","c","t"}, 
        {"p","p","p","p","p","p","p","p"},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {"P","P","P","P","P","P","P","P"},
        {"T","C","A","D","R","A","C","T"}};
    
    static int posicionReyBlancas, posicionReyNegras;

        
    public static void main(String[] args) {
        while (!"R".equals(tablero[posicionReyBlancas/8][posicionReyBlancas%8])) {posicionReyBlancas++;}
        while (!"r".equals(tablero[posicionReyNegras/8][posicionReyNegras%8])) {posicionReyNegras++;}
        /*
        /*
         * pieza=BLANCA/negra
         * peon = P/p
         * caballo = C/c 
         * alfil = A/a
         * torre = T/t
         * Dama = D/d
         * Rey = R/r 
         * 
         * My strategy is to create an alpha-beta tree diagram wich returns
         * the best outcome
         * 
         * (1234b represents row1,column2 moves to row3, column4 which captured
         * b (a space represents no capture))
         */
        /*JFrame f=new JFrame("Diego");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        InterfazUsuario iUsuario=new InterfazUsuario();
        f.add(iUsuario);
        f.setSize(500, 500);
        f.setVisible(true);*/
        System.out.println(movimientosPosibles());

    }

    public static String movimientosPosibles() {
        String movimientoLista="";
        for (int i=0; i<64; i++) {
            switch (tablero[i/8][i%8]) {
                case "P": movimientoLista+=movimientoP(i);
                    break;
                case "T": movimientoLista+=movimientoT(i);
                    break;
                case "C": movimientoLista+=movimientoC(i);
                    break;
                case "A": movimientoLista+=movimientoA(i);
                    break;
                case "D": movimientoLista+=movimientoD(i);
                    break;
                case "R": movimientoLista+=movimientoR(i);
                    break;
            }
        }
        return movimientoLista;//x1,y1,x2,yx, captura
    }

    public static String movimientoP(int i) {
        String movimientoLista="", posicionPeonAnterior;
        int filas=i/8, columnas=i%8;
        for (int j=-1; j<=1; j+=2) {
            try {//captura
                if (Character.isLowerCase(tablero[filas-1][columnas+j].charAt(0)) && i>=16) {
                    posicionPeonAnterior=tablero[filas-1][columnas+j];
                    tablero[filas][columnas]=" ";
                    tablero[filas-1][columnas+j]="P";
                    if (reySeguro()) {
                        movimientoLista=movimientoLista+filas+columnas+(filas-1)+(columnas+j)+posicionPeonAnterior;
                    }
                    tablero[filas][columnas]="P";
                    tablero[filas-1][columnas+j]=posicionPeonAnterior;
                }
            } catch (Exception e) {}
            try {//coronación con captura
                if (Character.isLowerCase(tablero[filas-1][columnas+j].charAt(0)) && i<16) {
                    String[] modeloPiezas={"D","T","A","C"};
                    for (int k=0; k<4; k++) {
                        posicionPeonAnterior=tablero[filas-1][columnas+j];
                        tablero[filas][columnas]=" ";
                        tablero[filas-1][columnas+j]=modeloPiezas[k];
                        if (reySeguro()) {
                            movimientoLista=movimientoLista+columnas+(columnas+j)+posicionPeonAnterior+modeloPiezas[k]+"P";
                        }
                        tablero[filas][columnas]="P";
                        tablero[filas-1][columnas+j]=posicionPeonAnterior;
                    }
                }
            } catch (Exception e) {}
        }
        try {//avanzar
            if (" ".equals(tablero[filas-1][columnas]) && i>=16) {
                posicionPeonAnterior=tablero[filas-1][columnas];
                tablero[filas][columnas]=" ";
                tablero[filas-1][columnas]="P";
                if (reySeguro()) {
                    movimientoLista=movimientoLista+filas+columnas+(filas-1)+columnas+posicionPeonAnterior;
                }
                tablero[filas][columnas]="P";
                tablero[filas-1][columnas]=posicionPeonAnterior;
            }
        } catch (Exception e) {}
        try {//coronar sin captura
            if (" ".equals(tablero[filas-1][columnas]) && i<16) {
                String[] modeloPiezas={"D","T","A","C"};
                for (int k=0; k<4; k++) {
                    posicionPeonAnterior=tablero[filas-1][columnas];
                    tablero[filas][columnas]=" ";
                    tablero[filas-1][columnas]=modeloPiezas[k];
                    if (reySeguro()) {
                        movimientoLista=movimientoLista+columnas+columnas+posicionPeonAnterior+modeloPiezas[k]+"P";
                    }
                    tablero[filas][columnas]="P";
                    tablero[filas-1][columnas]=posicionPeonAnterior;
                }
            }
        } catch (Exception e) {}
        try {//avanzar dos
            if (" ".equals(tablero[filas-1][columnas]) && " ".equals(tablero[filas-2][columnas]) && i>=48) {
                posicionPeonAnterior=tablero[filas-2][columnas];
                tablero[filas][columnas]=" ";
                tablero[filas-2][columnas]="P";
                if (reySeguro()) {
                    movimientoLista=movimientoLista+filas+columnas+(filas-2)+columnas+posicionPeonAnterior;
                }
                tablero[filas][columnas]="P";
                tablero[filas-2][columnas]=posicionPeonAnterior;
            }
        } catch (Exception e) {}
        return movimientoLista;
    }
    public static String movimientoT(int i) {
        String movimientoLista = "", posicionTorreAnterior;
        int filas = i/8, columnas = i%8;
        int modelo = 1;
        for (int j=-1; j<=1; j+=2) {
            try {
                while(" ".equals(tablero[filas][columnas + modelo*j]))
                {
                    posicionTorreAnterior = tablero[filas][columnas + modelo*j];
                    tablero[filas][columnas] = " ";
                    tablero[filas][columnas + modelo*j] = "T";
                    if (reySeguro()) {
                        movimientoLista = movimientoLista + filas + columnas + filas + (columnas + modelo*j) + posicionTorreAnterior;
                    }
                    tablero[filas][columnas] = "T";
                    tablero[filas][columnas + modelo*j] = posicionTorreAnterior;
                    modelo++;
                }
                if (Character.isLowerCase(tablero[filas][columnas + modelo*j].charAt(0))) {
                    posicionTorreAnterior = tablero[filas][columnas + modelo*j];
                    tablero[filas][columnas] = " ";
                    tablero[filas][columnas + modelo*j] = "T";
                    if (reySeguro()) {
                        movimientoLista = movimientoLista + filas + columnas + filas + (columnas + modelo*j) + posicionTorreAnterior;
                    }
                    tablero[filas][columnas] = "T";
                    tablero[filas][columnas + modelo*j] = posicionTorreAnterior;
                }
            } catch (Exception e) {}
            modelo=1;
            try {
                while(" ".equals(tablero[filas+modelo*j][columnas]))
                {
                    posicionTorreAnterior=tablero[filas+modelo*j][columnas];
                    tablero[filas][columnas]=" ";
                    tablero[filas+modelo*j][columnas]="R";
                    if (reySeguro()) {
                        movimientoLista=movimientoLista+filas+columnas+filas+(columnas+modelo*j)+posicionTorreAnterior;
                    }
                    tablero[filas][columnas]="R";
                    tablero[filas+modelo*j][columnas]=posicionTorreAnterior;
                    modelo++;
                }
                if (Character.isLowerCase(tablero[filas+modelo*j][columnas].charAt(0))) {
                    posicionTorreAnterior=tablero[filas+modelo*j][columnas];
                    tablero[filas][columnas]=" ";
                    tablero[filas+modelo*j][columnas]="R";
                    if (reySeguro()) {
                        movimientoLista=movimientoLista+filas+columnas+filas+(columnas+modelo*j)+posicionTorreAnterior;
                    }
                    tablero[filas][columnas]="R";
                    tablero[filas+modelo*j][columnas]=posicionTorreAnterior;
                }
            } catch (Exception e) {}
        }
        return movimientoLista;
    }
    public static String movimientoC(int i) {
        String movimientoLista="", posicionCaballoAnterior;
        int filas=i/8, columnas=i%8;
        for (int j=-1; j<=1; j+=2) {
            for (int columnas=-1; columnas<=1; columnas+=2) {
                try {
                    if (Character.isLowerCase(tablero[filas+j][columnas+columnas*2].charAt(0)) || " ".equals(tablero[filas+j][columnas+columnas*2])) {
                        posicionCaballoAnterior=tablero[filas+j][columnas+columnas*2];
                        tablero[filas][columnas]=" ";
                        if (reySeguro()) {
                            movimientoLista=movimientoLista+filas+columnas+(filas+j)+(columnas+columnas*2)+posicionCaballoAnterior;
                        }
                        tablero[filas][columnas]="C";
                        tablero[filas+j][columnas+columnas*2]=posicionCaballoAnterior;
                    }
                } catch (Exception e) {}
                try {
                    if (Character.isLowerCase(tablero[filas+j*2][columnas+columnas].charAt(0)) || " ".equals(tablero[filas+j*2][columnas+columnas])) {
                        posicionCaballoAnterior=tablero[filas+j*2][columnas+columnas];
                        tablero[filas][columnas]=" ";
                        if (reySeguro()) {
                            movimientoLista=movimientoLista+filas+columnas+(filas+j*2)+(columnas+columnas)+posicionCaballoAnterior;
                        }
                        tablero[filas][columnas]="C";
                        tablero[filas+j*2][columnas+columnas]=posicionCaballoAnterior;
                    }
                } catch (Exception e) {}
            }
        }
        return movimientoLista;
    }
    public static String movimientoA(int i) {
        String movimientoLista = "", posicionAlfilAnterior;
        int filas=i/8, columnas=i%8;
        int modelo = 1;
        for (int j=-1; j<=1; j+=2) {
            for (int columnas=-1; columnas<=1; columnas+=2) {
                try {
                    while(" ".equals(tablero[filas+modelo * j][columnas+modelo * columnas]))
                    {
                        posicionAlfilAnterior=tablero[filas+modelo * j][columnas+modelo * columnas];
                        tablero[filas][columnas]=" ";
                        tablero[filas+modelo * j][columnas+modelo * columnas]="A";
                        if (reySeguro()) {
                            movimientoLista=movimientoLista+filas+columnas+(filas+modelo * j)+(columnas+modelo * columnas)+posicionAlfilAnterior;
                        }
                        tablero[filas][columnas]="A";
                        tablero[filas+modelo * j][columnas+modelo * columnas]=posicionAlfilAnterior;
                        modelo + +;
                    }
                    if (Character.isLowerCase(tablero[filas+modelo * j][columnas+modelo * columnas].charAt(0))) {
                        posicionAlfilAnterior=tablero[filas+modelo * j][columnas+modelo * columnas];
                        tablero[filas][columnas]=" ";
                        tablero[filas+modelo * j][columnas+modelo * columnas]="A";
                        if (reySeguro()) {
                            movimientoLista=movimientoLista+filas+columnas+(filas+modelo * j)+(columnas+modelo * columnas)+posicionAlfilAnterior;
                        }
                        tablero[filas][columnas]="A";
                        tablero[filas+modelo * j][columnas+modelo * columnas]=posicionAlfilAnterior;
                    }
                } catch (Exception e) {}
                modelo = 1;
            }
        };
        return movimientoLista;
    }
    public static String movimientoD(int i) {
        String movimientoLista="", posicionDamaAnterior;
        int filas = i/8, columnas = i%8;
        int modelo = 1;
        for (int j=-1; j<=1; j++) {
            for (int columnas=-1; columnas<=1; columnas++) {
                try {
                    while(" ".equals(tablero[filas + modelo*j][columnas + modelo*columnas]))
                    {
                        posicionDamaAnterior = tablero[filas + modelo*j][columnas + modelo*columnas];
                        tablero[filas][columnas] = " ";
                        tablero[filas + modelo*j][columnas + modelo*columnas] = "Q";
                        if (reySeguro()) {
                            movimientoLista = movimientoLista + filas + columnas + (filas + modelo*j) + (columnas + modelo*columnas) + posicionDamaAnterior;
                        }
                        tablero[filas][columnas] = "Q";
                        tablero[filas + modelo*j][columnas + modelo*columnas] = posicionDamaAnterior;
                        modelo++;
                    }
                    if (Character.isLowerCase(tablero[filas + modelo*j][columnas + modelo*columnas].charAt(0))) {
                        posicionDamaAnterior = tablero[filas + modelo*j][columnas + modelo*columnas];
                        tablero[filas][columnas] = " ";
                        tablero[filas + modelo*j][columnas + modelo*columnas] = "Q";
                        if (reySeguro()) {
                            movimientoLista = movimientoLista + filas + columnas + (filas +modelo*j) + (columnas + modelo*columnas) + posicionDamaAnterior;
                        }
                        tablero[filas][columnas] = "Q";
                        tablero[filas + modelo*j][columnas + modelo*columnas] = posicionDamaAnterior;
                    }
                } catch (Exception e) {}
                modelo = 1;
            }
        }
        return movimientoLista;
    }
    public static String movimientoR(int i) {
        String movimientomLista="", posicionReyAnterior;
        int filas =i/8, colunmas=i%8;
        for (int j=0; j<9; j++) {
            if (j!=4) {
                try {
                    if (Character.isLowerCase(tablero[filas -1+j/3][colunmas-1+j%3].charAt(0)) || " ".equals(tablero[filas -1+j/3][colunmas-1+j%3])) {
                        posicionReyAnterior = tablero[filas -1+j/3][columnas -1+j%3];
                        tablero[filas ][colunmas] = " ";
                        tablero[filas -1+j/3][colunmas-1+j%3] = "R";
                        int reyModelo = posicionReyBlancas;
                        posicionReyBlancas = i+(j/3)*8+j%3-9;
                        if (reySeguro()) {
                            movimientoLista = movimientoLista + filas + columnas + (filas -1+j/3)+(columnas -1+j%3)+posicionReyAnterior;
                        }
                        tablero[filas ][colunmas] = "R";
                        tablero[filas -1+j/3][colunmas-1+j%3] = posicionReyAnterior;
                        posicionReyBlancas = reyModelo;
                    }
                } catch (Exception e) {}
            }
        }
        return movimientoLista;
    }

    public static boolean reySeguro() {
        //alfil/dama
        int modelo=1;
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {
                    while(" ".equals(tablero[posicionReyBlancas/8+modelo*i][posicionReyBlancas%8+modelo*j])) {modelo++;}
                    if ("a".equals(tablero[posicionReyBlancas/8+modelo*i][posicionReyBlancas%8+modelo*j]) ||
                            "d".equals(tablero[posicionReyBlancas/8+modelo*i][posicionReyBlancas%8+modelo*j])) {
                        return false;
                    }
                } catch (Exception e) {}
                modelo=1;
            }
        }
        //torre/dama
        for (int i=-1; i<=1; i+=2) {
            try {
                while(" ".equals(tablero[posicionReyBlancas/8][posicionReyBlancas%8+modelo*i])) {modelo++;}
                if ("t".equals(tablero[posicionReyBlancas/8][posicionReyBlancas%8+modelo*i]) ||
                        "d".equals(tablero[posicionReyBlancas/8][posicionReyBlancas%8+modelo*i])) {
                    return false;
                }
            } catch (Exception e) {}
            modelo=1;
            try {
                while(" ".equals(tablero[posicionReyBlancas/8+modelo*i][posicionReyBlancas%8])) {modelo++;}
                if ("t".equals(tablero[posicionReyBlancas/8+modelo*i][posicionReyBlancas%8]) ||
                        "d".equals(tablero[posicionReyBlancas/8+modelo*i][posicionReyBlancas%8])) {
                    return false;
                }
            } catch (Exception e) {}
            modelo=1;
        }
        //caballo
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {
                    if ("c".equals(tablero[posicionReyBlancas/8+i][posicionReyBlancas%8+j*2])) {
                        return false;
                    }
                } catch (Exception e) {}
                try {
                    if ("c".equals(tablero[posicionReyBlancas/8+i*2][posicionReyBlancas%8+j])) {
                        return false;
                    }
                } catch (Exception e) {}
            }
        }
        //peón
        if (posicionReyBlancas>=16) {
            try {
                if ("p".equals(tablero[posicionReyBlancas/80-1][posicionReyBlancas%8-1])) {
                    return false;
                }
            } catch (Exception e) {}
            try {
                if ("p".equals(tablero[posicionReyBlancas/80-1][posicionReyBlancas%8+1])) {
                    return false;
                }
            } catch (Exception e) {}
            //rey
            for (int i=-1; i<=1; i++) {
                for (int j=-1; j<=1; j++) {
                    if (i!=0 || j!=0) {
                        try {
                            if ("r".equals(tablero[posicionReyBlancas/8+i][posicionReyBlancas%8+j])) {
                                return false;
                            }
                        } catch (Exception e) {}
                    }
                }
            }
        }
        return true;
    }
    }
}