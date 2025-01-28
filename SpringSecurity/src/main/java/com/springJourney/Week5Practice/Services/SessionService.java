//package com.springJourney.Week5Practice.Services;
// // homework week 5 3rd problem .....
//import com.springJourney.Week5Practice.Entities.SessionEntity;
//import com.springJourney.Week5Practice.Repositories.SessionRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//@Service
//@RequiredArgsConstructor
//public class SessionService {
//    private final SessionRepository sessionRepository;
//
//    public void createSession(Long userId, String token) {
//        // Remove any existing session for this user
//        sessionRepository.deleteByUserId(userId);
//
//        // Save the new session
//        SessionEntity session = new SessionEntity();
//        session.setUserId(userId);
//        session.setToken(token);
//        session.setCreatedAt(LocalDateTime.now());
//
//        sessionRepository.save(session);
//    }
//
//    public void removeSession(String token) {
//        sessionRepository.findByToken(token).ifPresent(sessionRepository::delete);
//    }
//}
//
