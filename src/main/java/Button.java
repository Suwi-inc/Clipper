import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Button extends JButton {
    private static final int BUTTON_SIZE = 10;
    private static final int H = 50;
    private static final int W = 50;

Button(String text, ImageIcon icon, ActionListener listener)
{
    //super(text);
    this.setPreferredSize(new Dimension(W, H));
    this.setContentAreaFilled(false);
   // this.setBorder();
    resizeIcon(icon,W,H);
    System.out.println(icon.getIconHeight()+" "+icon.getIconWidth());
    //this.setIcon(icon);
    this.addActionListener(listener);
    this.setMargin(new Insets(10, 20, 10, 20));
    this.setOpaque(false);
   // this.setBackground(Color.cyan);

    this.setFocusable(false);


}
    private void resizeIcon(ImageIcon icon,int W, int H) {

        Image img = icon.getImage();
        img = img.getScaledInstance(W, H, Image.SCALE_DEFAULT);
        this.setIcon(new ImageIcon(img));
    }

}
