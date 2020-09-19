package org.mcsg.bot.drawing.filter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import org.mcsg.bot.drawing.Filter;
import org.mcsg.bot.drawing.ImageTools;
import org.mcsg.bot.drawing.filter.Smoosh_.ColorCount;
import org.mcsg.bot.util.MapWrapper;

import de.androidpit.colorthief.ColorThief;

public class Smoosh implements Filter{

	@Override
	public BufferedImage filter(BufferedImage input, Graphics2D g, MapWrapper map) {
		int[][] colors = ColorThief.getPalette(input, map.getInt("palette", 10));
				
		return create(input, colors);
	}

	private BufferedImage create(BufferedImage input, int[][] colors) {
		for(int x = 0; x < input.getWidth(); x++) {
			for(int y = 0; y < input.getHeight(); y++) {
				int []color = ImageTools.getRgb(input.getRGB(x, y));
				
				int [] close = getClosest(color, colors);
				
				input.setRGB(x, y, ImageTools.rgbToInt(close[0], close[1], close[2]));
			}
		}
		
		return input;
	}
	
	private int[] getClosest(int [] color, int [][] colors) {
		double distance = Double.MAX_VALUE;
		int [] val = null;
		for(int [] test : colors) {
			double d = distance(test, color);
			if(distance > d) {
				distance = d;
				val = test;
			}
		}
		
		return val;
	}
	
	private double distance(int[] c1, int[] c2) {
		return Math.pow((c1[0]-c2[0])*0.30, 2)
				+ Math.pow((c1[1]- c2[1])*0.59,2)
				+ Math.pow((c1[2]- c2[2])*0.11,2);
	}
}
