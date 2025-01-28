package com.springJourney.Week5Practice.Repositories;

import com.springJourney.Week5Practice.Entities.SessionManagement;
import com.springJourney.Week5Practice.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionManagementRepository extends JpaRepository<SessionManagement, Long> {
    List<SessionManagement> findByUserEntity(UserEntity userEntity);

    Optional<SessionManagement> findByRefreshToken(String refreshToken);
}
