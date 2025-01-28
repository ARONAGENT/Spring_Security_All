package com.springJourney.Week5Practice.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="sessions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;

    @CreationTimestamp
    private LocalDateTime lastUsedAt;

    @ManyToOne
    @JoinColumn(name = "user_entity_id") // Specifies the join column name
    private UserEntity userEntity;
}
