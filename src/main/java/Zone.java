import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

class Zone implements Serializable {

    //ATTRIBUTE
    private ArrayList<Karte> karten = new ArrayList<>();

    //KONSTRUKTOR
    Zone(ArrayList<Karte> pKarten){
        karten.addAll(pKarten);
    }
    //LEER-KONSTRUKTOR
    Zone(){}
    //KLON-KONSTRUKTOR
    Zone(Zone pZone){
        for(int i=0; i<pZone.karten.size(); i++){
            karten.add(new Karte(pZone.karten.get(i)));
        }
    }
    //METHODEN
    //Get Anzahl der Karten in der Zone
    int getSize(){
        return karten.size();
    }
    //Get Karte an index
    Karte get(int pIndex){
        return karten.get(pIndex);
    }
    //Füge Karte hinzu
    Karte add(Karte pKarte){
        karten.add(pKarte);
        return pKarte;
    }
    //Entferne und returne Karte an index
    Karte remove(int pIndex){
        return karten.remove(pIndex);
    }
    //Entferne und returne oberste Karte
    Karte removeTop(){
        return karten.remove(karten.size()-1);
    }
    //Mische Zone
    void mischen(){
        Collections.shuffle(karten);
    }
    //Bekomme Arrayliste
    ArrayList<Karte> getArrayList(){
        return karten;
    }
    void legeGanzNachUnten(Karte karte){
        karten.add(0, karte);
    }
}
