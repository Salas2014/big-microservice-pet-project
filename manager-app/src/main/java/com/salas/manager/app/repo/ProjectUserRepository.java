package com.salas.manager.app.repo;

import com.salas.manager.app.entity.ProjectUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectUserRepository extends CrudRepository<ProjectUser, Integer> {

    Optional<ProjectUser> findByUsername(String username);
}
