package com;

import lombok.Data;
import org.apache.xpath.operations.Bool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M on 10/15/2016.
 */
@Data
public class Player {

    private List<Integer> pits = new ArrayList<Integer>();

    private Integer home = 0;

    private Player otherPlayer;

    private Boolean enabled;

    private Integer id;

    /**
     * checks if the last stoned landed in home so he will get another turn
     */
    private boolean landedInHome;

    public void initializePits(int numberOfPits, int initialNumberOfStones) {
        for(int count = 1; count<=numberOfPits; count++){
            pits.add(initialNumberOfStones);
        }
    }
}
