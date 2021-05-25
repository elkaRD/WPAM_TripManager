package com.elkard.tripmanager.services;

import com.elkard.tripmanager.dto.requests.place.AddPlaceRequest;
import com.elkard.tripmanager.dto.requests.question.AddAnswerRequest;
import com.elkard.tripmanager.dto.requests.question.AddQuestionRequest;
import com.elkard.tripmanager.dto.requests.question.SelectAnswerRequest;
import com.elkard.tripmanager.dto.requests.question.SubmitAnswerRequest;
import com.elkard.tripmanager.dto.responses.BaseResponse;
import com.elkard.tripmanager.exceptions.RequestParamsException;
import com.elkard.tripmanager.model.dao.*;
import com.elkard.tripmanager.model.entities.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuestionService
{
    private static final Logger log = LogManager.getLogger(QuestionService.class);

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private AnswerVoteRepository answerVoteRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private AccessService accessService;

    public BaseResponse addQuestion(AddQuestionRequest request)
    {
        log.info("START addQuestion");

        BaseResponse response = new BaseResponse();

        try
        {
            User user = userService.getUserFromRequest(request);
            Trip trip = tripRepository.getOne(request.getTripId());

            //TODO check if provided deviceId should have access to this trip
            accessService.checkAccessToTrip(user, trip);

            createAndSaveQuestion(user, trip, request.getQuestion(), request.getAnswers());

            log.info("SUCCESS addQuestion");
        }
        catch (Exception e)
        {
            response.setErrorCode("-1");
            response.setErrorDesc(e.getMessage());
            log.error("ERROR addQuestion: " + e.getMessage(), e);
        }

        return response;
    }

    public BaseResponse addAnswer(AddAnswerRequest request)
    {
        log.info("START addAnswer");

        BaseResponse response = new BaseResponse();

        try
        {
            User user = userService.getUserFromRequest(request);
            Trip trip = tripRepository.getOne(request.getTripId());

            //TODO check if provided deviceId should have access to this trip
            accessService.checkAccessToTrip(user, trip);

            Question question = questionRepository.getOne(request.getQuestionId());

            //TODO check if question belongs to right trip

            addAnswer(user, trip, question, request.getAnswer());

            log.info("SUCCESS addAnswer");
        }
        catch (Exception e)
        {
            response.setErrorCode("-1");
            response.setErrorDesc(e.getMessage());
            log.error("ERROR addAnswer: " + e.getMessage(), e);
        }

        return response;
    }

    public BaseResponse submitAnswer(SubmitAnswerRequest request)
    {
        log.info("START submitAnswer");

        BaseResponse response = new BaseResponse();

        try
        {
            User user = userService.getUserFromRequest(request);
            Trip trip = tripRepository.getOne(request.getTripId());

            //TODO check if provided deviceId should have access to this trip
            accessService.checkAccessToTrip(user, trip);

            Question question = questionRepository.getOne(request.getQuestionId());

            //TODO check if question belongs to right trip

            submitAnswer(user, trip, question, request.getSelectedAnswers());

            log.info("SUCCESS submitAnswer");
        }
        catch (Exception e)
        {
            response.setErrorCode("-1");
            response.setErrorDesc(e.getMessage());
            log.error("ERROR submitAnswer: " + e.getMessage(), e);
        }

        return response;
    }

    public BaseResponse selectAnswer(SelectAnswerRequest request)
    {
        log.info("START selectAnswer");

        BaseResponse response = new BaseResponse();

        try
        {
            User user = userService.getUserFromRequest(request);
            Trip trip = tripRepository.getOne(request.getTripId());

            //TODO check if provided deviceId should have access to this trip
            accessService.checkAccessToTrip(user, trip);

            Question question = questionRepository.getOne(request.getQuestionId());

            //TODO check if question belongs to right trip

            selectAnswer(user, trip, question, request.getAnswerId(), request.getSelected());

            log.info("SUCCESS selectAnswer");
        }
        catch (Exception e)
        {
            response.setErrorCode("-1");
            response.setErrorDesc(e.getMessage());
            log.error("ERROR selectAnswer: " + e.getMessage(), e);
        }

        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    private void createAndSaveQuestion(User user, Trip trip, String questionText, List<String> answers)
    {
        Question question = new Question();
        question.setUser(user);
        question.setTrip(trip);
        question.setQuestion(questionText);
        question.setQuestionDate(Instant.now());
        questionRepository.save(question);

        for (String answerText : answers)
        {
            Answer answer = new Answer();
            answer.setUser(user);
            answer.setTrip(trip);
            answer.setQuestion(question);
            answer.setAnswer(answerText);
            answerRepository.save(answer);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    private void addAnswer(User user, Trip trip, Question question, String answerText)
    {
        Answer answer = new Answer();
        answer.setUser(user);
        answer.setTrip(trip);
        answer.setQuestion(question);
        answer.setAnswer(answerText);
        answerRepository.save(answer);
    }

    @Transactional(rollbackFor = Exception.class)
    private void submitAnswer(User user, Trip trip, Question question, List<Long> selectedAnswers)
    {
        Set<Long> selectedBefore = new HashSet<>();
        Set<Long> selectedAfter = new HashSet<>();

        List<AnswerVote> answerVotes = answerVoteRepository.findByUserId(user.getUserId());

        for (AnswerVote vote : answerVotes)
            selectedBefore.add(vote.getAnswerId());

        for (Long answerId : selectedAnswers)
            selectedAfter.add(answerId);

        for (Long answerId : selectedBefore)
        {
            if (!selectedAfter.contains(answerId))
            {
                answerVoteRepository.deleteByUserIdAndAnswerId(user.getUserId(), answerId);
            }
        }

        for (Long answerId : selectedAfter)
        {
            if (!selectedBefore.contains(answerId))
            {
                AnswerVote vote = new AnswerVote();
                vote.setAnswerId(answerId);
                vote.setTrip(trip);
                vote.setUserId(user.getUserId());
                vote.setQuestion(question);
                answerVoteRepository.save(vote);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    private void selectAnswer(User user, Trip trip, Question question, Long answerId, Boolean selected)
    {
        Boolean alreadySelected = answerVoteRepository.findByUserIdAndAnswerId(user.getUserId(), answerId) != null;

        if (selected ^ alreadySelected)
        {
            if (selected)
            {
                AnswerVote vote = new AnswerVote();
                vote.setTrip(trip);
                vote.setQuestion(question);
                vote.setUserId(user.getUserId());
                vote.setAnswerId(answerId);
                answerVoteRepository.save(vote);
            }
            else
            {
                answerVoteRepository.deleteByUserIdAndAnswerId(user.getUserId(), answerId);
            }
        }
    }
}
