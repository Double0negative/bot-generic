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
		
		paintMnts(height * .25,height *1.7 , args);
	    //paintFlat(mid, 0, args);


	}

	public void paintFlat(int top, int bottom, MapWrapper args) {
		int diff = top - bottom;
		double noise [][] = ImageTools.createNoise(height, width, height,(int)(width * args.getDouble("flatscale", 1)), (int)(diff * args.getDouble("flatscale", 1)),args.getDouble("flatpersist", .1));

		for(double a = top;  a < bottom; a += diff / args.getDouble("divisor", 200)) {
			int []y = new int [width + 4];
			int []x = new int [width + 4];

			for(int i = 0;  i < width; i++) {
				x[i] = i;
				y[i] = (int)  (a +  (noise[i][(int) a] * diff)) ;
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



	public void paintMnts(double top, double bottom, MapWrapper args) {
		int diff = (int) (bottom - top);

		double noise [][] = ImageTools.createNoise(diff, width, diff,(int)(width * args.getDouble("mntscale", 1)), (int)(diff * args.getDouble("mntscale", 1)),args.getDouble("mntpersist", .4));
		Color c = Color.decode(args.getOrDefault("landcolor", "#ffffff"));

		for(double a = top;  a < bottom ; a += diff / args.getDouble("divisor", diff / 2)) {
			int []y = new int [width + 4];
			int []x = new int [width + 4];

			for(int i = 0;  i < width; i++) {
				x[i] = i;
				y[i] =  (int) (a -  (noise[i][(int) ((int) a - top)] * (.7 *diff))) ;
			}
			int i = width;

			y[i] = height;
			x[i] = width;

			i++;
			y[i] = height;
			x[i] = -1;

			i++;
			y[i] = y[0];
			x[i] = -1;

			g.setColor(Color.black);
			g.drawPolygon(x, y, i);
			
			g.setColor(c);
			g.drawPolygon(x, y, i);
		}
	}



}
