package org.mcsg.bot.drawing.filter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.mcsg.bot.drawing.Filter;
import org.mcsg.bot.drawing.ImageTools;
import org.mcsg.bot.util.MapWrapper;

public class Warp implements Filter {
	
	@Override
	public BufferedImage filter(BufferedImage input, Graphics2D g, MapWrapper map) {
		int[][][] colors = new int[input.getWidth()][input.getHeight()][3];
		
		
		int multi = Integer.parseInt(map.getOrDefault("warp", "1"));
		
		
		for(int x = 0; x < input.getWidth(); x++) {
			for(int y = 0; y < input.getHeight(); y++) {
				int[] color = ImageTools.getRgb(input.getRGB(x, y));
				int distance = (int) distance(input.getWidth(), input.getHeight(), x, y);
				int[] offset = offset(x, y, distance);
				for(int c = 0; c< 3 ;c++) {
					set(colors, x + offset[0] / (input.getWidth() / 50), y + offset[1], c, color[c]);
				}
			}
		}
		
		BufferedImage output = blank(input);
		for(int x = 0; x < input.getWidth(); x++) {
			for(int y = 0; y < input.getHeight(); y++) {
				output.setRGB(x, y, ImageTools.rgbToInt(colors[x][y][0], colors[x][y][1], colors[x][y][2]));
			}
		}
		return output;
	}

	private double distance(int width, int height, int x, int y) {
		return Math.sqrt(Math.pow((width/2-x), 2) + Math.pow((height/2-y), 2));
	}
	
	private int[] offset(int x, int y, int distance) {
		Random rand = new Random();
		return new int[] {rand.nextInt(Math.abs(distance * 2) + 1) - distance, rand.nextInt(Math.abs(distance * 2) + 1) - distance};
	}
	
	private void set(int[][][]color, int x, int y, int c, int value) {
		if(x >= 0 && x < color.length && y >= 0 && y < color[x].length && c >=0 && c < color[x][y].length) {
			color[x][y][c] = value;
		}
	}
	
}
