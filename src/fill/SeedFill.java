package fill;

import raster.Raster;

public class SeedFill {
    private String hexaCode = "ff2f2f2f";
    private String boundery = "ffffff00";
    private String fillColor = "ffff0000";
    private Raster raster;

    public SeedFill(Raster raster) {
        this.raster = raster;
    }

    public void fillBoundery(int x, int y, int bgcolor) {
        int colorNumber = raster.getPixel(x, y);
        String hexaColor = Integer.toHexString(colorNumber);

            if (!hexaColor.equals(boundery) && (!hexaColor.equals(fillColor))) {
                    raster.setPixel(x, y, bgcolor);
                    fillBoundery(x + 1, y, bgcolor);
                    fillBoundery(x + 1, y, bgcolor);
                    fillBoundery(x, y + 1, bgcolor);
                    fillBoundery(x - 1, y, bgcolor);
                    fillBoundery(x, y - 1, bgcolor);
                }

        }

        public void fill ( int x, int y, int bgColor){

            if (isInside(x, y)) {
                raster.setPixel(x, y, bgColor);
                fill(x + 1, y, bgColor);
                fill(x, y + 1, bgColor);
                fill(x - 1, y, bgColor);
                fill(x, y - 1, bgColor);

            }
        }

        private boolean isInside ( int x, int y){

            int colorNumber = raster.getPixel(x, y);
            String hexaColor = Integer.toHexString(colorNumber);
            if (hexaColor.equals(hexaCode)) {
                return true;
            }
            return false;
        }
    }

