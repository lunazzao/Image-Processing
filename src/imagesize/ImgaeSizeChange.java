package imagesize;

import java.util.List;

/**
 * This interface include all image resize method. This interface have downscale method and can add
 * upscale method in the future.
 */
public interface ImgaeSizeChange {

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
  List<int[][]> downscale(int[][] r, int[][] g, int[][] b, int height, int width);
}
