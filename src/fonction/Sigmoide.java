package fonction;

public class Sigmoide implements Fonction {

  public double fonction(double x) {
    return 1.0 / (1.0 + Math.exp(-x));
  }

  public double derivee(double x) {
    return fonction(x) - Math.pow(fonction(x), 2);
  }
}
