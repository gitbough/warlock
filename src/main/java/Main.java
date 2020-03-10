import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static String name=null;
    private static String ip=null;
    private static String port=null;
    private static int host=-1;
    private static String deck=null;
    //private static String picture=null;
    private static int cardSizeHover=-1;
    private static int cardSizeDeineSpielzone=-1;
    private static int cardSizeMeineSpielzone=-1;
    private static int cardSizeMeineHand=-1;
    private static int cardSizeExtra=-1;

    public static void main(String[] args){
        checkConfig();
        try{
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
        }
        catch(Exception ignored){}
        //new ConnectGUI(name,ip,port,host,deck,picture);
        new ConnectGUI(
                name,
                ip,
                port,
                host,
                deck,
                cardSizeHover,
                cardSizeDeineSpielzone,
                cardSizeMeineSpielzone,
                cardSizeMeineHand,
                cardSizeExtra
        );
    }

    public static boolean checkDeck(ArrayList<Karte> pDeck, int pAnfuehrer, int pGott){
        String fraktion="";
        switch(pAnfuehrer){
            case 0:
                fraktion="Vedalken";
                break;
            case 1:
                fraktion="Sterbliche";
                break;
            case 2:
                fraktion="Elfen";
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
        int kartenlimit=0, goldlimit=0;
        switch(pGott){
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
        int karten=0, gold=0;
        for(int i=0; i<pDeck.size(); i++){
            karten += 1;
            if(karten>kartenlimit){return false;}
            Karte k = pDeck.get(i);
            if (k.getName().equals(""))
                return false;
            if(!fraktion.equals(k.getFraktion())) {
                gold += k.getGold();
                if(gold>goldlimit){return false;}
            }
            for(int j=0; j<i; j++){
                if(k.getNummer() == pDeck.get(j).getNummer()){
                    return false;
                }
            }
        }
        if(karten==kartenlimit && gold<=goldlimit) {
            return true;
        } else {
            return false;
        }
    }

    private static void checkConfig(){
        boolean fileExists;
        String dir = System.getProperty("user.dir") + "\\warlock_userdata\\warlock.config";
        File file = new File(dir);
        if(file.exists()){
            fileExists=true;
        } else {
            try{
                if(file.createNewFile()){
                    FileWriter writer = new FileWriter(dir);
                    writer.write("name=Name;\n");
                    writer.write("ip=localhost;\n");
                    writer.write("port=5555;\n");
                    writer.write("host=1;\n");
                    writer.write("deck=;\n");
                    //writer.write("picture=;\n");
                    writer.write("cardSizeHover=3;\n");
                    writer.write("cardSizeDeineSpielzone=2;\n");
                    writer.write("cardSizeMeineSpielzone=2;\n");
                    writer.write("cardSizeMeineHand=2;\n");
                    writer.write("cardSizeExtra=1;\n");
                    writer.close();
                    fileExists=true;
                } else {
                    fileExists=false;
                }
            }catch(Exception ignored){
                fileExists=false;
            }
        }
        if (fileExists){
            readConfig(dir, file);
        } else {
            setNameIpPort();
        }
    }
    private static void readConfig(String pDir, File pFile){
        if(pFile.exists()){
            try {
                Scanner reader = new Scanner(pFile);
                while (reader.hasNextLine()) {
                    String str = reader.nextLine();
                    boolean correctEndOfString = str.substring(str.length() - 1).equals(";");
                    if (str.length() > 5 && str.substring(0, 5).equals("name=") && correctEndOfString) {
                        name = str.substring(5, str.length() - 1);
                    } else if (str.length() > 3 && str.substring(0, 3).equals("ip=") && correctEndOfString) {
                        ip = str.substring(3, str.length() - 1);
                    } else if (str.length() > 5 && str.substring(0, 5).equals("port=") && correctEndOfString) {
                        port = str.substring(5, str.length() - 1);
                    } else if (str.length()>5 && str.substring(0,5).equals("host=") && correctEndOfString){
                        host = Integer.parseInt(str.substring(5, str.length() - 1));
                    } else if (str.length()>5 && str.substring(0,5).equals("deck=") && correctEndOfString){
                        deck = str.substring(5, str.length() - 1);
                    //} else if (str.length()>5 && str.substring(0,8).equals("picture=") && correctEndOfString){
                    //    picture = str.substring(8, str.length() - 1);
                    } else if (str.length()>14 && str.substring(0,14).equals("cardSizeHover=") && correctEndOfString){
                        cardSizeHover = Integer.parseInt(str.substring(14, str.length() - 1));
                    } else if (str.length()>23 && str.substring(0,23).equals("cardSizeDeineSpielzone=") && correctEndOfString){
                        cardSizeDeineSpielzone = Integer.parseInt(str.substring(23, str.length() - 1));
                    } else if (str.length()>23 && str.substring(0,23).equals("cardSizeMeineSpielzone=") && correctEndOfString){
                        cardSizeMeineSpielzone = Integer.parseInt(str.substring(23, str.length() - 1));
                    } else if (str.length()>18 && str.substring(0,18).equals("cardSizeMeineHand=") && correctEndOfString){
                        cardSizeMeineHand = Integer.parseInt(str.substring(18, str.length() - 1));
                    } else if (str.length()>14 && str.substring(0,14).equals("cardSizeExtra=") && correctEndOfString){
                        cardSizeExtra = Integer.parseInt(str.substring(14, str.length() - 1));
                    }

                }
                reader.close();
            } catch (Exception ignored){}
            writeConfig(pDir, pFile);
        } else {
            setNameIpPort();
        }
    }
    private static void writeConfig(String pDir, File pFile) {
        setNameIpPort();
        if (pFile.exists()) {
            try {
                FileWriter writer = new FileWriter(pDir);
                writer.write("name=" + name + ";\n");
                writer.write("ip=" + ip + ";\n");
                writer.write("port=" + port + ";\n");
                writer.write("host=" + host + ";\n");
                writer.write("deck=" + deck + ";\n");
                //writer.write("picture=" + picture + ";\n");
                writer.write("cardSizeHover=" + cardSizeHover + ";\n");
                writer.write("cardSizeDeineSpielzone=" + cardSizeDeineSpielzone + ";\n");
                writer.write("cardSizeMeineSpielzone=" + cardSizeMeineSpielzone + ";\n");
                writer.write("cardSizeMeineHand=" + cardSizeMeineHand + ";\n");
                writer.write("cardSizeExtra=" + cardSizeExtra + ";\n");
                writer.close();
            } catch (Exception ignored){}
        }
    }
    private static void setNameIpPort(){
        if(name==null)
            name = "Name";
        if(ip==null)
            ip = "localhost";
        if(port==null)
            port = "5555";
        if(host==-1)
            host = 1;
        if (deck==null)
            deck = "";
        //if (picture==null)
        //    picture = "";
        if(cardSizeHover==-1)
            cardSizeHover = 3;
        if(cardSizeDeineSpielzone==-1)
            cardSizeDeineSpielzone = 2;
        if(cardSizeMeineSpielzone==-1)
            cardSizeMeineSpielzone = 2;
        if(cardSizeMeineHand==-1)
            cardSizeMeineHand = 2;
        if(cardSizeExtra==-1)
            cardSizeExtra = 1;
    }
}
