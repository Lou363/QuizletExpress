import javax.swing.*;
import java.awt.event.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;

public class fileGUI extends JFrame{
    private JPanel mainPanel;
    private JComboBox<String> fileBox;
    private JPanel typeBox;
    private JLabel instruction;
    private JLabel extensionInfo;
    private JButton submitButton;
    private JCheckBox soundEnabled;
    //General variables
    public String fileName;
    private File file;
    private ArrayList<Question> setOfQuestions = new ArrayList<>();

    public fileGUI(){
        setTitle("Select a file");
        setSize(380,115);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //I list out ALL THE FILES:
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
        instruction = new JLabel("Select the questionaire you wish to open:");
        typeBox = new JPanel();
        extensionInfo = new JLabel(".txt");
        submitButton = new JButton("Submit");
        /* ##############################
         * #    LOOP THROUGH FILES      #
         * ##############################
         */
        //I handle the fileBox:
        //Now I loop through every files
        File folder = new File("Database/");
        List<String> setOfFiles = new ArrayList<String>();
        try{
        //I loop through every files
            File[] listOfFiles = folder.listFiles();;
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    setOfFiles.add(listOfFiles[i].getName().replace(".txt", ""));
                } else if (listOfFiles[i].isDirectory()) {
                    continue;
                }
            }
        } catch (NullPointerException e){
            //This code will execute if the directory is empty
            System.out.println("An error has occured: no files in directory.");
            JOptionPane.showMessageDialog(this, "No files have been detected " +
            "in folder 'Database'.\nThis programm cannot proceed", "No files in directory", 
            JOptionPane.ERROR_MESSAGE);
        }
        //From this point I initialize the ComboBox
        String[] strFileSet = new String[setOfFiles.size()];
        strFileSet = setOfFiles.toArray(strFileSet);
        fileBox = new JComboBox<String>(strFileSet);
        //I handle the sound check box
        soundEnabled = new JCheckBox("Enable sound");
        soundEnabled.setSelected(true);
        soundEnabled.setAlignmentX(CENTER_ALIGNMENT);
        //I adjust in the secondary panel
        typeBox.add(fileBox);
        typeBox.add(extensionInfo);
        typeBox.add(submitButton);
        typeBox.setAlignmentX(CENTER_ALIGNMENT);
        //I append the rest in the main panel:
        instruction.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(instruction);
        mainPanel.add(typeBox);
        mainPanel.add(soundEnabled);
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
        fileName = fileBox.getSelectedItem().toString();
        //I verify that the string has actually something in it.
        if(fileName.length()==0){
            JOptionPane.showMessageDialog(this, "Please enter a file name.", "Empty field",
             JOptionPane.WARNING_MESSAGE);
             return;
        }
        try{
            // I read the main file
            fileName = fileBox.getSelectedItem().toString();
            file = new File("Database/"+fileBox.getSelectedItem().toString()+".txt");
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
            UpdateGUI.intializeGUI(setOfQuestions,fileName,soundEnabled.isSelected());
            //I close this GUI
            this.setVisible(false);
            fileContent.close();
        } catch(FileNotFoundException e){ //I handle errors
            JOptionPane.showMessageDialog(this, "File '"+fileName+".txt has not been found.",
            "File not found", JOptionPane.WARNING_MESSAGE);
            fileBox.setSelectedIndex(0);
        }
    }
}