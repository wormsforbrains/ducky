package dev.kai;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;
import static java.awt.image.BufferedImage.TYPE_BYTE_BINARY;

public class Image {
    /**
     * Enumeration of image modes.
     *
     * <p>An {@code Image} object can be in one of two modes:
     * <ul>
     * <li>{@link #BINARY} - an image with only two colors (black and white).
     * <li>{@link #RGB} - an image with a full range of colors.
     * </ul>
     */
    public enum ImageMode {
        BINARY, RGB
    }


    /**
     * The Image's pixel data.
     */
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

    /**
     * Creates a new {@code Image} object with the specified mode, width, and height.
     * All pixels will be set to the specified color.
     *
     * @param mode   the mode of the image.
     * @param width  the width of the image.
     * @param height the height of the image.
     * @param color  the color of all pixels in the image.
     * @return a new {@code Image} object with the specified mode, width, and height, and filled with the specified color.
     */
    public static Image create(ImageMode mode, int width, int height, int color) {
        Image image = Image.create(mode, width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.image.setRGB(x, y, color);
            }
        }
        return image;
    }

    /**
     * Creates a new {@code Image} object with the specified mode, width, and height.
     *
     * @param mode   the mode of the image.
     * @param width  the width of the image.
     * @param height the height of the image.
     * @return a new {@code Image} object with the specified mode, width, and height.
     * @throws IllegalArgumentException if the mode is not BINARY or RGB.
     */
    public static Image create(ImageMode mode, int width, int height) {
        int type = switch (mode) {
            case BINARY -> TYPE_BYTE_BINARY;
            case RGB -> TYPE_3BYTE_BGR;
            default -> throw new IllegalArgumentException("Illegal mode: " + mode);
        };
        BufferedImage image = new BufferedImage(width, height, type);
        return new Image(image, mode, width, height);
    }

    /**
     * Returns the underlying {@code BufferedImage} object.
     * <br><br>
     * This can be used to access the underlying {@code BufferedImage} object to perform operations on it that are not
     * provided by the {@code Image} class, such as accessing the image data directly, or using other image processing
     * libraries that work with {@code BufferedImage}.
     *
     * @return the underlying {@code BufferedImage} object.
     */
    public BufferedImage getBuffer() {
        return this.image;
    }

    /**
     * Returns an {@code ImageDraw} object for this image.
     * <br><br>
     * The {@code ImageDraw} class can be used to perform drawing operations on the image such as drawing lines,
     * shapes, and text.
     *
     * @return an {@code ImageDraw} object for this image.
     */
    public ImageDraw draw() {
        return new ImageDraw(this);
    }

    /**
     * Saves the image to the specified file path in the specified format.
     *
     * @param filePath the file path where the image will be saved.
     * @param format   the format of the image file, such as "jpg" or "png".
     */

    public void save(String filePath, String format) {
        try {
            ImageIO.write(image, format, new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
