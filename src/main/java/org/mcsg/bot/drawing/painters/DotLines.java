package org.mcsg.bot.drawing.painters;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;

import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.drawing.ImageTools;
import org.mcsg.bot.util.MapWrapper;

public class DotLines extends AbstractPainter {

	int r=rand.nextInt(255);
	int gc=rand.nextInt(255);
	int b=rand.nextInt(255);
	int a=rand.nextInt(255);

	int x = rand.nextInt(width);
	int y = rand.nextInt(height);

	int size = rand.nextInt(45);


	public DotLines(BufferedImage img) {
		super(img);
	}


	@Override
	public void paint(MapWrapper args) {
		Color color = getRandomColor(false);

		g.setStroke(new BasicStroke(rand.nextInt(7)));
		
		int stop = rand.nextInt(10000);
		
		for(int aa = 0; aa < stop; aa++){
			color  = randIncColor(color, 7, false);
			g.setColor(color);

			x += (rand.nextDouble() * size) - (size / 2);
			y += (rand.nextDouble() * size) - (size / 2);
			size += (rand.nextDouble() * 3)  - 1;

			size = ImageTools.limit(size, 10, 50);
			x = ImageTools.limit(x, width, 0);
			y = ImageTools.limit(y, height, 0);
			

			g.fillOval(x, y, size, size);
			g.setColor(g.getColor().brighter());
			g.drawOval(x, y, size, size);
		}
	}

}