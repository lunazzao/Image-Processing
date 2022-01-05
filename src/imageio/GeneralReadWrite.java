package imageio;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * this class for translate image and rgb value if image is not the ppm format. This image support
 * all java ImageIo class support image format.
 */
public class GeneralReadWrite implements ImageReadWrite {

  /**
   * output image base on the specify rgb value and path.
   *
   * @param path image path we want to output.
   * @param rgb  image's pixel information.
   * @throws IOException path is wrong
   */
  @Override
  public void outputImage(String path, List<int[][]> rgb) throws IOException {
    int height = rgb.get(0).length;
    int width = rgb.get(0)[0].length;
    BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int red = ensureValue(rgb.get(0)[y][x]);
        int green = ensureValue(rgb.get(1)[y][x]);
        int blue = ensureValue(rgb.get(2)[y][x]);
        Color c = new Color(red, green, blue);
        int color = c.getRGB();
        output.setRGB(x, y, color);
      }
    }
    FileOutputStream out = new FileOutputStream(path);
    ImageIO.write(output, path.substring(path.lastIndexOf(".") + 1), out);
    out.close();
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
    BufferedImage inputIo = ImageIO.read(new File(path));
    int height = inputIo.getHeight();
    int width = inputIo.getWidth();
    int[][] r = new int[height][width];
    int[][] g = new int[height][width];
    int[][] b = new int[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int pixel = inputIo.getRGB(x, y);
        Color color = new Color(pixel, true);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        r[y][x] = red;
        g[y][x] = green;
        b[y][x] = blue;
      }
    }
    return new ArrayList<int[][]>(Arrays.asList(r, g, b));
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
