import static org.junit.Assert.assertTrue;

import imageio.GeneralReadWrite;
import imageio.ImageReadWrite;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/**
 * test generalReadWrite class.
 */
public class GeneralReadWriteTest {

  ImageReadWrite gene = new GeneralReadWrite();

  @Test
  public void outputImageTest() throws IOException {
    int[][] r = new int[][]{{1, 2, 3}, {3, 2, 1}, {1, 2, 3}};
    int[][] g = new int[][]{{1, 2, 3}, {2, 2, 1}, {3, 2, 3}};
    int[][] b = new int[][]{{1, 4, 3}, {3, 2, 1}, {1, 2, 9}};
    List<int[][]> rgb = new ArrayList<>(Arrays.asList(r, g, b));
    gene.outputImage("/Users/hzh-mac/Desktop/test/HW5/res/a.png", rgb);
    List<int[][]> result = gene.readRGB("/Users/hzh-mac/Desktop/test/HW5/res/a.png");
    int p = 0;
    while (p != 3) {
      for (int i = 0; i < 3; i++) {
        for (int k = 0; k < 3; k++) {
          assertTrue(result.get(p)[i][k] == rgb.get(p)[i][k]);
        }
      }
      p++;
    }
    gene.outputImage("/Users/hzh-mac/Desktop/test/HW5/res/a.jpg", rgb);
    result = gene.readRGB("/Users/hzh-mac/Desktop/test/HW5/res/a.jpg");

    while (p != 3) {
      for (int i = 0; i < 3; i++) {
        for (int k = 0; k < 3; k++) {
          assertTrue(rgb.get(p)[i][k] - result.get(p)[i][k] <= 1);
        }
      }
      p++;
    }
  }

}
