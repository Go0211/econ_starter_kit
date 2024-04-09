package com.econstarterkit.econstarterkit.repository;

import com.econstarterkit.econstarterkit.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
}
