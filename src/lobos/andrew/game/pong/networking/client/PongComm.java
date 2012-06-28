package lobos.andrew.game.pong.networking.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class PongComm extends Thread {
	Socket socket;
	float yPos = 0;
	BufferedWriter writer;
	BufferedReader reader;
	public PongComm()
	{
		try {
			socket = new Socket("127.0.0.1", 1218);
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if ( !socket.isConnected() )
		{
			System.out.println("Connect failed");
			System.exit(0);
		}
		start();
	}
	
	public float getY()
	{
		return yPos;
	}
	
	public void send(float pos)
	{
		String data = new Float(pos).toString();
		try {
			writer.write(data);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void run()
	{
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Connect failed");
			System.exit(0);
		}
		
		while ( true )
		{
			try {
				yPos = Float.parseFloat(reader.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
