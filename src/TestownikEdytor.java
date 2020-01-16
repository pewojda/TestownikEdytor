import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TestownikEdytor {
    Question question;


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

    public TestownikEdytor() {
        newQuestion();

        try {
            question = new Question("136","X0111\n" +
                    "Generator impulsów testowych zgodny z wymaganiami CCITT K-17 umożliwia badanie urządzeń telekomunikacyjnych dołączonych do:\n" +
                    "linii światłowodowych\n" +
                    "linii typu symetrycznej i nie symetrycznej\n" +
                    "przewodów interfejsowych\n" +
                    "przewodów zasilających");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                //File dialog *.txt
                //Expection Dialog
            }
        });

        menuItem = new JMenuItem("Zapisz");
        menuItem.setMnemonic(KeyEvent.VK_Z);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //if exist save without dialog
                //else file dialog name.txt
            }
        });

        menuItem = new JMenuItem("Zapisz Jako...");
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        menu.add(menuItem);

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //File dialog name.txt
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
                //Mini help
            }
        });

        menuItem = new JMenuItem("O...");
        menuItem.setMnemonic(KeyEvent.VK_O);
        menu.add(menuItem);

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Info
                System.out.println(question.debugString());
            }
        });

        return menuBar;
    }

    public Container createContentPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JScrollPane scrollPane = new JScrollPane(questionTextPanel());
        panel.add(scrollPane);

        scrollPane = new JScrollPane(answersPanel());
        panel.add(scrollPane);

        return panel;
    }

    public Container questionTextPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea textArea = new JTextArea(question.getText());
        textArea.setLineWrap(true);
        panel.add(textArea);

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                question.setText(textArea.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                question.setText(textArea.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                question.setText(textArea.getText());
            }
        });

        return panel;
    }

    public Container answersPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        for (ArrayList<Object> answer : question.getAnswers()) {
            panel.add(answerPanel(answer));
        }

        return panel;
    }

    public Container answerPanel(ArrayList<Object> answer) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        JTextArea textArea = new JTextArea((String) answer.get(0));
        textArea.setLineWrap(true);
        panel.add(textArea);

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                answer.set(0, textArea.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                answer.set(0, textArea.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                answer.set(0, textArea.getText());
            }
        });

        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected((boolean) answer.get(1));
        panel.add(checkBox);

        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                answer.set(1,checkBox.isSelected());
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
                }
                else {
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
        JFrame frame = new JFrame("Edytor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TestownikEdytor te = new TestownikEdytor();
        frame.setJMenuBar(te.createJMenu());
        frame.setContentPane(te.createContentPane());

        frame.pack();
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
