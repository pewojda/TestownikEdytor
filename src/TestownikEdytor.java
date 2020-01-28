import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.stream.Stream;
import javax.swing.filechooser.*;

public class TestownikEdytor {
    Question question;
    File currentDirectory;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    private void newQuestion() {
        question = new Question();
        question.setName("000");
        question.setText("");
    }

    private String fileToString(File file) {
        StringBuilder temp = new StringBuilder();

        try {
            Stream<String> stream = Files.lines(file.toPath(), Charset.forName("windows-1250"));
            stream.forEach(s -> temp.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return temp.toString();
    }

    private void saveAs() {
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
                Files.writeString(file.toPath(), question.toTxt(), Charset.forName("windows-1250"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            question.setName(file.getName().substring(0, file.getName().length() - 4));
            currentDirectory = fc.getCurrentDirectory();
        }
    }

    public TestownikEdytor() {
        newQuestion();
        currentDirectory = new File(".");
    }

    private JMenuBar createJMenu() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("Plik");
        menu.setMnemonic(KeyEvent.VK_P);
        menuBar.add(menu);

        menuItem = new JMenuItem("Nowy");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                newQuestion();

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
                frame.setContentPane(createContentPane());
                frame.setTitle("Edytor - " + question.getName() + ".txt");
                frame.validate();
            }
        });

        menuItem = new JMenuItem("Otwórz");
        menuItem.setMnemonic(KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
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

                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
                        frame.setContentPane(createContentPane());
                        frame.setTitle("Edytor - " + question.getName() + ".txt");
                        frame.validate();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), name, JOptionPane.ERROR_MESSAGE);
                    }
                    currentDirectory = fc.getCurrentDirectory();
                }
            }
        });

        menuItem = new JMenuItem("Zapisz");
        menuItem.setMnemonic(KeyEvent.VK_Z);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                File file = new File(currentDirectory.getAbsolutePath() + "\\" + question.getName() + ".txt");

                if (file.exists()) {
                    try {
                        Files.writeString(file.toPath(), question.toTxt(), Charset.forName("windows-1250"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else saveAs();

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
                frame.setTitle("Edytor - " + question.getName() + ".txt");
            }
        });

        menuItem = new JMenuItem("Zapisz Jako...");
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        menu.add(menuItem);

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveAs();

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
                frame.setTitle("Edytor - " + question.getName() + ".txt");
            }
        });

        menuItem = new JMenuItem("Wyjdź");
        menuItem.setMnemonic(KeyEvent.VK_W);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
        menu.add(menuItem);

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

        menu = new JMenu("Pytanie");
        menu.setMnemonic(KeyEvent.VK_Y);
        menuBar.add(menu);

        menuItem = new JMenuItem("Dodaj Odpowiedź...");
        menuItem.setMnemonic(KeyEvent.VK_D);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ArrayList<Object> answerEntry = new ArrayList<>();
                answerEntry.add("");
                answerEntry.add(false);

                question.getAnswers().add(answerEntry);

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
                frame.setContentPane(createContentPane());
                frame.validate();
            }
        });

        menu = new JMenu("Pomoc");
        menu.setMnemonic(KeyEvent.VK_O);
        menuBar.add(menu);

        menuItem = new JMenuItem("Pomoc");
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.ALT_DOWN_MASK));
        menu.add(menuItem);

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null,
                        "Dołączanie obrazów:\n" +
                                "[IMG]xxx.png[/IMG]\n" +
                                "Dozwolony jest tylko format \"png\". \n" +
                                "Dodanie obrazu powoduje pominięcie reszty tekstu podczas wyświetlania pytania lub odpowiedzi.\n" +
                                "Plik powinien być w tym samym folderze bazy.\n" +
                                "\n" +
                                "Zapis:\n" +
                                "xxx.txt\n" +
                                "Numerację należy zaczynać od 000. W starszej wersji inne pliki są ignorowane.\n" +
                                "Dla starej wersji testownika pytania powinny być umieszczone w folderze \"baza\". \n" +
                                "Nowy testownik pozwala na wybór dowolnego folderu.",
                        "Pomoc", JOptionPane.PLAIN_MESSAGE);
            }
        });

        menuItem = new JMenuItem("O...");
        menuItem.setMnemonic(KeyEvent.VK_O);
        menu.add(menuItem);

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "TestownikEdytor\nProjekt zaliczeniowy \"Programowanie w języku Java\"\nPW 2020", "O...", JOptionPane.PLAIN_MESSAGE);
            }
        });

        return menuBar;
    }

    public Container createContentPane() {
        JPanel panel = new JPanel(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(questionTextField()), new JScrollPane(answersPanel()));
        splitPane.setResizeWeight(0.25);
        panel.add(splitPane);

        return panel;
    }

    public Container questionTextField() {
        JTextField textField = new JTextField(question.getText());

        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                question.setText(textField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                question.setText(textField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                question.setText(textField.getText());
            }
        });

        return textField;
    }

    public Container answersPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        for (ArrayList<Object> answer : question.getAnswers()) {
            panel.add(answerPanel(answer));
            if (question.getAnswers().get(question.getAnswers().size() - 1) != answer) {
                panel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        return panel;
    }

    public Container answerPanel(ArrayList<Object> answer) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        JTextField textField = new JTextField((String) answer.get(0));
        JScrollPane scrollPane = new JScrollPane(textField);
        panel.add(scrollPane);

        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                answer.set(0, textField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                answer.set(0, textField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                answer.set(0, textField.getText());
            }
        });

        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected((boolean) answer.get(1));
        panel.add(checkBox);

        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                answer.set(1, checkBox.isSelected());
            }
        });

        JButton button = new JButton("Usuń");
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
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

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                frame.setContentPane(createContentPane());
                frame.validate();
            }
        });

        return panel;
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TestownikEdytor te = new TestownikEdytor();
        frame.setTitle("Edytor - " + te.getQuestion().getName() + ".txt");
        frame.setJMenuBar(te.createJMenu());
        frame.setContentPane(te.createContentPane());

        frame.pack();
        frame.setMinimumSize(new Dimension(300, 250));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
