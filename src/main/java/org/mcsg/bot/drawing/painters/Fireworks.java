package org.mcsg.bot.drawing.painters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.drawing.BlendComposite;
import org.mcsg.bot.drawing.BlendComposite.BlendingMode;
import org.mcsg.bot.util.MapWrapper;

public class Fireworks extends AbstractPainter{

	public Fireworks(BufferedImage img) {
		super(img);
	}

	private double grav = -9.8;

	@Override
	public void paint(MapWrapper args) {
		for(int a = 0; a < rand.nextInt(3) + 3; a++) {
			drawFirework(args);
		}
	}
	
	public void drawFirework(MapWrapper args) {
		int x = rand.nextInt(width);
		int y = rand.nextInt(height - height / 3);

		drawPath(x, y);
		drawExplosion(x, y);
	}


	public void drawPath(int x, int y) {
		for(int a = height; a >  y; a --) {
			int c = rand.nextInt(40) + 215;
			g.setColor(new Color(c,c,c,30));

			int offset = rand.nextInt((a- y) / 15 + 1);

			g.fillOval(x - offset - 10,  a - 10, 20, 20);
		}
	}


	private void drawExplosion(int x, int y) {
		int count = rand.nextInt(30) + 10;
		List<Trail> trails = new ArrayList<>();

		int basettl = rand.nextInt(40) + 80;
		for(int a = 0; a < count;a ++) {
			trails.add(new Trail(x, y, rand.nextDouble() * 5 - 3, rand.nextDouble() * 5 - 3, basettl + rand.nextInt(20)));
		}


		int c;
		do{
			c=0;
			for(Trail trail : trails) {
				if(trail.alive()) {
					c++;
					trail.tick();

					int col = rand.nextInt(40) + 215;
					g.setColor(new Color(col,col,col,10));

					g.fillOval((int)trail.getX() - 6,(int) trail.getY() - 6, 12, 12);
				}
			}
		}while (c > 0);

		drawSpark(trails);
	}

	private void drawSpark(List<Trail> trails) {
		g.setColor(createColor());
//		g.setComposite(BlendComposite.getInstance(BlendingMode.ADD));
		for(Trail trail : trails) {
			double diff = 4;
			for(int a = 30; a > 10; a-=diff) {
				diff *= .5;
				g.fillOval((int)trail.getX() - a / 2, (int)trail.getY() - a / 2, a,a);
			}
		}
	}
	
	private Color createColor() {
		float h = rand.nextFloat();
		float s = rand.nextFloat();
		float b = 70 + ((1f - 70) * rand.nextFloat());
		Color c = Color.getHSBColor(h, s, b);
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), 10);
	}

	private class Trail {
		private double x, y, velx, vely;
		int ttl;

		public Trail(double x, double y, double velx, double vely, int ttl) {
			super();
			this.x = x;
			this.y = y;
			this.velx = velx;
			this.vely = vely;
			this.ttl = ttl;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public double getVelx() {
			return velx;
		}

		public double getVely() {
			return vely;
		}

		public boolean alive() {
			return ttl > 0;
		}

		public void tick() {
			vely += 0.01;
			//velx += velx > 0 ? -0.01 : 0.01;

			x += velx;
			y += vely;

			ttl--;
		}
	}

}
