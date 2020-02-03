import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.stream.Stream;

public class TestownikEdytorController {
    private Question question;
    private File currentDirectory;
    private boolean modified;

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void newQuestion() {
        question = new Question();
        question.setName("001");
        question.setText("");
    }

    public String fileToString(File file) {
        StringBuilder temp = new StringBuilder();

        try {
            Stream<String> stream = Files.lines(file.toPath(), Charset.forName("windows-1250"));
            stream.forEach(s -> temp.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return temp.toString();
    }

    public void open() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(currentDirectory);
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }

                return file.getName().endsWith(".txt");
            }

            @Override
            public String getDescription() {
                return "Pytanie (*.txt)";
            }
        });

        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String name = file.getName().substring(0, file.getName().length() - 4);

            try {
                question = new Question(name, fileToString(file));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), name, JOptionPane.ERROR_MESSAGE);
            }
            currentDirectory = fc.getCurrentDirectory();
        }
    }

    public void saveAs() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(currentDirectory);
        fc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }

                return file.getName().endsWith(".txt");
            }

            @Override
            public String getDescription() {
                return "Pytanie (*.txt)";
            }
        });

        fc.setSelectedFile(new File(question.getName() + ".txt"));

        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            if (file.exists()) {
                if (JOptionPane.showConfirmDialog(null, "Wybrany plik istnieje!\n Czy nadpisać?", "Błąd", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                    return;
                }
            }

            if (!file.getName().endsWith(".txt")) file = new File(file.getAbsolutePath() + ".txt");

            try {
                Files.writeString(file.toPath(), question.toString(), Charset.forName("windows-1250"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            question.setName(file.getName().substring(0, file.getName().length() - 4));
            currentDirectory = fc.getCurrentDirectory();
        }
    }

    public void save() {
        File file = new File(currentDirectory.getAbsolutePath() + "\\" + question.getName() + ".txt");

        if (file.exists()) {
            try {
                Files.writeString(file.toPath(), question.toString(), Charset.forName("windows-1250"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else saveAs();
    }

    public void addAnswer() {
        ArrayList<Object> answerEntry = new ArrayList<>();
        answerEntry.add("");
        answerEntry.add(false);

        question.getAnswers().add(answerEntry);
    }

    public void removeAnswer(ArrayList<Object> answer) {
        int idx = question.getAnswers().indexOf(answer);

        if (question.getAnswers().size() > 1) {
            question.getAnswers().remove(idx);
        } else {
            question.getAnswers().remove(idx);

            ArrayList<Object> answerEntry = new ArrayList<>();
            answerEntry.add("");
            answerEntry.add(false);

            question.getAnswers().add(answerEntry);
        }
    }

    public void exitApp() {
        if (modified) {
            if (JOptionPane.showConfirmDialog(null, "Plik został zmodyfikowany\nCzy zapisać?", "Pytanie", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                save();
            }
        }
        System.exit(0);
    }

    public TestownikEdytorController() {
        currentDirectory = new File(".");
        newQuestion();
        modified = false;
    }
}
