package lobos.andrew.game.pong.networking.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;

import lobos.andrew.game.networking.PlayerTable;

public class PlayerHandler extends PlayerTable implements Runnable  {
	Socket socket;
	Thread thread;
	ObjectInputStream reader;
	ObjectOutputStream writer;
	public PlayerHandler(Socket s)
	{
		socket = s;
		System.out.println("recv");
		try {
			writer = new ObjectOutputStream(socket.getOutputStream());
			reader = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("run");
		thread = new Thread(this);
		thread.start();
	}
	
	public void send(PlayerTable table)
	{
		try {
			writer.writeObject(table.getHashMap());
		} catch (IOException e) {
			System.out.println("Something Broke!");
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		while (true)
		{
			try {
				map = (HashMap<String,Object>) reader.readObject();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
