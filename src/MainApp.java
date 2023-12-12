import MLP.MLP;
import MNIST.Images;
import MNIST.Imagette;
import app.Parametrer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static MNIST.Chargement.*;

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

    switch (parametrer.getLearning()){
      case "etouxor":
        final double[] ET = new double[]{0,0,0,1};
        final double[] OU = new double[]{1,0,1,1};
        final double[] XOR = new double[]{0,1,1,0};

        double[] output = {0.5};
        for (int i = 0; i < 10; i++) {
          mlp.backPropagate(ET, output);
        }
        for (int i = 0; i < 10; i++) {
          mlp.backPropagate(OU, output);
        }
        for (int i = 0; i < 10; i++) {
          mlp.backPropagate(XOR, output);
        }

        System.out.println("Résultat avec ET : "+mlp.backPropagate(ET,output));
        System.out.println("Résultat avec OU : "+mlp.backPropagate(OU,output));
        System.out.println("Résultat avec XOR : "+mlp.backPropagate(XOR,output));
        break;

      case "mnist":
        mnist(mlp);
        break;
    }



  }

  private static void mnist(MLP mlp) {

    double[] output = {0.5};

    Images stockage = chargerImages();
    stockage = chargerEtiquettes(stockage);

    //chargement dans une hashmap
    //HashMap<Integer, int[][]> mnist = HashMap.newHashMap(10);
    //for (int i = 0; i < 1000; i++) {
    //
    //}

    List<Imagette> imagettes = stockage.getImagettes();
    for (int i = 0; i < 1000; i++) {
      int[][] imagette = imagettes.get(i).getEntiers();
      double[] imagetteDouble = new double[imagette.length*imagette[0].length];
      for (int j = 0; j < imagette.length; j++) {
        for (int k = 0; k < imagette[j].length; k++) {
          imagetteDouble[imagette.length*j+k] = imagette[j][k];
        }
      }
      System.out.println((i+1)+" : "+mlp.backPropagate(imagetteDouble, output));
    }


  }
}
