package org.mcsg.bot.drawing.painters;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;

import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.util.MapWrapper;

public class Sunset extends AbstractPainter{

	public Sunset(BufferedImage img) {
		super( img);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paint(MapWrapper args) {
		drawBackground();
		drawLand();

	}



	private void drawBackground() {
		Color color = new Color (rand.nextInt(255), rand.nextInt(255), 0);
		g.setStroke(new BasicStroke(2));

		for(int y = 0; y < height; y++) {
			g.setColor(color);
			
			g.drawLine(0, y, width, y);

			color = this.incColor(color, rand.nextInt(3) - 1, rand.nextInt(3) - 1, 0, 0);
		}
	}

	
	private void drawLand() {
		Color color = new Color(0, 0, rand.nextInt(255));
		int y = rand.nextInt(height);
		
		for(int x = 0; x < width; x++) {
			g.setColor(color);
			
			g.drawLine(x, y, x, height);

			color = this.incColor(color, 0, 0,rand.nextInt(3) - 1,  0);
			
			y += rand.nextInt(3) - 1;
		}
	}



}