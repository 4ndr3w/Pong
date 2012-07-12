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
		
		System.out.println("Starting game");
	}
	
	void run()
	{
		while ( true )
		{
			player1.send(player2);
			player2.send(player1);
			//System.out.println("Player 1 at "+player1.getFloat("yPos")+" player 2 at "+player2.getFloat("yPos"));
			try {
				Thread.sleep(50);
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
