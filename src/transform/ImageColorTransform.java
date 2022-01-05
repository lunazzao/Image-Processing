package transform;

import java.util.List;

/**
 * An interface to represent all color transforming operations. Those operation only for calculate
 * color matrix * not product image.
 */
public interface ImageColorTransform {

  /**
   * Grey method for calculate grey mapping. This method received 3 color channel matrix and give
   * the color channel matrix after grey mapping. The 3 matrix are packed by a list structure.
   *
   * @param r red color matrix
   * @param g green color matrix
   * @param b blue color matrix
   * @return a list include 3 color matrix
   * @throws IllegalArgumentException if any input is empty
   */
  List<int[][]> grey(int[][] r, int[][] g, int[][] b) throws IllegalArgumentException;

  /**
   * Sepia method to calculate sepia mapping. this method received 3 color channel matrix and give
   * the color channel matrix after sepia mapping. 3 matrix be packed by a list structure.
   *
   * @param r red color matrix
   * @param g green color matrix
   * @param b blue color matrix
   * @return a list include 3 color matrix
   * @throws IllegalArgumentException if any input is empty
   */
  List<int[][]> sepia(int[][] r, int[][] g, int[][] b) throws IllegalArgumentException;
}
