import model.Point;
import model.Polygon;
import raster.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Trida pro kresleni na platno pomocí DDA algoritmu nebo pomocí Graphics od Javy, zadávání pomocí bodů, pomocí polyhogonu nebo čarchovací čáry
 * ---------------------------------------------------------------------------------------------------------
 * Je zde třída Canvas, pro vykreslování dané úsečky při různému výberu algoritmu za pomocí zadávání bodů. - každý se spouští vzlášť
 * Je zde třída CanvasMouse, pro interaktivní vykreslování dané usečky pomocí klikání myší. - každý se spouští vzlášť
 * --------------------------------------------------------------------------------------------------------------------
 *Něco navíc
 * Je zde třída CanvasKeyRows, který kreslý pixeli pomocí držení šipek na numerické klávesnici. - každý se spouští vzlášť
 * --------------------------------------------------------------------------------------------------------------------
 * Detalnější popis je v dané třídě.
 * --------------------------------------------------------------------------------------------------------
 * Poté v metodě draw, lze změnit, jak má být délka čáry velká a popřípadě změnit hodnoty poygonu.
 * ------------------------------------
 * Polygon
 * Popřípadě pomocí polygonu, lze zadat body a vytvořit, tak obrazec.
 * ---------------------------------------------------------------------------------------------------------------------------------
 * DDA algoritmus výhody a nevýhody
 * Byl použit DDA algoritmus: Výhody-je jednoduchý na implementování; vyhýbá se vícero operacím, které mají dopad na rychlost,
 *                            Nevýhody- jelikož závisí na orientaci nemá zas, tak dobrou přesnost koncového bodu; jelikož nemá zas takovou přesnost co se týká floating
 *                                          point, tak vytváří docela hodně errorů
 * --------------------------------------------------------------------------------------------------------------------------------------------------------------
 *
 *Ovládání
 * vždy odkomentovat, jakou čáru chceme vykreslit a pomocí čeho v kontruktoru CANVAS
 * buď lineRasterizer= new LineRasterizerFill(raster) což je DDA algoritmus
 * buď lineRasterizer = new LineRasterizerBI(raster) což je Graphics JAVA
 * buď lineRasterizer = new LineRasterizerDashed(raster) což je pro kreslení čarchované čáry
 * ---------------------------------------------------------------------------------------------------------------------------------------------------
 * @author PGRF FIM UHK
 * @version 2020
 */

public class Canvas {

    private JFrame frame;
    private JPanel panel;
    //private BufferedImage img;
    private Raster raster;
    private LineRasterizer lineRasterizer;
    public Canvas(int width, int height) {
        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        raster = new RasterBufferedImage(width, height);

        /*Zde postupně zakomentovávat a odkomentovávat jaký algoritmy popřípadě čára se použijí, vždy se použije najednou jenom jeden!*/

        lineRasterizer= new LineRasterizerFill(raster);/*Odkomentovat pro použití, potom zase zakomentovat. DDA algoritmus pro všechny kvadranty*/
        //lineRasterizer = new LineRasterizerDashed(raster); /*Odkomentovat pro použití, potom zase zakomentovat. Čarchovaná čára pomocí Graphics JAVA*/
        //lineRasterizer = new LineRasterizerBI(raster);/*Odkomentovat pro použití, potom zase zakomentovat. Algoritmus poomocí Graphics JAVA*/




        //img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void clear() {
        BufferedImage img = ((RasterBufferedImage)raster).getImg();
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0x2f2f2f));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    public void present(Graphics graphics) {
        BufferedImage img = ((RasterBufferedImage)raster).getImg();
        graphics.drawImage(img, 0, 0, null);
    }

    public void draw() {
        clear();

        /*Lze je možné změnit velikost úsečky.*/
        lineRasterizer.rasterize(500,200,300,100);


        /*Pomocí tohoto se pomocí polygonu vezmou body a vytvoří se úsečky. Odkomentovat pro použití.*/
        Polygon  polygon = new Polygon();
        polygon.addPoint(new Point(50,100));
        polygon.addPoint(new Point(150,250));
        polygon.addPoint(new Point(100,10));
        lineRasterizer.rasterize(polygon);

    }


    public void start() {
        draw();
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
    }

}