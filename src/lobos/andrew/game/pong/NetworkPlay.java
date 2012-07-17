package lobos.andrew.game.pong;

import java.awt.Color;
import java.awt.event.KeyEvent;

import lobos.andrew.game.baseObjects.Circle;
import lobos.andrew.game.physics.Force;
import lobos.andrew.game.pong.networking.client.ServerLink;
import lobos.andrew.game.pong.objects.Player;
import lobos.andrew.game.scene.Scene;

public class NetworkPlay extends Scene {
	Player player1 = new Player(-0.8f, 0);
	Player player2 = new Player(0.8f, 0);
	
	Circle ball = new Circle(0.02f, 0, 0);
	
	float yRate = 0.008f;
	float xRate = 0.005f;
	ServerLink networking;
	public NetworkPlay(boolean isServer)
	{
		System.out.println("Waiting for opponent...");
		networking = new ServerLink(isServer);
		System.out.println("Play!");
		setBackgroundColor(Color.BLACK);
		ball.setFill(true);
		ball.setColor(Color.GREEN);
		
		addObject(ball);
		
		addObject(player1);
		addObject(player2);
		
		setCharacter( ( isServer ? player1 : player2 ) );
		
		networking.getMyTable().put("yPos", "init");
		ball.applyForce(new Force(xRate, yRate));
	}
	
	@Override
	public void keyPressed(KeyEvent event) 
	{
		super.keyPressed(event);
		
		if ( event.getKeyChar() == 'g' )
			System.out.println("Ball is at ("+ball.getX()+", "+ball.getY()+")");
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
}
