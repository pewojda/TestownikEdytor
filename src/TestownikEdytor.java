import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TestownikEdytor {
    private TestownikEdytorController testownikEdytorController;

    public TestownikEdytorController getController() {
        return testownikEdytorController;
    }

    private void updateFrame(JFrame frame) {
        frame.setContentPane(createContentPane());
        frame.validate();
    }

    private void updateFrameTitle(JFrame frame) {
        frame.setTitle("Edytor - " + testownikEdytorController.getQuestion().getName() + ".txt");
    }

    private void updateFrameTitleModified(JFrame frame) {
        frame.setTitle("Edytor - *" + testownikEdytorController.getQuestion().getName() + ".txt");
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
                testownikEdytorController.newQuestion();

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
                updateFrameTitle(frame);
                updateFrame(frame);
            }
        });

        menuItem = new JMenuItem("Otwórz");
        menuItem.setMnemonic(KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                testownikEdytorController.open();

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
                updateFrameTitle(frame);
                updateFrame(frame);
            }
        });

        menuItem = new JMenuItem("Zapisz");
        menuItem.setMnemonic(KeyEvent.VK_Z);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        menu.add(menuItem);

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                testownikEdytorController.save();

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
                updateFrameTitle(frame);
            }
        });

        menuItem = new JMenuItem("Zapisz Jako...");
        menuItem.setMnemonic(KeyEvent.VK_S);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        menu.add(menuItem);

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                testownikEdytorController.saveAs();

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
                updateFrameTitle(frame);
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
                testownikEdytorController.addAnswer();

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(menuBar);
                updateFrameTitleModified(frame);
                testownikEdytorController.setModified(true);
                updateFrame(frame);
            }
        });

        menuItem = new JMenuItem("Zmień nazwę");
        menuItem.setMnemonic(KeyEvent.VK_Z);
        menu.add(menuItem);

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(menuBar);

                String newName = JOptionPane.showInputDialog(frame, "Podaj nową nazwę pliku:");

                if (newName!= null && !newName.trim().isEmpty()) {
                    testownikEdytorController.getQuestion().setName(newName);
                    updateFrameTitleModified(frame);
                    testownikEdytorController.setModified(true);
                    updateFrame(frame);
                }
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
                                "[img]xxx.png[/img]\n" +
                                "Dozwolony jest tylko format \"png\". \n" +
                                "Dodanie obrazu powoduje pominięcie reszty tekstu podczas wyświetlania pytania lub odpowiedzi.\n" +
                                "Plik powinien być w tym samym folderze bazy.\n" +
                                "\n" +
                                "Zapis:\n" +
                                "xxx.txt\n" +
                                "Numerację należy zaczynać od 001. W starszej wersji inna numeracja jest ignorowana.\n" +
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
        JTextField textField = new JTextField(testownikEdytorController.getQuestion().getText());

        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                testownikEdytorController.getQuestion().setText(textField.getText());
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(textField);
                testownikEdytorController.setModified(true);
                updateFrameTitleModified(frame);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                testownikEdytorController.getQuestion().setText(textField.getText());
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(textField);
                testownikEdytorController.setModified(true);
                updateFrameTitleModified(frame);
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                testownikEdytorController.getQuestion().setText(textField.getText());
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(textField);
                testownikEdytorController.setModified(true);
                updateFrameTitleModified(frame);
            }
        });

        return textField;
    }

    public Container answersPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        for (ArrayList<Object> answer : testownikEdytorController.getQuestion().getAnswers()) {
            panel.add(answerPanel(answer));
            if (testownikEdytorController.getQuestion().getAnswers().get(testownikEdytorController.getQuestion().getAnswers().size() - 1) != answer) {
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
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                testownikEdytorController.setModified(true);
                updateFrameTitleModified(frame);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                answer.set(0, textField.getText());
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                testownikEdytorController.setModified(true);
                updateFrameTitleModified(frame);
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                answer.set(0, textField.getText());
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                testownikEdytorController.setModified(true);
                updateFrameTitleModified(frame);
            }
        });

        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected((boolean) answer.get(1));
        panel.add(checkBox);

        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                answer.set(1, checkBox.isSelected());
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                testownikEdytorController.setModified(true);
                updateFrameTitleModified(frame);
            }
        });

        JButton button = new JButton("Usuń");
        panel.add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                testownikEdytorController.removeAnswer(answer);

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                testownikEdytorController.setModified(true);
                updateFrameTitleModified(frame);
                updateFrame(frame);
            }
        });

        return panel;
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame();

        TestownikEdytor testownikEdytor = new TestownikEdytor();
        testownikEdytor.testownikEdytorController = new TestownikEdytorController();

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                testownikEdytor.getController().exitApp();
            }
        });

        frame.setTitle("Edytor - " + testownikEdytor.getController().getQuestion().getName() + ".txt");
        frame.setJMenuBar(testownikEdytor.createJMenu());
        frame.setContentPane(testownikEdytor.createContentPane());

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
