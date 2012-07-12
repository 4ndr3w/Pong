package lobos.andrew.game.pong.networking.server;

import java.net.Socket;

public class Player extends Thread {
	Socket socket;
	public Player(Socket s)
	{
		socket = s;
	}
	
	public void run()
	{
		
	}
}
