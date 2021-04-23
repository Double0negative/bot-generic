package org.mcsg.bot.drawing;

public class Point {

	public int x,y;

	public Point(Point point){
		this(point.getX(), point.getY());
	}
	
	public Point(int x, int y) {
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
	
	public int distance(Point pnt){
		return distance(pnt.getX(), pnt.getY());
	}
	
	
}