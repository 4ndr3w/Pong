package lobos.andrew.game.pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import lobos.andrew.game.baseObjects.Text;
import lobos.andrew.game.core.Renderer;
import lobos.andrew.game.scene.Scene;

public class MultiplayerMenu extends Scene {

	Text bigText = new Text(new Font("SansSerif", Font.BOLD, 48), -0.4f, 0.19f);
	
	Text runAsClientOption = new Text(new Font("SansSerif", Font.BOLD, 12), -0.399f, 0.15f);
	Text runAsServerOption = new Text(new Font("SansSerif", Font.PLAIN, 12), -0.399f, 0.125f);
	Text exitOption = new Text(new Font("SansSerif", Font.PLAIN, 12), -0.399f, 0.1f);
	
	int selected = 0;
	
	public MultiplayerMenu()
	{
		runAsClientOption.setColor(Color.GREEN);
		runAsClientOption.setText("Connect to server");
		addObject(runAsClientOption);
		
		runAsServerOption.setColor(Color.GREEN);
		runAsServerOption.setText("Start server");
		addObject(runAsServerOption);
		
		exitOption.setColor(Color.GREEN);
		exitOption.setText("Back");
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
					Renderer.getInstance().setScene(new NetworkPlay(false));
					break;
				case 1:
					Renderer.getInstance().setScene(new NetworkPlay(true));
					break;
				case 2:
					Renderer.getInstance().setScene(new Menu());
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
			runAsClientOption.setFont(new Font("SansSerif", Font.BOLD, 12));
			runAsServerOption.setFont(new Font("SansSerif", Font.PLAIN, 12));
			exitOption.setFont(new Font("SansSerif", Font.PLAIN, 12));	
			break;
		case 1:
			runAsClientOption.setFont(new Font("SansSerif", Font.PLAIN, 12));
			runAsServerOption.setFont(new Font("SansSerif", Font.BOLD, 12));
			exitOption.setFont(new Font("SansSerif", Font.PLAIN, 12));	
			break;
		case 2:
			runAsClientOption.setFont(new Font("SansSerif", Font.PLAIN, 12));
			runAsServerOption.setFont(new Font("SansSerif", Font.PLAIN, 12));
			exitOption.setFont(new Font("SansSerif", Font.BOLD, 12));	
			break;
		}
	}
	
	@Override
	public void sceneLogic() {
		
		
	}

}
