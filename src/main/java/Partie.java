import java.io.Serializable;
import java.util.ArrayList;

class Partie implements Serializable {

    //ATTRIBUTE
    private Spieler ich;
    private Spieler du;
    private Spieler aktiverSpieler;
    private int spielzug;
    private int kampfzug;
    private ZugPhase phase;
    private Element aktivesElement;

    private Partie beginnCharakterphase;
    private Partie beginnSF;
    private int aktionLimit;
    private int aktionenGespielt;
    private int charakterLimit;
    private int charaktereGespielt;
    private int verstaerkungLimit;
    private int verstaerkungenGespielt;
    private int einfachVLimit;
    private int evGespielt;
    private int bleibendVLimit;
    private int bvGespielt;
    private boolean charakterGespielt;
    private int abgeworfenFuerVerzicht;
    private int sonderfunktion=0;
    private int sfZaehler=0;
    private ArrayList<Integer> sfVorrat = new ArrayList<>();
    private String msg;
    private boolean gottaSend = false;
    private int gottaShow = -1;
    private Spieler sieger;
    private boolean gameEnd=false;
    private boolean gezogen;
    private boolean yinyangEffekt = false;
    private boolean flutEffekt = false;
    private ArrayList<Integer> gottaShowKarten = new ArrayList<Integer>();
    private String gottaShowTitel;
    private int gottaOpen=-1;
    private boolean gottaOpenNextSF=false;


    //KONSTRUKTOR
    Partie(Spieler pIch, Spieler pDu) {
        ich = pIch;
        du = pDu;
        aktivesElement = Element.UNBESTIMMT;
    }

    //KLON-KONSTRUKTOR
    Partie(Partie pPartie, boolean vertauscht) {
        if (vertauscht) {
            if (pPartie.aktiverSpieler == pPartie.ich) {
                ich = new Spieler(pPartie.du);
                du = new Spieler(pPartie.ich);
                aktiverSpieler = du;
            } else {
                ich = new Spieler(pPartie.du);
                du = new Spieler(pPartie.ich);
                aktiverSpieler = ich;
            }
        } else {
            if (pPartie.aktiverSpieler == pPartie.ich){
                ich = new Spieler(pPartie.ich);
                du = new Spieler(pPartie.du);
                aktiverSpieler = ich;
            } else {
                ich = new Spieler(pPartie.ich);
                du = new Spieler(pPartie.du);
                aktiverSpieler = du;
            }
        }
        ich.setGegner(du);
        du.setGegner(ich);
        spielzug = pPartie.spielzug;
        kampfzug = pPartie.kampfzug;
        phase = pPartie.phase;
        aktivesElement = pPartie.aktivesElement;
        aktionLimit = pPartie.aktionLimit;
        aktionenGespielt = pPartie.aktionenGespielt;
        charakterLimit = pPartie.charakterLimit;
        charaktereGespielt = pPartie.charaktereGespielt;
        verstaerkungLimit = pPartie.verstaerkungLimit;
        verstaerkungenGespielt = pPartie.verstaerkungenGespielt;
        einfachVLimit = pPartie.einfachVLimit;
        evGespielt = pPartie.evGespielt;
        bleibendVLimit = pPartie.bleibendVLimit;
        bvGespielt = pPartie.bvGespielt;
        charakterGespielt = pPartie.charakterGespielt;
        beginnCharakterphase = pPartie.beginnCharakterphase;
        beginnSF = pPartie.beginnSF;
        abgeworfenFuerVerzicht = pPartie.abgeworfenFuerVerzicht;
        sonderfunktion = pPartie.sonderfunktion;
        sfZaehler = pPartie.sfZaehler;
        sfVorrat.addAll(pPartie.sfVorrat);
        msg = pPartie.msg;
        gottaSend = pPartie.gottaSend;
        gottaShow = pPartie.gottaShow;
        sieger = pPartie.sieger;
        gameEnd = pPartie.gameEnd;
        gezogen = pPartie.gezogen;
        yinyangEffekt = pPartie.yinyangEffekt;
        flutEffekt = pPartie.flutEffekt;
        gottaShowKarten.addAll(pPartie.gottaShowKarten);
        gottaShowTitel = pPartie.gottaShowTitel;
        gottaOpen = pPartie.gottaOpen;
        gottaOpenNextSF = pPartie.gottaOpenNextSF;
    }
    //Springe zurück zum Beginn der Charakterphase
    private void wiederherstelleGameState(Partie pPartie){
        if (pPartie.aktiverSpieler == pPartie.ich) {
            ich = new Spieler(pPartie.ich);
            du = new Spieler(pPartie.du);
            aktiverSpieler = ich;
        } else {
            ich = new Spieler(pPartie.ich);
            du = new Spieler(pPartie.du);
            aktiverSpieler = ich;
        }
        ich.setGegner(du);
        du.setGegner(ich);
        spielzug = pPartie.spielzug;
        kampfzug = pPartie.kampfzug;
        phase = pPartie.phase;
        aktivesElement = pPartie.aktivesElement;
        aktionLimit = pPartie.aktionLimit;
        aktionenGespielt = pPartie.aktionenGespielt;
        charakterLimit = pPartie.charakterLimit;
        charaktereGespielt = pPartie.charaktereGespielt;
        verstaerkungLimit = pPartie.verstaerkungLimit;
        verstaerkungenGespielt = pPartie.verstaerkungenGespielt;
        einfachVLimit = pPartie.einfachVLimit;
        evGespielt = pPartie.evGespielt;
        bleibendVLimit = pPartie.bleibendVLimit;
        bvGespielt = pPartie.bvGespielt;
        charakterGespielt = pPartie.charakterGespielt;
        //beginnCharakterphase = pPartie.beginnCharakterphase; (Geht nur 1mal wenn unkommentiert)
        beginnSF = pPartie.beginnSF;
        abgeworfenFuerVerzicht = pPartie.abgeworfenFuerVerzicht;
        sonderfunktion = pPartie.sonderfunktion;
        sfZaehler = pPartie.sfZaehler;
        sfVorrat.addAll(pPartie.sfVorrat);
        msg = pPartie.msg;
        gottaSend = pPartie.gottaSend;
        gottaShow = pPartie.gottaShow;
        sieger = pPartie.sieger;
        gameEnd = pPartie.gameEnd;
        gezogen = pPartie.gezogen;
        yinyangEffekt = pPartie.yinyangEffekt;
        flutEffekt = pPartie.flutEffekt;
        gottaShowKarten.addAll(pPartie.gottaShowKarten);
        gottaShowTitel = pPartie.gottaShowTitel;
        gottaOpen = pPartie.gottaOpen;
        gottaOpenNextSF = pPartie.gottaOpenNextSF;
    }

