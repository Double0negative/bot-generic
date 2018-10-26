
package org.mcsg.bot.drawing.painters;

import java.awt.BasicStroke;
import java.awt.image.BufferedImage;

import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.util.MapWrapper;

public class RoundRect extends AbstractPainter {

	public RoundRect(BufferedImage img) {
		super(img);
	}

	@Override
	public void paint(MapWrapper args) {

		g.setStroke(new BasicStroke(4));

		int rows = rand.nextInt(20) + 5;
		int columns = rand.nextInt(20) + 5;
		int startX = rand.nextInt(width / 2);
		int startY = rand.nextInt(height / 2);
		int brickSep = rand.nextInt(width / 40);
		int brickWidth = rand.nextInt(100);
		int brickHeight = rand.nextInt(100);


		while(startX + ((columns - 1) * (brickWidth + brickSep)) > width) {
			columns--;
		}

		while(startY + ((rows - 1) * (brickHeight + brickSep)) > height) {
			rows--;
		}


		for(int a = 0; a < rows; a++) {
			setRandomColor(false);

			int arcRow;
			int arcColumn;
			//int arcBrick;

			if(a <= rows / 2) {
				arcRow = (brickHeight) / (a + 1);
			} else if(a > rows / 2) {
				arcRow = (brickHeight) / (rows - a);
			} else {
				arcRow = 1;
			}

			for(int b = 0; b < columns; b++) {
				if(b <= columns / 2) {
					arcColumn = (brickWidth) / (b + 1);
				} else if(b > columns / 2) {
					arcColumn = (brickWidth) / (columns - b);
				} else {
					arcColumn = 1;
				}
				//arcBrick = (arcRow + arcColumn);
				
				System.out.println(arcRow);
				System.out.println(arcColumn);
				//System.out.println(arcBrick);

				g.fillRoundRect(((brickWidth + brickSep) * b) + startX, ((brickHeight + brickSep) * a) + startY, brickWidth, brickHeight, arcColumn, arcRow);
			}
		}
	}
}
