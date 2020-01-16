import java.util.ArrayList;
import java.util.Scanner;

public class Question {

    private String name;
    private String text;
    private ArrayList<ArrayList<Object>> answers;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<ArrayList<Object>> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<ArrayList<Object>> answers) {
        this.answers = answers;
    }

    public String toTxt() {
        StringBuilder temp = new StringBuilder("X");

        for (ArrayList<Object> i : answers) {
            if ((boolean) i.get(1)) temp.append("1");
            else temp.append("0");
        }

        temp.append("\n").append(text).append("\n");

        for (ArrayList<Object> i : answers) {
            temp.append(i.get(0)).append("\n");
        }

        return temp.toString();
    }

    public String debugString() {
        StringBuilder temp = new StringBuilder();

        temp.append("Name: ")
                .append(name)
                .append("\n")
                .append("Text: ")
                .append(text)
                .append("\n")
                .append("Answers:")
                .append("\n");

        for (ArrayList<Object> i : answers) {
            if ((boolean) i.get(1)) temp.append("->\t").append(i.get(0)).append("\n");
            else temp.append(i.get(0)).append("\n");
        }

        return temp.toString();
    }

    public Question(String name, String file) throws Exception {
        this.name = name;

        Scanner sc = new Scanner(file);

        String configLine = sc.nextLine().toUpperCase();
        if (configLine.charAt(0) == 'Y') {
            throw new Exception("Tylko pytania standardowe są wspierane!");
        }
        if (configLine.charAt(0) != 'X') {
            throw new Exception("Plik nie zawiera pytania!");
        }
        configLine = configLine.substring(1);

        this.text = sc.nextLine();

        answers = new ArrayList<>();
        for (char correctKey : configLine.toCharArray()) {
            ArrayList<Object> answerEntry = new ArrayList<>();

            answerEntry.add(sc.nextLine());
            answerEntry.add((correctKey == '1'));

            answers.add(answerEntry);
        }
    }

    public Question() {
        ArrayList<Object> answerEntry = new ArrayList<>();
        answerEntry.add("");
        answerEntry.add(false);

        answers = new ArrayList<>();
        answers.add(answerEntry);
    }
}