    //METHODEN
    private void zugBeenden(){

        while(ichBinAmZug()){
            naechstePhase();
        }
    }
    void versucheZiehPhase(){
        boolean ziehen = true;
        if (aktiverSpieler.getGegner().hatAktivUndUnverbraucht(240)){ //Unwiderstehliche Sirenen
            drachenGewinnen(aktiverSpieler.getGegner(), 1);
        }
        if (aktiverSpieler.hatAktiv(49)) { //Talentierte Bardin
            sfZaehler=0;
            nextSF(49);
            ziehen = false;
        }
        if(aktiverSpieler.hatAktiv(195)) { //Meisterchronist
            nextSF(195);
            ziehen = false;
        }
        if (aktiverSpieler.hatAktiv(167)) { //Goblinräuber
            sfZaehler=0;
            nextSF(167);
            ziehen = false;
        }
        if (aktiverSpieler.hatAktiv(139)){ //Meuchling Delta
            nextSF(139);
            ziehen = false;
        }
        if (aktiverSpieler.hatAktiv(140)) { //Meuchling Echo
            nextSF(140);
            ziehen = false;
        }
        if (ich.hatAktiv(79)) { //Schamane des Jenseits
            nextSF(79);
            ziehen = false;
        }
        if(ich.hatAktiv(78)) { //Hexenmeister des Jenseits
            nextSF(78);
            ziehen = false;
        }
        nextSF(0);
        if (ziehen){
            ziehPhase();
        }
    }
    String getMsg(){
        return msg;
    }
    boolean isGameEnd(){ return gameEnd; }
    void setSonderfunktion(int n){sonderfunktion=n;}
    void setSfZaehler(int n){sfZaehler=n;}
    int getAktionenGespielt(){return aktionenGespielt;}
    //Führe als aktiver Spieler eine Aktion durch
    boolean aktion(int a) {
        if (gameEnd){
            //...
        } else if(sonderfunktion==0) {
            if (a == 0) { //OK
                if (phase != ZugPhase.ZIEH && phase != ZugPhase.ENDE && (!charakterGespielt||ich.hatAktiv(124))) { //Charakterphase oder früher, kein Charaktergespielt
                    if (kampfzug == 1) { //Verzicht auf Kampfbeginn
                        verzicht();
                        msg = "Verzicht auf Kampfbeginn";
                        return true;
                    } else {
                        msg = "Rückzug vom Kampf";
                        rueckzug(); //Rückzug
                        return true;
                    }
                } else if (phase == ZugPhase.CHARAKTER || phase == ZugPhase.VERSTAERKUNG) { //Zu Charakterphase
                    wiederherstelleGameState(beginnCharakterphase);
                    msg = "Rückkehr zum Beginn der Charakterphase";
                    return true;
                } else {
                    boolean beenden = true;
                    if (ich.hatAktiv(208)) { //Expeditionskarte
                        du.deckeHandAuf();
                    }
                    if (ich.hatAktiv(47)) { //Erfinderischer Tüftler
                        beenden = false;
                        nextSF(47);
                        msg = "EOT: Trigger eff von Tüftler";
                    }
                    if (ich.hatAktiv(81)) { //Druide des Lebens
                        beenden = false;
                        nextSF(81);
                    }
                    nextSF(0);
                    if (beenden) {
                        zugBeenden();
                        msg = "Ende des Zuges";
                    }
                    return true;
                }
            } else if (a == -2) { //FEUER
                if (feuerAnsagenErlaubt()) {
                    aktivesElement = Element.FEUER;
                    versucheZiehPhase();
                    msg = "Sage Feuer an";
                    return true;
                }
            } else if (a == -3) { //ERDE
                if (erdeAnsagenErlaubt()) {
                    aktivesElement = Element.ERDE;
                    versucheZiehPhase();
                    msg = "Sage Erde an";
                    return true;
                }
            } else if (
                    a+100 >= 0 && a+100 < ich.getHandSize()
                    && !flutEffekt
                    &&ichBinAmZug()
                    && (ich.hatAktiv(298) || ich.getHandKarte(a+100).hatSymbol(KartenSymbol.ERSETZEN, du))
                    && (phase==ZugPhase.BEGINN || phase==ZugPhase.AKTION)
                    && (aktionenGespielt<1 || (ich.hatAktiv(14)&&aktionenGespielt<2))
                    && !ich.hatAktiv(124)
            ){ //ERSETZBAR
                sonderfunktion=-5;
                sfZaehler=a+100;
                return true;
            } else if (a >= 1 && a <= ich.getHandSize() && !flutEffekt) { //KARTE SPIELEN //NICHT ERSETZBAR
                if (karteSpielenErlaubt(ich.getHandKarte(a - 1))) {
                    msg = "Spiele " + ich.getHandKarte(a - 1).getName();
                    spieleHandKarte(a - 1);
                    return true;
                }
            } else if ( //AUFNEHMSYMBL-KARTE NEHMEN
                    ichBinAmZug()
                    && phase==ZugPhase.BEGINN
                    && a-100>=0
                    && a-100<ich.getSpielzone().getSize()
                    && ich.getSpielzone().get(a-100).istAktiv()
                    && ich.getSpielzone().get(a-100).hatSymbol(KartenSymbol.AUFNEHMEN, du)
                    && (
                            ich.getSpielzone().get(a-100).getTyp()!=KartenTyp.CHARAKTER
                            || !du.hatAufnehmCharakter()
                        )
                    && !flutEffekt
            ) {
                msg = "Nehme " + ich.getSpielzone().get(a-100).getName() + "auf die Hand";
                ich.nehmeVonSpielzoneAufHand(a-100);
                return true;

            } else if( //SONDERFUNKTION AUF KARTE IN EIGENER SPIELZONE AKTIVIEREN
                    ichBinAmZug()
                    && phase!=ZugPhase.ZIEH && phase!=ZugPhase.ENDE
                    && a-100>=0
                    && a-100<ich.getSpielzone().getSize()
                    && ich.getSpielzone().get(a-100).istAktiv()
                    && !ich.getSpielzone().get(a-100).sfIgnoriert(du)
                    && !ich.getSpielzone().get(a-100).isSfVerbraucht()
                    && !flutEffekt
            ){
                Karte karte = ich.getSpielzone().get(a-100);
                int nummer = karte.getNummer();
                if(
                        nummer==136 //Meuchling Alpha
                        || nummer==137 //Meuchling Bravo
                        || nummer==146 //Improvisierte Schleuder
                ) {
                    merkeBeginnSF();
                    sonderfunktion = nummer;
                    return true;
                }
            } else if ( //SONDERFUNKTIONEN AUF KARTE IN GEGN. SPIELZONE AKTIVIEREN
                    ichBinAmZug()
                    && phase!=ZugPhase.ZIEH && phase!=ZugPhase.ENDE
                    && a-200>=0
                    && a-200<du.getSpielzone().getSize()
                    && du.getSpielzone().get(a-200).istAktiv()
                    && !du.getSpielzone().get(a-200).sfIgnoriert(ich)
                    && !du.getSpielzone().get(a-200).isSfVerbraucht()
                    && !flutEffekt
            ){
                Karte karte = du.getSpielzone().get(a-200);
                int nummer = karte.getNummer();
                if(
                        nummer==198 //Flammenwellen-Bombe
                        || nummer==199 //Erdaushöhler-Bombe
                        || nummer==200 //Tiefenwasser-Bombe
                        || nummer==201 //Lichtsucher-Bombe
                        || nummer==202 //Monduntergangs-Bombe
                        || nummer==240 //Unwiderstehliche Sirenen
                ) {
                    merkeBeginnSF();
                    sfZaehler=0;
                    sonderfunktion = nummer;
                    return true;
                }
            }
        //SONDERFUNKTION != 0
        } else if(sonderfunktion==-1 && ichBinAmZug()) { //Verzicht auf Kampfbeginn
            if (a >= 1 && a <= aktiverSpieler.getHandSize() && sfZaehler < 3) { //Karte abwerfen
                sfZaehler += 1;
                msg = "Werfe ab "+ich.getHandKarte(a-1).getName();
                aktiverSpieler.werfeHandkarteAb(a - 1);
                return true;
            } else if (a == 0 && sfZaehler >= 1) { //OK klicken
                verzichtDone();
                msg = "Bestätige Verzicht auf Kampfbeginn";
                return true;
            } else if (a==-1){ //Abbrechen klicken
                wiederherstelleGameState(beginnSF);
                msg = "Breche Verzicht auf Kampfbeginn ab";
                return true;
            }
        } else if (sonderfunktion==-2 && ichBinAmZug()) { //Rückzug vom Kamf
            if (a == 0) { //OK klicken
                rueckzugDone();
                msg = "Ziehe wirklich zurück";
                return true;
            } else if (a == -1) { //Abbrechen klicken
                wiederherstelleGameState(beginnSF);
                msg = "Ziehe doch nicht zurück";
                return true;
            }
        } else if ((sonderfunktion==-3||sonderfunktion==-4) && ichBinAmZug()) { //Startspieler Mulligan
            if (a == 0) {
                ich.mulliganMischen();
                wechselAktivenSpieler();
                if (sonderfunktion == -3) {
                    sonderfunktion = -4;
                } else {
                    ich.mulliganLeereSpielzone();
                    du.mulliganLeereSpielzone();
                    sonderfunktion = 0;
                    beginnCharakterphase = new Partie(this, false);
                    beginnDesZuges();
                }
                gottaSend = true;
                return true;
            } else if (
                    a - 1 >= 0
                            && a - 1 < ich.getHandSize()
            ) {
                boolean set = !ich.getHandKarte(a - 1).getSelected();
                ich.getHandKarte(a - 1).setSelected(set);
                return true;
            }
        } else if (sonderfunktion==-5 && ichBinAmZug()) { //Karte ersetzen oder spielen?
            if (a == -1) { //ersetzen
                ich.werfeHandkarteAb(sfZaehler);
                ziehe(1, ich);
                aktionenGespielt += 1;
                sonderfunktion = 0;
                sfZaehler = 0;
                return true;
            } else if (a == 0 && karteSpielenErlaubt(ich.getHandKarte(sfZaehler))) { //spielen
                msg = "Spiele " + ich.getHandKarte(sfZaehler).getName();
                spieleHandKarte(sfZaehler);
                sonderfunktion = 0;
                sfZaehler = 0;
                return true;
            }
        } else if (sonderfunktion==-6 && ichBinAmZug()){ //Nächste SF wählen
            if (
                    a-6000 >=0
                    && a-6000 < sfVorrat.size()
            ){
                nextSF(a);
                return true;
            }
        } else if (sonderfunktion==2 && ichBinAmZug()) { //Bezaubere den Heiligen Drachen (1)
            if (a == -1) { //NEIN
                sonderfunktion=0;
                msg = "Werfe keine Karten ab";
                return true;
            } else if (a == 0) { //OK
                merkeBeginnSF();
                sfZaehler=0;
                sonderfunktion += 1000;
                msg = "Mache bereit zum Karten abwerfen";
                return true;
            }
        } else if (sonderfunktion==1002 && ichBinAmZug()){ //Bezaubere den Heiligen Drachen (2)
            if (a==-1){ //Abbrechen
                wiederherstelleGameState(beginnSF);
                msg = "Breche sf ab";
                return true;
            } else if (a==0){ //OK
                if (sfZaehler>=8){
                    for (int i=ich.getHandSize()-1; i>=0; i--){
                        if (ich.getHandKarte(i).getSelected()){
                            ich.getHandKarte(i).setSelected(false);
                            ich.werfeHandkarteAb(i);
                        }
                    }
                    drachenGewinnen(ich, 1);
                    sfZaehler=0;
                    sonderfunktion=0;
                    msg = "sf durchgeführt, drache gewonnen";
                    return true;
                }
            } else if (
                    a-1>=0 &&
                    a-1<ich.getHandSize()
            ){
                boolean set = !ich.getHandKarte(a-1).getSelected();
                ich.getHandKarte(a-1).setSelected(set);
                //Zähle Handkarten
                sfZaehler=0;
                int selectedKarten=0;
                for(int i=0; i<ich.getHandSize(); i++){
                    Karte karte = ich.getHandKarte(i);
                    if (karte.getSelected()){
                        sfZaehler += karte.getFeuer(du);
                        selectedKarten += 1;
                    }
                }
                for (int i=0; i<selectedKarten; i++){
                    int sum=0;
                    int count=0;
                    for(int j=0; j<ich.getHandSize(); j++){
                        Karte karte = ich.getHandKarte(j);
                        if (karte.getSelected()){
                            if (i!=count) {
                                sum += karte.getFeuer(du);
                                if(sum>=8){break;}
                            }
                            count+=1;
                        }
                    }
                    if(sum>=8){
                        sfZaehler=0;
                        break;
                    }
                }
                msg = "klicke karte " + (a-1) + " an";
                return true;
            }
        } else if (sonderfunktion==3 && ichBinAmZug()){ //Vernichte den Feind
            if (a==-1) { //NEIN
                sonderfunktion=0;
                msg = "führe sf nicht durch";
                return true;
            } else if (a==0) { //JA
                du.werfeAlleAktivenVonSpielzoneAb(KartenTyp.BLEIBENDE_VERSTAERKUNG);
                sonderfunktion=0;
                msg = "führe sf durch";
                return true;
            }
        } else if (sonderfunktion==4 && ichBinAmZug()) { //Rufe den Meister (1)
            if (a == -1) { //Abbrechen
                sonderfunktion=0;
                msg = "führe sf nicht durch";
                return true;
            } else if (a == 0) { //OK
                sonderfunktion += 1000;
                msg = "führe sf durch";
                return true;
            }
        } else if (sonderfunktion==1004 && ichBinAmZug()){ //Rufe den Meister(2)
            if (a==-1) { //Abbrechen
                sonderfunktion -= 1000;
                msg = "Breche sf ab";
                return true;
            } else if (
                    a-100>=0 &&
                    a-100<ich.getSpielzone().getSize() &&
                    ich.getSpielzone().get(a-100).istAktiv() &&
                    (
                        ich.getSpielzone().get(a-100).getTyp()== KartenTyp.CHARAKTER ||
                        ich.getSpielzone().get(a-100).getTyp()== KartenTyp.EINFACHE_VERSTAERKUNG ||
                        ich.getSpielzone().get(a-100).getTyp()== KartenTyp.BLEIBENDE_VERSTAERKUNG
                    )
            ){
                ich.nehmeVonSpielzoneAufHand(a-100);
                sonderfunktion=0;
                msg = "nehme karte " + (a-100) + "auf die hand";
                return true;
            }
        } else if(
                (sonderfunktion==6 && !ichBinAmZug()) //Erwecke das Wissen (1)
                || (sonderfunktion==1006 && ichBinAmZug()) //Erwecke das Wissen (2)
        ){
            if (a==-1){ //Abbrechen
                sonderfunktion+=1000;
                if (sonderfunktion==2006){sonderfunktion=0;}
                msg = "breche ab";
                return true;
            } else if (
                a-100>=0 &&
                a-100<ich.getSpielzone().getSize() &&
                ich.getSpielzone().get(a-100).getTyp()== KartenTyp.EINFACHE_AKTION &&
                ich.getSpielzone().get(a-100).getNummer()!=6
            ){
                ich.mischeVonSpielzoneInNachziehstapel(a-100);
                sonderfunktion+=1000;
                if (sonderfunktion==2006){sonderfunktion=0;}
                msg = "mische karte " + (a-100) + " ins deck";
                gottaSend=true;
                return true;
            }
        } else if (sonderfunktion==15 && ichBinAmZug()) { //Pulverisierer-Novize(1)
            if (a==-1) { //Abbrechen
                sonderfunktion=0;
                return true;
            } else if (a==0) {
                sonderfunktion += 1000;
                return true;
            }
        } else if (sonderfunktion==1015 && ichBinAmZug()) { //Pulverisierer-Novize(2)
            if (a==-1){
                sonderfunktion-=1000;
                return true;
            } else if (
                    a-200>=0 &&
                    a-200<du.getSpielzone().getSize() &&
                    du.getSpielzone().get(a-200).istAktiv() &&
                    !du.getSpielzone().get(a-200).hatSymbol(KartenSymbol.GESCHUETZT,ich) &&
                    (
                        du.getSpielzone().get(a-200).getTyp()== KartenTyp.EINFACHE_VERSTAERKUNG
                        || du.getSpielzone().get(a-200).getTyp()== KartenTyp.BLEIBENDE_VERSTAERKUNG
                    )
            ){
                du.werfeVonSpielzoneAb(a-200);
                sonderfunktion=0;
                return true;
            }
        } else if (sonderfunktion==16 && ichBinAmZug()){ //Schleichschritt-Novize
            if (a==-1){ //Abbrechen
                nextSF(0);
                return true;
            } else if (a==0){ //OK
                ziehe(2, aktiverSpieler);
                nextSF(0);
                return true;
            }
        } else if (sonderfunktion==18 && ichBinAmZug()){ //Nichtigmacher-Magus
            if (a==-1){ //Abbrechen
                sonderfunktion=0;
                return true;
            } else if (a==0){ //OK
                du.nichtigMacherMagusEffekt();
                sonderfunktion=0;
                gottaSend=true;
                return true;
            }
        } else if ((sonderfunktion==27) && ichBinAmZug()){ //Eifrige Produktion
            if (a==-1){ //Abbrechen
                nextSF(0);
                return true;
            } else if (a==0){ //OK
                ziehe(1, aktiverSpieler);
                nextSF(0);
                return true;
            }
        } else if (sonderfunktion==32 && ichBinAmZug()){ //Erzürne den Heiligen Drachen (1)
            if(a==-1){
                sonderfunktion=0;
                return true;
            } else if (a==0){
                merkeBeginnSF();
                sfZaehler=0;
                sonderfunktion+=1000;
                return true;
            }
        } else if (sonderfunktion==1032 && ichBinAmZug()){ //Erzürne den Heiligen Drachen (2)
            if (a==-1){
                wiederherstelleGameState(beginnSF);
                return true;
            } else if (a==0){
                if(sfZaehler==2){
                    for (int i=ich.getHandSize()-1; i>=0; i--){
                        if (ich.getHandKarte(i).getSelected()){
                            ich.getHandKarte(i).setSelected(false);
                            ich.werfeHandkarteAb(i);
                        }
                    }
                    drachenGewinnen(ich, 1);
                    sfZaehler=0;
                    sonderfunktion=0;
                    return true;
                }
            } else if(
                    a-1>=0 &&
                    a-1<ich.getHandSize()
            ){
                boolean set = !ich.getHandKarte(a-1).getSelected();
                ich.getHandKarte(a-1).setSelected(set);
                //Zähle Handkarten
                sfZaehler=0;
                boolean nurAktion=true;
                boolean nurSchiff=true; //TODO
                for (int i=0; i<ich.getHandSize(); i++){
                    Karte karte = ich.getHandKarte(i);
                    if(karte.getSelected()){
                        sfZaehler+=1;
                        if(karte.getTyp()!= KartenTyp.EINFACHE_AKTION){
                            nurAktion=false;
                        }
                        //if(karte.getSubTyp()!=kartenSubtyp.SCHIFF){nurSchiff=false;} TODO
                        if (sfZaehler>2 || (!nurAktion)){
                            sfZaehler=0;
                            break;
                        }
                    }
                }
                return true;
            }
        } else if (sonderfunktion==33 && ichBinAmZug()){ //Sende Verstärkung
            if(a==-1){
                sonderfunktion=0;
                return true;
            } else if (a==0){
                ziehe(5, aktiverSpieler);
                sonderfunktion=0;
                return true;
            }
        } else if (sonderfunktion==35 && ichBinAmZug()){ //Vertreibe die Eindringlinge
            if (a==-1){
                sonderfunktion=0;
                return true;
            } else if(a==0){
                du.vertreibeEindringlingeEffekt();
                sonderfunktion=0;
                return true;
            }
        } else if (
                (sonderfunktion==37 && !ichBinAmZug()) //Erwecke die Demut (1)
                || (sonderfunktion==1037 && ichBinAmZug()) //Erwecke die Demut (2)
        ){
            if (sfZaehler<3 && sfZaehler==ich.getHandSize()){
                sfZaehler=3;
            }
            if (a==0 && sfZaehler==3){
                for (int i=ich.getHandSize()-1; i>=0; i--){
                    if (ich.getHandKarte(i).getSelected()){
                        ich.getHandKarte(i).setSelected(false);
                        ich.werfeHandkarteAb(i);
                    }
                }
                sfZaehler=0;
                sonderfunktion+=1000;
                if (sonderfunktion==2037){
                    sonderfunktion=0;
                }
                return true;
            } else if (a-1>=0 && a-1<ich.getHandSize()){
                sfZaehler=0;
                boolean set = !ich.getHandKarte(a-1).getSelected();
                ich.getHandKarte(a-1).setSelected(set);
                //Zähle Handkarten
                for (int i=0; i<ich.getHandSize(); i++){
                    if(ich.getHandKarte(i).getSelected()){
                        sfZaehler+=1;
                    }
                }
                /*
                if (sfZaehler<3 && sfZaehler==ich.getHandSize()){
                    sfZaehler=3;*/
                if (sfZaehler>3){
                    sfZaehler=0;
                }
                return true;
            }
        } else if (sonderfunktion==47 && ichBinAmZug()){ //Erfinderischer Tüftler
            if(a==-1){
                nextSF(0);
                return true;
            } else if (a==0){
                zieheAuf(9, aktiverSpieler);
                nextSF(0);
                return true;
            }
        } else if (sonderfunktion==49 && ichBinAmZug()){ //Talentierte Bardin
            if(a==-1) { //Abbrechen
                sfZaehler = 0;
                nextSF(0);
                return true;
            } else if (a==0 && sfZaehler<=2){ //Abwerfen
                for (int i=du.getHandSize()-1; i>=0; i--){
                    if(du.getHandKarte(i).getSelected()){
                        du.getHandKarte(i).setSelected(false);
                        du.werfeHandkarteAb(i);
                    }
                }
                sfZaehler=0;
                nextSF(0);
                return true;
            } else if ( //Karte wählen
                    a-3000>=0 && a-3000<du.getHandSize()
            ){
                boolean set = !du.getHandKarte(a-3000).getSelected();
                du.getHandKarte(a-3000).setSelected(set);
                sfZaehler=0;
                for (int i=0; i<du.getHandSize(); i++){
                    if(du.getHandKarte(i).getSelected()){
                        sfZaehler+=1;
                    }
                }
                return true;
            }
        } else if (sonderfunktion==52 && ichBinAmZug()){ //Relikt der Segnung
            if(a==-1){
                sonderfunktion=0;
                return true;
            } else if (a==0){
                sonderfunktion+=1000;
                return true;
            }
        } else if (sonderfunktion==1052 && ichBinAmZug()){ //Relikt der Segnung
            if(a==-1){
                sonderfunktion-=1000;
                return true;
            } else if (
                    a-200 >= 0
                    && a-200 < du.getSpielzone().getSize()
                    && du.getSpielzone().get(a-200).istAktiv()
                    && !du.getSpielzone().get(a-200).hatSymbol(KartenSymbol.GESCHUETZT,ich)
            ){
                Karte karte = du.getSpielzone().get(a-200);
                boolean abwerfen = false;
                if(
                    karte.getTyp()== KartenTyp.EINFACHE_VERSTAERKUNG
                    || karte.getTyp()== KartenTyp.BLEIBENDE_VERSTAERKUNG
                ){abwerfen=true;}
                if(karte.getTyp()== KartenTyp.CHARAKTER){
                    for(int i=0; i<du.getSpielzone().getSize(); i++){
                        if(i!=a-200 && du.getSpielzone().get(i).getTyp()== KartenTyp.CHARAKTER && du.getSpielzone().get(i).istAktiv()){
                            abwerfen = true;
                            break;
                        }
                    }
                }
                if (abwerfen){
                    du.werfeVonSpielzoneAb(a-200);
                    sonderfunktion=0;
                    return true;
                }
            }
        } else if (sonderfunktion==62 && ichBinAmZug()){ //Rufe den Geist der Ahnen (1)
            if (a==-1){
                sonderfunktion=0;
                return true;
            } else if (a==0){
                sonderfunktion+=1000;
                return true;
            }
        } else if (sonderfunktion==1062 && ichBinAmZug()){ //Rufe den Geiste der Ahnen(2)
            if (a==-1){
                sonderfunktion-=1000;
                return true;
            } else if (
                    a-1000>=0 && a-1000<ich.getAbwurfzone().getSize()
            ){
                ich.nehmeVonAbwurfzoneAufHand(a-1000);
                sonderfunktion=0;
                return true;
            }
        } else if (sonderfunktion==63 && ichBinAmZug()) { //Rufe den Geist der Schlacht (1)
            if (a==-1){
                sonderfunktion=0;
                return true;
            } else if (a==0){
                sonderfunktion+=1000;
                return true;
            }
        } else if (sonderfunktion==1063 && ichBinAmZug()){ //Rufe den Geist der Schlacht (2)
            if (a==-1){
                sonderfunktion-=1000;
                return true;
            } else if (
                    a-100 >= 0
                    && a-100 < ich.getSpielzone().getSize()
                    && (    ich.getSpielzone().get(a-100).getTyp()== KartenTyp.CHARAKTER
                            || ich.getSpielzone().get(a-100).getTyp()== KartenTyp.EINFACHE_VERSTAERKUNG
                            || ich.getSpielzone().get(a-100).getTyp()== KartenTyp.BLEIBENDE_VERSTAERKUNG
                    )
            ){
                ich.nehmeVonSpielzoneAufHand(a-100);
                sonderfunktion=0;
                return true;
            }
        } else if (sonderfunktion==78 && ichBinAmZug()){ //Hexenmeister des Jenseits
            if(a==-1){
                nextSF(0);
                return true;
            } else if (
                    !du.hatAktiv(29) //(Explosive Sabotage)
                    && a-4000 >= 0
                    && a-4000 < ich.getNachziehSize()
            ){
                //TODO vorzeigen evtl in extra window
                ich.nehmeVonNachziehAufDieHand(a-4000);
                ich.mischeNachziehstapel();
                gottaSend=true;
                nextSF(0);
                return true;
            }
        } else if (sonderfunktion==79 && ichBinAmZug()){ //Schamane des Jenseits
            if(a==-1){
                nextSF(0);
                return true;
            } else if(a==0){
                ich.SchamaneDesJenseitsEffekt();
                gottaSend=true;
                //TODO evtl vorzeigen
                nextSF(0);
                return true;
            }
        } else if (sonderfunktion==80 && ichBinAmZug()) { //Priester des Lebens (1)
            if (a==-1){
                nextSF(0);
                return true;
            } else if(a==0){
                ziehe(3, aktiverSpieler);
                if(ich.getHandSize()==0){
                    sfZaehler=2;
                } else {
                    sfZaehler=0;
                }
                sonderfunktion+=1000;
                return true;
            }
        } else if (sonderfunktion==1080 && ichBinAmZug()){ //Priester des Lebens (2)
            if (a==0 && sfZaehler==2){
                for(int i=ich.getHandSize()-1; i>=0; i--){
                    if(ich.getHandKarte(i).getSelected()){
                        ich.getHandKarte(i).setSelected(false);
                        ich.mischeVonHandInNachziehstapel(i);
                    }
                }
                gottaSend=true;
                nextSF(0);
                sfZaehler=0;
                return true;
            } else if (a-1>=0 && a-1<ich.getHandSize()){
                sfZaehler=0;
                boolean set = !ich.getHandKarte(a-1).getSelected();
                ich.getHandKarte(a-1).setSelected(set);
                //Zähle Handkarten
                for (int i=0; i<ich.getHandSize(); i++){
                    if(ich.getHandKarte(i).getSelected()){
                        sfZaehler+=1;
                    }
                }
                if(sfZaehler>2)
                    sfZaehler=0;
                if(sfZaehler<2 && sfZaehler==ich.getHandSize()){
                    sfZaehler=2;
                }
                return true;
            }
        } else if (sonderfunktion==81 && ichBinAmZug()) { //Druide des Lebens (1)
            if (a==-1){
                nextSF(0);
                return true;
            } else if(a==0){
                if(ich.getNachziehSize()==0){
                    sfZaehler=2;
                } else {
                    sfZaehler=0;
                }
                sonderfunktion+=1000;
                return true;
            }
        } else if (sonderfunktion==1081 && ichBinAmZug()){ //Druide des Lebens (2)
            if (a==0 && sfZaehler==2){
                for(int i=ich.getNachziehstapel().getSize()-1; i>=0; i--){
                    if(ich.getNachziehstapel().get(i).getSelected()){
                        ich.getNachziehstapel().get(i).setSelected(false);
                        ich.werfeVonNachziehAb(i);
                    }
                }
                ziehe(2, ich);
                nextSF(0);
                //TODO vorgezeigte und abgeworfene/gezgene kartenin extrafenster zeigen (evtl)
                sfZaehler=0;
                return true;
            } else if (
                    a-4000>=0
                    && a-4000<ich.getNachziehSize()
                    && a-4000<=ich.getNachziehSize()-1
                    && a-4000>=ich.getNachziehSize()-4
            ){
                sfZaehler=0;
                boolean set = !ich.getNachziehstapel().get(a-4000).getSelected();
                ich.getNachziehstapel().get(a-4000).setSelected(set);
                //Zähle Handkarten
                for (int i=0; i<ich.getNachziehSize(); i++){
                    if(ich.getNachziehstapel().get(i).getSelected()){
                        sfZaehler+=1;
                    }
                }
                if(sfZaehler>2)
                    sfZaehler=0;
                if(sfZaehler<2 && sfZaehler==ich.getNachziehSize()){
                    sfZaehler=2;
                }
                return true;
            }
        } else if (sonderfunktion==92 && ichBinAmZug()){ //Täusche den Heiligen Drachen
            if(a==-1){
                wiederherstelleGameState(beginnSF);
                return true;
            } else if (a==0 && sfZaehler==3){
                for(int i=ich.getHandSize()-1; i>=0; i--){
                    if(ich.getHandKarte(i).getSelected()){
                        ich.getHandKarte(i).setSelected(false);
                        ich.werfeHandkarteAb(i);
                    }
                }
                drachenGewinnen(ich, 1);
                sonderfunktion=0;
                sfZaehler=0;
                return true;
            } else if (
                    a-1 >= 0
                    && a-1 < ich.getHandSize()
            ){
                boolean set = !ich.getHandKarte(a-1).getSelected();
                ich.getHandKarte(a-1).setSelected(set);
                //zähle karten
                sfZaehler=0;
                for(int i=0; i<ich.getHandSize(); i++){
                    if (ich.getHandKarte(i).getSelected()) {
                        if (ich.getHandKarte(i).getTyp() == KartenTyp.CHARAKTER) {
                            sfZaehler += 1;
                        } else {
                            sfZaehler = 0;
                            break;
                        }
                    }
                }
                if(sfZaehler!=3){sfZaehler=0;}
                return true;
            }
        } else if (sonderfunktion==101 && ichBinAmZug()){ //Kratzerhexer
            if (a==-1){
                sonderfunktion=0;
                return true;
            } else {
                if(
                        a-200 >= 0
                        && a-200 < du.getSpielzone().getSize()
                        && du.getSpielzone().get(a-200).getTyp() == KartenTyp.CHARAKTER
                        && du.getSpielzone().get(a-200).istAktiv()
                        && !du.getSpielzone().get(a-200).hatSymbol(KartenSymbol.GESCHUETZT,ich)
                ){
                    du.werfeVonSpielzoneAb(a-200);
                    sonderfunktion=0;
                    return true;
                }
            }
        } else if (sonderfunktion==122 && ichBinAmZug()) { //Rekrutiere einen Krieger
            if (a==-1){
                sonderfunktion=0;
                return true;
            } else if (
                            !du.hatAktiv(29) //(Explosive Sabotage)
                            && a-4000 >= 0
                            && a-4000 < ich.getNachziehSize()
            ){
                gottaShow=1; //Du
                gottaShowTitel="Rekrutiere einen Krieger (Zug "+spielzug+")";
                gottaShowKarten.add(ich.getNachziehstapel().get(a-4000).getNummer());

                ich.nehmeVonNachziehAufDieHand(a-4000);
                ich.mischeNachziehstapel();
                gottaSend=true;
                sonderfunktion=0;
                return true;
            }
        } else if (sonderfunktion==123 && ichBinAmZug()){ //Ehre die Gefallenen
            if(a==-1){
                sonderfunktion=0;
                return true;
            } else if (a==0){
                ich.ehreDieGefallenenEffekt();
                gottaSend=true;
                sonderfunktion=0;
                return true;
            }
        } else if (sonderfunktion==136 && ichBinAmZug()){ //Meuchling Alpha
            if (a==-1){
                sonderfunktion=0;
                return true;
            } else if (
                    a-200>=0 &&
                    a-200<du.getSpielzone().getSize() &&
                    du.getSpielzone().get(a-200).istAktiv() &&
                    (
                        du.getSpielzone().get(a-200).getTyp()== KartenTyp.EINFACHE_VERSTAERKUNG
                        || du.getSpielzone().get(a-200).getTyp()== KartenTyp.BLEIBENDE_VERSTAERKUNG
                    )
                    && !du.getSpielzone().get(a-200).hatSymbol(KartenSymbol.GESCHUETZT,ich)
            ){
                du.werfeVonSpielzoneAb(a-200);
                ich.verbrauche(136);
                sonderfunktion=0;
                return true;
            }
        } else if (sonderfunktion==137 && ichBinAmZug()){ //Meuchling Bravo
            if (a==-1){
                sonderfunktion=0;
                return true;
            } else if (
                    a-200>=0
                    && a-200<du.getSpielzone().getSize()
                    && du.getSpielzone().get(a-200).istAktiv()
                    && !du.getSpielzone().get(a-200).hatSymbol(KartenSymbol.GESCHUETZT,ich)
            ){
                Karte karte = du.getSpielzone().get(a-200);
                boolean abwerfen = false;
                if(karte.hatSymbole()){abwerfen=true;}
                if(abwerfen && karte.getTyp()== KartenTyp.CHARAKTER){
                    abwerfen=false;
                    for(int i=0; i<du.getSpielzone().getSize(); i++){
                        if(i!=a-200 && du.getSpielzone().get(i).getTyp()== KartenTyp.CHARAKTER && du.getSpielzone().get(i).istAktiv()){
                            abwerfen = true;
                            break;
                        }
                    }
                }
                if (abwerfen){
                    du.werfeVonSpielzoneAb(a-200);
                    ich.verbrauche(137);
                    sonderfunktion=0;
                    return true;
                }
            }
        } else if (sonderfunktion==138 && ichBinAmZug()){ //Meuchling Charlie
            if (a==-1){
                nextSF(0);
                return true;
            } else if (a==0){
                ziehe(1, ich);
                nextSF(0);
                return true;
            }
        } else if (sonderfunktion==139 && ichBinAmZug()){ //Meuchling Delta (1)
            if (a==-1){
                nextSF(0);
                return true;
            } else if (a==0 && du.getHandSize()>=1){
                sonderfunktion += 1000;
                return true;
            }
        } else if (sonderfunktion==1139 && !ichBinAmZug()){ //Meuchling Delta (2)
            if(a-1>=0 && a-1<ich.getHandSize()){
                ich.werfeHandkarteAb(a-1);
                nextSF(0);
                return true;
            }
        } else if (sonderfunktion==140 && ichBinAmZug()){ //Meuchling Echo
            if (a==-1){
                nextSF(0);
                return true;
            } else if (a==0 && du.getNachziehSize()>=1){
                du.werfeVonNachziehAb(du.getNachziehSize()-1);
                nextSF(0);
                return true;
            }
        } else  if (sonderfunktion==145 && ichBinAmZug()){ //Ganzling
            if(a==0){
                sonderfunktion=0;
                return true;
            } else if (a==-1){
                aktiverSpieler.deaktiviereAlle(KartenTyp.CHARAKTER);
                aktiverSpieler.deaktiviereAlle(KartenTyp.EINFACHE_VERSTAERKUNG);
                ich.getSpielzone().get(ich.getSpielzone().getSize()-1).aktiviere();
                sonderfunktion=0;
                return true;
            }
        } else if (sonderfunktion==146 && ichBinAmZug()){ //Improvisierte Schleuder
            if (a == 0) {
                sonderfunktion=0;
                return true;
            } else if (a==-1){
                wiederherstelleGameState(beginnSF);
                return true;
            } else if (
                    a-100>=0
                    && a-100<ich.getSpielzone().getSize()
                    && ich.getSpielzone().get(a-100).istAktiv()
                    && (
                            ich.getSpielzone().get(a-100).getTyp()==KartenTyp.CHARAKTER
                            || ich.getSpielzone().get(a-100).getTyp()==KartenTyp.EINFACHE_VERSTAERKUNG
                            || ich.getSpielzone().get(a-100).getTyp()==KartenTyp.BLEIBENDE_VERSTAERKUNG
                    )
            ){
                ich.werfeVonSpielzoneAb(a-100);
                return true;
            }
        } else if (sonderfunktion==152 && ichBinAmZug()) { //Beschenke den Heiligen Drachen
            if (a == -1) {
                wiederherstelleGameState(beginnSF);
                return true;
            } else if (a == 0 && sfZaehler >= 8) {
                for (int i = ich.getHandSize() - 1; i >= 0; i--) {
                    if (ich.getHandKarte(i).getSelected()) {
                        ich.getHandKarte(i).setSelected(false);
                        ich.werfeHandkarteAb(i);
                    }
                }
                drachenGewinnen(ich, 1);
                sfZaehler = 0;
                sonderfunktion = 0;
                return true;
            } else if (
                    a - 1 >= 0
                            && a - 1 < ich.getHandSize()
            ) {
                boolean set = !ich.getHandKarte(a - 1).getSelected();
                ich.getHandKarte(a - 1).setSelected(set);
                //Zähle Handkarten
                sfZaehler = 0;
                int selectedKarten = 0;
                for (int i = 0; i < ich.getHandSize(); i++) {
                    Karte karte = ich.getHandKarte(i);
                    if (karte.getSelected()) {
                        sfZaehler += karte.getErde(du);
                        selectedKarten += 1;
                    }
                }
                for (int i = 0; i < selectedKarten; i++) {
                    int sum = 0;
                    int count = 0;
                    for (int j = 0; j < ich.getHandSize(); j++) {
                        Karte karte = ich.getHandKarte(j);
                        if (karte.getSelected()) {
                            if (i != count) {
                                sum += karte.getErde(du);
                                if (sum >= 8) {
                                    break;
                                }
                            }
                            count += 1;
                        }
                    }
                    if (sum >= 8) {
                        sfZaehler = 0;
                        break;
                    }
                }
                return true;
            }
        } else if (sonderfunktion==153 && ichBinAmZug()) { //Plündere das Dorf
            if (a == -1) {
                sonderfunktion = 0;
                return true;
            } else if (a == 0) {
                du.werfeAlleVonSpielzoneAb(KartenTyp.CHARAKTER);
                du.werfeAlleVonSpielzoneAb(KartenTyp.EINFACHE_VERSTAERKUNG);
                sonderfunktion = 0;
                return true;
            }
        } else if (sonderfunktion==167 && ichBinAmZug()) { //Goblinräuber (1)
            if (a==-1) {
                nextSF(0);
                return true;
            } else if (a==0 && du.getHandSize()>=1) {
                sfZaehler=0;
                sonderfunktion += 1000;
                return true;
            }
        } else if (sonderfunktion==1167 && !ichBinAmZug()) { //Goblinräuber (2)
            if (a == 0 && sfZaehler == 2) {
                for (int i = ich.getHandSize() - 1; i >= 0; i--) {
                    if (ich.getHandKarte(i).getSelected()) {
                        ich.getHandKarte(i).setSelected(false);
                        ich.werfeHandkarteAb(i);
                    }
                }
                sfZaehler = 0;
                nextSF(0);
                return true;
            } else if (a - 1 >= 0 && a - 1 < ich.getHandSize()) {
                sfZaehler = 0;
                boolean set = !ich.getHandKarte(a - 1).getSelected();
                ich.getHandKarte(a - 1).setSelected(set);
                //Zähle Handkarten
                for (int i = 0; i < ich.getHandSize(); i++) {
                    if (ich.getHandKarte(i).getSelected()) {
                        sfZaehler += 1;
                    }
                }
                if (sfZaehler > 2)
                    sfZaehler = 0;
                if (sfZaehler < 2 && sfZaehler == ich.getHandSize())
                    sfZaehler = 2;
                return true;
            }
        } else if (sonderfunktion==171 && ichBinAmZug()) { //Schwächlinggranate
            if (a == -1) {
                sonderfunktion = 0;
                return true;
            } else if (
                    a - 200 >= 0
                            && a - 200 < du.getSpielzone().getSize()
                            && du.getSpielzone().get(a - 200).istAktiv()
                            && !du.getSpielzone().get(a-200).hatSymbol(KartenSymbol.GESCHUETZT,ich)
                            && (
                                du.getSpielzone().get(a - 200).getTyp() == KartenTyp.EINFACHE_VERSTAERKUNG
                                || du.getSpielzone().get(a - 200).getTyp() == KartenTyp.BLEIBENDE_VERSTAERKUNG
                            )
            ) {
                du.werfeVonSpielzoneAb(a - 200);
                sonderfunktion = 0;
                return true;
            }
        } else if (sonderfunktion==182 && ichBinAmZug()) { //Versuche den Heiligen Drachen
            if (a == -1) {
                wiederherstelleGameState(beginnSF);
                return true;
            } else if (a == 0 && sfZaehler >= 5) {
                for (int i = ich.getHandSize() - 1; i >= 0; i--) {
                    if (ich.getHandKarte(i).getSelected()) {
                        ich.getHandKarte(i).setSelected(false);
                        ich.werfeHandkarteAb(i);
                    }
                }
                drachenGewinnen(ich, 1);
                sfZaehler = 0;
                sonderfunktion = 0;
                return true;
            } else if (
                    a - 1 >= 0
                            && a - 1 < ich.getHandSize()
            ) {
                boolean set = !ich.getHandKarte(a - 1).getSelected();
                ich.getHandKarte(a - 1).setSelected(set);
                //Zähle Handkarten
                sfZaehler = 0;
                int selectedKarten = 0;
                for (int i = 0; i < ich.getHandSize(); i++) {
                    Karte karte = ich.getHandKarte(i);
                    if (karte.getSelected()) {
                        sfZaehler += karte.getGold();
                        selectedKarten += 1;
                    }
                }
                for (int i = 0; i < selectedKarten; i++) {
                    int sum = 0;
                    int count = 0;
                    for (int j = 0; j < ich.getHandSize(); j++) {
                        Karte karte = ich.getHandKarte(j);
                        if (karte.getSelected()) {
                            if (i != count) {
                                sum += karte.getGold();
                                if (sum >= 5)
                                    break;
                            }
                            count += 1;
                        }
                    }
                    if (sum >= 5) {
                        sfZaehler = 0;
                        break;
                    }
                }
                return true;
            }
        } else if ((sonderfunktion==184 && !ichBinAmZug()) || (sonderfunktion==1184 && ichBinAmZug())) { //Erwecke die Gier
            if (a == -1) {
                for (int i = 0; i < ich.getHandSize(); i++) {
                    ich.getHandKarte(i).setSelected(false);
                }
                sfZaehler = 0;
                if (sonderfunktion == 184) sonderfunktion += 1000;
                else sonderfunktion = 0;
                return true;
            } else if (a == 0 && sfZaehler == 1) {
                for (int i = ich.getHandSize() - 1; i >= 0; i--) {
                    if (ich.getHandKarte(i).getSelected()) {
                        ich.getHandKarte(i).setSelected(false);
                        ich.werfeHandkarteAb(i);
                    }
                }
                drachenGewinnen(ich, 1);
                sfZaehler = 0;
                if (sonderfunktion == 184) sonderfunktion += 1000;
                else sonderfunktion = 0;
                return true;
            } else if (a - 1 >= 0 && a - 1 < ich.getHandSize()) {
                sfZaehler = 0;
                boolean set = !ich.getHandKarte(a - 1).getSelected();
                ich.getHandKarte(a - 1).setSelected(set);
                //1) Selektierte Handkarten zählen
                int selectedKarten = 0;
                for (int i = 0; i < ich.getHandSize(); i++) {
                    if (ich.getHandKarte(i).getSelected())
                        selectedKarten += 1;
                }
                //2) Überprüfen
                if (FeuerErdeAufSeperatenKarten(selectedKarten, 2)) {
                    sfZaehler = 1;
                }
                return true;
            }
        } else if (sonderfunktion==194 && ichBinAmZug()) { //Kriegskunstmeister
            if (a == -1) {
                sonderfunktion = 0;
                return true;
            } else if (
                    a - 100 >= 0
                            && a - 100 < ich.getSpielzone().getSize()
            ) {
                Karte karte = ich.getSpielzone().get(a - 100);
                if (
                        (karte.getTyp() == KartenTyp.CHARAKTER || karte.getTyp() == KartenTyp.EINFACHE_VERSTAERKUNG)
                                && karte.getNummer() != 194
                ) {
                    ich.nehmeVonSpielzoneAufHand(a - 100);
                    sonderfunktion = 0;
                    return true;
                }
            }
        } else if (sonderfunktion==195 && ichBinAmZug()) { //Meisterchronist
            if (a == -1) {
                nextSF(0);
                return true;
            } else if (
                    a - 3000 >= 0
                            && a - 3000 < du.getHandSize()
            ) {
                gottaShow=2; //Beide
                gottaShowTitel = "Meisterchronist (Zug "+spielzug+")";
                gottaShowKarten.add(du.getHandKarte(a-3000).getNummer());

                du.werfeHandkarteAb(a - 3000);
                nextSF(0);
                return true;
            }
        } else if (sonderfunktion==198 && ichBinAmZug()) { //Flammenwellen-Bombe
            if (a == -1) {
                for (int i = 0; i < ich.getHandSize(); i++) {
                    ich.getHandKarte(i).setSelected(false);
                }
                sonderfunktion = 0;
                return true;
            } else if (a == 0 && sfZaehler >= 3) {
                for (int i = ich.getHandSize() - 1; i >= 0; i--) {
                    if (ich.getHandKarte(i).getSelected()) {
                        ich.getHandKarte(i).setSelected(false);
                        ich.werfeHandkarteAb(i);
                    }
                }
                du.verbrauche(198);
                sfZaehler=0;
                sonderfunktion = 0;
                return true;
            } else if (
                    a - 1 >= 0
                            && a - 1 < ich.getHandSize()
            ) {
                int mussErreichen = 3;
                if (du.hatAktiv(196)) {//explosionsmeister
                    for (int i = du.getSpielzone().getSize() - 1; i >= 0; i--) {
                        int nr = du.getSpielzone().get(i).getNummer();
                        if (nr >= 198 && nr <= 202) {
                            if (nr == sonderfunktion)
                                mussErreichen *= 2;
                            break;
                        }
                    }
                }
                boolean set = !ich.getHandKarte(a - 1).getSelected();
                ich.getHandKarte(a - 1).setSelected(set);
                //Zähle Karten
                sfZaehler = 0;
                int selectedKarten = 0;
                for (int i = 0; i < ich.getHandSize(); i++) {
                    Karte karte = ich.getHandKarte(i);
                    if (karte.getSelected()) {
                        sfZaehler += karte.getFeuer(du);
                        selectedKarten += 1;
                    }
                }
                if (sfZaehler >= mussErreichen)
                    sfZaehler = 3;
                else
                    sfZaehler = 0;
                for (int i = 0; i < selectedKarten; i++) {
                    int sum = 0;
                    int count = 0;
                    for (int j = 0; j < ich.getHandSize(); j++) {
                        Karte karte = ich.getHandKarte(j);
                        if (karte.getSelected()) {
                            if (i != count) {
                                sum += karte.getFeuer(du);
                                if (sum >= mussErreichen) break;
                            }
                            count += 1;
                        }
                    }
                    if (sum >= mussErreichen) {
                        sfZaehler = 0;
                        break;
                    }
                }
                return true;
            }
        } else if (sonderfunktion==199 && ichBinAmZug()) { //Erdaushöhler-Bombe
            if (a == -1) {
                for (int i = 0; i < ich.getHandSize(); i++) {
                    ich.getHandKarte(i).setSelected(false);
                }
                sonderfunktion = 0;
                return true;
            } else if (a == 0 && sfZaehler >= 3) {
                for (int i = ich.getHandSize() - 1; i >= 0; i--) {
                    if (ich.getHandKarte(i).getSelected()) {
                        ich.getHandKarte(i).setSelected(false);
                        ich.werfeHandkarteAb(i);
                    }
                }
                du.verbrauche(199);
                sfZaehler=0;
                sonderfunktion = 0;
                return true;
            } else if (
                    a - 1 >= 0
                            && a - 1 < ich.getHandSize()
            ) {
                int mussErreichen = 3;
                if (du.hatAktiv(196)) {//explosionsmeister
                    for (int i = du.getSpielzone().getSize() - 1; i >= 0; i--) {
                        int nr = du.getSpielzone().get(i).getNummer();
                        if (nr >= 198 && nr <= 202) {
                            if (nr == sonderfunktion)
                                mussErreichen *= 2;
                            break;
                        }
                    }
                }
                boolean set = !ich.getHandKarte(a - 1).getSelected();
                ich.getHandKarte(a - 1).setSelected(set);
                //Zähle Karten
                sfZaehler = 0;
                int selectedKarten = 0;
                for (int i = 0; i < ich.getHandSize(); i++) {
                    Karte karte = ich.getHandKarte(i);
                    if (karte.getSelected()) {
                        sfZaehler += karte.getErde(du);
                        selectedKarten += 1;
                    }
                }
                if (sfZaehler >= mussErreichen)
                    sfZaehler = 3;
                else
                    sfZaehler = 0;
                for (int i = 0; i < selectedKarten; i++) {
                    int sum = 0;
                    int count = 0;
                    for (int j = 0; j < ich.getHandSize(); j++) {
                        Karte karte = ich.getHandKarte(j);
                        if (karte.getSelected()) {
                            if (i != count) {
                                sum += karte.getErde(du);
                                if (sum >= mussErreichen) break;
                            }
                            count += 1;
                        }
                    }
                    if (sum >= mussErreichen) {
                        sfZaehler = 0;
                        break;
                    }
                }
                return true;
            }
        } else if (sonderfunktion==200 && ichBinAmZug()) { //Tiefenwasserbombe
            if (a == -1) {
                for (int i = 0; i < ich.getHandSize(); i++) {
                    ich.getHandKarte(i).setSelected(false);
                }
                sonderfunktion = 0;
                return true;
            } else if (a == 0 && sfZaehler == 1) {
                for (int i = ich.getHandSize() - 1; i >= 0; i--) {
                    if (ich.getHandKarte(i).getSelected()) {
                        ich.getHandKarte(i).setSelected(false);
                        ich.werfeHandkarteAb(i);
                    }
                }
                du.verbrauche(200);
                sfZaehler = 0;
                sonderfunktion = 0;
                return true;
            } else if (
                    a - 1 >= 0
                            && a - 1 < ich.getHandSize()
            ) {
                int mussErreichen = 2;
                if (du.hatAktiv(196)) {//explosionsmeister
                    for (int i = du.getSpielzone().getSize() - 1; i >= 0; i--) {
                        int nr = du.getSpielzone().get(i).getNummer();
                        if (nr >= 198 && nr <= 202) {
                            if (nr == sonderfunktion)
                                mussErreichen *= 2;
                            break;
                        }
                    }
                }
                sfZaehler = 0;
                boolean set = !ich.getHandKarte(a - 1).getSelected();
                ich.getHandKarte(a - 1).setSelected(set);
                //1) Selektierte Handkarten zählen
                int selectedKarten = 0;
                for (int i = 0; i < ich.getHandSize(); i++) {
                    if (ich.getHandKarte(i).getSelected())
                        selectedKarten += 1;
                }
                //2) Überprüfen
                if (FeuerErdeAufSeperatenKarten(selectedKarten, mussErreichen)) {
                    sfZaehler = 1;
                }
                return true;
            }
        } else if (sonderfunktion==201 && ichBinAmZug()) { //Lichtsucher-Bombe
            if (a == -1) {
                for (int i = 0; i < ich.getHandSize(); i++) {
                    ich.getHandKarte(i).setSelected(false);
                }
                sonderfunktion = 0;
                return true;
            } else if (a == 0 && sfZaehler >= 5) {
                for (int i = ich.getHandSize() - 1; i >= 0; i--) {
                    if (ich.getHandKarte(i).getSelected()) {
                        ich.getHandKarte(i).setSelected(false);
                        ich.werfeHandkarteAb(i);
                    }
                }
                du.verbrauche(201);
                sfZaehler = 0;
                sonderfunktion = 0;
                return true;
            } else if (
                    a - 1 >= 0
                            && a - 1 < ich.getHandSize()
            ) {
                int mussErreichen = 5;
                if (du.hatAktiv(196)) {//explosionsmeister
                    for (int i = du.getSpielzone().getSize() - 1; i >= 0; i--) {
                        int nr = du.getSpielzone().get(i).getNummer();
                        if (nr >= 198 && nr <= 202) {
                            if (nr == sonderfunktion)
                                mussErreichen *= 2;
                            break;
                        }
                    }
                }
                boolean set = !ich.getHandKarte(a - 1).getSelected();
                ich.getHandKarte(a - 1).setSelected(set);
                //Zähle Karten
                int erde = 0;
                int feuer = 0;
                int selectedKarten = 0;
                for (int i = 0; i < ich.getHandSize(); i++) {
                    Karte karte = ich.getHandKarte(i);
                    if (karte.getSelected()) {
                        feuer += karte.getFeuer(du);
                        erde += karte.getErde(du);
                        selectedKarten += 1;
                    }
                }
                if (feuer >= mussErreichen || erde >= mussErreichen)
                    sfZaehler = 5;
                else
                    sfZaehler = 0;
                for (int i = 0; i < selectedKarten; i++) {
                    int sumf = 0;
                    int sume = 0;
                    int count = 0;
                    for (int j = 0; j < ich.getHandSize(); j++) {
                        Karte karte = ich.getHandKarte(j);
                        if (karte.getSelected()) {
                            if (i != count) {
                                sumf += karte.getFeuer(du);
                                sume += karte.getErde(du);
                                if (sumf >= mussErreichen || sume >= mussErreichen) break;
                            }
                            count += 1;
                        }
                    }
                    if (sumf >= mussErreichen || sume >= mussErreichen) {
                        sfZaehler = 0;
                        break;
                    }
                }
                return true;
            }
        } else if (sonderfunktion==202 && ichBinAmZug()) { //Monduntergangs-Bombe
            if (a == -1) {
                for (int i = 0; i < ich.getHandSize(); i++) {
                    ich.getHandKarte(i).setSelected(false);
                }
                sonderfunktion = 0;
                return true;
            } else if (a == 0 && sfZaehler >= 2) {
                for (int i = ich.getHandSize() - 1; i >= 0; i--) {
                    if (ich.getHandKarte(i).getSelected()) {
                        ich.getHandKarte(i).setSelected(false);
                        ich.werfeHandkarteAb(i);
                    }
                }
                du.verbrauche(202);
                sfZaehler = 0;
                sonderfunktion = 0;
                return true;
            } else if (
                    a - 1 >= 0
                            && a - 1 < ich.getHandSize()
            ) {
                int mussErreichen = 2;
                if (du.hatAktiv(196)) {//explosionsmeister
                    for (int i = du.getSpielzone().getSize() - 1; i >= 0; i--) {
                        int nr = du.getSpielzone().get(i).getNummer();
                        if (nr >= 198 && nr <= 202) {
                            if (nr == sonderfunktion)
                                mussErreichen *= 2;
                            break;
                        }
                    }
                }
                boolean set = !ich.getHandKarte(a - 1).getSelected();
                ich.getHandKarte(a - 1).setSelected(set);
                //Zähle Karten
                sfZaehler = 0;
                int selectedKarten = 0;
                for (int i = 0; i < ich.getHandSize(); i++) {
                    Karte karte = ich.getHandKarte(i);
                    if (karte.getSelected()) {
                        sfZaehler += karte.getGold();
                        selectedKarten += 1;
                    }
                }
                if (sfZaehler >= mussErreichen)
                    sfZaehler = 2;
                else
                    sfZaehler = 0;
                for (int i = 0; i < selectedKarten; i++) {
                    int sum = 0;
                    int count = 0;
                    for (int j = 0; j < ich.getHandSize(); j++) {
                        Karte karte = ich.getHandKarte(j);
                        if (karte.getSelected()) {
                            if (i != count) {
                                sum += karte.getGold();
                                if (sum >= mussErreichen) break;
                            }
                            count += 1;
                        }
                    }
                    if (sum >= mussErreichen) {
                        sfZaehler = 0;
                        break;
                    }
                }
                return true;
            }
        } else if (sonderfunktion==212 && ichBinAmZug()) { //Trinke das Wasser des Lebens
            if (a == -1) {
                sonderfunktion = 0;
                return true;
            } else if (a == 0) {
                ich.mischeAbwurfinNachzieh();
                gottaSend = true;
                sonderfunktion = 0;
                return true;
            }
        } else if (sonderfunktion==213 && ichBinAmZug()) { //Ertränke den Widerstand (1)
            if (a==-1){
                sonderfunktion=0;
                return true;
            } else if (a==0){
                if(du.hatMehrAls1AktivenChar()) {
                    sonderfunktion += 1000;
                } else {
                    du.ertraenkeDenWiderstandEffekt1(-1);
                    sonderfunktion=0;
                }
                return true;
            }
        } else if (sonderfunktion==1213 && ichBinAmZug()) { //Ertränke den Widerstand (2)
            if (a == -1) {
                sonderfunktion -= 1000;
                return true;
            } else if (
                    a - 200 >= 0
                            && a - 200 < du.getSpielzone().getSize()
            ) {
                Karte karte = du.getSpielzone().get(a - 200);
                if (karte.istAktiv() && karte.getTyp() == KartenTyp.CHARAKTER) {
                    du.ertraenkeDenWiderstandEffekt1(a - 200);
                    sonderfunktion = 0;
                    return true;
                }
            }
        } else if (sonderfunktion==214 && !ichBinAmZug()) { //Verteidige die Tiefe
            if (a == 0 && sfZaehler == 1) {
                for (int i = ich.getHandSize() - 1; i >= 0; i--) {
                    if (ich.getHandKarte(i).getSelected()) {
                        ich.getHandKarte(i).setSelected(false);
                        ich.werfeHandkarteAb(i);
                    }
                }
                sfZaehler = 0;
                sonderfunktion = 0;
                return true;
            } else if (a - 1 >= 0 && a - 1 < ich.getHandSize()) {
                boolean set = !ich.getHandKarte(a - 1).getSelected();
                ich.getHandKarte(a - 1).setSelected(set);
                //Zähle Handkarten
                sfZaehler = ich.getHandSize();
                for (int i = 0; i < ich.getHandSize(); i++) {
                    if (ich.getHandKarte(i).getSelected())
                        sfZaehler--;
                }
                if (sfZaehler == 5)
                    sfZaehler = 1;
                else
                    sfZaehler = 0;
                return true;
            }
        } else if (sonderfunktion==222 && ichBinAmZug()) { //Schatztaucher
            if (a == -1) {
                for (int i = 0; i < ich.getHandSize(); i++) {
                    ich.getHandKarte(i).setSelected(false);
                }
                sonderfunktion = 0;
                sfZaehler = 0;
                return true;
            } else if (a == 0 && sfZaehler >= 1) {
                for (int i = ich.getHandSize() - 1; i >= 0; i--) {
                    if (ich.getHandKarte(i).getSelected()) {
                        ich.getHandKarte(i).setSelected(false);
                        ich.werfeHandkarteAb(i);
                    }
                }
                sfZaehler = 0;
                sonderfunktion = 0;
                return true;
            } else if (a - 1 >= 0 && a - 1 < ich.getHandSize()) {
                boolean set = !ich.getHandKarte(a - 1).getSelected();
                ich.getHandKarte(a - 1).setSelected(set);
                //Zähle Handkarten
                sfZaehler = 0;
                for (int i = 0; i < ich.getHandSize(); i++) {
                    Karte karte = ich.getHandKarte(i);
                    if (karte.getSelected()) {
                        sfZaehler++;
                        break;
                    }
                }
                return true;
            }
        } else if (sonderfunktion==225 && ichBinAmZug()) { //Diebischer Taucher
            if (a == -1) {
                int merkeSfZaehler = sfZaehler;
                wiederherstelleGameState(beginnSF);
                merkeBeginnSF();
                if (merkeSfZaehler == 0)
                    sonderfunktion = 0;
                else
                    sonderfunktion = 225;
                sfZaehler = 0;
                return true;
            } else if (a == 0 && sfZaehler >= 1) {
                sonderfunktion = 0;
                sfZaehler = 0;
                return true;
            } else if (a - 1 >= 0 && a - 1 < ich.getHandSize()) {
                ich.legeHandkarteUnterNachziehstapel(a - 1);
                sfZaehler++;
                return true;
            }
        } else if (sonderfunktion==240 && ichBinAmZug()){ //Unwiderstehliche Sirenen
            if (a==-1){
                for(int i=0; i<ich.getHandSize(); i++){
                    ich.getHandKarte(i).setSelected(false);
                }
                sfZaehler=0;
                sonderfunktion=0;
                return true;
            } else if (a==0 && sfZaehler==1){
                for(int i=0; i<ich.getHandSize(); i++){
                    if (ich.getHandKarte(i).getSelected()) {
                        ich.getHandKarte(i).setSelected(false);
                        ich.werfeHandkarteAb(i);
                        break;
                    }
                }
                du.verbrauche(240);
                sfZaehler=0;
                sonderfunktion=0;
                return true;
            } else if (a-1>=0 && a-1<ich.getHandSize()){
                boolean set = !ich.getHandKarte(a-1).getSelected();
                ich.getHandKarte(a-1).setSelected(set);
                for(int i=0; i<ich.getHandSize(); i++){
                    if (a-1 != i)
                        ich.getHandKarte(i).setSelected(false);
                }
                if (ich.getHandKarte(a-1).getTyp()==KartenTyp.CHARAKTER)
                    sfZaehler=1;
                else
                    sfZaehler=0;
                return true;
            }


        } else if ((sonderfunktion==300) && ichBinAmZug()){ //Schlitzohr
            if (a==-1){ //Abbrechen
                nextSF(0);
                return true;
            } else if (a==0){ //OK
                ziehe(1, aktiverSpieler);
                nextSF(0);
                return true;
            }
        } //else if (sonderfunktion==xxx && ichBinAmZug()) {}
        return false;
    }
    //Aktiver Spieler verzichtet auf Kampfbeginn
    private void verzicht(){
        merkeBeginnSF();
        sfZaehler = 0;
        sonderfunktion=-1;
        if(aktiverSpieler.getHandSize()==0){
            sfZaehler=3;
        }
    }
    private void verzichtDone(){
        nextSF(0);
        sfZaehler = 0;
        wechselAktivenSpieler();
        neuerKampf();
    }
    //Aktiver Spieler zieht sich zurück
    private void rueckzug(){
        merkeBeginnSF();
        sfZaehler=0;
        sonderfunktion=-2;
    }
    Spieler getSieger(){return sieger;}
    private void rueckzugDone(){
        int drachenanzahl;
        if (du.hatAktivUndUnverbraucht(240)){
            drachenGewinnen(du, 1);
        }
        if (du.hat6oderMehrCharVer()){
            drachenanzahl = 2;
        } else {
            drachenanzahl = 1;
        }
        //Mehr drachen
        if (du.hatAktiv(30)){ //Geister-Resonanz
            drachenanzahl+=1;
        }
        if (du.hatAktiv(42)){ //Charismatischer Prediger
            drachenanzahl+=1;
        }
        if (du.hatAktiv(147)){ //Klebriges Pulver
            drachenanzahl+=1;
        }

        //Weniger Drachen
        if (ich.hatAktiv(90)){ //Brennender Geistertanz
            drachenanzahl-=1;
        }
        if (ich.hatAktiv(119)){ //Abwurf Scherbenschauer
            drachenanzahl-=1;
        }

        //Genau X drachen
        if(ich.hatAktiv(93)) { //Ehre die Heiligen Geister
            if(drachenanzahl>1) {
                drachenanzahl = 1;
            }
        }
        if (ich.hatAktiv(43)){ //Ernste Ordensschwester
            drachenanzahl=0;
        }
        drachenGewinnen(du, drachenanzahl);
        neuerKampf();
        sonderfunktion=0;
    }
    //pSpieler gewinnt n Drachen
    private void drachenGewinnen(Spieler pSpieler, int n){
        for (int i=0; i<n; i++){
            drachenGewinnen(pSpieler);
        }
        if (pSpieler.getDrachen() >= 4){
            gameEnd = true;
            sieger = pSpieler;
        }
    }
    //pSpieler gewinnt 1 Drache
    private void drachenGewinnen(Spieler pSpieler){
        Spieler gewinner, verlierer;
        if (pSpieler == ich){
            gewinner = ich;
            verlierer = du;
        } else {
            gewinner = du;
            verlierer = ich;
        }
        if (verlierer.getDrachen()>0){
            verlierer.aendereDrachen(-1);
        } else {
            gewinner.aendereDrachen(1);
        }
        if (pSpieler.getDrachen() >= 4){
            gameEnd = true;
            sieger = pSpieler;
        }
    }
    //Speichere Stand direkt vor Beginn der Charakterphase ab
    private void merkeBeginnCharakterphase(){
        beginnCharakterphase = new Partie(this, false);
    }
    private void merkeBeginnSF(){
        beginnSF = new Partie(this, false);
    }
    //Versuche in die nächste Phase überzugehen
    /*
    private boolean versucheNaechstePhase(){
        if (phase==zugPhase.CHARAKTER && !charakterGespielt){ //Noch kein Charakter gespielt
            if (kampfzug == 1){
                aktivesElement = element.UNBESTIMMT;
            }
            return false;
        }
        if (
                (phase==zugPhase.VERSTÄRKUNG && du.getStaerke(aktivesElement)>ich.getStaerke(aktivesElement)) //Ich kann deinen Wert nicht erreichen
                && (!(ich.hatSchild(aktivesElement))) //und habe keine aktive Karte mit Schildsymbol des aktiven Elements
        ){
            return false;
        }
        naechstePhase();
        return true;
    }
    */
    //Versuche eine Karte zu spielen
    boolean karteSpielenErlaubt(Karte pKarte){
        if (flutEffekt)
            return false;
        if (aktiverSpieler.getGegner().hatAktiv(60) && (aktionenGespielt>0||charaktereGespielt>0||verstaerkungenGespielt>0||charakterGespielt)){ //Blutige Guillotine
            return false;
        }
        KartenTyp typ = pKarte.getTyp();
        if (//Falsche Phase
                ((typ== KartenTyp.EINFACHE_AKTION||typ== KartenTyp.BLEIBENDE_AKTION) && (phase!= ZugPhase.AKTION&&phase!= ZugPhase.BEGINN)) ||
                (typ== KartenTyp.CHARAKTER && (phase== ZugPhase.VERSTAERKUNG ||phase== ZugPhase.ZIEH||phase== ZugPhase.ENDE)) ||
                ((typ== KartenTyp.EINFACHE_VERSTAERKUNG ||typ== KartenTyp.BLEIBENDE_VERSTAERKUNG) && ((phase!= ZugPhase.CHARAKTER&&phase!= ZugPhase.VERSTAERKUNG)||!charakterGespielt))
        ){return false;}
        int minus = 0;
        if (
                ich.hatStoppKarten(charakterGespielt)
                && charakterGespielt
        ){minus=1;}
        int alimit = aktionLimit-minus;
        int climit = charakterLimit-minus;
        int vlimit = verstaerkungLimit-minus;
        int evlimit = einfachVLimit-minus;
        int bvlimit = bleibendVLimit-minus;
        if(aktiverSpieler.hatAktiv(14)){ //Zwillingszauber-Novize
            alimit+=1;
        }
        if(aktiverSpieler.hatAktiv(36)) { //Entfache die Kriegsmaschinen
            vlimit+=100;
            bvlimit+=100;
        }
        if(aktiverSpieler.hatAktiv(46)){ //Fleißiger Waffenschmied
            vlimit+=1;
            bvlimit+=1;
        }
        if(aktiverSpieler.hatAktiv(94)){ //Entsende die Wurfwaffen
            vlimit+=100;
            evlimit+=100;
        }
        if(aktiverSpieler.hatAktiv(193)){ //Meister der Trünke
            vlimit+=1;
            evlimit+=1;
        }
        if ( //Kartenlimit erreicht
            (
                !istFrei(pKarte)
            )
            &&
            (
                ((typ== KartenTyp.EINFACHE_AKTION||typ== KartenTyp.BLEIBENDE_AKTION) && alimit <= aktionenGespielt)
                || (typ== KartenTyp.CHARAKTER && climit <= charaktereGespielt)
                || ((typ== KartenTyp.EINFACHE_VERSTAERKUNG) && (vlimit<=verstaerkungenGespielt||evlimit<=evGespielt))
                || ((typ== KartenTyp.BLEIBENDE_VERSTAERKUNG) && (vlimit<=verstaerkungenGespielt||bvlimit<=bvGespielt))
            )
        ){return false;}
        //SONDERFUNKTIONEN, DIE KARTEN SPIELEN VERBIETEN
        if (
                (du.hatAktiv(17) && typ== KartenTyp.CHARAKTER && !pKarte.hatSF())//Bannzauber-Magus
                || (du.hatAktiv(26) && typ== KartenTyp.EINFACHE_AKTION) //Turm der Gestze
                || (du.hatAktiv(28) && pKarte.hatSF() && (typ== KartenTyp.CHARAKTER||typ== KartenTyp.EINFACHE_VERSTAERKUNG)) //Operative Einschränkung
                || (du.hatAktiv(45) && (typ== KartenTyp.EINFACHE_AKTION||typ== KartenTyp.EINFACHE_VERSTAERKUNG||typ== KartenTyp.BLEIBENDE_VERSTAERKUNG)) //Strenger Amtmann
                || (du.hatAktiv(89) && typ== KartenTyp.EINFACHE_VERSTAERKUNG) //Natürliche Entwaffnung
                || (du.hatAktiv(102) && (typ== KartenTyp.CHARAKTER||typ== KartenTyp.EINFACHE_VERSTAERKUNG) && (charakterGespielt||charaktereGespielt>0)) //Plusterhexer
                || ich.hatAktiv(124) //Lass die Fäuste tanzen
                || (du.hatAktiv(150) && typ== KartenTyp.BLEIBENDE_VERSTAERKUNG) //Wacher Alptraum
                || (du.hatAktiv(163) && pKarte.hatSF()) //Kettenschwinger
                || (du.hatAktiv(168) && !pKarte.hatSF()) //Schwingkette
                || (du.hatAktiv(177) && typ== KartenTyp.CHARAKTER && (pKarte.getFeuer(du)>4||pKarte.getErde(du)>4)) //Mordende Truppe
                || (du.hatAktiv(178) && (typ== KartenTyp.EINFACHE_AKTION||typ== KartenTyp.BLEIBENDE_AKTION)) //Plündernde Truppe
        ){
            return false;
        }
        return true;
    }
    boolean feuerAnsagenErlaubt(){
        if(
                ichBinAmZug() &&
                aktivesElement != Element.ERDE &&
                (phase == ZugPhase.CHARAKTER || phase == ZugPhase.VERSTAERKUNG) &&
                charakterGespielt &&
                (
                    ich.getStaerke(aktivesElement) >= du.getStaerke(aktivesElement)
                    || ich.hatAktivenSchild(Element.FEUER)
                )
                && diverseSfOK()
        ){ return true; }
        return false;
    }
    boolean erdeAnsagenErlaubt(){
        if(
                ichBinAmZug() &&
                aktivesElement != Element.FEUER &&
                (phase == ZugPhase.CHARAKTER || phase == ZugPhase.VERSTAERKUNG) &&
                charakterGespielt &&
                (
                    ich.getStaerke(aktivesElement) >= du.getStaerke(aktivesElement)
                    || ich.hatAktivenSchild(Element.ERDE)
                )
                && diverseSfOK()
        ){ return true; }
        return false;
    }
    private boolean diverseSfOK(){
        return
                roechelMoerderOK()
                && winselStreckerOK()
                && gurgelSchlitzerOK()
                && bombenOK();
    }
    private boolean roechelMoerderOK(){ //Röchelmörder
        return !aktiverSpieler.getGegner().hatAktiv(164) || aktionenGespielt>=1;
    }
    private boolean winselStreckerOK(){ //Winselstrecker
        return !aktiverSpieler.getGegner().hatAktiv(165) || bvGespielt>=1;
    }
    private boolean gurgelSchlitzerOK(){ //Gurgelschlitzer
        return !aktiverSpieler.getGegner().hatAktiv(166) || evGespielt>=1;
    }
    private boolean bombenOK(){
        for (int i=198; i<=202; i++){
            if (aktiverSpieler.getGegner().hatAktivUndUnverbraucht(i))
                return false;
        }
        return true;
    }

