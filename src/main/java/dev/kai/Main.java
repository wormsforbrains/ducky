package dev.kai;

import static dev.kai.Image.ImageMode.BINARY;

public class Main {
    public static void main(String[] args) {
        Image img;
        ImageDraw draw;

        img = Image.create(BINARY, 1000, 1000, 0);
        draw = img.draw();
        draw.line(0, 0, 999, 999, -1);
        img.save("test.png", "png");
    }
}