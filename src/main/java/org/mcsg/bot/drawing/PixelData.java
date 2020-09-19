package org.mcsg.bot.drawing;

import java.awt.Color;

public class PixelData{
		int x,y;
		Color color;
		public PixelData(int x, int y, Color color) {
			super();
			this.x = x;
			this.y = y;
			this.color = color;
		}
		public PixelData(int x, int y) {
			this(x, y, null);
		}
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public Color getColor() {
			return color;
		}
		public void setColor(Color color) {
			this.color = color;
		}
		
	}