    //Überprüfe, ob eine Karte nicht zum Limit dazuzählt
    private boolean istFrei(Karte pKarte){
        //Wenn ich Karte mit Stopp habe -> false
        if (aktiverSpieler.hatStoppKarten(charakterGespielt)){
            return false;
        }
        //Falls ich zuletzt Yin oder Yang gespielt habe
        if (yinyangEffekt)
            return true;
        //Falls Karte unignoriertes Frei-Symbol hat -> true
        if (pKarte.hatSymbol(KartenSymbol.FREI, aktiverSpieler.getGegner())){
            return true;
        }
        //Falls Karte relevantes Paar-Symbol hat -> true
        if (
            pKarte.hatSymbol(KartenSymbol.PAAR, aktiverSpieler.getGegner())
            && aktiverSpieler.getSpielzone().getSize()>0
        ){
            Karte paarKarte = aktiverSpieler.getSpielzone().get(aktiverSpieler.getSpielzone().getSize()-1);
            if (
                    paarKarte.istAktiv()
                    && paarKarte.hatSymbol(KartenSymbol.PAAR, aktiverSpieler.getGegner())
                    && paarKarte.getTyp()==pKarte.getTyp()
                    && paarKarte.getName().substring(0, 3).equals(pKarte.getName().substring(0, 3))
            ) {
                if (aktiverSpieler.getSpielzone().getSize()>1){
                    paarKarte = aktiverSpieler.getSpielzone().get(aktiverSpieler.getSpielzone().getSize()-2);
                    if (
                        paarKarte.istAktiv()
                        && paarKarte.hatSymbol(KartenSymbol.PAAR, aktiverSpieler.getGegner())
                        && paarKarte.getTyp()==pKarte.getTyp()
                        && paarKarte.getName().substring(0, 3).equals(pKarte.getName().substring(0, 3))
                    ){return false;}
                }
                    return true;
            }
        }
        //Falls Karte relevantes GANG-Symbol hat -> ya guessed it, true :*
        if (
                charakterGespielt
                && pKarte.hatSymbol(KartenSymbol.GANG, aktiverSpieler.getGegner())
                && aktiverSpieler.getSpielzone().getSize()>0
        ){
            Karte gangKarte = aktiverSpieler.getSpielzone().get(aktiverSpieler.getSpielzone().getSize()-1);
            if (
                    gangKarte.istAktiv()
                    && gangKarte.hatSymbol(KartenSymbol.GANG, aktiverSpieler.getGegner())
                    && gangKarte.getName().substring(0, 3).equals(pKarte.getName().substring(0, 3))
            ) { return true; }
        }
        //Ansonsten -> false
        return false;
    }
    //Spiele eine Handkarte
    private void spieleHandKarte(int pIndex){
        Karte karte = aktiverSpieler.getHandKarte(pIndex);
        KartenTyp typ = karte.getTyp();
        //Pass Zugphasse ggf an
        if ((typ== KartenTyp.EINFACHE_AKTION||typ== KartenTyp.BLEIBENDE_AKTION) && phase!= ZugPhase.AKTION){
            phase = ZugPhase.AKTION;
        } else if (typ== KartenTyp.CHARAKTER && phase!= ZugPhase.CHARAKTER){
            charakterPhase();
        } else if ((typ== KartenTyp.EINFACHE_VERSTAERKUNG ||typ== KartenTyp.BLEIBENDE_VERSTAERKUNG) && phase!= ZugPhase.VERSTAERKUNG){
            phase = ZugPhase.VERSTAERKUNG;
        }
        //deaktivier ggf darunter liegende karten
        if (
                karte.getTyp() == KartenTyp.CHARAKTER
                && !charakterGespielt
                && aktiverSpieler.getSpielzone().getSize()>0
        ){
            boolean deaktivier=true;
            if (karte.hatSymbol(KartenSymbol.GANG, aktiverSpieler.getGegner())){
                Karte letzteInKampfbereich;
                for(int i=aktiverSpieler.getSpielzone().getSize()-1; i>=0; i--){
                    letzteInKampfbereich = aktiverSpieler.getSpielzone().get(i);
                    if((letzteInKampfbereich.getTyp()==KartenTyp.CHARAKTER || letzteInKampfbereich.getTyp()==KartenTyp.EINFACHE_VERSTAERKUNG)){
                        if(
                                letzteInKampfbereich.istAktiv()
                                && letzteInKampfbereich.hatSymbol(KartenSymbol.GANG, aktiverSpieler.getGegner())
                                && letzteInKampfbereich.getName().substring(0,3).equals(karte.getName().substring(0,3))
                        ){deaktivier=false;}
                        break;
                    }
                }
            } else if( //Ganzling
                    karte.getNummer()==145
                    && !karte.sfIgnoriert(aktiverSpieler.getGegner())
            ){deaktivier=false;}
            if (deaktivier) {
                aktiverSpieler.deaktiviereAlle(KartenTyp.CHARAKTER);
                aktiverSpieler.deaktiviereAlle(KartenTyp.EINFACHE_VERSTAERKUNG);
            }
        }
        //Passe Kartenlimit ggf an
        if (typ == KartenTyp.EINFACHE_AKTION || typ == KartenTyp.BLEIBENDE_AKTION) {
            aktionenGespielt += 1;
        } else if (typ == KartenTyp.CHARAKTER) {
            charaktereGespielt += 1;
        } else if (typ == KartenTyp.EINFACHE_VERSTAERKUNG) {
            verstaerkungenGespielt += 1;
            evGespielt += 1;
        } else if (typ == KartenTyp.BLEIBENDE_VERSTAERKUNG) {
            verstaerkungenGespielt += 1;
            bvGespielt += 1;
        }
        if(istFrei(karte)){
            if (typ == KartenTyp.EINFACHE_AKTION || typ == KartenTyp.BLEIBENDE_AKTION) {
                aktionLimit += 1;
            } else if (typ == KartenTyp.CHARAKTER) {
                charakterLimit += 1;
            } else if (typ == KartenTyp.EINFACHE_VERSTAERKUNG) {
                verstaerkungLimit += 1;
                einfachVLimit += 1;
            } else if (typ == KartenTyp.BLEIBENDE_VERSTAERKUNG) {
                verstaerkungLimit += 1;
                bleibendVLimit += 1;
            }
        }
        if (typ == KartenTyp.CHARAKTER){
            charakterGespielt = true;
        }
        //Spiele Karte
        aktiverSpieler.spiele(pIndex);
        yinyangEffekt=false;
        int nr = karte.getNummer();
        if (
            !karte.sfIgnoriert(aktiverSpieler.getGegner()) &&
            (
                nr==2 || //Bezaubere den Heiligen Drachen
                nr==3 || //Vernichte den Feind
                nr==4 || //Rufe den Meister
                nr==6 || //Erwecke das Wissen
                nr==15 || //Pulverisierer-Novize
                nr==18 || //Nichtigmacher-Magus
                nr==32 //Erzürne den Heiligen Drachen
                || nr==33 //Sende Verstärkung
                || nr==35 //Vertreibe die Eindringlinge
                || nr==37 //Erwecke die Demut
                || nr==52 //Relikt der Segnung
                || nr==62 //Rufe den Geist der Ahnen
                || nr==63 //Rufe den Geist der Schlacht
                || nr==92 //Täusche den Heiligen Drachen
                || (nr==101 && du.hatMehrAls1AktivenChar()) //Kratzerhexer
                || nr==122 //Rekrutiere einen Krieger
                || nr==123 //Ehre die Gefallenen
                //|| nr==125 //Erwecke den Elan
                || nr==145 //Ganzling
                || nr==152 //Beschenke den Heiligen Drachen
                || nr==153 //Plündere das Dorf
                || nr==171 //Schwächlinggranate
                || nr==182 //Versuche den Heiligen Drachen
                || nr==184 //Erwecke die Gier
                || nr==194 //Kriegskunstmeister
                || nr==212 //Trinke das Wasser des Lebens
                || nr==213 //Ertränke den Widerstand
                || (nr==214 && du.getHandSize()>5) //Verteidige die Tiefe
                || nr==222 //Schatztaucher
                || nr==225 //Diebischer Taucher
            )
        ){
            sfZaehler=0;
            merkeBeginnSF();
            sonderfunktion = nr;

            if(nr==122) //Rekrutiere einen Krieger
                gottaOpen=0; //Eigener Nachziehstapel
        }
        //INSTANT AUSFÜHR SONDERFUNKTIONEN
        if (!karte.sfIgnoriert(aktiverSpieler.getGegner())){
            if(nr==64){ //Erwecke den Zyklus
                du.mischeAbwurfinNachzieh();
                ich.mischeAbwurfinNachzieh();
                gottaSend=true;
            } else if (nr==95){ //Erwecke die Freiheit
                ich.werfeAlleVonSpielzoneAb(KartenTyp.BLEIBENDE_VERSTAERKUNG);
                du.werfeAlleVonSpielzoneAb(KartenTyp.BLEIBENDE_VERSTAERKUNG);
            } else if (nr==124){ //Lass die Fäuste tanzen
                charakterGespielt=true;
                phase = ZugPhase.CHARAKTER;
            } else if (nr==154){ //Erwecke die Raserei
                ziehe(3, du);
                ziehe(3, ich);
            } else if (nr==183){ //Studiere die Landkarte
                du.deckeHandAuf();
            } else if (nr==215){ //Erwecke die Balance
                for(int i=du.getHandSize()-1; i>=0; i--) {
                    du.mischeVonHandInNachziehstapel(i);
                }
                zieheAuf(6, du);
                for(int i=ich.getHandSize()-1; i>=0; i--) {
                    ich.mischeVonHandInNachziehstapel(i);
                }
                zieheAuf(6, ich);
                gottaSend=true;
            } else if (nr==226 || nr==227){ //Yin & Yang
                yinyangEffekt=true;
            }
        }

    }
    //Überprüft ob Spieler "ich" am Zug ist
    boolean ichBinAmZug(){
        return aktiverSpieler == ich;
    }
    private void zieheAuf(int n, Spieler pSpieler){
        if(!pSpieler.getGegner().hatAktiv(29)) { //Explosive Sabotage
            if (pSpieler.getGegner().hatAktiv(149) && pSpieler==aktiverSpieler){ //Geschwätziger Wachposten
                if (!gezogen) {
                    if (n > pSpieler.getHandSize()) {
                        ziehe(1, pSpieler);
                    }
                } else {return;}
            } else {
                if (pSpieler.zieheAuf(n)>=1 && pSpieler==aktiverSpieler)
                    gezogen=true;
            }
        }
    }
    private void ziehe(int n, Spieler pSpieler){
        if (!pSpieler.getGegner().hatAktiv(29)) { //Explosive Sabotage
            if (pSpieler.getGegner().hatAktiv(149) && pSpieler==aktiverSpieler) { //Geschwätziger Wachposten
                if(!gezogen) {
                    if (pSpieler.ziehe(1)>=1 && pSpieler==aktiverSpieler)
                        gezogen = true;
                } else {return;}
            } else {
                if (pSpieler.ziehe(n)>=1 && pSpieler==aktiverSpieler)
                    gezogen=true;
            }
        }
    }
    //Wechsel aktiven Spieler
    private void wechselAktivenSpieler() {
        if(aktiverSpieler==ich){
            aktiverSpieler=du;
        }else{
            aktiverSpieler=ich;
        }
    }
    //Get Spieler ich
    Spieler getIch() {
        return ich;
    }
    //Get Spieler du
    Spieler getDu() {
        return du;
    }
    //Starte eine Partie (Spielaufbau)
    void spielAufbau(){
        ich.mischeNachziehstapel();
        du.mischeNachziehstapel();
        ich.zieheAuf(6);
        du.zieheAuf(6);
        spielzug=1;
        kampfzug=1;
        aktivesElement = Element.UNBESTIMMT;
        ich.anfuehrerUndGottInSpielzoneLegen();
        du.anfuehrerUndGottInSpielzoneLegen();
        //Donnferfaust-Effekt
        if(ich.hatAktiv(297))
            drachenGewinnen(ich, 1);
        if(du.hatAktiv(297))
            drachenGewinnen(du, 1);

        //Ermittle Startspieler
        int ichGold, duGold;
        do{
            Karte ichKarte, duKarte;
            ichKarte = ich.mulliganAufdecken();
            ichGold = ichKarte.getGold();
            duKarte = du.mulliganAufdecken();
            duGold = duKarte.getGold();
            if (ichGold==duGold){
                if (ichKarte.getName().toLowerCase().charAt(0) < duKarte.getName().toLowerCase().charAt(0))
                    ichGold++;
                else if (ichKarte.getName().toLowerCase().charAt(0) > duKarte.getName().toLowerCase().charAt(0))
                    duGold++;
            }
        }while(
                ich.getNachziehSize()>0
                && du.getNachziehSize()>0
                && ichGold==duGold
        );
        if (ichGold==duGold){
            int random = (int) (Math.random() * 2);
            if (random==0){
                ichGold++;
            } else {
                duGold++;
            }
        }
        if (ichGold>duGold){
            aktiverSpieler=ich;
        } else {
            aktiverSpieler=du;
        }
        sonderfunktion=-3;
    }
    int getKampfzug(){
        return kampfzug;
    }
    int getSpielzug(){
        return spielzug;
    }
    Element getAktivesElement(){
        return aktivesElement;
    }
    ZugPhase getPhase(){
        return phase;
    }
    boolean getCharakterGespielt(){
        return charakterGespielt;
    }
    //Wechsel in die nächste Phase (sollte nur durch versucheNaechstePhase() aufgerufen werden)
    private void naechstePhase(){
        switch(phase){
            case BEGINN:
                phase = ZugPhase.AKTION;
                break;
            case AKTION:
                charakterPhase();
                break;
            case CHARAKTER:
                phase = ZugPhase.VERSTAERKUNG;
                break;
            case VERSTAERKUNG:
                ziehPhase();
                break;
            case ZIEH:
                phase = ZugPhase.ENDE;
                break;
            case ENDE:
                naechsterZug();
                break;
        }
    }
    //Beginne einen neuen Zug
    private void beginnDesZuges(){
        wargCheck();
        gezogen=false;
        aktiverSpieler.alleSfUnverbraucht();
        beginnCharakterphase=null;
        aktionLimit=1;
        aktionenGespielt=0;
        charakterLimit=1;
        charaktereGespielt=0;
        charakterGespielt=false;
        verstaerkungenGespielt=0;
        evGespielt=0;
        bvGespielt=0;
        if (kampfzug<=1){
            verstaerkungLimit=0;
            einfachVLimit =0;
            bleibendVLimit =0;
        } else {
            verstaerkungLimit=1;
            einfachVLimit =1;
            bleibendVLimit =1;
        }
        phase = ZugPhase.BEGINN;
    }
    //Beginne die Charakterphase
    private void charakterPhase(){
        phase = ZugPhase.CHARAKTER;
        sonderfunktion=0;
        merkeBeginnCharakterphase();
    }

