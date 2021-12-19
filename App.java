import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class App implements KeyListener{
    JFrame f;
    Surface s;
    

    public App(){
        s = new Surface();
        f = new JFrame();
        f.setSize(1000,1000);
        f.setLayout(new GridLayout(1,1));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(s);
        f.addKeyListener(this);
        f.setVisible(true);
    }
    private static int hex2int(char h){
        switch(h){
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'a':
                return 10;
            case 'b':
                return 11;
            case 'c':
                return 12;
            case 'd':
                return 13;
            case 'e':
                return 14;
            case 'f':
                return 15;
        }
        return -1;
    }
    private static int hex2int(char[] hex){
        int ret = 0;
        for(int i = 0; i<hex.length; i++){
            ret+=((int)Math.pow(16,hex.length-i-1)) * hex2int(hex[i]);
        }
        return ret;
    }
    public static void main(String[] args){
        //if no args we just do a new app
        //otherwise args should be: 
        /*
        -f FILENAME [optional, default out.png]
        -c HEXCODE, Color - default red (rgb 255 0 0)
        -o Order, required
        -s scale up by, default 10
        */
        if(args.length==0){
            new App();
        }else{
            File out = null;
            Color c = Color.RED;
            int hilbert = -1;
            int scale = 10;
            boolean skipthis = false;
            for(int i = 0; i<args.length; i++){
                if(skipthis){
                    skipthis=false;
                    continue;
                }
                if(args[i].equals("-f")){
                    skipthis = true;
                    out = new File(args[i+1]);
                }else if(args[i].equals("-c")){
                    skipthis = true;
                    char[] hx = args[i+1].toLowerCase().toCharArray();
                    int r = hex2int(new char[]{hx[0],hx[1]});
                    int g = hex2int(new char[]{hx[2],hx[3]});
                    int b = hex2int(new char[]{hx[4],hx[5]});
                    System.out.println(r+","+g+","+b);
                    c =  new Color(r, g, b);
                }else if(args[i].equals("-o")){
                    skipthis = true;
                    hilbert = (int) Math.pow(Integer.parseInt(args[i+1]),2);
                }
                else if(args[i].equals("-s")){
                    skipthis = true;
                    scale = Integer.parseInt(args[i+1]);
                }
            }
            int img_dim = (hilbert*2)*scale;
            BufferedImage img = new BufferedImage(img_dim, img_dim, BufferedImage.TYPE_INT_RGB);
            HilbertFactory hf = new HilbertFactory();
            Graphics g = img.getGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, img_dim, img_dim);
            hf.draw(
                    img.getGraphics(),
                    hilbert*scale, 
                    hilbert,
                    c
                );
            try {
                ImageIO.write(img, "png", out);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar()==' '){
            s.hilbert = s.hilbert * 2;
            f.repaint();
        }
        
    }
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}