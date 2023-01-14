package dev.kai;

import java.awt.image.BufferedImage;

public class ImageDraw {
    private final Image image;

    public ImageDraw(Image image) {
        this.image = image;
    }

    public void pixel(int x, int y, int color) {
        image.getBuffer().setRGB(x, y, color);
    }

    public void pixels(Integer[][] pixels, int color) {
        BufferedImage img = image.getBuffer();
        for (var pixel : pixels) {
            image.getBuffer().setRGB(pixel[0], pixel[1], color);
        }
    }

    public void line(int x1, int y1, int x2, int y2, int color) {
        BufferedImage img = image.getBuffer();
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            img.setRGB(x1, y1, color);
            if (x1 == x2 && y1 == y2) {
                break;
            }
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    public void lines(Integer[][] lines, int color) {
        BufferedImage img = image.getBuffer();
        for (var line : lines) {
            int x1 = line[0];
            int y1 = line[1];
            int x2 = line[2];
            int y2 = line[3];

            int dx = Math.abs(x2 - x1);
            int dy = Math.abs(y2 - y1);
            int sx = x1 < x2 ? 1 : -1;
            int sy = y1 < y2 ? 1 : -1;
            int err = dx - dy;

            while (true) {
                img.setRGB(x1, y1, color);
                if (x1 == x2 && y1 == y2) {
                    break;
                }
                int e2 = 2 * err;
                if (e2 > -dy) {
                    err -= dy;
                    x1 += sx;
                }
                if (e2 < dx) {
                    err += dx;
                    y1 += sy;
                }
            }
        }
    }
}
