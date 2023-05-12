package com.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BowlingGameRepo extends JpaRepository<Roll, Integer> {
	
	Roll getByFrame(int frame);
	@Query("from Roll order by id")
	List<Roll> findAllByOrderById();	
}
