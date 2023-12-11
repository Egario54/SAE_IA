package app;

import MLP.TransferFunction;

import java.util.Scanner;

public class Parametrer {

  private int[] nbNeurones;
  private double tauxApprentissage;

  private TransferFunction fonctionActivation;


  public void parametrer() {
    String in;
    System.out.println("Parametrage du MLP");
    System.out.println("Nombre de couches : (default 3)");
    Scanner sc = new Scanner(System.in);
    String nbCouches = "3";
    in = sc.nextLine();
    if (!in.equals("")) {
      nbCouches = in;
    }
    nbNeurones = new int[Integer.parseInt(nbCouches)];
    for (int i = 0; i < nbNeurones.length; i++) {
      System.out.println("Nombre de neurones dans la couche " + i + " : (default 2 (1 pour la couche de sortie))");
      in = sc.nextLine();
      if (!in.equals("")) {
        nbNeurones[i] = Integer.parseInt(in);
      } else {
        if (i == nbNeurones.length - 1) {
          nbNeurones[i] = 1;
        } else {
          nbNeurones[i] = 2;
        }
      }
    }
    System.out.println("Taux d'apprentissage : (default 0.3)");
    in = sc.nextLine();
    if (!in.equals("")) {
      tauxApprentissage = Double.parseDouble(in);
    } else {
      tauxApprentissage = 0.3;
    }
    System.out.println("Fonction d'activation : ");
    System.out.println("1 : Sigmoide");
    System.out.println("2 : Tanh");
    String fonction = sc.nextLine();
    switch (fonction) {
      case "1" -> fonctionActivation = new fonction.Sigmoide();
      case "2" -> fonctionActivation = new fonction.Hyperbolique();
      default -> System.out.println("Erreur");
    }
  }

  public void parametrerDefaut() {
    nbNeurones = new int[]{2, 2, 1};
    tauxApprentissage = 0.3;
    fonctionActivation = new fonction.Sigmoide();
  }

  public int[] getNbNeurones() {
    return nbNeurones;
  }

  public double getTauxApprentissage() {
    return tauxApprentissage;
  }

  public TransferFunction getFonctionActivation() {
    return fonctionActivation;
  }
}
