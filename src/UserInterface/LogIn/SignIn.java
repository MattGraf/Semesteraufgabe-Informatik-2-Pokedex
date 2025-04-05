package UserInterface.LogIn;

import Communicator.UserCsvCommunicator;

import User.User;
import User.UserContainer;

import UserInterface.Main;
import UserInterface.MainFrame;

import java.io.File;
import javax.imageio.ImageIO;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;
import javax.swing.ButtonGroup;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.io.IOException;


import static javax.swing.SwingUtilities.invokeLater;

/**
 * Fenster zur Anmeldung
 */
public class SignIn extends JFrame {
    /**
     * Ueberpruefung, ob schon gefetched wurde
     */
    public static boolean fetched = false;

    /**
     * Erstellung des Fensters zur Anmeldung
     * Moegliche Aktivitaeten:
     * - Eingabe Benutzername + Password
     * Button:
     * - close: Schliessen des Fensters und damit des Programms
     * - register: Uebergehen zur Registrierung
     * - accept: Ueberpruefung, ob Benutzername existiert, erfolgreiche Anmeldung, wenn Benutzername und Passwort passen
     */
    public SignIn() {
        super("Anmeldung");
        setMinimumSize(new Dimension(400, 350));
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

        Locale.setDefault(Locale.ENGLISH);
        JOptionPane.setDefaultLocale(Locale.ENGLISH);
        Main.guiBundle = ResourceBundle.getBundle("resources.guiBundle_en", Locale.getDefault());
        setTitle(Main.guiBundle.getString("signIn"));

        JMenuBar menuBar = new JMenuBar();

        JMenu languageMenu = new JMenu(Main.guiBundle.getString("language"));
        menuBar.add(languageMenu);

        ButtonGroup languageMenuGroup = new ButtonGroup();

        JRadioButtonMenuItem engMenuItem = new JRadioButtonMenuItem(Main.guiBundle.getString("english"));
        languageMenu.add(engMenuItem);
        languageMenuGroup.add(engMenuItem);
        engMenuItem.setSelected(true);

        JRadioButtonMenuItem gerMenuItem = new JRadioButtonMenuItem(Main.guiBundle.getString("german"));
        languageMenu.add(gerMenuItem);
        languageMenuGroup.add(gerMenuItem);

        JRadioButtonMenuItem jpnMenuItem = new JRadioButtonMenuItem(Main.guiBundle.getString("japanese"));
        languageMenu.add(jpnMenuItem);
        languageMenuGroup.add(jpnMenuItem);

        this.setJMenuBar(menuBar);

        if (!fetched) {
            try {
                UserCsvCommunicator.fetchUser();
                fetched = true;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, Main.guiBundle.getString("userLoadFail"));
                dispose();
            }
        }

