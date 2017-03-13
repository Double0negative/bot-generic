package org.mcsg.bot.drawing.painters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.util.MapWrapper;

public class SprayPaint extends AbstractPainter{

	public SprayPaint(BufferedImage img) {
		super(img);
	}

	@Override
	public void paint(MapWrapper args) {
		paintStreak();
		
		
	}
	
	private void paintStreak() {
		Color color = getRandomColor(false);
		g.setColor(color);
		int x = rand.nextInt(width);
		int y = rand.nextInt(height);
		
		for(int i = 0; i < rand.nextInt(1000); i++) {
			for(int a = 0; a < rand.nextInt(5000); a++) {
				double angle = rand.nextDouble() * (2 * Math.PI);
				double distance = rand.nextDouble() * 200 - 100;
				double xoff = Math.sin(angle) * distance;
				double yoff = Math.cos(distance) * distance;
				
				g.fillOval((int)(x + xoff),(int) (y + yoff), 2, 2);
				
			}
			x += rand.nextInt(5);
			y += rand.nextInt(5);

		}
		
	}

}
