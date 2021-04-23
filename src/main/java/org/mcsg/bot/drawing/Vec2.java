package org.mcsg.bot.drawing;

public class Vec2 {

	public double x, y;

	public Vec2(Vec2 podouble) {
		this(podouble.getX(), podouble.getY());
	}

	public Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Vec2 add(double a) {
		return new Vec2(x + a, y + a);
	}

	public Vec2 add(Vec2 vec2) {
		return new Vec2(x + vec2.x, y + vec2.y);
	}

	public Vec2 multi(double a) {
		return new Vec2(x * a, y * a);
	}

	public Vec2 multi(Vec2 vec2) {
		return new Vec2(x * vec2.x, y * vec2.y);
	}

	public Vec2 div(double a) {
		return new Vec2(x / a, y / a);
	}

	public Vec2 div(Vec2 vec2) {
		return new Vec2(x / vec2.x, y / vec2.y);
	}

	public double distance(double x, double y) {
		return (double) Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
	}

	public double distance(Vec2 pnt) {
		return distance(pnt.getX(), pnt.getY());
	}

	public Point toPoint() {
		return new Point((int) this.x, (int) this.y);
	}

}