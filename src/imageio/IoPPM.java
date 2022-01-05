package imageio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * IoPPM contain any write and read operation about ppm image. This class can read ppm and write ppm
 * simultaneously.
 */
public class IoPPM {

  private String outPath;
  private FileWriter writer;
  private String filePath;
  private Scanner sc;

  /**
   * Constructor for IoPPM.
   *
   * @param filePath file we need to read
   * @param outPath  path we want to output
   * @throws IllegalArgumentException filepath empty
   * @throws IOException              scanner generated wrong
   */
  public IoPPM(String filePath, String outPath) throws IllegalArgumentException, IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("filePath can not be empty");
    }
    this.filePath = filePath;
    this.outPath = outPath;
    this.sc = this.newScanner();
  }

  /**
   * Easy constructor for IoPPM.
   *
   * @param outPath path we want to output
   * @throws IllegalArgumentException filepath empty
   * @throws IOException              output file path wrong
   */
  public IoPPM(String outPath) throws IllegalArgumentException, IOException {
    this.outPath = outPath;
    File output = new File(this.outPath);
  }

  private Scanner newScanner() throws FileNotFoundException {
    return new Scanner(new File(this.filePath));
  }

  /**
   * Create new a new file within the given path, throw IllegalArgumentException when having illegal
   * file path.
   *
   * @throws IllegalArgumentException wrong path
   */
  public void createFile() throws IllegalArgumentException {
    if (this.outPath == null) {
      throw new IllegalArgumentException("outPath empty");
    }
    File output = new File(this.outPath);
    try {
      output.createNewFile();
      this.writer = new FileWriter(output);
    } catch (IOException e) {
      throw new IllegalArgumentException("wrong path");
    }
  }

  /**
   * The validator for the end of this source.
   *
   * @return whether is source has a next line.
   */
  public Boolean isEnd() {

    if (this.sc == null) {
      throw new IllegalArgumentException("input path empty");
    }
    return !this.sc.hasNextLine();
  }

  /**
   * Reader for the next line of this source.
   *
   * @return the following content.
   */
  public String readLine() {
    if (this.sc == null) {
      throw new IllegalArgumentException("input path empty");
    }
    return this.sc.nextLine();
  }

  /**
   * Writer method with auto /n.
   *
   * @param n output String
   * @throws IOException can not find writer
   */
  public void writerFile(String n) throws IOException {
    if (this.outPath == null) {
      throw new IllegalArgumentException("outPath empty");
    }
    this.writer.write(n + "\n");
  }

  /**
   * Close the file after reading and writing.
   *
   * @throws IOException can not find writer
   */
  public void ioClose() throws IOException {
    if (this.sc != null) {
      this.sc.close();
    }
    if (this.writer != null) {
      this.writer.close();
    }
  }
}
