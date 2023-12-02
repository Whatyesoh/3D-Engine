import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EngineMain extends JFrame{

    static JFrame frame = new EngineMain();
    Screen ScreenObject = new Screen();

    public EngineMain() {
        add(ScreenObject);
        setUndecorated(true);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        getContentPane().setCursor(blankCursor);
    }
    public static void main(String [] args) {
    }
}