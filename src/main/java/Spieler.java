
import java.io.Serializable;
import java.util.ArrayList;

class Spieler implements Serializable {

    //ATTRIBUTE
    private String name;
    private Zone nachziehstapel;
    private Zone hand;
    private Zone spielzone;
    private Zone abwurfzone;
    private int drachen;
    private Spieler gegner;

    private int anfuehrer;
    private int gott;

    //KONSTRUKTOR
    Spieler(
            String pName,
            ArrayList<Karte> pDeck,
            int pAnfuehrer,
            int pGott
    ){
        name = pName;
        nachziehstapel = new Zone(pDeck);
        hand = new Zone();
        spielzone = new Zone();
        abwurfzone = new Zone();
        drachen = 0;
        anfuehrer = pAnfuehrer;
        gott = pGott;
    }
    //KLON-KONSTRUKTOR
    Spieler(Spieler pSpieler){
        name = pSpieler.name;
        nachziehstapel = new Zone(pSpieler.nachziehstapel);
        hand = new Zone(pSpieler.hand);
        spielzone = new Zone(pSpieler.spielzone);
        abwurfzone = new Zone(pSpieler.abwurfzone);
        drachen = pSpieler.drachen;
        anfuehrer = pSpieler.anfuehrer;
        gott = pSpieler.gott;
    }

