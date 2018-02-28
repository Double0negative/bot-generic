package org.mcsg.bot.drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.mcsg.bot.util.MapWrapper;

public interface Filter {

	public BufferedImage filter(BufferedImage input,  Graphics2D g, MapWrapper map);
	
	public default BufferedImage transparent(BufferedImage img) {
		BufferedImage output = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		return output;
	}
	
	
	public default BufferedImage blank(BufferedImage img) {
		BufferedImage output = transparent(img);
		
		Graphics2D g = (Graphics2D)output.getGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, output.getWidth(), output.getHeight());
		
		return output;
	}
	
	public default BufferedImage scale(BufferedImage img, int width, int height) {
		return ImageTools.scale(img, width, height);
	}
	
	public default BufferedImage duplicate(BufferedImage input) {
		
		return null;
		
	}
	
}
