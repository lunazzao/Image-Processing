package sinimage;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * for generate a checkerboard and output it to specify path. This class can update path. Therefore,
 * we can output image any times.
 */
public class CreateCheckerboard implements ImageModifyControl {

  private final int tileWidth;
  private final int numTileHeight;
  private final int numTileWidth;
  private final List<Color> colors;

  /**
   * Constructor for generating io for output ppm image and confirm what checkerboard need to be
   * generated.
   *
   * @param tileWidth     each square width
   * @param numTileWidth  the number of square in a row
   * @param numTileHeight the number of square in a row
   * @param colors        the color square to be draw
   * @throws IllegalArgumentException if any input is empty
   */
  public CreateCheckerboard(int tileWidth, int numTileWidth, int numTileHeight,
      List<Color> colors) throws IllegalArgumentException {
    this.tileWidth = tileWidth;
    this.numTileWidth = numTileWidth;
    this.numTileHeight = numTileHeight;
    this.colors = colors;
  }

  /**
   * Easy constructor for generating io for output ppm image and confirm what checkerboard need to
   * be generated.
   *
   * @param tileWidth     each square width
   * @param numTileWidth  the number of square in a row
   * @param numTileHeight the number of square in a row
   * @param colors        the color square to be draw
   * @throws IOException if any input is empty
   */
  private List<int[][]> checkerboard(int tileWidth, int numTileWidth, int numTileHeight,
      List<Color> colors) {
    int width = tileWidth * numTileWidth;
    int height = tileWidth * numTileHeight;
    int[][] r = new int[height][width];
    int[][] g = new int[height][width];
    int[][] b = new int[height][width];
    for (int i = 0; i < height * width; i++) {
      Color color = colors.get((i % width / tileWidth + i / width / tileWidth) % colors.size());
      int w = i % width;
      int h = i / width;
      r[h][w] = color.getRed();
      g[h][w] = color.getGreen();
      b[h][w] = color.getBlue();
    }
    return new ArrayList<>(Arrays.asList(r, g, b));
  }

  /**
   * return this image's rgb value. Those value be packed in a list. First is red, second is blue
   * and third is green.
   *
   * @return a list of image color information.
   */
  @Override
  public List<int[][]> getColor() {
    return this.checkerboard(this.tileWidth, this.numTileWidth, this.numTileHeight, this.colors);
  }
}