    //METHODEN
    boolean allesAlle(){
        return hand.getSize()==0 && nachziehstapel.getSize()==0;
    }
    void setGegner(Spieler pSpieler){
        gegner = pSpieler;
    }
    Spieler getGegner(){
        return gegner;
    }
    //Lege eine Karte von der Hand in die eigene Spielzone
    void spiele(int pIndex){
        Karte karte = hand.remove(pIndex);
        karte.aktiviere();
        karte.setSfVerbraucht(false);
        spielzone.add(karte);
    }
    Karte mulliganAufdecken(){
        Karte k = nachziehstapel.remove(nachziehstapel.getSize()-1);
        k.aktiviere();
        spielzone.add(k);
        return k;
    }
    void mulliganMischen(){
        //Gewählte Handkarten in den Nachziehstapel
        for (int i=hand.getSize()-1; i>=0 ; i--){
            if (hand.get(i).getSelected()){
                hand.get(i).setSelected(false);
                nachziehstapel.add(hand.remove(i));
            }
        }
        //Aufgedeckte in den Nachziehstapel
        for(int i=spielzone.getSize()-1; i>=0; i--){
            //nachziehstapel.add(spielzone.remove(i));
            if (spielzone.get(i).getTyp()!=KartenTyp.ANFUEHRER && spielzone.get(i).getTyp()!=KartenTyp.GOTT)
                nachziehstapel.add(new Karte(spielzone.get(i).getNummer()));
        }
        //Mischen
        nachziehstapel.mischen();
        //Auf 6 ziehen
        zieheAuf(6);
    }
    void mulliganLeereSpielzone(){
        for(int i=spielzone.getSize()-1; i>=0; i--){
            if (spielzone.get(i).getTyp()!=KartenTyp.ANFUEHRER && spielzone.get(i).getTyp()!=KartenTyp.GOTT)
                spielzone.remove(i);
        }
    }
    void anfuehrerUndGottInSpielzoneLegen(){
        Karte karte = new Karte(anfuehrer*30 + 1);
        karte.aktiviere();
        spielzone.add(karte);
        karte = new Karte(gott + 297);
        karte.aktiviere();
        spielzone.add(karte);
    }
    //Deaktiviere alle Karten (in der Spielzone) eines beliebigen Typs
    void deaktiviereAlle(KartenTyp pTyp){
        Karte k;
        for (int i=0; i<spielzone.getSize(); i++){
            k = spielzone.get(i);
            if (k.getTyp() == pTyp){
                k.deaktiviere();
            }
        }
    }
    //Erhalte momentane eigene Stärke in einem Element
    int getStaerke(Element pElement){
        Karte trunkKarte = null;
        //TRÜNKE (1/2)
        if (hatAktiv(205)){ //Trunk des Feuers
            for(int i=spielzone.getSize()-1; i>=0; i--){
                trunkKarte = spielzone.get(i);
                if(trunkKarte.istAktiv() && trunkKarte.getTyp()==KartenTyp.CHARAKTER){
                    if (trunkKarte.getFeuer(gegner)<6)
                        trunkKarte.setFeuer(6);
                    break;
                }
            }
        }
        if (hatAktiv(206)){ //Trunk der Erde
            for(int i=spielzone.getSize()-1; i>=0; i--){
                trunkKarte = spielzone.get(i);
                if(trunkKarte.istAktiv() && trunkKarte.getTyp()==KartenTyp.CHARAKTER){
                    if (trunkKarte.getErde(gegner)<6)
                        trunkKarte.setErde(6);
                    break;
                }
            }
        }
        if (hatAktiv(207)){ //Trunk der Leidenschaft
            for(int i=spielzone.getSize()-1; i>=0; i--){
                trunkKarte = spielzone.get(i);
                if(trunkKarte.istAktiv() && trunkKarte.getTyp()==KartenTyp.CHARAKTER){
                    if (trunkKarte.getFeuer(gegner)<5)
                        trunkKarte.setFeuer(5);
                    if (trunkKarte.getErde(gegner)<5)
                        trunkKarte.setErde(5);
                    break;
                }
            }
        }
        if (hatAktiv(204)){ //Trunk der Vereinigung
            for(int i=spielzone.getSize()-1; i>=0; i--){
                trunkKarte = spielzone.get(i);
                if(trunkKarte.istAktiv() && trunkKarte.getTyp()==KartenTyp.CHARAKTER){
                    int neuerWert = trunkKarte.getErde(gegner) + trunkKarte.getFeuer(gegner);
                    trunkKarte.setFeuer( neuerWert );
                    trunkKarte.setErde( neuerWert );
                    break;
                }
            }
        }
        if (hatAktiv(203)){ //Trunk der kosmischen Kraft
            for(int i=spielzone.getSize()-1; i>=0; i--){
                trunkKarte = spielzone.get(i);
                if(trunkKarte.istAktiv() && trunkKarte.getTyp()==KartenTyp.CHARAKTER){
                    trunkKarte.setFeuer( 2*trunkKarte.getFeuer(gegner) );
                    trunkKarte.setErde( 2*trunkKarte.getErde(gegner) );
                    break;
                }
            }
        }

        int staerke = 0;
        Karte k;
        for (int i=0; i<spielzone.getSize(); i++){
            k = spielzone.get(i);
            if (k.istAktiv() && !k.karteIgnoriert(gegner)) {
                int erhoehung=0;
                if (pElement == Element.ERDE) {
                    if(k!=trunkKarte)
                        erhoehung = k.getErde(gegner);
                    else
                        erhoehung = k.getE();
                    if (hatAktiv(239) && k.getTyp()==KartenTyp.CHARAKTER && k.getFeuer(gegner)>erhoehung)
                        erhoehung = k.getFeuer(gegner);
                } else if (pElement == Element.FEUER) {
                    if (k!=trunkKarte)
                        erhoehung = k.getFeuer(gegner);
                    else
                        erhoehung = k.getF();
                    if (hatAktiv(239) && k.getTyp()==KartenTyp.CHARAKTER && k.getErde(gegner)>erhoehung)
                        erhoehung = k.getErde(gegner);
                }
                staerke += erhoehung;
            }
        }
        //WERT DES ZULETZT GESPIELTEN CHARS VERDOPPELN
        if (
            (hatAktiv(22) && pElement== Element.FEUER) //Vulkanblitz-Erzeuger
            || (hatAktiv(23) && pElement== Element.ERDE) //Titanblitz-Erzeuger
            || (hatAktiv(59) && pElement== Element.ERDE) //Kraft spendender Turm
        ){
            int verdopplungen = 1;
            if (hatAktiv(22) && pElement== Element.FEUER) verdopplungen *= 2; //vulkan
            if (hatAktiv(23) && pElement== Element.ERDE) verdopplungen *= 2; //titan
            if (hatAktiv(59) && pElement== Element.ERDE) verdopplungen *= 2; //kraftturm
            if (hatAktiv(135)) verdopplungen *= 2; //schmackes
            verdopplungen -= 1;
            if (hatAktiv(135)) verdopplungen -= 1; //Fals schmackes -> 1 mehr abziehen, da 1 drauch kommt, bei alle chars verdoppeln
            for(int i=spielzone.getSize()-1; i>=0; i--){
                k = spielzone.get(i);
                if(k.getTyp()== KartenTyp.CHARAKTER && k.istAktiv() && !k.karteIgnoriert(gegner)){
                    if (pElement == Element.ERDE) {
                        int erhoehung;
                        if (k!=trunkKarte)
                            erhoehung = verdopplungen * k.getErde(gegner);
                        else
                            erhoehung = verdopplungen * k.getE();
                        if (hatAktiv(239) && k.getFeuer(gegner)>erhoehung)
                            erhoehung = k.getFeuer(gegner);
                        staerke += erhoehung;
                    } else if (pElement == Element.FEUER) {
                        int erhoehung;
                        if (k!=trunkKarte)
                            erhoehung = verdopplungen * k.getFeuer(gegner);
                        else
                            erhoehung = verdopplungen * k.getF();
                        if (hatAktiv(239) && k.getErde(gegner)>erhoehung)
                            erhoehung = k.getErde(gegner);
                        staerke += erhoehung;
                    }
                    break;
                }
            }
        }
        //WERT ALLER CHARS VERDOPPELN
        if (hatAktiv(135)){ //Edling Schmackes
            for(int i=0; i<spielzone.getSize(); i++){
                k = spielzone.get(i);
                if (k.getTyp()== KartenTyp.CHARAKTER && k.istAktiv() && !k.karteIgnoriert(gegner)){
                    if (pElement == Element.ERDE) {
                        int erhoehung;
                        if (k!=trunkKarte)
                            erhoehung = k.getErde(gegner);
                        else
                            erhoehung = k.getE();
                        if (hatAktiv(239) && k.getFeuer(gegner)>erhoehung)
                            erhoehung = k.getFeuer(gegner);
                        staerke += erhoehung;
                    } else if (pElement == Element.FEUER) {
                        int erhoehung;
                        if (k!=trunkKarte)
                            erhoehung = k.getFeuer(gegner);
                        else
                            erhoehung = k.getF();
                        if (hatAktiv(239) && k.getErde(gegner)>erhoehung)
                            erhoehung = k.getErde(gegner);
                        staerke += erhoehung;
                    }
                }
            }
        }
        //WERT ALLER BLEIBENDEN-VERS VERDOPPELN
        if (hatAktiv(44)){ //Flinkfüßige Jägerin
            for(int i=0; i<spielzone.getSize(); i++){
                k = spielzone.get(i);
                if (k.getTyp()== KartenTyp.BLEIBENDE_VERSTAERKUNG && k.istAktiv() && !k.karteIgnoriert(gegner)){
                    if (pElement == Element.ERDE) {
                        staerke += k.getErde(gegner);
                    } else if (pElement == Element.FEUER) {
                        staerke += k.getFeuer(gegner);
                    }
                }
            }
        }
        //WERT ALLER EINFACHEN-VERS VERDOPPELN
        if (hatAktiv(106)){ //Geschwaderberserker
            for(int i=0; i<spielzone.getSize(); i++){
                k = spielzone.get(i);
                if (k.getTyp()== KartenTyp.EINFACHE_VERSTAERKUNG && k.istAktiv() && !k.karteIgnoriert(gegner)){
                    if (pElement == Element.ERDE) {
                        staerke += k.getErde(gegner);
                    } else if (pElement == Element.FEUER) {
                        staerke += k.getFeuer(gegner);
                    }
                }
            }
        }
        //ERHÖHE GESAMTSTÄRKE AUF X
        if (hatAktiv(231) && staerke<7 && pElement==Element.FEUER){ //Verfluchter Dreizack
            staerke = 7;
        }
        if (hatAktiv(232) && staerke<7 && pElement==Element.ERDE){ //Zweizack des Lichts
            staerke = 7;
        }
        if (hatAktiv(21) && staerke<6){ //Urgewaltiger Zauber
            staerke = 6;
        }

        //TRÜNKE RÜCKGÄNGIG (2/2)
        if (trunkKarte!=null){
            Karte original = new Karte(trunkKarte.getNummer());
            trunkKarte.setFeuer( original.getF() );
            trunkKarte.setErde( original.getE() );
        }


        return staerke;
    }
    void alleSfUnverbraucht(){
        for(int i=0; i<spielzone.getSize(); i++){
            spielzone.get(i).setSfVerbraucht(false);
        }
    }
    void verbrauche(int pNummer){
        for(int i=0; i<spielzone.getSize(); i++){
            Karte karte = spielzone.get(i);
            if(karte.getNummer()==pNummer){
                karte.setSfVerbraucht(true);
                break;
            }
        }
    }
    boolean hatAktiv(int pNummer){
        for(int i=0; i<spielzone.getSize(); i++){
            Karte karte = spielzone.get(i);
            if(karte.istAktiv() && karte.getNummer()==pNummer && !karte.sfIgnoriert(gegner)){
                return true;
            }
        }
        return false;
    }
    boolean hatAktivUndUnverbraucht(int pNummer){
        for(int i=0; i<spielzone.getSize(); i++){
            Karte karte = spielzone.get(i);
            if(karte.istAktiv() && !karte.isSfVerbraucht() && karte.getNummer()==pNummer && !karte.sfIgnoriert(gegner)){
                return true;
            }
        }
        return false;
    }
    boolean hat(int pNummer){
        for(int i=0; i<spielzone.getSize(); i++){
            Karte karte = spielzone.get(i);
            if(karte.istAktiv() && karte.getNummer()==pNummer && !karte.karteIgnoriert(gegner)){
                return true;
            }
        }
        return false;
    }
    boolean hatAktivenSchild(Element pElement){
        KartenSymbol symbol;
        if (pElement == Element.ERDE){
            symbol = KartenSymbol.SCHILD_ERDE;
        } else if (pElement == Element.FEUER){
            symbol = KartenSymbol.SCHILD_FEUER;
        } else {
            return false;
        }
        for(int i=0; i<spielzone.getSize(); i++){
            if (spielzone.get(i).istAktiv() && spielzone.get(i).hatSymbol(symbol, gegner)){
                return true;
            }
        }
        return false;
    }
    //Erhalte Zone
    Zone getSpielzone(){
        return spielzone;
    }
    Zone getAbwurfzone(){ return abwurfzone;}
    Zone getHand(){
        return hand;
    }
    //erhalte Anzahl der eigenen Handkarten
    int getHandSize(){
        return hand.getSize();
    }
    //erhalte eine bestimmte handkarte
    Karte getHandKarte(int pIndex){
        return hand.get(pIndex);
    }
    //Get Name
    String getName(){
        return name;
    }
    //Werfe alle charaktere und verstärkungen der eigenen spielzone ab (ende eines kampfes)
    void raeumeSpielzoneAuf(){
        Karte k;
        for (int i=0; i<spielzone.getSize(); i++){
            k = spielzone.get(i);
            if (k.getTyp()== KartenTyp.CHARAKTER || k.getTyp()== KartenTyp.EINFACHE_VERSTAERKUNG || k.getTyp()== KartenTyp.BLEIBENDE_VERSTAERKUNG){
                k.deaktiviere();
                abwurfzone.add(spielzone.remove(i));
                i--;
            }
        }
    }
    void mischeVonHandInNachziehstapel(int pIndex){
        Karte karte = hand.remove(pIndex);
        karte.deaktiviere();
        nachziehstapel.add(karte);
        nachziehstapel.mischen();
    }
    void legeHandkarteUnterNachziehstapel(int pIndex){
        Karte karte = hand.remove(pIndex);
        nachziehstapel.legeGanzNachUnten(karte);
    }

