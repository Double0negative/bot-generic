package org.mcsg.bot.drawing;

import java.awt.Color;

public class Vec3 {

	public double x, y, z;

	public Vec3() {
		
	}
	
	public Vec3(Vec2 v) {
		this.x = v.x;
		this.y = v.y;
	}
	
	public Vec3(Vec2 v, double z) {
		this.x = v.x;
		this.y = v.y;
		this.z = z;
	}
	
	public Vec3(double x, Vec2 v) {
		this.x = x;
		this.y = v.x;
		this.z = v.y;
	}
	
	public Vec3(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void add(double a) {
		this.x += a;
		this.y += a;
		this.z += a;
	}

	public void mult(double a) {
		this.x *= a;
		this.y *= a;
		this.z *= a;
	}

	public void div(double a) {
		this.x /= a;
		this.y /= a;
		this.z /= a;
	}

	public Color toColor() {
		return new Color((int) ImageTools.limit(x * 255, 255, 0), (int) ImageTools.limit(y * 255, 255, 0),
				(int) ImageTools.limit(z * 255, 255, 0), 255);
	}

	public Color toColorInt() {
		return new Color((int) ImageTools.limit(x, 255, 0), (int) ImageTools.limit(y, 255, 0),
				(int) ImageTools.limit(z, 255, 0), 255);
	}
}