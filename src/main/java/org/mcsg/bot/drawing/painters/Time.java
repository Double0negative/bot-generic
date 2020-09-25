package org.mcsg.bot.drawing.painters;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.IntStream;

import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.util.MapWrapper;

public class Time extends AbstractPainter{

    Font font = new Font("Helvetica", Font.PLAIN, 200);
	
	public Time(BufferedImage img) {
		super(img);
	}

	@Override
	public void paint(MapWrapper args) {
		float hue = (float)getHue(.65);
		
		IntStream.range(0, this.height).forEach(e -> {
			float offset = (float) (.1 * (e + 0.0) / height);
			System.out.println(offset);
			g.setColor(Color.getHSBColor(hue + offset, .7f, .3f));
			g.drawLine(0, e, width, e);
		});
		
		g.setColor(Color.getHSBColor(hue, .7f, .7f));
		drawCenteredString(g, "Test Text", new Rectangle(width, height), font);
	}
	
	private double getHue(double offset) {
		LocalTime now = LocalTime.now(ZoneId.systemDefault()); // LocalTime = 14:42:43.062
		System.out.println(now.toSecondOfDay() + " " + now.toSecondOfDay()  / 86400.0);
        double percent =  offset + (now.toSecondOfDay()  / 86400.0);
        percent = percent - Math.floor(percent);
        return percent;
    }
	
	public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Set the font
	    g.setFont(font);
	    // Draw the String
	    g.drawString(text, x, y);
	}

}