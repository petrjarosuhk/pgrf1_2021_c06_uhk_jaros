package raster;

import java.awt.image.BufferedImage;

public class LineRasterizerBI extends LineRasterizer{
    public LineRasterizerBI(Raster raster) {
        super(raster);
    }

    public void rasterize(int x1, int y1, int x2, int y2) {
        BufferedImage img = ((RasterBufferedImage)raster).getImg();
        img.getGraphics().drawLine(x1,y1,x2,y2);
    }
}
