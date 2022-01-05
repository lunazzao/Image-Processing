package imagesize;

import java.util.Arrays;
import java.util.List;

/**
 * this class include all upscale and downscale algorithm. Those method only received and output rgb
 * value of image. They do not make a image here.
 */
public class ImgaeSizeModel implements ImgaeSizeChange {

  /**
   * Downscale image to specify resolution image. This image's height and width must smaller then
   * original image.
   *
   * @param r      red color matrix
   * @param g      green color matrix
   * @param b      blue color matrix
   * @param height image height
   * @param width  image width
   * @return resize image rgb value
   */
  @Override
  public List<int[][]> downscale(int[][] r, int[][] g, int[][] b, int height, int width)
      throws IllegalArgumentException {
    if (r == null || g == null || b == null || r.length == 0 || r[0].length == 0 || g.length == 0
        || g[0].length == 0 || b.length == 0 || b[0].length == 0) {
      throw new IllegalArgumentException("Wrong input.");
    }

    if (height >= r.length || width >= r[0].length) {
      throw new IllegalArgumentException("The input size cannot be bigger than the original.");
    }

    return Arrays.asList(downscaleHelper(r, height, width), downscaleHelper(g, height, width),
        downscaleHelper(b, height, width));
  }

  private int[][] downscaleHelper(int[][] ori, int height, int width) {
    float x1;
    float y1;
    int[][] newMatrix = new int[height][width];
    for (int i = 0; i < height; i++) { //height
      for (int j = 0; j < width; j++) { //width
        y1 = (float) j * ori[0].length / width;
        x1 = (float) i * ori.length / height;

        newMatrix[i][j] = getSingleValue(ori, x1, y1);
      }
    }
    return newMatrix;
  }

  private boolean isFloatHelper(float x1) {
    return x1 % 1 == 0;
  }


  private int getSingleValue(int[][] ori, float x1, float y1) {
    float m, n;
    int xCeil = (int) Math.ceil(x1);
    int yCeil = (int) Math.ceil(y1);
    int xFloor = (int) Math.floor(x1);
    int yFloor = (int) Math.floor(y1);
    if (isFloatHelper(x1)) {
      xCeil = ori[0].length - 1 == xFloor ? xFloor : (int) (x1 + 1);
    }

    if (isFloatHelper(y1)) {
      yCeil = ori.length - 1 == yFloor ? yFloor : (int) (y1 + 1);
    }
    m = ori[xCeil][yFloor] * (x1 - xFloor) + ori[xFloor][yFloor] * (xCeil - x1);
    n = ori[xCeil][yCeil] * (x1 - xFloor) + ori[xFloor][yCeil] * (xCeil - x1);
    return (int) (n * (y1 - yFloor) + m * (yCeil - y1));

  }
}
