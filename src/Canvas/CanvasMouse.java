package Canvas;

import model.Line;
import model.Point;
import model.Polygon;
import raster.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Trida pro kresleni na plátno pomocí DDA algoritmu vlastní implementace nebo pomocí Graphics od Javy Táhnutí myší
 *Ovládání
 * Na plátně kliknout levým tlačítkem myší a táhnout a potom pustit.
 * Pomocí klávesi C se vymaže plátno
 * vždy odkomentovat, jakou čáru chceme vykreslit a pomocí čeho v kontruktoru CANVASMOUSE
 * buď lineRasterizer= new LineRasterizerFill(raster) což je DDA algoritmus vlastní implementace
 * buď lineRasterizer = new LineRasterizerBI(raster) což je Graphics JAVA
 * buď lineRasterizer = new LineRasterizerDashed(raster) což je pro kreslení čarchované čáry
 * @author PGRF FIM UHK
 * @version 2020
 */
public class CanvasMouse {

    private JPanel panel;
    //private BufferedImage img;
    private Raster raster;
	private LineRasterizer lineRasterizer;
    private int x, y;
    private List<Line> lines = new ArrayList<>();


    public CanvasMouse(int width, int height) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        raster = new RasterBufferedImage(width, height);

        lineRasterizer= new LineRasterizerFill(raster);/*Odkomentovat pro použití, potom zase zakomentovat. DDA algoritmus*/
        //lineRasterizer = new LineRasterizerDashed(raster); /*Odkomentovat pro použití, potom zase zakomentovat. Čarchovaná čáara Graphics */
        //lineRasterizer = new LineRasterizerBI(raster);/*Odkomentovat pro použití, potom zase zakomentovat. Graphics*/

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

        Polygon polygon = new Polygon();
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                    raster.setPixel(e.getX(), e.getY(), 0xffff00);
                    x = e.getX();
                    y = e.getY();

                    panel.repaint();
            }

			@Override
			public void mouseReleased(MouseEvent e) {
				lineRasterizer.rasterize(x,y,e.getX(),e.getY());

                panel.repaint();

                lines.add(new Line(x,y,e.getX(),e.getY()));
                System.out.println("Polozky: "+lines.size());
			}
		});

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                clear();
                lineRasterizer.rasterize(x,y,e.getX(),e.getY());
                panel.repaint();
                redraw();

            }
        });
        panel.addKeyListener(new KeyListener() {


            @Override
            public void keyTyped(KeyEvent e) {


                char znak = e.getKeyChar();

                if(znak == 'c') {

                    System.out.println(znak);
                    polygon.getPoints().clear();
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

    public List<Point> clear() {
        raster.clear();
        return null;
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
        SwingUtilities.invokeLater(() -> new CanvasMouse(800, 600).start());
    }

}
