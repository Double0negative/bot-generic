package org.mcsg.bot.drawing.painters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.mcsg.bot.drawing.AbstractPainter;
import org.mcsg.bot.drawing.Vec2;
import org.mcsg.bot.util.MapWrapper;

public class BallPhysics extends AbstractPainter{

	public BallPhysics(BufferedImage img) {
		super(img);
		// TODO Auto-generated constructor stub
	}

	
	Vec2 pos = new Vec2(100,100);
	Vec2 vel = new Vec2(3, 0);
	Vec2 grav = new Vec2(0,.05);
		
	Vec2 drag = new Vec2(.003, .01);
	
	@Override
	public void paint(MapWrapper args) {

		
		for(int a = 0; a < 10000; a++) {
			g.setColor(Color.black);
			//g.fillRect(0, 0, width, height);
			vel = vel.add(grav);
			
			if(Math.abs(vel.x) > drag.getX() * 2) {
				vel.x += vel.x >= 0 ? -drag.x : drag.x;
			}
			
			if(Math.abs(vel.y) > drag.getY() * 2) {
				vel.y += vel.y >= 0 ? -drag.y : drag.y;
			}
			
			pos = pos.add(vel);
			g.setColor(Color.blue);
			this.drawCircle((int)pos.getX(),(int) pos.getY(), 50);
			
			if(pos.y > height) {
				vel = new Vec2(vel.getX(), -vel.getY());
			}
			
			
			
			
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}

}
