import java.io.IOException;
import java.io.InputStreamReader;
import view.MyView;
import view.MyWindow;

/**
 * main class for begin whole software.
 */
public class Main {

  /**
   * main method for run whole software.
   *
   * @param args input inform.
   * @throws IOException read path error.
   */
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      return;
    }
    control.CommandModel co = null;
    multiimage.MultiImageControl multi = new multiimage.MultiImageControlModel();
    if (args[0].equals("-interactive")) {
      MyWindow my = new MyWindow();
    } else if (args[0].equals("-text")) {
      Readable reader = new InputStreamReader(System.in);
      co = new control.SimpleBatchCommands(multi, new MyView(null), reader);
      co.readScript();
    } else if (args[0].equals("-script")) {
      co = new control.SimpleBatchCommands(multi, null, args[1]);
      co.readScript();
    }
  }

}
