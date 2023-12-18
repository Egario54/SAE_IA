package Defi3.ia.algo.jeux;

import Defi3.ia.framework.common.Action;
import Defi3.ia.framework.common.ActionValuePair;
import Defi3.ia.framework.jeux.Game;
import Defi3.ia.framework.jeux.GameState;
import Defi3.ia.framework.jeux.Player;

import java.util.ArrayList;
import java.util.Collections;

public class MinMaxAlphaBetaPlayer extends Player {
    /**
     * Represente un joueur
     *
     * @param g          l'instance du jeu
     * @param player_one si joueur 1
     */

    private int profondeur;
    private final int maxProfondeur;
    public MinMaxAlphaBetaPlayer(Game g, boolean player_one) {
        super(g, player_one);
        this.maxProfondeur = 10;
    }

    public MinMaxAlphaBetaPlayer(Game g, boolean player_one, int profondeurMax) {
        super(g, player_one);
        this.maxProfondeur = profondeurMax;
    }

    @Override
    public Action getMove(GameState state) {
        ActionValuePair avp;
        if(player == PLAYER1){
            avp = maxValue(state, Double.MIN_VALUE, Double.MAX_VALUE, 0);
        } else {
            avp = minValue(state, Double.MIN_VALUE, Double.MAX_VALUE, 0);
        }
        return avp.getAction();
    }

    private ActionValuePair maxValue(GameState state, double alpha, double beta, int profondeur){
        if(game.endOfGame(state)){
            return new ActionValuePair(null, state.getGameValue());
        }
        ActionValuePair avpMax = new ActionValuePair(null, Integer.MIN_VALUE);

        ArrayList<Action> actions = game.getActions(state);
        Collections.shuffle(actions);

        for (Action a: actions) {
            GameState stateSuivant = (GameState) game.doAction(state,a);
            ActionValuePair avp = minValue(stateSuivant, alpha, beta, profondeur++);
            if(avp.getValue()> avpMax.getValue()){
                if(avp.getAction()==null) avpMax = new ActionValuePair(a, avp.getValue());
                else avpMax = avp;
                if(avpMax.getValue() > alpha){
                    alpha = avpMax.getValue();
                }
            }
            if(avp.getValue()>=beta){
                return avpMax;
            }
        }

        return avpMax;
    }

    private ActionValuePair minValue(GameState state, double alpha, double beta, int profondeur){
        if(game.endOfGame(state)){
            return new ActionValuePair(null, state.getGameValue());
        }

        ActionValuePair avpMin = new ActionValuePair(null, Double.MAX_VALUE);

        ArrayList<Action> actions = game.getActions(state);
        Collections.shuffle(actions);

        for (Action a: actions) {
            GameState stateSuivant = (GameState) game.doAction(state,a);
            ActionValuePair avp = maxValue(stateSuivant, alpha, beta, profondeur++);
            if(avp.getValue()<avpMin.getValue()){
                if(avp.getAction()==null) avpMin = new ActionValuePair(a, avp.getValue());
                else avpMin = avp;
                if(avpMin.getValue() < beta){
                    beta = avpMin.getValue();
                }
            }
            if(avpMin.getValue()<=alpha){
                return avpMin;
            }
        }

        return avpMin;
    }
}
