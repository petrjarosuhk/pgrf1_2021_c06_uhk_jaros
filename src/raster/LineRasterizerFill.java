package raster;

import static java.lang.Math.abs;
import static java.lang.Math.round;

public class LineRasterizerFill extends LineRasterizer{
    public LineRasterizerFill(Raster raster) {
        super(raster);
    }

    public void rasterize(int x1, int y1, int x2, int y2, int color) {
        int dx = x2 - x1;
        int dy = y2 - y1;


        int steps = abs(dx) > abs(dy) ? abs(dx) : abs(dy);


        float Xinc = dx / (float) steps;
        float Yinc = dy / (float) steps;

        // Put pixel for each step
        float X = x1;
        float Y = y1;
        for (int i = 0; i <= steps; i++)
        {
            raster.setPixel(round(X),round(Y),0xFFFF00);
            X += Xinc;
            Y += Yinc;


        }



    }
}

