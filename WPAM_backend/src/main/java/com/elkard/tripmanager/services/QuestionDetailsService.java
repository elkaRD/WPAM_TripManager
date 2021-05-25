package com.elkard.tripmanager.services;

import com.elkard.tripmanager.model.dao.AnswerRepository;
import com.elkard.tripmanager.model.dao.AnswerVoteRepository;
import com.elkard.tripmanager.model.dao.QuestionRepository;
import com.elkard.tripmanager.model.entities.*;
import com.elkard.tripmanager.dto.AnswerDetails;
import com.elkard.tripmanager.dto.QuestionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuestionDetailsService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private AnswerVoteRepository answerVoteRepository;

    public List<QuestionDetails> getQuestions(Trip trip, User user)
    {
        List<Question> questions = questionRepository.findByTripTripId(trip.getTripId());

        List<QuestionDetails> result = new ArrayList<>();

        for (Question question : questions)
        {
            QuestionDetails details = new QuestionDetails();
            details.setQuestionId(question.getQuestionId());
            details.setQuestion(question.getQuestion());
            details.setAnswers(getAnswers(question, user));

            List<AnswerVote> allVotes = answerVoteRepository.findByQuestionQuestionId(question.getQuestionId());
            Set<Long> uniqueVotes = new HashSet<>();
            for (AnswerVote vote : allVotes)
                uniqueVotes.add(vote.getUserId());
            details.setAllVotes(Long.valueOf(uniqueVotes.size()));

            result.add(details);
        }

        return result;
    }

    private List<AnswerDetails> getAnswers(Question question, User user)
    {
        List<Answer> answers = answerRepository.findByQuestionQuestionId(question.getQuestionId());
        List<AnswerVote> selected = answerVoteRepository.findByUserId(user.getUserId());
        Set<Long> selectedIds = new HashSet<>();
        for (AnswerVote vote : selected)
            selectedIds.add(vote.getAnswerId());

        List<AnswerDetails> result = new ArrayList<>();

        for (Answer answer : answers)
        {
            AnswerDetails details = new AnswerDetails();
            details.setAnswerId(answer.getAnswerId());
            details.setAnswer(answer.getAnswer());
            details.setVotes((int) answerVoteRepository.countByAnswerId(answer.getAnswerId()));
            details.setSelected(selectedIds.contains(answer.getAnswerId()));
            result.add(details);
        }

        return result;
    }


}
