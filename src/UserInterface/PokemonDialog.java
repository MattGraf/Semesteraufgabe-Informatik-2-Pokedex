package UserInterface;

import Pokemon.Pokemon.Pokemon;
import Pokemon.Pokemon.PokemonContainer;
import Pokemon.Pokemon.Information;
import Pokemon.Type.EnglishTypeContainer;
import Pokemon.Type.GermanTypeContainer;
import Pokemon.Type.JapaneseTypeContainer;
import Pokemon.Type.PokemonType;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Erweiterung der JDialog-Klasse - enthält GUI-Elemente zur Erstellung und Anpassung von Pokemon
 */
public class PokemonDialog extends JDialog {
    /**
     * Konstante fuer illegale Eingabe in einem Textfeld
     */
    private static final int ILLEGAL_INPUT = -1;
    /**
     * Konstante fuer leere Eingabe in einem Textfeld
     */
    private static final int EMPTY_INPUT = 0;
    /**
     * Konstante fuer legale Nutzereingabe in einem Textfeld
     */
    private static final int LEGAL_INPUT = 1;
    /**
     * ImageIcon, das einen Platzhalter enthält, fuer Pokemon, denen der Nutzer kein pixelIcon mitgegeben hat
     */
    private static ImageIcon missing;
    /**
     * Zustand der englischen Nutzereingabe, wird mit den oben deklarierten Konstanten verwendet
     */
    private int engState = EMPTY_INPUT;
    /**
     * Zustand der deutschen Nutzereingabe, wird mit den oben deklarierten Konstanten verwendet
     */
    private int gerState = EMPTY_INPUT;
    /**
     * Zustand der japanischen Nutzereingabe, wird mit den oben deklarierten Konstanten verwendet
     */
    private int jpnState = EMPTY_INPUT;
    /**
     * Speicherort des pixelIcon, der vom Nutzer uebergeben werden kann
     */
    private String pixelIconPath = "";
    /**
     * Speicherort des mainIcon, der vom Nutzer uebergeben werden kann
     */
    private String mainIconPath = "";
    /**
     * Frame, von welchem der Dialog aufgerufen wurde
     */
    private final MainFrame frame;
    /**
     * Pokemon, das, wenn der Dialog im Editierungsmodus aufgerufen wurde, dem zu editierenden Pokemon entspricht
     * und sonst null ist
     */
    private Pokemon selectedPokemon;
    /**
     * Gibt an, ob der Dialog im Editierungsmodus aufgerufen wurde
     */
    private boolean isEdit = false;

    /**
     * Konstrukter, der aufgerufen wird, wenn ein neues Pokemon erstellt werden soll
     * @param frame Frame, von welchem der Dialog aufgerufen wurde
     */
    public PokemonDialog(MainFrame frame) {
        super(frame, true);

        this.frame = frame;

        setTitle(Main.guiBundle.getString("addPokemon"));

        createPokemonDialog();

        setVisible(true);
    }

    /**
     * Konstrukter, der aufgerufen wird, wenn ein bestehendes Pokemon bearbeitet werden soll
     * @param frame Frame, von welchem der Dialog aufgerufen wurde
     * @param selectedPokemon Pokemon, das bearbeitet werden soll
     */
    public PokemonDialog(MainFrame frame, Pokemon selectedPokemon) {
        super(frame, true);

        this.frame = frame;

        setTitle(Main.guiBundle.getString("editPokemon"));

        this.selectedPokemon = selectedPokemon;
        isEdit = true;

        if (selectedPokemon.getInformation(Pokemon.ENGLISH) != null) {
            engState = LEGAL_INPUT;
        }
        if (selectedPokemon.getInformation(Pokemon.GERMAN) != null) {
            gerState = LEGAL_INPUT;
        }
        if (selectedPokemon.getInformation(Pokemon.JAPANESE) != null) {
            jpnState = LEGAL_INPUT;
        }
        createPokemonDialog();

        setVisible(true);
    }

