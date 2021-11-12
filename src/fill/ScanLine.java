package fill;

import model.Polygon;
import raster.Raster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ScanLine extends Filler {
    private Polygon polygon;
    private List<Edge> edges = new ArrayList<>();
    private int ymin = 0;
    private int ymax = 0;
    private int bg = 16777215;

    public ScanLine(Raster raster) {
        super(raster);
    }

    public void fill(int c) {
        if(c == 1) {
            Random rnd = new Random();
            bg = rnd.nextInt(16777215);
        }
        List<Integer> intersections = new ArrayList<>();
        int [ ][ ] patt =       {
                {0xFF0000,0x0000FF,0x0000FF,0xFF0000,0xFF0000},
                {0xFF0000,0x0000FF,0x00FF00,0xFF0000,0xFF0000},
                {0xFF0000,0x0000FF,0x0000FF,0x00FF00,0xFF0000},
                {0xFF0000,0x0000FF,0x0000FF,0xFF0000,0xFF0000},
                {0xFF0000,0x0000FF,0x0000FF,0xFF0000,0xFF0000}
        };
        for (int y = ymin; y <= ymax; y++){
          intersections.clear();
            for (Edge edge: this.edges) {
                if (edge.hasIntersection(y)) {
                    int x = edge.getIntersection(y);
                    intersections.add(x);
                }
            }

            //Bubble sort
            int n = intersections.size();
            int temp = 0;
            for(int i=0; i < n; i++){
                for(int j=1; j < (n-i); j++){
                    if(intersections.get(j - 1) > intersections.get(j)){
                        temp = intersections.get(j - 1);
                        intersections.set(j - 1, intersections.get(j));
                        intersections.set(j, temp);
                    }
                }
            }

            for(int i=0; i <intersections.size();i=i+2){
                int x1 = intersections.get(i);
                int x2 = intersections.get(i+1);
                for(int x = x1; x<=x2;x++){
                    if(c == 2) {
                        int k = x % patt.length;
                        int l = y % patt.length;
                        bg = patt[k][l];
                        raster.setPixel(x,y,bg);
                    }
                    else
                    raster.setPixel(x,y,bg);
                }
            }
        }
    }

    @Override
    public void setFillColor(int color) {

    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
        edges.clear();
        ymin = polygon.getPoints().get(0).getY();
        ymax = polygon.getPoints().get(0).getY();

        for(int i=0;i<polygon.getPoints().size();i++){
            int x1 = polygon.getPoints().get(i).getX();
            int y1 = polygon.getPoints().get(i).getY();
            int idx = (i+1)%polygon.getPoints().size();
            int x2 = polygon.getPoints().get(idx).getX();
            int y2 = polygon.getPoints().get(idx).getY();

            Edge edge = new Edge(x1, x2, y1, y2);
            if (!edge.isHorizontal()) {
                edge.orientate();
                if(ymin > edge.y1)
                    ymin = edge.y1;
                if(ymax< edge.y2)
                    ymax = edge.y2;
                edges.add(edge);
            }
        }
    }

    private class Edge{
        int x1, x2, y1, y2;

        Edge (int x1,int x2,int y1,int y2){
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }
        void orientate(){
            if(y1>y2) {
                int temp = y1;
                y1=y2;
                y2=temp;
                temp = x1;
                x1=x2;
                x2=temp;
            }
        }
        public boolean isHorizontal(){
            return y1 == y2;
        }

        boolean hasIntersection(int y){
            return y>=y1 && y<y2;
        }

        public int getIntersection(int y) {
           float k = (x2 - x1) / (float)(y2 -y1);
           float q = x1 - k * y1;
           int x = (int) (k * y + q);
           return x;
        }
    }
}
