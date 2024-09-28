package backend.project.services;

import backend.project.entities.Authority;

public interface AuthorityService {
    Authority insertAuthority(Authority authority);
    public Authority findByName(String name);
    Authority findById(Long id);

}
