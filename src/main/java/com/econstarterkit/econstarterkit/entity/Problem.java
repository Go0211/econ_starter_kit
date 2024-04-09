package com.econstarterkit.econstarterkit.entity;

import com.econstarterkit.econstarterkit.type.Difficulty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "problem")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "description")
    String description;

    @Column(name = "correct_word")
    String correctWord;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    Difficulty difficulty;
}
