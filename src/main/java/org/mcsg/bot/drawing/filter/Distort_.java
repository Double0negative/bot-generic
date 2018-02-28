package org.mcsg.bot.drawing.filter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.mcsg.bot.drawing.Filter;
import org.mcsg.bot.drawing.filter.Pixelate.PixelData;
import org.mcsg.bot.util.MapWrapper;

public class Distort_ implements Filter{

	@Override
	public BufferedImage filter(BufferedImage img, Graphics2D g, MapWrapper map) {
		Random rand = new Random();

		final AtomicInteger count = new AtomicInteger(0);

		for(int i = 0; i < 4; i ++) {
			new Thread() {
				public void run() {
					for(int a = 0; a < 10000 ; a++) {
						int x1 = rand.nextInt(img.getWidth());
						int y1 = rand.nextInt(img.getHeight());

						int x2 = x1 + rand.nextInt(11) - 7;
						int y2 = y1 + rand.nextInt(11) - 7;

						if(x2 > 0 && x2 < img.getWidth() && y2 > 0 && y2 < img.getHeight())
							img.setRGB(x1, y1, img.getRGB(x2, y2));
					}
					count.incrementAndGet();
					synchronized (count) {
						count.notifyAll();
					}
				}
			}.start();
		}

		return blank(img);
	}

}
