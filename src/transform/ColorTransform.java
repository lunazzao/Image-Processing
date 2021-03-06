package transform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ColorTransform class include all color transform operations. Those operation help to calculate
 * the color matrix mapping.
 */
public class ColorTransform implements ImageColorTransform {

  /**
   * The built-in grey filter, set as final private static to avoid modification and outer change.
   */
  final private static Double[][] greyScale = new Double[][]{{0.2126, 0.7152, 0.0722}
      , {0.2126, 0.7152, 0.0722},
      {0.2126, 0.7152, 0.0722}};

  /**
   * The built-in sepia filter, set as final private static to avoid modification and outer change.
   */
  final private static Double[][] sepiaFilter = new Double[][]{{0.393, 0.769, 0.189},
      {0.349, 0.686, 0.168},
      {0.272, 0.534, 0.131}};


  @Override
  public List<int[][]> grey(int[][] r, int[][] g, int[][] b) throws IllegalArgumentException {
    if (r == null || g == null || b == null) {
      throw new IllegalArgumentException("Input can not be empty");
    }
    this.colorTransformUpdate(greyScale, r, g, b);
    return new ArrayList<>(Arrays.asList(r, g, b));
  }


  @Override
  public List<int[][]> sepia(int[][] r, int[][] g, int[][] b) throws IllegalArgumentException {
    if (r == null || g == null || b == null) {
      throw new IllegalArgumentException("Input can not be empty");
    }
    this.colorTransformUpdate(sepiaFilter, r, g, b);
    return new ArrayList<>(Arrays.asList(r, g, b));
  }

  /**
   * Method to update the transformed rgb values from list generated by colorTransforming method.
   *
   * @param op the color transforming filter used, ie, grey or sepia.
   * @param r  the matrix of red color
   * @param g  the matrix of g color
   * @param b  the matrix of b color
   */
  private void colorTransformUpdate(Double[][] op, int[][] r, int[][] g, int[][] b) {
    for (int i = 0; i < r.length; i++) {
      for (int k = 0; k < r[0].length; k++) {
        int[] cache = this.colorTransforming(op, r[i][k], g[i][k], b[i][k]);
        r[i][k] = cache[0];
        g[i][k] = cache[1];
        b[i][k] = cache[2];
      }
    }
  }

  /**
   * The method receiving rgb values at specific place of the matrix, and calculate specific values
   * by multiplying indexes from the given transforming filter. Return a new list of values.
   *
   * @param op    the filter used, ie, grey or sepia
   * @param red   the specific red value passed by colorTransformUpdate
   * @param green the specific green value passed by colorTransformUpdate
   * @param blue  the specific blue value passed by colorTransformUpdate
   * @return a cache list containing all the transformed values.
   */
  private int[] colorTransforming(Double[][] op, int red, int green, int blue) {
    int r = (int) (op[0][0] * red + op[0][1] * green + op[0][2] * blue);
    int g = (int) (op[1][0] * red + op[1][1] * green + op[1][2] * blue);
    int b = (int) (op[2][0] * red + op[2][1] * green + op[2][2] * blue);
    return new int[]{r, g, b};
  }
}
