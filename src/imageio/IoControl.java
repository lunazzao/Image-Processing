package imageio;

import imageio.GeneralReadWrite;
import imageio.ImageReadWrite;
import imageio.PPMReadWrite;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import sinimage.AImageModifyControl;
import sinimage.ImageModifyControlModel;

/**
 * a helper class for control io part. This part can help to change the input and output image type
 * and image number. This class can output several images and help to load multi-layer record.
 */
public class IoControl {

  /**
   * get a list of image for path.
   *
   * @param path the txt file path.
   * @return list of image
   * @throws IOException path wrong.
   */
  public List<AImageModifyControl> getImages(String path) throws IOException {
    if (!path.substring(path.lastIndexOf(".") + 1).equals("txt")) {
      throw new IllegalArgumentException("wrong file");
    }
    ImageReadWrite io = null;
    File myObj = new File(path);
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(myObj));
    } catch (Exception e) {
      throw new IllegalArgumentException("wrong file");
    }
    String foldPath = path.substring(0, path.lastIndexOf("/") + 1);
    String st;
    st = reader.readLine();
    st = reader.readLine();
    List<AImageModifyControl> images = new ArrayList<>();
    while ((st = reader.readLine()) != null) {
      if (st.substring(st.lastIndexOf(".") + 1).equals("ppm")) {
        io = new PPMReadWrite();
      } else {
        io = new GeneralReadWrite();
      }
      images.add(new ImageModifyControlModel(io.readRGB(foldPath + st), foldPath + st));
    }
    reader.close();
    return images;
  }

  /**
   * get a image from path.
   *
   * @param path this image path
   * @return a image
   * @throws IOException path wrong.
   */
  public AImageModifyControl getImage(String path) throws IOException {
    ImageReadWrite io = null;
    String st = path.substring(path.lastIndexOf(".") + 1);
    if (st.substring(st.lastIndexOf(".") + 1).equals("ppm")) {
      io = new PPMReadWrite();
    } else {
      io = new GeneralReadWrite();
    }
    return new ImageModifyControlModel(io.readRGB(path), path);
  }

  /**
   * get the layer transparent information from txt.
   *
   * @param path txt file path
   * @return a list of image transparent state
   * @throws IllegalArgumentException wrong txt file.
   * @throws IOException              reader error.
   */
  public List<Boolean> getTransparent(String path) throws IllegalArgumentException, IOException {
    if (!path.substring(path.lastIndexOf(".") + 1).equals("txt")) {
      throw new IllegalArgumentException("wrong file");
    }
    File myObj = new File(path);
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(myObj));
    } catch (Exception e) {
      throw new IllegalArgumentException("wrong file");
    }
    String st = reader.readLine();
    st = reader.readLine();

    reader.close();
    List<Boolean> tran = new ArrayList<>();
    for (int i = 0; i < st.length(); i++) {
      boolean state = st.charAt(i) == '1' ? true : false;
      tran.add(state);
    }
    return tran;
  }

  /**
   * outputAll for output all image and generate their record txt.
   *
   * @param images a list of image need to output
   * @param path   output path
   * @param tran   image transpatent state
   * @throws IOException txt writer error.
   */
  public void outputAll(List<AImageModifyControl> images, String path, List<Integer> tran)
      throws IOException {
    boolean text = true;
    ImageReadWrite io = null;
    FileWriter writer = null;
    if (tran == null) {
      text = false;
    }
    if (text) {
      writer = new FileWriter(path + "record.txt");
      writer.write(path + "\n");
      String transparent = "";
      for (int i = 0; i < tran.size(); i++) {
        transparent += tran.get(i) + "";
      }
      writer.write(transparent + "\n");
    }
    for (int i = 0; i < images.size(); i++) {
      String fileName = images.get(i).returnImagePath()
          .substring(images.get(i).returnImagePath().lastIndexOf("/") + 1);
      String imagePath = text ? path + fileName : path;
      if (text) {
        writer.write(fileName + "\n");
      }
      if (imagePath.substring(imagePath.lastIndexOf(".") + 1).equals("ppm")) {
        io = new PPMReadWrite();
      } else {
        io = new GeneralReadWrite();
      }
      io.outputImage(imagePath, images.get(i).getColor());
    }
    if (text) {
      writer.close();
    }
  }

}
