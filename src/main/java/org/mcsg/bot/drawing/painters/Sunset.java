package org.mcsg.bot.drawing.painters;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;

import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.drawing.ImageTools;
import org.mcsg.bot.util.MapWrapper;

public class Sunset extends AbstractPainter{

	public Sunset(BufferedImage img) {
		super( img);
		// TODO Auto-generated constructor stub
	}


	@Override
	public void paint(MapWrapper args) {
		boolean storm = false; //rand.nextBoolean();
		drawBackground();
		drawSun();
		
		if(storm)
			drawStorm();
		else 
			drawClouds();
		drawOcean2(storm);
		drawReflection();
	}



	private void drawBackground() {
		for(int y = 0; y < 300; y++) {
			int col = rand.nextInt(100) + 150;
			Color color = new Color (col, col, 255, 10);
			g.setColor(color);

			int size = rand.nextInt(Math.max(width,  height));

			g.fillOval(rand.nextInt(width) - size / 2, rand.nextInt(height) - size / 2, size, size);
		}
	}

	int sunsize = rand.nextInt(100) + 100;

	int sunx = rand.nextInt(width - sunsize *4) + sunsize* 2;
	int suny = (rand.nextInt(200) + 150 + height / 2);
	Color suncolor = new Color(rand.nextInt(100) + 150, rand.nextInt(50), rand.nextInt(25), 10);

	private void drawSun() {
		int max = Math.max(width,  height) * 2;


		g.setColor(suncolor);
		for(int a = 0; a < max;) {
			if(a > sunsize) {
				a *= 1.3;
			} else {
				a++;
			}

			g.fillOval(sunx - a / 2, suny - a / 2, a, a);
		}
	}

	public void drawStorm() {
		int size = 200;
		for(int y = suny - size - 200; y > 0; y -= size / 3.2) {
			for(int x =0; x < width; x+=size /2) {
				drawCloud(x, y, size, 4, suncolor, new Color(25, 25, 25));
			}
		}
	}

	public void drawClouds() {
		int count = rand.nextInt(12);

		int size = 100;

		for(int a = 0; a < count; a ++) {
			int cx = rand.nextInt(width);
			int cy = rand.nextInt((int) (suny - size * 1.5));


			drawCloud(cx, cy, size, 8, suncolor, new Color(255,255, 255));
		}
	}

	private void drawCloud(int cx, int cy, int size, int fluff, Color bottom, Color top) {
		for(int c = 0;c < 8; c++) {
			int offx = rand.nextInt(size * 2) - size;
			int offy = (int) (rand.nextInt(size) - size/ 2);
			int dx = rand.nextInt(size / 2) + 20 ;

			for(int b = 0; b < dx; b+= 10) {
				for(double ang = 0; ang < Math.PI * 2; ang += Math.PI / (b + 1)) {


					int posx = (int) (b * Math.sin(ang));
					int posy = (int) (b * Math.cos(ang));

					int s = rand.nextInt(50);
					double w = .3;


					double perc = 1 - (offy  + posy + dx+ .01 ) /((dx * 2) + (size * 2));
					g.setColor(new Color(fade(bottom.getRed(), top.getRed(), perc), fade(bottom.getGreen(), top.getGreen(), perc), fade(bottom.getBlue(), top.getBlue(), perc), 20));


					g.fillOval(cx + posx  + offx - (s /2), cy + posy + offy - (s /2 ),s, s );
				}
			}
		}
	}

	private int fade(int start, int end, double perc) {
		int diff = end - start;
		double offset = diff * perc;
		return (int) ImageTools.limit((start + offset), 255, 0);
	}


	private void drawOcean(boolean storm) {
		int offsetMax = storm ? 75 : 12;
		int size = 20;
		int count = 25;
		
		int col = rand.nextInt(20);
		
		g.setStroke(new BasicStroke(1));
		int lastOffset1 = 0;
		int lastOffset2 = 0;

		for(int a = suny; a < height; a+= 1) {
			Color color  = new Color(col, col, rand.nextInt(150) + 75, 30);
			if(rand.nextInt(50) == 0)
				color = new Color(255, 255, 255, 30);
			
			g.setColor(color);
			int inv = (width / count);
			for(int b = 0; b < width; b += inv ) {

				int offset1 = rand.nextInt(offsetMax) - offsetMax /2 ;
				int offset2 = rand.nextInt(offsetMax) - offsetMax /2 ;

				g.fillPolygon(new int [] {b,  b + inv,  b + inv, b}, new int[] {a + lastOffset1,a + offset1, a + offset2 + size, a + lastOffset2 + size}, 4);
				lastOffset1 = offset1;				
				lastOffset2 = offset2;
				
			
			}
			
		}
	}

	private void drawOcean2(boolean storm) {
		int offsetMax = storm ? 70 : 14;
		int size = 20;
		int count = 25;
		
		int col = rand.nextInt(20);
		
		g.setStroke(new BasicStroke(1));
		
		for(int a = suny; a < height; a+= 1) {
			int[] x = new int[count * 2 + 4];
			int[] y = new int[count * 2 + 4];
			
			x[0] = 0;
			y[0] = a;
			
			x[count + 1] = width;
			y[count + 1] = a;
			
			x[count + 2] = width;
			y[count + 2] = a;
			
			x[x.length - 1] = 0;
			y[y.length - 1] = a + size;
			
			Color color = new Color(col, col, rand.nextInt(150) + 75, 30);
			if(rand.nextInt(50) == 0)
				g.setColor(new Color(255, 255, 255, 30));
			else 
				g.setColor(color);

			
			for(int b = 0; b < count; b ++) {
				x[b + 1] = b * (width / count);
				y[b + 1] = a + rand.nextInt(offsetMax) - offsetMax / 2;
 			}
			
			for(int b = 0; b < count; b ++) {
				x[b + count + 3] = width - (b * (width / count));
				y[b + count + 3] = size + a + rand.nextInt(offsetMax) - offsetMax / 2;
 			}
			
			g.fillPolygon(x, y, x.length);
		}
	}

	private void drawReflection() {
		int max = 200;
		g.setStroke(new BasicStroke(2));
		g.setColor(new Color(suncolor.getRed(),suncolor.getGreen(), suncolor.getBlue(), 50));
		
		for(int a = suny; a < suny + max; a++) {
			int len = rand.nextInt(max + 50 - (a - suny));
			g.drawLine(sunx - len, a + rand.nextInt(10) - 5, sunx + len,  a + rand.nextInt(10) - 5);
		}
	}


}