package org.mcsg.bot.drawing.painters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.util.MapWrapper;
import org.mcsg.bot.util.Point;

public class Clusters extends AbstractPainter {

	public Clusters(BufferedImage img) {
		super(img);

		for(int a = 0; a < rand.nextInt(5)  + 1; a++){
			points.add(new Point(rand.nextInt(width- 500 ) + 250, rand.nextInt(height - 500) + 250));
		}

		baseColor = getRandomColor(true);
	}


	private List<Point> points = new ArrayList<>();
	private Color baseColor;


	public void paint(MapWrapper args) {

		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		int stop = rand.nextInt(10000);
		for(int a = 0; a < stop; a++){
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);

			int dis = Integer.MAX_VALUE;
			for(Point point : points){
				dis = Math.min(dis, Math.abs(point.distance(x, y)));
			}

			int size = Math.max(0, 200 - dis) / 4;
			size += rand.nextInt(25);


			/*g.setColor(new Color(baseColor.getRed(), baseColor.getBlue(), baseColor.getGreen(), rand.nextInt(50)));
			g.fillOval(x - size /2 , y - size /2, size, size);*/

			g.setColor(new Color(baseColor.getRed(), baseColor.getBlue(), baseColor.getGreen(), size));
			size = Math.max(1, 200 - dis) / 3;

			drawCircle(x, y, size);

		}
	}

	public void drawCircle(int x, int y, int size){

		if(size == 1)
			while(rand.nextBoolean())
				size=size * 3;
		size = rand.nextInt(size + 10);
		g.fillOval(x - size /2 , y - size /2, size, size);
	}

}