import static org.junit.Assert.assertEquals;

import control.SimpleBatchCommands;
import imageio.IoPPM;
import java.io.IOException;
import java.io.StringReader;
import multiimage.MultiImageControlModel;
import org.junit.Test;
import view.MyView;

/**
 * for test simpleBatchCommands class.
 */
public class CommandTest {

  @Test
  public void FilePathIllegalTest() throws IOException {
    Readable rd = new StringReader("load s");
    Appendable ap = new StringBuffer();

    SimpleBatchCommands cotrol = new SimpleBatchCommands(new MultiImageControlModel(),
        new MyView(ap), rd);
    cotrol.readScript();
    assertEquals(ap.toString(), "Please input your command:\n"
        + "create layer*, remove layer*, "
        + "load, saveTop path*, saveAll path*, image processing methods...\n"
        + "invalid load file");
  }

  @Test(expected = IllegalArgumentException.class)
  public void BuildCommandModelIllegalTest() throws IOException {
    Readable rd = new StringReader("");
    Appendable ap = new StringBuffer();
    SimpleBatchCommands cotrol = new SimpleBatchCommands(null, new MyView(ap), (Readable) null);
  }

  @Test
  public void CreatLayerTest() throws IOException {
    Readable rd = new StringReader("create 0 /Users/hzh-mac/Desktop/test/HW5/res/d.ppm\n");
    Appendable ap = new StringBuffer();
    SimpleBatchCommands cotrol = new SimpleBatchCommands(new MultiImageControlModel(),
        new MyView(ap), rd);
    cotrol.readScript();
    assertEquals(ap.toString(), "Please input your command:\n"
        + "create layer*, remove layer*, load, saveTop "
        + "path*, saveAll path*, image processing methods...\n");
  }

  @Test
  public void CreatLayerIllegalTest() throws IOException {
    Readable rd = new StringReader("create /Users/hzh-mac/Desktop/test/HW5/res/d.ppm\n");
    Appendable ap = new StringBuffer();
    SimpleBatchCommands cotrol = new SimpleBatchCommands(new MultiImageControlModel(),
        new MyView(ap), rd);
    cotrol.readScript();
    assertEquals(ap.toString(), "Please input your command:\n"
        + "create layer*, remove layer*, load, saveTop "
        + "path*, saveAll path*, image processing methods...\n"
        + "invalid command");
  }

  @Test
  public void RemoveLayerTest() throws IOException {
    Readable rd = new StringReader(
        "create 0 /Users/hzh-mac/Desktop/test/HW5/res/d.ppm\n" + "remove 0");
    Appendable ap = new StringBuffer();
    SimpleBatchCommands cotrol = new SimpleBatchCommands(new MultiImageControlModel(),
        new MyView(ap), rd);
    cotrol.readScript();
    assertEquals(ap.toString(), "Please input your command:\n"
        + "create layer*, remove layer*, load, saveTop path*, "
        + "saveAll path*, image processing methods...\n");
  }

  @Test
  public void CommandSepiaTest() throws IOException {
    Readable rd = new StringReader(
        "create 0 /Users/hzh-mac/Desktop/test/HW5/res/d.ppm\n" + "sepia 0");
    Appendable ap = new StringBuffer();
    SimpleBatchCommands cotrol = new SimpleBatchCommands(new MultiImageControlModel(),
        new MyView(ap), rd);
    cotrol.readScript();
    assertEquals(ap.toString(), "Please input your command:\n"
        + "create layer*, remove layer*, load, saveTop path*, "
        + "saveAll path*, image processing methods...\n"
        + "sepia success");
  }

  @Test
  public void ILLegalCommandTest() throws IOException {
    Readable rd = new StringReader(
        "create 0 /Users/hzh-mac/Desktop/test/HW5/res/d.ppm\n" + "transform 0");
    Appendable ap = new StringBuffer();
    SimpleBatchCommands cotrol = new SimpleBatchCommands(new MultiImageControlModel(),
        new MyView(ap), rd);
    cotrol.readScript();
    assertEquals(ap.toString(), "Please input your command:\n"
        + "create layer*, remove layer*, load, saveTop path*, "
        + "saveAll path*, image processing methods...\n"
        + "invalid command");
  }

  /**
   * Try to command image processing before creating layer.
   */
  @Test
  public void ILLegalCommandTest2() throws IOException {
    Readable rd = new StringReader("create 0 /Users/hzh-mac/Desktop/test/HW5/res/d.ppm\n" + "0");
    Appendable ap = new StringBuffer();
    SimpleBatchCommands cotrol = new SimpleBatchCommands(new MultiImageControlModel(),
        new MyView(ap), rd);
    cotrol.readScript();
    assertEquals(ap.toString(), "Please input your command:\n"
        + "create layer*, remove layer*, load, saveTop path*, "
        + "saveAll path*, image processing methods...\n"
        + "invalid command");
  }

