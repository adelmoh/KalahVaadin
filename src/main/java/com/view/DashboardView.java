package com.view;

import com.domain.Player;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by M on 10/15/2016.
 */
public class DashboardView extends VerticalLayout implements View {
	
	public static final String NAME = "DashBoardView";
	
	
	private Player player1;
	
	private Player player2;
	
	public DashboardView(Player player, Player otherPlayer) {
		if (player.getId() == 1) {
			this.player1 = player;
			this.player2 = otherPlayer;
		} else {
			this.player1 = otherPlayer;
			this.player2 = player;
		}
	}
	
	public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
		setSpacing(true);
		setMargin(true);
		
		
		addComponent(new Label("<h2>Welcome to Kalah</h2>", ContentMode.HTML));
		
		addComponent(new Label("<b>Player 1</b>", ContentMode.HTML));
		PlayerLayout player1Layout = new PlayerLayout(player1, true);
		addComponent(player1Layout);
		
		PlayerLayout player2Layout = new PlayerLayout(player2, false);
		addComponent(player2Layout);
		addComponent(new Label("<b>Player 2</b>", ContentMode.HTML));
		
	}
	
	
}
