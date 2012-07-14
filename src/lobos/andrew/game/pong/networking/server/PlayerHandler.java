package lobos.andrew.game.pong.networking.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import lobos.andrew.game.networking.PlayerTable;

public class PlayerHandler extends PlayerTable implements Runnable  {
	Socket socket;
	Thread thread;
	ObjectInputStream reader;
	ObjectOutputStream writer;
	PlayerHandler opponent = null;
	public PlayerHandler(Socket s)
	{
		socket = s;
		try {
			writer = new ObjectOutputStream(socket.getOutputStream());
			reader = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		thread = new Thread(this);
		thread.start();
	}
	
	public void setOpponent(PlayerHandler o)
	{
		opponent = o;
	}
	
	public void send(PlayerTable table)
	{
		try {
			writer.writeObject(table.getHashMap());
		} catch (IOException e) {
			System.exit(0);
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		while (true)
		{
			try {
				mix((HashMap<String,Object>) reader.readObject());
				if ( opponent != null )
					send(opponent);
				Thread.sleep(100);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
