package com.relations.relations.many2many.question;

import com.relations.relations.many2many.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestionService implements QuestionServiceInterface{
    private final QuestionRepository questionRepository;


    @Override
    public Question createQuestion(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    public Question getQuestion(Long id) {
        return this.questionRepository.findById(id).orElseThrow(()-> new IllegalStateException("no question of that id"));
    }

    @Override
    public Collection<Question> getQuestions() {
        return this.questionRepository.findAll();
    }
}
