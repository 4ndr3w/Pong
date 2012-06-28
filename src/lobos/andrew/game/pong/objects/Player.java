package lobos.andrew.game.pong.objects;

import java.awt.Color;

import lobos.andrew.game.baseObjects.Line;
import lobos.andrew.game.objects.ControllableCharacter;
import lobos.andrew.game.scene.ContainerObject;

public class Player extends ContainerObject implements ControllableCharacter
{
	Line bar = new Line(0.3f, 90, 0, 0);
	
	public Player(float xPos, float yPos) {
		super(xPos, yPos);
		bar.setColor(Color.GREEN);
		addObject(bar);
		initPos();
	}

	@Override
	public void moveRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveUp() {
		if ( bar.getStopY()+yPos >= 1.0f )
			return;
		
		yPos += 0.05;
		initPos();
		
	}

	@Override
	public void moveDown() {
		if ( yPos <= -1.0f )
			return;
		
		yPos -= 0.05;
		initPos();
	}

	@Override
	public void jump() {
		
	}

}
