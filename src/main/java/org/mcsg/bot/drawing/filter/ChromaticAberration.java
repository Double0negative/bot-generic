package org.mcsg.bot.drawing.filter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.mcsg.bot.drawing.Filter;
import org.mcsg.bot.drawing.ImageTools;
import org.mcsg.bot.util.MapWrapper;

public class ChromaticAberration implements Filter {

	@Override
	public BufferedImage filter(BufferedImage input, Graphics2D g, MapWrapper map) {
		int[][][] colors = new int[input.getWidth()][input.getHeight()][3];
		
		
		int [] offsets = new int[3];
		
		String split[] = map.getOrDefault("aboffset", "15,0,-15").split(",");
		offsets = new int[] {Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2])};
		
		
		for(int x = 0; x < input.getWidth(); x++) {
			for(int y = 0; y < input.getHeight(); y++) {
				int[] color = ImageTools.getRgb(input.getRGB(x, y));
				
				for(int c = 0; c< 3 ;c++) {
					set(colors, x + offsets[c], y, c, color[c]);
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


	
	private void set(int[][][]color, int x, int y, int c, int value) {
		if(x >= 0 && x < color.length && y >= 0 && y < color[x].length && c >=0 && c < color[x][y].length) {
			color[x][y][c] = value;
		}
	}
	
}
