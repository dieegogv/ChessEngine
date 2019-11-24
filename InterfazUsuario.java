import java.awt.*;
import javax.swing.*;
public class InterfazUsuario extends JPanel implements MouseListener, MouseMotionListener{
     
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.yellow);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        g.setColor(Color.BLUE);
        g.fillRect(20, 20, 20, 20);
        g.setColor(new Color(190,81,215));
        g.fillRect(40, 20, 80, 50);
        Image piezasImagen;
        piezasImagen=new ImageIcon("piezas.png").getImage();
        g.drawImage(piezasImagen, x, 0, x+100, 100, x, 0, x+100, 100, this);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x=e.getX();
        y=e.getY();
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        x=e.getX();
        y=e.getY();
        repaint();
    }
    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}