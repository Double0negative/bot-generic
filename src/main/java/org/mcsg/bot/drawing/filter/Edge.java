package org.mcsg.bot.drawing.filter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.mcsg.bot.drawing.Filter;
import org.mcsg.bot.util.CannyEdgeDetector;
import org.mcsg.bot.util.MapWrapper;

public class Edge implements Filter{

	@Override
	public BufferedImage filter(BufferedImage input, Graphics2D g, MapWrapper map) {
		CannyEdgeDetector edge = new CannyEdgeDetector();
		edge.setSourceImage(input);
		edge.setGaussianKernelWidth(map.getInt("kernelwidth", 16));
		edge.setGaussianKernelRadius((float) map.getDouble("kernelradius", 2));
		
		edge.process();
		return edge.getEdgesImage();
	}

}
