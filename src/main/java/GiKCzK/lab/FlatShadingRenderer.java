package GiKCzK.lab;

import java.util.List;

import static java.lang.Math.*;

public class FlatShadingRenderer extends Renderer {

    public FlatShadingRenderer(String filename) {
        super(filename);
    }

    public FlatShadingRenderer(String path, int w, int h) {
        super(path, w, h);
    }

//    public void render(Model model) {
//        // bierzemy wszystkie wierzchołki
//        List<Vec3i> faceList = model.getFaceList();
//
//        for (Vec3i face : faceList) {
//            Vec3i[] screenCoords = new Vec3i[3];
//            Vec3f[] worldCoords = new Vec3f[3];
//
//            // world coordinates przetrzymuja wierzcholki danego wielokota
//            worldCoords[0] = model.getVertex(face.x);
//            worldCoords[1] = model.getVertex(face.y);
//            worldCoords[2] = model.getVertex(face.z);
//
//            for (int i = 0; i < 3; i++) {
//                Vec3f worldCoordinate = worldCoords[i];
//
//                Vec3i screenCoordinate = new Vec3i(
//                        (int) ((worldCoordinate.x + 1) * render.getWidth() / 2),
//                        (int) ((worldCoordinate.y + 1) * render.getHeight() / 2 - render.getHeight() / 2),
//                        (int) ((worldCoordinate.z + 1) * render.getHeight() / 2 - render.getHeight() / 2)
//                );
//
//                screenCoords[i] = screenCoordinate;
//            }
//
//            // obliczamy cos kąta między wektorem [50,50,50] - nie wiem dlaczego, a wektorem normalnym do wielokąta
//            // ten [50, 50, 50] to jest chyba wketor padania światła czy cos,
//            // jego chyba sami możemy sobie ustalić jak nam się podoba
//            // i ten cos jeszcze mnozymy * 128 * 2, tez nie wiem skad sie to wzielo
//            float cosBetweenVectors = getCosBetweenVectors(
//                    new Vec3f(50, 50, 50),
//                    getNormalVector(worldCoords[0], worldCoords[1], worldCoords[2])
//            ) * 128 * 2;
//
//            // zaokraglamy ten cos i za jego pomoca tworzymy kolor (cień)
//            int roundedCos = round(cosBetweenVectors);
//
//            drawTriangle3i(screenCoords[0], screenCoords[1], screenCoords[2], roundedCos);
//        }
//    }

    public void render(Model model) {
        for (Vec3i face : model.getFaceList()) {
            Vec3i[] screenCoords = new Vec3i[3];
            Vec3f[] worldCoords = new Vec3f[3];

            worldCoords[0] = model.getVertex(face.x);
            worldCoords[1] = model.getVertex(face.y);
            worldCoords[2] = model.getVertex(face.z);

            for (int i = 0; i < 3; i++) {
                worldCoords[i] = model.getVertex(face.getXYZ(i));

                Vec3i screenCoordinate = new Vec3i(
                        (int) ((worldCoords[i].x + 1.0) * render.getWidth() / 2.0),
                        (int) ((worldCoords[i].y + 1.0) * render.getWidth() / 2.0 - render.getHeight() / 2.0),
                        (int) ((worldCoords[i].z + 1.0) * render.getHeight() / 2.0 - render.getHeight() / 2.0)
                );
                screenCoords[i] = screenCoordinate;
            }

            float cosBetweenVectors = getCosBetweenVectors(new Vec3f(50, 50, 50),
                    getNormalVector(worldCoords[0], worldCoords[1], worldCoords[2])) * 128 * 2;
            int roundedCosBetweenVectors = Math.round(cosBetweenVectors);

            Vec3i color = new Vec3i(roundedCosBetweenVectors, roundedCosBetweenVectors, roundedCosBetweenVectors);
            drawTriangle3i(screenCoords[0], screenCoords[1], screenCoords[2], roundedCosBetweenVectors);
        }
    }

    public float getCosBetweenVectors(Vec3f A, Vec3f B) {
        // dlugosc wektorow to suma kwadratow punktow wektora pod pierwiastkiem

        // dlugosc wektora A
        float lengthA = (float) sqrt(pow(A.x, 2) + pow(A.y, 2) + pow(A.z, 2));
        // dlugosc wektora B
        float lengthB = (float) sqrt(pow(B.x, 2) + pow(B.y, 2) + pow(B.z, 2));
        // iloczyn wektorow (suma iloczynów poszczególnych punktów)
        float product = (A.x * B.x) + (A.y * B.y) + (A.z + B.z);

        // cos miedzy wektorami A i B = product / (lengthA * lengthB)
        return product / (lengthA * lengthB);
    }

    public Vec3f getNormalVector(Vec3f A, Vec3f B, Vec3f C) {
        // dla wektorow z x,y,z: n = (b-a)x(c-a)

        // (b-a)
        Vec3f v1 = new Vec3f((B.x - A.x), (B.y - A.y), (B.z - A.z));
        // (c-a)
        Vec3f v2 = new Vec3f((C.x - A.x), (C.y - A.y), (C.z - A.z));

        // (b-a)x(c-a)
        return countCross(v1, v2);
    }
}
