


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class GameGUI extends JFrame implements ActionListener {
    //Connection
    private Socket gameSocket;
    private Socket moveSocket;
    private Socket chatSocket;
    private int gameStatus = 0;
    private boolean iAmHost;

    //Game
    private String configIchName;
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

    private Karte hoverKarte = new Karte(0);
    private int imageSizeHover; private JLabel labelISH;
    private int imageSizeDuSpiel; private JLabel labelISDS;
    private int imageSizeIchSpiel; private  JLabel labelISIS;
    private int imageSizeIchHand; private JLabel labelISIH;
    private int imageSizeDialogs; private JLabel labelISE;
    private int[] imageSizeX = {100, 150, 200, 250, 300, 350, 400, 450, 500};
    private int[] imageSizeY = {139, 209, 279, 349, 418, 488, 558, 627, 697};
    private JTextArea infoMsg;

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
    private JLabel infoLabelIchAbwurf;
    private JLabel infoLabelDuAbwurf;
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

    private javax.swing.JPanel panelIchInfos;
    private javax.swing.JPanel panelDuInfos;

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
    private JPanel panelNextSF;
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


    private javax.swing.JButton buttonDuSpielzGroesser;
    private javax.swing.JButton buttonDuSpielzKleiner;
    private javax.swing.JButton buttonHandGroesser;
    private javax.swing.JButton buttonHandKleiner;
    private javax.swing.JButton buttonHoverGroesser;
    private javax.swing.JButton buttonHoverKleiner;
    private javax.swing.JButton buttonIchSpielzGroesser;
    private javax.swing.JButton buttonIchSpielzKleiner;
    private JButton buttonDialogsKleiner;
    private JButton buttonDialogsGroesser;
    private javax.swing.JButton buttonOptionenSpeichern;
    private javax.swing.JPanel panelMessage;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel panelOptionen;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel labelBigCard;
    private javax.swing.JLabel labelDuSpielzOption;
    private javax.swing.JLabel labelHoverOption;
    private javax.swing.JLabel labelIchHandOption;
    private JLabel labelDialogsOptionen;
    private javax.swing.JLabel labelIchSpielzOption;
    private javax.swing.JLabel labelSfxOption;
    private javax.swing.JPanel panelChatLogSettings;
    private javax.swing.JPanel panelMessageAndButtons;
    private javax.swing.JPanel panelRight;
    private javax.swing.JRadioButton radioSfxAn;
    private javax.swing.JRadioButton radioSfxAus;
    private javax.swing.JScrollPane scrollpaneChat;
    private javax.swing.JTabbedPane tabbedpane;

    private JDialog dialogDuHand;
    private JDialog dialogIchAbwurf;
    private JDialog dialogDuAbwurf;
    private JDialog dialogIchNachzieh;
    private JDialog dialogDuNachzieh;
    private JDialog dialogNextSF;

    private JPanel panelDuNachzieh;

    private String configDeck;
    private String configIp;
    private String configPort;
    private int configCSHover;
    private int configCSDuSpiel;
    private int configCSIchSpiel;
    private int configCSIchHand;
    private int configCSExtra;
    private int configHost;


    //GameGUINew(boolean pIAmHost, Socket pGameSocket, Socket pMoveSocket, Socket pChatSocket, String pIchName, ArrayList<Karte> pDeck, int pAnfuehrer, int pGott, ConnectGUI pConnectGUI, File picFile) {
    GameGUI(boolean pIAmHost, Socket pGameSocket, Socket pMoveSocket, Socket pChatSocket, String pIchName, ArrayList<Karte> pDeck, int pAnfuehrer, int pGott, ConnectGUI pConnectGUI,
            int pCSHover, int pCSDuSpiel, int pCSIchSpiel, int pCSIchHand, int pCSExtra,
            String pConfigDeck, String pConfigIp, String pConfigPort) {
        gameSocket = pGameSocket;
        moveSocket = pMoveSocket;
        chatSocket = pChatSocket;
        iAmHost = pIAmHost;
        configIchName = pIchName;
        myDeck = pDeck;
        myAnfuehrer = pAnfuehrer;
        myGott = pGott;
        connectgui = pConnectGUI;

        imageSizeHover = pCSHover;
        imageSizeDuSpiel = pCSDuSpiel;
        imageSizeIchSpiel = pCSIchSpiel;
        imageSizeIchHand = pCSIchHand;
        imageSizeDialogs = pCSExtra;

        ichName = pIchName;
        configDeck = pConfigDeck;
        configIp = pConfigIp;
        configPort = pConfigPort;
        configCSHover = pCSHover;
        configCSDuSpiel = pCSDuSpiel;
        configCSIchSpiel = pCSIchSpiel;
        configCSIchHand = pCSIchHand;
        configCSExtra = pCSExtra;
        if (iAmHost)
            configHost=1;
        else
            configHost=0;

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

    private void startGame() {
        running = true;
        initComponents();
        initializeGameStatus();
    }

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
        infoLabelDuName = new javax.swing.JLabel();
        infoLabelDuDrachen = new javax.swing.JLabel();
        infoLabelDuHand = new javax.swing.JLabel();
        infoLabelDuHand.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                openDuHand();
            }
        });
        infoLabelDuCharVerst = new javax.swing.JLabel();
        infoLabelDuNachzieh = new javax.swing.JLabel();
        infoLabelDuNachzieh.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                openDuNachzieh();
            }
        });
        infoLabelIchName = new javax.swing.JLabel();
        infoLabelIchStaerke = new JLabel();
        infoLabelDuStaerke = new JLabel();
        infoLabelIchAbwurf = new JLabel();
        infoLabelIchAbwurf.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                openIchAbwurf();
            }
        });
        infoLabelDuAbwurf = new JLabel();
        infoLabelDuAbwurf.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                openDuAbwurf();
            }
        });
        infoMsg = new JTextArea();
        infoMsg.setLineWrap(true);
        infoMsg.setEditable(false);
        buttonOK = new javax.swing.JButton();
        buttonFeuer = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();
        buttonErde = new javax.swing.JButton();
        infoLabelIchDrachen = new javax.swing.JLabel();
        infoLabelIchHand = new javax.swing.JLabel();
        infoLabelIchCharVerst = new javax.swing.JLabel();
        infoLabelIchNachzieh = new javax.swing.JLabel();
        infoLabelIchNachzieh.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                openIchNachzieh();
            }
        });


        panelRight = new javax.swing.JPanel();
        labelBigCard = new javax.swing.JLabel();
        panelDuInfos = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();

        panelIchInfos = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        panelMessageAndButtons = new javax.swing.JPanel();
        buttonCancel = new javax.swing.JButton();
        buttonCancel.addActionListener(this);
        buttonOK = new javax.swing.JButton();
        buttonOK.addActionListener(this);
        buttonFeuer = new javax.swing.JButton();
        buttonFeuer.addActionListener(this);
        buttonErde = new javax.swing.JButton();
        buttonErde.addActionListener(this);
        panelMessage = new javax.swing.JPanel();
        panelDuSpielzone = new javax.swing.JPanel();
        panelIchSpielzone = new javax.swing.JPanel();
        panelIchHand = new javax.swing.JPanel();
        panelChatLogSettings = new javax.swing.JPanel();
        tabbedpane = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        bChat = new javax.swing.JButton();
        bChat.addActionListener(this);
        tInput = new javax.swing.JTextField();
        scrollpaneChat = new javax.swing.JScrollPane();
        taChat = new javax.swing.JTextArea();
        taChat.setLineWrap(true);
        taChat.setEditable(false);
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        taLog2 = new javax.swing.JTextArea();
        taLog2.setLineWrap(true);
        taLog2.setEditable(false);
        panelOptionen = new javax.swing.JPanel();
        labelHoverOption = new javax.swing.JLabel();
        buttonHoverGroesser = new javax.swing.JButton();
        buttonHoverKleiner = new javax.swing.JButton();
        labelDuSpielzOption = new javax.swing.JLabel();
        buttonDuSpielzGroesser = new javax.swing.JButton();
        buttonDuSpielzKleiner = new javax.swing.JButton();
        labelIchSpielzOption = new javax.swing.JLabel();
        buttonIchSpielzKleiner = new javax.swing.JButton();
        buttonIchSpielzGroesser = new javax.swing.JButton();
        labelIchHandOption = new javax.swing.JLabel();
        labelDialogsOptionen = new JLabel();
        buttonHandGroesser = new javax.swing.JButton();
        buttonHandKleiner = new javax.swing.JButton();
        buttonDialogsKleiner = new JButton();
        buttonDialogsGroesser = new JButton();
        buttonOptionenSpeichern = new javax.swing.JButton();
        labelSfxOption = new javax.swing.JLabel();
        radioSfxAn = new javax.swing.JRadioButton();
        radioSfxAus = new javax.swing.JRadioButton();

        labelISH = new JLabel();
        labelISDS = new JLabel();
        labelISIS = new JLabel();
        labelISIH = new JLabel();
        labelISE = new JLabel();


        dialogDuHand = new JDialog(this, "Gegnerische Hand");
        panelDuHand = new JPanel();
        dialogDuHand.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                dialogDuHand.dispose();
            }
        });

        dialogIchAbwurf = new JDialog();
        panelIchAbwurf = new JPanel();
        dialogIchAbwurf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                dialogIchAbwurf.dispose();
            }
        });

        dialogDuAbwurf = new JDialog();
        panelDuAbwurf = new JPanel();
        dialogDuAbwurf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                dialogDuAbwurf.dispose();
            }
        });

        dialogIchNachzieh = new JDialog();
        panelIchNachzieh = new JPanel();
        dialogIchNachzieh.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                dialogIchNachzieh.dispose();
            }
        });

        dialogDuNachzieh = new JDialog();
        panelDuNachzieh = new JPanel();
        dialogDuNachzieh.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                dialogDuNachzieh.dispose();
            }
        });

        dialogNextSF = new JDialog();
        panelNextSF = new JPanel();
        dialogNextSF.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(960, 540));

        panelRight.setBackground(new java.awt.Color(255, 204, 204));


        ImageIcon kartenIcon = new ImageIcon(getClass().getResource("/images200/000.png"));
        kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(200, 279, Image.SCALE_SMOOTH));
        labelBigCard.setIcon(kartenIcon);
        //labelBigCard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images200/000.png")));

        javax.swing.GroupLayout panelRightLayout = new javax.swing.GroupLayout(panelRight);
        panelRight.setLayout(panelRightLayout);
        panelRightLayout.setHorizontalGroup(
                panelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRightLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(labelBigCard))
        );
        panelRightLayout.setVerticalGroup(
                panelRightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(labelBigCard, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        panelDuInfos.setBackground(new java.awt.Color(102, 102, 255));

        infoLabelDuName.setText("Spieler DU");

        infoLabelDuDrachen.setText("Drachen: 0");

        infoLabelDuStaerke.setText("Stärke: 7 (FEUER)");

        infoLabelDuAbwurf.setText("DU ABWURF");

        infoLabelDuNachzieh.setText("Nachziehstapel: 19");

        infoLabelDuCharVerst.setText("Abwurfzone: 3");

        infoLabelDuHand.setText("Hand: 6");

        javax.swing.GroupLayout panelDuInfosLayout = new javax.swing.GroupLayout(panelDuInfos);
        panelDuInfos.setLayout(panelDuInfosLayout);
        panelDuInfosLayout.setHorizontalGroup(
                panelDuInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelDuInfosLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelDuInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator2)
                                        .addGroup(panelDuInfosLayout.createSequentialGroup()
                                                .addGroup(panelDuInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(infoLabelDuName)
                                                        .addComponent(infoLabelDuDrachen)
                                                        .addComponent(infoLabelDuStaerke)
                                                        .addComponent(infoLabelDuAbwurf)
                                                        .addComponent(infoLabelDuNachzieh)
                                                        .addComponent(infoLabelDuCharVerst)
                                                        .addComponent(infoLabelDuHand))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        panelDuInfosLayout.setVerticalGroup(
                panelDuInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelDuInfosLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(infoLabelDuName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(infoLabelDuDrachen)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelDuStaerke)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelDuAbwurf)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelDuNachzieh)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelDuCharVerst)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelDuHand)
                                .addContainerGap(0, Short.MAX_VALUE))
        );

        panelIchInfos.setBackground(new java.awt.Color(204, 255, 204));

        infoLabelIchName.setText("Spieler ICH");

        infoLabelIchDrachen.setText("Drachen: 2");

        infoLabelIchStaerke.setText("Stärke: 5 (FEUER)");

        infoLabelIchAbwurf.setText("ICH ABWRFU");

        infoLabelIchNachzieh.setText("Nachziehstapel: 17");

        infoLabelIchCharVerst.setText("Abwurfzone: 5");

        infoLabelIchHand.setText("Hand: 7");

        javax.swing.GroupLayout panelIchInfosLayout = new javax.swing.GroupLayout(panelIchInfos);
        panelIchInfos.setLayout(panelIchInfosLayout);
        panelIchInfosLayout.setHorizontalGroup(
                panelIchInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelIchInfosLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelIchInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator1)
                                        .addGroup(panelIchInfosLayout.createSequentialGroup()
                                                .addGroup(panelIchInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(infoLabelIchName)
                                                        .addComponent(infoLabelIchDrachen)
                                                        .addComponent(infoLabelIchStaerke)
                                                        .addComponent(infoLabelIchAbwurf)
                                                        .addComponent(infoLabelIchNachzieh)
                                                        .addComponent(infoLabelIchCharVerst)
                                                        .addComponent(infoLabelIchHand))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        panelIchInfosLayout.setVerticalGroup(
                panelIchInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelIchInfosLayout.createSequentialGroup()
                                //.addContainerGap(0, Short.MAX_VALUE)
                                .addContainerGap()
                                .addComponent(infoLabelIchName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelIchDrachen)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelIchStaerke)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelIchAbwurf)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelIchNachzieh)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelIchCharVerst)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoLabelIchHand)
                                .addContainerGap())
        );

        panelMessageAndButtons.setBackground(new java.awt.Color(0, 204, 204));

        buttonCancel.setText("Abbrechen");

        buttonOK.setText("OK");

        buttonFeuer.setText("Feuer");

        buttonErde.setText("Erde");


        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(panelMessage);
        panelMessage.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(infoMsg)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(infoMsg)
                        .addGap(0, 0, Short.MAX_VALUE)
        );


        javax.swing.GroupLayout panelMessageAndButtonsLayout = new javax.swing.GroupLayout(panelMessageAndButtons);
        panelMessageAndButtons.setLayout(panelMessageAndButtonsLayout);
        panelMessageAndButtonsLayout.setHorizontalGroup(
                panelMessageAndButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(panelMessageAndButtonsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(panelMessageAndButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(panelMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE)
                                        .addComponent(buttonOK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(panelMessageAndButtonsLayout.createSequentialGroup()
                                                .addComponent(buttonFeuer, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonErde, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                                        .addComponent(buttonCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        panelMessageAndButtonsLayout.setVerticalGroup(
                panelMessageAndButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMessageAndButtonsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(panelMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelMessageAndButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(buttonFeuer)
                                        .addComponent(buttonErde))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(buttonOK)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonCancel)
                                .addContainerGap())
        );

        panelDuSpielzone.setBackground(new java.awt.Color(255, 153, 153));
        panelDuSpielzone.setMinimumSize(new java.awt.Dimension(0, 0));
        sPaneDuSpielzone = new JScrollPane(panelDuSpielzone);


        panelIchSpielzone.setBackground(new java.awt.Color(102, 255, 102));
        panelIchSpielzone.setMinimumSize(new java.awt.Dimension(0, 0));
        panelIchSpielzone.setPreferredSize(new java.awt.Dimension(0, 0));
        sPaneIchSpielzone = new JScrollPane(panelIchSpielzone);


        panelIchHand.setBackground(new java.awt.Color(204, 204, 0));
        sPaneIchHand = new javax.swing.JScrollPane(panelIchHand);

        panelChatLogSettings.setBackground(new java.awt.Color(153, 255, 153));

        tabbedpane.setMinimumSize(new java.awt.Dimension(0, 0));

        jPanel2.setBackground(new java.awt.Color(153, 0, 153));

        bChat.setText("send");

        tInput.setText("");

        taChat.setColumns(20);
        taChat.setRows(5);
        scrollpaneChat.setViewportView(taChat);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(scrollpaneChat, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(tInput, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(bChat)))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollpaneChat, javax.swing.GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(tInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bChat))
                                .addContainerGap())
        );

        tabbedpane.addTab("Chat", jPanel2);

        taLog2.setColumns(20);
        taLog2.setRows(5);
        jScrollPane2.setViewportView(taLog2);

        jScrollPane1.setViewportView(jScrollPane2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
                                .addContainerGap())
        );

        tabbedpane.addTab("GameLog", jPanel3);

        labelHoverOption.setText("hover");

        buttonHoverGroesser.setText("+");
        buttonHoverGroesser.addActionListener(this);
        buttonHoverKleiner.setText("-");
        buttonHoverKleiner.addActionListener(this);

        labelDuSpielzOption.setText("duspielz");

        buttonDuSpielzGroesser.setText("+");
        buttonDuSpielzGroesser.addActionListener(this);
        buttonDuSpielzKleiner.setText("-");
        buttonDuSpielzKleiner.addActionListener(this);

        labelIchSpielzOption.setText("ichspielz");

        buttonIchSpielzKleiner.setText("-");
        buttonIchSpielzKleiner.addActionListener(this);
        buttonIchSpielzGroesser.setText("+");
        buttonIchSpielzGroesser.addActionListener(this);

        labelIchHandOption.setText("ichahnd");

        buttonHandGroesser.setText("+");
        buttonHandGroesser.addActionListener(this);
        buttonHandKleiner.setText("-");
        buttonHandKleiner.addActionListener(this);

        labelDialogsOptionen.setText("popups");

        buttonDialogsGroesser.setText("+");
        buttonDialogsGroesser.addActionListener(this);
        buttonDialogsKleiner.setText("-");
        buttonDialogsKleiner.addActionListener(this);

        buttonOptionenSpeichern.setText("Speichern");
        buttonOptionenSpeichern.addActionListener(this);

        labelSfxOption.setText("sfx");

        radioSfxAn.setText("an");

        radioSfxAus.setText("aus");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(panelOptionen);
        panelOptionen.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(labelHoverOption)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(buttonHoverKleiner)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelISH)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonHoverGroesser))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(labelDuSpielzOption)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(buttonDuSpielzKleiner)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelISDS)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonDuSpielzGroesser))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(labelIchSpielzOption)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(buttonIchSpielzKleiner)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelISIS)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonIchSpielzGroesser))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(labelIchHandOption)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(buttonHandKleiner)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelISIH)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonHandGroesser))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(labelDialogsOptionen)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(buttonDialogsKleiner)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(labelISE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonDialogsGroesser))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(buttonOptionenSpeichern))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(labelSfxOption)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(radioSfxAus)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(radioSfxAn)))
                                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelHoverOption)
                                        .addComponent(buttonHoverGroesser)
                                        .addComponent(labelISH)
                                        .addComponent(buttonHoverKleiner))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelDuSpielzOption)
                                        .addComponent(buttonDuSpielzGroesser)
                                        .addComponent(labelISDS)
                                        .addComponent(buttonDuSpielzKleiner))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelIchSpielzOption)
                                        .addComponent(buttonIchSpielzKleiner)
                                        .addComponent(labelISIS)
                                        .addComponent(buttonIchSpielzGroesser))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelIchHandOption)
                                        .addComponent(buttonHandGroesser)
                                        .addComponent(labelISIH)
                                        .addComponent(buttonHandKleiner))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelDialogsOptionen)
                                        .addComponent(buttonDialogsGroesser)
                                        .addComponent(labelISE)
                                        .addComponent(buttonDialogsKleiner))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelSfxOption)
                                        .addComponent(radioSfxAn)
                                        .addComponent(radioSfxAus))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(buttonOptionenSpeichern)
                                .addContainerGap())
        );

        tabbedpane.addTab("Optionen", new JScrollPane(panelOptionen));

        javax.swing.GroupLayout panelChatLogSettingsLayout = new javax.swing.GroupLayout(panelChatLogSettings);
        panelChatLogSettings.setLayout(panelChatLogSettingsLayout);
        panelChatLogSettingsLayout.setHorizontalGroup(
                panelChatLogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tabbedpane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
        );
        panelChatLogSettingsLayout.setVerticalGroup(
                panelChatLogSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tabbedpane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(panelMessageAndButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(panelIchInfos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(panelDuInfos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(sPaneDuSpielzone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE)
                                        .addComponent(sPaneIchSpielzone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(sPaneIchHand, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(panelRight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(panelChatLogSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(panelRight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(panelChatLogSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(panelDuInfos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(sPaneDuSpielzone, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(panelIchInfos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(sPaneIchSpielzone, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(panelMessageAndButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(sPaneIchHand, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))))
                                .addContainerGap())
        );
        checkSaveButton();
        redrawKartenDetails(new Karte(0));
        setMinimumSize(new Dimension(640, 480));
        pack();
        setSize(new Dimension(1200, 800));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                int x = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit ?", "Comform !",
                        JOptionPane.YES_NO_OPTION);

                if (x == JOptionPane.YES_OPTION) {
                    running = false;
                    connectgui.reopen();
                    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                } else {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    private void logMsg(int pMsg, boolean pIch) {
        String msg = "";
        if (pIch) {
            msg = "Ich: ";
        } else {
            msg = "Du: ";
        }
        msg += partie.getMsg();
        msg += ", Aktion " + pMsg;
        msg += " (" + partie.getPhase() + ")";
        taLog2.setText(taLog2.getText() + msg + "\n");
    }

    private void doAction(int n) {
        if (partie.aktion(n)) {
            if (partie.gottaSend()) {
                sendObject(partie);
            } else {
                sendMove(n);
            }
            gottaShowCheck();
            gottaOpenCheck();
            if(partie.gottaOpenNextSF()){
                openNextSFDialog();
            }
            redrawBoard();
            logMsg(n, true);
        }
    }

    void openNextSFDialog(){
        dialogNextSF = new JDialog(this, "NÄCHSTE SONDERFUNKTION WÄHLEN");
        dialogNextSF.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialogNextSF.add(new JScrollPane(panelNextSF));
        int width = redrawNextSFPanel() + 50; // + kartenhöhe * X todo
        int height = 350; //todo
        dialogNextSF.setSize(width, height);
        dialogNextSF.setVisible(true);
    }

    void showContextMenu(MouseEvent pMouseEvent, int pIndex, Zone pZone) {
        contextMenu.removeAll();
        getContextMenuItems(pIndex, pZone);
        contextMenu.show(pMouseEvent.getComponent(), pMouseEvent.getX(), pMouseEvent.getY());
    }

    int getContextMenuItems(int pIndex, Zone pZone) {
        contextMenuKarte = pIndex;
        contextMenuZone = pZone;
        int count = 0;
        Karte karte = new Karte(0);
        //Titel
        if (contextMenuZone == partie.getIch().getHand()) {
            karte = contextMenuZone.get(contextMenuKarte - 1);

        } else if (contextMenuZone == partie.getIch().getSpielzone()) {
            karte = contextMenuZone.get(contextMenuKarte - 100);
        } else if (contextMenuZone == partie.getDu().getSpielzone()) {
            karte = contextMenuZone.get(contextMenuKarte - 200);
        }
        if (!partie.isGameEnd()) {
            //SPIELEN
            if (partie.ichBinAmZug()
                    && partie.getSonderfunktion() == 0
                    && contextMenuZone == partie.getIch().getHand()
                    && partie.karteSpielenErlaubt(contextMenuZone.get(contextMenuKarte - 1))
            ) {
                contextMenu.add(contextMenuItemSpielen);
                count++;
            }
            //ERSETZEN
            if (partie.ichBinAmZug()
                    && partie.getSonderfunktion() == 0
                    && contextMenuZone == partie.getIch().getHand()
                    && (
                    (karte.hatSymbol(KartenSymbol.ERSETZEN, partie.getDu())
                            || partie.getIch().hatAktiv(298)) //Argusauge
                            && (partie.getPhase() == ZugPhase.BEGINN
                            || partie.getPhase() == ZugPhase.AKTION)
                            && (partie.getAktionenGespielt() < 1 || (partie.getIch().hatAktiv(14) && partie.getAktionenGespielt() < 2))
            )
                    && !partie.getFlutEffekt()
            ) {
                contextMenu.add(contextMenuItemErsetzen);
                count++;
            }
            //AUFNEHMEN
            if (
                    partie.ichBinAmZug()
                            && partie.getSonderfunktion() == 0
                            && contextMenuZone == partie.getIch().getSpielzone()
                            && partie.getPhase() == ZugPhase.BEGINN
                            && karte.istAktiv()
                            && karte.hatSymbol(KartenSymbol.AUFNEHMEN, partie.getDu())
                            && (
                            karte.getTyp() != KartenTyp.CHARAKTER
                                    || !partie.getDu().hatAufnehmCharakter()
                    )
                            && !partie.getFlutEffekt()
            ) {
                contextMenu.add(contextMenuItemAufnehm);
                count++;
            }
            //EIGENE SF IN SPIELZONE AKTIVIEREN
            if (
                    partie.ichBinAmZug()
                            && partie.getSonderfunktion() == 0
                            && contextMenuZone == partie.getIch().getSpielzone()
                            && karte.istAktiv()
                            && !karte.sfIgnoriert(partie.getDu())
                            && !karte.isSfVerbraucht()
                            && !partie.getFlutEffekt()
                            && (karte.getNummer() == 136 || karte.getNummer() == 137 || karte.getNummer() == 146)
            ) {
                contextMenu.add(contextMenuItemAktivieren);
                count++;
            }
            //GEGNERISCHE SF IN SPIELZONE AKTIVIEREN
            if (
                    partie.ichBinAmZug()
                            && partie.getSonderfunktion() == 0
                            && contextMenuZone == partie.getDu().getSpielzone()
                            && karte.istAktiv()
                            && !karte.sfIgnoriert(partie.getDu())
                            && !karte.isSfVerbraucht()
                            && !partie.getFlutEffekt()
                            && (
                            karte.getNummer() == 198
                                    || karte.getNummer() == 199
                                    || karte.getNummer() == 200
                                    || karte.getNummer() == 201
                                    || karte.getNummer() == 202
                                    || karte.getNummer() == 240
                    )
            ) {
                contextMenu.add(contextMenuItemBefriedigen);
                count++;
            }
        }

        if (count == 0)
            contextMenu.add(contextMenuItemNichts);
        return count;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == buttonOK) {
            doAction(0);
        } else if (ae.getSource() == buttonCancel) {
            doAction(-1);
        } else if (ae.getSource() == buttonFeuer) {
            doAction(-2);
        } else if (ae.getSource() == buttonErde) {
            doAction(-3);
        } else if (ae.getSource() == contextMenuItemSpielen) {
            if (partie.ichBinAmZug()
                    && contextMenuKarte >= 1 && contextMenuKarte <= partie.getIch().getHandSize()
                    && partie.karteSpielenErlaubt(partie.getIch().getHandKarte(contextMenuKarte - 1))
            ) {
                doAction(contextMenuKarte);
            }
        } else if (ae.getSource() == contextMenuItemErsetzen) {
            if (contextMenuKarte >= 1 && contextMenuKarte <= partie.getIch().getHandSize()
                    && (
                    (contextMenuZone.get(contextMenuKarte - 1).hatSymbol(KartenSymbol.ERSETZEN, partie.getDu())
                            || partie.getIch().hatAktiv(298)) //Argusauge
                            && (partie.getPhase() == ZugPhase.BEGINN
                            || partie.getPhase() == ZugPhase.AKTION)
                            && (partie.getAktionenGespielt() < 1 || (partie.getIch().hatAktiv(14) && partie.getAktionenGespielt() < 2))
            )
            ) {
                partie.setSonderfunktion(-5);
                partie.setSfZaehler(contextMenuKarte - 1);
                doAction(-1);
                partie.gottaSendTrue();
            }
        } else if (ae.getSource() == contextMenuItemBluff) {
        } else if (ae.getSource() == contextMenuItemSchiffDrauf) {
        } else if (ae.getSource() == contextMenuItemSchiffRunter) {
        } else if (ae.getSource() == contextMenuItemAufnehm) {
            klickeAn(contextMenuKarte);
        } else if (ae.getSource() == contextMenuItemAktivieren) {
            klickeAn(contextMenuKarte);
        } else if (ae.getSource() == contextMenuItemBefriedigen) {
            klickeAn(contextMenuKarte);
        } else if (ae.getSource() == buttonHoverGroesser) {
            imageSizeHover += 1;
            if (imageSizeHover > 8)
                imageSizeHover = 8;
            else
                redrawKartenDetails(hoverKarte);
            checkSaveButton();
        } else if (ae.getSource() == buttonHoverKleiner) {

            imageSizeHover -= 1;
            if (imageSizeHover < 0)
                imageSizeHover = 0;
            else
                redrawKartenDetails(hoverKarte);
            checkSaveButton();
        } else if (ae.getSource() == buttonDuSpielzGroesser) {
            imageSizeDuSpiel += 1;
            if (imageSizeDuSpiel > 8)
                imageSizeDuSpiel = 8;
            else
                redrawDuSpielzone();
            checkSaveButton();
        } else if (ae.getSource() == buttonDuSpielzKleiner) {
            imageSizeDuSpiel -= 1;
            if (imageSizeDuSpiel < 0)
                imageSizeDuSpiel = 0;
            else
                redrawDuSpielzone();
            checkSaveButton();
        } else if (ae.getSource() == buttonIchSpielzGroesser) {
            imageSizeIchSpiel += 1;
            if (imageSizeIchSpiel > 8)
                imageSizeIchSpiel = 8;
            else
                redrawIchSpielzone();
            checkSaveButton();
        } else if (ae.getSource() == buttonIchSpielzKleiner) {
            imageSizeIchSpiel -= 1;
            if (imageSizeIchSpiel < 0)
                imageSizeIchSpiel = 0;
            else
                redrawIchSpielzone();
            checkSaveButton();
        } else if (ae.getSource() == buttonHandGroesser) {
            imageSizeIchHand += 1;
            if (imageSizeIchHand > 8)
                imageSizeIchHand = 8;
            else
                redrawIchHand();
            checkSaveButton();
        } else if (ae.getSource() == buttonHandKleiner) {
            imageSizeIchHand -= 1;
            if (imageSizeIchHand < 0)
                imageSizeIchHand = 0;
            else
                redrawIchHand();
            checkSaveButton();
        } else if (ae.getSource() == buttonDialogsGroesser) {
            imageSizeDialogs += 1;
            if (imageSizeDialogs > 8)
                imageSizeDialogs = 8;
            else {
                if (dialogIchNachzieh.isVisible())
                    redrawIchNachziehstapel();
                //if (dialogDuNachzieh.isVisible())
                //  redrawDuNachziehstapel();
                if (dialogIchAbwurf.isVisible())
                    redrawIchAbwurfzone();
                if (dialogDuAbwurf.isVisible())
                    redrawDuAbwurfzone();
                if (dialogDuHand.isVisible())
                    redrawDuHand();
                if (dialogNextSF.isVisible())
                    redrawNextSFPanel();
            }
            checkSaveButton();
        } else if (ae.getSource() == buttonDialogsKleiner) {
            imageSizeDialogs -= 1;
            if (imageSizeDialogs < 0)
                imageSizeDialogs = 0;
            else {
                if (dialogIchNachzieh.isVisible())
                    redrawIchNachziehstapel();
                //if (dialogDuNachzieh.isVisible())
                //  redrawDuNachziehstapel();
                if (dialogIchAbwurf.isVisible())
                    redrawIchAbwurfzone();
                if (dialogDuAbwurf.isVisible())
                    redrawDuAbwurfzone();
                if (dialogDuHand.isVisible())
                    redrawDuHand();
                if (dialogNextSF.isVisible())
                    redrawNextSFPanel();
            }
            checkSaveButton();
        } else if (ae.getSource()==buttonOptionenSpeichern){
            writeConfig();
            buttonOptionenSpeichern.setEnabled(false);
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

    private void checkSaveButton(){
        buttonOptionenSpeichern.setEnabled(
                configCSHover!=imageSizeHover
                || configCSDuSpiel!=imageSizeDuSpiel
                || configCSIchSpiel!=imageSizeIchSpiel
                || configCSIchHand!=imageSizeIchHand
                || configCSExtra!=imageSizeDialogs
        );
        labelISH.setText(Integer.toString(imageSizeHover+1));
        labelISDS.setText(Integer.toString(imageSizeDuSpiel+1));
        labelISIS.setText(Integer.toString(imageSizeIchSpiel+1));
        labelISIH.setText(Integer.toString(imageSizeIchHand+1));
        labelISE.setText(Integer.toString(imageSizeDialogs+1));
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
                writer.write("name="+configIchName+";\n");
                writer.write("ip="+configIp+";\n");
                writer.write("port="+configPort+";\n");
                writer.write("host="+configHost+";\n");
                writer.write("deck="+configDeck+";\n");
                writer.write("cardSizeHover=" + imageSizeHover + ";\n");
                writer.write("cardSizeDeineSpielzone=" + imageSizeDuSpiel + ";\n");
                writer.write("cardSizeMeineSpielzone=" + imageSizeIchSpiel + ";\n");
                writer.write("cardSizeMeineHand=" + imageSizeIchHand + ";\n");
                writer.write("cardSizeExtra=" + imageSizeDialogs + ";\n");
                configCSHover=imageSizeHover;
                configCSDuSpiel=imageSizeDuSpiel;
                configCSIchSpiel=imageSizeIchSpiel;
                configCSIchHand=imageSizeIchHand;
                configCSExtra=imageSizeDialogs;
                writer.close();
            }catch(Exception ignored){}
        }
    }

    void openDuHand() {
        if (dialogDuHand.isVisible()) {
            dialogDuHand.dispose();
        } else {
            dialogDuHand = new JDialog(this, "Gegnerische Hand");
            dialogDuHand.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    dialogDuHand.dispose();
                }
            });
            dialogDuHand.add(new JScrollPane(panelDuHand));
            int width = redrawDuHand() + 50; // + kartenhöhe * X todo
            int height = 350; //kartenhöhe + X tood
            dialogDuHand.setSize(width, height);
            dialogDuHand.setVisible(true);
        }
    }

    void openIchAbwurf() {
        if (dialogIchAbwurf.isVisible()) {
            dialogIchAbwurf.dispose();
        } else {
            dialogIchAbwurf = new JDialog(this, "Eigene Abwurfzone");
            dialogIchAbwurf.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    dialogIchAbwurf.dispose();
                }
            });
            dialogIchAbwurf.add(new JScrollPane(panelIchAbwurf));
            int width = redrawIchAbwurfzone() + 50; // + kartenhöhe * X todo
            int height = 350; //todo
            dialogIchAbwurf.setSize(width, height);
            dialogIchAbwurf.setVisible(true);
        }
    }

    void openDuAbwurf() {
        if (dialogDuAbwurf.isVisible()) {
            dialogDuAbwurf.dispose();
        } else {
            dialogDuAbwurf = new JDialog(this, "Gegnerische Abwurfzone");
            dialogDuAbwurf.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    dialogDuAbwurf.dispose();
                }
            });
            dialogDuAbwurf.add(new JScrollPane(panelDuAbwurf));
            int width = redrawDuAbwurfzone() + 50; //todo
            int height = 350; //todo
            dialogDuAbwurf.setSize(width, height);
            dialogDuAbwurf.setVisible(true);
        }
    }

    void openIchNachzieh() {
        if (dialogIchNachzieh.isVisible()) {
            dialogIchNachzieh.dispose();
        } else {
            dialogIchNachzieh = new JDialog(this, "Eigener Nachziehstapel");
            dialogIchNachzieh.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    dialogIchNachzieh.dispose();
                }
            });
            dialogIchNachzieh.add(new JScrollPane(panelIchNachzieh));
            int width = redrawIchNachziehstapel() + 50; //todo
            int height = 350; //todo
            dialogIchNachzieh.setSize(width, height);
            dialogIchNachzieh.setVisible(true);
        }
    }

    void openDuNachzieh() {
    } //todo


    //Spiellogikwert intialisieren
    private void initializeGameStatus() {
        if (iAmHost) {
            gameStatus = 1;
        } else {
            gameStatus = 4;
        }
        startGameUpdater();
    }

    private void startGameUpdater() {
        gameUpdater gu = new gameUpdater();
        Thread gameLogicThread = new Thread(gu);
        gameLogicThread.start();
    }

    public class gameUpdater extends Thread {
        public void run() {
            while (running) {
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
                    ArrayList<Karte> gegnerDeck = (ArrayList<Karte>) receiveObject();
                    int gegnerAnf = (int) receiveObject();
                    int gegnerGot = (int) receiveObject();
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

    private void gottaOpenCheck(){
        int val = partie.gottaOpen();
        Zone zone;
        if (
              val!=-1
              && (
                (val<100 && partie.ichBinAmZug())
                ||(val>=100 && !partie.ichBinAmZug())
              )
        ) {
            if(val>=100)
                val-=100;
            switch (val) {
                case 0: //Eigener Nachziehstapel
                    if (!dialogIchNachzieh.isVisible())
                        openIchNachzieh();
                    break;
                case 1: //Eigene Abwurfzone
                    if (!dialogIchAbwurf.isVisible())
                        openIchAbwurf();
            }
        }
    }

    private String getFolderString(int size){
        if (size <= 2)
            return "/images200/";
        else if (size <= 5)
            return "/images350/";
        else
            return "/images500/";
    }

    private void gottaShowCheck(){
        int val = partie.gottaShow();
        if(
                (val==0 && partie.ichBinAmZug())
                || (val==1 && !partie.ichBinAmZug())
                || val==2
        ){
            ArrayList<Integer> arr = partie.gottaShowKarten();
            int xOffset = 0, yOffset = 0;
            int x = 0, y = 0;
            JLayeredPane newPane = new JLayeredPane();
            String imageFolder = getFolderString(imageSizeDialogs);
            for (int i = 0; i < arr.size(); i++) {
                Karte karte = new Karte(arr.get(i));
                ImageIcon kartenIcon = new ImageIcon(getClass().getResource(imageFolder + karte.getFileName()));
                kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageSizeX[imageSizeDialogs], imageSizeY[imageSizeDialogs], Image.SCALE_SMOOTH));
                JLabel kartenLabel = new JLabel();
                kartenLabel.setIcon(kartenIcon);
                int hoverInt = arr.get(i);
                kartenLabel.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent evt) {
                        redrawKartenDetails(new Karte(hoverInt));
                    }

                    public void mousePressed(MouseEvent e) {
                        redrawKartenDetails(new Karte(hoverInt));
                    }
                });
                kartenLabel.setBounds(xOffset, yOffset, kartenIcon.getIconWidth(), kartenIcon.getIconHeight());
                xOffset += 75; //todo
                newPane.add(kartenLabel, i, i);
                if (xOffset + kartenIcon.getIconWidth() > x)
                    x = xOffset + kartenIcon.getIconWidth();
                if (yOffset + kartenIcon.getIconHeight() > y)
                    y = yOffset + kartenIcon.getIconHeight();
            }
            newPane.setPreferredSize(new Dimension(x, y));
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(x, y));
            panel.add(newPane);

            JDialog jd = new JDialog(this, partie.gottaShowTitel());
            jd.add(new JScrollPane(panel));
            jd.setSize(x + 50, 350); //todo
            jd.setVisible(true);
        } else {
            partie.gottaShowTitel();
            partie.gottaShowKarten();
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
                            gottaShowCheck();
                            gottaOpenCheck();
                            partie.gottaOpenNextSF();
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
                    gottaShowCheck();
                    gottaOpenCheck();
                    partie.gottaOpenNextSF();
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

        if (dialogIchNachzieh.isVisible())
            redrawIchNachziehstapel();
        //if (dialogDuNachzieh.isVisible())
        //  redrawDuNachziehstapel();
        if (dialogIchAbwurf.isVisible())
            redrawIchAbwurfzone();
        if (dialogDuAbwurf.isVisible())
            redrawDuAbwurfzone();
        if (dialogDuHand.isVisible())
            redrawDuHand();
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
            panelIchInfos.setBackground(new java.awt.Color(204, 255, 255));
            panelDuInfos.setBackground(new java.awt.Color(204, 204, 204));
        } else {
            panelIchInfos.setBackground(new java.awt.Color(204, 204, 204));
            panelDuInfos.setBackground(new java.awt.Color(204, 255, 255));
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
        } else if (sonderfunktion==-6){ //Nächste sf wählen
            msg = "Welche Sonderfunktion als nächstes aktivieren?";
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
        //infoMsg.setText("<html>Message:<br>" + msg + "</html>");
        infoMsg.setText(msg);

    }
    private int redrawIchNachziehstapel(){
        JLayeredPane newPane = new JLayeredPane();
        int x=0, y=0;
        int xOffset=0, yOffset=0;
        String imageFolder = getFolderString(imageSizeDialogs);
        for(int i=0; i<partie.getIch().getNachziehSize(); i++){
            ImageIcon kartenIcon;
            kartenIcon = new ImageIcon(getClass().getResource(imageFolder + "000.png"));
            kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageSizeX[imageSizeDialogs], imageSizeY[imageSizeDialogs], Image.SCALE_SMOOTH));
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
                kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageSizeX[imageSizeDialogs], imageSizeY[imageSizeDialogs], Image.SCALE_SMOOTH));
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
        return x;
    }
    private int redrawDuHand(){
        JLayeredPane newPane = new JLayeredPane();
        int x=0, y=0;
        int xOffset=0;
        int yOffset=0;
        String imageFolder = getFolderString(imageSizeDialogs);
        for (int i=0; i<partie.getDu().getHandSize(); i++){
            ImageIcon kartenIcon;
            JLabel kartenLabel = new JLabel();
            if (partie.getDu().getHandKarte(i).isAufgedeckt()){
                kartenIcon = new ImageIcon(getClass().getResource(imageFolder + partie.getDu().getHandKarte(i).getFileName()));
                kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageSizeX[imageSizeDialogs], imageSizeY[imageSizeDialogs], Image.SCALE_SMOOTH));
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
            } else {
                kartenIcon = new ImageIcon(getClass().getResource(imageFolder + "000.png"));
                kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageSizeX[imageSizeDialogs], imageSizeY[imageSizeDialogs], Image.SCALE_SMOOTH));
                kartenLabel.setIcon(kartenIcon);
                kartenLabel.setEnabled(!partie.getDu().getHandKarte(i).getSelected());
                //Füge Hoverevent hinzu
                int hoverInt = i;
                //Füge Clickevent hinzu
                int aktionInt = i+3000;
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
        return x;
    }
    private int redrawDuAbwurfzone(){
        JLayeredPane newPane = new JLayeredPane();
        int x=0, y=0;
        int xOffset=0;
        int yOffset=0;
        String imageFolder = getFolderString(imageSizeDialogs);
        for (int i=0; i<partie.getDu().getAbwurfzone().getSize(); i++){
            ImageIcon kartenIcon = new ImageIcon(getClass().getResource(imageFolder + partie.getDu().getAbwurfzone().get(i).getFileName()));
            //ImageIcon kartenIcon = new javax.swing.ImageIcon(getClass().getResource("/images200/009.jpg"));
            kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageSizeX[imageSizeDialogs], imageSizeY[imageSizeDialogs], Image.SCALE_SMOOTH));
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
        return x;
    }
    private int redrawIchAbwurfzone(){
        JLayeredPane newPane = new JLayeredPane();
        int x=0, y=0;
        int xOffset=0;
        int yOffset=0;
        String imageFolder = getFolderString(imageSizeDialogs);
        for (int i=0; i<partie.getIch().getAbwurfzone().getSize(); i++){
            ImageIcon kartenIcon = new ImageIcon(getClass().getResource(imageFolder + partie.getIch().getAbwurfzone().get(i).getFileName()));
            //ImageIcon kartenIcon = new javax.swing.ImageIcon(getClass().getResource("/images200/009.jpg"));
            kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageSizeX[imageSizeDialogs], imageSizeY[imageSizeDialogs], Image.SCALE_SMOOTH));
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
        return x;
    }
    private  void redrawDuSpielzone(){
        JLayeredPane newPane = new JLayeredPane();
        int x=0, y=0;
        int xOffset = 0;
        int yOffset = 0;
        String imageFolder = getFolderString(imageSizeDuSpiel);
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
                    kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageSizeX[imageSizeDuSpiel], imageSizeY[imageSizeDuSpiel], Image.SCALE_SMOOTH));
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
        String imageFolder = getFolderString(imageSizeIchSpiel);
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
                    kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageSizeX[imageSizeIchSpiel], imageSizeY[imageSizeIchSpiel], Image.SCALE_SMOOTH));
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
        hoverKarte = pKarte;
        if(!contextMenu.isShowing()){
            String imageFolder = getFolderString(imageSizeHover);
            ImageIcon kartenIcon = new ImageIcon(getClass().getResource(imageFolder + pKarte.getFileName()));
            kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageSizeX[imageSizeHover], imageSizeY[imageSizeHover], Image.SCALE_SMOOTH));
            labelBigCard.setIcon(kartenIcon);
        }
    }
    private void redrawIchHand(){
        JPanel newPanel = new JPanel();
        String imageFolder = getFolderString(imageSizeIchHand);
        for(int i=0; i<partie.getIch().getHandSize(); i++){
            Karte karte = partie.getIch().getHand().get(i);
            ImageIcon kartenIcon = new ImageIcon(getClass().getResource(imageFolder + karte.getFileName()));
            kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageSizeX[imageSizeIchHand], imageSizeY[imageSizeIchHand], Image.SCALE_SMOOTH));

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
    private int redrawNextSFPanel(){
        JLayeredPane newPane = new JLayeredPane();
        int x=0, y=0;
        int xOffset=0;
        int yOffset=0;
        String imageFolder = getFolderString(imageSizeDialogs);
        for (int i=0; i<partie.getNextSFs().size(); i++){
            Karte karte = new Karte(partie.getNextSFs().get(i));
            ImageIcon kartenIcon = new ImageIcon(getClass().getResource(imageFolder + karte.getFileName()));
            kartenIcon = new ImageIcon(kartenIcon.getImage().getScaledInstance(imageSizeX[imageSizeDialogs], imageSizeY[imageSizeDialogs], Image.SCALE_SMOOTH));
            JLabel kartenLabel = new JLabel();
            kartenLabel.setIcon(kartenIcon);

            //Füge Hoverevent hinzu
            int hoverInt = karte.getNummer();
            //Füge Clickevent hinzu
            int aktionInt = i+6000;
            kartenLabel.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    redrawKartenDetails(new Karte(hoverInt));
                }
                public void mousePressed(MouseEvent e){
                    redrawKartenDetails(new Karte (hoverInt));
                    klickeAn(aktionInt);
                    dialogNextSF.dispose();
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
        panelNextSF.setPreferredSize(new Dimension(x, y));
        panelNextSF.removeAll();
        panelNextSF.add(newPane);
        panelNextSF.revalidate();
        panelNextSF.repaint();
        return x;
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