    private boolean FeuerErdeAufSeperatenKarten(int pSelectedKarten, int mussErreichen){
       if (zfeaskErreicht(pSelectedKarten, -1, mussErreichen))
           return zfeaskKeineUeberfluessig(pSelectedKarten, mussErreichen);
       return false;
    }
    private boolean zfeaskErreicht(int pSelectedKarten, int ignoreNr, int mussErreichen){
        int wieOft = (int)Math.pow(2, pSelectedKarten);
        for(int durchlauf=0; durchlauf<wieOft; durchlauf++){
            int feuer=0;
            int erde=0;
            int selectVal=1;
            int selectNr=0;
            for(int i=0; i<ich.getHandSize(); i++){
                if (ich.getHandKarte(i).getSelected()){
                    if (ignoreNr != selectNr) {
                        if (((durchlauf / selectVal) % 2) == 0) {
                            feuer += ich.getHandKarte(i).getFeuer(du);
                        } else {
                            erde += ich.getHandKarte(i).getErde(du);
                        }
                        if (feuer >= mussErreichen && erde >= mussErreichen)
                            return true;
                    }
                    selectVal *= 2;
                    selectNr += 1;
                }
            }
        }
        return false;
    }
    private boolean zfeaskKeineUeberfluessig(int pSelectedKarten, int mussErreichen){
        for (int i=0; i<pSelectedKarten; i++){
            if (zfeaskErreicht(pSelectedKarten, i, mussErreichen))
                return false;
        }
        return true;
    }

