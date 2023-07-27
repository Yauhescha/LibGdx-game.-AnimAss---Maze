package com.hescha.game.maz.generator;

public class Cell {
	private Cell node;
	private int x;
	private int y;

	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Cell getNext() {
		return node;
	}

	public void setNext(Cell node) {
		this.node = node;
	}

	public String toString() {
		return "[" + x + ":" + y + "]";
	}
}
