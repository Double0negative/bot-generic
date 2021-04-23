package org.mcsg.bot.drawing.painters;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.drawing.ImageTools;
import org.mcsg.bot.util.MapWrapper;

public class Landscape extends AbstractPainter {

	public Landscape(BufferedImage img) {
		super(img);
		// TODO Auto-generated constructor stub
	}

	//	public static void main(String argsp[]) {
	//		new Landscape(new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR)).paint(null);
	//	}

	@Override
	public void paint(MapWrapper args) {

		int mid = rand.nextInt((int)((height  +0.0) / 2.0));
		
		mid = 500;
		System.out.println("mid " + mid);
		
		paintMnts(height,0 , args);
		paintFlat(mid, 0, args);


	}

	public void paintFlat(int top, int bottom, MapWrapper args) {
		int diff = top - bottom;
		double noise [][] = ImageTools.createNoise(diff, diff, width,(int)(diff * args.getDouble("flatscale", 1)), (int)(diff * args.getDouble("flatscale", 1)),args.getDouble("flatpersist", .1));

		for(int a = 0;  a < diff; a += diff / args.getDouble("divisor", 200)) {
			int []y = new int [width + 4];
			int []x = new int [width + 4];

			for(int i = 0;  i < width; i++) {
				x[i] = i;
				y[i] = height - (((int) (noise[a][i] * diff)) - (a / diff * bottom) - top);
			}
			int i = width;

			y[i] = height;
			x[i] = width;

			i++;
			y[i] = height;
			x[i] = 0;

			i++;
			y[i] = y[0];
			x[i] = 0;
			
			g.setColor(Color.black);
			g.drawPolygon(x, y, i);
			
			g.setColor(Color.white);
			g.drawPolygon(x, y, i);
		}
	}



	public void paintMnts(int top, int bottom, MapWrapper args) {
		int diff = top - bottom;

		double noise [][] = ImageTools.createNoise(diff, diff, width,(int)(diff * args.getDouble("mntscale", 1)), (int)(diff * args.getDouble("mntscale", 1)),args.getDouble("mntpersist", .4));
		Color c = Color.decode(args.getOrDefault("landcolor", "#fff"));

		for(int a = 0;  a < diff ; a += diff / args.getDouble("divisor", diff / 2)) {
			int []y = new int [width + 4];
			int []x = new int [width + 4];

			for(int i = 0;  i < width; i++) {
				x[i] = i;
				y[i] = (height - (diff - a)) - (int) (noise[a][i] * diff) ;
			}
			int i = width;

			y[i] = height;
			x[i] = width;

			i++;
			y[i] = height;
			x[i] = 0;

			i++;
			y[i] = y[0];
			x[i] = 0;

			g.setColor(Color.black);
			g.drawPolygon(x, y, i);
			
			g.setColor(c);
			g.drawPolygon(x, y, i);
		}
	}



}
