package org.mcsg.bot.drawing.painters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.util.MapWrapper;

public class Pyramid extends AbstractPainter {
	
	public Pyramid(BufferedImage img) {
		super(img);
	}
	
	public void paint(MapWrapper args) {
		
	int baseBrickAmount = rand.nextInt(20) + 1;
	int startingX = rand.nextInt(width);
	int startingY = rand.nextInt(height);
	int xPos = startingX;
	int yPos = startingY;
	int brickWidth = rand.nextInt(width / baseBrickAmount);
	int brickHeight = rand.nextInt(brickWidth);
	System.out.println(baseBrickAmount);
	System.out.println(xPos);
	System.out.println(yPos);
	System.out.println(brickWidth);
	System.out.println(brickHeight);
	
		g.setColor(Color.WHITE);
		for(int i = 0; i < baseBrickAmount; i++) {
			for(int j = 0; j < baseBrickAmount - i; j++) {
				setRandomColor(true);
				g.fillRect(xPos, yPos, brickWidth, brickHeight);
				xPos += brickWidth;
			}
			xPos = startingX + ((brickWidth / 2) * (i + 1));
			yPos -= brickHeight;
		}
	}

}
