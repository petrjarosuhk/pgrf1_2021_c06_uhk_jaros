package raster;

import model.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Trida pro kresleni na platno pomocí šipek na numerické klávesnici
 * --------------------------------------------------------------------------------------------------------
 * V konstruktoru CANVASKEYROWS, lze odkomentovávat a zakomentovávat, jaku algoritmus se použije jestli DDA nebo Graphics JAVA
 * --------------------------------------------------------------------------------------------------------------------------
 *Ovládání
 * Pomocí klávesi C se vymaže plátno
 * Prvně někde levou klávesou myší kliknout, kde se má začít vykreslovat. Potom použít následující.
 * klávesa 8 na numerické klávesnici - vytvoří pixel nahoru
 * klávesa 2 na numerické klávesnici - vytvoří pixel dolů
 * klávesa 4 na numerické klávesnici - vytvoří pixel doleva
 * klávesa 6 na numerické klávesnici - vytvoří pixel doprava
 * kreslení probíhá pomocí DDA algoritmu
 *
 *  vždy odkomentovat, jakou čáru chceme vykreslit a pomocí čeho v kontruktoru CANVASKEYROWS
 *  * buď lineRasterizer= new LineRasterizerFill(raster) což je DDA algoritmus
 *  * buď lineRasterizer = new LineRasterizerBI(raster) což je Graphics JAVA
 * @author PGRF FIM UHK
 * @version 2020
 */
public class CanvasKeyRows {

    private JPanel panel;
    //private BufferedImage img;
    private Raster raster;
	private LineRasterizer lineRasterizer;
    private int x, y;
    private List<Line> lines = new ArrayList<>();


    public CanvasKeyRows(int width, int height) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        raster = new RasterBufferedImage(width, height);

         //lineRasterizer = new LineRasterizerBI(raster); // kreslení pomocí Graphics JAVA
         lineRasterizer = new LineRasterizerFill(raster); //kreslení pomocí DDA algoritmu



        panel = new JPanel() {


            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));

        panel.setFocusable(true);
        panel.requestFocus(true);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

panel.addMouseListener(new MouseAdapter() {
    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        x = e.getX();
        y = e.getY();
        raster.setPixel(x,y, 0xFFFF00);
        panel.repaint();
    }
});

        panel.addKeyListener(new KeyListener() {




            @Override
            public void keyTyped(KeyEvent e) {


                char znak = e.getKeyChar();
                if(znak == '2'){
                    y++;
                    raster.setPixel(x,y, 0xFFFF00);
                    panel.repaint();
                }

                if(znak == '8'){
                    y--;
                    raster.setPixel(x,y, 0xFFFF00);
                    panel.repaint();
                }
                if(znak == '6'){
                    x++;
                    raster.setPixel(x,y, 0xFFFF00);
                    panel.repaint();
                }
                if(znak == '4'){
                    x--;
                    raster.setPixel(x,y, 0xFFFF00);
                    panel.repaint();
                }

                if(znak == 'c') {

                    lines.clear();
                    redraw();
                    clear();
                }

            }



            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

        });
    }

    public void clear() {
        raster.clear();
    }

    void redraw(){
        for (Line line: lines) {
            lineRasterizer.rasterize(line);
        }
        panel.repaint();
    }

    public void present(Graphics graphics) {
        BufferedImage img = ((RasterBufferedImage) raster).getImg();
        graphics.drawImage(img, 0, 0, null);
    }

    public void start() {
        clear();
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CanvasKeyRows(800, 600).start());
    }

}
