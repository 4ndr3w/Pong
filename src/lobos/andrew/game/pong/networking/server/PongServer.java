package lobos.andrew.game.pong.networking.server;

import java.net.ServerSocket;
import java.net.Socket;

public class PongServer extends Thread {

	ServerSocket server;
	
	Player player1;
	Player player2;
	
	public PongServer() throws Throwable
	{
		server = new ServerSocket(1218);
	}
	
	public void run()
	{
	}
	
	public static void main(String[] args) throws Throwable {
		new PongServer().start();
	}

}
