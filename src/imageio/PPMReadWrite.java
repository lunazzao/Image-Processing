package imageio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * a class for read and write ppm image. This class include methods can transform between image and
 * rgb value matrix.
 */
public class PPMReadWrite implements ImageReadWrite {


  /**
   * output a ppm image base on the specify rgb value and path.
   *
   * @param path image path we want to output.
   * @param rgb  image's pixel information.
   * @throws IOException path is wrong
   */
  @Override
  public void outputImage(String path, List<int[][]> rgb) throws IOException {
    IoPPM output = new IoPPM(path);
    output.createFile();
    int[][] r = rgb.get(0);
    int[][] g = rgb.get(1);
    int[][] b = rgb.get(2);
    int height = r.length;
    int width = r[0].length;
    output.writerFile("P3");
    output.writerFile(width + " " + height);
    output.writerFile("255");
    for (int i = 0; i < height; i++) {
      for (int k = 0; k < width; k++) {
        try {
          output.writerFile(this.ensureValue(r[i][k]) + "");
          output.writerFile(this.ensureValue(g[i][k]) + "");
          output.writerFile(this.ensureValue(b[i][k]) + "");
        } catch (IOException e) {
          output.ioClose();
          throw e;
        }
      }
    }
    output.ioClose();
  }

  /**
   * read image form specify path and translate to rbg value matrix.
   *
   * @param path image path we want to read.
   * @return image's rgb matrix
   * @throws IOException image path wrong.
   */
  @Override
  public List<int[][]> readRGB(String path) throws IOException {
    IoPPM inputIo = new IoPPM(path, null);
    int count = 0;
    int height = 0;
    int width = 0;
    while (count < 3) {
      String s = inputIo.readLine();
      if (s.charAt(0) != '#') {
        count++;
      } else {
        continue;
      }
      if (count == 2) {
        width = Integer.parseInt(s.substring(0, s.indexOf(' ')));
        height = Integer.parseInt(s.substring(s.indexOf(' ') + 1));
      }
    }
    count = 0;
    int[][] redMatrix = new int[height][width];
    int[][] greenMatrix = new int[height][width];
    int[][] blueMatrix = new int[height][width];
    while (!inputIo.isEnd()) {
      if (count == width * height * 3) {
        throw new IllegalArgumentException("Invalid ppm image");
      }
      int w = count / 3 % width;
      int h = count / 3 / width;
      String s = inputIo.readLine();
      if (s.charAt(0) == '#') {
        continue;
      }
      if (Integer.parseInt(s) < 0 || Integer.parseInt(s) > 255) {
        throw new IllegalArgumentException("Invalid ppm image");
      }

      if (count % 3 == 0) {
        redMatrix[h][w] = Integer.parseInt(s);
      } else if (count % 3 == 1) {
        greenMatrix[h][w] = Integer.parseInt(s);
      } else {
        blueMatrix[h][w] = Integer.parseInt(s);
      }
      count++;
    }
    inputIo.ioClose();
    return new ArrayList<int[][]>(Arrays.asList(redMatrix, greenMatrix, blueMatrix));
  }

  private int ensureValue(int i) {
    if (i < 0) {
      return 0;
    } else if (i > 255) {
      return 255;
    } else {
      return i;
    }
  }
}
