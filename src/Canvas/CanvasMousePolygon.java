package Canvas;

import fill.ScanLine;
import fill.SeedFill;
import model.Line;
import model.Point;
import model.Polygon;
import raster.LineRasterizer;
import raster.LineRasterizerFill;
import raster.Raster;
import raster.RasterBufferedImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CanvasMousePolygon {

    private JPanel panel;
    //private BufferedImage img;
    private Raster raster;
	private LineRasterizer lineRasterizer;
    private int x, y, x1, y1;
    private List<Line> lines = new ArrayList<>();


    public CanvasMousePolygon(int width, int height) {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        raster = new RasterBufferedImage(width, height);

        lineRasterizer= new LineRasterizerFill(raster);
        
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

                if(e.getButton() == MouseEvent.BUTTON2){
                    x1 = e.getX();
                    y1 = e.getY();
                }

                    raster.setPixel(e.getX(), e.getY(), 0xffff00);
                    x = e.getX();
                    y = e.getY();
                    polygon.addPoint(new Point(x, y));

                    panel.repaint();

            }

			@Override
			public void mouseReleased(MouseEvent e) {
                clear();
                polygon.addPoint(new Point(e.getX(),e.getY()));
                lineRasterizer.rasterize(polygon);
                panel.repaint();

                lines.add(new Line(x,y,e.getX(),e.getY()));
                System.out.println("Polozky: "+lines.size());


			}
		});

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {


            }
            @Override
            public void mouseDragged(MouseEvent e) {

                clear();
                lineRasterizer.rasterize(x,y, e.getX(), e.getY());
                lineRasterizer.rasterize(polygon);
                panel.repaint();

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

                if(znak == 'v'){

                    SeedFill seedFill = new SeedFill(raster);
                    seedFill.fill(x1,y1, 0xFF0000);
                    panel.repaint();

                }


                if(znak == 'b'){

                    SeedFill seedFill = new SeedFill(raster);
                    seedFill.fillBoundery(x1,y1,  0XFF0000);
                    panel.repaint();


                }


                if(znak == 's'){

                    ScanLine scanLine = new ScanLine(raster);
                    scanLine.setPolygon(polygon);
                    scanLine.fill(0);
                    lineRasterizer.rasterize(polygon);
                    panel.repaint();


                }

                if(znak == 'k'){
                    ScanLine scanLine = new ScanLine(raster);
                    scanLine.setPolygon(polygon);
                    scanLine.fill(1);
                    lineRasterizer.rasterize(polygon);
                    panel.repaint();
                }

                if(znak == 'p'){
                    ScanLine scanLine = new ScanLine(raster);
                    scanLine.setPolygon(polygon);
                    scanLine.fill(2);
                    lineRasterizer.rasterize(polygon);
                    panel.repaint();
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
        SwingUtilities.invokeLater(() -> new CanvasMousePolygon(800, 600).start());
    }

}
