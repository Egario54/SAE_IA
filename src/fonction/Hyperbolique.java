package fonction;

import MLP.TransferFunction;

public class Hyperbolique  implements TransferFunction {


  @Override
  public double evaluate(double x) {
    return Math.tanh(x);
  }

  @Override
  public double evaluateDer(double x) {
    return 1 - Math.pow(evaluate(x), 2);
  }
}
