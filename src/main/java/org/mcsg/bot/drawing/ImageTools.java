package org.mcsg.bot.drawing;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.mcsg.bot.util.SimplexNoise;

public class ImageTools {

	public static double[][] createNoise(int max, int width, int height, int scalex, int scaley, double persist){
		SimplexNoise noise = new SimplexNoise(max, persist, new Random().nextInt());

		double[][] result=new double[width][height];

		double xStart=0;
		double yStart=0;

		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				int x=(int)(xStart+i*((scalex-xStart)/width));
				int y=(int)(yStart+j*((scaley-yStart)/height));
				result[i][j]=noise.getNoise(x,y);
			}
		}
		return result;
	}

	public static void createBWNoise(BufferedImage img, int max, int width, int height, int boundxs, int boundys, int boundx, int boundy, int scalex, int scaley, double persist){
		double[][] result=  createNoise(max , width, height, scalex, scaley, persist);
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				if (result[x][y]>1){
					result[x][y]=1;
				}
				if (result[x][y]<0){
					result[x][y]=0;
				}
				Color col=new Color((float)result[x][y],(float)result[x][y],(float)result[x][y]); 
				img.setRGB(x, y, col.getRGB());
			}
		}
	}

	public static void createColorNoise(BufferedImage img, int max, int width, int height, int boundxs, int boundys, int boundx, int boundy, int scalex, int scaley, double persist){
		double[][] r=  createNoise(max, width, height, scalex, scaley, persist);
		double[][] g=  createNoise(max, width, height, scalex, scaley, persist);
		double[][] b=  createNoise(max, width, height, scalex, scaley, persist);

		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				r[x][y] = limit(r[x][y], 1, 0);
				g[x][y] = limit(g[x][y], 1, 0);
				b[x][y] = limit(b[x][y], 1, 0);

				Color col=new Color((float)r[x][y],(float)g[x][y],(float)b[x][y]); 
				img.setRGB(x, y, col.getRGB());
			}
		}
	}

	public static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight) {
		BufferedImage scaledImage = null;
		if (imageToScale != null) {
			scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.getType());
			Graphics2D graphics2D = scaledImage.createGraphics();
			graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
			graphics2D.dispose();
		}
		return scaledImage;
	}

	public static int[] getRgb(int rgb) {
		int r = (rgb >> 16) & 0xFF;
		int g = (rgb >> 8) & 0xFF;
		int b = (rgb & 0xFF);

		return new int[]{r, g, b};
	}

	public static int rgbToInt(int R, int G, int B) {
		R = (R << 16) & 0x00FF0000;
		G = (G << 8) & 0x0000FF00;
		B = B & 0x000000FF;

		return 0xFF000000 | R | G | B;
	}


	public static double limit (double val, double max, double min){
		return (val > max) ? max : val < min ? min : val;
	}

	public static int limit (int val, int max, int min){
		return (val > max) ? max : val < min ? min : val;
	}

}