    //Gibt an, ob Spieler mind eine aktive Karte mit dem Stopp-Symbol hat
    boolean hatStoppKarten(boolean pCharGespielt){
        if (pCharGespielt && gegner.hatAktiv(24)){ //Talisman der Verstummung
            return true;
        }
        for(int i=0; i<spielzone.getSize(); i++){
            if(spielzone.get(i).hatSymbol(KartenSymbol.STOPP, gegner) && spielzone.get(i).istAktiv()){
                return true;
            }
        }
        return false;
    }
    //Gibt an, ob Spieler mind einen aktiven Charakter mit dem Aufnehm-Symbol hat
    boolean hatAufnehmCharakter(){
        for (int i=0; i<spielzone.getSize(); i++){
            Karte karte = spielzone.get(i);
            if (karte.istAktiv() && karte.hatSymbol(KartenSymbol.AUFNEHMEN, gegner) && karte.getTyp()== KartenTyp.CHARAKTER){
                return true;
            }
        }
        return false;
    }
    //Gibt an, ob Spieler 6 oder mehr Char/Verst in der Spielzone hat (zweiter Drache)
    boolean hat6oderMehrCharVer(){
        return getCharVerst() >= 6;
    }
    int getCharVerst(){
        int count=0;
        KartenTyp typ;
        for (int i=0; i<spielzone.getSize(); i++){
            typ = spielzone.get(i).getTyp();
            if (typ== KartenTyp.CHARAKTER || typ== KartenTyp.EINFACHE_VERSTAERKUNG || typ== KartenTyp.BLEIBENDE_VERSTAERKUNG){
                count++;
            }
        }
        return count;
    }
    int getNachziehSize(){
        return nachziehstapel.getSize();
    }
    Zone getNachziehstapel(){
        return nachziehstapel;
    }
    void nehmeVonNachziehAufDieHand(int pIndex){
        if ((!gegner.hatAktiv(180) || getHandSize()<4) && !gegner.hatAktiv(197))
            hand.add(nachziehstapel.remove(pIndex)).verdecke();
    }
    void deckeHandAuf(){
        for(int i=0; i<hand.getSize(); i++){
            hand.get(i).deckeAuf();
        }
    }
    void werfeAlleVonSpielzoneAb(KartenTyp pTyp){
        for(int i=spielzone.getSize()-1; i>=0; i--){
            if (
                    spielzone.get(i).getTyp() == pTyp
                    && !(spielzone.get(i).istAktiv() && spielzone.get(i).hatSymbol(KartenSymbol.GESCHUETZT,gegner))
            ){
                Karte karte = spielzone.remove(i);
                karte.deaktiviere();
                abwurfzone.add(karte);
            }
        }
    }
    void werfeAlleAktivenVonSpielzoneAb(KartenTyp pTyp){
        for(int i=spielzone.getSize()-1; i>=0; i--){
            if (spielzone.get(i).getTyp() == pTyp && spielzone.get(i).istAktiv() && !spielzone.get(i).hatSymbol(KartenSymbol.GESCHUETZT, gegner)){
                Karte karte = spielzone.remove(i);
                karte.deaktiviere();
                abwurfzone.add(karte);
            }
        }
    }
    //Werfe eine bestimmte Handkarte ab
    void werfeHandkarteAb(int pIndex){
        abwurfzone.add(hand.remove(pIndex));
    }
    void werfeVonSpielzoneAb(int pIndex){
        Karte karte = spielzone.remove(pIndex);
        karte.deaktiviere();
        abwurfzone.add(karte);
    }
    void mischeAbwurfinNachzieh(){
        for(int i=abwurfzone.getSize()-1; i>=0; i--){
            nachziehstapel.add(abwurfzone.remove(i));
        }
        mischeNachziehstapel();
    }
    void mischeVonSpielzoneInNachziehstapel(int pIndex){
        Karte karte = spielzone.remove(pIndex);
        karte.deaktiviere();
        nachziehstapel.add(karte);
        nachziehstapel.mischen();
    }
    void nehmeVonSpielzoneAufHand(int pIndex){
        if ((!gegner.hatAktiv(180) || getHandSize()<4) && !gegner.hatAktiv(197)) {
            Karte karte = spielzone.remove(pIndex);
            karte.verdecke();
            karte.deaktiviere();
            hand.add(karte);
        }
    }
    void nehmeVonAbwurfzoneAufHand(int pIndex){
        if ((!gegner.hatAktiv(180) || getHandSize()<4) && !gegner.hatAktiv(197))
            hand.add(abwurfzone.remove(pIndex)).verdecke();
    }
    boolean hatMehrAls1AktivenChar(){
        boolean ersterChar = false;
        for(int i=0; i<spielzone.getSize(); i++){
            if(spielzone.get(i).getTyp() == KartenTyp.CHARAKTER && spielzone.get(i).istAktiv()){
                if (ersterChar){
                    return true;
                } else {
                    ersterChar=true;
                }
            }
        }
        return false;
    }
    boolean hatMehrAls1AktivenCharJeOhneGeschuetzt(){
        boolean ersterChar = false;
        for(int i=0; i<spielzone.getSize(); i++){
            if(
                    spielzone.get(i).getTyp() == KartenTyp.CHARAKTER
                    && spielzone.get(i).istAktiv()
                    && !spielzone.get(i).hatSymbol(KartenSymbol.GESCHUETZT,gegner)
            ){
                if (ersterChar){
                    return true;
                } else {
                    ersterChar=true;
                }
            }
        }
        return false;
    }
    //Get Anzahl der Drachen
    int getDrachen(){
        return drachen;
    }
    //Füge Drachen hinzu, oder entferne Drachen
    void aendereDrachen(int n){
        drachen += n;
    }
    //Mische Nachziehstapel
    void mischeNachziehstapel(){
        nachziehstapel.mischen();
    }
    //Ziehe n Karten
    int ziehe(int n){
        int rval=0;
        n = Math.min(n, nachziehstapel.getSize());
        for(int i=0; i<n; i++){
            if ((!gegner.hatAktiv(180) || getHandSize()<4) && !gegner.hatAktiv(197)) {
                hand.add(nachziehstapel.removeTop()).verdecke();
                rval++;
            }
        }
        return rval;
    }
    void werfeVonNachziehAb(int pIndex){
        abwurfzone.add(nachziehstapel.remove(pIndex));
    }
    //ziehe auf n Handkarten nach
    int zieheAuf(int n){
        int rval = 0;
        if(n>hand.getSize()){
            n = n-getHandSize();
            rval = ziehe(n);
        }
        return rval;
    }
    void nichtigMacherMagusEffekt(){
        boolean mischen = false;
        for(int i=spielzone.getSize()-1; i>=0; i--){
            Karte karte = spielzone.get(i);
            KartenTyp typ = karte.getTyp();
            if (
                    (typ== KartenTyp.CHARAKTER || typ== KartenTyp.EINFACHE_VERSTAERKUNG || typ== KartenTyp.BLEIBENDE_VERSTAERKUNG)
                    && !(karte.istAktiv() && karte.hatSymbol(KartenSymbol.GESCHUETZT,gegner))
            ){
                karte = spielzone.remove(i);
                karte.deaktiviere();
                nachziehstapel.add(karte);
                mischen = true;
            }
        }
        if(mischen){
            nachziehstapel.mischen();
        }
    }
    void vertreibeEindringlingeEffekt(){
        for(int i=spielzone.getSize()-1; i>=0; i--){
            Karte karte = spielzone.get(i);
            if(
                    karte.hatSymbol(KartenSymbol.FREI, gegner)
                    && karte.istAktiv()
                    && !karte.hatSymbol(KartenSymbol.GESCHUETZT,gegner)
            ){
                karte = spielzone.remove(i);
                karte.deaktiviere();
                abwurfzone.add(karte);
            }
        }
    }
    void SchamaneDesJenseitsEffekt(){
        for(int i=1; i<=2; i++) {
            if (abwurfzone.getSize()>0) {
                int random = (int) (Math.random() * abwurfzone.getSize());
                if ((!gegner.hatAktiv(180) || getHandSize()<4) && !gegner.hatAktiv(197))
                    hand.add(abwurfzone.remove(random)).verdecke();
            }
        }
    }
    void ehreDieGefallenenEffekt(){
        for(int i=1; i<=3; i++) {
            if (abwurfzone.getSize()>0) {
                int random = (int) (Math.random() * abwurfzone.getSize());
                if ((!gegner.hatAktiv(180) || getHandSize()<4) && !gegner.hatAktiv(197))
                    hand.add(abwurfzone.remove(random)).verdecke();
            }
        }
    }
    void ertraenkeDenWiderstandEffekt1(int pAusserDieserKarte){
        if (pAusserDieserKarte==-1){
            for(int i=spielzone.getSize()-1; i>=0; i--){
                if (
                        spielzone.get(i).getTyp()==KartenTyp.CHARAKTER
                        && spielzone.get(i).istAktiv()
                ){
                    pAusserDieserKarte=i;
                    break;
                }
            }
        }
        for(int i=spielzone.getSize()-1; i>=0; i--){
            Karte karte = spielzone.get(i);
            KartenTyp typ = karte.getTyp();
            if(
                    i!=pAusserDieserKarte
                    && spielzone.get(i).istAktiv()
                    && (typ==KartenTyp.CHARAKTER||typ==KartenTyp.EINFACHE_VERSTAERKUNG)
                    && !spielzone.get(i).hatSymbol(KartenSymbol.GESCHUETZT,gegner)
            ){
                karte = spielzone.remove(i);
                karte.deaktiviere();
                abwurfzone.add(karte);
            }
        }
    }
}
