package org.mcsg.bot.drawing.painters.shapes;

import java.awt.Graphics2D;

public class Heart implements Shape{

	@Override
	public void draw(Graphics2D g, int x, int y, int width, int height) {
		 int[] triangleX = {
		            x - 2*width/18,
		            x + width + 2*width/18,
		            (x - 2*width/18 + x + width + 2*width/18)/2};
		    int[] triangleY = { 
		            y + height - 2*height/3, 
		            y + height - 2*height/3, 
		            y + height };
		    g.fillOval(
		            x - width/12,
		            y, 
		            width/2 + width/6, 
		            height/2); 
		    g.fillOval(
		            x + width/2 - width/12,
		            y,
		            width/2 + width/6,
		            height/2);
		    g.fillPolygon(triangleX, triangleY, triangleX.length);
	}

}
