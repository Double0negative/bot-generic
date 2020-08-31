package org.mcsg.bot.drawing.filter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mcsg.bot.drawing.Filter;
import org.mcsg.bot.drawing.ImageTools;
import org.mcsg.bot.util.MapWrapper;

public class Smoosh_ implements Filter{

	@Override
	public BufferedImage filter(BufferedImage input, Graphics2D g, MapWrapper map) {
		List<ColorCount> topColors = sample(input);
		
		System.out.println(topColors);
		
		return create(input, topColors);
	}

	private BufferedImage create(BufferedImage input, List<ColorCount> colors) {
		BufferedImage output = blank(input);
		
		for(int x = 0; x < input.getWidth(); x++) {
			for(int y = 0; y < input.getHeight(); y++) {
				int color = input.getRGB(x, y);
				
				int close = getClosestColor(color, colors);
				
				output.setRGB(x, y, close);
			}
		}
		
		return output;
	}
	
	private int getClosestColor(int color, List<ColorCount> colors) {
		double distance = Double.MAX_VALUE;
		int c = 0;
		
		for(ColorCount count : colors) {
			double d = count.colorDistance(color);
			if(d < distance) {
				distance = d;
				c = count.getColor();
			}
		}
		
		return c;
	}

	private List<ColorCount> sample(BufferedImage input) {
		double samplex = 0;
		double sampley = 0;

		if(input.getWidth() < 300 && input.getHeight() < 300) {
			samplex = 1; 
			sampley = 1;
		} else {
			samplex = input.getWidth() / 1200;
			sampley = input.getHeight() / 1200;
		}

		Map<Integer, ColorCount> counts = new HashMap<>();

		for(double x = 0; x < input.getWidth() - samplex; x+=samplex) {
			for(double y = 0; x < input.getHeight() - samplex; x+=sampley) {
				int color [] = ImageTools.getRgb(input.getRGB((int)x,(int) y));
				int copy [] = Arrays.copyOf(color, 3);
				/*color[0] ;
				color[1];
				color[2] /= 8;*/

				int icolor = ImageTools.rgbToInt(color[0], color[1], color[2]);
				int ccolor = ImageTools.rgbToInt(copy[0], copy[1], copy[2]);

				ColorCount count = counts.getOrDefault(icolor, new ColorCount(ccolor, 0));
				count.inc();

				counts.put(icolor, count);
			}
		}

		List<ColorCount> sorted = new ArrayList<>(counts.values());
		sorted.sort((a, b) -> {
			return b.getCount() - a.getCount();
		});

		return sorted.subList(0, Math.min(20, sorted.size()));
	}


	protected class ColorCount {
		private int color, count;

		public ColorCount(int color, int count) {
			super();
			this.color = color;
			this.count = count;
		}

		public int getColor() {
			return color;
		}

		public int getCount() {
			return count;
		}

		public void inc() {
			count++;
		}

		public double colorDistance(int color) {
			int [] c1 = ImageTools.getRgb(this.color);
			int [] c2 = ImageTools.getRgb(color);


			return Math.pow((c1[0]-c2[0])*0.30, 2)
					+ Math.pow((c1[1]- c2[1])*0.59,2)
					+ Math.pow((c1[2]- c2[2])*0.11,2);
		}
		
		@Override
		public String toString() {
			return "[color="+ color +", count=" + count +"]";
		}
	}
}
