package MNIST;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe servant à stocker des images et leur étiquette
 */
public class Images {
    private int typeFichier;

    private int nbImages;
    private int nbEtiquettes;
    private int nbLignes;
    private int nbColonnes;
    private List<Imagette> imagettes;

    private List<Etiquette> etiquettes;

    public Images(int numeroMagique, int nbLignes, int nbColonnes){
        this.imagettes = new ArrayList<Imagette>();
        this.etiquettes = new ArrayList<Etiquette>();
        this.typeFichier = numeroMagique;
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
    }

    public void saveImage(Imagette image, int nomFich) {
        try{
            BufferedImage bi = new BufferedImage(nbLignes, nbColonnes, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i<nbLignes; i++){
                for (int j = 0; j<nbColonnes; j++){
                    bi.setRGB(i,j,image.getEntiers()[i][j]);
                }
            }
            ImageIO.write(bi, "PNG", new File("res/IMG_"+nomFich+".png"));
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public List<Imagette> getImagettes() {
        return imagettes;
    }

    public void setImagettes(List<Imagette> imagettes) {
        this.imagettes = imagettes;
    }

    public List<Etiquette> getEtiquettes() {
        return etiquettes;
    }

    public void setEtiquettes(List<Etiquette> etiquettes) {
        this.etiquettes = etiquettes;
    }

    public int getNbImages() {
        return nbImages;
    }

    public void setNbImages(int nbImages) {
        this.nbImages = nbImages;
    }

    public int getNbEtiquettes() {
        return nbEtiquettes;
    }

    public void setNbEtiquettes(int nbEtiquettes) {
        this.nbEtiquettes = nbEtiquettes;
    }

    public int getNbLignes() {
        return nbLignes;
    }

    public int getNbColonnes() {
        return nbColonnes;
    }
}
