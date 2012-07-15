package lobos.andrew.game.pong.networking.client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import lobos.andrew.game.networking.PlayerTable;

public class ServerLink
{
	Socket socket;
	
	NetworkLinkReader reader;
	NetworkLinkWriter writer;
	
	boolean isServer = false;

	public ServerLink(boolean isServer)
	{	
		this.isServer = isServer;
		
		try {
			if ( isServer )
			{
				ServerSocket sServer = new ServerSocket(1218);
				System.out.println("waiting for accept...");
				socket = sServer.accept();
				System.out.println("got client!");
			}
			else 
			{
				socket = new Socket("127.0.0.1", 1218);
				if ( socket.isConnected() )
					System.out.println("connect success");
			}
			reader = new NetworkLinkReader(socket);
			writer = new NetworkLinkWriter(socket);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public PlayerTable getMyTable()
	{
		return writer.getTable();
	}
	
	public PlayerTable getOpponentTable()
	{
		return reader.getTable();
	}
	
	public boolean isServer()
	{
		return isServer;
	}
}
