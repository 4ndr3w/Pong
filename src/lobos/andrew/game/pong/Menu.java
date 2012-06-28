package lobos.andrew.game.pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import lobos.andrew.game.baseObjects.Text;
import lobos.andrew.game.core.Renderer;
import lobos.andrew.game.scene.Scene;

public class Menu extends Scene
{
	Text bigText = new Text(new Font("SansSerif", Font.BOLD, 48), -0.4f, 0.19f);
	
	Text startSingleplayerGameOption = new Text(new Font("SansSerif", Font.BOLD, 12), -0.399f, 0.15f);
	Text startMultiplayerGameOption = new Text(new Font("SansSerif", Font.PLAIN, 12), -0.399f, 0.125f);
	Text exitOption = new Text(new Font("SansSerif", Font.PLAIN, 12), -0.399f, 0.1f);
	
	int selected = 0;
	
	public Menu()
	{
		startSingleplayerGameOption.setColor(Color.GREEN);
		startSingleplayerGameOption.setText("Play Local");
		addObject(startSingleplayerGameOption);
		
		startMultiplayerGameOption.setColor(Color.GREEN);
		startMultiplayerGameOption.setText("Play Online");
		addObject(startMultiplayerGameOption);
		
		exitOption.setColor(Color.GREEN);
		exitOption.setText("Exit");
		addObject(exitOption);
		
		bigText.setText("Pong");
		bigText.setColor(Color.GREEN);
		
		setBackgroundColor(Color.BLACK);
		addObject(bigText);
	}
	
	@Override
	public void keyPressed(KeyEvent event) 
	{
		super.keyPressed(event);

		if ( event.getKeyCode() == 10 ) // Enter Key
		{
			switch ( selected )
			{
				case 0:
					Renderer.getInstance().setScene(new LocalPlay());
					break;
				case 1:
					Renderer.getInstance().setScene(new NetworkPlay());
					break;
				case 2:
					System.exit(0);
					break;
			}
		}
		
		if ( event.getKeyCode() == KeyEvent.VK_UP )
		{
			selected--;
			if ( selected < 0 )
				selected = 2;
		}
		
		if ( event.getKeyCode() == KeyEvent.VK_DOWN )
		{
			selected++;
			if ( selected > 2 )
				selected = 0;
		}
		
		switch ( selected )
		{
		case 0:
			startSingleplayerGameOption.setFont(new Font("SansSerif", Font.BOLD, 12));
			startMultiplayerGameOption.setFont(new Font("SansSerif", Font.PLAIN, 12));
			exitOption.setFont(new Font("SansSerif", Font.PLAIN, 12));	
			break;
		case 1:
			startSingleplayerGameOption.setFont(new Font("SansSerif", Font.PLAIN, 12));
			startMultiplayerGameOption.setFont(new Font("SansSerif", Font.BOLD, 12));
			exitOption.setFont(new Font("SansSerif", Font.PLAIN, 12));	
			break;
		case 2:
			startSingleplayerGameOption.setFont(new Font("SansSerif", Font.PLAIN, 12));
			startMultiplayerGameOption.setFont(new Font("SansSerif", Font.PLAIN, 12));
			exitOption.setFont(new Font("SansSerif", Font.BOLD, 12));	
			break;
		}
	}
	
	@Override
	public void sceneLogic() {
		
		
	}
	
}
