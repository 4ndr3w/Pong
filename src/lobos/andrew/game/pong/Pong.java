package lobos.andrew.game.pong;
import lobos.andrew.game.core.Renderer;


public class Pong {
	public static void main(String[] args) {
		Renderer.getInstance().setScene(new Menu());
	}
}
