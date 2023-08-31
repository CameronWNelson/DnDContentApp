import java.awt.*;
import java.awt.event.*;

public class SpellWriter {
    
    private Frame frame;

    public SpellWriter()
    {
        System.out.println("Creating SpellWriter.");
        frame = new Frame("Spell Writer");
        frame.setLayout(new GridBagLayout());

        Label label = new Label("Spell:");
        frame.add(label, generateGridBagConstraints(0, 0, 1, 1));

        TextField field = new TextField(20);
        frame.add(field, generateGridBagConstraints(1, 0, 1, 1));

        label = new Label("Level:");
        frame.add(label, generateGridBagConstraints(0, 1, 1, 1));

        field = new TextField(20);
        frame.add(field, generateGridBagConstraints(1, 1, 1, 1));

        label = new Label("School:");
        frame.add(label, generateGridBagConstraints(0, 2, 1, 1));

        field = new TextField(20);
        frame.add(field, generateGridBagConstraints(1, 2, 1, 1));
    

        label = new Label("Ritual:");
        frame.add(label, generateGridBagConstraints(0, 3, 1, 1));
        Checkbox checkbox = new Checkbox("");
        frame.add(checkbox, generateGridBagConstraints(1, 3, 1, 1));

        label = new Label("Concentration:");
        frame.add(label, generateGridBagConstraints(0, 4, 1, 1));
        checkbox = new Checkbox("");
        frame.add(checkbox, generateGridBagConstraints(1, 4, 1, 1));

        label = new Label("Verbal:");
        frame.add(label, generateGridBagConstraints(0, 5, 1, 1));
        checkbox = new Checkbox("");
        frame.add(checkbox, generateGridBagConstraints(1, 5, 1, 1));

        label = new Label("Somatic:");
        frame.add(label, generateGridBagConstraints(0, 6, 1, 1));
        checkbox = new Checkbox("");
        frame.add(checkbox, generateGridBagConstraints(1, 6, 1, 1));

        label = new Label("Material:");
        frame.add(label, generateGridBagConstraints(0, 7, 1, 1));
        checkbox = new Checkbox("");
        frame.add(checkbox, generateGridBagConstraints(1, 7, 1, 1));

        

        



        frame.setSize(400, 800);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {
            System.exit(0);
           } 
        });
    }

    public static GridBagConstraints generateGridBagConstraints(int x, int y, int width, int height)
    {
        return generateGridBagConstraints(x, y, width, height, GridBagConstraints.BOTH, new Insets(2, 5, 2, 5));
    }

    public static GridBagConstraints generateGridBagConstraints(int x, int y, int width, int height, int fill, Insets insets)
    {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = x;
        gridBagConstraints.gridy = y;
        gridBagConstraints.gridwidth = width;
        gridBagConstraints.gridheight = height;
        gridBagConstraints.fill = fill;
        gridBagConstraints.insets = insets;
        return gridBagConstraints;
    }

    public static void main(String[] args)
    {
        SpellWriter sw = new SpellWriter();
    }
}
