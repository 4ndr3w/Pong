package lobos.andrew.game.pong.networking.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Iterator;

import lobos.andrew.game.core.Renderer;
import lobos.andrew.game.networking.PlayerTable;
import lobos.andrew.game.pong.Menu;

public class NetworkLinkWriter extends Thread {
	Socket client;
	PlayerTable table = new PlayerTable();
	BufferedWriter writer;
	boolean running = true;
	public NetworkLinkWriter(Socket client) throws IOException
	{
		this.client = client;
		writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		start();
	}
	
	public void run()
	{
		while ( running )
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
			} catch (Throwable e) {
				running = false;
				Renderer.getInstance().setScene(new Menu());
			}
		}
	}
	
	public PlayerTable getTable()
	{
		return table;
	}

	public void disconnect() {
		running = false;
	}
	
}