    //Beginne die Ziehphase
    private void ziehPhase(){
        phase = ZugPhase.ZIEH;
        zieheAuf(6, aktiverSpieler);
        if (!aktiverSpieler.getGegner().hatAktiv(120)){ //Nervtötendes Gezwitscher
            if (aktiverSpieler.hatAktiv(300)) { //Schlitzohr
                nextSF(300);
            }
            if (aktiverSpieler.hatAktiv(16)) { //Schleichschritt-Novize
                nextSF(16);
            }
            if (aktiverSpieler.hatAktiv(27)) { //Eifrige Produktion
                nextSF(27);
            }
            if(aktiverSpieler.hatAktiv(138)) { //Meuchling Charlie
                nextSF(138);
            }
            if (aktiverSpieler.hatAktiv(80)) { //Priester des Lebens
                nextSF(80);
            }
            nextSF(0);
        }
    }

    ArrayList<Integer> getNextSFs(){
        return sfVorrat;
    }

    private void nextSF(int n){
        if (sonderfunktion==-6) {
            if (n - 6000 >= 0 && n - 6000 < sfVorrat.size()) {
                sonderfunktion = sfVorrat.get(n - 6000);

                Karte k = new Karte(sonderfunktion);

                sfVorrat.remove(n - 6000);
            }
        } else {
            if (n == 0) {
                if (sfVorrat.size() > 1) {
                    sonderfunktion = -6;
                    gottaOpenNextSF = true;
                } else if (sfVorrat.size() > 0) {
                    sonderfunktion = sfVorrat.get(0);
                    sfVorrat.remove(0);
                } else {
                    if (//ENDE DES ZUGES  -EFFEKTE
                            sonderfunktion == 47 //Erfinderischer Tüftler
                            || sonderfunktion == 1081 //Druide des Lebens
                    ) {
                        sonderfunktion = 0;
                        zugBeenden();
                    } else if (//DIREKT NACH VERSTÄRKUNGSPHASE -EFFEKTE
                            sonderfunktion == 49 //Talentierte Bardin
                            || sonderfunktion == 78 //Hexenmeister des Jenseits
                            || sonderfunktion == 79 //Schamane des Jenseits
                            || sonderfunktion == 139 //Meuchling Delta (1)
                            || sonderfunktion == 1139 //Meuchling Delta (2)
                            || sonderfunktion == 140 //Meuchling Echo
                            || sonderfunktion == 167 //Goblinräuber (1)
                            || sonderfunktion == 1167 //Goblinräuber (2)
                            || sonderfunktion == 195 //Meisterchronist
                    ) {
                        sonderfunktion = 0;
                        ziehPhase();
                    } else {
                        sonderfunktion = 0;
                    }
                }
            } else {
                sfVorrat.add(n);
            }
        }
    }
    private void wargCheck(){
        int wargs=0;
        for(int i=173; i<=176; i++) {
            if (aktiverSpieler.hatAktiv(i)){wargs += 1;}
            if(wargs==2){break;}
        }
        if(wargs>=2){
            drachenGewinnen(aktiverSpieler,1);
        }
    }
    //Wechsel in den nächsten Zug
    private void naechsterZug(){
        if(aktiverSpieler.hatAktiv(237) && aktiverSpieler.hatAktiv(238)){ //Reißende Sturmflut & Gewaltige Gezeitenflut
            flutEffekt=true;
        }
        if(aktiverSpieler.hatAktiv(159)) { //Wargfreund
            wargCheck();
        }
        aktiverSpieler.deaktiviereAlle(KartenTyp.EINFACHE_AKTION);
        spielzug+=1;
        kampfzug+=1;
        wechselAktivenSpieler();
        beginnDesZuges();
    }
    //Beginne einen neuen Kampf
    private void neuerKampf(){
        flutEffekt=false;
        ich.deaktiviereAlle(KartenTyp.EINFACHE_AKTION);
        du.deaktiviereAlle(KartenTyp.EINFACHE_AKTION);
        ich.raeumeSpielzoneAuf();
        du.raeumeSpielzoneAuf();
        zieheAuf(6, ich);
        zieheAuf(6, du);
        aktivesElement = Element.UNBESTIMMT;
        kampfzug=1;
        spielzug+=1;
        boolean ichLeer = ich.allesAlle();
        boolean duLeer = du.allesAlle();
        if (ichLeer||duLeer){
            gameEnd=true;
            if(ich.getDrachen() != du.getDrachen()){ //Spieler mit mehr Drachen gewinnt
                if (ich.getDrachen() > du.getDrachen()){
                    sieger = ich;
                } else {
                    sieger = du;
                }
            } else if (ichLeer != duLeer){ //Spieler mit Karten übrig gewinnt
                if (duLeer){
                    sieger = ich;
                } else {
                    sieger = du;
                }
            } else { //Spieler, der sich NICHT gerade zurückgezogen hat, gewinnt
                if (aktiverSpieler==du){
                    sieger = ich;
                } else {
                    sieger = du;
                }
            }
        }
        beginnDesZuges();
    }
    Spieler getAktiverSpieler(){
        return aktiverSpieler;
    }

    int getSonderfunktion(){
        return sonderfunktion;
    }
    int getSfZaehler(){
        return sfZaehler;
    }
    boolean gottaSend(){
        boolean returnval = gottaSend;
        gottaSend=false;
        return returnval;
    }
    int gottaShow(){
        int returnval = gottaShow;
        gottaShow = -1;
        return returnval;
    }
    String gottaShowTitel() {
        String returnString = gottaShowTitel;
        gottaShowTitel = null;
        return returnString;
    }
    ArrayList<Integer> gottaShowKarten(){
        ArrayList<Integer> returnArray = new ArrayList<Integer>();
        for(int i=0; i<gottaShowKarten.size(); i++){
            returnArray.add(gottaShowKarten.get(i));
        }
        gottaShowKarten = new ArrayList<Integer>();
        return returnArray;
    }
    void gottaSendTrue(){
        gottaSend=true;
    }
    boolean getFlutEffekt(){return flutEffekt;}
    int gottaOpen(){
        int returnint = gottaOpen;
        gottaOpen=-1;
        return returnint;
    }
    boolean gottaOpenNextSF(){
        boolean returnval = gottaOpenNextSF;
        gottaOpenNextSF = false;
        return returnval;
    }
}
