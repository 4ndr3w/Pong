package lobos.andrew.game.pong.networking.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import lobos.andrew.game.networking.PlayerTable;

public class NetworkLinkReader extends Thread {
	Socket server;
	PlayerTable table = new PlayerTable();
	BufferedReader reader;
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
		while ( true )
		{
			try {
				//System.out.println("waiting for line...");
				String line = reader.readLine();
				if ( line != null )
				{
					String[] input = reader.readLine().split(":");
					table.put(input[0], input[1]);
					//System.out.println("Added "+input[0]+" with value "+input[1]);
				}
				else System.out.println("Got null string from socket!");
				//Thread.sleep(100);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
