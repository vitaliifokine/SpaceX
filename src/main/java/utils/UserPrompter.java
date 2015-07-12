package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class designed to create a user prompt window.
 * This window created using Swing and Concurrency.
 * When user prompter to input main thread turns to sleep (joining)
 * until prompt not submitted. Prompt has timeout and suggestion message.
 * Designed to handle captcha manual input because Scanner not works when tests runs.
 */

public class UserPrompter extends JFrame{

    public UserPrompter() {
        createPrompt();
    }
    public UserPrompter(int timeoutSeconds) {
        if (timeoutSeconds >= 1 && timeoutSeconds <= 90) {
            setTimeout(timeoutSeconds);
            createPrompt();
        } else {
            System.err.println("WARNING: You must set timeout from 1 to 90 seconds. Reset timeout to default (15 seconds)");
            resetTimeoutDefault();
            createPrompt();
        }
    }
    public UserPrompter(int timeoutSeconds, String suggestion) {
        if (timeoutSeconds >= 1 && timeoutSeconds <= 90) {
            setTimeout(timeoutSeconds);
            setSuggestion(suggestion);
            createPrompt();
        } else {
            System.err.println("WARNING: You must set timeout from 1 to 90 seconds. Reset timeout to default (15 seconds)");
            resetTimeoutDefault();
            setSuggestion(suggestion);
            createPrompt();
        }
    }

    JButton submitButton;
    JTextField inputTextField;
    JLabel timeLeft;
    JLabel suggestion;
    JLabel hint;

    String suggestionMessage = "Please, enter your text below"; public void setSuggestion (String suggestion) {this.suggestionMessage = suggestion;}

    boolean isClicked = false; private void setClicked (boolean newStatus) {this.isClicked = newStatus;}

    private  String userInput = "";
    private  int timeout = 15;

    public void setTimeout (int seconds) {
        this.timeout = seconds;
    }
    public void resetTimeoutDefault() {
        this.timeout = 15;
    }
    public String getUserInput() {
        return userInput;
    }

    private void createPrompt () {



        this.setSize(210, 170);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int xPos = (screenSize.width / 2) - (this.getWidth() / 2);
        int yPos = (screenSize.height / 2) - (this.getHeight() / 2);
        this.setLocation(xPos, yPos);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String title = "Prompt";

        this.setTitle(title);
        JPanel thePanel = new JPanel();

        submitButton = new JButton("Submit");
        ListenForButton lForButton = new ListenForButton(this);
        submitButton.addActionListener(lForButton);

        inputTextField = new JTextField("Type Here", 15);
        ListenForMouse lForMouseAtInputField = new ListenForMouse();
        inputTextField.addMouseListener(lForMouseAtInputField);
        ListenForKeys lForKeysAInputField = new ListenForKeys();
        inputTextField.addKeyListener(lForKeysAInputField);



        timeLeft = new JLabel("TimeLeft: ");
        suggestion = new JLabel(suggestionMessage);
        hint = new JLabel("Press F1/F2 to swap letters");
        hint.setForeground(Color.GRAY);

        thePanel.add(suggestion);
        thePanel.add(inputTextField);
        thePanel.add(hint);
        thePanel.add(timeLeft);
        thePanel.add(submitButton);

        this.add(thePanel);
        this.setVisible(true);

        //this thread will not allow to continue program until captcha not entered and submit not clicked
        Thread captchaPrompt = new Thread(new Runnable() {
            public void run() {
                int secondsLeft;
                int counter = 0;
                int timeOutLimit = timeout;
                while ( !isClicked && counter < (timeOutLimit * 10) ) {
                    try {
                        Thread.sleep(100);
                        counter++;
                        secondsLeft = timeOutLimit - counter/10;
                        timeLeft.setText("Time left: " + secondsLeft + " seconds");
                        if (counter >= timeOutLimit * 10) {
                            inputTextField.setText("");
                            timeLeft.setText("Time is out. Closing window...");
                            submitButton.doClick();
                        }
                    } catch (InterruptedException e) {}
                }

            }
        });

        captchaPrompt.start();
        try {
            captchaPrompt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private class ListenForButton implements ActionListener {

        private ListenForButton () {

        }
        JFrame frame;
        private ListenForButton (JFrame frame) {
            this.frame = frame;
        }

        public void actionPerformed(ActionEvent e){

            if(e.getSource() == submitButton){

                userInput = inputTextField.getText();
                setClicked(true);
                if (frame != null) {
                    frame.dispose();
                } else {
                    System.exit(0);
                }
            }
        }



        }
    private class ListenForMouse  implements MouseListener {

        // Called when a mouse button is clicked

        public void mouseClicked(MouseEvent e) {

            if (e.getSource() == inputTextField && inputTextField.getText().equals("Type Here")) {
                inputTextField.setText("");
            }

        }

        // Called when the mouse enters the component assigned
        // the MouseListener

        public void mouseEntered(MouseEvent arg0) {
            // TODO Auto-generated method stub

        }

        // Called when the mouse leaves the component assigned
        // the MouseListener

        public void mouseExited(MouseEvent arg0) {
            // TODO Auto-generated method stub

        }

        // Mouse button pressed

        public void mousePressed(MouseEvent arg0) {
            // TODO Auto-generated method stub

        }

        // Mouse button released

        public void mouseReleased(MouseEvent arg0) {
            // TODO Auto-generated method stub

        }
    }
    private class ListenForKeys   implements KeyListener {

        // Handle the key typed event from the text field.
        public void keyTyped(KeyEvent e) {
            //textArea1.append("Key Hit: " + e.getKeyChar() + "\n");
        }

        // Handle the key-pressed event from the text field.
        public void keyPressed(KeyEvent e) {

            int sourceCode = e.getKeyCode();

            if (sourceCode == 112) { //f1
                inputTextField.setText(StringProcessor.swapRussianLettersToEng(inputTextField.getText()));
            }
            if (sourceCode == 113) { //f2
                inputTextField.setText(StringProcessor.swapEnglishLettersToRus(inputTextField.getText()));
            }

            if (sourceCode == 10) { // enter
                submitButton.doClick();
            }
            if (sourceCode != 10 && inputTextField.getText().equals("Type Here")) {
                inputTextField.setText("");

            }

            if (inputTextField.getText().length() > 20) {
                inputTextField.setText(inputTextField.getText().substring(0, 19));
            }
        }

        // Handle the key-released event from the text field.
        public void keyReleased(KeyEvent e) {
            if (inputTextField.getText().length() > 20) {
                inputTextField.setText(inputTextField.getText().substring(0, 19));
            }
        }

    }

}
