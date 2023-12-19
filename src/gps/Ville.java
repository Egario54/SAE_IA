package gps;

public record Ville(String nom, double latitude, double longitude){

    public double distance(Ville v) {
        double lat1 = Math.toRadians(latitude);
        double lat2 = Math.toRadians(v.latitude);
        double long1 = Math.toRadians(longitude);
        double long2 = Math.toRadians(v.longitude);
        double d = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin((lat1 - lat2) / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin((long1 - long2) / 2), 2)));
        return d * 6371;
    }

    public String toString() {
        return nom + " (" + latitude + ", " + longitude + ")";
    }
}
