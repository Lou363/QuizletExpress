import java.util.ArrayList;

public class Question {
    private String questionPrompt;
    private ArrayList<String> proposition;

    public Question(String questionPrompt, ArrayList<String> proposition){
        this.proposition = proposition;
        this.questionPrompt = questionPrompt;
    }

    public void addNewAnswer(String newAnswer){
        proposition.add(newAnswer);
    }

    public String getQuestionPrompt() {
        return questionPrompt;
    }
    public ArrayList<String> getProposition() {
        return proposition;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(questionPrompt);
        for(String prompt: proposition){
            str.append("\n" + prompt);
        }
        return str.toString();
    }
}
