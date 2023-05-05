package com.game;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BowlingGameController {

	private int rollId;
	private int[] rolls;
	private boolean bonusFrame;
	boolean bonusFrameMesageSent;
	@Autowired
	BowlingGameRepo repo;

	@RequestMapping("/BowlingGame")
	public ModelAndView game(Model model) {
		rollId = 1;
		rolls = new int[23];
		bonusFrame = false;
		bonusFrameMesageSent = false;
		repo.deleteAll();
		model.addAttribute("frame", 1);
		model.addAttribute("ball", 1);
		ModelAndView mv = new ModelAndView("game.jsp");
		return mv;
	}

	@RequestMapping(value = "/addRoll", method = RequestMethod.POST)
	public ModelAndView addRoll(@RequestParam int pins, @RequestParam int frame, @RequestParam int ball, Model model) {
		List<Roll> rollList;
		if (ball == 2) {
			Roll rollPrev = repo.getByFrame(frame);
			if (rollPrev.getPins() + pins > 10) {
				model.addAttribute("errorMessage", "Total number of pins in one frame is 10!\\n"
						+ "Number of pins left in this frame is " + (10 - rollPrev.getPins()) + "!");

				rollList = repo.findAllByOrderById();
				for (Roll rl : rollList) {
					model.addAttribute("roll" + rl.getFrame() + rl.getBall(), rl.getPins());
				}

				model.addAttribute("frame", frame);
				model.addAttribute("ball", ball);
				ModelAndView mv = new ModelAndView("game.jsp");
				return mv;
			}
			if (rollPrev.getPins() + pins == 10) {
				model.addAttribute("gameMessage", "NICE, SPARE!!");
				if (frame == 10) {
					bonusFrame = true;
				}
			}
		}

		Roll roll = new Roll();
		roll.setId(rollId++);
		roll.setPins(pins);
		roll.setFrame(frame);
		roll.setBall(ball);
		repo.save(roll);

		if (ball == 1 && pins == 10) {
			model.addAttribute("gameMessage", "NICE, STRIKE!!");
			if (frame == 10) {
				bonusFrame = true;
			}
			frame++;

		} else if (ball == 2) {
			frame++;
			ball = 1;
		} else {
			ball = 2;
		}

		if (pins == 0) {
			model.addAttribute("gameMessage", "FAIL, MISS!!");
		}

		if (frame == 12 || (frame == 11 && !bonusFrame)) {
			model.addAttribute("gameOverMessage", "GAME OVER!!");
		}

		rollList = repo.findAllByOrderById();
		for (Roll rl : rollList) {
			int rollIndex = rl.getId() - 1;
			rolls[rollIndex] = rl.getPins();
			model.addAttribute("roll" + rl.getFrame() + rl.getBall(), rl.getPins());
		}

		// System.out.println(Arrays.toString(rolls));

		int score = getScore();
		model.addAttribute("frame", frame);
		model.addAttribute("ball", ball);
		model.addAttribute("score", score);

		if (bonusFrame) {
			if (!bonusFrameMesageSent) {
				model.addAttribute("bonusFrameMesage", "BONUS BALLS!!");
				bonusFrameMesageSent = true;
			} else {
				model.addAttribute("bonusFrameMesage", "");
			}
		}

		ModelAndView mv = new ModelAndView("game.jsp");
		return mv;
	}

	public int getScore() {
		int score = 0;
		int rollIndex = 0;
		for (int frameIndex = 0; frameIndex < 11; frameIndex++) {
			if (isStrike(rollIndex)) {
				score += getStrikeScore(rollIndex);
				rollIndex++;
			} else if (isSpare(rollIndex)) {
				score += getSpareScore(rollIndex);
				rollIndex += 2;
			} else {
				score += getStandardScore(rollIndex);
				rollIndex += 2;
			}
		}
		return score;
	}

	private boolean isSpare(int rollIndex) {
		return rolls[rollIndex] + rolls[rollIndex + 1] == 10;
	}

	private boolean isStrike(int rollIndex) {
		return rolls[rollIndex] == 10;
	}

	private int getStandardScore(int rollIndex) {
		return rolls[rollIndex] + rolls[rollIndex + 1];
	}

	private int getSpareScore(int rollIndex) {
		return 10 + rolls[rollIndex + 2];
	}

	private int getStrikeScore(int rollIndex) {
		return 10 + rolls[rollIndex + 1] + rolls[rollIndex + 2];
	}

}
