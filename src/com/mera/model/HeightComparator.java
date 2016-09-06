package com.mera.model;

import com.teamtreehouse.model.Player;
import java.util.Comparator;

/**
 * Created by Javi on 9/6/2016.
 */

public class HeightComparator implements Comparator<Player>
{
    @Override
    public int compare(Player player1, Player player2) {

        if(player1.getHeightInInches() > player2.getHeightInInches())
            return 1;
        else if(player1.getHeightInInches() < player2.getHeightInInches())
            return -1;

        return 0;
    }
}
