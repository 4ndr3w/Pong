package lobos.andrew.game.pong.networking.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Iterator;

import lobos.andrew.game.networking.PlayerTable;

public class NetworkLinkWriter extends Thread {
	Socket client;
	PlayerTable table = new PlayerTable();
	BufferedWriter writer;
	public NetworkLinkWriter(Socket client) throws IOException
	{
		this.client = client;
		writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		start();
	}
	
	public void run()
	{
		while ( true )
		{
			try {
				Iterator<String> keys = table.getHashMap().keySet().iterator();
				while ( keys.hasNext() )
				{
					String thisKey = keys.next();
					writer.write(thisKey+":"+table.getHashMap().get(thisKey)+"\n");
				}
				writer.flush();
				
				Thread.sleep(5);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public PlayerTable getTable()
	{
		return table;
	}
	
}
