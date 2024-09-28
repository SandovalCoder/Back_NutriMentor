package backend.project.serviceimpl;

import backend.project.entities.Authority;
import backend.project.exceptions.KeyRepeatedDataException;
import backend.project.exceptions.ResourceNotFoundException;
import backend.project.repositories.AuthorityRepository;
import backend.project.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Authority findByName(String name) {
        Authority authorityFound = authorityRepository.findByName(name);
        if (authorityFound == null) {
            throw new ResourceNotFoundException("Authority with name: " + name + " cannot be found");
        }
        return authorityFound;
    }

    @Override
    public Authority insertAuthority(Authority authority) {
        // Aquí simplemente insertamos la autoridad sin verificar si ya existe.
        return authorityRepository.save(authority);
    }

    @Override
    public Authority findById(Long id) {
        return authorityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Authority not found with id: " + id));
    }
}
