package com.game;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ROLLS")
public class Roll {
	@Id	
	private int id;
	private int pins;
	private int frame;
	private int ball;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPins() {
		return pins;
	}
	public void setPins(int pins) {
		this.pins = pins;
	}
	public int getFrame() {
		return frame;
	}
	public void setFrame(int frame) {
		this.frame = frame;
	}
	public int getBall() {
		return ball;
	}
	public void setBall(int ball) {
		this.ball = ball;
	}
	@Override
	public String toString() {
		return "Roll [id=" + id + ", pins=" + pins + ", frame=" + frame + ", ball=" + ball + "]";
	}	
	
}
