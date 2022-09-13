import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUI extends JFrame{
    private JPanel mainPanel;
    private JLabel questionLabel;
    private JButton button0;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JLabel score_label;

    //This will construct the basic function
    public GUI(String quizzName){
        setTitle("Revising "+quizzName);
        setSize(500,180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Now I must add its members: the panel
        buildMainPanel();
        add(mainPanel, BorderLayout.CENTER);
        setAlwaysOnTop(true);
        setVisible(true);
    }



    private void buildMainPanel(){
        mainPanel = new JPanel();
        //I initialize the prompt
        questionLabel = new JLabel("This is the default question for demonstrational purposes ?");
        button0 = new JButton("Answer 1 is this");
        button1 = new JButton("Answer 2 is this");
        button2 = new JButton("Answer 3 is this");
        button3 = new JButton("Answer 4 is this");
        score_label = new JLabel("Score -/10");
        //We center x-wise
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        button0.setAlignmentX(Component.CENTER_ALIGNMENT);
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        button3.setAlignmentX(Component.CENTER_ALIGNMENT);
        score_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        //I add those elements
        //I setup the button panel:
        BoxLayout boxlayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(boxlayout);
        mainPanel.add(questionLabel);
        mainPanel.add(button0);
        mainPanel.add(button1);
        mainPanel.add(button2);
        mainPanel.add(button3);
        mainPanel.add(score_label);
        //I register action listenners
        button0.addActionListener(new button0Listener());
        button1.addActionListener(new button1Listener());
        button2.addActionListener(new button2Listener());
        button3.addActionListener(new button3Listener());
    }
    private class button0Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            UpdateGUI.updateGUI(button0.getText());
        }
    }
    private class button1Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            UpdateGUI.updateGUI(button1.getText());
        }
    }
    private class button2Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            UpdateGUI.updateGUI(button2.getText());
        }
    }
    private class button3Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            UpdateGUI.updateGUI(button3.getText());
        }
    }

    //Update the GUI
    public void updateQuestionLabel(String newQuestion){
        String[] strArr = newQuestion.split(" ");
        //If the array is too long to fit in the main size
        if(strArr.length >= 17){
            StringBuilder str = new StringBuilder("<html><center>");
            //I recontruct 
            int i = 0;
            for(String currentSubstring: strArr){
                str.append(currentSubstring);
                if(i%17==0&&i!=0){
                    str.append("<br>");
                }else{
                    str.append(" ");
                }
                i++;
            }
            str.append("</center></html>");
            newQuestion = str.toString();
            questionLabel.setText(newQuestion);
        } else {
            questionLabel.setText(newQuestion);
        }
    }
    public void updateButton0Label(String newPrompt){
        button0.setText(newPrompt);
    }
    public void updateButton1Label(String newPrompt){
        button1.setText(newPrompt);
    }
    public void updateButton2Label(String newPrompt){
        button2.setText(newPrompt);
    }
    public void updateButton3Label(String newPrompt){
        button3.setText(newPrompt);
    }
    public void updateScoreLabel(int newScore,int maxScore){
        score_label.setText("Score: "+newScore+"/"+maxScore);
    }
    public void setVisibilityButton2(boolean stat){
        button2.setVisible(stat);
    }
    public void setVisibilityButton3(boolean stat){
        button3.setVisible(stat);
    }
    //Generate dialogue box
    public void displayWrongAnswerMessage(String correctAnswer){
        JOptionPane.showMessageDialog(this, "Wrong answer!\nThe correct answer was '"+correctAnswer+"''.",
         "Wrong answer", JOptionPane.WARNING_MESSAGE);
    }
    public void reachedEndOfQuestion(int scoreFinal,int maxScore){
        if(scoreFinal==maxScore){ //If all answers are correct
            JOptionPane.showMessageDialog(this,"Congratulation, you completed all the answer propely.",
            "End of game",JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
            System.exit(0);
            //We stop the program
        } else {
            JOptionPane.showMessageDialog(this, "You scored "+scoreFinal+"/"+maxScore+", you will restart the"+
            "questions to whom you go a wrong answer", "Game over", JOptionPane.WARNING_MESSAGE);
        }
    }

}