  @Test
  public void RemoveLayerIllegalTest() throws IOException {
    Readable rd = new StringReader(
        "create 0 /Users/hzh-mac/Desktop/test/HW5/res/d.ppm\n" + "remove 1");
    Appendable ap = new StringBuffer();
    SimpleBatchCommands cotrol = new SimpleBatchCommands(new MultiImageControlModel(),
        new MyView(ap), rd);
    cotrol.readScript();
    assertEquals(ap.toString(), "Please input your command:\n"
        + "create layer*, remove layer*, load, saveTop path*, "
        + "saveAll path*, image processing methods...\n"
        + "invalid layer");
  }

  @Test
  public void outputToplayer() throws IOException {
    Readable rd = new StringReader(
        "create 0 /Users/hzh-mac/Desktop/test/HW5/res/d.ppm\n"
            + "create 1 /Users/hzh-mac/Desktop/test/HW5/res/checker.ppm\n"
            + "invisible 1\n"
            + "saveTop /Users/hzh-mac/Desktop/test/HW5/res/c.ppm");
    Appendable ap = new StringBuffer();
    SimpleBatchCommands cotrol = new SimpleBatchCommands(new MultiImageControlModel(),
        new MyView(ap), rd);
    cotrol.readScript();
    assertEquals(readPPM("/Users/hzh-mac/Desktop/test/HW5/res/a.ppm"), "P3\n"
        + "1 4\n"
        + "255\n"
        + "119\n"
        + "106\n"
        + "83\n"
        + "122\n"
        + "109\n"
        + "85\n"
        + "116\n"
        + "103\n"
        + "80\n"
        + "0\n"
        + "0\n"
        + "0\n");
  }

