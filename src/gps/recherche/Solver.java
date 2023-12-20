package gps.recherche;

import gps.initData.Ville;

import java.util.*;

public class Solver {

  public static int minPop130;
  public static int minPop110;

  public static List<Ville> findOptimalRouteDistance(List<Ville> cities, Ville arrivee) {
    // Initialiser la file de priorité avec l'état initial
    PriorityQueue<State> priorityQueue = new PriorityQueue<>();
    priorityQueue.offer(new State(cities.get(0), new ArrayList<>(Arrays.asList(cities.get(0))), 0));

    while (!priorityQueue.isEmpty()) {
      State currentState = priorityQueue.poll();
      System.out.println(currentState.visitedCities.size() + " " + priorityQueue.size());

      // Si toutes les villes ont été visitées, retourner le chemin
      if (currentState.visitedCities.size() == cities.size()) {
        return currentState.visitedCities;
      }

      // Explorer les villes voisines non visitées parmis les moins éloignées restantes
      int nbRestantesProches = (cities.size() - currentState.visitedCities.size());
      if(nbRestantesProches > 4) nbRestantesProches = 4;
      Ville[] restantesProches = new Ville[nbRestantesProches];
      for (Ville neighbor : cities) {
        if (neighbor == arrivee)  continue;
        if (!currentState.visitedCities.contains(neighbor)) {
          if(containsNull(restantesProches)){
            for (int i = 0; i < restantesProches.length; i++) {
              if(restantesProches[i] == null){
                restantesProches[i] = neighbor;
                break;
              }
            }
          }
          else for (int i = 0; i < restantesProches.length; i++) {
            if (restantesProches[i] == null || currentState.currentCity.timeTo(neighbor) < currentState.currentCity.timeTo(restantesProches[i])) {
              restantesProches[i] = neighbor;
              break;
            }
          }
        }
      }
      for (Ville neighbor : restantesProches) {
        if (!currentState.visitedCities.contains(neighbor)) {
          List<Ville> newVisitedCities = new ArrayList<>(currentState.visitedCities);
          newVisitedCities.add(neighbor);

          double newCost = currentState.cost + currentState.currentCity.timeTo(neighbor);

          // Utiliser la distance euclidienne comme heuristique
          double heuristic = calculateHeuristic(neighbor,currentState.currentCity, arrivee);

          double priority = newCost + heuristic;

          priorityQueue.offer(new State(neighbor, newVisitedCities, newCost, priority));
        }
      }
    }

    return null; // Aucun chemin trouvé
  }
  private static boolean containsNull(Ville[] restantesProches) {
    for (Ville restantesProche : restantesProches) {
      if (restantesProche == null) {
        return true;
      }
    }
    return false;
  }

  private static double calculateHeuristic(Ville v1, Ville v2, Ville arrivee) {
    return v1.timeTo(v2) + v2.timeTo(arrivee);
  }

  public static double calculateTotalDistance(List<Ville> route) {
    double totalDistance = 0;
    for (int i = 0; i < route.size() - 1; i++) {
      totalDistance += route.get(i).distance(route.get(i + 1));
    }
    // Ajouter la distance de retour à la ville de départ
    totalDistance += route.get(route.size() - 1).distance(route.get(0));
    return totalDistance;
  }

  public static double calculateTotalTime(List<Ville> route) {
    double totalTime = 0;
    for (int i = 0; i < route.size() - 1; i++) {
      totalTime += route.get(i).timeTo(route.get(i + 1));
    }
    // Ajouter la distance de retour à la ville de départ
    totalTime += route.get(route.size() - 1).timeTo(route.get(0));
    return totalTime;
  }
}
