package dev.macrobug.asmodeo.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.*;

public class Gui implements AbstractUI {

  private JFrame frmAsmodeo;

  /**
   * Create the application.
   */
  public Gui() {
    SplashScreen splash = SplashScreen.getSplashScreen();
    initialize();
    if(splash!=null) splash.close();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frmAsmodeo = new JFrame();
    frmAsmodeo.setTitle("Asmodeo");
    frmAsmodeo.setIconImage(Toolkit.getDefaultToolkit().getImage(Gui.class.getResource("/images/logo.gif")));
    frmAsmodeo.setBounds(100, 100, 450, 300);
    frmAsmodeo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frmAsmodeo.getContentPane().setLayout(new BoxLayout(frmAsmodeo.getContentPane(), BoxLayout.Y_AXIS));
    
    SaveOnExitListener soel=new SaveOnExitListener();
    frmAsmodeo.addWindowListener(soel);
    
    Box horizontalBox = Box.createHorizontalBox();
    frmAsmodeo.getContentPane().add(horizontalBox);
    
    JComboBox<JCoder> comboBox = new JComboBox<JCoder>();
    horizontalBox.add(comboBox);
    
    JButton button = new JButton("+");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      }
    });
    button.setToolTipText("add new alphabet");
    horizontalBox.add(button);
    
    JTextArea txtrEncodetext = new JTextArea();
    frmAsmodeo.getContentPane().add(txtrEncodetext);
    
    JProgressBar progressBar = new JProgressBar();
    frmAsmodeo.getContentPane().add(progressBar);
    
    JTextArea txtrDecodedtext = new JTextArea();
    frmAsmodeo.getContentPane().add(txtrDecodedtext);
    
    JMenuBar menuBar = new JMenuBar();
    frmAsmodeo.setJMenuBar(menuBar);
    
    JMenu mnFile = new JMenu("File");
    mnFile.setMnemonic('f');
    menuBar.add(mnFile);
    
    JMenu mnImport = new JMenu("Load");
    mnImport.setIcon(new ImageIcon(Gui.class.getResource("/javax/swing/plaf/metal/icons/ocean/hardDrive.gif")));
    mnImport.setMnemonic('l');
    mnFile.add(mnImport);
    
    JMenuItem mntmLoadIntoEncoded = new JMenuItem("Load encoded text");
    mntmLoadIntoEncoded.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
    mnImport.add(mntmLoadIntoEncoded);
    
    JMenuItem mntmLoadDecodedText = new JMenuItem("Load decoded text");
    mntmLoadDecodedText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
    mnImport.add(mntmLoadDecodedText);
    
    JMenu mnExport = new JMenu("Save");
    mnExport.setIcon(new ImageIcon(Gui.class.getResource("/javax/swing/plaf/metal/icons/ocean/floppy.gif")));
    mnExport.setMnemonic('s');
    mnFile.add(mnExport);
    
    JMenuItem mntmSaveEncodedText = new JMenuItem("Save encoded text");
    mntmSaveEncodedText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
    mnExport.add(mntmSaveEncodedText);
    
    JMenuItem mntmSaveDecodedText = new JMenuItem("Save decoded text");
    mntmSaveDecodedText.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
    mnExport.add(mntmSaveDecodedText);
    
    JMenuItem mntmClose = new JMenuItem("Quit");
    mntmClose.setIcon(new ImageIcon(Gui.class.getResource("/javax/swing/plaf/metal/icons/ocean/close.gif")));
    mntmClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
    mntmClose.addActionListener(soel);
    mnFile.add(mntmClose);
    
    JMenu mnTools = new JMenu("Tools");
    mnTools.setMnemonic('t');
    menuBar.add(mnTools);
    
    JMenuItem mntmEncode = new JMenuItem("Encode");
    mntmEncode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
    mnTools.add(mntmEncode);
    
    JMenuItem mntmDecode = new JMenuItem("Decode");
    mntmDecode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
    mnTools.add(mntmDecode);
    
    JMenu mnHelp = new JMenu("Help");
    mnHelp.setMnemonic('h');
    menuBar.add(mnHelp);
    
    JMenuItem mntmAbout = new JMenuItem("About");
    mntmAbout.setIcon(new ImageIcon(Gui.class.getResource("/images/help.gif")));
    mntmAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
    mnHelp.add(mntmAbout);
  }

  @Override
  public void start(String[] args) {
    frmAsmodeo.setVisible(true);
  }
  
  private class SaveOnExitListener implements WindowListener, ActionListener{
    private File getFile() throws IOException{
      File fTry=new File(System.getProperty("user.home"), ".macrobug/asmodeo/data");
      if(!fTry.exists())
        fTry.createNewFile();
      return fTry;
    }
    
    private void save(JFrame frame){
      try{
        File file=getFile();
        ObjectOutputStream output = null;
        output = new ObjectOutputStream(new FileOutputStream(file));
        output.writeObject(frame);
        output.close();
      }catch (IOException x){
        // TODO da fare
      }catch(NullPointerException n){
        // TODO da fare
      }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      JFrame frame=(JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
      save(frame);
      frame.setVisible(false);
      frame.dispose();
      System.exit(0);
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
      save((JFrame) e.getWindow());
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
  }
}
