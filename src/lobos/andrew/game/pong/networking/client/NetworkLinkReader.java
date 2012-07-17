package lobos.andrew.game.pong.networking.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import lobos.andrew.game.core.Renderer;
import lobos.andrew.game.networking.PlayerTable;
import lobos.andrew.game.pong.Menu;

public class NetworkLinkReader extends Thread {
	Socket server;
	PlayerTable table = new PlayerTable();
	BufferedReader reader;
	boolean running = true;
	public NetworkLinkReader(Socket server) throws IOException
	{
		this.server = server;
		reader = new BufferedReader(new InputStreamReader(server.getInputStream()));
		start();
	}
	
	public PlayerTable getTable()
	{
		return table;
	}
	
	public void run()
	{
		while ( running )
		{
			try {
				String line = reader.readLine();
				if ( line != null )
				{
					String[] input = reader.readLine().split(":");
					table.put(input[0], input[1]);
				}
				else
				{
					running = false;
					Renderer.getInstance().setScene(new Menu());
					System.out.println("Got null string from socket!");
				}
			} catch (IOException e) {
				running = false;
				Renderer.getInstance().setScene(new Menu());
				e.printStackTrace();
			}
		}
	}

	public void disconnect() {
		running = false;
	}
}
