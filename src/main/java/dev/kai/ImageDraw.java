package dev.kai;

import java.awt.image.BufferedImage;

public class ImageDraw {
    private final Image image;

    public ImageDraw(Image image) {
        this.image = image;
    }

    /**
     * Draws a pixel.
     *
     * @param x     The x-coordinate of the pixel.
     * @param y     The y-coordinate of the pixel.
     * @param color The color of the pixel.
     */
    public void pixel(int x, int y, int color) {
        image.getBuffer().setRGB(x, y, color);
    }

    /**
     * Draws pixels.
     *
     * @param pixels An array containing the "pixels".
     * @param color  The color of the pixel.
     */
    public void pixels(Integer[][] pixels, int color) {
        BufferedImage img = image.getBuffer();
        for (var pixel : pixels) {
            image.getBuffer().setRGB(pixel[0], pixel[1], color);
        }
    }

    /**
     * Draws a line.
     *
     * @param x1    The x coordinate of the origin point.
     * @param y1    The y coordinate of the origin point.
     * @param x2    The x coordinate of the destination point.
     * @param y2    The y coordinate of the destination point.
     * @param color The color of the line.
     */
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

    /**
     * Draws lines.
     *
     * @param lines An array containing the "lines".
     * @param color The color of the line.
     */
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
