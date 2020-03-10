import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ConnectGUI extends JFrame implements ActionListener {

    private JButton buttonDeckbuilder;
    private JButton buttonCancel;
    private JButton buttonConnect;
    private JButton buttonF5Deck;
    private JButton buttonF5Pic;
    private JComboBox<String> comboDeck;
    private JComboBox<String> comboPic;
    private JLabel labelCardback;
    private JLabel labelDeck;
    private JLabel labelPlayername;
    private JLabel labelIP;
    private JLabel labelPort;
    private JLabel labelPic;
    private JPanel panel;
    private JRadioButton radioHost;
    private JRadioButton radioJoin;
    private JSeparator separator;
    private JTextField textPlayername;
    private JTextField textIP;
    private JTextField textPort;

    private int port;
    private String ip;
    private Thread thread;
    private ServerSocket ss_game;
    private ServerSocket ss_move;
    private ServerSocket ss_chat;
    private Socket s_game;
    private Socket s_move;
    private Socket s_chat;
    private boolean killThreads=false;
    private boolean iAmHost;
    private String sName;
    private String sIp;
    private String sPort;
    private int sHost;
    private String sDeckname;
    private ArrayList<File> deckFiles = new ArrayList<File>();
    private int myAnfuehrer;
    private int myGott;
    private ArrayList<Karte> myDeck = new ArrayList<Karte>();

    private String sPicname;
    private ArrayList<File> picFiles = new ArrayList<File>();
    private File picFile;

    private int imageX = 200;
    private int imageY = 279;

    private int cardSizeHover;
    private int cardSizeDeineSpielzone;
    private int cardSizeMeineSpielzone;
    private int cardSizeMeineHand;
    private int cardSizeExtra;
    private String deck;

    //public ConnectGUI(String pName, String pIp, String pPort, int pHost, String pDeckname, String pPicname) {
    public ConnectGUI(String pName, String pIp, String pPort, int pHost, String pDeckname,
                      int pCSHover, int pCSDuSpiel, int pCSIchSpiel, int pCSIchHand, int pCSExtra) {
        sName = pName;
        sIp = pIp;
        sPort = pPort;
        sHost = pHost;
        sDeckname = pDeckname;
        //sPicname = pPicname;
        initComponents();
        findeDecks();
        //findePics();
        cardSizeHover = pCSHover;
        cardSizeDeineSpielzone = pCSDuSpiel;
        cardSizeMeineSpielzone = pCSIchSpiel;
        cardSizeMeineHand = pCSIchHand;
        cardSizeExtra = pCSExtra;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()==buttonConnect){
            setPic();
            if (checkDeck()) {
                if (radioHost.isSelected()) {
                    getIpAndPort();
                    iAmHost = true;
                    //labelImage.setText("Hoste Server...");
                    setEnabledAllElements(false);
                    connectHost();
                } else {
                    getIpAndPort();
                    iAmHost = false;
                    //labelImage.setText("Suche Server...");
                    setEnabledAllElements(false);
                    connectJoin();
                }
            }
        } else if(ae.getSource()==buttonCancel){
            buttonCancel.setEnabled(false);
            //labelImage.setText("Abbrechen...");
            closeSocket();
        } else if(ae.getSource()==buttonDeckbuilder){
           openDeckbuilder();
        } else if(ae.getSource()==radioHost){
            textIP.setEnabled(false);
        } else if(ae.getSource()==radioJoin) {
            textIP.setEnabled(true);
        } else if(ae.getSource()==comboDeck){
            if (comboDeck.getItemCount() != 0)
                deck = deckFiles.get(comboDeck.getSelectedIndex()).getName();
            checkBtn();
        } else if(ae.getSource()==buttonF5Deck){
            deckFiles = new ArrayList<File>();
            comboDeck.removeAllItems();
            findeDecks();
            if (deckFiles.size()==0){
                JOptionPane.showMessageDialog(this, "Erstelle ein Deck im Deckbuilder.\nDieser Button übernimmt Änderungen in deinem warlock_userdata-Ordner.");
            }
        } else if (ae.getSource()==comboPic){
            setPic();
        } else if (ae.getSource()==buttonF5Pic){
            picFiles = new ArrayList<File>();
            comboPic.removeAllItems();
            findePics();
            if (picFiles.size()==0){
                JOptionPane.showMessageDialog(this, "Lege eine PNG-Datei in deinem warlock_userdata-Ordner ab.\nDas Bild sollte quadratisch sein, Maximalgröße 10MB\nDieser Button übernimmt Änderungen in deinem warlock_userdata-Ordner.");
            }
        }
    }

    private void changeImage(){
        int random = (int) (Math.random() * 300)+1;
        //labelCardback.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images200/" + get3Stellig(random) + ".png")));
        ImageIcon ii = new javax.swing.ImageIcon(getClass().getResource("/images200/" + get3Stellig(random) + ".png"));
        ii = new ImageIcon(ii.getImage().getScaledInstance(imageX, imageY, Image.SCALE_SMOOTH));
        labelCardback.setIcon(ii);
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

    private void openDeckbuilder(){
        setEnabledAllElements(false); buttonCancel.setEnabled(false);
        BuilderGUI deckbuilder = new BuilderGUI();
        deckbuilder.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                deckFiles = new ArrayList<File>(); comboDeck.removeAllItems(); findeDecks();
                setEnabledAllElements(true); checkBtn();
            }
        });
    }

    private void setPic(){
        if (comboPic.getItemCount()==0)
            picFile=null;
        else
            picFile = picFiles.get(comboPic.getSelectedIndex());
    }

    private void findePics(){
        File folder = new File(System.getProperty("user.dir") + "\\warlock_userdata");
        if (!folder.exists())
            new File(System.getProperty("user.dir") + "\\warlock_userdata").mkdirs();
        File[] listOfFiles = folder.listFiles();
        for(int i=0; i<listOfFiles.length; i++) {
            if (
                    listOfFiles[i].isFile()
                    && listOfFiles[i].length() <= 10000000
                    && listOfFiles[i].getName().length()>=4
                    && listOfFiles[i].getName().toLowerCase().substring(listOfFiles[i].getName().length()-4).equals(".png")
            ){
                picFiles.add(listOfFiles[i]);
            }
        }
        if (picFiles.size()>0) {
            addeGefundenePics();
        }
    }
    private void addeGefundenePics(){
        int setIdx=0;
        for(int i=0; i<picFiles.size(); i++){
            String name = picFiles.get(i).getName();
            if (name.toLowerCase().equals(sPicname.toLowerCase())){setIdx=i;}
            comboPic.addItem(name.substring(0,name.length()-4));
        }
        comboPic.setSelectedIndex(setIdx);
    }

    private void findeDecks(){
        File folder = new File(System.getProperty("user.dir") + "\\warlock_userdata");
        if (!folder.exists())
            new File(System.getProperty("user.dir") + "\\warlock_userdata").mkdirs();
        File[] listOfFiles = folder.listFiles();
        for(int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && listOfFiles[i].getName().length()>6 && listOfFiles[i].getName().toLowerCase().substring(listOfFiles[i].getName().length()-6).equals(".wdeck")) {
                deckFiles.add(listOfFiles[i]);
            }
        }
        if (deckFiles.size()>0) {
            addeGefundeneDecks();
        }
    }
    private void addeGefundeneDecks(){
        int setIdx=0;
        for(int i=0; i<deckFiles.size(); i++){
            String name = deckFiles.get(i).getName();
            if (name.toLowerCase().equals(sDeckname.toLowerCase())){setIdx=i;}
            comboDeck.addItem(name.substring(0,name.length()-6));
        }
        comboDeck.setSelectedIndex(setIdx);
    }

    private boolean checkDeck(){
        int n = comboDeck.getSelectedIndex();
        File file = deckFiles.get(n);
        if(!file.exists()){
            fehlerMitDatei();
            return false;
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
                    if (i>=1 && i!=1 && i!= 31 && i!=61 && i!=91 && i!=121 && i!=151 && i!=181 && i!=211 && i!=241 && i<=300) {
                        deck.add(new Karte(i));
                    }
                }
            }
            reader.close();
        } catch (Exception ignored){
            fehlerMitDatei();
            return false;
        }
        if(bname && banfuehrer && bgott && Main.checkDeck(deck, anfuehrer, gott)) {
            myDeck = deck;
            myAnfuehrer = anfuehrer;
            myGott = gott;
            return true;
        } else {
            fehlerMitDatei();
            return false;
        }
    }

    private void fehlerMitDatei(){
        JOptionPane.showMessageDialog(this, "Fehlerhafte Deckdatei.");
    }

    public void checkBtn(){
        buttonConnect.setEnabled((textIP.getText().length()>0||radioHost.isSelected()) && textPort.getText().length()>0 && textPlayername.getText().length()>0&& comboDeck.getItemCount()>0);
    }

    private void setEnabledAllElements(boolean pBool){
        buttonConnect.setEnabled(pBool);
        textIP.setEnabled(pBool && radioJoin.isSelected());
        textPort.setEnabled(pBool);
        radioHost.setEnabled(pBool);
        radioJoin.setEnabled(pBool);
        textPlayername.setEnabled(pBool);
        comboDeck.setEnabled(pBool);
        buttonF5Deck.setEnabled(pBool);
        //comboPic.setEnabled(pBool);
        //buttonF5Pic.setEnabled(pBool);
        buttonDeckbuilder.setEnabled(pBool);
        buttonCancel.setEnabled(!pBool);
    }

    void startGame(){
        setEnabledAllElements(false);buttonCancel.setEnabled(false);
        //new GameGUI(iAmHost, s_game, s_move, s_chat, sName, myDeck, myAnfuehrer, myGott, this, picFile);
        new GameGUI(iAmHost, s_game, s_move, s_chat, sName, myDeck, myAnfuehrer, myGott, this,
                cardSizeHover, cardSizeDeineSpielzone, cardSizeMeineSpielzone, cardSizeMeineHand, cardSizeExtra,
                deck, sIp, sPort);
        try{
            ss_game.close();
            ss_move.close();
            ss_chat.close();
        } catch(Exception e){}
    }

    void reopen(){
        deckFiles = new ArrayList<File>();
        comboDeck.removeAllItems();
        findeDecks();
        setEnabledAllElements(true);
        checkBtn();
        try {
            s_game.close();
            s_move.close();
            s_chat.close();
        } catch (Exception e2) {
        }
        setVisible(true);
    }

    private void getIpAndPort(){
        if (textPlayername.getText().length()>20) {
            textPlayername.setText(textPlayername.getText().substring(0,19));
        }
        ip = textIP.getText();
        int n = Math.min(textPort.getText().length(), 6);
        port = Integer.parseInt(textPort.getText().substring(0, n));
        if (port<2){port=2;}
        if (port>65535){port=65535;}
        textPort.setText(String.valueOf(port));

        sName = textPlayername.getText();
        sIp = ip;
        sPort = textPort.getText();
        if (radioHost.isSelected()) {
            sHost = 1;
        } else {
            sHost = 0;
        }
        writeConfig();
    }

    private void writeConfig(){
        boolean fileExists;
        String dir = System.getProperty("user.dir") + "\\warlock_userdata\\warlock.config";
        File file = new File(dir);
        if(file.exists()){
            fileExists=true;
        } else {
            try{
                fileExists = file.createNewFile();
            }catch(Exception ignored){
                fileExists=false;
            }
        }
        if(fileExists){
            try {
                FileWriter writer = new FileWriter(dir);
                writer.write("name="+sName+";\n");
                writer.write("ip="+sIp+";\n");
                writer.write("port="+sPort+";\n");
                writer.write("host="+sHost+";\n");
                writer.write("deck="+deckFiles.get(comboDeck.getSelectedIndex()).getName()+";\n");
                writer.write("cardSizeHover=" + cardSizeHover + ";\n");
                writer.write("cardSizeDeineSpielzone=" + cardSizeDeineSpielzone + ";\n");
                writer.write("cardSizeMeineSpielzone=" + cardSizeMeineSpielzone + ";\n");
                writer.write("cardSizeMeineHand=" + cardSizeMeineHand + ";\n");
                writer.write("cardSizeExtra=" + cardSizeExtra + ";\n");
                writer.close();
            }catch(Exception ignored){}
        }
    }

    private void closeSocket(){
        if(thread.isAlive()) {
            killThreads = true;
            if (iAmHost) {
                serverSocketCloser ssc = new serverSocketCloser();
                thread = new Thread(ssc);
                thread.start();
            }
        }
    }

    class serverSocketCloser extends Thread{
        public void run() {
            while (true){
                try{
                    new Socket("localhost", port);
                    break;
                }catch(Exception e){
                    //System.out.println(e);
                }
            }
            while (true) {
                try {
                    new Socket("localhost", port+-1);
                    break;
                } catch (Exception e) {
                    //System.out.println(e);
                }
            }
            while (true) {
                try {
                    new Socket("localhost", port+1);
                    break;
                } catch (Exception e) {
                    //System.out.println(e);
                }
            }
            try{ss_game.close();}catch(Exception ignored){}
            try{ss_move.close();}catch(Exception ignored){}
            try{ss_chat.close();}catch(Exception ignored){}
        }
    }
    private void connectHost(){
        connectAsHost cah = new connectAsHost();
        thread = new Thread(cah);
        thread.start();
    }
    class connectAsHost extends Thread{
        public void run() {
            s_game = null;
            while (true) {
                try {
                    ss_game = new ServerSocket(port);
                    s_game = ss_game.accept();
                    ss_move = new ServerSocket(port-1);
                    s_move = ss_move.accept();
                    ss_chat = new ServerSocket(port+1);
                    s_chat = ss_chat.accept();
                    if (!killThreads) {
                        startGame();
                    } else {
                        killThreads = false;
                        setEnabledAllElements(true);
                        //linfo.setText("Warte auf Eingabe");
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    //System.out.println(e);
                    killThreads = false;
                    setEnabledAllElements(true);
                    labelIP.setText("Warte auf Eingabe");
                    break;
                } catch (Exception e) {
                    //System.out.println(e);
                }
            }
        }
    }
    private void connectJoin(){
        connectThroughJoin ctj = new connectThroughJoin();
        thread = new Thread(ctj);
        thread.start();
    }
    class connectThroughJoin extends Thread{
        public void run(){
            s_game=null;
            while(!killThreads) {
                while(!killThreads){
                    try{
                        s_game = new Socket(ip, port);
                        break;
                    }catch(Exception e){
                        //System.out.println(e);
                    }
                }
                while(!killThreads) {
                    try {
                        s_move = new Socket(ip, port - 1);
                        break;
                    } catch (Exception e) {
                        //System.out.println(e);
                    }
                }
                try {
                    s_chat = new Socket(ip, port + 1);
                    if (!killThreads) {
                        startGame();
                    }
                    break;
                } catch (Exception e) {
                    //System.out.println(e);
                }
            }
            if (killThreads) {
                killThreads = false;
                setEnabledAllElements(true);
                //labelImage.setText("Warte auf Eingabe");
            }
        }
    }

    private void initComponents() {
        labelCardback = new javax.swing.JLabel();
        panel = new javax.swing.JPanel();
        labelDeck = new javax.swing.JLabel();
        comboDeck = new javax.swing.JComboBox<>();
        comboDeck.addActionListener(this);
        buttonDeckbuilder = new javax.swing.JButton();
        buttonDeckbuilder.addActionListener(this);
        separator = new javax.swing.JSeparator();
        labelPlayername = new javax.swing.JLabel();
        textPlayername = new javax.swing.JTextField();
        labelIP = new javax.swing.JLabel();
        textIP = new javax.swing.JTextField();
        textPort = new javax.swing.JTextField();
        labelPort = new javax.swing.JLabel();
        buttonCancel = new javax.swing.JButton();
        buttonCancel.addActionListener(this);
        buttonConnect = new javax.swing.JButton();
        buttonConnect.addActionListener(this);
        radioHost = new javax.swing.JRadioButton();
        radioHost.addActionListener(this);
        radioJoin = new javax.swing.JRadioButton();
        radioJoin.addActionListener(this);
        ButtonGroup group = new ButtonGroup();
        radioHost.setSelected(sHost==1); group.add(radioHost);
        radioJoin.setSelected(sHost!=1); group.add(radioJoin);
        buttonF5Deck = new javax.swing.JButton();
        buttonF5Deck.addActionListener(this);
        labelPic = new javax.swing.JLabel();
        buttonF5Pic = new javax.swing.JButton();
        buttonF5Pic.addActionListener(this);
        comboPic = new javax.swing.JComboBox<>();
        comboPic.addActionListener(this);
        comboPic.setEnabled(false);
        buttonF5Pic.setEnabled(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        //labelCardback.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images200/000.png")));
        ImageIcon ii = new javax.swing.ImageIcon(getClass().getResource("/images200/000.png"));
        ii = new ImageIcon(ii.getImage().getScaledInstance(imageX, imageY, Image.SCALE_SMOOTH));
        labelCardback.setIcon(ii);
        labelCardback.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                changeImage();
            }
        });

        labelDeck.setText("Deck:");

        buttonDeckbuilder.setText("Deckbuilder");

        labelPlayername.setText("Spielername:");

        textPlayername.setText(sName);

        labelIP.setText("IP:");

        textIP.setText(sIp);

        textPort.setText(sPort);

        labelPort.setText("Port:");

        buttonCancel.setText("Abbrechen");
        buttonCancel.setEnabled(false);

        buttonConnect.setText("Verbinden");
        buttonConnect.setEnabled(false);

        radioHost.setText("Host");

        radioJoin.setText("Join");

        buttonF5Deck.setText("↓");

        labelPic.setText("Profilbild");

        buttonF5Pic.setText("↓");
        buttonF5Pic.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(panel);
        panel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(comboDeck, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonF5Deck)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(buttonDeckbuilder))
                                        .addComponent(separator)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(radioHost)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(radioJoin)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(buttonConnect)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonCancel))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(textPlayername, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                                                        .addComponent(textIP, javax.swing.GroupLayout.Alignment.LEADING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, true)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(comboPic, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(buttonF5Pic))
                                                        .addComponent(textPort, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(labelDeck)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(labelIP)
                                                                .addGap(182, 182, 182)
                                                                .addComponent(labelPort))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(labelPlayername)
                                                                .addGap(134, 134, 134)
                                                                .addComponent(labelPic)))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(labelDeck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(comboDeck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonDeckbuilder)
                                        .addComponent(buttonF5Deck))
                                .addGap(18, 18, 18)
                                .addComponent(separator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelPlayername)
                                        .addComponent(labelPic))
                                .addGap(2, 2, 2)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(textPlayername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonF5Pic)
                                        .addComponent(comboPic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelIP)
                                        .addComponent(labelPort))
                                .addGap(13, 13, 13)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(textIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(textPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(buttonCancel)
                                        .addComponent(buttonConnect)
                                        .addComponent(radioHost)
                                        .addComponent(radioJoin))
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(labelCardback, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelCardback)
                        .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setVisible(true);
    }
}
