package view;

import control.CommandModel;
import control.SimpleBatchCommands;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.StringReader;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import multiimage.MultiImageControlModel;

/**
 * A concrete window class that implements window. This class enable us to show user view with
 * images as long as keeping the functions of views. The user interface expose all the features of
 * the program (blurring, sharpening, greyscale, sepia, layer operations, saving and loading images,
 * loading and executing a script from a file). Users use buttons to interact with the view.
 */
public class MyWindow extends JFrame implements IWindow, ActionListener {

  private JComboBox layer;
  private JPanel main;
  private JButton create;
  private JComboBox createLayer;
  private JButton delete;
  private JButton visible;

  private JLabel message;

  private CommandModel control;
  private JButton save;
  private JButton saveAll;
  private JButton load;
  private JButton apply;
  private JComboBox effect;
  private JLabel imageLabel;
  private JButton mosaic;
  private JTextField jt;
  private JTextField height;
  private JTextField width;
  private JButton downscale;

  /**
   * Empty constructor for MyWindow. Self- setup all the params.
   */
  public MyWindow() {
    super();
    control = new SimpleBatchCommands(new MultiImageControlModel(), this);
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.setTitle("Image Postprocessing");
    setSize(720, 480);
    main = new JPanel();
    main.setLayout(new BorderLayout(5, 5));
    JScrollPane mainScrollPane = new JScrollPane(main);
    add(mainScrollPane);

    topPanel();

    centralPanel();

    rightPanel();

    buttomPanel();

    addFunction();
    pack();
    setVisible(true);
  }

  private void buttomPanel() {
    message = new JLabel("Message shows up here");
    message.setFont(new java.awt.Font("Dialog", 2, 20));
    message.setForeground(Color.red);
    main.add(message, BorderLayout.SOUTH);
  }

  private void topPanel() {
    JPanel top = new JPanel();
    top.setLayout(new FlowLayout());
    main.add(top, BorderLayout.NORTH);

    JLabel createLayerLab = new JLabel("create layer position:");
    createLayer = new JComboBox();
    create = new JButton("create layer");
    create.setActionCommand("create");
    createLayer.setModel(new DefaultComboBoxModel());
    refreshLayer(createLayer, true);
    top.add(createLayerLab);
    top.add(createLayer);
    top.add(create);

    JLabel bindLayer = new JLabel("  choose layer:");
    layer = new JComboBox();
    layer.setModel(new DefaultComboBoxModel());
    refreshLayer(layer, false);
    top.add(bindLayer);
    top.add(layer);

    delete = new JButton("delete");
    delete.setActionCommand("remove");
    top.add(delete);

    visible = new JButton("visible layer");
    visible.setActionCommand("invisible");
    top.add(visible);

    top.add(new JLabel("      "));
    load = new JButton("load");
    load.setActionCommand("load");
    top.add(load);

    save = new JButton("saveTop");
    save.setActionCommand("saveTop");
    top.add(save);

    saveAll = new JButton("saveAll");
    saveAll.setActionCommand("saveAll");
    top.add(saveAll);
  }

  private void rightPanel() {
    JPanel right = new JPanel();
    right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

    JLabel lab0 = new JLabel("process");
    lab0.setAlignmentX(Component.RIGHT_ALIGNMENT);
    right.add(lab0);
    right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

    effect = new JComboBox();
    effect.setAlignmentX(Component.RIGHT_ALIGNMENT);
    effect.setModel(new DefaultComboBoxModel());
    effect.addItem("blur");
    effect.addItem("sharp");
    effect.addItem("grey");
    effect.addItem("sepia");
    effect.setMaximumSize(effect.getPreferredSize());
    right.add(effect);

    apply = new JButton("apply");
    apply.setActionCommand("apply");
    apply.setAlignmentX(Component.RIGHT_ALIGNMENT);
    apply.setMaximumSize(apply.getPreferredSize());
    right.add(apply);

    right.add(new JLabel("\n"));
    right.add(new JLabel("\n"));
    JLabel lab1 = new JLabel("mosaic seeds:");
    lab1.setAlignmentX(Component.RIGHT_ALIGNMENT);
    right.add(lab1);
    jt = new JTextField(5);
    jt.setAlignmentX(Component.RIGHT_ALIGNMENT);
    jt.setMaximumSize(jt.getPreferredSize());
    right.add(jt);

    mosaic = new JButton("mosaic");
    mosaic.setActionCommand("mosaic");
    mosaic.setAlignmentX(Component.RIGHT_ALIGNMENT);
    mosaic.setMaximumSize(mosaic.getPreferredSize());
    right.add(mosaic);

    right.add(new JLabel("\n"));
    right.add(new JLabel("\n"));
    JLabel lab2 = new JLabel("downscale height :");
    lab2.setAlignmentX(Component.RIGHT_ALIGNMENT);
    right.add(lab2);
    height = new JTextField(10);
    height.setMaximumSize(height.getPreferredSize());
    height.setAlignmentX(Component.RIGHT_ALIGNMENT);
    right.add(height);
    JLabel lab3 = new JLabel("downscale height :");
    lab3.setAlignmentX(Component.RIGHT_ALIGNMENT);
    right.add(lab3);
    width = new JTextField(10);
    width.setAlignmentX(Component.RIGHT_ALIGNMENT);
    width.setMaximumSize(width.getPreferredSize());
    right.add(width);

    downscale = new JButton("downscale");
    downscale.setActionCommand("downscale");
    downscale.setAlignmentX(Component.RIGHT_ALIGNMENT);
    downscale.setMaximumSize(downscale.getPreferredSize());
    right.add(downscale);

    main.add(right, BorderLayout.EAST);
  }

