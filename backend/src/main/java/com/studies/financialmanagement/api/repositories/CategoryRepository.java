package com.studies.financialmanagement.api.repositories;

import com.studies.financialmanagement.api.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
