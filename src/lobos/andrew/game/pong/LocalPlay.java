package lobos.andrew.game.pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import lobos.andrew.game.baseObjects.Circle;
import lobos.andrew.game.baseObjects.Text;
import lobos.andrew.game.core.Renderer;
import lobos.andrew.game.physics.Force;
import lobos.andrew.game.pong.objects.Player;
import lobos.andrew.game.scene.Scene;

public class LocalPlay extends Scene {
	Player player1 = new Player(-0.8f, 0);
	Player player2 = new Player(0.8f, 0);
	
	Circle ball = new Circle(0.02f, 0, 0);
	
	Text player1ScoreT = new Text(new Font("SansSerif", Font.BOLD, 24), -0.3f, 0.45f);
	Text player2ScoreT = new Text(new Font("SansSerif", Font.BOLD, 24), 0.3f, 0.45f);
	
	int player1Score = 0;
	int player2Score = 0;
	
	float yRate = 0.008f;
	float xRate = 0.005f;
	
	public LocalPlay()
	{
		player1ScoreT.setText("0");
		player1ScoreT.setColor(Color.GREEN);
		
		player2ScoreT.setText("0");
		player2ScoreT.setColor(Color.GREEN);
		
		setBackgroundColor(Color.BLACK);
		ball.setFill(true);
		ball.setColor(Color.GREEN);
		
		addObject(ball);
		
		addObject(player1);
		addObject(player2);
		addObject(player1ScoreT);
		addObject(player2ScoreT);
		
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
		else if ( event.getKeyCode() == 27 )
			Renderer.getInstance().setScene(new Menu());
		
		if ( event.getKeyChar() == 'g' )
			System.out.println("Ball is at ("+ball.getX()+", "+ball.getY()+")");
		else if ( event.getKeyChar() == 'r' )
		{
			player1Score = 0;
			player2Score = 0;
			
			player1ScoreT.setText(new Integer(player1Score).toString());
			player2ScoreT.setText(new Integer(player2Score).toString());
		}
	}
	
	@Override
	public void sceneLogic() {
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
		{
			if ( ball.getX() > 1.0 )
			{
				player1Score++;
				player1ScoreT.setText(new Integer(player1Score).toString());
			}
			else if ( ball.getX() < -1.0 )
			{
				player2Score++;
				player2ScoreT.setText(new Integer(player2Score).toString());
			}
			ball.applyForce(new Force(-ball.getForce().getX(), ball.getForce().getY()));
			ball.setLocation(0, 0);
		}
	}
}
