package com.loginform.repository;

import java.util.Optional;

import com.loginform.model.RoleEntity;
import com.loginform.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	Optional<RoleEntity> findByName(RoleName roleName);
    
}
