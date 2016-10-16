package com;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import lombok.Getter;

/**
 * Created by M on 10/15/2016.
 */
@Theme("valo")
public class MainVaadinUI extends UI {

    private Player player1;

    private Player player2;


    private static final int INITIAL_NUMBER_OF_STONES = 6;
    private static final int NUMBER_OF_PITS = 6;

    @Getter
    Navigator navigator;

    @Override
    public void init(VaadinRequest request) {

        initializePlayers();

        // Create a navigator to control the views
        navigator = new Navigator(this, this);

        // Create and register the views
        DashboardView dashboardView = new DashboardView(player1, player2);
        navigator.addView(DashboardView.NAME, dashboardView);

        navigator.navigateTo(DashboardView.NAME);
    }


    private void initializePlayers(){
        player1 = new Player();
        player1.initializePits(NUMBER_OF_PITS, INITIAL_NUMBER_OF_STONES);
        player1.setEnabled(true);
        player1.setId(1);

        player2 = new Player();
        player2.initializePits(NUMBER_OF_PITS, INITIAL_NUMBER_OF_STONES);
        player2.setEnabled(false);
        player2.setId(2);

        player1.setOtherPlayer(player2);
        player2.setOtherPlayer(player1);

    }


}
