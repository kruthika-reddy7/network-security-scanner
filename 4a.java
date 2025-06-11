import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DivisionGUI extends JFrame implements ActionListener {
    // Components
    private JTextField num1Field, num2Field, resultField;
    private JButton divideButton;

    public DivisionGUI() {
        // Set up frame
        setTitle("Integer Division");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        // Labels and text fields
        add(new JLabel("Num1:"));
        num1Field = new JTextField();
        add(num1Field);

        add(new JLabel("Num2:"));
        num2Field = new JTextField();
        add(num2Field);

        add(new JLabel("Result:"));
        resultField = new JTextField();
        resultField.setEditable(false);
        add(resultField);

        // Button
        divideButton = new JButton("Divide");
        divideButton.addActionListener(this);
        add(divideButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int num1 = Integer.parseInt(num1Field.getText());
            int num2 = Integer.parseInt(num2Field.getText());

            if (num2 == 0) {
                throw new ArithmeticException("Cannot divide by zero");
            }

            int result = num1 / num2;
            resultField.setText(String.valueOf(result));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Please enter valid integers!",
                "Number Format Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (ArithmeticException ex) {
            JOptionPane.showMessageDialog(this,
                "Division Error: " + ex.getMessage(),
                "Arithmetic Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new DivisionGUI();
    }
}