package dev.kai;

import java.awt.*;
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
            img.setRGB(pixel[0], pixel[1], color);
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

    /**
     * Draws an anti-aliased line.
     *
     * @param x1    The x-coordinate of the origin point.
     * @param y1    The y-coordinate of the origin point.
     * @param x2    The x-coordinate of the destination point.
     * @param y2    The y-coordinate of the destination point.
     * @param color The color of the line.
     */
    public void lineAA(int x1, int y1, int x2, int y2, int color) {
        BufferedImage img = image.getBuffer();

        int dx = x2 - x1;
        int dy = y2 - y1;
        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        float xIncrement = (float) dx / (float) steps;
        float yIncrement = (float) dy / (float) steps;

        float x = x1;
        float y = y1;

        for (int i = 0; i <= steps; i++) {
            int xInt = Math.round(x);
            int yInt = Math.round(y);

            // calculate the alpha value based on the distance from the nearest pixel
            float alpha = 1 - Math.max(Math.abs(x - xInt), Math.abs(y - yInt));

            // blend the color with the existing pixel color
            Color blendedColor = blendColors(img.getRGB(xInt, yInt), color, alpha);

            img.setRGB(xInt, yInt, blendedColor.getRGB());

            x += xIncrement;
            y += yIncrement;
        }
    }

    /**
     * Draws anti-aliased lines.
     *
     * @param lines An array containing the "lines".
     * @param color The color of the line.
     */
    public void linesAA(Integer[][] lines, int color) {
        BufferedImage img = image.getBuffer();
        for (var line : lines) {
            int x1 = line[0];
            int y1 = line[1];
            int x2 = line[2];
            int y2 = line[3];

            int dx = x2 - x1;
            int dy = y2 - y1;
            int steps = Math.max(Math.abs(dx), Math.abs(dy));

            float xIncrement = (float) dx / (float) steps;
            float yIncrement = (float) dy / (float) steps;

            float x = x1;
            float y = y1;

            for (int i = 0; i <= steps; i++) {
                int xInt = Math.round(x);
                int yInt = Math.round(y);

                // calculate the alpha value based on the distance from the nearest pixel
                float alpha = 1 - Math.max(Math.abs(x - xInt), Math.abs(y - yInt));

                // blend the color with the existing pixel color
                Color blendedColor = blendColors(img.getRGB(xInt, yInt), color, alpha);

                img.setRGB(xInt, yInt, blendedColor.getRGB());

                x += xIncrement;
                y += yIncrement;
            }
        }
    }

    /**
     * Draws a circle on the image.
     *
     * @param x      The x-coordinate of the center of the circle.
     * @param y      The y-coordinate of the center of the circle.
     * @param radius The radius of the circle.
     * @param color  The color of the circle.
     */
    public void circle(int x, int y, int radius, int color) {
        BufferedImage img = image.getBuffer();
        int x0 = x;
        int y0 = y;
        int f = 1 - radius;
        int ddF_x = 0;
        int ddF_y = -2 * radius;
        x = 0;
        y = radius;

        img.setRGB(x0, y0 + radius, color);
        img.setRGB(x0, y0 - radius, color);
        img.setRGB(x0 + radius, y0, color);
        img.setRGB(x0 - radius, y0, color);

        while (x < y) {
            if (f >= 0) {
                y--;
                ddF_y += 2;
                f += ddF_y;
            }
            x++;
            ddF_x += 2;
            f += ddF_x + 1;

            img.setRGB(x0 + x, y0 + y, color);
            img.setRGB(x0 - x, y0 + y, color);
            img.setRGB(x0 + x, y0 - y, color);
            img.setRGB(x0 - x, y0 - y, color);
            img.setRGB(x0 + y, y0 + x, color);
            img.setRGB(x0 - y, y0 + x, color);
            img.setRGB(x0 + y, y0 - x, color);
            img.setRGB(x0 - y, y0 - x, color);
        }
    }

    /**
     * Draws a filled disc on the image.
     *
     * @param x      The x-coordinate of the center of the disc.
     * @param y      The y-coordinate of the center of the disc.
     * @param radius The radius of the disc.
     * @param color  The color of the disc.
     */
    public void disc(int x, int y, int radius, int color) {
        BufferedImage img = image.getBuffer();
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                if (i * i + j * j <= radius * radius) {
                    img.setRGB(x + i, y + j, color);
                }
            }
        }
    }
}
