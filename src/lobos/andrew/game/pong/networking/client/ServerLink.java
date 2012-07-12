package lobos.andrew.game.pong.networking.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

import lobos.andrew.game.networking.PlayerTable;

public class ServerLink extends Thread
{
	Socket socket;
	ObjectInputStream inStream;
	ObjectOutputStream outStream;
	
	PlayerTable myTable, opponentTable;

	public ServerLink(PlayerTable _myTable)
	{
		myTable = _myTable;
		opponentTable = new PlayerTable();
		
		try {
			socket = new Socket("127.0.0.1", 1218);
			outStream = new ObjectOutputStream(socket.getOutputStream());
			inStream = new ObjectInputStream(socket.getInputStream());

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("waiting for read");
		opponentTable.updateMapFromReader(inStream);
		System.out.println("done");
		start();
	}
	
	public PlayerTable getOpponentTable()
	{
		return opponentTable;
	}
	
	public void run()
	{
		while ( true )
		{
			try {
				outStream.writeObject(myTable.getHashMap());
				
				Map<String,Object> got = (Map<String, Object>) inStream.readObject();
				opponentTable.getHashMap().putAll(got);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
