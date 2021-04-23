package org.mcsg.bot.drawing.painters;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.drawing.ImageTools;
import org.mcsg.bot.drawing.Point;
import org.mcsg.bot.util.MapWrapper;

public class Spooky extends AbstractPainter {

	public Spooky(BufferedImage img) {
		super(img);
	}

	@Override
	public void paint(MapWrapper args) {
		drawBackground();
		drawMoon();
		drawClouds();
		drawBats();
		drawLand();
		drawFog(0);
		drawTrees();
		drawFog(-200);
		drawGravyard();
	}

	private void drawBackground() {
		for (int y = 0; y < 300; y++) {
			int col = rand.nextInt(60);
			Color color = new Color(col, col, col, 10);
			g.setColor(color);

			int size = rand.nextInt(Math.max(width, height));

			g.fillOval(rand.nextInt(width) - size / 2, rand.nextInt(height) - size / 2, size, size);
		}
	}

	int moonSize = rand.nextInt(300) + 100;
	int moonx = rand.nextInt(width - moonSize * 4) + moonSize * 2;
	int moony = (rand.nextInt(300) + 150);
	int moonBrightness = rand.nextInt(50) + 200;
	Color moonColor = new Color(moonBrightness, moonBrightness, moonBrightness, 5);

	private void drawMoon() {
		int max = Math.max(width, height) * 2;

		g.setColor(moonColor);
		for (int a = 0; a < max;) {
			if (a > moonSize) {
				a *= 1.3;
			} else {
				a++;
			}

			g.fillOval(moonx - a / 2, moony - a / 2, a, a);
		}
	}

	public void drawClouds() {
		int count = rand.nextInt(15);

		int size = 200;
		int fluffSize = 100;

		for (int a = 0; a < count;) {
			int cx = rand.nextInt(width);
			int cy = rand.nextInt(height);

			if (new Point(cx, cy).distance(new Point(moonx, moony)) > size + fluffSize + moonSize) {
				drawCloud(cx, cy, size, 30, fluffSize, darker(moonColor, 100), new Color(40, 40, 40));
				a++;
			}
		}
	}

	private void drawCloud(int cx, int cy, int size, int fluff, int fluffMaxSize, Color top, Color bottom) {
		for (int cloudInterations = 0; cloudInterations < fluff; cloudInterations++) {
			int offx = rand.nextInt(size * 2) - size;
			int offy = (int) (rand.nextInt(size) - size / 2);
			Point center = new Point(cx + offx, cy + offy);
			int fluffSize = rand.nextInt(fluffMaxSize) + 20;

			double angleFromMoon = getAngleFromPoint(new Point(moonx, moony), center);

			int edgex = (int) (center.getX() + (fluffSize * Math.cos(angleFromMoon)));
			int edgey = (int) (center.getY() + (fluffSize * Math.sin(angleFromMoon)));
			Point edgePoint = new Point(edgex, edgey);

			for (int distance = 0; distance < fluffSize; distance += 5) {
				for (double ang = 0; ang < Math.PI * 2; ang += .2) {
					int posx = (int) (distance * Math.sin(ang));
					int posy = (int) (distance * Math.cos(ang));
					Point point = new Point(center.getX() + posx, center.getY() + posy);

					int s = rand.nextInt(50);

					double perc = 1 - (point.distance(edgePoint) + .0) / (2.0 * fluffSize);
					g.setColor(new Color(fade(bottom.getRed(), top.getRed(), perc),
							fade(bottom.getGreen(), top.getGreen(), perc), fade(bottom.getBlue(), top.getBlue(), perc),
							12));

					g.fillOval(cx + posx + offx - (s / 2), cy + posy + offy - (s / 2), s, s);
				}
			}
		}
	}

