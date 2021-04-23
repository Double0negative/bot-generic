package org.mcsg.bot.drawing.painters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.mcsg.bot.drawing.AbstractShaderLikePainter;
import org.mcsg.bot.drawing.Vec2;
import org.mcsg.bot.drawing.Vec3;

public class ShaderTest extends AbstractShaderLikePainter{

	public ShaderTest(BufferedImage img) {
		super(img);
	}

	@Override
	public Color render(Vec2 coord) {
		Vec2 uv = coord.div(iRes);
		
		
		return new Vec3(rand.nextDouble(), rand.nextDouble(), rand.nextDouble()).toColor();
	}

}
