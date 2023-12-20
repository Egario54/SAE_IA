package gps;

import gps.initData.DataHandler;
import gps.initData.Ville;
import gps.recherche.Solver;

import java.io.IOException;
import java.util.List;

public class Main {

  public static void main(String[] args) throws IOException {
    System.out.println("import des villes...");
    List<Ville> villes50PlusPeuplees, villes50a100PlusPeuplees;
    List<Ville>[] temp = DataHandler.initLists();
    villes50PlusPeuplees = temp[0];
    villes50a100PlusPeuplees = temp[1];
    System.out.println("import terminé!");
    System.out.println("recherche de l'itinéraire optimal...");

    List<Ville> cities = DataHandler.mergeLists(villes50PlusPeuplees, villes50a100PlusPeuplees);
    Solver.minPop130 = DataHandler.getLowestPop(villes50PlusPeuplees);
    Solver.minPop110 = DataHandler.getLowestPop(villes50a100PlusPeuplees);
    List<Ville> optimalDistance = Solver.findOptimalRouteDistance(cities, cities.get(cities.size()-1));

    System.out.println("Itinéraire optimal (distance) : " + optimalDistance);
    System.out.println("Distance totale : " + Solver.calculateTotalDistance(optimalDistance));


  }
}
