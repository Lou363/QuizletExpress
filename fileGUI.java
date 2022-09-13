import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class fileGUI extends JFrame{
    private JPanel mainPanel;
    private JTextField fileBox;
    private JPanel typeBox;
    private JLabel instruction;
    private JLabel extensionInfo;
    private JButton submitButton;
    //General variables
    public String fileName;
    private File file;
    private ArrayList<Question> setOfQuestions = new ArrayList<>();

    public fileGUI(){
        setTitle("Select a file");
        setSize(332,102);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //I build the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        //I call the function to construct this panel
        constructMainPanel();
        add(mainPanel);
        setAlwaysOnTop(true);
        setVisible(true);
    }

    public void constructMainPanel(){
        //I initialize the first few elements
        instruction = new JLabel("Type the name of the questionaire you wish to open:");
        typeBox = new JPanel();
        fileBox = new JTextField(15);
        extensionInfo = new JLabel(".txt");
        submitButton = new JButton("Submit");
        //I adjust the X adjustement
        typeBox.setAlignmentX(CENTER_ALIGNMENT);
        instruction.setAlignmentX(CENTER_ALIGNMENT);
        //I adjust in the secondary panel
        typeBox.add(fileBox);
        typeBox.add(extensionInfo);
        typeBox.add(submitButton);
        //I append the rest in the main panel:
        mainPanel.add(instruction);
        mainPanel.add(typeBox);
        //I register the ation listeners
        fileBox.addKeyListener(new TriggerOnEnterKey());
        submitButton.addActionListener(new GetButtonPressed());
    }
    
    /* ##########################################
     * #            ACTION LISTENERS            #
     * ##########################################
     */
    private class GetButtonPressed implements ActionListener{
        public void actionPerformed(ActionEvent e){
            readFileAndExcute();
        }
    }

    private class TriggerOnEnterKey implements KeyListener{
        public void keyPressed(KeyEvent e){
            if(e.getKeyCode()==10){
                readFileAndExcute();
            }
        }
        public void keyTyped(KeyEvent e) {}
        public void keyReleased(KeyEvent e) {}
    }
    /* ##########################################
     * #       FETCH FILE AND PROCEED           #
     * ##########################################
     */
    private void readFileAndExcute(){
        fileName = fileBox.getText();
        //I verify that the string has actually something in it.
        if(fileName.length()==0){
            JOptionPane.showMessageDialog(this, "Please enter a file name.", "Empty field",
             JOptionPane.WARNING_MESSAGE);
             return;
        }
        try{
            // I read the main file
            fileName = fileBox.getText();
            file = new File("Database/"+fileBox.getText()+".txt");
            Scanner fileContent = new Scanner(file);
            //I begin to read the file line by line.
            String currentLine = fileContent.nextLine();
            while(fileContent.hasNextLine() && !currentLine.equals("END")){
                currentLine = fileContent.nextLine();
                //I determine if I need to open a new question
                if(currentLine.regionMatches(false, 0, "Q: ", 0, 3)){
                    //I create a new instance of an object;
                    String questionTitle = currentLine.replace("Q: ", "");
                    Question newQuestion = new Question(questionTitle, new ArrayList<String>());
                    currentLine = fileContent.nextLine();
                    // I now append propositions to the new set of questions:
                    while(!currentLine.equals("")&&!currentLine.equals("END")){
                        newQuestion.addNewAnswer(currentLine);
                        currentLine = fileContent.nextLine();
                    }
                    setOfQuestions.add(newQuestion);
                }
            }
            UpdateGUI.intializeGUI(setOfQuestions,fileName);
            //I close this GUI
            this.setVisible(false);
            fileContent.close();
        } catch(FileNotFoundException e){ //I handle errors
            JOptionPane.showMessageDialog(this, "File '"+fileName+".txt has not been found.",
            "File not found", JOptionPane.WARNING_MESSAGE);
            fileBox.setText("");
        }
    }
}