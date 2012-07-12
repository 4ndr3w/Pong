package lobos.andrew.game.pong;

import java.awt.Color;
import java.awt.event.KeyEvent;

import lobos.andrew.game.baseObjects.Circle;
import lobos.andrew.game.networking.PlayerTable;
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
	PlayerTable myTable = new PlayerTable();
	public NetworkPlay()
	{
		System.out.println("Waiting for opponent...");
		networking = new ServerLink(myTable);
		System.out.println("Play!");
		setBackgroundColor(Color.BLACK);
		ball.setFill(true);
		ball.setColor(Color.GREEN);
		
		addObject(ball);
		
		addObject(player1);
		addObject(player2);
		
		setCharacter(player1);

		ball.applyForce(new Force(xRate, yRate));
	}
	
	@Override
	public void keyPressed(KeyEvent event) 
	{
		super.keyPressed(event);
		
		if ( event.getKeyChar() == 'i' )
			player2.moveUp();
		else if ( event.getKeyChar() == 'k' )
			player2.moveDown();
		
		if ( event.getKeyChar() == 'g' )
			System.out.println("Ball is at ("+ball.getX()+", "+ball.getY()+")");
	}
	
	
	@Override
	public void sceneLogic() {
		//networking.send(player1.getY());
		myTable.put("yPos", "hello");
		System.out.println("got "+networking.getOpponentTable().getString("yPos"));
		player2.setLocation(player2.getX(), networking.getOpponentTable().getFloat("yPos"));
		
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
	}
}
