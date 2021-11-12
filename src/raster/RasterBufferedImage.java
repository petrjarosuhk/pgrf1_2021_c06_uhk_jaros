package raster;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RasterBufferedImage<x> implements Raster{
    private BufferedImage img;
    private int backgoundColor;

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
    public int getPixel(int x, int y) {
        return this.img.getRGB(x,y);
    }

    @Override
    public int getWidth() {
        return img.getWidth();
    }

    @Override
    public int getHeight() {
        return img.getHeight();
    }
    @Override
    public  void clear(){
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0x2f2f2f));
       gr.fillRect(0, 0, img.getWidth(), img.getHeight());};

}
