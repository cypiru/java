package finale;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Stack;

public class Solution {

    public static String decodedString(String s) {
        Stack<Integer> countStack = new Stack<>();
        Stack<String> resultStack = new Stack<>();
        StringBuilder currentString = new StringBuilder();
        int index = 0;

        while (index < s.length()) {
            char ch = s.charAt(index);

            if (Character.isDigit(ch)) {
                int count = 0;
                while (Character.isDigit(s.charAt(index))) {
                    count = count * 10 + (s.charAt(index) - '0');
                    index++;
                }
                countStack.push(count);
            } else if (ch == '[') {
                resultStack.push(currentString.toString());
                currentString = new StringBuilder();
                index++;
            } else if (ch == ']') {
                StringBuilder decodedString = new StringBuilder(resultStack.pop());
                int repeatCount = countStack.pop();
                for (int i = 0; i < repeatCount; i++) {
                    decodedString.append(currentString);
                }
                currentString = decodedString;
                index++;
            } else {
                currentString.append(ch);
                index++;
            }
        }
        return currentString.toString();
    }

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Hello! Welcome to the String Decoder Application!", 
                                      "Welcome", JOptionPane.INFORMATION_MESSAGE);

        JFrame frame = new JFrame("Enhanced String Decoder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JLabel inputLabel = new JLabel("Enter Encoded String:");
        JTextField inputField = new JTextField(20);
        JButton decodeButton = new JButton("Decode");
        JButton uploadButton = new JButton("Load from File");
        JButton exitButton = new JButton("Exit");
        JTextArea outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 5, 5));
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(uploadButton);
        inputPanel.add(decodeButton);

        JPanel outputPanel = new JPanel();
        outputPanel.add(new JScrollPane(outputArea));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(exitButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(outputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        decodeButton.addActionListener(e -> {
            String input = inputField.getText();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter or load an encoded string.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    String decoded = decodedString(input);
                    outputArea.setText("Decoded String:\n" + decoded);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error decoding the string: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String input = reader.readLine();
                    inputField.setText(input);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error reading file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Thank you for using the String Decoder. Goodbye!", "Goodbye", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        });

        frame.setVisible(true);
    }
}
