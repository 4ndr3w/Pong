package lobos.andrew.game.pong.networking.server;

import java.net.ServerSocket;
import java.net.Socket;

import lobos.andrew.game.networking.PlayerTable;

public class PongServer {

	ServerSocket server;
	
	PlayerHandler player1, player2;
	
	public PongServer() throws Throwable
	{
		server = new ServerSocket(1218);
		System.out.println("Waiting for player 1...");
		player1 = new PlayerHandler(server.accept());
		System.out.println("Waiting for player 2...");
		player2 = new PlayerHandler(server.accept());
		
		player1.setOpponent(player2);
		player2.setOpponent(player1);
		
		System.out.println("Starting game");
	}
	
	void run()
	{
		while ( true )
		{
			System.out.println("Player 1 at "+player1.getString("yPos")+" player 2 at "+player2.getString("yPos"));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws Throwable {
		new PongServer().run();
	}

}
