package Algo;

import java.math.BigDecimal;

public class Point {

	BigDecimal x , y;

	public Point() {
	}
	
	public Point(BigDecimal x, BigDecimal y) {
		super();
		this.x = x;
		this.y = y;
	}

	public BigDecimal getX() {
		return x;
	}

	public void setX(BigDecimal x) {
		this.x = x;
	}

	public BigDecimal getY() {
		return y;
	}

	public void setY(BigDecimal y) {
		this.y = y;
	}
	
}
