package lobos.andrew.game.pong.networking.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Iterator;

import lobos.andrew.game.networking.PlayerTable;

public class NetworkLinkWriter extends Thread {
	Socket client;
	PlayerTable myTable;
	BufferedWriter writer;
	public NetworkLinkWriter(PlayerTable myTable, Socket client) throws IOException
	{
		this.client = client;
		this.myTable = myTable;
		writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		start();
	}
	
	public void run()
	{
		while ( true )
		{
			try {
				Iterator<String> keys = myTable.getHashMap().keySet().iterator();
				while ( keys.hasNext() )
				{
					String thisKey = keys.next();
					writer.write("w:"+thisKey+":"+myTable.getHashMap().get(thisKey)+"\n");
				}
				writer.write("e:e:e\n");
				
				Thread.sleep(500);
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
