package sinimage;

import filter.Filter;
import filter.ImageFilter;
import imageio.GeneralReadWrite;
import imageio.ImageReadWrite;
import imageio.PPMReadWrite;
import imagesize.ImgaeSizeChange;
import imagesize.ImgaeSizeModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import transform.ColorTransform;
import transform.ImageColorTransform;

/**
 * A standard model to represent the processing of an image. user can input a image and modify any
 * times and output at specify path.
 */
public class ImageModifyControlModel implements AImageModifyControl {

  private final ImageFilter filter;
  private final ImageColorTransform transform;
  private final String filePath;
  private int[][] redMatrix;
  private int[][] greenMatrix;
  private int[][] blueMatrix;
  private int[][] redMatrixCache;
  private int[][] greenMatrixCache;
  private int[][] blueMatrixCache;

  /**
   * The standard constructor for the model.
   *
   * @param filePath the given path.
   * @param rgb      the image rgb value.
   * @throws IllegalArgumentException if the path is null or the output is null
   */
  public ImageModifyControlModel(List<int[][]> rgb, String filePath)
      throws IllegalArgumentException {
    if (rgb == null || rgb.size() != 3 || filePath == null) {
      throw new IllegalArgumentException("wrong input");
    }
    this.filePath = filePath;
    this.generateChannel(rgb);
    this.filter = new Filter();
    this.transform = new ColorTransform();
  }

  private ImageReadWrite makeImageIo(String filePath) {
    String suffix = filePath.substring(filePath.lastIndexOf(".") + 1);
    if (suffix.equals("ppm")) {
      return new PPMReadWrite();
    } else {
      return new GeneralReadWrite();
    }
  }

  /**
   * The method to generate a copy of new channel to store rgb values, avoid directly changing the
   * file.
   */
  private void generateChannel(List<int[][]> rgb) {
    int height = rgb.get(0).length;
    int width = rgb.get(0)[0].length;
    this.redMatrix = rgb.get(0);
    this.greenMatrix = rgb.get(1);
    this.blueMatrix = rgb.get(2);
    this.redMatrixCache = new int[height][width];
    this.greenMatrixCache = new int[height][width];
    this.blueMatrixCache = new int[height][width];
    this.redMatrixCache[0][0] = -1;
  }

  /**
   * Validator for file path, throw new IllegalArgumentException if the path is null.
   */
  private void isImageInput() {
    if (this.filePath == null) {
      throw new IllegalArgumentException("image have no be loaded");
    }
  }

  /**
   * The built-in blur effect within the model.
   *
   * @throws IllegalArgumentException if image have no be loaded.
   */
  public void blur() throws IllegalArgumentException {
    this.isImageInput();
    List<int[][]> target = this.bindColorMatrix();
    List<int[][]> result = this.filter.blur(target.get(0), target.get(1), target.get(2));
    this.updateImage(result);
  }

  /**
   * Update the image cache matrix with the given updated list.
   *
   * @param update the given updated list
   */
  private void updateImage(List<int[][]> update) {
    this.redMatrixCache = update.get(0);
    this.greenMatrixCache = update.get(1);
    this.blueMatrixCache = update.get(2);
  }

  /**
   * Use the copied matrix and generate a new list to store them.
   *
   * @return the generated list.
   */
  private List<int[][]> bindColorMatrix() {
    int[][] r;
    int[][] g;
    int[][] b;
    if (this.redMatrixCache[0][0] == -1) {
      r = this.redMatrix;
      g = this.greenMatrix;
      b = this.blueMatrix;
    } else {
      r = this.redMatrixCache;
      g = this.greenMatrixCache;
      b = this.blueMatrixCache;
    }
    return new ArrayList<>(Arrays.asList(r, g, b));
  }

  /**
   * The built-in sharp effect within the model.
   *
   * @throws IllegalArgumentException if image have no be loaded.
   */
  public void sharp() throws IllegalArgumentException {
    this.isImageInput();
    List<int[][]> target = this.bindColorMatrix();
    List<int[][]> result = this.filter.sharp(target.get(0), target.get(1), target.get(2));
    this.updateImage(result);
  }

  /**
   * The built-in grey effect within the model.
   *
   * @throws IllegalArgumentException if image have no be loaded.
   */
  public void grey() throws IllegalArgumentException {
    this.isImageInput();
    List<int[][]> target = this.bindColorMatrix();
    List<int[][]> result = this.transform.grey(target.get(0), target.get(1), target.get(2));
    this.updateImage(result);
  }

  /**
   * The built-in sepia effect within the model.
   *
   * @throws IllegalArgumentException if image have no be loaded.
   */
  public void sepia() throws IllegalArgumentException {
    this.isImageInput();
    List<int[][]> target = this.bindColorMatrix();
    List<int[][]> result = this.transform.sepia(target.get(0), target.get(1), target.get(2));
    this.updateImage(result);
  }

  /**
   * return this image's rgb value. Those value be packed in a list. First is red, second is blue
   * and third is green.
   *
   * @return a list of image color information.
   */
  @Override
  public List<int[][]> getColor() {
    return new ArrayList<>(this.bindColorMatrix());
  }

  /**
   * return the image path.
   *
   * @return image path
   */
  public String returnImagePath() {
    return this.filePath;
  }

  @Override
  public void mosaic(int seed) {
    this.isImageInput();
    List<int[][]> target = this.bindColorMatrix();
    List<int[][]> result = this.filter.mosaic(target.get(0), target.get(1), target.get(2), seed);
    this.updateImage(result);
  }

  @Override
  public void downscale(int height, int width) {
    this.isImageInput();
    ImgaeSizeChange down = new ImgaeSizeModel();
    List<int[][]> target = this.bindColorMatrix();
    if (height > target.get(0).length || width > target.get(0)[0].length) {
      return;
    }
    List<int[][]> result = down.downscale(target.get(0), target.get(1),
        target.get(2), height, width);
    this.updateImage(result);
  }

}
