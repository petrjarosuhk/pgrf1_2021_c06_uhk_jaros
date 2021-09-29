package raster;

import model.Line;

public abstract class LineRasterizer {
    Raster raster;
    public LineRasterizer(Raster raster){
        this.raster = raster;
    }

    public void rasterize(int x1, int y1, int x2, int y2){

    }

    public void rasterize(Line line){
        //TODO
    }
}
