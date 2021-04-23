package org.mcsg.bot.drawing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

import org.mcsg.bot.util.MapWrapper;

public abstract class AbstractShaderLikePainter extends AbstractPainter {

	protected Vec2 iRes;
	protected boolean parallel;

	public AbstractShaderLikePainter(BufferedImage img) {
		super(img);

		this.iRes = new Vec2(width, height);
	}

	@Override
	public void paint(MapWrapper args) {
		IntStream stream = IntStream.range(0, width * height);
		if (parallel) {
			stream.parallel();
		}
		stream.forEach(i -> {
			int x = i % width;
			int y = i / width;

			Color color = this.render(new Vec2(x, y));
			this.img.setRGB(x, y, color.getRGB());
		});
	}

	public abstract Color render(Vec2 coord);

}
