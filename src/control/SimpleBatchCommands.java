package control;

import imageio.IoControl;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import multiimage.MultiImageControl;
import multiimage.MultiImageControlModel;
import sinimage.AImageModifyControl;
import view.IView;
import view.IWindow;

/**
 * a class to control all other part. this part can use to read command and control other part to
 * manager the whole software.
 */
public class SimpleBatchCommands implements CommandModel {

  private MultiImageControl model;
  //private Appendable appendable;
  private IView view;
  private Readable rd;
  private IoControl profile = new IoControl();
  private IWindow win;

  /**
   * Constructor for manual type-in input.
   *
   * @param model multiimage.MultiImageControl
   * @param view  output massage. can be null
   * @param rd    readable
   */

  public SimpleBatchCommands(MultiImageControl model, IView view, Readable rd) {
    if (rd == null || model == null || view == null) {
      throw new IllegalArgumentException("invalid input");
    }
    this.model = model;
    this.view = view;
    this.rd = rd;
    this.renderMessage(
        "Please input your command:\n"
            + "create layer*, remove layer*, load, saveTop path*, saveAll path*, "
            + "image processing methods...\n");
  }

  /**
   * Constructor for file input.
   *
   * @param model         multiimage.MultiImageControl
   * @param view          output massage. can be null
   * @param typedFilePath read parameter
   */
  public SimpleBatchCommands(MultiImageControl model, IView view, String typedFilePath)
      throws FileNotFoundException {
    if (typedFilePath == null || model == null) {
      throw new IllegalArgumentException("invalid input");
    }
    this.rd = new InputStreamReader(new FileInputStream(typedFilePath));
    this.model = model;
    this.view = view;
  }

  /**
   * Constructor  that has image window along with view.
   *
   * @param model the new model to be used
   * @param win   the new window to be used
   */
  public SimpleBatchCommands(MultiImageControl model, IWindow win) {
    if (model == null || win == null) {
      throw new IllegalArgumentException("invalid input");
    }

    this.model = model;
    this.win = win;
  }


  @Override
  public void readScript() throws IllegalArgumentException, IOException {
    Scanner scan = new Scanner(this.rd);
    while (scan.hasNext()) {
      String command = scan.nextLine();
      String[] commands = command.split(" ");
      if (commands[0].equals("quit") && commands.length == 1) {
        return;
      }
      if (!imageOperation(commands)) {
        try {
          basicImageProcess(commands);
        } catch (Exception e) {
          renderMessage(e.toString());
        }
      }
    }

    updateImage();
  }

  private boolean imageOperation(String[] commands) throws IOException {
    if (commands[0].equals("load") && commands.length == 2) {
      load(commands[1]);
      renderMessage("load success");
      return true;
    } else if (commands[0].equals("remove") && commands.length == 2) {
      try {
        model.removeImage(Integer.parseInt(commands[1]));
        renderMessage("remove success");
        return true;
      } catch (IllegalArgumentException e) {
        renderMessage("invalid layer");
      }
    } else if (commands[0].equals("saveAll") && commands.length == 2) {
      String savePath = commands[1];
      try {
        imageDecode(model.outputMultiImage(), savePath);
        renderMessage("saveAll success");
        return true;
      } catch (Exception e) {
        renderMessage("invalid save path");
      }
    } else if (commands[0].equals("invisible") && commands.length == 2) {

      try {
        model.invisibleSingleImage(Integer.parseInt(commands[1]));
        String state = "";
        for (Map.Entry<String, AImageModifyControl> pair : model.outputMultiImage().entrySet()) {
          boolean tran = pair.getKey().charAt(0) == '0';
          state += pair.getKey().substring(1) + ": " + tran + " ";
        }
        renderMessage("layer: " + commands[1] + " visible state change success " + state);
        return true;
      } catch (IllegalArgumentException e) {
        renderMessage("invalid layer");
      }
    } else if (commands[0].equals("saveTop") && commands.length == 2) {
      imageDecode(model.outputTopImage(), commands[1]);
      renderMessage("SaveTop success");
      return true;
    } else if (commands[0].equals("create") && commands.length == 3) {
      try {
        model.putImage(Integer.parseInt(commands[1]), profile.getImage(commands[2]));
        renderMessage("create image success");
        return true;
      } catch (Exception e) {
        renderMessage("invalid layer or path");
      }
    }
    return false;
  }

