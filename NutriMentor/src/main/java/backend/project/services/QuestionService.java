package backend.project.services;

import backend.project.entities.Question;

import java.util.List;

public interface QuestionService {

    List<Question> listAll();

    Question insertQuestion(Question question);

    Question updateQuestion(Question question);

    void deleteQuestion(Long id);

    Question findById(Long id);
}
