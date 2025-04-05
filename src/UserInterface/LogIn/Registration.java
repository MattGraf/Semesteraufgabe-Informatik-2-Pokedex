package UserInterface.LogIn;

import Communicator.UserCsvCommunicator;

import User.User;
import User.UserContainer;
import User.UserException;

import UserInterface.Main;
import UserInterface.MainFrame;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.io.FileNotFoundException;

import static javax.swing.SwingUtilities.invokeLater;

/**
 * Fenster zur Registrierung
 */
public class Registration extends JFrame {
    /**
     * Erstellung des Fensters zur Registrierung
     * Moegliche Aktivitaeten:
     * - Eingabe Benutzername + Passwort und Passwort-Wiederholung
     * Button:
     * - back: Zurueckgehen zur Anmeldung
     * - accept: Erstellen eines neuen Kontos, wenn Benutzername nicht existiert und Passwort und Passwort-Wiederholung uebereinstimmen
     */
    public Registration() {
        super(Main.guiBundle.getString("registration"));
        setMinimumSize(new Dimension(400, 400));
        setResizable(false);
        setLayout(new BorderLayout(0,30));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        try {
            setIconImage(ImageIO.read(new File(System.getProperty("user.dir") + "/src/UserInterface/res/ui/app_icon.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            e.printStackTrace();
        }

        JPanel south = new JPanel();
        south.setLayout(new GridLayout(2,2));
        south.add(new JPanel());
        south.add(new JPanel());

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(6,1));

        JLabel anmeldename = new JLabel(Main.guiBundle.getString("signInName") + ": ");
        JLabel passwort = new JLabel(Main.guiBundle.getString("password") + ": ");
        JLabel passwortWiederholen = new JLabel(Main.guiBundle.getString("repeatPassword") + ": ");

        JTextField anmeldenameEingabe = new JTextField();
        anmeldenameEingabe.setSize(150,10);
        anmeldenameEingabe.revalidate();

        JPasswordField passwortEingabe = new JPasswordField();
        passwortEingabe.setSize(150,10);
        passwortEingabe.revalidate();

        JPasswordField passwortWiederholenEingabe = new JPasswordField();
        passwortWiederholenEingabe.setSize(150,10);
        passwortWiederholenEingabe.revalidate();

        center.add(anmeldename);
        center.add(anmeldenameEingabe);
        center.add(passwort);
        center.add(passwortEingabe);
        center.add(passwortWiederholen);
        center.add(passwortWiederholenEingabe);


        JButton back = new JButton(Main.guiBundle.getString("back"));
        JButton accept = new JButton(Main.guiBundle.getString("accept"));

        back.addActionListener(e -> {
            dispose();
            new SignIn();
        });

        accept.addActionListener(e -> {
            try {
                boolean dataSaved = false;

                User user = new User(anmeldenameEingabe.getText(), new String(passwortEingabe.getPassword()));
                if (!new String(passwortEingabe.getPassword()).equals(new String(passwortWiederholenEingabe.getPassword()))) {
                    JOptionPane.showMessageDialog(this, Main.guiBundle.getString("passwordNotEqual"));
                } else {
                    UserContainer users = UserContainer.instance();

                    boolean exist = false;
                    for (User u : users) {
                        if (u.getName().equals(anmeldenameEingabe.getText())) {
                            JOptionPane.showMessageDialog(this, Main.guiBundle.getString("nameAlreadyExists"));
                            exist = true;
                            break;
                        }
                    }
                    if (!exist) {
                        JOptionPane.showMessageDialog(this, Main.guiBundle.getString("hello") + ", " + anmeldenameEingabe.getText());
                        User.setCurrentUser(user);
                        users.addUser(user);
                        UserCsvCommunicator.updateDataLines();
                        try {
                            UserCsvCommunicator.update_csv();
                            dataSaved = true;
                        } catch (FileNotFoundException f) {
                            JOptionPane.showMessageDialog(this, Main.guiBundle.getString("dateSaveFail"));
                        }

                        dispose();

                        if (dataSaved) {
                            invokeLater(MainFrame::new);
                        }
                    }
                }
            } catch (UserException f) {
                JOptionPane.showMessageDialog(this, Main.guiBundle.getString("generalFail"));
            }
        });

        south.add(back);
        south.add(accept);

        add(new JPanel(), BorderLayout.NORTH);
        add(new JPanel(), BorderLayout.EAST);
        add(new JPanel(), BorderLayout.WEST);
        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        setVisible(true);
    }

}
