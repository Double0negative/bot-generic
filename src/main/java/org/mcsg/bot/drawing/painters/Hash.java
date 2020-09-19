package org.mcsg.bot.drawing.painters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.util.MapWrapper;

public class Hash extends AbstractPainter{

	public Hash(BufferedImage img) {
		super(img);
	}

	@Override
	public void paint(MapWrapper args) {
		int x = rand.nextInt(width);
		int y = rand.nextInt(height);

		g.setColor(getRandomColor(true));
		for(int i = 0; i < 20000; i++) {
			int dirx = rand.nextBoolean() ? 1 : -1;
			int diry = rand.nextBoolean() ? 1 : -1;

			int offset = rand.nextInt(200);
			
			x = randInc(x, 9, width);
			y = randInc(y, 9, height);

			g.setColor(randIncColor(g.getColor(), 7));
			g.drawLine(x, y, x + (dirx * offset), y + (diry * offset));
		};
	}

}