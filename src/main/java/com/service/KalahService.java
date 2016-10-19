package com.service;

import java.util.List;

import com.domain.Player;

/**
 * Created by M on 10/16/2016.
 */
public class KalahService {
	
	/**
	 * the main logic when clicking on any pit
	 *
	 * @param player the player where its pit is clicked on
	 * @param index  pit index
	 * @return null if no one won yet, return winning player if there is one.
	 */
	public Player updateDashboard(Player player, int index) {
		if (player.getId() == 1) {
			updateForPlayer1(player, index);
		} else {
			updateForPlayer2(player, index);
		}
		
		//disable the current player, and allow the other player to play
		if (!player.isLandedInHome()) {
			player.setEnabled(false);
			player.getOtherPlayer().setEnabled(true);
		}
		
		//check if game has ended
		if (hasGamedEnded(player, player.getOtherPlayer())) {
			return detectTheWinner(player, player.getOtherPlayer());
		} else
			return null;
	}
	
	private Player detectTheWinner(Player player, Player otherPlayer) {
		finalizeForPlayer(player);
		finalizeForPlayer(otherPlayer);
		if (player.getHome() > otherPlayer.getHome())
			return player;
		else
			return otherPlayer;
	}
	
	private void finalizeForPlayer(Player player) {
		Integer home = player.getHome();
		for (int i = 0; i < player.getPits().size(); i++) {
			home += player.getPits().get(i);
			player.getPits().set(i, 0);
		}
		player.setHome(home);
	}
	
	private boolean hasGamedEnded(Player player, Player otherPlayer) {
		return allPitsAreEmpty(player) || allPitsAreEmpty(otherPlayer);
	}
	
	private boolean allPitsAreEmpty(Player player) {
		for (Integer pit : player.getPits()) {
			if (pit > 0) {
				return false;
			}
		}
		return true;
	}
	
	private void updateForPlayer1(Player player, int index) {
		Integer currentValueInPit = player.getPits().get(index);
		player.getPits().set(index, 0);
		//current player
		for (int i = index - 1; i >= 0 && currentValueInPit > 0; i--) {
			Integer currentValueInOtherPit = player.getPits().get(i);
			player.getPits().set(i, ++currentValueInOtherPit);
			currentValueInPit--;
			
			captureOtherPlayerPitsIfPossible(player, currentValueInPit, currentValueInOtherPit, i);
		}
		
		currentValueInPit = updatedHome(player, currentValueInPit);
		
		//other player
		List<Integer> otherPlayerPits = player.getOtherPlayer().getPits();
		for (int i = 0; i < otherPlayerPits.size() && currentValueInPit > 0; i++) {
			Integer currentValueInOtherPit = otherPlayerPits.get(i);
			otherPlayerPits.set(i, ++currentValueInOtherPit);
			currentValueInPit--;
		}
	}
	
	/**
	 * check if it can capture other player stones as well
	 *
	 * @param player
	 * @param currentValueInPit
	 * @param currentValueInOtherPit
	 * @param index
	 */
	private void captureOtherPlayerPitsIfPossible(Player player, Integer currentValueInPit, Integer currentValueInOtherPit, int index) {
		if (currentValueInPit == 0 && currentValueInOtherPit == 1) {
			Integer home = player.getHome();
			home += currentValueInOtherPit;
			home += player.getOtherPlayer().getPits().get(index);
			
			player.setHome(home);
			player.getPits().set(index, 0);
			player.getOtherPlayer().getPits().set(index, 0);
		}
	}
	
	private void updateForPlayer2(Player player, int index) {
		Integer currentValueInPit = player.getPits().get(index);
		player.getPits().set(index, 0);
		//current player
		for (int i = index + 1; i < player.getPits().size() && currentValueInPit > 0; i++) {
			Integer currentValueInOtherPit = player.getPits().get(i);
			player.getPits().set(i, ++currentValueInOtherPit);
			currentValueInPit--;
			
			captureOtherPlayerPitsIfPossible(player, currentValueInPit, currentValueInOtherPit, i);
		}
		
		currentValueInPit = updatedHome(player, currentValueInPit);
		
		//other player
		List<Integer> otherPlayerPits = player.getOtherPlayer().getPits();
		for (int i = otherPlayerPits.size() - 1; i >= 0 && currentValueInPit > 0; i--) {
			Integer currentValueInOtherPit = otherPlayerPits.get(i);
			otherPlayerPits.set(i, ++currentValueInOtherPit);
			currentValueInPit--;
		}
	}
	
	private int updatedHome(Player player, int currentValueInPit) {
		boolean isHomeUpdated = false;
		//home of current player
		if (currentValueInPit > 0) {
			Integer homeValue = player.getHome();
			player.setHome(++homeValue);
			currentValueInPit--;
			isHomeUpdated = true;
		}
		determineIfPlayerCanPlayAgain(player, currentValueInPit, isHomeUpdated);
		return currentValueInPit;
	}
	
	private void determineIfPlayerCanPlayAgain(Player player, Integer currentValueInPit, boolean isHomeUpdated) {
		if (isHomeUpdated && currentValueInPit == 0) {
			player.setLandedInHome(true);
		} else {
			player.setLandedInHome(false);
		}
	}
}
