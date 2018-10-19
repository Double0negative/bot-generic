package org.mcsg.bot.drawing.painters;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;

import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.drawing.ImageTools;
import org.mcsg.bot.util.MapWrapper;

public class Bricks extends AbstractPainter {

    public Bricks(BufferedImage img) {
    	super(img);
    	}

    @Override
    public void paint(MapWrapper args) {

        g.setStroke(new BasicStroke(4));

        //int rows = rand.nextInt(20) + 1;
        //int columns = rand.nextInt(20) + 1;
        int rows = 20;
        int columns = 20;
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
        
        	float alphaRow;
        	float alphaColumn;
        	float alphaBrick;
        	
        	if(a < rows / 2) {
        		alphaRow = (1f / (rows / 2)) * (a + 1);
        	} else if(a > rows / 2) {
        		alphaRow = (rows / 2f) / (a + 1);
        	} else {
        		alphaRow = 1;
        	}
        	
        	for(int b = 0; b < columns; b++) {
        		if(b < columns / 2) {
        			alphaColumn =(1f / (columns / 2)) * (b + 1);
        		} else if(b > columns / 2) {
        			alphaColumn =(columns / 2f) / (b + 1);
        		} else {
        			alphaColumn = 1;
        		}
        		alphaBrick = alphaRow * alphaColumn;


        		if(alphaBrick > 1) { alphaBrick = 1; }
        		
        		System.out.println(alphaBrick);
        		
        		AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaBrick);
        		g.setComposite(alphaComposite);

        		
        		g.fillRect(((brickWidth + brickSep) * b) + startX, ((brickHeight + brickSep) * a) + startY, brickWidth, brickHeight);
        		g.drawRect(((brickWidth + brickSep) * b) + startX, ((brickHeight + brickSep) * a) + startY, brickWidth, brickHeight);

        	}
        }
        
        /*for(int a = 0; a < rows; a++) {
            setRandomColor(true);
            for(int b = 0; b < columns; b++) {

                g.fillRect(b * (brickWidth + brickSep), a * (brickHeight + brickSep), brickWidth, brickHeight);
                g.drawRect(b * (brickWidth + brickSep), a * (brickHeight + brickSep), brickWidth, brickHeight);

            }
        }*/
    }
}
