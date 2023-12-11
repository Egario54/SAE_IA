package MNIST;

public class Imagette {
    private int[][] entiers;

    public Imagette(int lignes, int colonnes){
        entiers = new int[lignes][colonnes];
    }

    public int[][] getEntiers() {
        return entiers;
    }

    public void setEntiers(int[][] entiers) {
        this.entiers = entiers;
    }
}
