package lobos.andrew.game.pong.networking.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import lobos.andrew.game.networking.PlayerTable;

public class ServerLink
{
	Socket socket;
	
	NetworkLinkReader reader;
	NetworkLinkWriter writer;
	
	PlayerTable myTable = new PlayerTable();
	PlayerTable opponentTable = new PlayerTable();
	public ServerLink(boolean isServer)
	{	
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
			reader = new NetworkLinkReader(opponentTable, socket);
			writer = new NetworkLinkWriter(myTable, socket);
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
		return myTable;
	}
	
	public PlayerTable getOpponentTable()
	{
		return opponentTable;
	}
}
