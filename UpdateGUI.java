import java.util.*;

public class UpdateGUI {
    private static int score=0;
    private static ArrayList<Integer> possibleRandomization = new ArrayList<>();
    private static ArrayList<Question> setOfQuestions;
    private static ArrayList<Question> WrongAnswers = new ArrayList<>();
    private static int MAX_SCORE;
    //Those apply for the current event
    private static int currentQuestionOnGUI;
    private static GUI window;
    private static String correctAnswer;

    public static void intializeGUI(ArrayList<Question> tobeDefinedSQuestions,String fileName){
        setOfQuestions = tobeDefinedSQuestions;
        for(int i=0;i<setOfQuestions.size();i++){
            possibleRandomization.add(i);
        }
        //We now intialise every button with the new answer
        window = new GUI(fileName);
        Random rand = new Random();
        int firstIndex = rand.nextInt(possibleRandomization.size());
        currentQuestionOnGUI = possibleRandomization.get(firstIndex);
        possibleRandomization.remove(firstIndex);
        //I update the interface
        //I load the interface:
        window.updateQuestionLabel(setOfQuestions.get(currentQuestionOnGUI).getQuestionPrompt());
        MAX_SCORE = setOfQuestions.size();
        //I shuffle the answers
        shuffleAndUpdateQuestion();
        window.updateScoreLabel(score, MAX_SCORE);
    }

    public static void updateGUI(String buttonPressed){
        //I verify the answer
        if(buttonPressed.equalsIgnoreCase(correctAnswer)){
            score++;
        } else {
            WrongAnswers.add(setOfQuestions.get(currentQuestionOnGUI));
            window.displayWrongAnswerMessage(correctAnswer);
        }
        //I update the score
        window.updateScoreLabel(score, MAX_SCORE);
        //I now go and fetch the next question:
        int firstIndex;
        if (possibleRandomization.size()!=0){
            if(!(possibleRandomization.size()<=1)){
                Random rand = new Random();
                firstIndex = rand.nextInt(possibleRandomization.size());
            } else {
                firstIndex = 0;
            }
            currentQuestionOnGUI = possibleRandomization.get(firstIndex);
            possibleRandomization.remove(firstIndex);
            //I now update the function
            window.updateQuestionLabel(setOfQuestions.get(currentQuestionOnGUI).getQuestionPrompt());
            shuffleAndUpdateQuestion();
        } else {
            System.out.println("End of questions, restarting with errors.");
            if(MAX_SCORE==score){
                System.out.println("Completed the quizz, we're closing.");
                window.reachedEndOfQuestion(score, MAX_SCORE);
                System.out.println("Completed program.");
            } else {
                window.reachedEndOfQuestion(score, MAX_SCORE);
                setOfQuestions = WrongAnswers;
                WrongAnswers = new ArrayList<>();
                possibleRandomization = new ArrayList<>();
                //We re-initialize the possible randomization.
                for(int i=0;i<setOfQuestions.size();i++){
                    possibleRandomization.add(i);
                }
                // We relaunch the previous statements to restart the game.
                if (possibleRandomization.size()!=0){
                    if(!(possibleRandomization.size()<=1)){
                        Random rand = new Random();
                        firstIndex = rand.nextInt(possibleRandomization.size());
                    } else {
                        firstIndex = 0;
                    }
                    currentQuestionOnGUI = possibleRandomization.get(firstIndex);
                    possibleRandomization.remove(firstIndex);
                    //I now update the function
                    window.updateQuestionLabel(setOfQuestions.get(currentQuestionOnGUI).getQuestionPrompt());
                    shuffleAndUpdateQuestion();
                    window.updateScoreLabel(score, MAX_SCORE);
                }
            }

        }
    }

    public static void shuffleAndUpdateQuestion(){
        correctAnswer = setOfQuestions.get(currentQuestionOnGUI).getProposition().get(0);
        ArrayList<String> tempCopy = new ArrayList<>(setOfQuestions.get(currentQuestionOnGUI).getProposition());
        Collections.shuffle(tempCopy);
        if(tempCopy.size()==4){
            window.setVisibilityButton2(true);
            window.setVisibilityButton3(true);
            window.updateButton0Label(tempCopy.get(0));
            window.updateButton1Label(tempCopy.get(1));
            window.updateButton2Label(tempCopy.get(2));
            window.updateButton3Label(tempCopy.get(3));
        } else if (tempCopy.size()==2 && (tempCopy.get(0).equalsIgnoreCase("false")
        ||tempCopy.get(0).equalsIgnoreCase("true"))){
            window.setVisibilityButton2(false);
            window.setVisibilityButton3(false);
            window.updateButton0Label("True");
            window.updateButton1Label("False");
        } else{
            window.setVisibilityButton2(false);
            window.setVisibilityButton3(false);
            window.updateButton0Label(tempCopy.get(0));
            window.updateButton1Label(tempCopy.get(1));
        }
    }
}
