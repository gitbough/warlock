

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class GameGUI_old extends JFrame implements ActionListener {
    //Connection
    private Socket gameSocket;
    private Socket moveSocket;
    private Socket chatSocket;
    private int gameStatus = 0;
    private boolean iAmHost;

    //Game
    private String ichName;
    private String duName;
    private Partie partie;

    //private JFrame frame;
    private JTextArea taChat;
    private JTextField tInput;
    //private JButton bGame;
    private JButton bChat;
    private boolean running = true;


    //NEU GUI
    private JTextArea taLog2;

    private String imageFolder = "/images200/";
    private int imageX = 200;
    private int imageY = 279;
    /*
    String imageFolder = "/images100/";
    int imageX = 100;
    int imageY = 139;
    */
    private JLabel infoMsg;

    private JButton buttonOK;
    private JButton buttonFeuer;
    //private JButton buttonBeenden;
    private JButton buttonCancel;
    private JButton buttonErde;

    private JLabel infoLabelDuName;
    private JLabel infoLabelIchStaerke;
    private JLabel infoLabelDuDrachen;
    private JLabel infoLabelIchName;
    private JLabel infoLabelDuStaerke;
    private JLabel infoLabelDuHand;
    private JLabel infoLabelDuCharVerst;
    private JLabel infoLabelIchNachzieh;
    private JLabel infoLabelIchDrachen;
    private JLabel infoLabelIchHand;
    private JLabel infoLabelIchCharVerst;
    private JLabel infoLabelDuNachzieh;

    private JPanel panelDuSpielzone;
    private JScrollPane sPaneDuSpielzone;
    private JPanel panelIchSpielzone;
    private JScrollPane sPaneIchSpielzone;
    private JPanel panelIchHand;
    private JScrollPane sPaneIchHand;

    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;

    private JLabel statusAktiverSpieler;
    private JLabel statusZugPhase;
    private JLabel statusKampfzug;
    private JLabel statusSpielzug;
    private JLabel detailName;
    private JLabel detailTyp;
    //...
    private JLabel detailBild;
    private JPanel panelDuAbwurf;
    private JScrollPane sPaneDuAbwurf2;
    private JPanel panelIchAbwurf;
    private JScrollPane sPaneIchAbwurf2;
    private JPanel panelDuHand;
    private JScrollPane sPaneDuHand2;
    private JPanel panelIchNachzieh;
    private JScrollPane sPaneIchNachzieh2;
    //ENDE - NEU GUI
    private ArrayList<Karte> myDeck = new ArrayList<Karte>();
    private int myAnfuehrer;
    private int myGott;

    private ConnectGUI connectgui;

    //private JLabel labelIchPic = new JLabel();
    //private JLabel labelDuPic = new JLabel();
    //private BufferedImage profilePicBIMG;


    JPopupMenu contextMenu = new JPopupMenu("Kartenoptionen");
    JMenuItem contextMenuItemTitle = new JMenuItem("");
    JMenuItem contextMenuItemSpielen = new JMenuItem("Spielen");
    JMenuItem contextMenuItemErsetzen = new JMenuItem("Ersetzen");
    JMenuItem contextMenuItemBluff = new JMenuItem("Als Bluff spielen");
    JMenuItem contextMenuItemSchiffDrauf = new JMenuItem("Auf Schiff lade");
    JMenuItem contextMenuItemSchiffRunter = new JMenuItem("Schiff anlanden");
    JMenuItem contextMenuItemAufnehm = new JMenuItem("Auf die Hand nehmen");
    JMenuItem contextMenuItemAktivieren = new JMenuItem("Sonderfunktion aktivieren");
    JMenuItem contextMenuItemBefriedigen = new JMenuItem("Sonderfunktion befriedigen");
    JMenuItem contextMenuItemNichts = new JMenuItem("Keine Option verfügbar");
    Zone contextMenuZone;
    int contextMenuKarte;

    //GameGUI(boolean pIAmHost, Socket pGameSocket, Socket pMoveSocket, Socket pChatSocket, String pIchName, ArrayList<Karte> pDeck, int pAnfuehrer, int pGott, ConnectGUI pConnectGUI, File picFile) {
    GameGUI_old(boolean pIAmHost, Socket pGameSocket, Socket pMoveSocket, Socket pChatSocket, String pIchName, ArrayList<Karte> pDeck, int pAnfuehrer, int pGott, ConnectGUI pConnectGUI) {
        gameSocket = pGameSocket;
        moveSocket = pMoveSocket;
        chatSocket = pChatSocket;
        iAmHost = pIAmHost;
        ichName = pIchName;
        myDeck = pDeck;
        myAnfuehrer = pAnfuehrer;
        myGott = pGott;
        connectgui = pConnectGUI;

        /*
        profilePicFile = picFile;
        try {
            if (picFile==null || !picFile.exists())
                profilePicBIMG = ImageIO.read(getClass().getResource("/images200/000.png"));
            else
                profilePicBIMG = ImageIO.read(picFile);
            ImageIcon ii = new ImageIcon(profilePicBIMG);
            ii = new ImageIcon(ii.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
            labelIchPic.setIcon(ii);
        } catch (Exception e) {
            System.out.println(e);
        }
        */

        startGame();
    }

    private void startGame(){
        running = true;
        initComponents();
        initializeGameStatus();
    }

    //NEUES GUI
    private void initComponents() {
        contextMenuItemSpielen.addActionListener(this);
        contextMenuItemErsetzen.addActionListener(this);
        contextMenuItemBluff.addActionListener(this);
        contextMenuItemSchiffDrauf.addActionListener(this);
        contextMenuItemSchiffRunter.addActionListener(this);
        contextMenuItemAufnehm.addActionListener(this);
        contextMenuItemAktivieren.addActionListener(this);
        contextMenuItemBefriedigen.addActionListener(this);
        contextMenuItemTitle.setEnabled(false);
        contextMenuItemNichts.setEnabled(false);
        jPanel5 = new javax.swing.JPanel();
        infoLabelDuName = new javax.swing.JLabel();
        infoLabelDuDrachen = new javax.swing.JLabel();
        infoLabelDuHand = new javax.swing.JLabel();
        infoLabelDuHand.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                openDuHand();
            }
        });
        infoLabelDuCharVerst = new javax.swing.JLabel();
        infoLabelDuNachzieh = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        infoLabelIchName = new javax.swing.JLabel();
        infoLabelIchStaerke = new JLabel();
        infoLabelDuStaerke = new JLabel();
        infoMsg = new JLabel();
        buttonOK = new javax.swing.JButton();
        buttonFeuer = new javax.swing.JButton();
        //buttonBeenden = new JButton();
        buttonCancel = new javax.swing.JButton();
        buttonErde = new javax.swing.JButton();
        infoLabelIchDrachen = new javax.swing.JLabel();
        infoLabelIchHand = new javax.swing.JLabel();
        infoLabelIchCharVerst = new javax.swing.JLabel();
        infoLabelIchNachzieh = new javax.swing.JLabel();
        JSplitPane jSplitPane1 = new JSplitPane();
        JSplitPane jSplitPane2 = new JSplitPane();
        JTabbedPane jTabbedPane3 = new JTabbedPane();


        //CHAT
        JPanel tabChat1 = new JPanel();
        taChat = new JTextArea();
        taChat.setEditable(false);
        tabChat1.add(taChat);
        tInput = new JTextField();
        tabChat1.add(tInput);
        bChat = new JButton();
        bChat.addActionListener(this);
        tabChat1.add(bChat);
        /* ---EIGENE ELEMENTE STATT ELEMENTE VON TABCHAT1 STEHLEN!!!!
        JPanel tabChat2 = new JPanel();
        tabChat2.add(taChat);
        tabChat2.add(tInput);
        tabChat2.add(bChat);
        */
        //GAMELOG
        taLog2 = new JTextArea();

        //SPIELSTATUS
        JPanel tabStatus1 = new JPanel();
        statusAktiverSpieler = new JLabel();
        tabStatus1.add(statusAktiverSpieler);
        statusZugPhase = new JLabel();
        tabStatus1.add(statusZugPhase);
        statusKampfzug = new JLabel();
        tabStatus1.add(statusKampfzug);
        statusSpielzug = new JLabel();
        tabStatus1.add(statusSpielzug);
        //KARTENDETAILS
        JPanel tabKartenDetail2 = new JPanel();
        /*detailName = new JLabel();
        tabKartenDetail2.add(detailName);
        detailTyp = new JLabel();
        tabKartenDetail2.add(detailTyp);*/
        //...
        detailBild = new JLabel();
        tabKartenDetail2.add(detailBild);
        //MEINE ABWURZONE
        panelIchAbwurf = new JPanel();
        sPaneIchAbwurf2 = new JScrollPane(panelIchAbwurf);
        //GEGNERISCHE ABWURFZONE
        panelDuAbwurf = new JPanel();
        sPaneDuAbwurf2 = new JScrollPane(panelDuAbwurf);
        //GEGNERISCHE HAND
        panelDuHand = new JPanel();
        sPaneDuHand2 = new JScrollPane(panelDuHand);
        //EIGENER NACHZIEHSTAPEL
        panelIchNachzieh = new JPanel();
        sPaneIchNachzieh2 = new JScrollPane(panelIchNachzieh);

        JTabbedPane jTabbedPane4 = new JTabbedPane();
        JSplitPane jSplitPane3 = new JSplitPane();
        JSplitPane jSplitPane4 = new JSplitPane();

        panelDuSpielzone = new JPanel();
        sPaneDuSpielzone = new javax.swing.JScrollPane(panelDuSpielzone);

        panelIchSpielzone = new JPanel();
        sPaneIchSpielzone = new javax.swing.JScrollPane(panelIchSpielzone);

        panelIchHand = new JPanel();
        sPaneIchHand = new javax.swing.JScrollPane(panelIchHand);

        jPanel5.setPreferredSize(new java.awt.Dimension(54, 153));


        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(infoLabelDuName)
                                        //.addComponent(labelDuPic)
                                        .addComponent(infoLabelDuStaerke)
                                        .addComponent(infoLabelDuDrachen)
                                        .addComponent(infoLabelDuHand)
                                        .addComponent(infoLabelDuCharVerst)
                                        .addComponent(infoLabelDuNachzieh))
                                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(infoLabelDuName, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                //.addComponent(labelDuPic)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelDuStaerke)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelDuDrachen)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelDuHand)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelDuCharVerst)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelDuNachzieh)
                                .addContainerGap(152, Short.MAX_VALUE))
        );


        Border border = BorderFactory.createLineBorder(Color.black);
        infoMsg.setBorder(border);

        buttonOK.setText("O.K.");
        buttonOK.addActionListener(this);

        buttonFeuer.setText("Feuer");
        buttonFeuer.addActionListener(this);
        /*
        buttonBeenden.setText("Zurückziehen");
        buttonBeenden.addActionListener(this);
        */
        buttonCancel.setText("Abbrechen");
        buttonCancel.addActionListener(this);

        buttonErde.setText("Erde");
        buttonErde.addActionListener(this);

        infoLabelIchDrachen.setText("Ich Drachen: 2");

        infoLabelIchHand.setText("Ich Karten in Hand: 6");

        infoLabelIchCharVerst.setText("Char/Verst in Spielzone: 2");

        infoLabelIchNachzieh.setText("Ich Karten im Deck: 12");

        //bGame.setText("submit");//BGAME

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        //.addComponent(bGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) //BGAME
                                        .addComponent(buttonCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(buttonOK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        //.addComponent(buttonBeenden, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(infoLabelIchName)
                                                        //.addComponent(labelIchPic)
                                                        .addComponent(infoLabelIchStaerke)
                                                        .addComponent(infoLabelIchDrachen)
                                                        .addComponent(infoLabelIchHand)
                                                        .addComponent(infoLabelIchCharVerst)
                                                        .addComponent(infoLabelIchNachzieh))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(infoMsg)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(buttonFeuer, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonErde, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(infoLabelIchName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                //.addComponent(labelIchPic)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelIchStaerke)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelIchDrachen)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelIchHand)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelIchCharVerst)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelIchNachzieh)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(buttonErde)
                                        .addComponent(buttonFeuer))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                //.addComponent(buttonBeenden)
                                //.addGap(5, 5, 5)
                                .addComponent(buttonOK)
                                .addGap(5, 5, 5)
                                .addComponent(buttonCancel)
                                .addGap(5, 5, 5)
                                .addComponent(infoMsg)
                                .addContainerGap())
        );

        jSplitPane1.setDividerLocation(600);
        jSplitPane1.setResizeWeight(0.8);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(30, 25));

        jSplitPane2.setDividerLocation(200);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setResizeWeight(0.5);

        jTabbedPane3.addTab("Chat", new JScrollPane(tabChat1));
        jTabbedPane3.addTab("Spielstatus", new JScrollPane(tabStatus1));
        jTabbedPane3.addTab("Kartendetails", new JScrollPane(tabKartenDetail2));
        jTabbedPane3.addTab("Meine Abwurfzone", new JScrollPane(sPaneIchAbwurf2));
        jTabbedPane3.addTab("Gegnerische Abwurfzone", new JScrollPane(sPaneDuAbwurf2));
        jTabbedPane3.addTab("Gegnerische Hand", new JScrollPane(sPaneDuHand2));
        jTabbedPane3.addTab("Eigener Nachziehstapel", new JScrollPane(sPaneIchNachzieh2));
        jTabbedPane3.addTab("GameLog", new JScrollPane(taLog2));

        jSplitPane2.setTopComponent(jTabbedPane3);

        //-- -- -- Hier Klone der oberen Tabs adden :) -- -- --
        /*
        jTabbedPane4.addTab("Chat", new JScrollPane(tabChat2));
        jTabbedPane4.addTab("Spielstatus", new JScrollPane(tabStatus2));
        jTabbedPane4.addTab("Kartendetails", new JScrollPane(tabKartenDetail2));
        jTabbedPane4.addTab("Meine Abwurfzone", new JScrollPane(sPaneIchAbwurf2));
        jTabbedPane4.addTab("Gegnerische Abwurfzone", new JScrollPane(sPaneDuAbwurf2));
        jTabbedPane4.addTab("Gegnerische Hand", new JScrollPane(sPaneDuHand2));
        jTabbedPane4.addTab("Eigener Nachziehstapel", new JScrollPane(sPaneIchNachzieh2));
        jTabbedPane4.addTab("GameLog", new JScrollPane(taLog2));
        */


        jSplitPane2.setRightComponent(jTabbedPane4);

        jSplitPane1.setRightComponent(jSplitPane2);

        jSplitPane3.setDividerLocation(400);
        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane3.setResizeWeight(0.6);

        jSplitPane4.setDividerLocation(200);
        jSplitPane4.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane4.setResizeWeight(0.5);
        jSplitPane4.setTopComponent(sPaneDuSpielzone);

        jSplitPane4.setRightComponent(sPaneIchSpielzone);

        jSplitPane3.setTopComponent(jSplitPane4);


        jSplitPane3.setRightComponent(sPaneIchHand);


        jSplitPane1.setLeftComponent(jSplitPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        setMinimumSize(new Dimension(640, 480));
        pack();
        setSize(new Dimension(1200, 800));
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent evt){
                int x = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit ?", "Comform !",
                        JOptionPane.YES_NO_OPTION);

                if(x == JOptionPane.YES_OPTION) {
                    running = false;
                    connectgui.reopen();
                    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                }else{
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    private void logMsg(int pMsg, boolean pIch){
        String msg = "";
        if (pIch) {
            msg = "Ich: ";
        } else {
            msg = "Du: ";
        }
        msg += partie.getMsg();
        msg += ", Aktion " + pMsg;
        msg += " (" + partie.getPhase() + ")";
        taLog2.setText( taLog2.getText() + msg + "\n" );
    }

    private void doAction(int n){
        if (partie.aktion(n)) {
            if(partie.gottaSend()){
                sendObject(partie);
            } else {
                sendMove(n);
            }
            redrawBoard();
            logMsg(n, true);
        }
    }

    void showContextMenu(MouseEvent pMouseEvent, int pIndex, Zone pZone){
        contextMenu.removeAll();
        getContextMenuItems(pIndex, pZone);
        contextMenu.show(pMouseEvent.getComponent(), pMouseEvent.getX(), pMouseEvent.getY());
    }
    int getContextMenuItems(int pIndex, Zone pZone){
        contextMenuKarte = pIndex;
        contextMenuZone = pZone;
        int count=0;
        Karte karte = new Karte(0);
        //Titel
        if (contextMenuZone==partie.getIch().getHand()) {
            karte = contextMenuZone.get(contextMenuKarte-1);

        } else if (contextMenuZone==partie.getIch().getSpielzone()){
            karte = contextMenuZone.get(contextMenuKarte-100);
        } else if (contextMenuZone==partie.getDu().getSpielzone()){
            karte = contextMenuZone.get(contextMenuKarte-200);
        }
        contextMenuItemTitle.setText(karte.getName());
        contextMenu.add(contextMenuItemTitle);
        //SPIELEN
        if(partie.ichBinAmZug()
            && partie.getSonderfunktion()==0
            && contextMenuZone==partie.getIch().getHand()
            && partie.karteSpielenErlaubt(contextMenuZone.get(contextMenuKarte-1))
        ){
            contextMenu.add(contextMenuItemSpielen);
            count++;
        }
        //ERSETZEN
        if (partie.ichBinAmZug()
            && partie.getSonderfunktion()==0
            && contextMenuZone==partie.getIch().getHand()
            && (
                (karte.hatSymbol(KartenSymbol.ERSETZEN,partie.getDu())
                || partie.getIch().hatAktiv(298)) //Argusauge
                && (partie.getPhase()==ZugPhase.BEGINN
                || partie.getPhase()==ZugPhase.AKTION)
                && (partie.getAktionenGespielt()<1 || (partie.getIch().hatAktiv(14)&&partie.getAktionenGespielt()<2))
            )
            && !partie.getFlutEffekt()
        ){
            contextMenu.add(contextMenuItemErsetzen);
            count++;
        }
        //AUFNEHMEN
        if (
                partie.ichBinAmZug()
                && partie.getSonderfunktion()==0
                && contextMenuZone==partie.getIch().getSpielzone()
                && partie.getPhase()==ZugPhase.BEGINN
                && karte.istAktiv()
                && karte.hatSymbol(KartenSymbol.AUFNEHMEN,partie.getDu())
                && (
                    karte.getTyp()!=KartenTyp.CHARAKTER
                    || !partie.getDu().hatAufnehmCharakter()
                )
                && !partie.getFlutEffekt()
        ){
            contextMenu.add(contextMenuItemAufnehm);
            count++;
        }
        //EIGENE SF IN SPIELZONE AKTIVIEREN
        if (
                partie.ichBinAmZug()
                && partie.getSonderfunktion()==0
                && contextMenuZone==partie.getIch().getSpielzone()
                && karte.istAktiv()
                && !karte.sfIgnoriert(partie.getDu())
                && !karte.isSfVerbraucht()
                && !partie.getFlutEffekt()
                && (karte.getNummer()==136||karte.getNummer()==137||karte.getNummer()==146)
        ){
            contextMenu.add(contextMenuItemAktivieren);
            count++;
        }


        if (count==0)
            contextMenu.add(contextMenuItemNichts);
        return count;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == buttonOK){
            doAction(0);
        } else if(ae.getSource() == buttonCancel){
            doAction(-1);
        } else if (ae.getSource() == buttonFeuer){
            doAction(-2);
        } else if (ae.getSource() == buttonErde){
            doAction(-3);
        } else if(ae.getSource()==contextMenuItemSpielen){
            if (partie.ichBinAmZug()
                && contextMenuKarte >= 1 && contextMenuKarte <= partie.getIch().getHandSize()
                && partie.karteSpielenErlaubt(partie.getIch().getHandKarte(contextMenuKarte - 1))
            ){ doAction(contextMenuKarte); }
        } else if(ae.getSource()==contextMenuItemErsetzen){
            if (contextMenuKarte >= 1 && contextMenuKarte <= partie.getIch().getHandSize()
                    && (
                        (contextMenuZone.get(contextMenuKarte-1).hatSymbol(KartenSymbol.ERSETZEN,partie.getDu())
                        || partie.getIch().hatAktiv(298)) //Argusauge
                        && (partie.getPhase()==ZugPhase.BEGINN
                        || partie.getPhase()==ZugPhase.AKTION)
                        && (partie.getAktionenGespielt()<1 || (partie.getIch().hatAktiv(14)&&partie.getAktionenGespielt()<2))
                    )
            ) {
                partie.setSonderfunktion(-5);
                partie.setSfZaehler(contextMenuKarte - 1);
                doAction(-1);
                partie.gottaSendTrue();
            }
        } else if(ae.getSource()==contextMenuItemBluff){
        } else if(ae.getSource()==contextMenuItemSchiffDrauf){
        } else if(ae.getSource()==contextMenuItemSchiffRunter){
        } else if(ae.getSource()==contextMenuItemAufnehm){
            klickeAn(contextMenuKarte);
        } else if(ae.getSource()==contextMenuItemAktivieren){
            klickeAn(contextMenuKarte);
        } else if(ae.getSource()==contextMenuItemBefriedigen){
            klickeAn(contextMenuKarte);
        }

        //ALT:
        if (ae.getSource() == bChat) { //Chat Sendebutton
            sendString(tInput.getText());
            //CHEAT
            partie.getIch().getHand().add(new Karte(Integer.parseInt(tInput.getText())));
            sendObject(partie);
            redrawBoard();
            //CHEAT ENDE
            //System.out.println("Gesendet: \"" + tInput.getText() + "\"");
            taChat.setText(ichName + ": " + tInput.getText() + "\n" + taChat.getText());
            tInput.setText("");
        }
    }

    void openDuHand(){
        System.out.println("Deine Hand betrachten...");
        JDialog d = new JDialog(this, "gegn. hand");
        d.add(new JScrollPane(panelDuHand));
        int width = panelDuHand.getWidth();
        d.setSize(width, 300);
        redrawDuHand();
        d.setVisible(true);
    }


    //Spiellogikwert intialisieren
    private void initializeGameStatus(){
        if(iAmHost){
            gameStatus=1;
        } else {
            gameStatus = 4;
        }
        startGameUpdater();
    }
    private void startGameUpdater(){
        gameUpdater gu = new gameUpdater();
        Thread gameLogicThread = new Thread(gu);
        gameLogicThread.start();
    }
    public class gameUpdater extends Thread{
        public void run(){
            while(running) {
                if (gameStatus == 1) {
                    //Empfange Name von Joined
                    duName = (String) receiveObject();
                    if (ichName.equals(duName)) {
                        ichName = ichName + "(1)";
                        duName = duName + "(2)";
                    }
                    //Empfange Profilbild von Joined
                    /*
                    try {
                        BufferedImage bi = ImageIO.read(chatSocket.getInputStream()); System.out.println(0);
                        ImageIcon ii = new ImageIcon(bi); System.out.println(1);
                        ii = new ImageIcon(ii.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)); System.out.println(2);
                        labelDuPic.setIcon(ii); System.out.println(3);
                    }catch(Exception e){System.out.println(e);}
                    //Sende eigenes Profilbild an Joined

                    while(running) {
                        try {
                            ImageIO.write(
                                    profilePicBIMG,
                                    "png",
                                    chatSocket.getOutputStream()
                            );
                            break;
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    */


                    gameStatus = 2;
                } else if (gameStatus == 2) { //Empfange Deck von joined und erzeuge dann Partie
                    //empfange gegnerdeck
                    ArrayList<Karte> gegnerDeck = (ArrayList<Karte>)receiveObject();
                    int gegnerAnf = (int)receiveObject();
                    int gegnerGot = (int)receiveObject();
                    //Erzeuge Spielerobjekte
                    Spieler ich = new Spieler(ichName, myDeck, myAnfuehrer, myGott);
                    Spieler du = new Spieler(duName, gegnerDeck, gegnerAnf, gegnerGot);
                    ich.setGegner(du);
                    du.setGegner(ich);
                    //Erzeuge Partieobjekt
                    partie = new Partie(ich, du);
                    gameStatus = 3;

                } else if (gameStatus == 3) { //Sende Partie an Joined
                    partie.spielAufbau();
                    System.out.println("sende partie");
                    sendObject(partie);
/*
                    try {
                            InetAddress mIp = moveSocket.getInetAddress();
                            int mPort = moveSocket.getPort();
                            moveSocket.close();
                            moveSocket = new Socket(mIp, mPort);
                            break;
                        while (running) {
                            InetAddress cIp = chatSocket.getInetAddress();
                            int cPort = chatSocket.getPort();
                            chatSocket.close();
                            chatSocket = new Socket(cIp, cPort);
                            break;
                        }
                    }catch(Exception e){System.out.println(e);}*/
                    gameStatus = 6;

                } else if (gameStatus == 4) { //Sende eigenen Namen und dann eigenes Deck an Host
                    //Sende eigenen Namen
                    sendObject(ichName);
                    /*
                    //Sende eigenes Profilbild
                    while(running) {
                        try {
                            ImageIO.write(
                                    profilePicBIMG,
                                    "png",
                                    chatSocket.getOutputStream()
                            );
                            break;
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    //Empfange Profilbild von Host

                    System.out.println(-1);
                    try {
                        BufferedImage bi = ImageIO.read(chatSocket.getInputStream()); System.out.println(0);
                        ImageIcon ii = new ImageIcon(bi); System.out.println(1);
                        ii = new ImageIcon(ii.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)); System.out.println(2);
                        labelDuPic.setIcon(ii); System.out.println(3);
                    }catch(Exception e){System.out.println(e);}
                    */

                    //Sende eigenes Deck
                    sendObject(myDeck);
                    sendObject(myAnfuehrer);
                    sendObject(myGott);
                    gameStatus = 5;

                } else if (gameStatus == 5) { //Empfange Partie von Host
                    System.out.println("empfange partie");
                    partie = new Partie((Partie) receiveObject(), true);
                    ichName = partie.getIch().getName();
                    duName = partie.getDu().getName();
                    /*
                    try {
                        //InetAddress mIp = moveSocket.getInetAddress();
                        int mPort = moveSocket.getPort();
                        moveSocket.close();
                        ServerSocket ssMove = new ServerSocket(mPort);
                        moveSocket = ssMove.accept();

                        //InetAddress cIp = chatSocket.getInetAddress();
                        int cPort = chatSocket.getPort();
                        chatSocket.close();
                        ServerSocket ssChat = new ServerSocket(cPort);
                        chatSocket = ssChat.accept();
                    }catch(Exception e){System.out.println(e);}*/
                    gameStatus = 6;

                } else if (gameStatus == 6) { //Starte neuen thread, der kontinuierlich gamestate vom anderen spieler empfängt
                    startGameReceiver();
                    startMoveReceiver();
                    startChatReceiver();
                    redrawBoard();
                    gameStatus = 0;
                    setVisible(true);
                    connectgui.setVisible(false);
                    break;
                }
            }
        }
    }
    private boolean sendObject(Object o){
        int count=0;
        while(count<=30) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(gameSocket.getOutputStream()));
                oos.writeObject(o);
                oos.flush();
                return true;
            } catch (Exception e) {
                //System.out.println(e);
                try{
                    //Thread.sleep(50);
                    //count+=1;
                }catch(Exception ignored){}
            }
        }
        return false;
    }
    private Object receiveObject(){
        while(running) {
            try {
                ObjectInputStream ois = new ObjectInputStream(gameSocket.getInputStream());
                //if(ois.available()>0) {
                    return ois.readObject();
                //} else {
                //    try{
                 //       Thread.sleep(100);
                 //   }catch(Exception ignored){}
                //}
            } catch (Exception e) {
                //System.out.println(e);
            }
        }
        return null;
    }
    private boolean sendString(String msg){
        int count=0;
        while(count<=30) {
            try {
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(chatSocket.getOutputStream()));
                dos.writeUTF(msg);
                dos.flush();
                return true;
            } catch (Exception e) {
                try{
                    Thread.sleep(50);
                    //count+=1;
                }catch(Exception ignored){}
            }
        }
        //TODO schreibe in textarea und lösche aus textfield, nur wenn hier true returned wurde
        return false;
    }
    private void startChatReceiver(){
        stringReceiver sr = new stringReceiver();
        Thread receiveChatThread = new Thread(sr);
        receiveChatThread.start();
    }
    class stringReceiver extends Thread{
        public void run(){
            while(running){
                try{
                    DataInputStream dis = new DataInputStream(chatSocket.getInputStream());
                    if (dis.available() > 0) {
                        String str = dis.readUTF();
                        taChat.setText(duName + ": " + str + "\n" + taChat.getText());
                    } else {
                        sleep(50);
                    }
                }catch(Exception e){
                    //System.out.println(e);
                }
            }
        }
    }
    private boolean sendMove(int a){
        int count=0;
        while(count<=30) {
            try {
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(moveSocket.getOutputStream()));//System.out.println(1);
                dos.writeUTF(String.valueOf(a));// System.out.println(2);
                dos.flush();//System.out.println(3);
                return true;
            } catch (Exception e) {
                try{
                    Thread.sleep(50);
                    //count+=1;
                }catch(Exception ignored){}
            }
        }
        //TODO kehre zum spielstand zurück, bevor der move gemacht wurde
        return false;
    }
    private void startMoveReceiver(){
        moveReceiver mr = new moveReceiver();
        Thread receiveMoveThread = new Thread(mr);
        receiveMoveThread.start();
    }
    class moveReceiver extends Thread{
        public void run(){
            while(running){
                try{
                    DataInputStream dis = new DataInputStream(moveSocket.getInputStream());
                    if (dis.available() > 0) {
                        int a = Integer.parseInt(dis.readUTF());
                        if (partie.isGameEnd()) { //ENDE DES SPIELS
                            //...
                        } else { //Falls das Spiel noch nicht zu ende ist
                            Partie np = new Partie(partie, true);
                            np.aktion(a);
                            partie = new Partie(np, true);
                            logMsg(a, false);
                            redrawBoard();
                        }
                    } else {
                        sleep(50);
                    }
                }catch(Exception ignored){}
            }
        }
    }
    private void startGameReceiver(){
        gameReceiver gr = new gameReceiver();
        Thread receiveGameThread = new Thread(gr);
        receiveGameThread.start();
    }
    class gameReceiver extends Thread{
        public void run(){
            while(running){
                try{
                    ObjectInputStream ois = new ObjectInputStream(gameSocket.getInputStream());
                    Object o = ois.readObject();
                    partie = new Partie((Partie) o, true);
                    redrawBoard();
                } catch(Exception e){
                    //System.out.println(e);
                }
            }
        }
    }
    private void redrawBoard(){
        enableButtons();
        redrawIchHand();
        redrawIchSpielzone();
        redrawDuSpielzone();
        redrawStatus();

        redrawIchNachziehstapel();
        redrawDuHand();
        redrawIchAbwurfzone();
        redrawDuAbwurfzone();

        redrawStatusTab();
    }
    private void enableButtons(){
        buttonFeuer.setEnabled(false);
        buttonErde.setEnabled(false);
        buttonOK.setText("OK");
        buttonOK.setEnabled(false);
        buttonCancel.setText("Abbrechen");
        buttonCancel.setEnabled(false);

        int sonderfunktion = partie.getSonderfunktion();
        if (partie.isGameEnd()){ //Ende des Spiels
            //...
        } else if (sonderfunktion!=0) {
            if (sonderfunktion==-5 && partie.ichBinAmZug()){
                buttonOK.setText("Spielen");
                buttonOK.setEnabled(partie.karteSpielenErlaubt(partie.getIch().getHandKarte(partie.getSfZaehler())));
                buttonCancel.setText("Ersetzen");
                buttonCancel.setEnabled(true);
            }
            if (sonderfunktion == -1 && partie.ichBinAmZug()) { //Auf Kampfbeginn verzichten
                buttonOK.setEnabled(partie.getSfZaehler() >= 1);
                buttonCancel.setEnabled(true);
            } else if (sonderfunktion == -2 && partie.ichBinAmZug()) { //Von Kampf zurückziehen
                buttonOK.setEnabled(true);
                buttonCancel.setEnabled(true);
            } else if ( //JA ODER NEIN
                    partie.ichBinAmZug() &&
                            (
                                    sonderfunktion == 2 || //Bezaubere den Heiligen Drachen (1)
                                    sonderfunktion == 3 || //Vernichte den Feind
                                    sonderfunktion == 4 || //Rufe den Meister (1)
                                    sonderfunktion == 15 || //Pulverisierer-Novize (1)
                                    sonderfunktion == 16 || //Schleichschritt-Novize
                                    sonderfunktion == 18 || //Nichtigmacher-Magus
                                    sonderfunktion == 27 //Eifrige Produktion
                                    || sonderfunktion == 32 //Erzürne den Heiligen Drachen (1)
                                    || sonderfunktion == 33 //Sende Verstärkung
                                    || sonderfunktion == 35 //Vertreibe die Eindringlinge
                                    || sonderfunktion == 47 //Erfinderischer Tüftler
                                    || sonderfunktion == 52 //Relikt der Segnung (1)
                                    || sonderfunktion == 62 //Rufe den Geist der Ahnen (1)
                                    || sonderfunktion == 63 //Rufe den Geist der Schlacht (1)
                                    || sonderfunktion == 79 //Schamane des Jenseits
                                    || sonderfunktion == 80 //Priester des Lebens (1)
                                    || sonderfunktion == 81 //Druide des Lebens (1)
                                    || sonderfunktion == 123 //Ehre die Gefallenen
                                    || sonderfunktion == 138 //Meuchling Charlie
                                    || sonderfunktion == 139 //Meuchling Delta (1)
                                    || sonderfunktion == 140 //Meuchling Echo
                                    || sonderfunktion == 145 //Ganzling
                                    || sonderfunktion == 153 //Plündere das Dorf
                                    || sonderfunktion == 167 //Goblinräuber (1)
                                    || sonderfunktion == 212 //Trinke das Wasser des Lebens
                                    || sonderfunktion == 213 //Ertränke den Widerstand (1)
                                    || sonderfunktion==300 //Schlitzohr
                            )
            ) {
                buttonOK.setText("Ja");
                buttonOK.setEnabled(true);
                buttonCancel.setText("Nein");
                buttonCancel.setEnabled(true);
                if (
                        (sonderfunktion == 139 && partie.getAktiverSpieler().getGegner().getHandSize() == 0) //delta(1)
                                || (sonderfunktion == 140 && partie.getAktiverSpieler().getGegner().getNachziehSize() == 0) //echo
                ) {
                    buttonOK.setEnabled(false);
                }
            } else if ( //NUR "ABBRECHEN"
                    (sonderfunktion == 1002 && partie.ichBinAmZug()) || //Bezaubere den Heiligen Drachen (2)
                            (sonderfunktion == 1015 && partie.ichBinAmZug()) || //Pulverisierer-Novize (2)
                            (sonderfunktion == 1004 && partie.ichBinAmZug()) //Rufe den Meister (2)
                            || (sonderfunktion == 1032 && partie.ichBinAmZug()) //Erzürne den Heiligen Drachen (2)
                            || (sonderfunktion == 49 && partie.ichBinAmZug()) //Talentierte Bardin
                            || (sonderfunktion == 1052 && partie.ichBinAmZug()) //Relikt der Segnung (2)
                            || (sonderfunktion == 1062 && partie.ichBinAmZug()) //Rufe den Geist der Ahnen (2)
                            || (sonderfunktion == 1063 && partie.ichBinAmZug()) //Rufe den Geist der Schlacht (2)
                            || (sonderfunktion == 136 && partie.ichBinAmZug()) //Meuchling Alpha
                            || (sonderfunktion == 137 && partie.ichBinAmZug()) //Meuchling Bravo
                            || (sonderfunktion == 198 && partie.ichBinAmZug()) //Flammenwellen-Bombe
                            || (sonderfunktion == 199 && partie.ichBinAmZug()) //Erdaushöhler-Bombe
                            || (sonderfunktion == 200 && partie.ichBinAmZug()) //Tiefenwasser-Bombe
                            || (sonderfunktion == 201 && partie.ichBinAmZug()) //Lichtsucher-Bombe
                            || (sonderfunktion == 202 && partie.ichBinAmZug()) //Monduntergangs-Bombe
                            || (sonderfunktion == 1213 && partie.ichBinAmZug()) //Ertränke den Widerstand (2)
                            || (sonderfunktion==240 && partie.ichBinAmZug()) //Unwiderstehliche Sirenen
            ) {
                buttonCancel.setEnabled(true);
                if (//NOCH OK DAZU
                        (sonderfunktion == 1002 && partie.ichBinAmZug() && partie.getSfZaehler() >= 8) //Bezaubere den Heiligen Drachen (2)
                        || (sonderfunktion == 1032 && partie.ichBinAmZug() && partie.getSfZaehler() == 2) //Erzürne den Heiligen Drachen (2)
                        || (sonderfunktion == 49 && partie.ichBinAmZug() && partie.getSfZaehler() >= 1 && partie.getSfZaehler() <= 2) //Talentierte Bardin
                        || (sonderfunktion == 198 && partie.ichBinAmZug() && partie.getSfZaehler() >= 3) //Flammenwellen-Bombe
                        || (sonderfunktion == 199 && partie.ichBinAmZug() && partie.getSfZaehler() >= 3) //Erdaushöhler-Bombe
                        || (sonderfunktion == 200 && partie.ichBinAmZug() && partie.getSfZaehler() == 1) //Tiefenwasser-Bombe
                        || (sonderfunktion == 201 && partie.ichBinAmZug() && partie.getSfZaehler() >= 5) //Lichtsucher-Bombe
                        || (sonderfunktion == 202 && partie.ichBinAmZug() && partie.getSfZaehler() >= 2) //Monduntergangs-Bombe
                        || (sonderfunktion==240 && partie.ichBinAmZug() && partie.getSfZaehler() == 1) //Unwiderstehliche Sirenen
                ) {
                    buttonOK.setEnabled(true);
                }

            } else if ( //NUR "KEINE WÄHLEN"
                    (sonderfunktion == 6 && !partie.ichBinAmZug()) || //Erwecke das Wissen (1)
                            (sonderfunktion == 1006 && partie.ichBinAmZug()) //Erwecke das Wissen (2)
                            || (sonderfunktion == 78 && partie.ichBinAmZug()) //Hexenmeister des Jenseits
                            || (sonderfunktion == 92 && partie.ichBinAmZug()) //Täusche den Heiligen Drachen
                            || (sonderfunktion == 101 && partie.ichBinAmZug()) //Kratzerhexer
                            || (sonderfunktion == 122 && partie.ichBinAmZug()) //Rekrutiere einen Krieger
                            || (sonderfunktion == 152 && partie.ichBinAmZug()) //Beschenke den Heiligen Drachen
                            || (sonderfunktion == 171 && partie.ichBinAmZug()) //Schwächlinggranate
                            || (sonderfunktion == 182 && partie.ichBinAmZug()) //Versuche den Heiligen Drachen
                            || (sonderfunktion == 184 && !partie.ichBinAmZug()) //Erwecke die Gier (1)
                            || (sonderfunktion == 1184 && partie.ichBinAmZug()) //Erwecke die Gier (2)
                            || (sonderfunktion == 194 && partie.ichBinAmZug()) //Kriegskunstmeister
                            || (sonderfunktion == 195 && partie.ichBinAmZug()) //Meisterchronist
                            || (sonderfunktion == 222 && partie.ichBinAmZug()) //Schatztaucher
                            || (sonderfunktion == 225 && partie.ichBinAmZug()) //Diebischer Taucher
            ) {
                buttonCancel.setText("Keine wählen");
                buttonCancel.setEnabled(true);
                //NOCH OK DAZU
                if (
                        (sonderfunktion == 92 && partie.ichBinAmZug() && partie.getSfZaehler() == 3) //Täusche den Heiligen Drachen
                                || (sonderfunktion == 152 && partie.ichBinAmZug() && partie.getSfZaehler() >= 8) //Beschenke den Heiligen Drachen
                                || (sonderfunktion == 182 && partie.ichBinAmZug() && partie.getSfZaehler() >= 5) //Versuche den Heiligen Drachen
                                || (sonderfunktion == 184 && !partie.ichBinAmZug() && partie.getSfZaehler() == 1) //Erwecke die Gier (1)
                                || (sonderfunktion == 1184 && partie.ichBinAmZug() && partie.getSfZaehler() == 1) //Erwecke die Gier (2)
                                || (sonderfunktion == 222 && partie.ichBinAmZug() && partie.getSfZaehler() >= 1) //Schatztaucher
                ) {
                    buttonOK.setEnabled(true);
                }
                if (sonderfunktion == 225 && partie.ichBinAmZug() && partie.getSfZaehler() >= 1) {
                    buttonCancel.setText("Rückgängig");
                    buttonOK.setEnabled(true);
                }
            } else if ( //FERTIG und ABBRECHEN
                    (sonderfunktion == 146 && partie.ichBinAmZug()) //Improvisierte Schleuder
            ) {
                buttonOK.setText("Fertig");
                buttonOK.setEnabled(true);
                buttonCancel.setEnabled(true);
            } else {
                //GAR NICHTS
                if ( //OK KNOPF DAZU
                        ((sonderfunktion == -3 && partie.ichBinAmZug()) || (sonderfunktion == -4 && partie.ichBinAmZug())) //Mulligan
                                || (sonderfunktion == 37 && !partie.ichBinAmZug() && (partie.getSfZaehler() == 3 || (partie.getSfZaehler() < 3 && partie.getSfZaehler() == partie.getIch().getHandSize()))) //Erwecke die Demut (1)
                                || (sonderfunktion == 1037 && partie.ichBinAmZug() && (partie.getSfZaehler() == 3 || (partie.getSfZaehler() < 3 && partie.getSfZaehler() == partie.getIch().getHandSize()))) //Erwecke die Demut (2)
                                || (sonderfunktion == 1080 && partie.ichBinAmZug() && partie.getSfZaehler() == 2) //Priester des Lebens (2)
                                || (sonderfunktion == 1081 && partie.ichBinAmZug() && partie.getSfZaehler() == 2) //Druide des Lebens(2)
                                || (sonderfunktion == 1167 && !partie.ichBinAmZug() && partie.getSfZaehler() == 2) //Goblinräuber (2)
                                || (sonderfunktion == 214 && !partie.ichBinAmZug() && partie.getSfZaehler() == 1) //Verteidige die Tiefe
                ) {
                    buttonOK.setEnabled(true);
                }
            }
        } else if (partie.getPhase()==ZugPhase.BEGINN && partie.ichBinAmZug() && partie.getDu().hatAktiv(237) && partie.getDu().hatAktiv(238)){ //Reißende Sturmflut & Gewaltige Gezeitenflut
            buttonOK.setEnabled(true);
            buttonOK.setText("Zurückziehen");
        } else {
            Element aktivesElement = partie.getAktivesElement();
            //FEUER BUTTON
            if (partie.feuerAnsagenErlaubt()) {
                buttonFeuer.setEnabled(true);
            }
            //ERDE BUTTON
            if (partie.erdeAnsagenErlaubt()) {
                buttonErde.setEnabled(true);
            }

            //BEENDEN BUTTON
            if (partie.ichBinAmZug()) {
                buttonOK.setEnabled(true);
                ZugPhase phase = partie.getPhase();
                if (phase != ZugPhase.ZIEH && phase != ZugPhase.ENDE && (!partie.getCharakterGespielt()||partie.getAktiverSpieler().hatAktiv(124))) {
                    if (partie.getKampfzug() == 1)
                        buttonOK.setText("Auf Kampfbeginn verzichten");
                    else
                        buttonOK.setText("Zurückziehen");
                } else if (phase == ZugPhase.CHARAKTER || phase == ZugPhase.VERSTAERKUNG) {
                    buttonOK.setText("Zum Beginn der Charakterphase");
                } else {
                    buttonOK.setText("Zug beenden");
                }

            }
        }
    }
    private void redrawStatusTab(){
        String s;
        if(partie.ichBinAmZug())
            s = " (Ich)";
        else
            s = " (Gegner)";
        statusAktiverSpieler.setText("Aktiver Spieler: "+partie.getAktiverSpieler().getName() + s);
        statusZugPhase.setText("Phase: " + partie.getPhase());
        statusKampfzug.setText("Kampfzug: " + partie.getKampfzug());
        statusSpielzug.setText("Spielzug: " + partie.getSpielzug());
    }
    private void redrawStatus(){
        Element aktivesElement = partie.getAktivesElement();
        Spieler ich = partie.getIch();
        infoLabelIchName.setText("~~~ " + ich.getName() + " ~~~");
        infoLabelIchStaerke.setText("Stärke: " + ich.getStaerke(aktivesElement) + " (" + aktivesElement + ")");
        infoLabelIchDrachen.setText("Drachen: "+ich.getDrachen());
        infoLabelIchHand.setText("Hand: "+ich.getHandSize());
        infoLabelIchCharVerst.setText("Charakter/Verstärkungen: "+ich.getCharVerst());
        infoLabelIchNachzieh.setText("Nachziehstapel: "+ich.getNachziehSize());
        Spieler du = partie.getDu();
        infoLabelDuName.setText("~~~ " + du.getName() + " ~~~");
        infoLabelDuStaerke.setText("Stärke: " + du.getStaerke(aktivesElement) + " (" + aktivesElement + ")");
        infoLabelDuDrachen.setText("Drachen: "+du.getDrachen());
        infoLabelDuHand.setText("Hand: "+du.getHandSize());
        infoLabelDuCharVerst.setText("Charakter/Verstärkungen: "+du.getCharVerst());
        infoLabelDuNachzieh.setText("Nachziehstapel: "+du.getNachziehSize());
        if(partie.getAktiverSpieler()==ich){
            jPanel1.setBackground(new java.awt.Color(204, 255, 255));
            jPanel5.setBackground(new java.awt.Color(204, 204, 204));
        } else {
            jPanel1.setBackground(new java.awt.Color(204, 204, 204));
            jPanel5.setBackground(new java.awt.Color(204, 255, 255));
        }
        String msg;
        int sonderfunktion = partie.getSonderfunktion();
        if (partie.isGameEnd()){
            msg = partie.getSieger().getName() + " gewinnt!";
        } else if (sonderfunktion == 0) {
            msg = partie.getPhase().toString();
        } else if (sonderfunktion==-3 || sonderfunktion==-4) { //Mulligan
            if (partie.ichBinAmZug()) {
                msg = "Wähle die Handkarten, die du in den Nachziehstapel mischen willst";
            } else {
                msg = "Gegner wählt Karten für Mulligan";
            }
        } else if (sonderfunktion==-5){ //Karte ersetzen
            msg = "Karte spielen oder ersetzen?";
        } else if (sonderfunktion==-2) { //[[Rückzug vom Kampf]]
            msg = "Sicher zurückziehen?";
        } else if (sonderfunktion==-1) { //[[Verzicht auf Kampfbeginn]]
            msg = "Verzicht " + partie.getSfZaehler() + "/3";
        } else if (sonderfunktion==2) { //Bezaubere den Heiligen Drachen (1)
            msg = "Karten abwerfen, um 1 Drachen zu gewinnen?";
        } else if (sonderfunktion==1002){ //Bezaubere den heiligen Drachen (2)
            msg = "Karten mit Stärke von insgesamt mindestens 8 Feuer wählen";
        } else if (sonderfunktion==3) { //Vernichte den Feind
            msg = "Alle gegn. Bleibenden-Verstärkungen abwerfen?";
        } else if (sonderfunktion==4) { //Rufe den Meister (1)
            msg = "Charakter/Verstärkung von Spielzone auf die Hand nehmen?";
        } else if (sonderfunktion==1004){ //Rufe den Meister (2)
            msg = "Charakter/Verstärkung von Spielzone auf die Hand nehmen!";
        } else if (sonderfunktion==6){ //Erwecke das Wissen (1)
            msg = "Einfache Aktion von Spielzone in den Nachziehstapel mischen? (Gegner)";
        } else if (sonderfunktion==1006){ //Erwecke das Wissen (2)
            msg = "Einfache Aktion von Spielzone in den Nachziehstapel mischen? (Aktiver Spieler)";
        } else if (sonderfunktion==15) { //Pulverisierer-Novize (1)
            msg = "Gegnerische Verstärkung abwerfen?";
        } else if (sonderfunktion==1015){ //Pulverisierer-Novize (2)
            msg = "Wähle gegnerische Verstärkung";
        } else if (sonderfunktion==16) { //Schleichschritt-Novize
            msg = "Zwei zusätzliche Karten ziehen? (Schleichschritt-Novize)";
        } else if (sonderfunktion==27){ //Eifrige Produktion
            msg = "Eine zusätzliche Karte ziehen? (Eifrige Produktion)";
        } else if (sonderfunktion==18) { //Nichtigmacher-Magus
            msg = "Gegner zwingen?";
        } else if (sonderfunktion==32) { //Erzürne den Heiligen Drachen (1)
            msg = "Karten abwerfen, um 1 Drachen zu gewinnen?";
        } else if (sonderfunktion==1032) {
            msg = "2 Einfache-Aktionen oder 2 Schlachtfelder wählen";
        } else if (sonderfunktion==33) { //Sende Verstärkung
            msg = "5 Karten ziehen?";
        } else if (sonderfunktion==35) { //Vertreibe die Eindringlinge
            msg = "Alle gegn. Karten mit dem FREI-Symbol abwerfen?";
        } else if (sonderfunktion==37) { //Erwecke die Demut (1)
            msg = "3 Eigene Handkarten abwerfen (Gegner)";
        } else if (sonderfunktion==1037) { //Erwecke die Demut (2)
            msg = "3 eigene Handkarten abwerfne (Aktiver Spieler)";
        } else if (sonderfunktion==47) { //Erfinderischer Tüftler
            msg = "Auf 9 Handkarten ziehen?";
        } else if (sonderfunktion==49) { //Talentierte Bardin
            msg = "Werfe bis zu 2 Handkarten  ab";
        } else if (sonderfunktion==52) { //Relikt der Segnung (1)
            msg = "Gegnerische Charakter/Verstärkung abwerfen?";
        } else if (sonderfunktion==1052) { //Relikt der Segnung (2)
            msg = "Werfe gegn. Charakter/Verst. ab.";
        } else if (sonderfunktion==62) { //Geist der Ahnen (1)
            msg = "Karte von Abwurfzone auf die Hand nehmen?";
        } else if (sonderfunktion==1062) { //Geist der Ahnen (2)
            msg = "Wähle eine Karte aus der eigenne Abwurfzone";
        } else if (sonderfunktion==63){ //Rufe den Geist der Schlacht (1)
            msg = "Char/Verst von Spielzone auf die Hand nehmen?";
        } else if (sonderfunktion==1063) { //Rufe den Geist der Schlacht (2)
            msg = "Char/Verst von Spielzone auf die Hand nehmen!";
        } else if (sonderfunktion==78){ //Hexenmeister des Jenseis
            msg = "Karte von Nachziehstapel wählen und auf Hand nehmen";
        } else if (sonderfunktion==79) { //Schamane des Jenseis
            msg = "2 Zufällige Karte aus der Abwurfzone auf die Hand nehmen?";
        } else if (sonderfunktion==80) { //Priester des Lebens (1)
            msg = "3 Karten ziehen und danach 2 in Nachziehstapel mischen?";
        } else if (sonderfunktion==1080) { //Priester des Lebens (2)
            msg = "Mische 2 Handkarten in den Nachziehstapel";
        } else if (sonderfunktion==81) { //Druide des Lebens (1)
            msg = "4 Karten aufdecken?";
        } else if (sonderfunktion==1081) { //Druide des Lebens (2)
            msg = "Werfe 2 der aufgedeckten Karten ab";
        } else if (sonderfunktion==92) { //Täusche den Heiligen Drachen
            msg = "3 Charaktere von der Hand abwerfen, um 1 Drachen zu kriegen.";
        } else if (sonderfunktion==101) { //Kratzerhexer
            msg = "Einen Charakter abwerfen";
        } else if (sonderfunktion==122) { //Rekrutiere einen Krieger
            msg = "Nehme Karte von Spielzone auf die Hand";
        } else if (sonderfunktion==123) { //Ehre die Gefallenen
            msg = "3 zufällige Karten aus der Abwurfzone auf die Hand nehmen?";
        } else if (sonderfunktion==136) { //Meuchling Alpha
            msg = "Werfe 1 gegn Verst. ab";
        } else if (sonderfunktion==137) { //Meuchling Bravo
            msg = "Werfe 1 Karte mit Symbol ab, außer letzem Char.";
        } else if (sonderfunktion==138) { //Meuchling Charlie
            msg = "Zusätzliche Karte ziehen (Meuchling Charlie)";
        } else if (sonderfunktion==139){ //Meuchling Delta (1)
            msg = "Gegner zwingen 1 Handkarte abzuwerfen?";
        } else if (sonderfunktion==1139) { //Meuchling Delta (2)
            msg = "Werfe 1 Handkarte ab";
        } else if (sonderfunktion==140) { //Meuchling Echo
            msg = "Oberste Karte vom gegn. Nachziehstapel abwerfen?";
        } else if (sonderfunktion==145) { //Ganzling
            msg = "Aktive Karten aktiv lassen?";
        } else if (sonderfunktion==146) { //Improvisierte Schleuder
            msg = "Eigene Char/Verst abwerfen";
        } else if (sonderfunktion==152) { //Beschenke den Heiligen Drachen
            msg = "Karten mit Stärke von insgesamt mindestens 8 Ere abwerfen, um 1 Drachen zu gewinnen";
        } else if (sonderfunktion==153) { //Plündere das Dorf
            msg = "Alle gegn. Char und Einf-Verst abwerfen?";
        } else if (sonderfunktion==167) { //Goblinräuber (1)
            msg = "Gegner zwingen 2 Handkarten abzuwerfen?";
        } else if (sonderfunktion==1167) { //Goblinräuber (2)
            msg = "Werfe 2 Handkarten ab";
        } else if (sonderfunktion==171) { //Schwächlinggranate
            msg = "Eine gegn. aktive Verstärkung abwerfen";
        } else if (sonderfunktion==182) { //Versuche den Heiligen Drachen
            msg = "Karten mit insg. mind. 5 Gold abwerfen, um 1 Drachen gewinnen.";
        } else if (sonderfunktion==184 || sonderfunktion==1184) { //Erwecke die Gier
            msg = "Karten mit 2 Feuer und 2 Erde abwerfen, um 1 Drachen zu gewinnen";
        } else if (sonderfunktion==194) { //Kriegskunstmeister
            msg = "Char/Einfach-Ver auf die Hand nehmen";
        } else if (sonderfunktion==195) { //Meisterchronist
            msg = "Gegn. Handkarte abwerfen.";
        } else if (sonderfunktion==198) { //Flammenwellen-Bombe
            msg = "Karten mit einer aufgedruckten Stärke von insg. mind. 3 Feuer abwerfen";
        } else if (sonderfunktion==199) { //Erdaushöhler-Bombe
            msg = "Karten mit einer aufgedruckten Stärke von insg. mind. 3 Erde abwerfen";
        } else if (sonderfunktion==200) { //Tiefenwasser-Bombe
            msg = "Karten mit 2 Feuer und 2 Erde abwerfen";
        } else if (sonderfunktion==201){ //Lichtsucher-Bombe
            msg = "Karten mit insg 5 Feuer oder 5 Erde abwerfen";
        } else if (sonderfunktion==202) { //Monduntergangs-Bombe
            msg = "Karten mit insg mind 2 Gold abwerfen";
        } else if (sonderfunktion==212) { //Trinke das Wasser des Lebens
            msg = "Abwurfzone in Nachziehstapel mischen?";
        } else if (sonderfunktion==213) { //Ertränke den Widerstand (1)
            msg = "Gegnerische Karten abwerfen?";
        } else if (sonderfunktion==1213) { //Ertränke den Widerstand (2)
            msg = "Welchen aktiven Charakter NICHT abwerfen?";
        } else if (sonderfunktion==214) { //Verteidige die Tiefe
            msg = "Überschüssige Handkarten abwerfen.";
        } else if (sonderfunktion==222) { //Schatztaucher
            msg = "Beliebig viele Handkarten abwerfen";
        } else if (sonderfunktion==225) { //Diebischer Taucher
            msg = "Karten von der Hand unter das Deck legen (zuletzt gewählte=unterste)";
        } else if (sonderfunktion==240) { //Unwiderstehliche Sirenen
            msg = "1 Charakter abwerfen.";



        } else if (sonderfunktion==300) { //Eifrige Produktion
            msg = "Eine zusätzliche Karte ziehen? (Schlitzohr, Gott der Zeit)";
        } else msg = "";
        infoMsg.setText("<html>Message:<br>" + msg + "</html>");

    }
    private void redrawIchNachziehstapel(){
        JLayeredPane newPane = new JLayeredPane();
        int x=0, y=0;
        int xOffset=0, yOffset=0;
        for(int i=0; i<partie.getIch().getNachziehSize(); i++){
            ImageIcon kartenIcon;
            kartenIcon = new ImageIcon(getClass().getResource(imageFolder + "000.png"));
            kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageX, imageY, Image.SCALE_SMOOTH));
            JLabel kartenLabel = new JLabel();
            kartenLabel.setIcon(kartenIcon);
            kartenLabel.setEnabled(!partie.getIch().getNachziehstapel().get(i).getSelected());
            //Füge Hoverevent hinzu und Mache sichtbar
            //Füge Clickevent hinzu
            int aktionInt = i+4000;
            if(
                    partie.getSonderfunktion()==78 //Hexenmeister des Jenseits
                    || (partie.getSonderfunktion()==1081 && i>=partie.getIch().getNachziehSize()-4 && i<=partie.getIch().getNachziehSize()-1) //Druide des Lebens
                    || partie.getSonderfunktion()==122 //Rekrutiere einen Krieger
            ){
                kartenIcon = new ImageIcon(getClass().getResource(imageFolder + partie.getIch().getNachziehstapel().get(i).getFileName()));
                kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageX, imageY, Image.SCALE_SMOOTH));
                kartenLabel.setIcon(kartenIcon);

                int hoverInt = i;
                kartenLabel.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent evt) {
                        redrawKartenDetails(partie.getIch().getNachziehstapel().get(hoverInt));
                    }
                    public void mousePressed(MouseEvent e){
                        redrawKartenDetails(partie.getIch().getNachziehstapel().get(hoverInt));
                        klickeAn(aktionInt);
                    }
                });
            } else {
                kartenLabel.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent evt) {
                        redrawKartenDetails(new Karte(0));
                    }
                    public void mousePressed(MouseEvent e){
                        redrawKartenDetails(new Karte(0));
                        klickeAn(aktionInt);
                    }
                });
            }

            kartenLabel.setBounds(xOffset, yOffset,kartenIcon.getIconWidth(),kartenIcon.getIconHeight());
            yOffset+=120;
            newPane.add(kartenLabel, i, i);

            if (xOffset+kartenIcon.getIconWidth() > x)
                x = xOffset+kartenIcon.getIconWidth();
            if (yOffset+kartenIcon.getIconHeight() > y)
                y = yOffset+kartenIcon.getIconHeight();
        }
        newPane.setPreferredSize(new Dimension(x, y));
        panelIchNachzieh.setPreferredSize(new Dimension(x, y));
        panelIchNachzieh.removeAll();
        panelIchNachzieh.add(newPane);
        panelIchNachzieh.revalidate();
        panelIchNachzieh.repaint();
    }
    private void redrawDuHand(){
        JLayeredPane newPane = new JLayeredPane();
        int x=0, y=0;
        int xOffset=0;
        int yOffset=0;
        for (int i=0; i<partie.getDu().getHandSize(); i++){
            ImageIcon kartenIcon;
            if (partie.getDu().getHandKarte(i).isAufgedeckt()){
                kartenIcon = new ImageIcon(getClass().getResource(imageFolder + partie.getDu().getHandKarte(i).getFileName()));
            } else {
                kartenIcon = new ImageIcon(getClass().getResource(imageFolder + "000.png"));
            }
            kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageX, imageY, Image.SCALE_SMOOTH));
            JLabel kartenLabel = new JLabel();
            kartenLabel.setIcon(kartenIcon);
            kartenLabel.setEnabled(!partie.getDu().getHandKarte(i).getSelected());
            //Füge Hoverevent hinzu
            int hoverInt = i;
            //Füge Clickevent hinzu
            int aktionInt = i+3000;
            kartenLabel.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    redrawKartenDetails(partie.getDu().getHand().get(hoverInt));
                }
                public void mousePressed(MouseEvent e){
                    redrawKartenDetails(partie.getDu().getHand().get(hoverInt));
                    klickeAn(aktionInt);
                }
            });

            kartenLabel.setBounds(xOffset, yOffset,kartenIcon.getIconWidth(),kartenIcon.getIconHeight());
            xOffset+=75;
            newPane.add(kartenLabel, i, i);

            if (xOffset+kartenIcon.getIconWidth() > x)
                x = xOffset+kartenIcon.getIconWidth();
            if (yOffset+kartenIcon.getIconHeight() > y)
                y = yOffset+kartenIcon.getIconHeight();
        }
        newPane.setPreferredSize(new Dimension(x, y));
        panelDuHand.setPreferredSize(new Dimension(x, y));
        panelDuHand.removeAll();
        panelDuHand.add(newPane);
        panelDuHand.revalidate();
        panelDuHand.repaint();
    }
    private void redrawDuAbwurfzone(){
        JLayeredPane newPane = new JLayeredPane();
        int x=0, y=0;
        int xOffset=0;
        int yOffset=0;
        for (int i=0; i<partie.getDu().getAbwurfzone().getSize(); i++){
            ImageIcon kartenIcon = new ImageIcon(getClass().getResource(imageFolder + partie.getDu().getAbwurfzone().get(i).getFileName()));
            //ImageIcon kartenIcon = new javax.swing.ImageIcon(getClass().getResource("/images200/009.jpg"));
            kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageX, imageY, Image.SCALE_SMOOTH));
            JLabel kartenLabel = new JLabel();
            kartenLabel.setIcon(kartenIcon);

            //Füge Hoverevent hinzu
            int hoverInt = i;
            //Füge Clickevent hinzu
            int aktionInt = i+2000;
            kartenLabel.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    redrawKartenDetails(partie.getDu().getAbwurfzone().get(hoverInt));
                }
                public void mousePressed(MouseEvent e){
                    redrawKartenDetails(partie.getDu().getAbwurfzone().get(hoverInt));
                    klickeAn(aktionInt);
                }
            });
            kartenLabel.setBounds(xOffset, yOffset,kartenIcon.getIconWidth(),kartenIcon.getIconHeight());
            yOffset+=120;
            newPane.add(kartenLabel, i, i);

            if (xOffset+kartenIcon.getIconWidth() > x)
                x = xOffset+kartenIcon.getIconWidth();
            if (yOffset+kartenIcon.getIconHeight() > y)
                y = yOffset+kartenIcon.getIconHeight();
        }
        newPane.setPreferredSize(new Dimension(x, y));
        panelDuAbwurf.setPreferredSize(new Dimension(x, y));
        panelDuAbwurf.removeAll();
        panelDuAbwurf.add(newPane);
        panelDuAbwurf.revalidate();
        panelDuAbwurf.repaint();
    }
    private void redrawIchAbwurfzone(){
        JLayeredPane newPane = new JLayeredPane();
        int x=0, y=0;
        int xOffset=0;
        int yOffset=0;
        for (int i=0; i<partie.getIch().getAbwurfzone().getSize(); i++){
            ImageIcon kartenIcon = new ImageIcon(getClass().getResource(imageFolder + partie.getIch().getAbwurfzone().get(i).getFileName()));
            //ImageIcon kartenIcon = new javax.swing.ImageIcon(getClass().getResource("/images200/009.jpg"));
            kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageX, imageY, Image.SCALE_SMOOTH));
            JLabel kartenLabel = new JLabel();
            kartenLabel.setIcon(kartenIcon);

            //Füge Hoverevent hinzu
            int hoverInt = i;
            //Füge Clickevent hinzu
            int aktionInt = i+1000;
            kartenLabel.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    redrawKartenDetails(partie.getIch().getAbwurfzone().get(hoverInt));
                }
                public void mousePressed(MouseEvent e){
                    redrawKartenDetails(partie.getIch().getAbwurfzone().get(hoverInt));
                    klickeAn(aktionInt);
                }
            });

            kartenLabel.setBounds(xOffset, yOffset,kartenIcon.getIconWidth(),kartenIcon.getIconHeight());
            yOffset+=120;
            newPane.add(kartenLabel, i, i);

            if (xOffset+kartenIcon.getIconWidth() > x)
                x = xOffset+kartenIcon.getIconWidth();
            if (yOffset+kartenIcon.getIconHeight() > y)
                y = yOffset+kartenIcon.getIconHeight();
        }
        newPane.setPreferredSize(new Dimension(x, y));
        panelIchAbwurf.setPreferredSize(new Dimension(x, y));
        panelIchAbwurf.removeAll();
        panelIchAbwurf.add(newPane);
        panelIchAbwurf.revalidate();
        panelIchAbwurf.repaint();
    }
    private  void redrawDuSpielzone(){
        JLayeredPane newPane = new JLayeredPane();
        int x=0, y=0;
        int xOffset = 0;
        int yOffset = 0;
        for (int j=0; j<=3; j++) {
            for (int i = 0; i < partie.getDu().getSpielzone().getSize(); i++) {
                KartenTyp typ = partie.getDu().getSpielzone().get(i).getTyp();
                if (
                        ((typ==KartenTyp.ANFUEHRER||typ==KartenTyp.GOTT) && j==0) ||
                        ((typ==KartenTyp.CHARAKTER||typ==KartenTyp.EINFACHE_VERSTAERKUNG) && j==1) ||
                        ((typ==KartenTyp.BLEIBENDE_VERSTAERKUNG||typ==KartenTyp.BLEIBENDE_AKTION) && j==2) ||
                        (typ== KartenTyp.EINFACHE_AKTION && j==3)
                ) {
                    ImageIcon kartenIcon = new ImageIcon(getClass().getResource(imageFolder + partie.getDu().getSpielzone().get(i).getFileName()));
                    //ImageIcon kartenIcon = new javax.swing.ImageIcon(getClass().getResource("/images200/009.jpg"));
                    kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageX, imageY, Image.SCALE_SMOOTH));
                    JLabel kartenLabel = new JLabel();
                    kartenLabel.setIcon(kartenIcon);
                    kartenLabel.setEnabled(partie.getDu().getSpielzone().get(i).istAktiv());

                    //Füge Hoverevent hinzu
                    int hoverInt = i;
                    //Füge Clickevent hinzu
                    int aktionInt = i + 200;
                    kartenLabel.addMouseListener(new MouseAdapter() {
                        public void mouseEntered(MouseEvent evt) {
                            redrawKartenDetails(partie.getDu().getSpielzone().get(hoverInt));
                        }
                        public void mousePressed(MouseEvent ev) {
                            redrawKartenDetails(partie.getDu().getSpielzone().get(hoverInt));
                            if (partie.getSonderfunktion()==0 && partie.ichBinAmZug())
                                showContextMenu(ev, aktionInt, partie.getDu().getSpielzone());
                            else
                                klickeAn(aktionInt);
                        }
                    });

                    kartenLabel.setBounds(xOffset, yOffset, kartenIcon.getIconWidth(), kartenIcon.getIconHeight());
                    xOffset+=75;
                    newPane.add(kartenLabel, i+100*j, i+100*j);

                    if (xOffset + kartenIcon.getIconWidth() > x)
                        x = xOffset + kartenIcon.getIconWidth();
                    if (yOffset + kartenIcon.getIconHeight() > y)
                        y = yOffset + kartenIcon.getIconHeight();
                }
            }
            xOffset+=125+10;
            //yOffset=0;
        }
        newPane.setPreferredSize(new Dimension(x, y));
        panelDuSpielzone.setPreferredSize(new Dimension(x, y));
        panelDuSpielzone.removeAll();
        panelDuSpielzone.add(newPane);
        panelDuSpielzone.revalidate();
        panelDuSpielzone.repaint();
    }
    private void redrawIchSpielzone(){
        JLayeredPane newPane = new JLayeredPane();
        int x=0, y=0;
        int xOffset = 0;
        int yOffset = 0;
        for (int j=0; j<=3; j++) {
            for (int i = 0; i < partie.getIch().getSpielzone().getSize(); i++) {
                KartenTyp typ = partie.getIch().getSpielzone().get(i).getTyp();
                if (
                        ((typ==KartenTyp.ANFUEHRER||typ==KartenTyp.GOTT) && j==0) ||
                        ((typ==KartenTyp.CHARAKTER||typ==KartenTyp.EINFACHE_VERSTAERKUNG) && j==1) ||
                        ((typ==KartenTyp.BLEIBENDE_VERSTAERKUNG||typ==KartenTyp.BLEIBENDE_AKTION) && j==2) ||
                        (typ== KartenTyp.EINFACHE_AKTION && j==3)
                ){
                    ImageIcon kartenIcon = new ImageIcon(getClass().getResource(imageFolder + partie.getIch().getSpielzone().get(i).getFileName()));
                    kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageX, imageY, Image.SCALE_SMOOTH));
                    JLabel kartenLabel = new JLabel();
                    kartenLabel.setIcon(kartenIcon);
                    kartenLabel.setEnabled(partie.getIch().getSpielzone().get(i).istAktiv());

                    //Füge Hoverevent hinzu
                    int hoverInt = i;
                    //Füge Clickevent hinzu
                    int aktionInt = i + 100;
                    kartenLabel.addMouseListener(new MouseAdapter() {
                        public void mouseEntered(MouseEvent evt) {
                            redrawKartenDetails(partie.getIch().getSpielzone().get(hoverInt));
                        }
                        public void mousePressed(MouseEvent ev) {
                            redrawKartenDetails(partie.getIch().getSpielzone().get(hoverInt));
                            if (partie.getSonderfunktion()==0 && partie.ichBinAmZug())
                                showContextMenu(ev, aktionInt, partie.getIch().getSpielzone());
                            else
                                klickeAn(aktionInt);
                        }
                    });
                    kartenLabel.setBounds(xOffset, yOffset, kartenIcon.getIconWidth(), kartenIcon.getIconHeight());
                    xOffset+=75;
                    //yOffset+=15;
                    newPane.add(kartenLabel, i+100*j, i+100*j);

                    if (xOffset + kartenIcon.getIconWidth() > x)
                        x = xOffset + kartenIcon.getIconWidth();
                    if (yOffset + kartenIcon.getIconHeight() > y)
                        y = yOffset + kartenIcon.getIconHeight();
                }
            }
            xOffset+=125+10;
            //yOffset=0;
        }

        newPane.setPreferredSize(new Dimension(x, y));
        panelIchSpielzone.setPreferredSize(new Dimension(x, y));
        panelIchSpielzone.removeAll();
        panelIchSpielzone.add(newPane);
        panelIchSpielzone.revalidate();
        panelIchSpielzone.repaint();
    }
    private void redrawKartenDetails(Karte pKarte){
        if(!contextMenu.isShowing()){
            ImageIcon kartenIcon = new ImageIcon(getClass().getResource(imageFolder + pKarte.getFileName()));
            kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageX, imageY, Image.SCALE_SMOOTH));
            detailBild.setIcon(kartenIcon);
        }
    }
    private void redrawIchHand(){
        JPanel newPanel = new JPanel();
        for(int i=0; i<partie.getIch().getHandSize(); i++){
            Karte karte = partie.getIch().getHand().get(i);
            ImageIcon kartenIcon = new ImageIcon(getClass().getResource(imageFolder + karte.getFileName()));
            kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageX, imageY, Image.SCALE_SMOOTH));

            JLabel kartenLabel = new JLabel();
            kartenLabel.setIcon(kartenIcon);
            //kartenLabel.setEnabled(partie.ichBinAmZug() && !partie.getIch().getHandKarte(i).getSelected()); //enablen muss später differenzierter sein (mandatory effects im gegnerischen zug)
            kartenLabel.setEnabled(!partie.getIch().getHandKarte(i).getSelected());
            //Füge Hoverevent hinzu
            int hoverInt = i;
            //Füge Clickevent hinzu
            int aktionInt = i+1;
            kartenLabel.addMouseListener(new MouseAdapter(){
                public void mouseEntered(MouseEvent evt) {
                    redrawKartenDetails(partie.getIch().getHand().get(hoverInt));
                }
                public void mousePressed(MouseEvent ev){
                    redrawKartenDetails(partie.getIch().getHand().get(hoverInt));
                    if (partie.getSonderfunktion()==0 && partie.ichBinAmZug())
                        showContextMenu(ev, aktionInt, partie.getIch().getHand());
                    else
                        versucheZuSpielen(aktionInt);
                }
            });
            newPanel.add(kartenLabel);
        }
        panelIchHand.removeAll();
        panelIchHand.add(newPanel);
        panelIchHand.revalidate();
        panelIchHand.repaint();
    }

    private void klickeAn(int pIndex){
        doAction(pIndex);
    }
    private void versucheZuSpielen(int pIndex){
        Spieler aktiverSpieler = partie.getAktiverSpieler();
        int sonderfunktion = partie.getSonderfunktion();
        if (sonderfunktion == 0
                && partie.ichBinAmZug()
                && pIndex >= 1 && pIndex <= aktiverSpieler.getHandSize()
                && (
                    partie.karteSpielenErlaubt(aktiverSpieler.getHandKarte(pIndex - 1))
                    || partie.getIch().getHandKarte(pIndex-1).hatSymbol(KartenSymbol.ERSETZEN,partie.getDu())
                    || partie.getIch().hatAktiv(298) //Argusauge
                )
        ) {
            if (
                (partie.getIch().getHandKarte(pIndex-1).hatSymbol(KartenSymbol.ERSETZEN,partie.getDu())
                || partie.getIch().hatAktiv(298)) //Argusauge
                && (partie.getPhase()==ZugPhase.BEGINN || partie.getPhase()==ZugPhase.AKTION)
            ){
                doAction(pIndex-101);
            } else {
                doAction(pIndex);
            }
        } else if (pIndex >= 1 && pIndex <= partie.getIch().getHandSize()){
            if (sonderfunktion==-1 && partie.getSfZaehler()<3 && partie.ichBinAmZug()){ //Auf Kampfbeginn verzichten
                doAction(pIndex);
            } else if (
                    sonderfunktion==-3 //Mulligan 1
                    || sonderfunktion==-4 //Mulligan 2
                    || sonderfunktion==1002 //Bezaubere den Heiligen Drachen
                    || sonderfunktion==1032 //Erzürne den Heiligen Drachen
                    || sonderfunktion==37 //Erwecke die Demut (1)
                    || sonderfunktion==1037 //Erwecke die Demut (2)
                    || sonderfunktion==1080 //Priester des Lebens(2)
                    || sonderfunktion==92 //Täusche den Heiligen Drachen
                    || sonderfunktion==1139 //Meuchling Delta (2)
                    || sonderfunktion==152 //Beschenke den Heiligen Drachen
                    || sonderfunktion==1167 //Goblinräuber
                    || sonderfunktion==182 //Versuche den Heiligen Drachen
                    || sonderfunktion==184 //Erwecke die Gier (1)
                    || sonderfunktion==1184 //Erwecke die Gier (2)
                    || sonderfunktion==198 //Flammenwellen-Bombe
                    || sonderfunktion==199 //Erdaushöhler-Bombe
                    || sonderfunktion==200 //Tiefenwasser-Bombe
                    || sonderfunktion==201 //Lichtsucher-Bombe
                    || sonderfunktion==202 //Monduntergangs-Bombe
                    || sonderfunktion==214 //Verteidige die Tiefe
                    || sonderfunktion==222 //Schatztaucher
                    || sonderfunktion==225 //Diebischer Taucher
                    || sonderfunktion==240 //Unwiderstehliche Sirenen
            ){
                doAction(pIndex);
            }
        }
    }
}