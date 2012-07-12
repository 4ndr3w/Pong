package lobos.andrew.game.pong.networking.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import lobos.andrew.game.networking.PlayerTable;

public class ServerLink extends Thread
{
	Socket socket;
	ObjectInputStream inStream;
	ObjectOutputStream outStream;

	public ServerLink()
	{	
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
		start();
	}
	
	public void update(PlayerTable myTable, PlayerTable opponentTable)
	{
			try {
				outStream.writeObject(myTable.getHashMap());
				System.out.println("Waiting for response...");
				@SuppressWarnings("unchecked")
				HashMap<String,Object> got = (HashMap<String, Object>) inStream.readObject();
				opponentTable.mix(got);
				System.out.println("Got response");
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