  private void centralPanel() {
    JPanel image = new JPanel();
    image.setBorder(BorderFactory.createTitledBorder("top layer"));
    image.setLayout(new FlowLayout());
    main.add(image, BorderLayout.CENTER);

    imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageScrollPane.setPreferredSize(new Dimension(1080, 720));
    image.add(imageScrollPane);
  }

  private void addFunction() {
    create.addActionListener(this);
    delete.addActionListener(this);
    save.addActionListener(this);
    saveAll.addActionListener(this);
    load.addActionListener(this);
    visible.addActionListener(this);
    apply.addActionListener(this);
    mosaic.addActionListener(this);
    downscale.addActionListener(this);
  }


  /**
   * for customer all action.
   *
   * @param e action event
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("create")) {
      String path = openFile(JFileChooser.FILES_ONLY, null);
      if (path != null) {
        operate("create " + createLayer.getSelectedIndex() + " " + path);
        refreshLayer(createLayer, true);
        refreshLayer(layer, false);
      }
    } else if (e.getActionCommand().equals("remove")) {
      operate("remove " + layer.getSelectedIndex());
      refreshLayer(layer, false);
      refreshLayer(createLayer, true);

    } else if (e.getActionCommand().equals("invisible")) {
      operate("invisible " + layer.getSelectedIndex());

    } else if (e.getActionCommand().equals("saveTop")) {
      String path = saveFile(JFileChooser.FILES_ONLY);
      if (path != null) {
        operate("saveTop " + path);
      }

    } else if (e.getActionCommand().equals("saveAll")) {
      String path = openFile(JFileChooser.DIRECTORIES_ONLY, null);
      if (path != null) {
        operate("saveAll " + path + "/");
      }
    } else if (e.getActionCommand().equals("load")) {
      String path = openFile(JFileChooser.FILES_ONLY, "txt");
      if (path != null) {
        operate("load " + path);
        refreshLayer(createLayer, true);
        refreshLayer(layer, false);
      }
    } else if (e.getActionCommand().equals("apply")) {
      int topLayer = layer.getItemCount() - 1;
      operate(effect.getSelectedItem() + " " + topLayer);
    } else if (e.getActionCommand().equals("mosaic")) {
      int topLayer = layer.getItemCount() - 1;
      operate("mosaic" + " " + topLayer + " " + jt.getText());
    } else if (e.getActionCommand().equals("downscale")) {
      operate("downscale" + " " + height.getText() + " " + width.getText());
    }
  }

  private void refreshLayer(JComboBox box, Boolean create) {
    box.removeAllItems();
    for (int i = 0; i < control.getLayer(); i++) {
      box.addItem(i + "");
    }
    if (create) {
      box.addItem(control.getLayer() + "");
    }

    for (int i = 0; i < box.getItemCount() - 1; i++) {
      if (box.getItemAt(i).equals(box.getItemAt(i + 1))) {
        box.removeItemAt(i);
        break;
      }
    }
  }

  private String openFile(int mode, String fileType) {
    JFileChooser fileChooser = new JFileChooser();
    if (fileType != null) {
      fileChooser.setFileFilter(new FileNameExtensionFilter(fileType, fileType));
    }
    if (mode != -1) {
      fileChooser.setFileSelectionMode(mode);
    }
    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.CANCEL_OPTION) {
      return null;
    }
    return fileChooser.getSelectedFile().getAbsolutePath();
  }

  private String saveFile(int mode) {
    JFileChooser fileChooser = new JFileChooser();
    if (mode != -1) {
      fileChooser.setFileSelectionMode(mode);
    }
    int result = fileChooser.showSaveDialog(this);
    if (result == JFileChooser.CANCEL_OPTION) {
      return null;
    }
    return fileChooser.getSelectedFile().getAbsolutePath();
  }

  /**
   * To render messages to view according to the given string.
   *
   * @param mess the message string to be updated
   */
  public void renderMessage(String mess) {
    message.setText(mess);
  }

  /**
   * Update the image to the window according to the given image.
   *
   * @param im the given image
   */
  @Override
  public void updateImage(BufferedImage im) {
    ImageIcon icon = im == null ? null : new ImageIcon(im);
    imageLabel.setIcon(icon);
  }

  private void operate(String command) {
    control.updateReadable(new StringReader(command));
    try {
      control.readScript();
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
