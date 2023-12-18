package Defi3.ia.algo.jeux;


import Defi3.ia.framework.common.Action;
import Defi3.ia.framework.jeux.Game;
import Defi3.ia.framework.jeux.GameState;
import Defi3.ia.framework.jeux.Player;

/**
 * Définie un joueurAléatoire
 *
 */

public class RandomPlayer extends Player {

    /**
     * Crée un joueur Aléatoire 
     * @param g l'instance du jeux
     * @param p1 vrai si joueur 1
     */
    public RandomPlayer(Game g, boolean p1){
        super(g, p1);
        name = "Random";
    }
    
    
    
    /**
     * {@inheritDoc}
     * <p>Retourn un coup aléatoire</p>
     */
    public Action getMove(GameState state){
        return game.getRandomMove(state);
    }


}
