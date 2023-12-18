package Defi3.ia.algo.jeux;

import Defi3.ia.framework.common.Action;
import Defi3.ia.framework.common.ActionValuePair;
import Defi3.ia.framework.jeux.Game;
import Defi3.ia.framework.jeux.GameState;
import Defi3.ia.framework.jeux.Player;

import java.util.ArrayList;
import java.util.Collections;

public class MinMaxPlayer extends Player {
    /**
     * Represente un joueur
     *
     * @param g          l'instance du jeu
     * @param player_one si joueur 1
     */
    public MinMaxPlayer(Game g, boolean player_one) {
        super(g, player_one);
    }

    @Override
    public Action getMove(GameState state) {
        ActionValuePair avp;
        if(player == PLAYER1){
            avp = maxValue(state);
        } else {
            avp = minValue(state);
        }
        return avp.getAction();
    }

    private ActionValuePair maxValue(GameState state){
        if(game.endOfGame(state)){
            return new ActionValuePair(null, state.getGameValue());
        }
        ActionValuePair avpMax = new ActionValuePair(null, Integer.MIN_VALUE);

        ArrayList<Action> actions = game.getActions(state);
        Collections.shuffle(actions);

        for (Action a: actions) {
            GameState stateSuivant = (GameState) game.doAction(state,a);
            ActionValuePair avp = minValue(stateSuivant);
            if(avp.getValue()> avpMax.getValue()){
                if(avp.getAction()==null) avpMax = new ActionValuePair(a, avp.getValue());
                else avpMax = avp;
            }
        }

        return avpMax;
    }

    private ActionValuePair minValue(GameState state){
        if(game.endOfGame(state)){
            return new ActionValuePair(null, state.getGameValue());
        }

        ActionValuePair avpMin = new ActionValuePair(null, Integer.MAX_VALUE);

        ArrayList<Action> actions = game.getActions(state);
        Collections.shuffle(actions);

        for (Action a: actions) {
            GameState stateSuivant = (GameState) game.doAction(state,a);
            ActionValuePair avp = maxValue(stateSuivant);
            if(avp.getValue()<avpMin.getValue()){
                if(avp.getAction()==null) avpMin = new ActionValuePair(a, avp.getValue());
                else avpMin = avp;
            }
        }

        return avpMin;
    }
}
