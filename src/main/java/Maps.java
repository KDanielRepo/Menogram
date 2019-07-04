import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

public class Maps {
    int[][] colory = new int[32][32];
    int[][] mapa = new int[96][64];
    int[][] jedynka = new int[32][32];
    File file = new File("C:\\Menogram\\src\\main\\java\\testowanie.png");

    public void test() throws IOException {
        BufferedImage image = ImageIO.read(file);
        //System.out.println(image.getRGB(742, 10));
        for (int r = 0; r < 32; r++) {
            for (int o = 0; o < 32; o++) {
                jedynka[r][o] = 1;
            }
        }
        for (int l = 0; l < 64; l++) {
            for (int k = 0; k < 96; k++) {
                for (int i = 0; i < 32; i++) {
                    for (int j = 0; j < 32; j++) {
                        int korx = i;
                        int kory = j;
                        int clr;
                            clr = image.getRGB(korx+(k*32), kory+(l*32));
                        if (clr == -16248766) {
                            colory[j][i] = 1;
                        } else {
                            colory[j][i] = 0;
                        }
                    }
                }
                if (Arrays.deepEquals(colory, jedynka)) {
                    mapa[k][l] = 0;
                } else {
                    mapa[k][l] = 1;
                }
            }
        }
        File file1 = new File("C:\\Menogram\\src\\main\\java\\testowanie.txt");
        FileWriter fileWriter = new FileWriter(file1);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 96; j++) {
                //System.out.println(colory[i][j]);
                    bufferedWriter.write(Integer.toString(mapa[j][i]));
                }
                bufferedWriter.newLine();
            }
        bufferedWriter.close();
    }
}