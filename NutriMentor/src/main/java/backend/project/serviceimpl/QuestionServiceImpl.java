package backend.project.serviceimpl;

import backend.project.entities.Question;
import backend.project.exceptions.ResourceNotFoundException;
import backend.project.repositories.ClientRepository;
import backend.project.repositories.HealthProfessionalRepository;
import backend.project.repositories.QuestionRepository;
import backend.project.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private HealthProfessionalRepository healthProfessionalRepository;

    @Override
    public Question updateQuestion(Question question) {
        Question questionFound = findById(question.getId());
        if (questionFound == null) {
            throw new ResourceNotFoundException("Question with id: " + question.getId() + " cannot be found");
        }
        questionFound.setQuery(question.getQuery());
        questionFound.setResponse(question.getResponse());
        return questionRepository.save(questionFound);
    }

    @Override
    public Question insertQuestion(Question question) {
        if (!clientRepository.existsById(question.getClient().getId())) {
            throw new ResourceNotFoundException("Client with id: " + question.getClient().getId() + " cannot be found");
        }
        if (!healthProfessionalRepository.existsById(question.getHealthProfessional().getId())) {
            throw new ResourceNotFoundException("HealthProfessional with id: " + question.getHealthProfessional().getId() + " cannot be found");
        }
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(Long id) {
        Question questionFound = findById(id);
        if (questionFound == null) {
            throw new ResourceNotFoundException("Question with id: " + id + " cannot be found");
        }
        questionRepository.delete(questionFound);
    }

    @Override
    public Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question with id: " + id + " cannot be found"));
    }

    @Override
    public List<Question> listAll() {
        return questionRepository.findAll();
    }
}