  @Test
  public void outputMultiAndLoad() throws IOException {
    Readable rd = new StringReader(
        "create 0 /Users/hzh-mac/Desktop/test/HW5/res/d.ppm\n"
            + "create 1 /Users/hzh-mac/Desktop/test/HW5/res/checker.ppm\n"
            + "saveAll /Users/hzh-mac/Desktop/test/HW5/\n"
            + "load /Users/hzh-mac/Desktop/test/HW5/record.txt\n"
            + "remove 1\n"
            + "saveTop /Users/hzh-mac/Desktop/test/HW5/e.ppm\n");
    Appendable ap = new StringBuffer();
    SimpleBatchCommands cotrol = new SimpleBatchCommands(new MultiImageControlModel(),
        new MyView(ap), rd);
    cotrol.readScript();
    assertEquals(readPPM("/Users/hzh-mac/Desktop/test/HW5/d.ppm"), "P3\n"
        + "10 6\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "140\n"
        + "0\n"
        + "46\n"
        + "186\n"
        + "0\n"
        + "140\n"
        + "186\n"
        + "24\n"
        + "164\n"
        + "164\n"
        + "72\n"
        + "118\n"
        + "118\n"
        + "72\n"
        + "72\n"
        + "118\n"
        + "24\n"
        + "24\n"
        + "164\n"
        + "0\n"
        + "46\n"
        + "186\n"
        + "0\n"
        + "140\n"
        + "186\n"
        + "0\n"
        + "140\n"
        + "140\n"
        + "0\n"
        + "46\n"
        + "186\n"
        + "8\n"
        + "100\n"
        + "240\n"
        + "24\n"
        + "179\n"
        + "225\n"
        + "48\n"
        + "188\n"
        + "203\n"
        + "80\n"
        + "126\n"
        + "172\n"
        + "72\n"
        + "87\n"
        + "179\n"
        + "24\n"
        + "70\n"
        + "225\n"
        + "8\n"
        + "100\n"
        + "240\n"
        + "24\n"
        + "179\n"
        + "225\n"
        + "24\n"
        + "164\n"
        + "164\n"
        + "0\n"
        + "140\n"
        + "186\n"
        + "24\n"
        + "179\n"
        + "225\n"
        + "72\n"
        + "164\n"
        + "179\n"
        + "80\n"
        + "126\n"
        + "172\n"
        + "48\n"
        + "63\n"
        + "203\n"
        + "24\n"
        + "70\n"
        + "225\n"
        + "8\n"
        + "148\n"
        + "240\n"
        + "24\n"
        + "179\n"
        + "225\n"
        + "72\n"
        + "164\n"
        + "179\n"
        + "72\n"
        + "118\n"
        + "118\n"
        + "24\n"
        + "164\n"
        + "164\n"
        + "48\n"
        + "188\n"
        + "203\n"
        + "80\n"
        + "126\n"
        + "172\n"
        + "72\n"
        + "87\n"
        + "179\n"
        + "24\n"
        + "70\n"
        + "225\n"
        + "8\n"
        + "100\n"
        + "240\n"
        + "24\n"
        + "179\n"
        + "225\n"
        + "48\n"
        + "188\n"
        + "203\n"
        + "80\n"
        + "126\n"
        + "172\n"
        + "72\n"
        + "72\n"
        + "118\n"
        + "72\n"
        + "118\n"
        + "118\n"
        + "80\n"
        + "126\n"
        + "172\n"
        + "48\n"
        + "63\n"
        + "203\n"
        + "24\n"
        + "70\n"
        + "225\n"
        + "8\n"
        + "148\n"
        + "240\n"
        + "24\n"
        + "179\n"
        + "225\n"
        + "72\n"
        + "164\n"
        + "179\n"
        + "80\n"
        + "126\n"
        + "172\n"
        + "48\n"
        + "63\n"
        + "203\n"
        + "24\n"
        + "24\n"
        + "164\n"
        + "72\n"
        + "72\n"
        + "72\n"
        + "72\n"
        + "72\n"
        + "118\n"
        + "24\n"
        + "24\n"
        + "164\n"
        + "0\n"
        + "46\n"
        + "186\n"
        + "0\n"
        + "140\n"
        + "186\n"
        + "24\n"
        + "164\n"
        + "164\n"
        + "72\n"
        + "118\n"
        + "118\n"
        + "72\n"
        + "72\n"
        + "118\n"
        + "24\n"
        + "24\n"
        + "164\n"
        + "0\n"
        + "0\n"
        + "140\n");

    assertEquals(readPPM("/Users/hzh-mac/Desktop/test/HW5/checker.ppm"), "P3\n"
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

    assertEquals(readPPM("/Users/hzh-mac/Desktop/test/HW5/e.ppm"), "P3\n"
        + "10 6\n"
        + "255\n"
        + "0\n"
        + "0\n"
        + "140\n"
        + "0\n"
        + "46\n"
        + "186\n"
        + "0\n"
        + "140\n"
        + "186\n"
        + "24\n"
        + "164\n"
        + "164\n"
        + "72\n"
        + "118\n"
        + "118\n"
        + "72\n"
        + "72\n"
        + "118\n"
        + "24\n"
        + "24\n"
        + "164\n"
        + "0\n"
        + "46\n"
        + "186\n"
        + "0\n"
        + "140\n"
        + "186\n"
        + "0\n"
        + "140\n"
        + "140\n"
        + "0\n"
        + "46\n"
        + "186\n"
        + "8\n"
        + "100\n"
        + "240\n"
        + "24\n"
        + "179\n"
        + "225\n"
        + "48\n"
        + "188\n"
        + "203\n"
        + "80\n"
        + "126\n"
        + "172\n"
        + "72\n"
        + "87\n"
        + "179\n"
        + "24\n"
        + "70\n"
        + "225\n"
        + "8\n"
        + "100\n"
        + "240\n"
        + "24\n"
        + "179\n"
        + "225\n"
        + "24\n"
        + "164\n"
        + "164\n"
        + "0\n"
        + "140\n"
        + "186\n"
        + "24\n"
        + "179\n"
        + "225\n"
        + "72\n"
        + "164\n"
        + "179\n"
        + "80\n"
        + "126\n"
        + "172\n"
        + "48\n"
        + "63\n"
        + "203\n"
        + "24\n"
        + "70\n"
        + "225\n"
        + "8\n"
        + "148\n"
        + "240\n"
        + "24\n"
        + "179\n"
        + "225\n"
        + "72\n"
        + "164\n"
        + "179\n"
        + "72\n"
        + "118\n"
        + "118\n"
        + "24\n"
        + "164\n"
        + "164\n"
        + "48\n"
        + "188\n"
        + "203\n"
        + "80\n"
        + "126\n"
        + "172\n"
        + "72\n"
        + "87\n"
        + "179\n"
        + "24\n"
        + "70\n"
        + "225\n"
        + "8\n"
        + "100\n"
        + "240\n"
        + "24\n"
        + "179\n"
        + "225\n"
        + "48\n"
        + "188\n"
        + "203\n"
        + "80\n"
        + "126\n"
        + "172\n"
        + "72\n"
        + "72\n"
        + "118\n"
        + "72\n"
        + "118\n"
        + "118\n"
        + "80\n"
        + "126\n"
        + "172\n"
        + "48\n"
        + "63\n"
        + "203\n"
        + "24\n"
        + "70\n"
        + "225\n"
        + "8\n"
        + "148\n"
        + "240\n"
        + "24\n"
        + "179\n"
        + "225\n"
        + "72\n"
        + "164\n"
        + "179\n"
        + "80\n"
        + "126\n"
        + "172\n"
        + "48\n"
        + "63\n"
        + "203\n"
        + "24\n"
        + "24\n"
        + "164\n"
        + "72\n"
        + "72\n"
        + "72\n"
        + "72\n"
        + "72\n"
        + "118\n"
        + "24\n"
        + "24\n"
        + "164\n"
        + "0\n"
        + "46\n"
        + "186\n"
        + "0\n"
        + "140\n"
        + "186\n"
        + "24\n"
        + "164\n"
        + "164\n"
        + "72\n"
        + "118\n"
        + "118\n"
        + "72\n"
        + "72\n"
        + "118\n"
        + "24\n"
        + "24\n"
        + "164\n"
        + "0\n"
        + "0\n"
        + "140\n");
  }

  private String readPPM(String path) throws IOException {
    IoPPM read = new IoPPM(path,
        "/Users/hzh-mac/Desktop/test/HW5/res/c.ppm");
    String s = "";
    while (!read.isEnd()) {
      s += read.readLine() + '\n';
    }
    read.ioClose();
    return s;
  }

}
