package org.mcsg.bot.drawing.painters;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;

import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.drawing.ImageTools;
import org.mcsg.bot.util.MapWrapper;

public class Linked extends AbstractPainter{

	int x1 = rand.nextInt(width);
	int x2 = rand.nextInt(width);

	int y1 = rand.nextInt(height);
	int y2 = rand.nextInt(height);


	public Linked(BufferedImage img) {
		super(img);
	}


	public void paint(MapWrapper args) {	
		this.setRandomColor(true);
		g.setStroke(new BasicStroke(args.getInt("stroke-size", 2)));

		int stop = args.getInt("amount", rand.nextInt(10000) + 5000);
		int inc = args.getInt("inc", rand.nextInt(100));
		for(int aa = 0; aa < stop; aa++){
			g.setColor(this.randIncColor(g.getColor(), 7, true));

			x2 = x1;
			y2 = y1;
			
			x1 = this.randInc(x1, inc);
			y1 = this.randInc(y1, inc);

			x1 = ImageTools.limit(x1, width, 0);
			x2 = ImageTools.limit(x2, width, 0);
			y1 = ImageTools.limit(y1, height, 0);
			y2 = ImageTools.limit(y2, height, 0);
			
			g.drawLine(x1, y1, x2, y2);
		}

	}
}