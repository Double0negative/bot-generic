package org.mcsg.bot.drawing.filter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.mcsg.bot.drawing.Filter;
import org.mcsg.bot.util.MapWrapper;

public class ScanLines implements Filter{

	@Override
	public BufferedImage filter(BufferedImage input, Graphics2D g, MapWrapper map) {
		
		String[] colorss = map.getOrDefault("scancolor", "0,0,0,60").split(","); 
		int[] color = new int[] {Integer.parseInt(colorss[0]), Integer.parseInt(colorss[1]), Integer.parseInt(colorss[2]), Integer.parseInt(colorss[3])};
		
		double size = map.getInt("scansize", 1);
		double split = map.getInt("scansplit", 2);
		
		g.setStroke(new BasicStroke(1));
		
		BufferedImage img = transparent(input);
		
		g.setStroke(new BasicStroke(1));
		
		for(double y = 0; y < input.getHeight(); y++) {
			
			double position = (Math.abs((y % split) - split / 2) - (split / 2 - size)) / size ;
			
			position = Math.max(0, position);
			
			int a =  (int)((color[3]  + 0.0)* position);
			
			//System.out.println(position + " " +color[3] + " " + a);
			
			g.setColor(new Color(color[0], color[1], color[2], a));
			g.drawLine(0, (int)y, input.getWidth(), (int)y);	
			
		}	
		return input;
	}
  
}
