package dev.kai;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;
import static java.awt.image.BufferedImage.TYPE_BYTE_BINARY;

public class Image {
    // TODO: Add documentation

    public enum ImageMode {
        BINARY, RGB
    }

    // The Image's pixel data
    private final BufferedImage image;
    private final ImageMode mode;
    private final int width;
    private final int height;

    private Image(BufferedImage image, ImageMode mode, int width, int height) {
        this.image = image;
        this.mode = mode;
        this.width = width;
        this.height = height;
    }

    public static Image create(ImageMode mode, int width, int height, int color) {
        Image image = Image.create(mode, width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.image.setRGB(x, y, color);
            }
        }
        return image;
    }

    public static Image create(ImageMode mode, int width, int height) {
        int type = switch (mode) {
            case BINARY -> TYPE_BYTE_BINARY;
            case RGB -> TYPE_3BYTE_BGR;
            default -> throw new IllegalArgumentException("Invalid mode: " + mode);
        };
        BufferedImage image = new BufferedImage(width, height, type);
        return new Image(image, mode, width, height);
    }

    public BufferedImage getBuffer() {
        return this.image;
    }

    public ImageDraw draw() {
        return new ImageDraw(this);
    }

    public void save(String filePath, String format) {
        try {
            ImageIO.write(image, format, new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
