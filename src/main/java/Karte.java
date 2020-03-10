
import java.io.Serializable;
import java.util.ArrayList;

class Karte implements Serializable {

    //ATTRIBUTE
    private int nr;
    private String name;
    private KartenTyp typ;
    private KartenSubtyp subtyp = KartenSubtyp.KEINER;
    private String fraktion;
    private int feuer;
    private int erde;
    private boolean sf;
    private boolean selected;
    private ArrayList<KartenSymbol> symbole = new ArrayList<>();
    //String sf;
    private  boolean aktiv = false;
    private int gold;
    private boolean sfVerbraucht=false;
    private boolean aufgedeckt=false;


    //KONSTRUKTOR
    Karte(int pNummer){
        nr = pNummer;
        sf = false;
        selected = false;
        switch(pNummer){
            case 0:
                break;

            case 1:
            case 31:
            case 61:
            case 91:
            case 121:
            case 151:
            case 181:
            case 211:
            case 241:
                typ = KartenTyp.ANFUEHRER;
                break;
            case 297:
            case 298:
            case 300:
                typ = KartenTyp.GOTT;
                sf = true;
                break;
            case 299:
                typ = KartenTyp.GOTT;
                sf = false;
                break;

            case 2:
                name = "Bezaubere den Heiligen Drachen";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Vedalken";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 4;
                break;
            case 3:
                name = "Vernichte den Feind";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Vedalken";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 4:
                name = "Rufe den Meister";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Vedalken";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 5:
                name = "Assimiliere die Bestie";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Vedalken";
                feuer = 0;
                erde = 0;
                sf = true; //TODO: sonderfunktion hinzufügen (dass man bestien trotz ihrer sf spielen darf)
                gold = 1;
                break;
            case 6:
                name = "Erwecke das Wissen";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Vedalken";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 0;
                break;
            case 7:
                name = "Blendender Hochmagus";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Vedalken";
                feuer = 7;
                erde = 0;
                gold = 3;
                break;
            case 8:
                name = "Grausamer Erzmagier";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Vedalken";
                feuer = 6;
                erde = 3;
                gold = 2;
                break;
            case 9:
                name = "Hasserfüllter Erzmagier";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Vedalken";
                feuer = 6;
                erde = 0;
                gold = 2;
                break;
            case 10:
                name = "Mächtiger Großmagister";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Vedalken";
                feuer = 5;
                erde = 4;
                gold = 2;
                break;
            case 11:
                name = "Weiser Großmagister";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Vedalken";
                feuer = 5;
                erde = 1;
                gold = 1;
                break;
            case 12:
                name = "Weitreisender Adept";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Vedalken";
                feuer = 4;
                erde = 3;
                gold = 1;
                break;
            case 13:
                name = "Aussiedelnder Adept";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Vedalken";
                feuer = 3;
                erde = 4;
                gold = 1;
                break;
            case 14:
                name = "Zwillingszauber-Novize";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Vedalken";
                feuer = 3;
                erde = 3;
                sf = true;
                gold = 1;
                break;
            case 15:
                name = "Pulverisierer-Novize";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Vedalken";
                feuer = 2;
                erde = 2;
                sf = true;
                gold = 1;
                break;
            case 16:
                name = "Schleichschritt-Novize";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Vedalken";
                feuer = 2;
                erde = 2;
                sf = true;
                gold = 1;
                break;
            case 17:
                name = "Bannzauber-Magus";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Vedalken";
                feuer = 1;
                erde = 1;
                sf = true;
                gold = 1;
                break;
            case 18:
                name = "Nichtigmacher-Magus";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Vedalken";
                feuer = 1;
                erde = 1;
                sf = true;
                gold = 3;
                break;
            case 19:
                name = "Feuerstoß-Kartusche";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Vedalken";
                feuer = 3;
                erde = 2;
                gold = 1;
                break;
            case 20:
                name = "Schützendes Kraftfeld";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Vedalken";
                feuer = 0;
                erde = 0;
                symbole.add(KartenSymbol.SCHILD_ERDE);
                gold = 1;
                break;
            case 21:
                name = "Urgewaltiger Zauber";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Vedalken";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 22:
                name = "Vulkanblitz-Erzeuger";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Vedalken";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 23:
                name = "Titanblitz-Erzeuger";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Vedalken";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 24:
                name = "Talisman der Verstummung";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Vedalken";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 3;
                break;
            case 25:
                name = "Blitzturm";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Vedalken";
                feuer = 3;
                erde = 1;
                gold = 2;
                break;
            case 26:
                name = "Turm der Gesetze";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Vedalken";
                feuer = 1;
                erde = 1;
                sf = true;
                gold = 1;
                break;
            case 27:
                name = "Eifrige Produktion";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Vedalken";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 0;
                break;
            case 28:
                name = "Operative Einschränkung";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Vedalken";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 29:
                name = "Explosive Sabotage";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Vedalken";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 3;
                break;
            case 30:
                name = "Geister-Resonanz";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Vedalken";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 3;
                break;
            case 32:
                name = "Erzürne den Heiligen Drachen";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Sterbliche";
                feuer = 0;
                erde = 0;
                sf = true; //todo sf für bleibende aktionen ergänzen
                gold = 4;
                break;
            case 33:
                name = "Sende Verstärkung";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Sterbliche";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 4;
                break;
            case 34:
                name = "Banne den Gegner";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Sterbliche";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 1;
                break;
            case 35:
                name = "Vertreibe die Eindringlinge";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Sterbliche";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 1;
                break;
            case 36:
                name = "Entfache die Kriegsmaschinen";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Sterbliche";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 1;
                break;
            case 37:
                name = "Erwecke die Demut";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Sterbliche";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 0;
                break;
            case 38:
                name = "Königlicher Heermeister";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Sterbliche";
                feuer = 3;
                erde = 5;
                gold = 1;
                break;
            case 39:
                name = "Erster Ritter des Heeres";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Sterbliche";
                feuer = 5;
                erde = 1;
                gold = 1;
                break;
            case 40:
                name = "Schildträger des Heeres";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Sterbliche";
                feuer = 5;
                erde = 0;
                gold = 1;
                break;
            case 41:
                name = "Gepanzerte Wache";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Sterbliche";
                feuer = 3;
                erde = 3;
                symbole.add(KartenSymbol.AUFNEHMEN);
                gold = 1;
                break;
            case 42:
                name = "Charismatischer Prediger";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Sterbliche";
                feuer = 2;
                erde = 2;
                sf = true;
                gold = 2;
                break;
            case 43:
                name = "Ernste Ordensschwester";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Sterbliche";
                feuer = 2;
                erde = 2;
                sf = true;
                gold = 2;
                break;
            case 44:
                name = "Flinkfüßige Jägerin";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Sterbliche";
                feuer = 2;
                erde = 2;
                sf = true;
                gold = 1;
                break;
            case 45:
                name = "Strenger Amtmann";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Sterbliche";
                feuer = 2;
                erde = 2;
                sf = true;
                gold = 2;
                break;
            case 46:
                name = "Fleißiger Waffenschmied";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Sterbliche";
                feuer = 2;
                erde = 2;
                sf = true;
                gold = 1;
                break;
            case 47:
                name = "Erfinderischer Tüftler";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Sterbliche";
                feuer = 2;
                erde = 2;
                sf = true;
                gold = 2;
                break;
            case 48:
                name = "Stoischer Holzfäller";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Sterbliche";
                feuer = 2;
                erde = 2;
                sf = true;
                gold = 1;
                break;
            case 49:
                name = "Talentierte Bardin";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Sterbliche";
                feuer = 1;
                erde = 1;
                sf = true;
                gold = 1;
                break;
            case 50:
                name = "Gesegnete Streitaxt";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Sterbliche";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.AUFNEHMEN);
                gold = 2;
                break;
            case 51:
                name = "Segen-Plattenharnisch";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Sterbliche";
                feuer = 0;
                erde = 0;
                symbole.add(KartenSymbol.SCHILD_FEUER);
                gold = 1;
                break;
            case 52:
                name = "Relikt der Segnung";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Sterbliche";
                feuer = 0;
                erde = 0;
                symbole.add(KartenSymbol.FREI);
                sf = true;
                gold = 2;
                break;
            case 53:
                name = "Massiver Belagerungsturm";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Sterbliche";
                feuer = 3;
                erde = 3;
                gold = 3;
                break;
            case 54:
                name = "Donnernde Kriegsmaschine";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Sterbliche";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.FREI);
                symbole.add(KartenSymbol.AUFNEHMEN);
                gold = 4;
                break;
            case 55:
                name = "Lärmendes Katapult";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Sterbliche";
                feuer = 2;
                erde = 1;
                symbole.add(KartenSymbol.AUFNEHMEN);
                gold = 2;
                break;
            case 56:
                name = "Unerbittlicher Rammbock";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Sterbliche";
                feuer = 1;
                erde = 2;
                symbole.add(KartenSymbol.AUFNEHMEN);
                gold = 2;
                break;
            case 57:
                name = "Kraftvolle Schleuder";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Sterbliche";
                feuer = 2;
                erde = 1;
                gold = 1;
                break;
            case 58:
                name = "Verstärkte Ballliste";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Sterbliche";
                feuer = 1;
                erde = 2;
                gold = 1;
                break;
            case 59:
                name = "Kraft spendender Turm";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Sterbliche";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 4;
                break;
            case 60:
                name = "Blutige Guillotine";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Sterbliche";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 4;
                break;
            case 62:
                name = "Rufe den Geist der Ahnen";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Elfen";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 4;
                break;
            case 63:
                name = "Rufe den Geist der Schlacht";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Elfen";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 64:
                name = "Erwecke den Zyklus";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Elfen";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 0;
                break;
            case 65:
                name = "Wächterin der Zeit";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 6;
                erde = 4;
                symbole.add(KartenSymbol.PAAR);
                gold = 3;
                break;
            case 66:
                name = "Wächterin des Raumes";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 4;
                erde = 6;
                symbole.add(KartenSymbol.PAAR);
                gold = 3;
                break;
            case 67:
                name = "Gesandter des Wissens";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 6;
                erde = 1;
                symbole.add(KartenSymbol.PAAR);
                gold = 2;
                break;
            case 68:
                name = "Wächterin des Glaubens";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 1;
                erde = 6;
                symbole.add(KartenSymbol.PAAR);
                gold = 2;
                break;
            case 69: //nice
                name = "Champion der Nachtelfen";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 5;
                erde = 3;
                symbole.add(KartenSymbol.PAAR);
                gold = 2;
                break;
            case 70:
                name = "Champion der Waldelfen";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 3;
                erde = 5;
                symbole.add(KartenSymbol.PAAR);
                gold = 2;
                break;
            case 71:
                name = "Hüter des Jungen";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 5;
                erde = 2;
                symbole.add(KartenSymbol.PAAR);
                gold = 1;
                break;
            case 72:
                name = "Hüter des Uralten";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 2;
                erde = 5;
                symbole.add(KartenSymbol.PAAR);
                gold = 1;
                break;
            case 73:
                name = "Jäger der Baumwipfel";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 4;
                erde = 4;
                symbole.add(KartenSymbol.PAAR);
                gold = 2;
                break;
            case 74:
                name = "Jäger der Waldböden";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 4;
                erde = 4;
                symbole.add(KartenSymbol.PAAR);
                gold = 2;
                break;
            case 75:
                name = "Martyrer der Ehre";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 3;
                erde = 3;
                symbole.add(KartenSymbol.PAAR);
                gold = 0;
                break;
            case 76:
                name = "Martyrer der Freiheit";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 3;
                erde = 3;
                symbole.add(KartenSymbol.PAAR);
                gold = 0;
                break;
            case 77:
                name = "Martyrer der Gerechtigkeit";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 3;
                erde = 3;
                symbole.add(KartenSymbol.SCHILD_FEUER);
                symbole.add(KartenSymbol.SCHILD_ERDE);
                symbole.add(KartenSymbol.PAAR);
                gold = 2;
                break;
            case 78:
                name = "Hexenmeister des Jenseits";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 3;
                erde = 3;
                symbole.add(KartenSymbol.STOPP);
                sf = true;
                gold = 2;
                break;
            case 79:
                name = "Schamane des Jenseits";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 3;
                erde = 3;
                symbole.add(KartenSymbol.STOPP);
                sf = true;
                gold = 2;
                break;
            case 80:
                name = "Priester des Lebens";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 2;
                erde = 2;
                sf = true;
                gold = 1;
                break;
            case 81:
                name = "Druide des Lebens";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 2;
                erde = 2;
                sf = true;
                gold = 1;
                break;
            case 82:
                name = "Sonnenkämpfer";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 2;
                erde = 1;
                symbole.add(KartenSymbol.FREI);
                gold = 2;
                break;
            case 83:
                name = "Mondstreicher";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 1;
                erde = 2;
                symbole.add(KartenSymbol.FREI);
                gold = 2;
                break;
            case 84:
                name = "Nebelpirscher";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Elfen";
                feuer = 1;
                erde = 1;
                symbole.add(KartenSymbol.FREI);
                gold = 1;
                break;
            case 85:
                name = "Blattpfeil";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Elfen";
                feuer = 2;
                erde = 1;
                gold = 0;
                break;
            case 86:
                name = "Sternbogen";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Elfen";
                feuer = 1;
                erde = 2;
                gold = 0;
                break;
            case 87:
                name = "Betäubendes Horn";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Elfen";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 1;
                break;
            case 88:
                name = "Berdohlicher Kriegstanz";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Elfen";
                feuer = 2;
                erde = 2;
                gold = 2;
                break;
            case 89:
                name = "Natürliche Entwaffnung";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Elfen";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 90:
                name = "Brennender Geistertanz";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Elfen";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 3;
                break;
            case 92:
                name = "Täusche den Heiligen Drachen";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Avior";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 4;
                break;
            case 93:
                name = "Ehre die Heiligen Geister";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Avior";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 1;
                break;
            case 94:
                name = "Entsende die Wurfwaffen";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Avior";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 1;
                break;
            case 95:
                name = "Erwecke die Freiheit";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Avior";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 0;
                break;
            case 96:
                name = "Geschwaderführer Säbelfeder";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Avior";
                feuer = 4;
                erde = 4;
                symbole.add(KartenSymbol.AUFNEHMEN);
                gold = 2;
                break;
            case 97:
                name = "Staffelführer Schwinge";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Avior";
                feuer = 4;
                erde = 2;
                symbole.add(KartenSymbol.AUFNEHMEN);
                gold = 1;
                break;
            case 98:
                name = "Rottenführer Kralle";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Avior";
                feuer = 2;
                erde = 4;
                symbole.add(KartenSymbol.AUFNEHMEN);
                gold = 1;
                break;
            case 99:
                name = "Flatter";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Avior";
                feuer = 3;
                erde = 2;
                symbole.add(KartenSymbol.AUFNEHMEN);
                gold = 0;
                break;
            case 100:
                name = "Gleiter";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Avior";
                feuer = 2;
                erde = 3;
                symbole.add(KartenSymbol.AUFNEHMEN);
                gold = 0;
                break;
            case 101:
                name = "Kratzerhexer";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Avior";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.AUFNEHMEN);
                sf = true;
                gold = 1;
                break;
            case 102:
                name = "Plusterhexer";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Avior";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.AUFNEHMEN);
                sf = true;
                gold = 1;
                break;
            case 103:
                name = "Trillerhexer";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Avior";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.AUFNEHMEN);
                sf = true;
                gold = 2;
                break;
            case 104:
                name = "Zirperhexer";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Avior";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.AUFNEHMEN);
                sf = true;
                gold = 1;
                break;
            case 105:
                name = "Zwitscherhexer";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Avior";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.AUFNEHMEN);
                sf = true;
                gold = 1;
                break;
            case 106:
                name = "Geschwaderberserker";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Avior";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 1;
                break;
            case 107:
                name = "Staffelmagi";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Avior";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 108:
                name = "Rottenbrecher";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Avior";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 3;
                break;
            case 109:
                name = "Stolzhaupt Bohrschnabel";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Avior";
                feuer = 0;
                erde = 0;
                symbole.add(KartenSymbol.SCHILD_ERDE);
                gold = 1;
                break;
            case 110:
                name = "Abwurf Gluterde";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Avior";
                feuer = 3;
                erde = 3;
                symbole.add(KartenSymbol.PAAR);
                gold = 2;
                break;
            case 111:
                name = "Abwurf Meteorschauer";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Avior";
                feuer = 3;
                erde = 2;
                symbole.add(KartenSymbol.PAAR);
                gold = 2;
                break;
            case 112:
                name = "Abwurf Felshagel";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Avior";
                feuer = 2;
                erde = 3;
                symbole.add(KartenSymbol.PAAR);
                gold = 2;
                break;
            case 113:
                name = "Abwurf Flammenregen";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Avior";
                feuer = 3;
                erde = 1;
                symbole.add(KartenSymbol.PAAR);
                gold = 2;
                break;
            case 114:
                name = "Abwurf Dornenfall";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Avior";
                feuer = 1;
                erde = 3;
                symbole.add(KartenSymbol.PAAR);
                gold = 2;
                break;
            case 115:
                name = "Abwurf Insekenplage";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Avior";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.PAAR);
                symbole.add(KartenSymbol.AUFNEHMEN);
                gold = 2;
                break;
            case 116:
                name = "Abwurf Feuerblitze";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Avior";
                feuer = 2;
                erde = 1;
                symbole.add(KartenSymbol.PAAR);
                gold = 1;
                break;
            case 117:
                name = "Abwurf Schleudersteine";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Avior";
                feuer = 1;
                erde = 2;
                symbole.add(KartenSymbol.PAAR);
                gold = 1;
                break;
            case 118:
                name = "Abwurf Stinkkäfer";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Avior";
                feuer = 1;
                erde = 1;
                symbole.add(KartenSymbol.PAAR);
                symbole.add(KartenSymbol.AUFNEHMEN);
                gold = 1;
                break;
            case 119:
                name = "Abwurf Scherbenschauer";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Avior";
                feuer = 0;
                erde = 0;
                symbole.add(KartenSymbol.PAAR);
                sf = true;
                gold = 2;
                break;
            case 120:
                name = "Nervtötendes Gezwitscher";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Avior";
                feuer = 1;
                erde = 1;
                sf = true;
                gold = 1;
                break;
            case 122:
                name = "Rekrutiere einen Krieger";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Halblinge";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 4;
                break;
            case 123:
                name = "Ehre die Gefallenen";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Halblinge";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 3;
                break;
            case 124:
                name = "Lass' die Fäuste tanzen";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Halblinge";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 1;
                break;
            case 125:
                name = "Erwecke den Elan";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Halblinge";
                feuer = 0;
                erde = 0;
                sf = true; //todo sf implementieren
                gold = 0;
                break;
            case 126:
                name = "Kleinling Stürmer";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 4;
                erde = 1;
                symbole.add(KartenSymbol.GANG);
                gold = 1;
                break;
            case 127:
                name = "Kleinling Bewahrer";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 1;
                erde = 4;
                symbole.add(KartenSymbol.GANG);
                gold = 1;
                break;
            case 128:
                name = "Kleinling Bannerträger";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 3;
                erde = 3;
                symbole.add(KartenSymbol.GANG);
                gold = 1;
                break;
            case 129:
                name = "Kleinling Plänkler";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 3;
                erde = 2;
                symbole.add(KartenSymbol.GANG);
                gold = 0;
                break;
            case 130:
                name = "Kleinling Soldat";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 2;
                erde = 3;
                symbole.add(KartenSymbol.GANG);
                gold = 0;
                break;
            case 131:
                name = "Edling Luftritt";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 3;
                erde = 2;
                symbole.add(KartenSymbol.GANG);
                gold = 1;
                break;
            case 132:
                name = "Edling Eilflug";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 2;
                erde = 3;
                symbole.add(KartenSymbol.GANG);
                gold = 1;
                break;
            case 133:
                name = "Edling Jähzorn";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 3;
                erde = 1;
                symbole.add(KartenSymbol.GANG);
                gold = 1;
                break;
            case 134:
                name = "Edling Anmut";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 1;
                erde = 3;
                symbole.add(KartenSymbol.GANG);
                gold = 1;
                break;
            case 135:
                name = "Edling Schmackes";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 0;
                erde = 0;
                symbole.add(KartenSymbol.GANG);
                sf = true;
                gold = 3;
                break;
            case 136:
                name = "Meuchling Alpha";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.GANG);
                sf = true;
                gold = 2;
                break;
            case 137:
                name = "Meuchling Bravo";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.GANG);
                sf = true;
                gold = 1;
                break;
            case 138:
                name = "Meuchling Charlie";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.GANG);
                sf = true;
                gold = 1;
                break;
            case 139:
                name = "Meuchling Delta";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.GANG);
                sf = true;
                gold = 1;
                break;
            case 140:
                name = "Meuchling Echo";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.GANG);
                sf = true;
                gold = 1;
                break;
            case 141:
                name = "Hexling Gnom";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.GANG);
                sf = true;
                gold = 2;
                break;
            case 142:
                name = "Hexling Undine";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.GANG);
                sf = true;
                gold = 2;
                break;
            case 143:
                name = "Hexling Salamander";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.GANG);
                sf = true;
                gold = 2;
                break;
            case 144:
                name = "Hexling Sylphe";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.GANG);
                sf = true;
                gold = 2;
                break;
            case 145:
                name = "Ganzling";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Halblinge";
                feuer = 1;
                erde = 1;
                sf = true;
                gold = 4;
                break;
            case 146:
                name = "Improvisierte Schleuder";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Halblinge";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.FREI);
                sf = true;
                gold = 2;
                break;
            case 147:
                name = "Klebriges Pulver";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Halblinge";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 148:
                name = "Erstickender Zauber";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Halblinge";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 3;
                break;
            case 149:
                name = "Geschwätziger Wachposten";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Halblinge";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 150:
                name = "Wacher Alptraum";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Halblinge";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 152:
                name = "Beschenke den Heiligen Drachen";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Orks";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 4;
                break;
            case 153:
                name = "Plündere das Dorf";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Orks";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 4;
                break;
            case 154:
                name = "Erwecke die Raserei";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Orks";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 0;
                break;
            case 155:
                name = "Bergerschütterer";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Orks";
                feuer = 0;
                erde = 7;
                gold = 3;
                break;
            case 156:
                name = "Höhlenkönig";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Orks";
                feuer = 3;
                erde = 6;
                gold = 2;
                break;
            case 157:
                name = "Mannschlächter";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Orks";
                feuer = 0;
                erde = 6;
                gold = 2;
                break;
            case 158:
                name = "Kraftstrotzer";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Orks";
                feuer = 4;
                erde = 5;
                gold = 2;
                break;
            case 159:
                name = "Wargfreund";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Orks";
                feuer = 5;
                erde = 3;
                sf = true;
                gold = 2;
                break;
            case 160:
                name = "Sklavenmacher";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Orks";
                feuer = 1;
                erde = 5;
                gold = 1;
                break;
            case 161:
                name = "Schmierpfote";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Orks";
                feuer = 4;
                erde = 3;
                gold = 1;
                break;
            case 162:
                name = "Schlangenzunge";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Orks";
                feuer = 3;
                erde = 4;
                gold = 1;
                break;
            case 163:
                name = "Kettenschwinger";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Orks";
                feuer = 2;
                erde = 2;
                sf = true;
                gold = 2;
                break;
            case 164:
                name = "Röchelmörder";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Orks";
                feuer = 2;
                erde = 2;
                sf = true;
                gold = 3;
                break;
            case 165:
                name = "Winselstrecker";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Orks";
                feuer = 2;
                erde = 1;
                sf = true;
                gold = 3;
                break;
            case 166:
                name = "Gurgelschlitzer";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Orks";
                feuer = 1;
                erde = 2;
                sf = true;
                gold = 3;
                break;
            case 167:
                name = "Goblinräuber";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Orks";
                feuer = 1;
                erde = 2;
                sf = true;
                gold = 1;
                break;
            case 168:
                name = "Schwingkette";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Orks";
                feuer = 1;
                erde = 1;
                sf = true;
                gold = 2;
                break;
            case 169:
                name = "Steinerbe";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Orks";
                feuer = 0;
                erde = 0;
                symbole.add(KartenSymbol.SCHILD_FEUER);
                gold = 1;
                break;
            case 170:
                name = "Erdrutsch";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Orks";
                feuer = 2;
                erde = 3;
                gold = 1;
                break;
            case 171:
                name = "Schwächlinggranate";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Orks";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 1;
                break;
            case 172:
                name = "Schwächlingkanone";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Orks";
                feuer = 1;
                erde = 3;
                gold = 2;
                break;
            case 173:
                name = "Säbelwolf-Reittier";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Orks";
                feuer = 1;
                erde = 1;
                sf = true;
                gold = 1;
                break;
            case 174:
                name = "Kriegswolf-Reittier";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Orks";
                feuer = 1;
                erde = 1;
                sf = true;
                gold = 1;
                break;
            case 175:
                name = "Nachtwarg-Reittier";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Orks";
                feuer = 1;
                erde = 1;
                sf = true;
                gold = 1;
                break;
            case 176:
                name = "Streitwarg-Reittier";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Orks";
                feuer = 1;
                erde = 1;
                sf = true;
                gold = 1;
                break;
            case 177:
                name = "Mordende Truppe";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Orks";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 1;
                break;
            case 178:
                name = "Plündernde Truppe";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Orks";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 0;
                break;
            case 179:
                name = "Absorbierender Turm";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Orks";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 180:
                name = "Explodierende Opfergabe";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Orks";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 182:
                name = "Versuche den Heiligen Drachen";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Zwerge";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 4;
                break;
            case 183:
                name = "Studiere die Landkarte";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Zwerge";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 184:
                name = "Erwecke die Gier";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Zwerge";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 0;
                break;
            case 185:
                name = "Fürst der Goldminen";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Zwerge";
                feuer = 6;
                erde = 2;
                gold = 2;
                break;
            case 186:
                name = "Fürst der Festungen";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Zwerge";
                feuer = 2;
                erde = 6;
                gold = 2;
                break;
            case 187:
                name = "Erfahrener Elitesoldat";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Zwerge";
                feuer = 1;
                erde = 5;
                gold = 1;
                break;
            case 188:
                name = "Goldgekleideter Wächter";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Zwerge";
                feuer = 0;
                erde = 5;
                gold = 1;
                break;
            case 189:
                name = "Flüchtling der Berge";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Zwerge";
                feuer = 4;
                erde = 3;
                gold = 1;
                break;
            case 190:
                name = "Verbannter des Nordens";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Zwerge";
                feuer = 3;
                erde = 4;
                gold = 1;
                break;
            case 191:
                name = "Axtschwinger Elite";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Zwerge";
                feuer = 4;
                erde = 2;
                gold = 0;
                break;
            case 192:
                name = "Hammerstoß Elite";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Zwerge";
                feuer = 2;
                erde = 4;
                gold = 0;
                break;
            case 193:
                name = "Meister der Trünke";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Zwerge";
                feuer = 2;
                erde = 2;
                sf = true;
                gold = 1;
                break;
            case 194:
                name = "Kriegskunstmeister";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Zwerge";
                feuer = 2;
                erde = 2;
                sf = true;
                gold = 1;
                break;
            case 195:
                name = "Meisterchronist";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Zwerge";
                feuer = 2;
                erde = 2;
                sf = true;
                gold = 1;
                break;
            case 196:
                name = "Explosionsmeister";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Zwerge";
                feuer = 2;
                erde = 1;
                sf = true;
                gold = 1;
                break;
            case 197:
                name = "Meister der Flammen";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Zwerge";
                feuer = 1;
                erde = 2;
                sf = true;
                gold = 1;
                break;
            case 198:
                name = "Flammenwellen-Bombe";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Zwerge";
                feuer = 0;
                erde = 0;
                sf = true;
                symbole.add(KartenSymbol.FREI);
                gold = 2;
                break;
            case 199:
                name = "Erdaushöhler-Bombe";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Zwerge";
                feuer = 0;
                erde = 0;
                sf = true;
                symbole.add(KartenSymbol.FREI);
                gold = 2;
                break;
            case 200:
                name = "Tiefenwasser-Bombe";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Zwerge";
                feuer = 0;
                erde = 0;
                sf = true;
                symbole.add(KartenSymbol.FREI);
                gold = 2;
                break;
            case 201:
                name = "Lichtsucher-Bombe";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Zwerge";
                feuer = 0;
                erde = 0;
                sf = true;
                symbole.add(KartenSymbol.FREI);
                gold = 2;
                break;
            case 202:
                name = "Monduntergangs-Bombe";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Zwerge";
                feuer = 0;
                erde = 0;
                sf = true;
                symbole.add(KartenSymbol.FREI);
                gold = 2;
                break;
            case 203:
                name = "Trunk der Kosmischen Kraft";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Zwerge";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 3;
                break;
            case 204:
                name = "Trunk der Vereinigung";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Zwerge";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 205:
                name = "Trunk des Feuers";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Zwerge";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 206:
                name = "Trunk der Erde";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Zwerge";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 207:
                name = "Trunk der Leidenschaft";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Zwerge";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 208:
                name = "Expeditionskarte";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Zwerge";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 1;
                break;
            case 209:
                name = "Feldschmiede";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Zwerge";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.AUFNEHMEN);
                gold = 3;
                break;
            case 210:
                name = "Vulkanschmiede";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Zwerge";
                feuer = 2;
                erde = 2;
                symbole.add(KartenSymbol.FREI);
                gold = 3;
                break;
            case 212:
                name = "Trinke das Wasser des Lebens";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Meervolk";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 4;
                break;
            case 213:
                name = "Ertränke den Widerstand";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Meervolk";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 2;
                break;
            case 214:
                name = "Verteidige die Tiefe";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Meervolk";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 1;
                break;
            case 215:
                name = "Erwecke die Balance";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Meervolk";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 0;
                break;
            case 216:
                name = "König der Fluten";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Meervolk";
                feuer = 5;
                erde = 5;
                gold = 2;
                break;
            case 217:
                name = "Gigantische Seeschlange";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Meervolk";
                feuer = 5;
                erde = 0;
                symbole.add(KartenSymbol.GESCHUETZT);
                gold = 1;
                break;
            case 218:
                name = "Riesenkrake";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Meervolk";
                feuer = 0;
                erde = 5;
                symbole.add(KartenSymbol.GESCHUETZT);
                gold = 1;
                break;
            case 219:
                name = "Herr der Küsten";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Meervolk";
                feuer = 4;
                erde = 4;
                gold = 1;
                break;
            case 220:
                name = "Verfressener Monsterfisch";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Meervolk";
                feuer = 4;
                erde = 0;
                symbole.add(KartenSymbol.GESCHUETZT);
                gold = 0;
                break;
            case 221:
                name = "Tödliches Krustentier";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Meervolk";
                feuer = 0;
                erde = 4;
                symbole.add(KartenSymbol.GESCHUETZT);
                gold = 0;
                break;
            case 222:
                name = "Schatztaucher";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Meervolk";
                feuer = 3;
                erde = 3;
                sf = true;
                gold = 1;
                break;
            case 223:
                name = "Hüter der Tiefen";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Meervolk";
                feuer = 3;
                erde = 2;
                symbole.add(KartenSymbol.GESCHUETZT);
                gold = 0;
                break;
            case 224:
                name = "Wächter der Strände";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Meervolk";
                feuer = 2;
                erde = 3;
                symbole.add(KartenSymbol.GESCHUETZT);
                gold = 0;
                break;
            case 225:
                name = "Diebischer Taucher";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Meervolk";
                feuer = 2;
                erde = 2;
                sf = true;
                gold = 1;
                break;
            case 226:
                name = "Yin";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Meervolk";
                feuer = 2;
                erde = 0;
                sf = true;
                gold = 1;
                break;
            case 227:
                name = "Yang";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Meervolk";
                feuer = 0;
                erde = 2;
                sf = true;
                gold = 1;
                break;
            case 228:
                name = "Aura des Aales";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Meervolk";
                feuer = 2;
                erde = 0;
                symbole.add(KartenSymbol.FREI);
                gold = 1;
                break;
            case 229:
                name = "Aura des Oktopusses";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Meervolk";
                feuer = 0;
                erde = 2;
                symbole.add(KartenSymbol.FREI);
                gold = 1;
                break;
            case 230:
                name = "Aura der Krabbe";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Meervolk";
                feuer = 0;
                erde = 0;
                symbole.add(KartenSymbol.SCHILD_FEUER);
                symbole.add(KartenSymbol.SCHILD_ERDE);
                gold = 2;
                break;
            case 231:
                name = "Verfluchter Dreizack";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Meervolk";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 1;
                break;
            case 232:
                name = "Zweizack des Lichts";
                typ = KartenTyp.EINFACHE_VERSTAERKUNG;
                fraktion = "Meervolk";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 1;
                break;
            case 233:
                name = "Lebende Ebbe";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Meervolk";
                feuer = 3;
                erde = 0;
                symbole.add(KartenSymbol.GESCHUETZT);
                gold = 2;
                break;
            case 234:
                name = "Erweckte Flut";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Meervolk";
                feuer = 0;
                erde = 3;
                symbole.add(KartenSymbol.GESCHUETZT);
                gold = 2;
                break;
            case 235:
                name = "Florierender Handelsposten";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Meervolk";
                feuer = 2;
                erde = 0;
                symbole.add(KartenSymbol.FREI);
                gold = 2;
                break;
            case 236:
                name = "Gefährliche Fälle";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Meervolk";
                feuer = 0;
                erde = 2;
                symbole.add(KartenSymbol.FREI);
                gold = 2;
                break;
            case 237:
                name = "Reißende Sturmflut";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Meervolk";
                feuer = 1;
                erde = 1;
                sf = true;
                gold = 3;
                break;
            case 238:
                name = "Gewaltige Gezeitenflut";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Meervolk";
                feuer = 1;
                erde = 1;
                sf = true;
                gold = 3;
                break;
            case 239:
                name = "Wasser der Verbindung";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Meervolk";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 1;
                break;
            case 240:
                name = "Unwiderstehliche Sirenen";
                typ = KartenTyp.BLEIBENDE_VERSTAERKUNG;
                fraktion = "Meervolk";
                feuer = 0;
                erde = 0;
                sf = true;
                gold = 3;
                break;
            case 242:
                name = "Plane einen Raubzug";
                typ = KartenTyp.EINFACHE_AKTION;
                fraktion = "Fremde";
                feuer = 0;
                erde = 0;
                sf = true; //todo sf implementieren
                gold = 2;
                break;
            case 243:
                name = "Meeresfalke";
                typ = KartenTyp.BLEIBENDE_AKTION;
                subtyp = KartenSubtyp.SCHIFF; //todo schiffe implementieren
                fraktion = "Fremde";
                feuer = 0;
                erde = 0;
                sf = true; //todo sf implementieren
                gold = 3;
                break;
            case 244:
                name = "Seeteufel";
                typ = KartenTyp.BLEIBENDE_AKTION;
                subtyp = KartenSubtyp.SCHIFF; //todo schiffe implementieren
                fraktion = "Fremde";
                feuer = 0;
                erde = 0;
                sf = true; //todo sf implementieren
                gold = 2;
                break;
            case 245:
                name = "Ozeanmond";
                typ = KartenTyp.BLEIBENDE_AKTION;
                subtyp = KartenSubtyp.SCHIFF; //todo schiffe implementieren
                fraktion = "Fremde";
                feuer = 0;
                erde = 0;
                gold = 1;
                break;
            case 246:
                name = "Krieger Lo Tan";
                typ = KartenTyp.CHARAKTER;
                fraktion = "Fremde";
                feuer = 5;
                erde = 4;
                gold = 2;
                break;
        }
    }
    //KLON-KONSTRUKTOR
    Karte(Karte pKarte){
        nr = pKarte.nr;
        name = pKarte.name;
        typ = pKarte.typ;
        subtyp = pKarte.subtyp;
        fraktion = pKarte.fraktion;
        feuer = pKarte.feuer;
        erde = pKarte.erde;
        aktiv = pKarte.aktiv;
        sf = pKarte.sf;
        symbole.addAll(pKarte.symbole);
        selected = pKarte.selected;
        gold = pKarte.gold;
        sfVerbraucht = pKarte.sfVerbraucht;
        aufgedeckt = pKarte.aufgedeckt;
    }

    //METHODEN
    void setErde(int pErde){
        erde = pErde;
    }
    void setFeuer(int pFeuer){
        feuer = pFeuer;
    }
    void deckeAuf(){
        aufgedeckt=true;
    }
    void verdecke(){
        aufgedeckt=false;
    }
    boolean isAufgedeckt(){
        return aufgedeckt;
    }
    boolean isSfVerbraucht(){return sfVerbraucht;}
    void setSfVerbraucht(boolean pBool){sfVerbraucht = pBool;}

    boolean sfIgnoriert(Spieler pSpieler){
        if (nr==103){return false;} //Wenn ich Trillerhexer betrachte
        if (nr==34) { //Wenn ich BanneDenGegner betrachte
            if (pSpieler.hat(103) || pSpieler.hat(148))
                return true;
        } else if (nr==148){ //Wenn ich Erstickender Zauber betrachte
            if (pSpieler.hat(148) || pSpieler.hat(103) || karteIgnoriert(pSpieler))
                return true;
        } else if (
                    !(istAktiv() && hatSymbolOhneIgnorier(KartenSymbol.GESCHUETZT))
                    &&
                    (
                        karteIgnoriert(pSpieler)
                        || (!istAviorChar() && pSpieler.hatAktiv(103)) //Trillerhexer
                        || (pSpieler.hatAktiv(34)) //Banne den Gegner
                        || (!istAviorChar() && pSpieler.hatAktiv(148)) //Erstickender Zauber
                    )
        ){
            return true;
        }
        return false;
    }
    boolean istAviorChar(){
        return typ==KartenTyp.CHARAKTER && fraktion.equals("Avior");
    }
    boolean karteIgnoriert(Spieler pSpieler){
        if(
                !(istAktiv() && hatSymbolOhneIgnorier(KartenSymbol.GESCHUETZT))
                &&
                (
                    (typ==KartenTyp.EINFACHE_VERSTAERKUNG && pSpieler.hatAktiv(104)) //Zirperhexer
                    || (typ==KartenTyp.BLEIBENDE_VERSTAERKUNG && pSpieler.hatAktiv(105)) //Zwitscherhexer
                    || (!istAviorChar() && hatSymbole() && pSpieler.hatAktiv(107)) //Staffelmagi
                    || (!istAviorChar() && typ==KartenTyp.CHARAKTER && pSpieler.hatAktiv(108)) //Rottenbrecher
                )
        ){
            return true;
        }
        return false;
    }
    int getGold(){
        return gold;
    }
    void setSelected(boolean s){
        selected = s;
    }
    boolean getSelected(){
        return selected;
    }
    boolean hatSF(){
        return sf;
    }
    int getNummer(){return nr;}
    String getFileName(){
        if (nr<10){
            return "00" + nr + ".png";
        } else if (nr<100){
            return "0" + nr + ".png";
        } else {
            return nr + ".png";
        }
    }
    String getName(){ return name; }
    String getFraktion(){ return fraktion; }
    KartenTyp getTyp(){
        return typ;
    }
    int getF(){return feuer;}
    int getE(){return erde;}
    int getFeuer(Spieler pSpieler){
        if (
                (
                (pSpieler.hatAktiv(141) && typ==KartenTyp.CHARAKTER && feuer%2==0) //Hexling Gnom
                || (pSpieler.hatAktiv(142) && typ==KartenTyp.CHARAKTER && feuer%2!=0) //Hexling Undine
                || (pSpieler.hatAktiv(143) && (typ==KartenTyp.EINFACHE_VERSTAERKUNG||typ==KartenTyp.BLEIBENDE_VERSTAERKUNG) && feuer%2==0) //Hexling Salamander
                || (pSpieler.hatAktiv(144) && (typ==KartenTyp.EINFACHE_VERSTAERKUNG||typ==KartenTyp.BLEIBENDE_VERSTAERKUNG) && feuer%2!=0) //Hexling Sylphe
                )
                && !(istAktiv() && hatSymbol(KartenSymbol.GESCHUETZT,pSpieler))
        )return 0;
        return feuer;
    }
    int getErde(Spieler pSpieler){
        if (
                (
                (pSpieler.hatAktiv(141) && typ==KartenTyp.CHARAKTER && erde%2==0) //Hexling Gnom
                || (pSpieler.hatAktiv(142) && typ==KartenTyp.CHARAKTER && erde%2!=0) //Hexling Undine
                || (pSpieler.hatAktiv(143) && (typ==KartenTyp.EINFACHE_VERSTAERKUNG||typ==KartenTyp.BLEIBENDE_VERSTAERKUNG) && erde%2==0) //Hexling Salamander
                || (pSpieler.hatAktiv(144) && (typ==KartenTyp.EINFACHE_VERSTAERKUNG||typ==KartenTyp.BLEIBENDE_VERSTAERKUNG) && erde%2!=0) //Hexling Sylphe
                )
                && !(istAktiv() && hatSymbol(KartenSymbol.GESCHUETZT,pSpieler))
        )return 0;
        return erde;
    }
    void aktiviere(){
        aktiv = true;
    }
    void deaktiviere(){
        aktiv = false;
    }
    boolean istAktiv(){
        return aktiv;
    }
    boolean hatSymbole(){
        return symbole.size()>0;
    }
    boolean hatSymbol(KartenSymbol pSymbol, Spieler pSpieler){
        if (
                !(istAktiv() && hatSymbolOhneIgnorier(KartenSymbol.GESCHUETZT) && !pSpieler.hatAktiv(48))
                &&
                (
                    karteIgnoriert(pSpieler)
                    || pSpieler.hatAktiv(48) //Stoischer Holzfäller
                    || (pSpieler.hatAktiv(87) && pSymbol!= KartenSymbol.STOPP && pSymbol!= KartenSymbol.GESCHUETZT) //Betäubendes Horn
                    || (pSpieler.hatAktiv(179) && pSymbol!= KartenSymbol.STOPP && pSymbol!= KartenSymbol.GESCHUETZT) //Absorbierender Turm
                )
        ){
            return false;
        }
        for (KartenSymbol thisSymbol : symbole) {
            if (thisSymbol == pSymbol) {
                return true;
            }
        }
        return false;
    }
    boolean hatSymbolOhneIgnorier(KartenSymbol pSymbol){
        for(KartenSymbol thisSymbol : symbole){
            if(thisSymbol == pSymbol)
                return true;
        }
        return false;
    }
}
