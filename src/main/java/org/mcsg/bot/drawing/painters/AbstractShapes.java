package org.mcsg.bot.drawing.painters;
import java.awt.Color;
import java.awt.image.BufferedImage;

import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.drawing.ImageTools;
import org.mcsg.bot.drawing.Vec2;
import org.mcsg.bot.util.MapWrapper;

public class AbstractShapes extends AbstractPainter{

	public AbstractShapes(BufferedImage img) {
		super(img);
	}

	@Override
	public void paint(MapWrapper args) {
		int stop = args.getInt("amount", rand.nextInt(5000));

		Vec2 point = new Vec2(rand.nextInt(width), rand.nextInt(height));
		setRandomColor(true);
		g.setColor(new Color(255,170,0,50));
		for(int a = 0; a < stop; a++){
			int r = rand.nextInt(500);
			if( r > 450){
				if(rand.nextBoolean())
					g.setColor(brigher(g.getColor(), 3));
				else 
					g.setColor(darker(g.getColor(), 3));
			} else if(r < 100){
				g.setColor(randIncColor(g.getColor(), 10));
			}
			point = drawRandomShape(point);
		}
	}



	private Vec2 drawRandomShape(Vec2 point){
		int size = 3;
//		while(rand.nextInt(10) > 2)
//			size++;
		int xcor [] = new int[size];
		int ycor [] = new int[size];

		int lx = point.getX();
		int ly = point.getY();
		for(int a = 0; a < size; a++){
			lx = lx + (rand.nextInt(300) - 150);
			xcor[a] = lx = ImageTools.limit(lx, width, 0);
			ly = ly + (rand.nextInt(300) - 150);
			ycor[a] = ly = ImageTools.limit(ly, height, 0);
		}

		g.fillPolygon(xcor, ycor, size);

		return new Vec2(lx, ly);

	}

}