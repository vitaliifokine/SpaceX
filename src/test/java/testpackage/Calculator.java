package testpackage;


import javax.swing.*;
import java.awt.*;

public class Calculator {

    private JFrame window;
    private JPanel mainPanel, inputPanel, outputPanel, buttonPanel;
    private JButton jbtnAdd, jbtnSubtract, jbtnMultiply, jbtnIntDivide, jbtnRealDivide, jbtnModulo, jbtnReset, jbtnExit;
    private JLabel jlblOut, jlblNum1Caption, jlblNum2Caption;
    private JTextField jtxtNum1, jtxtNum2;

    public Calculator(){
        window = new JFrame("Калькулятор");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setResizable(false);

        mainPanel = new JPanel(new BorderLayout());
        inputPanel = new JPanel();
        outputPanel = new JPanel();
        buttonPanel = new JPanel();

        inputPanel.setLayout(new GridLayout(1,4));


    }
}

