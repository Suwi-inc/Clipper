import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
public class Clipper extends JFrame {
    private JPanel buttonPanel,textPanel;
    private Button newSnip;
    private  JButton longSnip;
    private  JPanel mainPanel;
    private JTextComponent test;

    private JTextComponent answer;
    private ScreenCapture screenCapture;
    private ImageIcon addIcon;
    private Color selectionColor;

    private Thread thread;

    public Clipper(){

        addIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Icons/plusL.png")));
        selectionColor = Color.RED;
        screenCapture = new ScreenCapture(selectionColor);
        thread = new Thread(() ->
        {
            screenCapture.captureImage();
        });
        this.setPreferredSize(new Dimension(500,500));
        this.setTitle("Clip Solver");
        this.setIconImage(addIcon.getImage());

        this.setLayout(new FlowLayout(FlowLayout.CENTER));

        buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.decode("#E2ECF2"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        newSnip = new Button("New Snip", addIcon,this::newSnipAction);
        longSnip = new JButton();
        longSnip.setPreferredSize(new Dimension(400,50));

        int borderWidth = 1;
        longSnip.setBorder(new LineBorder(Color.decode("#BBD7D7"), borderWidth, true));
        longSnip.setFocusPainted(false); //
      //  longSnip.addActionListener(this::closeAction);
        buttonPanel.add(newSnip);
        buttonPanel.add(longSnip);
        // Define the panel to hold the text fields
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));

        test = new JTextArea();
        answer = new JTextArea();

        test.setPreferredSize(new Dimension(240,400));
        test.setBackground(Color.decode("#E2ECF2"));

        answer.setPreferredSize(new Dimension(240,400));
        answer.setBackground(Color.decode("#E2ECF2"));

        mainPanel.add(test);
        mainPanel.add(answer);

        this.add(buttonPanel,BorderLayout.NORTH);
        this.add(mainPanel,BorderLayout.CENTER);
        this.pack();
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thread = null;
                super.windowClosing(e);
            }
        });
    }

    public void newSnipAction(ActionEvent e) {
        // create thread and capture image throw it;
        thread = new Thread(() -> {
            screenCapture = new ScreenCapture(selectionColor);
            screenCapture.captureImage();
        });
        thread.start();
    }
    public void closeAction(ActionEvent e) {
        thread = null;
        this.dispose();
    }

    public void showAndWait() {
        // show;
        this.setVisible(true);

        // wait;
        holdThread();
    }
    public void setResultText(String text)
    {
        this.test.setText(text);
    }

    public ScreenCapture getScreenCapture() {
        return screenCapture;
    }

    public void setSelectionColor(Color selectionColor) {
        this.selectionColor = selectionColor;
    }

    // hold current thread here;
    // until snipped_tool is open;
    private void holdThread()
    {
        while(thread != null) {
            try {
                ScreenCapture capture = this.getScreenCapture();
                if(capture.isImageCaptured()) {
                    String fileName = "Clipimage.png";
                    ImageIO.write(capture.getImage(), "png", new File(fileName));
                    String output = ExtractText.getText(fileName);
                    this.setResultText(output);
                }
                Thread.sleep(1);
            }
            catch (InterruptedException e) {} catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}