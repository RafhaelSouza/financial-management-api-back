package com.studies.financialmanagement.api.repositories;

import com.studies.financialmanagement.api.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
