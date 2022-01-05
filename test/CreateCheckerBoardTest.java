import static org.junit.Assert.assertEquals;

import imageio.ImageReadWrite;
import imageio.IoPPM;
import imageio.PPMReadWrite;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import sinimage.CreateCheckerboard;
import sinimage.ImageModifyControl;

/**
 * Test for creating checkerboards. for test seccessfully, please update the path to
 * ../HW5/res/*.ppm.
 */
public class CreateCheckerBoardTest {

  ImageReadWrite io = new PPMReadWrite();

  @Test
  public void CreateCheckerBoardTester() throws IOException {
    ImageModifyControl checker = new CreateCheckerboard(
        2, 5, 3,
        new ArrayList<Color>(Arrays.asList(Color.blue, Color.cyan, Color.gray)));
    io.outputImage("/Users/hzh-mac/Desktop/test/HW5/res/checker.ppm", checker.getColor());
    assertEquals(readPPM(), "P3\n"
        + "10 6\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "0\n"
        + "255\n"
        + "255\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "128\n"
        + "0\n"
        + "0\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "255\n");
  }


  private String readPPM() throws IOException {
    IoPPM read = new IoPPM("/Users/hzh-mac/Desktop/test/HW5/res/checker.ppm",
        "/Users/hzh-mac/Desktop/test/HW5/res/c.ppm");
    String s = "";
    while (!read.isEnd()) {
      s += read.readLine() + '\n';
    }
    read.ioClose();
    return s;
  }
}
