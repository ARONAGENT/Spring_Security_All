package com.springJourney.Week5Practice.Services;

import com.springJourney.Week5Practice.Entities.SessionManagement;
import com.springJourney.Week5Practice.Entities.UserEntity;
import com.springJourney.Week5Practice.Repositories.SessionManagementRepository;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;
import java.util.Comparator;

@Service
@AllArgsConstructor
public class SessionManagementServices {

    private final SessionManagementRepository sessionRepository;

    private final int Session_Limit=1;

    public void generateSession(UserEntity userEntity, String refreshToken){
        List<SessionManagement> userSessions = sessionRepository.findByUserEntity(userEntity);

        if (userSessions.size() == Session_Limit) {
            userSessions.sort(Comparator.comparing(SessionManagement::getLastUsedAt));
            SessionManagement leastRecentlyUsedSession = userSessions.get(0);
            sessionRepository.delete(leastRecentlyUsedSession);
        }
        SessionManagement newSession= SessionManagement.builder()
                .userEntity(userEntity)
                .refreshToken(refreshToken)
                .build();
        sessionRepository.save(newSession);
    }

    public void validateSession(String refreshToken){
        SessionManagement session=sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()->
                        new SessionAuthenticationException
                                ("Session not found with refreshToken :"+refreshToken));
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }

    public void deleteSession(String refreshToken) {
        SessionManagement session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() ->
                        new SessionAuthenticationException("Session not found with refreshToken: " + refreshToken)
                );
        sessionRepository.delete(session);
    }

}
