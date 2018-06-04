import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class ShapeCreator {

	public static Area createAreaFromImage(String pathToImage) throws IOException {
		BufferedImage image = ImageIO.read(new File(pathToImage));
		Area area = new Area();
		Rectangle r;
		int y1, y2;

		for (int x = 0; x < image.getWidth(); x++) {
			y1 = 99;
			y2 = -1;
			for (int y = 0; y < image.getHeight(); y++) {
				Color pixel = new Color(image.getRGB(x, y));
				if (pixel.getRGB() == Color.black.getRGB()) {
					if (y1 == 99) {
						y1 = y;
						y2 = y;
					}
					if (y > (y2 + 1)) {
						r = new Rectangle(x, y1, 1, y2 - y1);
						area.add(new Area(r));
						y1 = y;
						y2 = y;
					}
					y2 = y;
				}
			}
			if ((y2 - y1) >= 0) {
				r = new Rectangle(x, y1, 1, y2 - y1);
				area.add(new Area(r));
			}
		}
		return area;
	}
}