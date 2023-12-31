package Defi3.ia.algo.jeux;


import Defi3.ia.framework.common.Action;
import Defi3.ia.framework.jeux.Game;
import Defi3.ia.framework.jeux.GameState;
import Defi3.ia.framework.jeux.Player;

/**
 * Définie un joueur Humain
 *
 */

public class HumanPlayer extends Player {

    /**
     * Crée un joueur human
     * @param g l'instance du jeu
     * @param p1 vrai si joueur 1
     */
    public HumanPlayer(Game g, boolean p1){
        super(g, p1);
        name = "Human";
    }
    
    /**
     * {@inheritDoc}
     * <p>Demande un coup au joueur humain</p>
     */
    public Action getMove(GameState state){
        return game.getHumanMove(state);
    }


}
