import java.awt.*;
import java.awt.event.*;

public class SpellWriter {
    
    private Frame frame;

    public SpellWriter()
    {
        System.out.println("Creating SpellWriter.");
        frame = new Frame("Spell Writer");
        frame.setLayout(new FlowLayout());

        Label label = new Label("Test Label");
        frame.add(label);

        frame.setSize(300, 150);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {
            System.exit(0);
           } 
        });
    }

    public static void main(String[] args)
    {
        SpellWriter sw = new SpellWriter();
    }
}
