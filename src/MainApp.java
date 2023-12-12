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
      //parametrer.parametrer();
      parametrer.parametrerDefaut();
    }
    MLP mlp = new MLP(parametrer.getNbNeurones(), parametrer.getTauxApprentissage(), parametrer.getFonctionActivation());

    //Selon le paramètre,
    switch (/*parametrer.getLearning()*/"mnist"){
      case "etouxor":
        //Tbales de vérité, exemple rapide d'entraînement
        final double[] ET = new double[]{0,0,0,1};
        final double[] OU = new double[]{0,1,1,1};
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
        mnist(new MLP(new int[]{256, 64, 32, 10}, 0.3, parametrer.getFonctionActivation()));
        break;
    }



  }

  private static void mnist(MLP mlp) {

    //Chargement de MNIST
    Images stockage = chargerImages();
    stockage = chargerEtiquettes(stockage);

    //chargement dans une hashmap
    //HashMap<Integer, int[][]> mnist = HashMap.newHashMap(10);
    //for (int i = 0; i < 1000; i++) {
    //
    //}

    List<Imagette> imagettes = stockage.getImagettes();
    for (int i = 0; i < 1000; i++) {
      //convertir l'imagette en donnée d'entrainement
      int[][] imagette = imagettes.get(i).getEntiers();
      double[] imagetteDouble = new double[imagette.length*imagette[0].length];
      for (int j = 0; j < imagette.length; j++) {
        for (int k = 0; k < imagette[j].length; k++) {
          imagetteDouble[imagette.length*j+k] = imagette[j][k];
        }
      }
      //entraîner
      if(i%100 == 99)
      System.out.println((i+1)+" : "+mlp.backPropagate(imagetteDouble, new double[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
    }


  }
}
