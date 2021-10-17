package raster;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LineRasterizerBI extends LineRasterizer{
    public LineRasterizerBI(Raster raster) {
        super(raster);
    }

    public void rasterize(int x1, int y1, int x2, int y2, int color) {
      BufferedImage img = ((RasterBufferedImage)raster).getImg();
        Graphics2D g2 = img.createGraphics();
        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2.setStroke(dashed);
       /* Graphics gr = img.getGraphics();
        gr.setColor(new Color(color));*/
        g2.drawLine(x1,y1,x2,y2);


    }
}
