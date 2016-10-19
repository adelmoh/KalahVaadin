import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import com.domain.Player;
import com.service.KalahService;

/**
 * Created by ADELM on 17/10/2016.
 */
public class KalahServiceTest {
	
	private KalahService kalahService = new KalahService();
	
	@Test
	public void detectTheWinner() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		//prepare
		Class[] cArg = new Class[2];
		cArg[0] = Player.class;
		cArg[1] = Player.class;
		Method getWinnerMethod = kalahService.getClass().getDeclaredMethod("detectTheWinner", cArg);
		getWinnerMethod.setAccessible(true);
		
		Player[] players = new Player[2];
		Player player1 = new Player();
		player1.setId(1);
		player1.setHome(26);
		
		Player player2 = new Player();
		player2.setId(2);
		player2.setHome(13);
		players[0] = player1;
		players[1] = player2;
		
		//ACT
		Player winner = (Player) getWinnerMethod.invoke(kalahService, players);
		
		//ASSERT
		assertNotNull(winner);
		assertTrue(winner.getId() == 1);
	}
	
	@Test
	public void hasGameEnded() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		//prepare
		Class[] cArg = new Class[2];
		cArg[0] = Player.class;
		cArg[1] = Player.class;
		Method method = kalahService.getClass().getDeclaredMethod("hasGamedEnded", cArg);
		method.setAccessible(true);
		
		Player[] players = new Player[2];
		
		Player player1 = new Player();
		player1.setId(1);
		
		Player player2 = new Player();
		player2.setId(2);
		
		players[0] = player1;
		players[1] = player2;
		
		//ACT
		Boolean hasGameEnded = (Boolean) method.invoke(kalahService, players);
		
		//ASSERT
		assertTrue(hasGameEnded);
	}
	
	@Test
	public void determineIfPlayerCanPlayAgain() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		//prepare
		Class[] cArg = new Class[3];
		cArg[0] = Player.class;
		cArg[1] = Integer.class;
		cArg[2] = boolean.class;
		Method method = kalahService.getClass().getDeclaredMethod("determineIfPlayerCanPlayAgain", cArg);
		method.setAccessible(true);
		
		Object[] args = new Object[3];
		
		Player player = new Player();
		player.setId(1);
		
		Integer currentValueInPit = 2;
		
		boolean isHomeUpdated = false;
		
		args[0] = player;
		args[1] = currentValueInPit;
		args[2] = isHomeUpdated;
		
		//ACT
		method.invoke(kalahService, args);
		
		//ASSERT
		assertFalse(player.isLandedInHome());
	}
	
	@Test
	public void captureOtherPlayerPitsIfPossible() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		//prepare
		Class[] cArg = new Class[4];
		cArg[0] = Player.class;
		cArg[1] = Integer.class;
		cArg[2] = Integer.class;
		cArg[3] = int.class;
		Method method = kalahService.getClass().getDeclaredMethod("captureOtherPlayerPitsIfPossible", cArg);
		method.setAccessible(true);
		
		Object[] args = new Object[4];
		
		
		Player player1 = new Player();
		player1.setId(1);
		int player1Home = 3;
		player1.initializePits(6, 1);
		player1.setHome(player1Home);
		
		Player player2 = new Player();
		player2.setId(2);
		int stonesOfPlayer2 = 4;
		player2.initializePits(6, stonesOfPlayer2);
		player1.setOtherPlayer(player2);
		
		Integer currentValueInPit = 0;
		Integer currentValueInOtherPit = 1;
		int index = 3;
		
		boolean isHomeUpdated = false;
		
		args[0] = player1;
		args[1] = currentValueInPit;
		args[2] = currentValueInOtherPit;
		args[3] = index;
		
		//ACT
		method.invoke(kalahService, args);
		
		//ASSERT
		assertTrue(player1.getHome() == (player1Home + stonesOfPlayer2 + currentValueInOtherPit));
	}
}
