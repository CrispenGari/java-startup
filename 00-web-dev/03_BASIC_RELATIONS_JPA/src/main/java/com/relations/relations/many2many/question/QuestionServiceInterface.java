package com.relations.relations.many2many.question;
import java.util.Collection;
public interface QuestionServiceInterface {
    Question createQuestion(Question question);
    Question getQuestion(Long id);
    Collection<Question> getQuestions();
}