    /**
     * Gemeinsame Methode beider Konstruktoren zur Erstellung der GUI-Elemente des Dialogs
     */
    private void createPokemonDialog() {
        setSize(450, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel bgPanel = new JPanel();
        bgPanel.setLayout(new BorderLayout());

        JPanel gridbagPanel = new JPanel(new GridBagLayout());

        GridBagConstraints westGBC = new GridBagConstraints();
        westGBC.insets = new Insets(5, 10, 5, 10);
        westGBC.anchor = GridBagConstraints.WEST;

        GridBagConstraints eastGBC = new GridBagConstraints();
        eastGBC.insets = new Insets(5, 10, 5, 10);
        eastGBC.anchor = GridBagConstraints.WEST;
        eastGBC.gridwidth = GridBagConstraints.REMAINDER;

        LineBorder nameLineBorder = new LineBorder(Color.LIGHT_GRAY);
        TitledBorder nameTitledBorder = new TitledBorder(nameLineBorder, Main.guiBundle.getString("information"));
        nameTitledBorder.setTitleColor(Color.GRAY);
        nameTitledBorder.setTitleFont(Main.secondFont);

        gridbagPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 20, 5, 20),
                nameTitledBorder));

        JLabel idLabel = new JLabel(Main.guiBundle.getString("id"));
        idLabel.setFont(Main.secondFont);
        idLabel.setForeground(Color.DARK_GRAY);
        gridbagPanel.add(idLabel, westGBC);

        JTextField idTextField = new JTextField(15);
        if (isEdit) {
            idTextField.setText(String.format("%03d", selectedPokemon.getPokemonID()));
        } else {
            idTextField.setText(String.format("%03d", Pokemon.getCounter() + 1));
        }
        idTextField.setEnabled(false);

        idTextField.setFont(Main.secondFont);
        idTextField.setForeground(Main.secondColor);
        gridbagPanel.add(idTextField, eastGBC);

        JLabel engNameLabel = new JLabel(Main.guiBundle.getString("nameEnglish"));
        engNameLabel.setFont(Main.secondFont);
        engNameLabel.setForeground(Color.DARK_GRAY);
        gridbagPanel.add(engNameLabel, westGBC);

        JTextField temp;

        if (isEdit) {
            try {
                String name = selectedPokemon.getInformation(Pokemon.ENGLISH).getName();
                temp = new JTextField(name, 15);
            } catch(NullPointerException e) {
                temp = new JTextField(15);
            }
        } else {
            temp = new JTextField(15);
        }

        JTextField engNameTextField = temp;

        engNameTextField.setFont(Main.secondFont);
        engNameTextField.setForeground(Main.secondColor);
        gridbagPanel.add(engNameTextField, eastGBC);

        JLabel gerNameLabel = new JLabel(Main.guiBundle.getString("nameGerman"));
        gerNameLabel.setFont(Main.secondFont);
        gerNameLabel.setForeground(Color.DARK_GRAY);
        gridbagPanel.add(gerNameLabel, westGBC);

        if (isEdit) {
            try {
                String name = selectedPokemon.getInformation(Pokemon.GERMAN).getName();
                temp = new JTextField(name, 15);
            } catch(NullPointerException e) {
                temp = new JTextField(15);
            }
        } else {
            temp = new JTextField(15);
        }

        JTextField gerNameTextField = temp;

        gerNameTextField.setFont(Main.secondFont);
        gerNameTextField.setForeground(Main.secondColor);
        gridbagPanel.add(gerNameTextField, eastGBC);

        JLabel jpnNameLabel = new JLabel(Main.guiBundle.getString("nameJapanese"));
        jpnNameLabel.setFont(Main.secondFont);
        jpnNameLabel.setForeground(Color.DARK_GRAY);
        gridbagPanel.add(jpnNameLabel, westGBC);

        if (isEdit) {
            try {
                String name = selectedPokemon.getInformation(Pokemon.JAPANESE).getName();
                temp = new JTextField(name, 15);
            } catch(NullPointerException e) {
                temp = new JTextField(15);
            }
        } else {
            temp = new JTextField(15);
        }

        JTextField jpnNameTextField = temp;

        jpnNameTextField.setFont(Main.secondFont);
        jpnNameTextField.setForeground(Main.secondColor);
        gridbagPanel.add(jpnNameTextField, eastGBC);


        JLabel firstTypeLabel = new JLabel(Main.guiBundle.getString("firstType"));
        firstTypeLabel.setFont(Main.secondFont);
        firstTypeLabel.setForeground(Color.DARK_GRAY);
        gridbagPanel.add(firstTypeLabel, westGBC);

        ArrayList<String> typeArrayList = new ArrayList<>();

        switch (Locale.getDefault().toString()) {
            case "en" -> {
                EnglishTypeContainer etc = EnglishTypeContainer.instance();
                for (PokemonType pt : etc) {
                    typeArrayList.add(pt.getName());
                }
            }
            case "de" -> {
                GermanTypeContainer gtc = GermanTypeContainer.instance();
                for (PokemonType pt : gtc) {
                    typeArrayList.add(pt.getName());
                }
            }
            case "ja" -> {
                JapaneseTypeContainer jtc = JapaneseTypeContainer.instance();
                for (PokemonType pt : jtc) {
                    typeArrayList.add(pt.getName());
                }
            }
        }

        String[] typeArray = typeArrayList.toArray(new String[0]);
        JComboBox<String> firstTypeComboBox = new JComboBox<>(typeArray);
        firstTypeComboBox.setFont(Main.secondFont);

        switch (Locale.getDefault().toString()) {
            case "en" -> {
                EnglishTypeContainer etc = EnglishTypeContainer.instance();
                for (PokemonType pt : etc) {
                    typeArrayList.add(pt.getName());
                }
            }
            case "de" -> {
                GermanTypeContainer gtc = GermanTypeContainer.instance();
                for (PokemonType pt : gtc) {
                    typeArrayList.add(pt.getName());
                }
            }
            case "ja" -> {
                JapaneseTypeContainer jtc = JapaneseTypeContainer.instance();
                for (PokemonType pt : jtc) {
                    typeArrayList.add(pt.getName());
                }
            }
        }

        if (isEdit) {
            PokemonType t = null;
            if (selectedPokemon.getInformation(Pokemon.ENGLISH) != null) {
                t = selectedPokemon.getInformation(Pokemon.ENGLISH).getFirstType();
            } else if (selectedPokemon.getInformation(Pokemon.GERMAN) != null) {
                GermanTypeContainer gtc = GermanTypeContainer.instance();
                t = selectedPokemon.getInformation(Pokemon.GERMAN).getFirstType();
                t = gtc.getEnglishPokemonType(t);
            } else if (selectedPokemon.getInformation(Pokemon.JAPANESE) != null) {
                JapaneseTypeContainer jtc = JapaneseTypeContainer.instance();
                t = selectedPokemon.getInformation(Pokemon.JAPANESE).getFirstType();
                t = jtc.getEnglishPokemonType(t);
            }
            if (t != null) {
                EnglishTypeContainer etc = EnglishTypeContainer.instance();
                switch (Locale.getDefault().toString()) {
                    case "en" -> {
                        firstTypeComboBox.setSelectedItem(t.getName());
                    }
                    case "de" -> {
                        t = etc.getGermanPokemonType(t);
                        firstTypeComboBox.setSelectedItem(t.getName());
                    }
                    case "ja" -> {
                        t = etc.getJapanesePokemonType(t);
                        firstTypeComboBox.setSelectedItem(t.getName());
                    }
                }
            }
        }

        gridbagPanel.add(firstTypeComboBox, eastGBC);

        JLabel secondTypeLabel = new JLabel(Main.guiBundle.getString("secondType"));
        secondTypeLabel.setFont(Main.secondFont);
        secondTypeLabel.setForeground(Color.DARK_GRAY);
        gridbagPanel.add(secondTypeLabel, westGBC);


        typeArrayList.clear();

        typeArrayList.add("");

        switch (Locale.getDefault().toString()) {
            case "en" -> {
                EnglishTypeContainer etc = EnglishTypeContainer.instance();
                for (PokemonType pt : etc) {
                    typeArrayList.add(pt.getName());
                }
            }
            case "de" -> {
                GermanTypeContainer gtc = GermanTypeContainer.instance();
                for (PokemonType pt : gtc) {
                    typeArrayList.add(pt.getName());
                }
            }
            case "ja" -> {
                JapaneseTypeContainer jtc = JapaneseTypeContainer.instance();
                for (PokemonType pt : jtc) {
                    typeArrayList.add(pt.getName());
                }
            }
        }

        typeArray = typeArrayList.toArray(new String[0]);
        JComboBox<String> secondTypeComboBox = new JComboBox<>(typeArray);
        secondTypeComboBox.setFont(Main.secondFont);

        if (isEdit) {
            PokemonType t = null;
            if (selectedPokemon.getInformation(Pokemon.ENGLISH) != null && selectedPokemon.getInformation(Pokemon.ENGLISH).hasSecondType()) {
                t = selectedPokemon.getInformation(Pokemon.ENGLISH).getSecondType();
            } else if (selectedPokemon.getInformation(Pokemon.GERMAN) != null && selectedPokemon.getInformation(Pokemon.GERMAN).hasSecondType()) {
                GermanTypeContainer gtc = GermanTypeContainer.instance();
                t = selectedPokemon.getInformation(Pokemon.GERMAN).getSecondType();
                t = gtc.getEnglishPokemonType(t);
            } else if (selectedPokemon.getInformation(Pokemon.JAPANESE) != null && selectedPokemon.getInformation(Pokemon.JAPANESE).hasSecondType()) {
                JapaneseTypeContainer jtc = JapaneseTypeContainer.instance();
                t = selectedPokemon.getInformation(Pokemon.JAPANESE).getSecondType();
                t = jtc.getEnglishPokemonType(t);
            }
            if (t != null) {
                EnglishTypeContainer etc = EnglishTypeContainer.instance();
                switch (Locale.getDefault().toString()) {
                    case "en" -> {
                        secondTypeComboBox.setSelectedItem(t.getName());
                    }
                    case "de" -> {
                        t = etc.getGermanPokemonType(t);
                        secondTypeComboBox.setSelectedItem(t.getName());
                    }
                    case "ja" -> {
                        t = etc.getJapanesePokemonType(t);
                        secondTypeComboBox.setSelectedItem(t.getName());
                    }
                }
            }
        }

        gridbagPanel.add(secondTypeComboBox, eastGBC);

        Integer[] evoArray = {0, 1, 2};

        JLabel evolutionLabel = new JLabel(Main.guiBundle.getString("evolution"));
        evolutionLabel.setFont(Main.secondFont);
        evolutionLabel.setForeground(Color.DARK_GRAY);
        gridbagPanel.add(evolutionLabel, westGBC);

        JComboBox<Integer> evolutionComboBox = new JComboBox<Integer>(evoArray);
        evolutionComboBox.setFont(Main.secondFont);

        if (isEdit) {
            evolutionComboBox.setSelectedItem(selectedPokemon.getEvolution());
        }

        gridbagPanel.add(evolutionComboBox, eastGBC);

        JLabel pixelIconLabel = new JLabel(Main.guiBundle.getString("pixelIcon"));
        pixelIconLabel.setFont(Main.secondFont);
        pixelIconLabel.setForeground(Color.DARK_GRAY);
        gridbagPanel.add(pixelIconLabel, westGBC);

        JButton choosePixelIconButton = new JButton(Main.guiBundle.getString("selectImage") + " (120px²)");
        choosePixelIconButton.setFont(Main.secondFont);
        gridbagPanel.add(choosePixelIconButton, eastGBC);

        JLabel mainIconLabel = new JLabel(Main.guiBundle.getString("mainIcon"));
        mainIconLabel.setFont(Main.secondFont);
        mainIconLabel.setForeground(Color.DARK_GRAY);
        gridbagPanel.add(mainIconLabel, westGBC);

        JButton chooseMainIconButton = new JButton(Main.guiBundle.getString("selectImage") + " (400px²)");
        chooseMainIconButton.setFont(Main.secondFont);
        gridbagPanel.add(chooseMainIconButton, eastGBC);

        bgPanel.add(gridbagPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton okButton = new JButton(Main.guiBundle.getString("ok"));
        okButton.setFont(Main.secondFont);
        buttonPanel.add(okButton);
        JButton cancelButton = new JButton(Main.guiBundle.getString("cancel"));
        cancelButton.setFont(Main.secondFont);
        buttonPanel.add(cancelButton);

        bgPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bgPanel);

        choosePixelIconButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png"));
            int rval = fileChooser.showOpenDialog(null);
            if(rval == JFileChooser.APPROVE_OPTION) {
                pixelIconPath = fileChooser.getSelectedFile().getPath();
                choosePixelIconButton.setText(fileChooser.getSelectedFile().getName());
            }
        });

        chooseMainIconButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "png"));
            int rval = fileChooser.showOpenDialog(null);
            if(rval == JFileChooser.APPROVE_OPTION) {
                mainIconPath = fileChooser.getSelectedFile().getPath();
                chooseMainIconButton.setText(fileChooser.getSelectedFile().getName());
            }
        });

        okButton.addActionListener(e -> {
            Pokemon p = new Pokemon();

            PokemonType firstTypeEnglish = null;
            PokemonType secondTypeEnglish = null;

            PokemonType firstTypeGerman = null;
            PokemonType secondTypeGerman = null;

            PokemonType firstTypeJapanese = null;
            PokemonType secondTypeJapanese = null;

            switch (Locale.getDefault().toString()) {
                case "en" -> {
                    EnglishTypeContainer etc = EnglishTypeContainer.instance();
                    firstTypeEnglish = etc.getPokemonType(Objects.requireNonNull(firstTypeComboBox.getSelectedItem()).toString());
                    firstTypeGerman = etc.getGermanPokemonType(firstTypeEnglish);
                    firstTypeJapanese = etc.getJapanesePokemonType(firstTypeEnglish);
                    if (!Objects.requireNonNull(secondTypeComboBox.getSelectedItem()).toString().isEmpty()) {
                        secondTypeEnglish = etc.getPokemonType(Objects.requireNonNull(secondTypeComboBox.getSelectedItem()).toString());
                        secondTypeGerman = etc.getGermanPokemonType(secondTypeEnglish);
                        secondTypeJapanese = etc.getJapanesePokemonType(secondTypeEnglish);
                    }
                }
                case "de" -> {
                    GermanTypeContainer gtc = GermanTypeContainer.instance();
                    firstTypeGerman = gtc.getPokemonType(Objects.requireNonNull(firstTypeComboBox.getSelectedItem()).toString());
                    firstTypeEnglish = gtc.getEnglishPokemonType(firstTypeGerman);
                    firstTypeJapanese = gtc.getJapanesePokemonType(firstTypeGerman);
                    if (!Objects.requireNonNull(secondTypeComboBox.getSelectedItem()).toString().isEmpty()) {
                        secondTypeGerman = gtc.getPokemonType(Objects.requireNonNull(firstTypeComboBox.getSelectedItem()).toString());
                        secondTypeEnglish = gtc.getEnglishPokemonType(firstTypeGerman);
                        secondTypeJapanese = gtc.getJapanesePokemonType(firstTypeGerman);
                    }
                }
                case "ja" -> {
                    JapaneseTypeContainer jtc = JapaneseTypeContainer.instance();
                    firstTypeJapanese = jtc.getPokemonType(Objects.requireNonNull(firstTypeComboBox.getSelectedItem()).toString());
                    firstTypeEnglish = jtc.getEnglishPokemonType(firstTypeJapanese);
                    firstTypeGerman = jtc.getGermanPokemonType(firstTypeJapanese);
                    if (!Objects.requireNonNull(secondTypeComboBox.getSelectedItem()).toString().isEmpty()) {
                        secondTypeJapanese = jtc.getPokemonType(Objects.requireNonNull(secondTypeComboBox.getSelectedItem()).toString());
                        secondTypeEnglish = jtc.getEnglishPokemonType(secondTypeJapanese);
                        secondTypeGerman = jtc.getGermanPokemonType(secondTypeJapanese);
                    }
                }
            }

            if (engState == LEGAL_INPUT) {
                if (isEdit) {
                    Information engInfo = new Information(selectedPokemon.getID(), engNameTextField.getText(), firstTypeEnglish, secondTypeEnglish, Pokemon.ENGLISH);
                    selectedPokemon.setInformation(engInfo, Pokemon.ENGLISH);
                } else {
                    Information engInfo = new Information(p.getID(), engNameTextField.getText(), firstTypeEnglish, secondTypeEnglish, Pokemon.ENGLISH);
                    p.setInformation(engInfo, Pokemon.ENGLISH);
                }
            }

            if (gerState == LEGAL_INPUT) {
                if (isEdit) {
                    Information gerInfo = new Information(selectedPokemon.getID(), gerNameTextField.getText(), firstTypeGerman, secondTypeGerman, Pokemon.GERMAN);
                    selectedPokemon.setInformation(gerInfo, Pokemon.GERMAN);
                } else {
                    Information gerInfo = new Information(p.getID(), gerNameTextField.getText(), firstTypeGerman, secondTypeGerman, Pokemon.GERMAN);
                    p.setInformation(gerInfo, Pokemon.GERMAN);
                }
            }

            if (jpnState == LEGAL_INPUT) {
                if (isEdit) {
                    Information jpnInfo = new Information(selectedPokemon.getID(), jpnNameTextField.getText(), firstTypeJapanese, secondTypeJapanese, Pokemon.JAPANESE);
                    selectedPokemon.setInformation(jpnInfo, Pokemon.JAPANESE);
                } else {
                    Information jpnInfo = new Information(p.getID(), jpnNameTextField.getText(), firstTypeJapanese, secondTypeJapanese, Pokemon.JAPANESE);
                    p.setInformation(jpnInfo, Pokemon.JAPANESE);
                }
            }

            if (engState != ILLEGAL_INPUT && gerState != ILLEGAL_INPUT && jpnState != ILLEGAL_INPUT) {
                if (engState == LEGAL_INPUT || gerState == LEGAL_INPUT || jpnState == LEGAL_INPUT) {
                    PokemonContainer pc = PokemonContainer.instance();
                    if (isEdit) {
                        Pokemon.counterDecrement();
                        selectedPokemon.setEvolution((int) evolutionComboBox.getSelectedItem());
                        if (!pixelIconPath.isEmpty()) {
                            selectedPokemon.setPixelIcon(readAndWritePixelIcon(selectedPokemon.getPokemonID()));
                        }
                        if (!mainIconPath.isEmpty()) {
                            selectedPokemon.setMainIcon(readAndWriteMainIcon(selectedPokemon.getPokemonID()));
                        }
                        if (engState == EMPTY_INPUT) {
                            selectedPokemon.setInformation(null, Pokemon.ENGLISH);
                        }
                        if (gerState == EMPTY_INPUT) {
                            selectedPokemon.setInformation(null, Pokemon.GERMAN);
                        }
                        if (jpnState == EMPTY_INPUT) {
                            selectedPokemon.setInformation(null, Pokemon.JAPANESE);
                        }

                        pc.updatePokemon(selectedPokemon);
                    } else {
                        p.setEvolution((int) evolutionComboBox.getSelectedItem());
                        if (!pixelIconPath.isEmpty()) {
                            p.setPixelIcon(readAndWritePixelIcon(p.getPokemonID()));
                        } else {
                            p.setPixelIcon(getMissing());
                        }
                        if (!mainIconPath.isEmpty()) {
                            p.setMainIcon(readAndWriteMainIcon(p.getPokemonID()));
                        }
                        pc.addPokemon(p);
                    }

                    frame.updatePokemonModel();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, Main.guiBundle.getString("errorMissingName"), Main.guiBundle.getString("addPokemon"), JOptionPane.ERROR_MESSAGE);
                    Pokemon.counterDecrement();
                }
            } else {
                Pokemon.counterDecrement();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        engNameTextField.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                validate();
            }

            public void removeUpdate(DocumentEvent e) {
                validate();
            }

            public void changedUpdate(DocumentEvent e) {
                validate();
            }
            public void validate() {
                String enteredName = engNameTextField.getText();
                if (enteredName.isEmpty()) {
                    engState = EMPTY_INPUT;
                } else {
                    try {
                        new Information(0, enteredName, null, Pokemon.ENGLISH);
                        engNameTextField.setForeground(Main.secondColor);
                        engState = LEGAL_INPUT;
                    } catch(IllegalArgumentException e) {
                        engNameTextField.setForeground(Main.illegalColor);
                        engState = ILLEGAL_INPUT;
                    }
                }
            }
        });

        gerNameTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validate();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                validate();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                validate();
            }
            public void validate() {
                String enteredName = gerNameTextField.getText();
                if (enteredName.isEmpty()) {
                    gerState = EMPTY_INPUT;
                } else {
                    try {
                        new Information(0, enteredName, null, Pokemon.GERMAN);
                        gerNameTextField.setForeground(Main.secondColor);
                        gerState = LEGAL_INPUT;
                    } catch(IllegalArgumentException e) {
                        gerNameTextField.setForeground(Main.illegalColor);
                        gerState = ILLEGAL_INPUT;
                    }
                }
            }
        });

        jpnNameTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validate();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                validate();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                validate();
            }
            public void validate() {
                String enteredName = jpnNameTextField.getText();
                if (enteredName.isEmpty()) {
                    jpnState = EMPTY_INPUT;
                } else {
                    try {
                        new Information(0, enteredName, null, Pokemon.JAPANESE);
                        jpnNameTextField.setForeground(Main.secondColor);
                        jpnState = LEGAL_INPUT;
                    } catch(IllegalArgumentException e) {
                        jpnNameTextField.setForeground(Main.illegalColor);
                        jpnState = ILLEGAL_INPUT;
                    }
                }
            }
        });
    }

    /**
     * Einlesen des Bildes, dessen Pfad der Nutzer uebergeben hat, dann ggf. Anpassung der Größe und zuletzt Abspeicherung im Dateisystem des Projekts
     * @param pokemonID ID des Pokemon, fuer welches das pixelIcon gelesen und gespeichert wird
     * @return pixelIcon, ein ImageIcon erstellt aus dem Bild, das eingelesen wurde
     */
    public ImageIcon readAndWritePixelIcon(int pokemonID) {
        File outputfile = new File(System.getProperty("user.dir") + "/src/images/pixel_icons/" + String.format("%04d", pokemonID) + ".png");
        try {
            BufferedImage pixelIcon = ImageIO.read(new File(pixelIconPath));

            if (pixelIcon.getHeight() != 120 || pixelIcon.getWidth() != 120) {
                BufferedImage resizedPixelIcon = new BufferedImage(120, 120, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = resizedPixelIcon.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.drawImage(pixelIcon, 0, 0, 120, 120, null);
                g.dispose();
                pixelIcon = resizedPixelIcon;
            }
            ImageIO.write(pixelIcon, "png", outputfile);
            return new ImageIcon(pixelIcon);
        } catch (IOException ignored) { }
        return null;
    }

    /**
     * Einlesen des Bildes, dessen Pfad der Nutzer uebergeben hat, dann ggf. Anpassung der Größe und zuletzt Abspeicherung im Dateisystem des Projekts
     * @param pokemonID ID des Pokemon, fuer welches das mainIcon gelesen und gespeichert wird
     * @return mainIcon, ein ImageIcon erstellt aus dem Bild, das eingelesen wurde
     */
    public ImageIcon readAndWriteMainIcon(int pokemonID) {
        File outputfile = new File(System.getProperty("user.dir") + "/src/images/main_icons/" + String.format("%04d", pokemonID) + ".png");
        try {
            BufferedImage mainIcon = ImageIO.read(new File(mainIconPath));

            if (mainIcon.getHeight() != 400 || mainIcon.getWidth() != 400) {
                BufferedImage resizedMainIcon = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = resizedMainIcon.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.drawImage(mainIcon, 0, 0, 400, 400, null);
                g.dispose();
                mainIcon = resizedMainIcon;
            }

            ImageIO.write(mainIcon, "png", outputfile);
            return new ImageIcon(mainIcon);
        } catch (IOException ignored) { }
        return null;
    }
    /**
     * Bereitstellung des Platzhalter-Icons fuer Pokemon ohne ein vom Nutzer uebergebenes pixelIcon
     * @return Platzhalter-pixelIcon
     */
    private static ImageIcon getMissing() {
        if (missing == null) {
            try {
                missing = new ImageIcon(ImageIO.read(new File(System.getProperty("user.dir") + "/src/UserInterface/res/pokemon/missing.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return missing;
    }
}