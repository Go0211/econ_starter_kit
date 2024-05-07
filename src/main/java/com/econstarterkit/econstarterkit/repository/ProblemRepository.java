package com.econstarterkit.econstarterkit.repository;

import com.econstarterkit.econstarterkit.entity.Problem;
import com.econstarterkit.econstarterkit.type.Difficulty;
import com.econstarterkit.econstarterkit.type.ProblemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    Optional<Problem> findById(Long id);

    List<Problem> findAll();

    List<Problem> findAllByDifficultyAndType(Difficulty difficulty, ProblemType type);
}
