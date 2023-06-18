package GiKCzK.lab;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class App {
    String version = "0.02";

    public static void main(String[] args) {

        int white = 255 | (255 << 8) | (255 << 16) | (255 << 24);
        System.out.println(white);
//        Renderer mainRenderer = new Renderer(args[0], Integer.parseInt(args[1]),
//                Integer.parseInt(args[2]), args[3]);
//        Renderer mainRenderer = new Renderer(args[0], Integer.parseInt(args[1]),
//                Integer.parseInt(args[2]));
//        mainRenderer.clear();
//        mainRenderer.drawPoint(100, 100);
        FlatShadingRenderer mainRenderer =
                new FlatShadingRenderer(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        try {
            Model model = new Model();
            model.readOBJ("deer-mod.obj");
            mainRenderer.render(model);
            mainRenderer.save();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getVersion() {
        return this.version;
    }

    public static void allWhite(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        int white = (0xff << 24) | (0xff << 16) | (0xff << 8) | (0xff);

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                img.setRGB(i, j, white);
            }
        }
    }

    public static void imgNegative(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                int p = img.getRGB(i, j);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                a = (255 - a) & 0xff;
                r = (255 - r) & 0xff;
                g = (255 - g) & 0xff;
                b = (255 - b) & 0xff;

                p = b | (g << 8) | (r << 16) | (a << 24);
                img.setRGB(i, j, p);
            }
        }
    }
}
