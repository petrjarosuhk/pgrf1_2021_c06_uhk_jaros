
import model.Line;
import raster.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * trida pro kresleni na platno: zobrazeni pixelu, ovladani mysi
 *
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
		lineRasterizer = new LineRasterizerBI(raster);



        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    raster.setPixel(e.getX(), e.getY(), 0xffff00);
                    x = e.getX();
                    y = e.getY();
                }
                if (e.getButton() == MouseEvent.BUTTON2)
                    raster.setPixel(e.getX(), e.getY(), 0xffff00);
                if (e.getButton() == MouseEvent.BUTTON3)
                    raster.setPixel(e.getX(), e.getY(), 0xffff00);
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

                redraw();
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
        SwingUtilities.invokeLater(() -> new CanvasMouse(800, 600).start());
    }

}
