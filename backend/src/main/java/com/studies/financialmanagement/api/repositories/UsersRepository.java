package com.studies.financialmanagement.api.repositories;

import com.studies.financialmanagement.api.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    public Optional<Users> findByEmail(String email);

}
