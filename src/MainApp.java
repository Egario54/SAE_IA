import MLP.MLP;
import app.Parametrer;

public class MainApp {

  public static void main(String[] args) {
    Parametrer parametrer = new Parametrer();
    if(args.length == 0) {
      parametrer.parametrerDefaut();
    }
    else {
      parametrer.parametrer();
    }
    MLP mlp = new MLP(parametrer.getNbNeurones(), parametrer.getTauxApprentissage(), parametrer.getFonctionActivation());

    final double[] ET = new double[]{0,0,0,1};
    final double[] OU = new double[]{1,0,1,1};
    final double[] XOR = new double[]{0,1,1,0};


  }
}
