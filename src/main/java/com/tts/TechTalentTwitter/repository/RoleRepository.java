package com.tts.TechTalentTwitter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.TechTalentTwitter.model.Role;

//Repository that stores Roles (to use within Spring Security Framework), Long ID key
@Repository
public interface RoleRepository extends CrudRepository<Role, Long>
{
    Role findByRole(String role);
}
