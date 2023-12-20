package gps.recherche;

import gps.initData.Ville;

import java.util.List;

class State implements Comparable<State> {
  Ville currentCity;
  List<Ville> visitedCities;
  double cost;
  double priority;

  public State(Ville currentCity, List<Ville> visitedCities, double cost) {
    this.currentCity = currentCity;
    this.visitedCities = visitedCities;
    this.cost = cost;
  }

  public State(Ville currentCity, List<Ville> visitedCities, double cost, double priority) {
    this(currentCity, visitedCities, cost);
    this.priority = priority;
  }

  @Override
  public int compareTo(State other) {
    return Double.compare(this.priority, other.priority);
  }
}
