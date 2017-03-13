package org.mcsg.bot.drawing.painters.shapes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Circle implements Shape {

	@Override
	public void draw(Graphics2D g, int x, int y, int width, int height) {
		
		g.fillOval(x, y, width, height);

	}


}