        JPanel south = new JPanel();
        south.setLayout(new GridLayout(2,2));
        south.add(new JPanel());

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(4,1));

        JLabel anmeldename = new JLabel(Main.guiBundle.getString("signInName") + ": ");
        JLabel passwort = new JLabel(Main.guiBundle.getString("password") + ": ");

        JTextField anmeldenameEingabe = new JTextField();
        anmeldenameEingabe.setSize(150,10);
        anmeldenameEingabe.revalidate();

        JPasswordField passwortEingabe = new JPasswordField();
        passwortEingabe.setSize(150,10);
        passwortEingabe.revalidate();

        center.add(anmeldename);
        center.add(anmeldenameEingabe);
        center.add(passwort);
        center.add(passwortEingabe);


        JButton close = new JButton(Main.guiBundle.getString("cancel"));
        JButton accept = new JButton(Main.guiBundle.getString("accept"));
        JButton register = new JButton(Main.guiBundle.getString("register"));

        engMenuItem.addActionListener(e -> {
            ResourceBundle.clearCache();
            Locale.setDefault(Locale.ENGLISH);
            JOptionPane.setDefaultLocale(Locale.ENGLISH);
            Main.guiBundle = ResourceBundle.getBundle("resources.guiBundle_en", Locale.getDefault());

            setTitle(Main.guiBundle.getString("signIn"));

            languageMenu.setText(Main.guiBundle.getString("language"));
            engMenuItem.setText(Main.guiBundle.getString("english"));
            gerMenuItem.setText(Main.guiBundle.getString("german"));
            jpnMenuItem.setText(Main.guiBundle.getString("japanese"));

            anmeldename.setText(Main.guiBundle.getString("signInName") + ": ");
            passwort.setText(Main.guiBundle.getString("password") + ": ");

            close.setText(Main.guiBundle.getString("cancel"));
            accept.setText(Main.guiBundle.getString("accept"));
            register.setText(Main.guiBundle.getString("register"));

            repaint();
        });

        gerMenuItem.addActionListener(e -> {
            ResourceBundle.clearCache();
            Locale.setDefault(Locale.GERMAN);
            JOptionPane.setDefaultLocale(Locale.GERMAN);
            Main.guiBundle = ResourceBundle.getBundle("resources.guiBundle_de", Locale.getDefault());

            setTitle(Main.guiBundle.getString("signIn"));

            languageMenu.setText(Main.guiBundle.getString("language"));
            engMenuItem.setText(Main.guiBundle.getString("english"));
            gerMenuItem.setText(Main.guiBundle.getString("german"));
            jpnMenuItem.setText(Main.guiBundle.getString("japanese"));

            anmeldename.setText(Main.guiBundle.getString("signInName") + ": ");
            passwort.setText(Main.guiBundle.getString("password") + ": ");

            close.setText(Main.guiBundle.getString("cancel"));
            accept.setText(Main.guiBundle.getString("accept"));
            register.setText(Main.guiBundle.getString("register"));

            repaint();
        });

        jpnMenuItem.addActionListener(e -> {
            ResourceBundle.clearCache();
            Locale.setDefault(Locale.JAPANESE);
            JOptionPane.setDefaultLocale(Locale.JAPANESE);
            Main.guiBundle = ResourceBundle.getBundle("resources.guiBundle_ja", Locale.getDefault());

            setTitle(Main.guiBundle.getString("signIn"));

            languageMenu.setText(Main.guiBundle.getString("language"));
            engMenuItem.setText(Main.guiBundle.getString("english"));
            gerMenuItem.setText(Main.guiBundle.getString("german"));
            jpnMenuItem.setText(Main.guiBundle.getString("japanese"));

            anmeldename.setText(Main.guiBundle.getString("signInName"));
            passwort.setText(Main.guiBundle.getString("password"));

            close.setText(Main.guiBundle.getString("cancel"));
            accept.setText(Main.guiBundle.getString("accept"));
            register.setText(Main.guiBundle.getString("register"));

            repaint();
        });

        close.addActionListener(e -> dispose());

        register.addActionListener(e -> {
            dispose();
            new Registration();
        });

        accept.addActionListener(e -> {

            boolean exist = false;

            UserContainer users = UserContainer.instance();

            String anmeldenameText = anmeldenameEingabe.getText();
            String passwortText = new String(passwortEingabe.getPassword());

            for (User u : users) {
                if (anmeldenameText.equals(u.getName())) {
                    if (passwortText.equals(u.getPassword())) {
                        JOptionPane.showMessageDialog(this, Main.guiBundle.getString("welcomeBack") + ", " + anmeldenameText);
                        User.setCurrentUser(u);
                        dispose();

                        exist = true;

                        invokeLater(MainFrame::new);
                    } else {
                        JOptionPane.showMessageDialog(this, Main.guiBundle.getString("wrongPassword"));
                        exist = true;
                    }
                }
            }

            if (!exist) {
                JOptionPane.showMessageDialog(this, Main.guiBundle.getString("nameDoesNotExist"));
            }
        });

        south.add(register);
        south.add(close);
        south.add(accept);

        add(new JPanel(), BorderLayout.NORTH);
        add(new JPanel(), BorderLayout.EAST);
        add(new JPanel(), BorderLayout.WEST);
        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        setVisible(true);
    }

}
