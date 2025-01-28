package com.springJourney.Week5Practice.Repositories;

import com.springJourney.Week5Practice.Entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Integer> {
}
