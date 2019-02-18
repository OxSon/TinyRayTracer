package render;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Author: Alec Mills
 */

public class TinyRaytracer {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    private static float FOV = (float) Math.PI / 2;

    private static void render(Sphere sphere) {
        var framebuffer = new VectorF[WIDTH * HEIGHT];


        //setup framebuffer
//        for (int i = 0; i < HEIGHT; i++) {
//            for (int j = 0; j < WIDTH; j++) {
//                //FIXME debugging
////                int index = 769;
////                System.out.printf("index %s = %s", index, framebuffer[index]);
////
////                System.out.printf("i: %d, j: %d (framebuffer size: %d)%n", i, j, framebuffer.length);
//                framebuffer[j + (i * WIDTH)] = new VectorF(3, i / (float) HEIGHT, j / (float) WIDTH, 0);
//            }
//        }

        createFile();

        // actually write to file
        try (var fos = new FileOutputStream("out/out.ppm")) {
            //header
            fos.write(String.format("P6\n%d %d\n255\n", WIDTH, HEIGHT).getBytes());

                for (int i = 0; i < HEIGHT; i++) {
                    for (int j = 0; j < WIDTH; j++) {
//                        float x = 2 * (j + 0.5F) / (float) WIDTH - 1;
                        float x =
                                (2 * (j + 0.5F) / WIDTH - 1) *
                                        (float) Math.tan(FOV / 2) *
                                        WIDTH / HEIGHT;
                        float y =
                                (2 * (i + 0.5F) / WIDTH - 1) *
                                        (float) Math.tan(FOV / 2) *
                                        WIDTH / HEIGHT;
                        var dir = new VectorF(3, x, y, -1).normalize();

                        framebuffer[j + (i * WIDTH)] =
                                sphere.castRay(
                                        new VectorF(3, 0,
                                                0, 0), dir);
                    }
                }

                for (int i = 0; i < HEIGHT * WIDTH; i++) {
                    for (int j = 0; j < 3; j++) {
                        fos.write((char) (255 * Math.max(0F, Math.min(1F, framebuffer[i].getComponents()[j]))));
                    }
                }
        } catch (IOException e) {
            System.err.println("FileoutputStream failure");
            e.printStackTrace();
        }

        //TODO use return value of method?
        renameFile();
    }

    private static boolean createFile() {
        File f = new File("out/out.ppm");
        try {
            return f.createNewFile();
        } catch (IOException e) {
            System.err.println("Problem creating out.ppm");
            e.printStackTrace();
        }

        return false;
    }

    private static boolean renameFile() {
        File oldF = new File("out/out.ppm");

        //time stuff
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String name = String.format("%s, %d x %d", format.format(cal.getTime()),
                WIDTH,
                HEIGHT);

        var newF = new File("out/", name);

        return oldF.renameTo(newF);
    }

    public static void main(String[] args) {
        //FIXME
        //Sphere sphere = new Sphere(new VectorF(3, -3, 0, -16), 2);
        //render(sphere);
    }

}
