package com.example.bowlingGameTests;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.example.bowlingGame.BowlingGame;

public class BowlingGameTests {
	
	BowlingGame game;
	
	@Before
	public void setUp() {
		game = new BowlingGame();
	}
	
	@Test
	public void rollAllGutterGame() {
		game = new BowlingGame();
		rollMany(0, 20);
		assertEquals(0, game.getScore());
	}

	@Test
	public void rollAllOneGame() {		
		rollMany(1, 20);
		assertEquals(20, game.getScore());
	}
	
	@Test
	public void rollStandardGame() {		
		game.roll(5);
		game.roll(1);		
		game.roll(3);
		game.roll(3);		
		game.roll(7);
		game.roll(1);		
		game.roll(2);
		game.roll(7);		
		game.roll(2);
		game.roll(2);		
		game.roll(6);
		game.roll(3);		
		game.roll(1);
		game.roll(4);		
		game.roll(0);
		game.roll(9);		
		game.roll(2);
		game.roll(5);		
		game.roll(8);
		game.roll(0);		
		assertEquals(71, game.getScore());
	}
	
	@Test
	public void rollSpareGame() {		
		game.roll(4);
		game.roll(6);
		game.roll(3);
		game.roll(1);
		rollMany(0, 16);
		assertEquals(17, game.getScore());
	}
	
	@Test
	public void rollStrikeGame() {
		game = new BowlingGame();
		game.roll(10);
		game.roll(6);
		game.roll(3);
		game.roll(1);
		rollMany(0, 16);
		assertEquals(29, game.getScore());
	}
	
	@Test
	public void rollPerfectGame() {		
		rollMany(10, 11);
		assertEquals(300, game.getScore());
	}
	
	private void rollMany(int pins, int rolls) {
		for (int i = 0; i < rolls; i++)
		game.roll(pins);
	}

}

