package UserInterface;

import Communicator.CsvCommunicator;
import Communicator.CsvTeamCommunicator;
import Communicator.CsvImporter;

import Pokemon.Pokemon.Pokemon;

import Pokemon.Pokemon.PokemonContainer;
import Pokemon.Pokemon.Information;
import Pokemon.Type.GermanTypeContainer;
import Pokemon.Type.JapaneseTypeContainer;
import User.TeamContainer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.ButtonGroup;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Erweiterung der JFrame-Klasse - enthält Hauptinhalte der GUI
 */
public class MainFrame extends JFrame {
    /**
     * Model der pokemonList, enthält darzustellende Pokemon
     */
    private static DefaultListModel<Pokemon> pokemonModel;
    /**
     * Model der teamList, enthält darzustellende Teammitglieder
     */
    private static DefaultListModel<Integer> teamModel;
    /**
     * JList fuer Pokemon, regelt die visuelle Darstellung der Pokemon
     */
    private JList<Pokemon> pokemonList;
    /**
     * JList fuer Team, regelt die visuelle Darstellung der Teammitglieder
     */
    private JList<Integer> teamList;
    /**
     * ImagePanel, welches das Suchfeld enthält
     */
    private final ImagePanel searchFieldPanel;
    /**
     * Nutzereingabe im Suchfeld, welche die pokemonList filtert
     */
    private String searchFilter;
    /**
     * Anzahl der derzeit dargestellten Zeilen der pokemonList
     */
    private int currentRowCount = 0;

    /**
     * Konstruktor zur Erstellung des MainFrames
     */
    public MainFrame() {
        super();

        //Sprache und Lokale initialisieren
        switch (Locale.getDefault().toString()) {
            case "en" -> {
                Main.guiBundle = ResourceBundle.getBundle("resources.guiBundle_en", Locale.getDefault());
            }
            case "de" -> {
                Main.guiBundle = ResourceBundle.getBundle("resources.guiBundle_de", Locale.getDefault());
            }
            case "ja" -> {
                Main.guiBundle = ResourceBundle.getBundle("resources.guiBundle_ja", Locale.getDefault());
            }
        }
        setTitle(Main.guiBundle.getString("pokedex"));

        //LookAndFeel initialisieren und Akzentfarbe setzen
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            e.printStackTrace();
        }
        UIManager.put("OptionPane.background", Main.firstColor);
        UIManager.put("Panel.background", Main.firstColor);