  private void updateImage() {
    if (win != null) {
      HashMap<String, AImageModifyControl> cache = model.outputTopImage();
      if (cache == null) {
        win.updateImage(null);
        return;
      }

      List<int[][]> rgb = new ArrayList<>(cache.get("-1").getColor());
      int height = rgb.get(0).length;
      int width = rgb.get(0)[0].length;
      BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
          int red = ensureValue(rgb.get(0)[y][x]);
          int green = ensureValue(rgb.get(1)[y][x]);
          int blue = ensureValue(rgb.get(2)[y][x]);
          Color c = new Color(red, green, blue);
          int color = c.getRGB();
          output.setRGB(x, y, color);
        }
      }
      win.updateImage(output);
    }
  }

  private int ensureValue(int i) {
    if (i < 0) {
      return 0;
    } else if (i > 255) {
      return 255;
    } else {
      return i;
    }
  }

  /**
   * Update the readable to add new command.
   *
   * @param rd the readable used
   */
  @Override
  public void updateReadable(Readable rd) {
    this.rd = rd;
  }

  /**
   * Get a specific layer according to the given int.
   *
   * @return the layer number of the layer returning
   */
  @Override
  public int getLayer() {
    return model.maxLayer();
  }

  private void load(String filePath) throws IOException {
    List<AImageModifyControl> cache = null;
    List<Boolean> tran = null;
    try {
      cache = profile.getImages(filePath);
      tran = profile.getTransparent(filePath);
    } catch (IllegalArgumentException e) {
      renderMessage("invalid load file");
      return;
    }
    if (model.maxLayer() != 0) {
      model = new MultiImageControlModel();
    }
    for (int i = 0; i < cache.size(); i++) {
      model.putImage(i, cache.get(i));
      if (tran.get(i)) {
        model.invisibleSingleImage(i);
      }
    }
  }


  private void imageDecode(HashMap<String, AImageModifyControl> encode, String path) {
    if (encode == null) {
      renderMessage("no Image");
    }
    if (encode == null || !encode.keySet().toArray()[0].equals("-1")
        && path.lastIndexOf("/") != path.length() - 1) {
      throw new IllegalArgumentException("invalid path");
    } else if (encode.keySet().toArray()[0].equals("-1")) {
      try {
        profile.outputAll(Arrays.asList(encode.get("-1")), path, null);
      } catch (Exception e) {
        renderMessage(e.toString());
      }
      return;
    }

    int[] tran = new int[encode.size()];
    AImageModifyControl[] images = new AImageModifyControl[encode.size()];
    for (Map.Entry<String, AImageModifyControl> pair : encode.entrySet()) {
      String inform = pair.getKey();
      tran[Integer.parseInt(inform.substring(1))] = inform.charAt(0) - '0';
      images[Integer.parseInt(inform.substring(1))] = pair.getValue();
    }
    List<Integer> intList = new ArrayList<Integer>(tran.length);
    for (int i : tran) {
      intList.add(i);
    }
    try {
      profile.outputAll(Arrays.asList(images), path, intList);
    } catch (Exception e) {
      renderMessage(e.toString());
    }
  }

  private void renderMessage(String message) {
    if (win != null) {
      win.renderMessage(message);

    } else if (view != null) {
      view.renderMessage(message);
    }
  }

  private void basicImageProcess(String[] commands) {
    if (commands.length != 2 && !commands[0].equals("mosaic")
        && !commands[0].equals("downscale")) {
      renderMessage("invalid command");
      return;
    }
    String command = commands[0];
    int layer = 0;
    try {
      layer = Integer.parseInt(commands[1]);
    } catch (Exception e) {
      renderMessage("invalid command");
      return;
    }
    if (command.contains("sepia")) {
      model.sepiaSingleImage(layer);
      renderMessage("sepia success");
    } else if (command.contains("grey")) {
      model.greySingleImage(layer);
      renderMessage("grey success");
    } else if (command.contains("blur")) {
      model.blurSingleImage(layer);
      renderMessage("blur success");
    } else if (command.contains("sharp")) {
      model.sharpSingleImage(layer);
      renderMessage("sharp success");
    } else if (command.contains("mosaic")) {
      model.mosaicSingleImage(layer, Integer.parseInt(commands[2]));
      renderMessage("mosaic " + Integer.parseInt(commands[2]) + " success");
    } else if (command.contains("downscale")) {
      for (int i = 0; i < model.maxLayer(); i++) {
        model.downscale(i, Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
      }
      renderMessage("downscale success");
    } else {
      renderMessage("invalid command");
    }
  }
}
