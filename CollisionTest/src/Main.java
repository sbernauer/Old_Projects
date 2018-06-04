import java.awt.Toolkit;

public class Main {
	public static void main(String[] args) {
		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

		GUI gui = new GUI(width, height);
		gui.display();
	}
}
