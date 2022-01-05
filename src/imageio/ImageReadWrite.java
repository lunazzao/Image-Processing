package imageio;

import java.io.IOException;
import java.util.List;

/**
 * this interface include all image type input and output. This interface for read and write a image
 * form specified path.
 */
public interface ImageReadWrite {

  /**
   * output image base on the specify rgb value and path.
   *
   * @param path image path we want to output.
   * @param rgb  image's pixel information.
   * @throws IOException path is wrong
   */
  void outputImage(String path, List<int[][]> rgb) throws IOException;

  /**
   * read image form specify path and translate to rbg value matrix.
   *
   * @param path image path we want to read.
   * @return image's rgb matrix
   * @throws IOException image path wrong.
   */
  List<int[][]> readRGB(String path) throws IOException;


}
