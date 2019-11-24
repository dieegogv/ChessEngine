import javax.swing.*;
public class CnnChess {
    public static void main(String[] args) {
        JFrame f=new JFrame("Diego");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        InterfazUsuario iUsuario=new InterfazUsuario();
        f.add(iUsuario);
        f.setSize(500, 500);
        f.setVisible(true);
    }
}