package lobos.andrew.game.pong.networking.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

import lobos.andrew.game.networking.PlayerTable;

public class NetworkLinkReader extends Thread {
	Socket server;
	PlayerTable opponentTable;
	BufferedReader reader;
	public NetworkLinkReader(PlayerTable opponentTable, Socket server) throws IOException
	{
		this.server = server;
		this.opponentTable = opponentTable;
		reader = new BufferedReader(new InputStreamReader(server.getInputStream()));
		start();
	}
	
	public void run()
	{
		while ( true )
		{
			try {
				HashMap<String,Object> tmp = new HashMap<String,Object>();
				while ( true )
				{
					System.out.println("waiting for line...");
					String line = reader.readLine();
					System.out.println("got data: "+line);
					if ( line != null )
					{
						String[] input = reader.readLine().split(":");
						System.out.println("Got data: "+input[0]+":"+input[1]+":"+input[2]);
						if ( input[0].equals("e") )
							break;
						else if ( input[0].equals("w") )
							tmp.put(input[1], input[2]);
					}
					else System.out.println("Got null string from socket!");
				}
				opponentTable.mix(tmp);
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
