package org.mcsg.bot.drawing.filter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.mcsg.bot.drawing.Filter;
import org.mcsg.bot.drawing.PixelData;
import org.mcsg.bot.drawing.Vec2;
import org.mcsg.bot.util.MapWrapper;

public class Pixelate implements Filter{

	private ArrayList<PixelData> points;
	private HashMap<String, PixelData> data;
	private Random rand = new Random();

	@Override
	public BufferedImage filter(BufferedImage img, Graphics2D g, MapWrapper map) {
		this.points = new ArrayList<>();
		this.data = new HashMap<>();

		int size = map.getInt("pixelsize", 20);
		int dsize = (int) ((size + 0.0) * 1); 

		boolean distort = map.getBoolean("pixeldistort", false);

		for(int x = 0; x < img.getWidth(); x+=size){
			for(int y = 0; y < img.getHeight(); y+=size){
				int rgb = img.getRGB(x, y);
				int r = (rgb >> 16) & 0xFF;
				int gp = (rgb >> 8) & 0xFF;
				int b = (rgb & 0xFF);

				PixelData data =  new PixelData(x, y, new Color(r, gp, b));
				this.points.add(data);

				if(distort) {
					this.data.put(x / size + ":" + y / size, data);
				}

			}
		}

		if(distort) {

			final AtomicInteger count = new AtomicInteger(0);

			for(int i = 0; i < 4; i ++) {
				new Thread() {
					public void run() {
						for(int a = 0; a < points.size() / 4; a++) {
							int x1 = rand.nextInt(img.getWidth() / size);
							int y1 = rand.nextInt(img.getHeight() / size);

							int x2 = x1 + rand.nextInt(7) - 3;
							int y2 = y1 + rand.nextInt(7) - 3;

							PixelData p1 = data.get(x1 + ":" + y1);
							PixelData p2 = data.get(x2 + ":" + y2);

							if(p1 != null && p2 != null) {
								Color c1 = p1.getColor();

								p1.setColor(p2.getColor());
								p2.setColor(c1);
							}
						}
						count.incrementAndGet();
						synchronized (count) {
							count.notifyAll();
						}
					}
				}.start();
			}

			while(count.get() < 4) {
				try {
					synchronized (count) {
						count.wait();
					}
				} catch (InterruptedException e) {

				}
			}

		}

		BufferedImage output = blank(img);
		g = (Graphics2D)output.getGraphics();
		
		
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(0));
		for(PixelData point : points) {
			g.setColor(point.getColor());
			if(!map.getBoolean("square"))
				g.fillOval(point.getX() , point.getY() , dsize, dsize);
			else
				g.fillRect(point.getX(), point.getY() , dsize+1, dsize+1);
		}

		return output;

	}

	

}
