package gps.Arbre;

import gps.Ville;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Noeud {
    public static int ID_NOEUD = 0;
    String id = "";

    List<Ville> data;
    String sortie;
    String critereTest = null;

    Map<String, Noeud> fils;

    public Noeud(List<Ville> data, String sortie) {
        this.data = data;
        this.fils = new HashMap<>();

        this.sortie = sortie;

        // id du noeud pour graphviz
        this.id = "N" + ID_NOEUD;
        ID_NOEUD++;
    }
    public void creationArbre(String[] entrees) {
        // TODO a faire
        throw new Error("TODO");
    }

}
