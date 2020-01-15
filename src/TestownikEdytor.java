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

    public TestownikEdytor() {
        question = new Question();
        question.setName("0");
    }

    private JMenuBar createJMenu(JFrame frame) {
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
                question = new Question();
                question.setName("0");

                frame.setContentPane(createContentPane(frame));
                frame.validate();
            }
        });

        menuItem = new JMenuItem("Otwórz");
        menuItem.setMnemonic(KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Zapisz");
        menuItem.setMnemonic(KeyEvent.VK_Z);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Zapisz Jako...");
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("Wyjdź");
        menuItem.setMnemonic(KeyEvent.VK_W);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
        menu.add(menuItem);

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
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

        menu = new JMenu("Pomoc");
        menu.setMnemonic(KeyEvent.VK_O);
        menuBar.add(menu);

        menuItem = new JMenuItem("Pomoc");
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.ALT_DOWN_MASK));
        menu.add(menuItem);

        menuItem = new JMenuItem("O...");
        menuItem.setMnemonic(KeyEvent.VK_O);
        menu.add(menuItem);

        return menuBar;
    }

    public Container createContentPane(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JScrollPane scrollPane = new JScrollPane(questionTextPanel());
        panel.add(scrollPane);

        scrollPane = new JScrollPane(answersPanel(frame));
        panel.add(scrollPane);

        return panel;
    }

    public Container questionTextPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextArea textArea = new JTextArea(question.getText());
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

    public Container answersPanel(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        for (ArrayList<Object> answer : question.getAnswers()) {
            panel.add(answerPanel(answer, frame));
        }

        return panel;
    }

    public Container answerPanel(ArrayList<Object> answer, JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        //TextArea
        //Checkbox
        //Button

        return panel;
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("0 - Edytor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TestownikEdytor te = new TestownikEdytor();
        frame.setJMenuBar(te.createJMenu(frame));
        frame.setContentPane(te.createContentPane(frame));

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
