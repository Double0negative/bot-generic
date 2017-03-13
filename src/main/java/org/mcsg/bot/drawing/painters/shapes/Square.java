package org.mcsg.bot.drawing.painters.shapes;

import java.awt.Graphics2D;

public class Square implements Shape{

	@Override
	public void draw(Graphics2D g, int x, int y, int width, int height) {
		g.fillRect(x, y, width, height);
	}

}
