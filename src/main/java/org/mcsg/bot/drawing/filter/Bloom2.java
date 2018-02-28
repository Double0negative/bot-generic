package org.mcsg.bot.drawing.filter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.mcsg.bot.drawing.Filter;
import org.mcsg.bot.drawing.ImageTools;
import org.mcsg.bot.util.MapWrapper;

public class Bloom2 implements Filter {




	@Override
	public BufferedImage filter(BufferedImage input, Graphics2D g, MapWrapper map) {
		double[][][] colors = new double[input.getWidth()][input.getHeight()][3];


		int bloom = map.getInt("bloom", 5);


		for(double x = 0; x < input.getWidth(); x++) {
			for(double y = 0; y < input.getHeight(); y++) {
				int[] color = ImageTools.getRgb(input.getRGB((int)x, (int)y));


				for(double x2 = x - bloom; x2 < x + bloom; x2++) {
					for(double y2 = y - bloom; y2 < y + bloom; y2++) {
						for(int c = 0; c< 3 ;c++) {
							
							double offset = (color[c] - 200 ) / 15;
							offset = Math.max( 0, offset); // + (bloom -  Math.abs(distance(x2,y2, x, y))) / 2)
						
							set(colors, (int)x2,(int) y2, c,  offset);
						}
					}
				}
			}
		}

		BufferedImage output = blank(input);
		for(int x = 0; x < input.getWidth(); x++) {
			for(int y = 0; y < input.getHeight(); y++) {
				int[] color = ImageTools.getRgb(input.getRGB(x, y));

				output.setRGB(x, y, ImageTools.rgbToInt(
						(int)Math.min(255, color[0] + colors[x][y][0]), 
						(int)Math.min(255, color[1] + colors[x][y][1]), 
						(int)Math.min(255, color[2] + colors[x][y][2])));
			}
		}
		return output;
	}
	
	private double distance(double width, double height, double x, double y) {
		return Math.sqrt(Math.pow((width-x), 2) + Math.pow((height-y), 2));
	}

	private void set(double[][][]color, int x, int y, int c, double value) {
		if(x >= 0 && x < color.length && y >= 0 && y < color[x].length && c >=0 && c < color[x][y].length) {
			color[x][y][c] += value;
		}
	}

}
