import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class FactorialApplet extends Applet implements ActionListener {
    TextField input, result;
    Button compute;

    public void init() {
        input = new TextField(10);
        result = new TextField(20);
        result.setEditable(false);
        compute = new Button("Compute");
        compute.addActionListener(this);

        add(new Label("Enter number:"));
        add(input);
        add(compute);
        add(new Label("Factorial:"));
        add(result);
    }

    public void actionPerformed(ActionEvent e) {
        int num = Integer.parseInt(input.getText());
        int fact = 1;
        for (int i = 1; i <= num; i++)
            fact *= i;

        result.setText(String.valueOf(fact));
    }
}
