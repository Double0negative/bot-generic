package org.mcsg.bot.drawing.painters.shapes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public interface Shape {

	public void draw(Graphics2D g, int x, int y, int width, int height);
	
}
