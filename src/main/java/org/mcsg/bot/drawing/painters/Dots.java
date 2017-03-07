package org.mcsg.bot.drawing.painters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.drawing.ImageTools;
import org.mcsg.bot.util.MapWrapper;
import org.mcsg.bot.util.Point;

public class Dots extends AbstractPainter{

	public Dots(BufferedImage img) {
		super(img);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paint(MapWrapper args) {
		int x = rand.nextInt(width);
		int y = rand.nextInt(height);
		int amt = rand.nextInt(50000);
		int size = 12;
		Point lpoint = new Point(x, y);

		Color color = getRandomColor(false);


		for(int d = 0; d < amt; d++){

			Point p = new Point(lpoint);
			while(p.distance(lpoint) < size + 1){
				p.incX(rand.nextInt(3) - 1);
				p.incY(rand.nextInt(3) - 1);
			}

			/*p.incX(rand.nextInt(11) - 6);
			p.incY(rand.nextInt(11) - 6);*/

			color  = randIncColor(color, 7, false);
			g.setColor(color);

			p.setX(p.getX() > width ? width : p.getX()< 0 ? 0 : p.getX());
			p.setY(p.getY() > height ? height : p.getY() < 0 ? 0 : p.getY());

			drawCircle(p.getX(), p.getY(), size);
			g.setColor(g.getColor().brighter());
			drawCircle(p.getX(), p.getY(), size - 2);

			lpoint = p;

		}



	}








}