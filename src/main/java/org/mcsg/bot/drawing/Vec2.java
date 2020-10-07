package org.mcsg.bot.drawing;

public class Vec2 {

	public int x,y;

	public Vec2(Vec2 point){
		this(point.getX(), point.getY());
	}
	
	public Vec2(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void incX(int x){
		this.x += x;
	}
	
	public void incY(int y){
		this.y += y;
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

	public void add(int a) {
		this.x += a;
		this.y += a; 
	}
	
	public void mult(int a) {
		this.x *= a;
		this.y *= a; 
	}
	
	public void div(int a) {
		this.x /= a;
		this.y /= a; 
	}
	
	public int distance(int x, int y){
		return (int) Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
	}
	
	public int distance(Vec2 pnt){
		return distance(pnt.getX(), pnt.getY());
	}
	
	
}