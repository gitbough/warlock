import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class BuilderGUI extends JFrame implements ActionListener {

    private JButton buttonSave;
    private JButton buttonDelete;
    private JButton buttonLoad;
    private JComboBox<String> comboDeck;
    private JComboBox<String> comboAnfuehrer;
    private JComboBox<String> comboGott;
    private JLabel labelName;
    private JLabel labelKarte;
    private JLabel labelAnfuehrer;
    private JLabel labelGott;
    private JLabel labelKarten;
    private JLabel labelGold;
    private JPanel panel1;
    private JPanel panel2;
    private JScrollPane paneTableAlle;
    private JScrollPane paneTableDeck;
    private JSeparator separator;
    private JSplitPane splitpane;
    private JTable tableAlle;
    private JTable tableDeck;
    private JTextField textName;

    private int imageX = 200;
    private int imageY = 279;

    private DefaultTableModel modelAlle;
    private DefaultTableModel modelDeck;

    int karten=0, gold=0, kartenlimit=30, goldlimit=10;

    private ArrayList<File> deckFiles = new ArrayList<File>();

    public BuilderGUI() {
        buildGUI();
        initTableAlle();
        initTableDeck();
        findeDecks();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==comboAnfuehrer || ae.getSource()==comboGott || ae.getSource()==comboDeck){
            recount();
        } else if (ae.getSource()==buttonSave){
            saveCurrentDeck();
        } else if (ae.getSource()==buttonLoad){
            loadCurrentDeck();
        } else if (ae.getSource()==buttonDelete){
            deleteCurrentDeck();
        }
    }

    private void loadCurrentDeck(){
        if (modelDeck.getRowCount()>0) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Deck Laden?\n(Nicht gespeicherte Änderungen gehen verloren.)", "Title on Box", dialogButton);
            if (dialogResult != 0) {
                return;
            }
        }
        File file = (deckFiles.get(comboDeck.getSelectedIndex()-1));
        if (!file.exists()){
            fehlerMitDatei();
        }
        ArrayList<Karte> deck = new ArrayList<Karte>();
        String name=""; int anfuehrer=0; int gott=0;
        boolean bname=false; boolean banfuehrer=false; boolean bgott=false;
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String str = reader.nextLine();
                boolean correctEndOfString = str.substring(str.length() - 1).equals(";");
                if (str.length() > 9 && str.substring(0, 9).equals("deckname=") && correctEndOfString && !bname) {
                    name = str.substring(9, str.length() - 1);
                    bname = true;
                } else if (str.length() > 10 && str.substring(0, 10).equals("anfuehrer=") && correctEndOfString && !banfuehrer) {
                    anfuehrer = Integer.parseInt(str.substring(10, str.length() - 1));
                    banfuehrer = true;
                } else if (str.length() > 5 && str.substring(0, 5).equals("gott=") && correctEndOfString && !bgott) {
                    gott = Integer.parseInt(str.substring(5, str.length() - 1));
                    bgott = true;
                } else if (str.length() > 6 && str.substring(0, 6).equals("karte=") && correctEndOfString) {
                    int i = Integer.parseInt(str.substring(6, str.length() - 1));
                    if (i>=1 && i!=1 && i!= 31 && i!=61 && i!=91 && i!=121 && i!=151 && i!=181 && i!=211 && i!=241 && i<=300){
                        deck.add(new Karte(i));
                    }
                }
            }
            reader.close();
        } catch (Exception ignored){
            fehlerMitDatei();
        }
        if(bname && banfuehrer && bgott && Main.checkDeck(deck, anfuehrer, gott)) {
            textName.setText(name);
            comboAnfuehrer.setSelectedIndex(anfuehrer);
            comboGott.setSelectedIndex(gott);
            //Entferne alle Karten aus tableDeck
            for (int i = modelDeck.getRowCount() - 1; i >= 0; i--) {
                modelDeck.removeRow(i);
            }
            //Fülle tableAlle komplett auf
            initTableAlle();
            //Füge Karten tableDeck hinzu und entferne sie aus tableAlle
            for (int i = 0; i < deck.size(); i++) {
                Karte karte = deck.get(i);
                int nr = karte.getNummer();
                modelDeck.addRow(new Object[]{
                        get3Stellig(nr),
                        karte.getName(),
                        karte.getFraktion(),
                        karte.getTyp(),
                        karte.getF(),
                        karte.getE(),
                        karte.hatSymbole(),
                        karte.hatSF(),
                        karte.getGold()
                });
                for (int j = 0; j < modelAlle.getRowCount(); j++) {
                    if (Integer.parseInt((String) modelAlle.getValueAt(j, 0)) == nr) {
                        modelAlle.removeRow(j);
                    }
                }

            }
            recount();
            buttonLoad.setEnabled(false);
            buttonSave.setEnabled(false);
        } else {
            fehlerMitDatei();
        }
    }

    private void fehlerMitDatei(){
        JOptionPane.showMessageDialog(this, "Fehlerhafte Deckdatei.");
    }

    private void deleteCurrentDeck(){
        int n = comboDeck.getSelectedIndex()-1;
        String name = deckFiles.get(n).getName();
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Dieses Deck wirklich Löschen?\n\""+name+"\"", "Title on Box", dialogButton);
        if(dialogResult != 0) {
            return;
        }
        if (deckFiles.get(n).delete()) {
            deckFiles.remove(n);
            comboDeck.setSelectedIndex(0);
            comboDeck.removeItemAt(n+1);
            //System.out.println("Erfolgreich gelöscht");
        } else {
            //System.out.println("Löschen ging nicht.");
        }
    }

    private void saveCurrentDeck(){
        String name = textName.getText().replaceAll("[^a-zA-Z0-9_\\s]", "");
        if(name.length()>40){
            name = name.substring(0,40);
        }
        textName.setText(name);
        if (name.equals(""))
            return;
        String dateiName = System.getProperty("user.dir") + "\\warlock_userdata\\" + name + ".wdeck";
        File file = new File(dateiName);
        if (file.exists()){
            if(((String)comboDeck.getSelectedItem()).toLowerCase().equals(name.toLowerCase())){
                for (int i=0; i<deckFiles.size(); i++){
                    if(deckFiles.get(i).getName().toLowerCase().equals(name.toLowerCase())){
                        if (deckFiles.get(i).delete()) {
                            deckFiles.remove(i);
                            //System.out.println("Erfolgreich gelöscht");
                            break;
                        } else {
                            //System.out.println("Löschen ging nicht");
                            return;
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Eine Deck mit diesem Namen existiert bereits.");
                return;
            }
        }
        try{
            file.createNewFile();
            FileWriter writer = new FileWriter(dateiName);
            writer.write("deckname=" + name + ";\n");
            writer.write("anfuehrer=" + comboAnfuehrer.getSelectedIndex() + ";\n"); //TODO KARTENNUMMER WIEDERSPIEGELN
            writer.write("gott=" + comboGott.getSelectedIndex() + ";\n"); //TODO KARTENNUMMER WIEDERSPIEGELN
            for (int i=0; i<modelDeck.getRowCount(); i++){
                writer.write("karte="+modelDeck.getValueAt(i,0) + ";\n"); //TODO TAB + KARTENNAME ALS KOMMENTAR HINTEN DRAN
            }
            writer.close();
            if (comboDeck.getSelectedIndex()==0 || !comboDeck.getItemAt(comboDeck.getSelectedIndex()).toLowerCase().equals(name.toLowerCase())){
                deckFiles.add(file);
                comboDeck.addItem(name);
                comboDeck.setSelectedItem(name);
            }
            buttonSave.setEnabled(false);
            buttonLoad.setEnabled(false);
        }catch(Exception e){
            //TODO FEHLER
            //System.out.println("Fehler: "+e);
        }
    }

    private void findeDecks(){
        File folder = new File(System.getProperty("user.dir") + "\\warlock_userdata");
        if (!folder.exists())
            new File(System.getProperty("user.dir") + "\\warlock_userdata").mkdirs();
        File[] listOfFiles = folder.listFiles();
        for(int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().length()>6 && listOfFiles[i].getName().substring(listOfFiles[i].getName().length()-6).equals(".wdeck")) {
                //System.out.println(listOfFiles[i].getName());
                deckFiles.add(listOfFiles[i]);
            }
        }
        comboDeck.addItem("<<Neues Deck>>");
        comboDeck.setSelectedIndex(0);
        textName.setText("");
        if (deckFiles.size()>0) {
            addeGefundeneDecks();
        }
    }

    public void addeGefundeneDecks(){
        for(int i=0; i<deckFiles.size(); i++){
            String name = deckFiles.get(i).getName();
            comboDeck.addItem(name.substring(0,name.length()-6));
        }
    }

    private void recount() {
        String fraktion="";
        switch(comboAnfuehrer.getSelectedIndex()){
            case 0:
                fraktion = "Vedalken";
                break;
            case 1:
                fraktion = "Sterbliche";
                break;
            case 2:
                fraktion = "Elfen";
                break;
            case 3:
                fraktion = "Avior";
                break;
            case 4:
                fraktion = "Halblinge";
                break;
            case 5:
                fraktion = "Orks";
                break;
            case 6:
                fraktion = "Zwerge";
                break;
            case 7:
                fraktion = "Meervolk";
                break;
            case 8:
                fraktion = "Fremde";
                break;
        }
        kartenlimit=0; goldlimit=0;
        switch(comboGott.getSelectedIndex()){
            case 0:
                kartenlimit=30;
                goldlimit=10;
                break;
            case 1:
                kartenlimit=35;
                goldlimit=15;
                break;
            case 2:
                kartenlimit=40;
                goldlimit=25;
                break;
            case 3:
                kartenlimit=45;
                goldlimit=20;
                break;
        }
        karten=0; gold=0;
        for(int i=0; i<modelDeck.getRowCount(); i++){
            karten += 1;
            Karte k = new Karte(Integer.parseInt((String)modelDeck.getValueAt(i,0)));
            if(!fraktion.equals(k.getFraktion()))
                gold += k.getGold();
        }
        labelKarten.setText("Karten: " + karten + "/" + kartenlimit);
        labelGold.setText("Gold: " + gold + "/" + goldlimit);

        setButtonSaveEnabled();
        buttonLoad.setEnabled(comboDeck.getSelectedIndex()!=0);
        buttonDelete.setEnabled(comboDeck.getSelectedIndex()!=0);
    }

    private String get3Stellig(int n){
        if (n>=100){
            return Integer.toString(n);
        } else if (n>=10){
            return "0" + Integer.toString(n);
        } else {
            return "00" + Integer.toString(n);
        }
    }

    private void initTableDeck(){
        modelDeck = new DefaultTableModel();
        JTable tableDeck = new JTable(modelDeck){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableDeck.setShowVerticalLines(false);
        tableDeck.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableDeck.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                try {
                    int nr = Integer.parseInt((String)(tableDeck.getValueAt(tableDeck.getSelectedRow(), 0)));
                    String filename = "/images200/" + new Karte(nr).getFileName();
                    ImageIcon ii = new javax.swing.ImageIcon(getClass().getResource(filename));
                    ii = new ImageIcon(ii.getImage().getScaledInstance(imageX, imageY, Image.SCALE_SMOOTH));
                    labelKarte.setIcon(ii);
                }catch(Exception ignored){}
            }
        });
        tableDeck.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount()>0 && mouseEvent.getClickCount()%2==0) { //Doppelklick -> Add to deck
                    if(table.getSelectedRow() != -1) {
                        int nr = Integer.parseInt((String)table.getValueAt(table.getSelectedRow(),0));
                        Karte karte = new Karte(nr);
                        modelAlle.addRow(new Object[]{
                                get3Stellig(nr),
                                karte.getName(),
                                karte.getFraktion(),
                                karte.getTyp(),
                                karte.getF(),
                                karte.getE(),
                                karte.hatSymbole(),
                                karte.hatSF(),
                                karte.getGold()
                        });
                        ((DefaultTableModel) table.getModel()).removeRow(tableDeck.convertRowIndexToModel(table.getSelectedRow()));
                        recount();
                    }
                } else { //Normaler Klick -> Zeige als Karte an
                    int nr = Integer.parseInt((String)(tableDeck.getValueAt(tableDeck.getSelectedRow(), 0)));
                    String filename = "/images200/" + new Karte(nr).getFileName();
                    //labelKarte.setIcon(new javax.swing.ImageIcon(getClass().getResource(filename)));
                    ImageIcon ii = new javax.swing.ImageIcon(getClass().getResource(filename));
                    ii = new ImageIcon(ii.getImage().getScaledInstance(imageX, imageY, Image.SCALE_SMOOTH));
                    labelKarte.setIcon(ii);
                }
            }
        });
        tableDeck.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        tableDeck.getActionMap().put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int n = tableDeck.getSelectedRow();
                if(n != -1) {
                    int nr = Integer.parseInt((String)tableDeck.getValueAt(n,0));
                    Karte karte = new Karte(nr);
                    modelAlle.addRow(new Object[]{
                            get3Stellig(nr),
                            karte.getName(),
                            karte.getFraktion(),
                            karte.getTyp(),
                            karte.getF(),
                            karte.getE(),
                            karte.hatSymbole(),
                            karte.hatSF(),
                            karte.getGold()
                    });
                    ((DefaultTableModel)tableDeck.getModel()).removeRow(tableDeck.convertRowIndexToModel(n));
                    recount();
                    if(n >= tableDeck.getModel().getRowCount())
                        n = tableDeck.getModel().getRowCount()-1;
                    tableDeck.changeSelection(n,0, false, false);
                }
            }
        });
        modelDeck.addColumn("Nr");
        modelDeck.addColumn("Name");
        modelDeck.addColumn("Fraktion");
        modelDeck.addColumn("Typ");
        modelDeck.addColumn("Feuer");
        modelDeck.addColumn("Erde");
        modelDeck.addColumn("Symbole");
        modelDeck.addColumn("Sonderfunktion");
        modelDeck.addColumn("Gold");

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelDeck);
        tableDeck.setRowSorter(sorter);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>(300);
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

        tableDeck.setFillsViewportHeight(true);
        paneTableDeck.setViewportView(tableDeck);
    }

    private void initTableAlle(){
        modelAlle = new DefaultTableModel();
        JTable tableAlle = new JTable(modelAlle){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableAlle.setShowVerticalLines(false);
        tableAlle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableAlle.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                try {
                    int nr = Integer.parseInt((String)(tableAlle.getValueAt(tableAlle.getSelectedRow(), 0)));
                    String filename = "/images200/" + new Karte(nr).getFileName();
                    //labelKarte.setIcon(new javax.swing.ImageIcon(getClass().getResource(filename)));
                    ImageIcon ii = new javax.swing.ImageIcon(getClass().getResource(filename));
                    ii = new ImageIcon(ii.getImage().getScaledInstance(imageX, imageY, Image.SCALE_SMOOTH));
                    labelKarte.setIcon(ii);
                }catch(Exception ignored){}
            }
        });
        tableAlle.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount()>0 && mouseEvent.getClickCount()%2==0) { //Doppelklick -> Add to deck
                    if(table.getSelectedRow() != -1) {
                        int nr = Integer.parseInt((String)table.getValueAt(table.getSelectedRow(),0));
                        Karte karte = new Karte(nr);
                        modelDeck.addRow(new Object[]{
                                get3Stellig(nr),
                                karte.getName(),
                                karte.getFraktion(),
                                karte.getTyp(),
                                karte.getF(),
                                karte.getE(),
                                karte.hatSymbole(),
                                karte.hatSF(),
                                karte.getGold()
                        });
                        ((DefaultTableModel) table.getModel()).removeRow(tableAlle.convertRowIndexToModel(table.getSelectedRow()));
                        recount();
                    }
                } else { //Normaler Klick -> Zeige als Karte an
                    int nr = Integer.parseInt((String)(tableAlle.getValueAt(tableAlle.getSelectedRow(), 0)));
                    String filename = "/images200/" + new Karte(nr).getFileName();
                    //labelKarte.setIcon(new javax.swing.ImageIcon(getClass().getResource(filename)));
                    ImageIcon ii = new javax.swing.ImageIcon(getClass().getResource(filename));
                    ii = new ImageIcon(ii.getImage().getScaledInstance(imageX, imageY, Image.SCALE_SMOOTH));
                    labelKarte.setIcon(ii);
                }
            }
        });
        tableAlle.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        tableAlle.getActionMap().put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int n = tableAlle.getSelectedRow();
                if(n != -1) {
                    int nr = Integer.parseInt((String)tableAlle.getValueAt(n,0));
                    Karte karte = new Karte(nr);
                    modelDeck.addRow(new Object[]{
                            get3Stellig(nr),
                            karte.getName(),
                            karte.getFraktion(),
                            karte.getTyp(),
                            karte.getF(),
                            karte.getE(),
                            karte.hatSymbole(),
                            karte.hatSF(),
                            karte.getGold()
                    });
                    ((DefaultTableModel)tableAlle.getModel()).removeRow(tableAlle.convertRowIndexToModel(n));
                    recount();
                    if(n >= tableAlle.getModel().getRowCount())
                        n = tableAlle.getModel().getRowCount()-1;
                    tableAlle.changeSelection(n,0, false, false);
                }
            }
        });
        modelAlle.addColumn("Nr");
        modelAlle.addColumn("Name");
        modelAlle.addColumn("Fraktion");
        modelAlle.addColumn("Typ");
        modelAlle.addColumn("Feuer");
        modelAlle.addColumn("Erde");
        modelAlle.addColumn("Symbole");
        modelAlle.addColumn("Sonderfunktion");
        modelAlle.addColumn("Gold");
        for (int i=1; i<=240; i++){
            if (i!=1 && i!= 31 && i!=61 && i!=91 && i!=121 && i!=151 && i!=181 && i!=211 && i!=241) {
                Karte karte = new Karte(i);
                modelAlle.addRow(new Object[]{
                        get3Stellig(i),
                        karte.getName(),
                        karte.getFraktion(),
                        karte.getTyp(),
                        karte.getF(),
                        karte.getE(),
                        karte.hatSymbole(),
                        karte.hatSF(),
                        karte.getGold()
                });
            }
        }
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelAlle);
        tableAlle.setRowSorter(sorter);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>(300);
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);

        tableAlle.setFillsViewportHeight(true);
        paneTableAlle.setViewportView(tableAlle);
    }

    private void setButtonSaveEnabled(){
        buttonSave.setEnabled(karten==kartenlimit && gold<=goldlimit && textName.getText().length()>0);
    }

    private void buildGUI() {
        splitpane = new javax.swing.JSplitPane();
        paneTableAlle = new javax.swing.JScrollPane();
        panel1 = new javax.swing.JPanel();
        paneTableDeck = new javax.swing.JScrollPane();
        tableDeck = new javax.swing.JTable();
        panel2 = new javax.swing.JPanel();
        labelName = new javax.swing.JLabel();
        textName = new javax.swing.JTextField();
        textName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                setButtonSaveEnabled();
            }
            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                setButtonSaveEnabled();
            }
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                setButtonSaveEnabled();
            }
        });
        buttonSave = new javax.swing.JButton();
        buttonSave.addActionListener(this);
        buttonDelete = new javax.swing.JButton();
        buttonDelete.addActionListener(this);
        comboDeck = new javax.swing.JComboBox<>();
        comboDeck.addActionListener(this);
        comboAnfuehrer = new javax.swing.JComboBox<>();
        comboAnfuehrer.addActionListener(this);
        labelAnfuehrer = new javax.swing.JLabel();
        labelGott = new javax.swing.JLabel();
        comboGott = new javax.swing.JComboBox<>();
        comboGott.addActionListener(this);
        buttonLoad = new javax.swing.JButton();
        buttonLoad.addActionListener(this);
        labelKarten = new javax.swing.JLabel();
        labelGold = new javax.swing.JLabel();
        labelKarte = new javax.swing.JLabel();
        separator = new javax.swing.JSeparator();

        //TABLE ALLE
        tableAlle = new javax.swing.JTable();
        tableAlle.setCellSelectionEnabled(false);

        splitpane.setResizeWeight(0.7);

        splitpane.setLeftComponent(paneTableAlle);
        paneTableDeck.setViewportView(tableDeck);

        labelName.setText("Deckname");

        buttonSave.setText("Speichern");
        buttonSave.setEnabled(false);

        buttonDelete.setText("Löschen");
        buttonDelete.setEnabled(false);

        //comboDeck.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[neues Deck]" }));
        comboDeck.setModel(new javax.swing.DefaultComboBoxModel<>());

        comboAnfuehrer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
                "Meister Höchstmagus Merlin IV",
                "König Kenhart der Gerechte",
                "Ilanwe, die Herrin der Wälder",
                "Mutter Phönix",
                "Drogo, Halbling Vereiniger",
                "Gorgol, der Zerbrecher",
                "Bombur, König im Berg",
                "Ozeankaiser Teshyo",
                "Erster Kapitän Xilana Paal"
        }));


        labelAnfuehrer.setText("Anführer");

        labelGott.setText("Gott");

        comboGott.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
                "Donnerfaust, Gott des Krieges",
                "Argusauge, Göttin der Wahrheit",
                "Eisenfuß, Gott des Glücks",
                "Schlitzohr, Gott der Zeit"
        }));

        buttonLoad.setText("Laden");
        buttonLoad.setEnabled(false);

        labelKarten.setText("Karten: 0/30");

        labelGold.setText("Gold: 0/10");


        //labelKarte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images200/000.png")));
        ImageIcon ii = new javax.swing.ImageIcon(getClass().getResource("/images200/000.png"));
        ii = new ImageIcon(ii.getImage().getScaledInstance(imageX, imageY, Image.SCALE_SMOOTH));
        labelKarte.setIcon(ii);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(labelName)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(textName)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonSave))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(comboDeck, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(buttonLoad)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonDelete))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(labelAnfuehrer)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(comboAnfuehrer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(labelKarten))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(labelGold)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(labelGott)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(comboGott, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addComponent(separator))
                                .addGap(18, 18, 18)
                                .addComponent(labelKarte))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(comboDeck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(buttonLoad)
                                                                        .addComponent(buttonDelete))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(labelName)
                                                                        .addComponent(textName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(buttonSave))
                                                                .addGap(18, 18, 18)
                                                                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(labelAnfuehrer)
                                                                        .addComponent(comboAnfuehrer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(comboGott, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(labelGott))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                        .addComponent(labelKarten)
                                                                        .addComponent(labelGold))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addComponent(labelKarte))))
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(paneTableDeck, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
                        .addComponent(panel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(paneTableDeck, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
                                .addContainerGap())
        );

        splitpane.setRightComponent(panel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(splitpane, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(splitpane, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        setMinimumSize(new Dimension(640, 480));
        pack();
        setVisible(true);
        setSize(new Dimension(1200, 800));
    }
}
