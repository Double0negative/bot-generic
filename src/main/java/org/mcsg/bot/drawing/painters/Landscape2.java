package org.mcsg.bot.drawing.painters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.mcsg.bot.drawing.AbstractShaderLikePainter;
import org.mcsg.bot.drawing.ImageTools;
import org.mcsg.bot.drawing.Vec2;

public class Landscape2 extends AbstractShaderLikePainter {

	double noise[][];
	
	public Landscape2(BufferedImage img) {
		super(img);
		
		noise = ImageTools.createNoise(width, width, height, width , height, .6);
	}

	@Override
	public Color render(Vec2 coord) {
		// TODO Auto-generated method stub
		return null;
	}

}
