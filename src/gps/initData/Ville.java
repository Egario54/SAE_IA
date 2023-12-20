package gps.initData;

import gps.recherche.Solver;

public record Ville(String nom, double latitude, double longitude, int population){

    public double distance(Ville v) {
        double lat1 = Math.toRadians(latitude);
        double lat2 = Math.toRadians(v.latitude);
        double long1 = Math.toRadians(longitude);
        double long2 = Math.toRadians(v.longitude);
        double d = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin((lat1 - lat2) / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin((long1 - long2) / 2), 2)));
        return d * 6371;
    }

    public double timeTo(Ville v) {
        if(population > Solver.minPop130 && v.population() > Solver.minPop130) return distance(v) / 130;
        if(population > Solver.minPop110 && v.population() > Solver.minPop110) return distance(v) / 110;
        return distance(v) / 90;
    }

    public String toString() {
        return nom;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Ville)) return false;
        Ville v = (Ville) o;
        return nom.equals(v.nom) && latitude == v.latitude && longitude == v.longitude;
    }
}
