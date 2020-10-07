package org.mcsg.bot.drawing;

public class Vec3 {

	public int x,y,z;

	public Vec3(int x, int y, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
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

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public void add(int a) {
		this.x += a;
		this.y += a; 
		this.z += a;
	}
	
	public void mult(int a) {
		this.x *= a;
		this.y *= a; 
		this.z *= a;
	}
	
	public void div(int a) {
		this.x /= a;
		this.y /= a; 
		this.z /= a;
	}
}