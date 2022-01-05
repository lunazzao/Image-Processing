package filter;

import java.util.List;

/**
 * An interface for represent all image filter operation. Those operation only for calculate color
 * matrix not product image.
 */
public interface ImageFilter {

  /**
   * Blurry method to calculate blur mapping. this method received 3 color channel matrix and give
   * the color channel matrix after blur mapping. 3 matrix be packed by a list structure.
   *
   * @param r red color matrix
   * @param g green color matrix
   * @param b blue color matrix
   * @return a list include 3 color matrix
   * @throws IllegalArgumentException if any input is empty
   */
  List<int[][]> blur(int[][] r, int[][] g, int[][] b) throws IllegalArgumentException;

  /**
   * Sharp method to calculate sharp mapping. this method received 3 color channel matrix and give
   * the color channel matrix after sharp mapping. 3 matrix be packed by a list structure.
   *
   * @param r red color matrix
   * @param g green color matrix
   * @param b blue color matrix
   * @return a list include 3 color matrix
   * @throws IllegalArgumentException if any input is empty
   */
  List<int[][]> sharp(int[][] r, int[][] g, int[][] b) throws IllegalArgumentException;

  /**
   * Mosaic method to calculate mosaic mapping. this method received 3 color channel matrix and give
   * the color channel matrix after mosaic mapping. 3 matrix be packed by a list structure.
   *
   * @param r    red color matrix
   * @param g    green color matrix
   * @param b    blue color matrix
   * @param seed number of mosaic
   * @return a list include 3 color matrix
   */
  List<int[][]> mosaic(int[][] r, int[][] g, int[][] b, int seed);
}
