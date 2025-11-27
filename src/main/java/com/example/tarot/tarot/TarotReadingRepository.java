package com.example.tarot.tarot;

import com.example.tarot.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarotReadingRepository extends JpaRepository<TarotReading, Long> {

    List<TarotReading> findByUserOrderByCreatedAtDesc(User user);
}
