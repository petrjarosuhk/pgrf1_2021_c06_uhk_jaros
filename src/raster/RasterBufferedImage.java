package raster;

import java.awt.image.BufferedImage;

public class RasterBufferedImage implements Raster{
    private BufferedImage img;

    public RasterBufferedImage(int width, int height){
        img = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
    }

    public BufferedImage getImg(){
        return img;
    }

    @Override
    public void setPixel(int x, int y, int color) {
        img.setRGB(x, y, color);
    }

    @Override
    public int getWidth() {
        return img.getWidth();
    }

    @Override
    public int getHeight() {
        return img.getHeight();
    }
}
