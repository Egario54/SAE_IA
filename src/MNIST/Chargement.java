package MNIST;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Chargement {
    public static Images chargerImages(){
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream("Ensemble des fichiers MNIST-20231009/train-images.idx3-ubyte"));
            int typeFichier = dis.readInt();
            if(typeFichier != 2051) throw new Exception("Pas bon ça");
            int nbImages = dis.readInt();
            //if(nbImages>5000) nbImages=5000; //réduire le nombre d'images si nécessaire
            System.out.println("Computing " + nbImages + " images");
            int nbLignes = dis.readInt();
            int nbColonnes = dis.readInt();

            Images stockage = new Images(typeFichier, nbLignes, nbColonnes);
            for (int i = 0; i<nbImages; i++){
                //Creation imagettes
                Imagette ima = new Imagette(nbLignes, nbColonnes);
                for (int j = 0; j<nbLignes; j++){
                    for (int k = 0; k<nbColonnes; k++){
                        int[][] a = ima.getEntiers();
                        a[k][j] = dis.readUnsignedByte();
                        ima.setEntiers(a);
                    }
                }
                stockage.getImagettes().add(ima);
            }
            dis.close();
            return stockage;
        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Fichier mal lu");
        }
        return null;
    }

    public static Images chargerEtiquettes(Images stockage){
        //début d'un bordel immense
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream("Ensemble des fichiers MNIST-20231009/train-labels.idx1-ubyte"));
            int typeFichier = dis.readInt();
            if(typeFichier != 2049) throw new Exception("Pas bon ça");
            int nbEtiquettes = dis.readInt();
            //if(nbEtiquettes>5000) nbEtiquettes=5000; //réduire le nombre d'étiquettes si nécessaire
            System.out.println("Computing " + nbEtiquettes + " labels");

            for (int i = 0; i<nbEtiquettes; i++){
                //Creation etiquettes
                Etiquette ima = new Etiquette(dis.readUnsignedByte());
                stockage.getEtiquettes().add(ima);
            }
            dis.close();
            return stockage;
        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Fichier mal lu");
        }
        return null;
    }
}
