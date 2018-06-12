package com.ball.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ball.board.domain.AnswerRepository;
import com.ball.board.domain.Question;
import com.ball.board.domain.QuestionRepository;
import com.ball.board.domain.User;
import com.ball.board.utils.HttpSessionUtils;

@Controller
@RequestMapping("/questions")
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@GetMapping("")
	public String form(HttpSession session, Model model) {
		Object tempUser = HttpSessionUtils.sessionedUser(session);
		if (tempUser == null) {
			return "redirect:/";
		}
		model.addAttribute("loginedUser", (User) tempUser);
		return "/question/form";
	}

	@PostMapping("")
	public String question(String UserId, String title, String contents, HttpSession session) {
		Object tempUser = HttpSessionUtils.sessionedUser(session);
		if (tempUser == null) {
			return "redirect:/";
		}
		Question question = new Question((User) tempUser, title, contents);
		questionRepository.save(question);
		return "redirect:/";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model_1, Model model_2) {
		model_1.addAttribute("question", questionRepository.findById(id).get());
		model_2.addAttribute("answers", answerRepository.findAll());
		return "/question/show";
	}

}
