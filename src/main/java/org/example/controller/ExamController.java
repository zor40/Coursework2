package org.example.controller;

import org.example.model.Question;
import org.example.service.ExaminerService;
import org.example.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/exam")
public class ExamController {

    private final ExaminerService examinerService;
    private final QuestionService questionService;

    public ExamController(ExaminerService examinerService,
                          QuestionService questionService) {
        this.examinerService = examinerService;
        this.questionService = questionService;
    }

    @GetMapping("/get/{amount}")
    public Collection<Question> getQuestions(@PathVariable int amount) {
        if (amount <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Amount must be positive"
            );
        }

        int available = questionService.getAll().size();
        if (amount > available) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Requested " + amount + " questions, available: " + available
            );
        }

        return examinerService.getQuestions(amount);
    }
}