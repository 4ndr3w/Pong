package lobos.andrew.game.pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import lobos.andrew.game.baseObjects.Text;
import lobos.andrew.game.core.Renderer;
import lobos.andrew.game.scene.Scene;

public class ChooseServer extends Scene
{
	Text header = new Text(new Font("SansSerif", Font.BOLD, 24), -0.1f,0.03f);
	Text serverAddr = new Text(new Font("SansSerif", Font.BOLD, 12), 0,0);
	public ChooseServer()
	{
		setBackgroundColor(Color.BLACK);
		serverAddr.setColor(Color.GREEN);
		header.setColor(Color.GREEN);
		
		addObject(serverAddr);
		addObject(header);
		
		header.setText("Server Address:");
	}
	
	@Override
	public void keyPressed(KeyEvent event) 
	{
		super.keyPressed(event);

		if ( event.getKeyCode() == 10 ) // Enter
		{
			try {
				InetAddress address = InetAddress.getByName(serverAddr.getText());
				Renderer.getInstance().setScene(new NetworkPlay(address));
			} catch (UnknownHostException e) {
				serverAddr.setText("");
			}
		}
		else if ( event.getKeyCode() == 8 ) // Backspace
		{
			String str = serverAddr.getText();
			if ( str.length() > 0 )
				serverAddr.setText( str.substring(0, str.length()-1) );
		}
		else if ( event.getKeyCode() > 33 && event.getKeyCode() <= 90 )
			serverAddr.setText(serverAddr.getText()+event.getKeyChar());
	}
	
	@Override
	public void sceneLogic() {
		
	}

}
