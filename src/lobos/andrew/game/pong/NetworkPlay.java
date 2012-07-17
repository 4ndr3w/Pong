package lobos.andrew.game.pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.net.InetAddress;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;

import lobos.andrew.game.baseObjects.Circle;
import lobos.andrew.game.baseObjects.Text;
import lobos.andrew.game.physics.Force;
import lobos.andrew.game.pong.networking.client.ServerLink;
import lobos.andrew.game.pong.objects.Player;
import lobos.andrew.game.scene.Scene;

public class NetworkPlay extends Scene implements Runnable {
	Player player1 = new Player(-0.8f, 0);
	Player player2 = new Player(0.8f, 0);
	
	Circle ball = new Circle(0.02f, 0, 0);
	
	Text waitingText = new Text(new Font("SansSerif", Font.BOLD, 48), -0.3f, 0);
	
	float yRate = 0.008f;
	float xRate = 0.005f;
	ServerLink networking = null;
	InetAddress connectToAddr = null;
	boolean isServer;
	
	public NetworkPlay() // Server constructor
	{
		isServer = true;
		
		waitingText.setColor(Color.GREEN);
		waitingText.setText("Waiting for partner...");
		
		setBackgroundColor(Color.BLACK);
		ball.setFill(true);
		ball.setColor(Color.GREEN);
		
		addObject(ball);
		
		addObject(player1);
		addObject(player2);
		
		setCharacter( ( isServer ? player1 : player2 ) );

		ball.applyForce(new Force(xRate, yRate));
		new Thread(this).start();
	}
	
	public NetworkPlay(InetAddress serverAddr) // Client constructor
	{
		isServer = false;
		connectToAddr = serverAddr;
		waitingText.setColor(Color.GREEN);
		waitingText.setText("Waiting for partner...");
		
		setBackgroundColor(Color.BLACK);
		ball.setFill(true);
		ball.setColor(Color.GREEN);
		
		addObject(ball);
		
		addObject(player1);
		addObject(player2);
		
		setCharacter( ( isServer ? player1 : player2 ) );

		ball.applyForce(new Force(xRate, yRate));
		new Thread(this).start();
	}
	
	
	@Override
	public void keyPressed(KeyEvent event) 
	{
		super.keyPressed(event);
		
		if ( event.getKeyChar() == 'g' )
			System.out.println("Ball is at ("+ball.getX()+", "+ball.getY()+")");
	}
	
	public void render(GL gl, GLAutoDrawable renderable)
	{
		if ( networking != null )
			super.render(gl, renderable);
		else waitingText.render(gl, renderable);
			
	}
	
	@Override
	public void sceneLogic() 
	{
		if ( networking.isServer() )
		{
			networking.getMyTable().put("yPos", player1.getY());
			player2.setLocation(player2.getX(), networking.getOpponentTable().getFloat("yPos"));
		}
		else
		{
			networking.getMyTable().put("yPos", player2.getY());
			player1.setLocation(player1.getX(), networking.getOpponentTable().getFloat("yPos"));
		}
		
		if ( networking.isServer() )
		{
			if ( player1.isTouching(ball) )
			{
				System.out.println("Player1 hit");
				ball.applyForce(new Force(-ball.getForce().getX(), ball.getForce().getY()));
			}
			else if ( player2.isTouching(ball) )
			{
				System.out.println("Player2 hit");
				ball.applyForce(new Force(-ball.getForce().getX(), ball.getForce().getY()));
			}

			if ( ball.getY() > 1.0 )
			{
				ball.setLocation(ball.getX(), 1.0f);
				ball.applyForce(new Force(ball.getForce().getX(), -ball.getForce().getY()));
			}
			else if ( ball.getY() < -1.0 )
			{
				ball.setLocation(ball.getX(),-1.0f);
				ball.applyForce(new Force(ball.getForce().getX(), -ball.getForce().getY()));
			}
			else if ( ball.isOutOfBounds() )
				ball.setLocation(0, 0);
			networking.getMyTable().put("ballY", ball.getY());
			networking.getMyTable().put("ballX", ball.getX());
		}
		else
		{
			float x = networking.getOpponentTable().getFloat("ballX");
			float y = networking.getOpponentTable().getFloat("ballY");
			ball.setLocation(x, y);
		}
	}

	@Override
	public void run() {
		if ( connectToAddr == null )
			networking = new ServerLink();
		else
			networking = new ServerLink(connectToAddr);
	}
}