        //Taskleisten-Icon setzen
        try {
            setIconImage(ImageIO.read(new File(System.getProperty("user.dir") + "/src/UserInterface/res/ui/app_icon.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }

        ImagePanel bgPanel = createBackground();

        JPanel westPanel = createPanel();
        JPanel eastPanel = createPanel();

        westPanel.setLayout(new BorderLayout());
        eastPanel.setLayout(new BorderLayout());

        //BELEGUNG DES WEST-PANELS:

        JPanel westPanelNorth = createPanel(45, 45, 25, 10);
        JPanel westPanelCenter = createPanel(0, 50, 0, 30);
        JPanel westPanelSouth = createPanel(0, 50, 50, 0);

        westPanelNorth.setLayout(new BorderLayout());
        westPanelCenter.setLayout(new BorderLayout());
        westPanelSouth.setLayout(new BorderLayout());

        //Horizontale Anzeige des Teams erzeugen
        ImagePanel teamScrollPanePanel = new ImagePanel(System.getProperty("user.dir") + "/src/UserInterface/res/ui/team_panel.png");
        teamScrollPanePanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        teamScrollPanePanel.setLayout(new FlowLayout());

        JScrollPane teamScrollPane = createTeamScrollPane();
        teamList.setVisibleRowCount(1);
        teamList.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        //Team(-mitglieder) aus CSV in TeamContainer laden
        try {
            CsvTeamCommunicator.fetchPokemon();
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateTeamModel();

        teamScrollPanePanel.add(teamScrollPane);
        westPanelNorth.add(teamScrollPanePanel);

        //Anzeige des Haupt-Icons erzeugen
        ImagePanel mainIconPanel = new ImagePanel(System.getProperty("user.dir") + "/src/UserInterface/res/ui/main_icon_panel.png");
        mainIconPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        mainIconPanel.setLayout(new FlowLayout());

        JLabel mainIconLabel = new JLabel();

        mainIconPanel.add(mainIconLabel);
        westPanelCenter.add(mainIconPanel);

        //Anzeige der Typen und der Evolution erzeugen
        ImagePanel infoPanel = new ImagePanel(System.getProperty("user.dir") + "/src/UserInterface/res/ui/info_panel_inactive.png");
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 0));
        infoPanel.setLayout(new GridLayout(2, 2));

        JLabel firstTypeLabel = new JLabel();
        firstTypeLabel.setFont(Main.firstFont);

        JLabel secondTypeLabel = new JLabel();
        secondTypeLabel.setFont(Main.firstFont);

        JLabel evolutionLabel = new JLabel();
        evolutionLabel.setFont(Main.firstFont);
        evolutionLabel.setForeground(Main.secondColor);

        infoPanel.add(firstTypeLabel);
        infoPanel.add(secondTypeLabel);
        infoPanel.add(evolutionLabel);
        westPanelSouth.add(infoPanel);

        westPanel.add(westPanelSouth, BorderLayout.SOUTH);
        westPanel.add(westPanelCenter, BorderLayout.CENTER);
        westPanel.add(westPanelNorth, BorderLayout.NORTH);

        //BELEGUNG DES EAST-PANELS:

        JPanel northEastPanel = createPanel(50, 10, 5, 50);
        JPanel centerEastPanel = createPanel(0, 0, 50, 50);

        northEastPanel.setLayout(new BorderLayout());
        centerEastPanel.setLayout(new BorderLayout());

        //Anzeige des Such-/Filterfelds erzeugen
        searchFieldPanel = new ImagePanel(System.getProperty("user.dir") + "/src/UserInterface/res/ui/search_field.png");
        searchFieldPanel.setLayout(new BorderLayout());

        JTextField searchField = new JTextField();
        searchField.setBorder(new EmptyBorder(0,20,0,40));
        searchField.setFont(Main.firstFont);
        searchField.setForeground(Main.firstColor);
        searchField.setOpaque(false);

        searchFieldPanel.add(searchField);
        northEastPanel.add(searchFieldPanel);

        centerEastPanel.setPreferredSize(searchFieldPanel.getPreferredSize());

        //Vertikale Anzeige der Pokemon erzeugen
        JPanel scrollPanePanel = createPanel();
        scrollPanePanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        scrollPanePanel.setLayout(new FlowLayout());

        JScrollPane scrollPane = createPokemonScrollPane();

        scrollPanePanel.add(scrollPane);
        centerEastPanel.add(scrollPanePanel, BorderLayout.WEST);

        //Pokemon aus CSV in PokemonContainer laden
        try {
            CsvCommunicator.fetchPokemon();
        } catch (Exception e) {
            e.printStackTrace();
        }

        updatePokemonModel();

        if (pokemonModel.isEmpty()) {
            searchFieldPanel.setVisible(false);
        }

        eastPanel.add(northEastPanel, BorderLayout.NORTH);
        eastPanel.add(centerEastPanel, BorderLayout.CENTER);

        bgPanel.add(westPanel, BorderLayout.WEST);
        bgPanel.add(eastPanel, BorderLayout.EAST);

        add(bgPanel);

        this.setJMenuBar(createJMenuBar());

        this.setSize(1000, 675);
        this.setMinimumSize(new Dimension(1000,675));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //Dokumentabhörer dem Suchfeld hinzufuegen, um die aktuelle Eingabe zu bestimmen
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { search(); }
            @Override public void removeUpdate(DocumentEvent e) { search(); }
            @Override public void changedUpdate(DocumentEvent e) { search(); }
            private void search() {
                searchFilter = searchField.getText();
                updatePokemonModel();
            }
        });

        //Listenabhörer fuer pokemonList hinzufuegen, welcher teamList, mainIconPanel und infoPanel abhängig von der derzeitigen Auswahl anpasst
        ListSelectionModel listSelectionModel = pokemonList.getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                if (lsm.getMaxSelectionIndex() >= 0) {
                    infoPanel.setImage(System.getProperty("user.dir") + "/src/UserInterface/res/ui/info_panel_active.png");

                    PokemonContainer pc = PokemonContainer.instance();
                    Pokemon p = pc.getPokemonByID(pokemonList.getSelectedValue().getPokemonID());

                    TeamContainer tc = TeamContainer.instance();
                    if (teamList.getSelectedIndex() != -1 && tc.getPokemon(teamList.getSelectedIndex()) != -1) {
                        if (!(pc.getPokemonByID(tc.getPokemon(teamList.getSelectedIndex()))).equals(p)) {
                            teamList.clearSelection();
                        }
                    } else {
                        teamList.clearSelection();
                    }

                    try {
                        mainIconLabel.setIcon(p.getMainIcon());
                    } catch(NullPointerException ignore) {
                        mainIconLabel.setIcon(null);
                    }

                    evolutionLabel.setText(Main.guiBundle.getString("evolution") + ": " + p.getEvolution());

                    firstTypeLabel.setText("");
                    secondTypeLabel.setText("");

                    /*
                    Typen werden nur dargestellt, wenn das Pokemon eine Information fuer die derzeit ausgewählte Sprache besitzt,
                    um das Logo und die Farbe des Typen, die in typeAssets gespeichert sind, zu erhalten muss der Typname in Englisch umgewandelt werden
                     */
                    switch (Locale.getDefault().toString()) {
                        case ("en") -> {
                            if (p.getInformation(Pokemon.ENGLISH) != null) {
                                typeAssets firstTypeAssets = typeAssets.valueOf(p.getInformation(Pokemon.ENGLISH).getFirstType().getName().toUpperCase());

                                firstTypeLabel.setIcon(firstTypeAssets.getIcon());
                                firstTypeLabel.setForeground(firstTypeAssets.getColor());

                                firstTypeLabel.setText(p.getInformation(Pokemon.ENGLISH).getFirstType().getName());
                                if (p.getInformation(Pokemon.ENGLISH).hasSecondType()) {
                                    typeAssets secondTypeAssets = typeAssets.valueOf(p.getInformation(Pokemon.ENGLISH).getSecondType().getName().toUpperCase());

                                    secondTypeLabel.setIcon(secondTypeAssets.getIcon());
                                    secondTypeLabel.setForeground(secondTypeAssets.getColor());

                                    secondTypeLabel.setText(p.getInformation(Pokemon.ENGLISH).getSecondType().getName());
                                } else {
                                    secondTypeLabel.setIcon(null);
                                }
                            } else {
                                firstTypeLabel.setIcon(null);
                                secondTypeLabel.setIcon(null);
                            }
                        }
                        case ("de") -> {
                            if (p.getInformation(Pokemon.GERMAN) != null) {
                                GermanTypeContainer gtc = GermanTypeContainer.instance();
                                typeAssets firstTypeAssets = typeAssets.valueOf((gtc.getEnglishPokemonType(p.getInformation(Pokemon.GERMAN).getFirstType())).getName().toUpperCase());

                                firstTypeLabel.setIcon(firstTypeAssets.getIcon());
                                firstTypeLabel.setForeground(firstTypeAssets.getColor());

                                firstTypeLabel.setText(p.getInformation(Pokemon.GERMAN).getFirstType().getName());
                                if (p.getInformation(Pokemon.GERMAN).hasSecondType()) {
                                    typeAssets secondTypeAssets = typeAssets.valueOf((gtc.getEnglishPokemonType(p.getInformation(Pokemon.GERMAN).getSecondType())).getName().toUpperCase());

                                    secondTypeLabel.setIcon(secondTypeAssets.getIcon());
                                    secondTypeLabel.setForeground(secondTypeAssets.getColor());

                                    secondTypeLabel.setText(p.getInformation(Pokemon.GERMAN).getSecondType().getName());
                                } else {
                                    secondTypeLabel.setIcon(null);
                                }
                            } else {
                                firstTypeLabel.setIcon(null);
                                secondTypeLabel.setIcon(null);
                            }
                        }
                        case ("ja") -> {
                            if (p.getInformation(Pokemon.JAPANESE) != null) {
                                JapaneseTypeContainer jtc = JapaneseTypeContainer.instance();
                                typeAssets firstTypeAssets = typeAssets.valueOf((jtc.getEnglishPokemonType(p.getInformation(Pokemon.JAPANESE).getFirstType())).getName().toUpperCase());

                                firstTypeLabel.setIcon(firstTypeAssets.getIcon());
                                firstTypeLabel.setForeground(firstTypeAssets.getColor());

                                firstTypeLabel.setText(p.getInformation(Pokemon.JAPANESE).getFirstType().getName());
                                if (p.getInformation(Pokemon.JAPANESE).hasSecondType()) {
                                    typeAssets secondTypeAssets = typeAssets.valueOf((jtc.getEnglishPokemonType(p.getInformation(Pokemon.JAPANESE).getSecondType())).getName().toUpperCase());

                                    secondTypeLabel.setIcon(secondTypeAssets.getIcon());
                                    secondTypeLabel.setForeground(secondTypeAssets.getColor());

                                    secondTypeLabel.setText(p.getInformation(Pokemon.JAPANESE).getSecondType().getName());
                                } else {
                                    secondTypeLabel.setIcon(null);
                                }
                            } else {
                                firstTypeLabel.setIcon(null);
                                secondTypeLabel.setIcon(null);
                            }
                        }
                    }
                } else {
                    teamList.clearSelection();
                    infoPanel.setImage(System.getProperty("user.dir") + "/src/UserInterface/res/ui/info_panel_inactive.png");
                    mainIconLabel.setIcon(null);
                    evolutionLabel.setText("");
                    firstTypeLabel.setText("");
                    firstTypeLabel.setIcon(null);
                    secondTypeLabel.setText("");
                    secondTypeLabel.setIcon(null);
                }
            }
        });

        //Listenabhörer fuer teamList hinzufuegen, der bei Auswahl eines Teammitgliedes das entsprechende Pokemon in pokemonList auswählt
        ListSelectionModel teamListSelectionModel = teamList.getSelectionModel();
        teamListSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                TeamContainer tc = TeamContainer.instance();
                if (teamList.getSelectedIndex() != -1 && tc.getPokemon(teamList.getSelectedIndex()) != -1) {
                    PokemonContainer pc = PokemonContainer.instance();
                    pokemonList.setSelectedValue(pc.getPokemonByID(tc.getPokemon(teamList.getSelectedIndex())), true);
                }
            }
        });

        /*
        Komponentenabhörer hinzufuegen, der bei Änderung der Anzeigegröße festlegt wie viele Zeilen in der pokemonList angezeigt werden
        und infoPanel ausblendet, wenn dafuer kein Platz mehr ist
         */
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                int newRowCount = (scrollPanePanel.getHeight()) / 130;

                /*
                Da die ScrollBarPolicy VERTICAL_SCROLLBAR_AS_NEEDED zu ungewuenschtem Verhalten fuehrt (ScrollBar wird ggf. auf der List gerendert)
                muss manuell angepasst werden, wann die Scrollbar angezeigt wird.
                 */
                if (newRowCount < pokemonList.getModel().getSize()) {
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                } else {
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                }

                if (newRowCount != currentRowCount) {
                    pokemonList.setVisibleRowCount(newRowCount);
                    currentRowCount = newRowCount;
                    pokemonList.repaint();
                    pokemonList.revalidate();
                    repaint();
                    revalidate();
                }

                if (westPanel.getHeight() - 100 < 675) {
                    infoPanel.setVisible(false);
                } else if (westPanel.getHeight() - 100 > 675) {
                    infoPanel.setVisible(true);
                }
            }
        });

        /*
        Fensterabhörer hinzufuegen, der beim Schließen des Fensters, die CSV-Dateien,
        welche das Team und insgesamt die Pokemon speichern, aktualisiert.
         */
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                CsvCommunicator.updateDataLines();
                try {
                    CsvCommunicator.update_csv();
                } catch (FileNotFoundException exc) {
                    exc.printStackTrace();
                }

                int counter = 0;
                TeamContainer teamContainer = TeamContainer.instance();
                int[] team = teamContainer.getTeam();

                PokemonContainer pokemonContainer = PokemonContainer.instance();
                ArrayList<Integer> deletedPokemonList = pokemonContainer.getDeletedPokemonList();

                for (int i= 0; i < 6; i++) {
                    if (counter < deletedPokemonList.size()) {
                        if (team[i] == deletedPokemonList.get(counter)) {
                            teamContainer.deletePokemon(team[i]);
                            counter++;
                            i--;
                        } else if (team[i] > deletedPokemonList.get(counter)) {
                            counter++;
                        }
                    }
                    if (team[i] >= 0) {
                        team[i] -= counter;
                    }
                }

                CsvTeamCommunicator.updateDataLines();
                try {
                    CsvTeamCommunicator.update_csv();
                } catch (FileNotFoundException exc) {
                    exc.printStackTrace();
                }

                e.getWindow().dispose();
            }
        });
    }

    /**
     * Erzeugung des Hintergrundbildes in der Größe des Bildschirms
     * @return ImagePanel mit Hintergrundbild
     */
    private ImagePanel createBackground() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int w =  (int) screenSize.getWidth();
        int h = (int) screenSize.getHeight();
        ImagePanel temp = new ImagePanel(System.getProperty("user.dir") + "/src/UserInterface/res/ui/background.png");
        temp.setDrawSize(new Dimension(w, h));
        temp.setLayout(new BorderLayout());
        return temp;
    }

    /**
     * Erzeugung eines Panels mit Transparenz
     * @return JPanel, das Transparenz erlaubt
     */
    private JPanel createPanel() {
        return createPanel(0, 0, 0, 0);
    }

    /**
     * Erzeugung eines Panels mit Transparenz und leeren Rahmen mit folgenden Dimensionen:
     * @param top Eine ganze Zahl, die die Breite des oberen Bereichs angibt, in Pixel
     * @param left Eine ganze Zahl, die die Breite des linken Bereichs angibt, in Pixel
     * @param bottom Eine ganze Zahl, die die Breite des unteren Bereichs angibt, in Pixel
     * @param right Eine ganze Zahl, die die Breite des rechten Bereichs angibt, in Pixel
     * @return JPanel, das Transparenz erlaubt und einen leeren Rahmen besitzt
     */
    private JPanel createPanel(int top, int left, int bottom, int right) {
        JPanel temp = new JPanel();
        temp.setOpaque(false);
        temp.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
        return temp;
    }

    /**
     * Erzeugung der Menueleiste des Mainframe
     * @return JMenuBar des Mainframes
     */
    private JMenuBar createJMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu editMenu = new JMenu(Main.guiBundle.getString("edit"));
        menuBar.add(editMenu);

        ButtonGroup editMenuGroup = new ButtonGroup();

        JMenuItem addPokemonMenuItem = new JMenuItem(Main.guiBundle.getString("addPokemon"));
        editMenu.add(addPokemonMenuItem);
        editMenuGroup.add(addPokemonMenuItem);

        JMenuItem editPokemonMenuItem = new JMenuItem(Main.guiBundle.getString("editPokemon"));
        editMenu.add(editPokemonMenuItem);
        editMenuGroup.add(editPokemonMenuItem);

        JMenuItem removePokemonMenuItem = new JMenuItem(Main.guiBundle.getString("removePokemon"));
        editMenu.add(removePokemonMenuItem);
        editMenuGroup.add(removePokemonMenuItem);

        editMenu.addSeparator();

        JMenuItem importCsvMenuItem = new JMenuItem(Main.guiBundle.getString("importCsv"));
        editMenu.add(importCsvMenuItem);
        editMenuGroup.add(importCsvMenuItem);

        addPokemonMenuItem.addActionListener(e -> {
            new PokemonDialog(this);
        });

        editPokemonMenuItem.addActionListener(e -> {
            if (pokemonList.getSelectedIndex() != -1) {
                PokemonContainer pc = PokemonContainer.instance();
                new PokemonDialog(this, pc.getPokemon(pokemonList.getSelectedIndex()));
            } else {
                JOptionPane.showMessageDialog(this, Main.guiBundle.getString("errorSelected"), Main.guiBundle.getString("editPokemon"), JOptionPane.ERROR_MESSAGE);
            }
        });

        removePokemonMenuItem.addActionListener(e -> {
            if (pokemonList.getSelectedIndex() != -1) {
                int choice = JOptionPane.showConfirmDialog(this, Main.guiBundle.getString("remove") + " No. " + String.format("%03d", pokemonList.getSelectedValue().getPokemonID()) + "?", Main.guiBundle.getString("removePokemon"), JOptionPane.OK_CANCEL_OPTION);
                if (choice == JOptionPane.OK_OPTION) {
                    PokemonContainer pc = PokemonContainer.instance();
                    TeamContainer tc = TeamContainer.instance();
                    pc.deletePokemon(pokemonList.getSelectedValue().getID());
                    tc.deletePokemonByID(pokemonList.getSelectedValue().getID());
                    updatePokemonModel();
                    updateTeamModel();
                }
            } else {
                JOptionPane.showMessageDialog(this, Main.guiBundle.getString("errorSelected"), Main.guiBundle.getString("removePokemon"), JOptionPane.ERROR_MESSAGE);
            }
        });


        importCsvMenuItem.addActionListener(e -> {
            String[] lang = {
                    Main.guiBundle.getString("information") + ": " + Main.guiBundle.getString("english"),
                    Main.guiBundle.getString("information") + ": " + Main.guiBundle.getString("german"),
                    Main.guiBundle.getString("information") + ": " + Main.guiBundle.getString("japanese"),
            };
            JComboBox<String> comboBox = new JComboBox<>(lang);

            int choice = JOptionPane.showConfirmDialog(this, comboBox, Main.guiBundle.getString("importCsv"), JOptionPane.OK_CANCEL_OPTION);
            if (choice == JOptionPane.OK_OPTION) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("csv", "csv"));
                int rval = fileChooser.showOpenDialog(null);
                if(rval == JFileChooser.APPROVE_OPTION) {
                    String csvPath = fileChooser.getSelectedFile().getPath();
                    try {
                        if (comboBox.getSelectedIndex() == 0) {
                            CsvImporter.fetchImportedPokemon(csvPath, CsvImporter.english);
                            updatePokemonModel();
                        } else if (comboBox.getSelectedIndex() == 1) {
                            CsvImporter.fetchImportedPokemon(csvPath, CsvImporter.german);
                            updatePokemonModel();
                        } else if (comboBox.getSelectedIndex() == 2) {
                            CsvImporter.fetchImportedPokemon(csvPath, CsvImporter.japanese);
                            updatePokemonModel();
                        }
                    } catch (Exception exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });


        JMenu teamMenu = new JMenu(Main.guiBundle.getString("team"));
        menuBar.add(teamMenu);

        ButtonGroup teamMenuGroup = new ButtonGroup();

        JMenuItem addToTeamMenuItem = new JMenuItem(Main.guiBundle.getString("addToTeam"));
        teamMenu.add(addToTeamMenuItem);
        teamMenuGroup.add(addToTeamMenuItem);

        JMenuItem removeFromTeamMenuItem = new JMenuItem(Main.guiBundle.getString("removeFromTeam"));
        teamMenu.add(removeFromTeamMenuItem);
        teamMenuGroup.add(removeFromTeamMenuItem);

        addToTeamMenuItem.addActionListener(e -> {
            if (pokemonList.getSelectedIndex() != -1) {
                int choice = JOptionPane.showConfirmDialog(this, Main.guiBundle.getString("addToTeam") + "?", Main.guiBundle.getString("addToTeam"), JOptionPane.OK_CANCEL_OPTION);
                if (choice == JOptionPane.OK_OPTION) {
                    TeamContainer tc = TeamContainer.instance();
                    tc.setPokemon(pokemonList.getSelectedValue().getPokemonID());
                    updateTeamModel();
                }
            } else {
                JOptionPane.showMessageDialog(this, Main.guiBundle.getString("errorSelected"), Main.guiBundle.getString("addToTeam"), JOptionPane.ERROR_MESSAGE);
            }
        });

        removeFromTeamMenuItem.addActionListener(e -> {
            if (teamList.getSelectedIndex() != -1) {
                TeamContainer tc = TeamContainer.instance();
                if (tc.getPokemon(teamList.getSelectedIndex()) == -1) {
                    JOptionPane.showMessageDialog(this, Main.guiBundle.getString("errorEmpty"), Main.guiBundle.getString("removeFromTeam"), JOptionPane.ERROR_MESSAGE);
                } else {
                    int choice = JOptionPane.showConfirmDialog(this, Main.guiBundle.getString("removeFromTeam") + "?", Main.guiBundle.getString("removeFromTeam"), JOptionPane.OK_CANCEL_OPTION);
                    if (choice == JOptionPane.OK_OPTION) {
                        tc.deletePokemon(teamList.getSelectedIndex());
                        updateTeamModel();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, Main.guiBundle.getString("errorSelected"), Main.guiBundle.getString("removeFromTeam"), JOptionPane.ERROR_MESSAGE);
            }
        });

        JMenu languageMenu = new JMenu(Main.guiBundle.getString("language"));
        menuBar.add(languageMenu);

        ButtonGroup languageMenuGroup = new ButtonGroup();

        JRadioButtonMenuItem engMenuItem = new JRadioButtonMenuItem(Main.guiBundle.getString("english"));
        languageMenu.add(engMenuItem);
        languageMenuGroup.add(engMenuItem);

        JRadioButtonMenuItem gerMenuItem = new JRadioButtonMenuItem(Main.guiBundle.getString("german"));
        languageMenu.add(gerMenuItem);
        languageMenuGroup.add(gerMenuItem);

        JRadioButtonMenuItem jpnMenuItem = new JRadioButtonMenuItem(Main.guiBundle.getString("japanese"));
        languageMenu.add(jpnMenuItem);
        languageMenuGroup.add(jpnMenuItem);

        switch (Locale.getDefault().toString()) {
            case "en" -> {
                engMenuItem.setSelected(true);
            }
            case "de" -> {
                gerMenuItem.setSelected(true);
            }
            case "ja" -> {
                jpnMenuItem.setSelected(true);
            }
        }

        engMenuItem.addActionListener(e -> {
            ResourceBundle.clearCache();
            Locale.setDefault(Locale.ENGLISH);
            JOptionPane.setDefaultLocale(Locale.ENGLISH);
            Main.guiBundle = ResourceBundle.getBundle("resources.guiBundle_en", Locale.getDefault());

            setTitle(Main.guiBundle.getString("pokedex"));

            languageMenu.setText(Main.guiBundle.getString("language"));
            engMenuItem.setText(Main.guiBundle.getString("english"));
            gerMenuItem.setText(Main.guiBundle.getString("german"));
            jpnMenuItem.setText(Main.guiBundle.getString("japanese"));

            teamMenu.setText(Main.guiBundle.getString("team"));
            addToTeamMenuItem.setText(Main.guiBundle.getString("addToTeam"));
            removeFromTeamMenuItem.setText(Main.guiBundle.getString("removeFromTeam"));

            editMenu.setText(Main.guiBundle.getString("edit"));
            addPokemonMenuItem.setText(Main.guiBundle.getString("addPokemon"));
            editPokemonMenuItem.setText(Main.guiBundle.getString("editPokemon"));
            removePokemonMenuItem.setText(Main.guiBundle.getString("removePokemon"));
            importCsvMenuItem.setText(Main.guiBundle.getString("importCsv"));

            updatePokemonModel();

            repaint();
        });

        gerMenuItem.addActionListener(e -> {
            ResourceBundle.clearCache();
            Locale.setDefault(Locale.GERMAN);
            JOptionPane.setDefaultLocale(Locale.GERMAN);
            Main.guiBundle = ResourceBundle.getBundle("resources.guiBundle_de", Locale.getDefault());

            setTitle(Main.guiBundle.getString("pokedex"));

            languageMenu.setText(Main.guiBundle.getString("language"));
            engMenuItem.setText(Main.guiBundle.getString("english"));
            gerMenuItem.setText(Main.guiBundle.getString("german"));
            jpnMenuItem.setText(Main.guiBundle.getString("japanese"));

            teamMenu.setText(Main.guiBundle.getString("team"));
            addToTeamMenuItem.setText(Main.guiBundle.getString("addToTeam"));
            removeFromTeamMenuItem.setText(Main.guiBundle.getString("removeFromTeam"));

            editMenu.setText(Main.guiBundle.getString("edit"));
            addPokemonMenuItem.setText(Main.guiBundle.getString("addPokemon"));
            editPokemonMenuItem.setText(Main.guiBundle.getString("editPokemon"));
            removePokemonMenuItem.setText(Main.guiBundle.getString("removePokemon"));
            importCsvMenuItem.setText(Main.guiBundle.getString("importCsv"));

            updatePokemonModel();

            repaint();
        });

        jpnMenuItem.addActionListener(e -> {
            ResourceBundle.clearCache();
            Locale.setDefault(Locale.JAPANESE);
            JOptionPane.setDefaultLocale(Locale.JAPANESE);
            Main.guiBundle = ResourceBundle.getBundle("resources.guiBundle_ja", Locale.getDefault());

            setTitle(Main.guiBundle.getString("pokedex"));

            languageMenu.setText(Main.guiBundle.getString("language"));
            engMenuItem.setText(Main.guiBundle.getString("english"));
            gerMenuItem.setText(Main.guiBundle.getString("german"));
            jpnMenuItem.setText(Main.guiBundle.getString("japanese"));

            teamMenu.setText(Main.guiBundle.getString("team"));
            addToTeamMenuItem.setText(Main.guiBundle.getString("addToTeam"));
            removeFromTeamMenuItem.setText(Main.guiBundle.getString("removeFromTeam"));

            editMenu.setText(Main.guiBundle.getString("edit"));
            addPokemonMenuItem.setText(Main.guiBundle.getString("addPokemon"));
            editPokemonMenuItem.setText(Main.guiBundle.getString("editPokemon"));
            removePokemonMenuItem.setText(Main.guiBundle.getString("removePokemon"));
            importCsvMenuItem.setText(Main.guiBundle.getString("importCsv"));

            updatePokemonModel();

            repaint();
        });

        return menuBar;
    }

    /**
     * Erzeugung des Scrollbereich fuer die pokemonList
     * @return JScrollPane fuer pokemonList
     */
    private JScrollPane createPokemonScrollPane() {
        pokemonModel = new DefaultListModel<Pokemon>();
        updatePokemonModel();

        pokemonList = new JList<Pokemon>(pokemonModel);
        pokemonList.setFixedCellHeight(130);
        pokemonList.setCellRenderer(new PokemonCellRenderer());
        pokemonList.setSize(500, 500);
        pokemonList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        JScrollPane dexScrollPane = new JScrollPane(pokemonList);
        dexScrollPane.setOpaque(false);
        dexScrollPane.getViewport().setOpaque(false);
        if (dexScrollPane.getViewport().getView() instanceof JComponent view) {
            view.setOpaque(false);
        }

        dexScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        dexScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        return dexScrollPane;
    }

    /**
     * Aktualisierung des pokemonModels mit den Inhalten des PokemonContainers
     */
    public void updatePokemonModel() {
        pokemonModel.removeAllElements();
        PokemonContainer pc = PokemonContainer.instance();

        if (searchFilter == null || searchFilter.isEmpty()) {
            for (int i = 0; i < pc.getLength(); i++) {
                pokemonModel.addElement(pc.getPokemon(i));
            }
        } else {
            for (int i = 0; i < pc.getLength(); i++) {
                if (Integer.toString(pc.getPokemon(i).getID()).startsWith(searchFilter)) {
                    pokemonModel.addElement(pc.getPokemon(i));
                } else {
                    Information info;
                    switch (Locale.getDefault().toString()) {
                        case ("en") -> {
                            info = pc.getPokemon(i).getInformation(Pokemon.ENGLISH);
                            if (info != null && info.getName().startsWith(searchFilter)) {
                                pokemonModel.addElement(pc.getPokemon(i));
                            }
                        }
                        case ("de") -> {
                            info = pc.getPokemon(i).getInformation(Pokemon.GERMAN);
                            if (info != null && info.getName().startsWith(searchFilter)) {
                                pokemonModel.addElement(pc.getPokemon(i));
                            }
                        }
                        case ("ja") -> {
                            info = pc.getPokemon(i).getInformation(Pokemon.JAPANESE);
                            if (info != null && info.getName().startsWith(searchFilter)) {
                                pokemonModel.addElement(pc.getPokemon(i));
                            }
                        }
                    }
                }
            }
        }

        if (pokemonModel.isEmpty() && (searchFilter == null || searchFilter.isEmpty() || pc.getLength() == 0)) {
            searchFieldPanel.setVisible(false);
        } else {
            searchFieldPanel.setVisible(true);
        }

        revalidate();
        repaint();
    }

    /**
     * Erzeugung des Scrollbereich fuer die teamList
     * @return JScrollPane fuer teamList
     */
    private JScrollPane createTeamScrollPane() {
        teamModel = new DefaultListModel<>();
        updateTeamModel();

        teamList = new JList<>(teamModel);
        teamList.setCellRenderer(new TeamCellRenderer());
        teamList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        JScrollPane teamScrollPane = new JScrollPane(teamList);
        teamScrollPane.setOpaque(false);
        teamScrollPane.getViewport().setOpaque(false);
        if (teamScrollPane.getViewport().getView() instanceof JComponent view) {
            view.setOpaque(false);
        }

        teamScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        return teamScrollPane;
    }

    /**
     * Aktualisierung des teamModels mit den Inhalten des TeamContainers
     */
    public void updateTeamModel() {
        teamModel.removeAllElements();
        for (int i = 0; i < 6; i++) {
            teamModel.addElement(Integer.valueOf(i));
        }

        revalidate();
        repaint();
    }
}
