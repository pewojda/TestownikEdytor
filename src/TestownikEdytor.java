import javax.swing.*;

public class TestownikEdytor {

    //TODO

    //constructor
    ////Create empty question

    //menubar
    //Plik
    ////Nowy
    ////Otworz
    ////Zapisz
    ////Zapisz jako
    ////Zamknij
    //Pytanie
    ////Dodaj Odpowiedz
    //Pomoc
    ////Pomoc
    ////O...

    //mainpanel
    ////Field text
    ////Panel answers
    //////Panel answer

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TestownikEdytor te = new TestownikEdytor();
        //frame.setJMenuBar();
        //frame.setContentPane();

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
