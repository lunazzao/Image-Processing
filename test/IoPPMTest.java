import imageio.IoPPM;
import java.io.IOException;
import org.junit.Test;

/**
 * for test IoPPM class.
 */
public class IoPPMTest {

  @Test(expected = IllegalArgumentException.class)
  public void filePathNull() throws IOException {
    new IoPPM(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createPPMWrongPath() throws IOException {
    new IoPPM("/Users/hzh-mac/Desktop/test/HW5/res/test.ppm",
        "/Users/hzh-mac/Desktop/cde/a.ppm");
  }

  @Test(expected = IOException.class)
  public void writerPPMException() throws IOException {
    IoPPM io = new IoPPM("/Users/hzh-mac/Desktop/test/HW5/res/test.ppm",
        "/Users/hzh-mac/Desktop/test/HW5/res/a.ppm");
    io.ioClose();
    io.writerFile("");
  }

}
