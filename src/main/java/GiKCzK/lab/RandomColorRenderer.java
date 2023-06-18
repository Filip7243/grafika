package GiKCzK.lab;

import java.util.concurrent.ThreadLocalRandom;

public class RandomColorRenderer extends Renderer {

    public RandomColorRenderer(String filename) {
        super(filename);
    }

    public RandomColorRenderer(String filename, int w, int h) {
        super(filename, w, h);
    }

    public void render(Model model) {
        for (Vec3i face : model.getFaceList()) {

            Vec2i[] screenCoords = new Vec2i[3];
            Vec3f[] worldCoords = new Vec3f[3];

            worldCoords[0] = model.getVertex(face.x);
            worldCoords[1] = model.getVertex(face.y);
            worldCoords[2] = model.getVertex(face.z);

            for (int j = 0; j < 3; j++) {
                screenCoords[j] = new Vec2i((int) ((worldCoords[j].x + 1.0) * render.getWidth() / 2.0),
                        (int) ((worldCoords[j].y + 1.0) * render.getHeight() / 2.0) - render.getHeight() / 2);
            }

            int randColor = ThreadLocalRandom.current().nextInt(0, 0x00ffffff) | 0xff000000;
            drawTriangle(screenCoords[0], screenCoords[1], screenCoords[2], randColor);
        }
    }
}