	private void drawBats() {
		int clusters = rand.nextInt(6) + 2;
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(4));
		for(int a = 0; a < clusters ; a++) {
			int bats = rand.nextInt(8);
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);
			for(int b = 0; b < bats ; b++) {
				double angle =  (Math.random() * (Math.PI / 4)) - Math.PI / 2;
				int offsetx = x + rand.nextInt(300) - 150;
				int offsety = y + rand.nextInt(200) - 100;

				double flapAngle =  .3 + (Math.random() * (Math.PI / 8));
				
				
				int x2 = (int) (offsetx + (20 * Math.cos(angle + flapAngle)));
				int y2 = (int) (offsety + (20 * Math.sin(angle + flapAngle)));
				g.drawLine(offsetx, offsety, x2, y2);
				
				int x2_2 = (int) (offsetx + (20 * Math.cos(angle - flapAngle)));
				int y2_2 = (int) (offsety + (20 * Math.sin(angle - flapAngle)));
				g.drawLine(offsetx, offsety, x2_2, y2_2);
			}
		}
	}
	
	int halfHeight = height / 2;
	double landMap[][] = ImageTools.createNoise(halfHeight, width, 1, width, halfHeight, rand.nextDouble() * .3 + .1);
	int lowestLand = Integer.MAX_VALUE;
	private void drawLand() {

		g.setStroke(new BasicStroke(2));
		g.setColor(Color.black);
		for (int x = 0; x < width; x++) {
			int value = (int) (halfHeight * (landMap[x][0] + .4)) + 400;
			lowestLand = Math.min(lowestLand, value);
			g.drawLine(x, height, x, height - value);
		}
	}

	private void drawFog(int offset) {
		int maxLevel = rand.nextInt(lowestLand + offset);
		int taper = maxLevel - 200;
		
		double noise[][] = ImageTools.createNoise(200, width, maxLevel, 400, 400, .3);

		
		for (int x = 0; x < width; x += 1) {
			for (int y = 0; y < maxLevel; y += 1) {

				int color = (int) (60 * (noise[x][y] + .6));
				if(y >  taper) {
					color *=  (1 - (((y -  taper) + .0) / (maxLevel - taper + .0)));
				}
				color = ImageTools.limit(color, 255, 0);

				g.setColor(new Color(255, 255, 255, color));

				this.drawCircle(x, height - y, 1);

			}
		}
	}

	private void drawTrees() {
		for (int count = rand.nextInt(3) + 1; count-- > 0;) {
			int y = height - rand.nextInt(lowestLand);
			drawBranch(rand.nextInt(width),y, Math.max(80, y - halfHeight), (int) Math.max(30, ((y - halfHeight) * .4)),
					-Math.PI / 2, 8, true);
		}
	}

	private void drawBranch(int rootX, int rootY, int length, int width, double angle, int count, boolean first) {
		if (count < 0)
			return;
		else
			count--;

		int endX = (int) (rootX + (length * Math.cos(angle)));
		int endY = (int) (rootY + (length * Math.sin(angle)));

		g.setStroke(new BasicStroke(width));
		g.setColor(Color.black);
		g.drawLine(rootX, rootY, endX, endY);

		drawBranch(endX, endY, (int) (length * (Math.random() * .2 + .6)), width / 2,
				angle + .5 - (Math.random() * .25), count, false);
		drawBranch(endX, endY, (int) (length * (Math.random() * .2 + .6)), width / 2,
				angle - .5 + (Math.random() * .25), count, false);

		int extra = rand.nextInt(2);
		for (int a = 0; extra > a && !first; a++) {
			int newLen = rand.nextInt(length);
			int newX = (int) (rootX + (newLen * Math.cos(angle)));
			int newY = (int) (rootY + (newLen * Math.sin(angle)));

			double offset = .6 + (Math.random() * .3);
			offset *= rand.nextBoolean() ? 1 : -1;

			drawBranch(newX, newY, (int) (length * (Math.random() * .2 + .6)), width / 2, offset, count, false);
		}

	}
	
	private void drawGravyard() {
		int clusters = rand.nextInt(6) + 2;
		for(int a = 0; a < clusters ; a++) {
			int headstones = rand.nextInt(4);
			int rootX = rand.nextInt(width);
			int rootY = height - rand.nextInt(this.lowestLand);
			for(int b = 0; b < headstones ; b++) {
				int offsetX = rand.nextInt(400) - 200;
				int offsetY = rand.nextInt(150) - 75;

				g.setStroke(new BasicStroke(50));
				g.setColor(Color.black);
				g.drawLine(rootX + offsetX, rootY + offsetY, rootX + offsetX, rootY - 100 + offsetY);
				this.drawCircle(rootX + offsetX, rootY - 125 + offsetY, 50);
			}
		}
	}

	private int fade(int start, int end, double perc) {
		int diff = end - start;
		double offset = diff * perc;
		return (int) ImageTools.limit((start + offset), 255, 0);
	}

	public double getAngleFromPoint(Point firstPoint, Point secondPoint) {
		return Math.atan2((secondPoint.getX() - firstPoint.getX()), (firstPoint.getY() - secondPoint.getY()))
				+ 2.5 * Math.PI;
	}


}