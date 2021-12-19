import javax.swing.JPanel;

import java.awt.*;

public class Surface extends JPanel{
    int hilbert;
    HilbertFactory hf;
    public Surface(){
        super();
        hilbert = 2;
        hf = new HilbertFactory();
        //addMouseListener(this);
        
    }
    @Override
    public void paintComponent(Graphics g){
        hf.draw(g,getWidth(),hilbert, Color.RED);
    }
}
