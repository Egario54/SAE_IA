package fonction;

public class Hyperbolique  implements Fonction{


  @Override
  public double fonction(double x) {
    return Math.tanh(x);
  }

  @Override
  public double derivee(double x) {
    return 1 - Math.pow(fonction(x), 2);
  }
}
