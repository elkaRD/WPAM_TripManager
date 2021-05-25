package com.elkard.tripmanager.controllers;

import com.elkard.tripmanager.dto.requests.question.AddAnswerRequest;
import com.elkard.tripmanager.dto.requests.question.AddQuestionRequest;
import com.elkard.tripmanager.dto.requests.question.SelectAnswerRequest;
import com.elkard.tripmanager.dto.requests.question.SubmitAnswerRequest;
import com.elkard.tripmanager.services.QuestionService;
import com.elkard.tripmanager.dto.responses.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/question")
public class QuestionController
{
    @Autowired
    private QuestionService questionService;

    @PostMapping("/addQuestion")
    public BaseResponse addQuestion(@RequestBody AddQuestionRequest request)
    {
        return questionService.addQuestion(request);
    }

    @PostMapping("/addAnswer")
    public BaseResponse addAnswer(@RequestBody AddAnswerRequest request)
    {
        return questionService.addAnswer(request);
    }

    @PutMapping("/submitAnswer")
    public BaseResponse submitAnswer(@RequestBody SubmitAnswerRequest request)
    {
        return questionService.submitAnswer(request);
    }

    @PutMapping("/selectAnswer")
    public BaseResponse selectAnswer(@RequestBody SelectAnswerRequest request)
    {
        return questionService.selectAnswer(request);
    }
}
