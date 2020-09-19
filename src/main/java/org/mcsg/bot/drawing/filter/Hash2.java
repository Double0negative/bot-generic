package org.mcsg.bot.drawing.filter;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.stream.IntStream;

import org.mcsg.bot.drawing.Filter;
import org.mcsg.bot.drawing.ImageTools;
import org.mcsg.bot.drawing.PixelData;
import org.mcsg.bot.util.MapWrapper;

public class Hash2 implements Filter{

	private Random rand = new Random();
	
	@Override
	public BufferedImage filter(BufferedImage input, Graphics2D g, MapWrapper map) {
		map.put("palette", 3);
		BufferedImage smooshed = new Smoosh().filter(scale(input, input.getWidth(), input.getHeight()), g, map);
		BufferedImage img = blank(input);
		final Graphics2D g1 = (Graphics2D)img.getGraphics();
		g1.setStroke(new BasicStroke(1));
		IntStream.range(0, 1000).forEach(e -> {
			int x = rand.nextInt(input.getWidth());
			int y = rand.nextInt(input.getHeight());

			g1.setColor(new Color(input.getRGB(x, y)));
			
			int dirx = rand.nextBoolean() ? 1 : -1;
			int diry = rand.nextBoolean() ? 1 : -1;

			IntStream.range(0, 20).forEach(n -> {				
				int posx =  x + n - 10;
				int posy =  y + n - 10;
				
				PixelData point = getFurthestPoint(smooshed, smooshed.getRGB(x, y),posx, posy, dirx, diry);
				g1.drawLine(posx, posy, point.getX(), point.getY());
			});
			
		});
		return img;
	}

	
	private PixelData getFurthestPoint(BufferedImage smooshed, int color, int x, int y, int dirx, int diry) {
		x = ImageTools.limit(x, smooshed.getWidth(), 0);
		y = ImageTools.limit(y, smooshed.getHeight(), 0);

		while(x > 0 && x < smooshed.getWidth() - 1 && y > 0 && y < smooshed.getHeight() - 1 && smooshed.getRGB(x,  y) == color) {
			x += dirx;
			y += diry;
		}
		
		int offset = rand.nextInt(30);
		x += offset * dirx;
		y += offset * diry;

		return new PixelData(ImageTools.limit(x, smooshed.getWidth(), 0), ImageTools.limit(y, smooshed.getHeight(), 0));
	}
}
