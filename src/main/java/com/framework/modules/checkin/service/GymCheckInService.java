/*
 * 
 * Created by Gabriel Henrique
 */
package com.framework.modules.checkin.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import com.framework.framework.gamification.dto.request.GamificationEventRequestDTO;
import com.framework.framework.gamification.event.GamificationEvent;
import com.framework.modules.billing.validation.SubscriptionValidation;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.modules.checkin.dto.request.FilterGymCheckInRequestDTO;
import com.framework.modules.checkin.dto.request.GymCheckInAndCheckOutRequestDTO;
import com.framework.modules.checkin.dto.response.GymCheckInResponseDTO;
import com.framework.modules.checkin.entity.GymCheckIn;
import com.framework.modules.checkin.mapper.GymCheckInMapper;
import com.framework.modules.checkin.repository.GymCheckInRepository;
import com.framework.modules.checkin.util.SensitiveCheckInDataDecryptor;
import com.framework.modules.checkin.validation.GymCheckInValidation;
import com.framework.modules.useraccess.entity.User;
import com.framework.modules.useraccess.validation.UserValidation;

@Service
public class GymCheckInService {
    private final GymCheckInRepository gymCheckInRepository;
    private final GymCheckInValidation gymCheckInValidation;
    private final UserValidation userValidation;
    private final SubscriptionValidation subscriptionValidation;
    private final SensitiveCheckInDataDecryptor sensitiveCheckInDataDecryptor;
    private final ApplicationEventPublisher publisher;

    private static final int STREAK_DAYS_RANGE = 7;

    public GymCheckInService(GymCheckInRepository gymCheckInRepository, GymCheckInValidation gymCheckInValidation,
            UserValidation userValidation, SubscriptionValidation subscriptionValidation,
            SensitiveCheckInDataDecryptor sensitiveCheckInDataDecryptor,
            ApplicationEventPublisher publisher) {
        this.gymCheckInRepository = gymCheckInRepository;
        this.gymCheckInValidation = gymCheckInValidation;
        this.userValidation = userValidation;
        this.subscriptionValidation = subscriptionValidation;
        this.sensitiveCheckInDataDecryptor = sensitiveCheckInDataDecryptor;
        this.publisher = publisher;
    }

    @Transactional
    public GymCheckInResponseDTO doCheckIn(GymCheckInAndCheckOutRequestDTO requestDTO) {
        UUID userId = requestDTO.getUserId();
        User user = userValidation.validateUserById(userId);

        gymCheckInValidation.validateIsCommercialTime();
        subscriptionValidation.validateHasCurrentSubscription(userId);
        gymCheckInValidation.validateOpenCheckInNotExists(userId);

        sendGymCheckInEvents(user);

        GymCheckIn gymCheckIn = GymCheckInMapper.toEntity(requestDTO, user);
        gymCheckIn = gymCheckInRepository.save(gymCheckIn);

        return sensitiveCheckInDataDecryptor.decrypt(
                GymCheckInMapper.toResponse(gymCheckIn));
    }

    @Transactional
    public GymCheckInResponseDTO doCheckOut(GymCheckInAndCheckOutRequestDTO requestDTO) {
        GymCheckIn gymCheckIn = gymCheckInValidation.validateOpenGymCheckInByUserId(requestDTO.getUserId());

        gymCheckIn.setCheckOutTime(LocalDateTime.now());
        gymCheckIn = gymCheckInRepository.save(gymCheckIn);

        return sensitiveCheckInDataDecryptor.decrypt(GymCheckInMapper.toResponse(gymCheckIn));
    }

    @Transactional
    public void doCheckOutInAll() {
        gymCheckInRepository.updateAllCheckOutTime(LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public Boolean hasOpenCheckInByUserId(UUID userId) {
        return gymCheckInRepository.existsByUserIdAndCheckOutTimeIsNull(userId);
    }

    @Transactional(readOnly = true)
    public List<GymCheckInResponseDTO> getAllByUserId(UUID userId) {
        List<GymCheckIn> gymCheckIns = gymCheckInRepository.findByUserId(userId);

        return gymCheckIns.stream()
                .map(gymCheckIn -> sensitiveCheckInDataDecryptor.decrypt(GymCheckInMapper.toResponse(gymCheckIn)))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<GymCheckInResponseDTO> filterByUserIdAndDate(FilterGymCheckInRequestDTO requestDTO) {
        List<GymCheckIn> gymCheckIns = gymCheckInRepository.findByUserIdAndDateBetween(
                requestDTO.getUserId(),
                requestDTO.getStartDate(),
                requestDTO.getEndDate());

        return gymCheckIns.stream()
                .map(gymCheckIn -> sensitiveCheckInDataDecryptor.decrypt(GymCheckInMapper.toResponse(gymCheckIn)))
                .toList();
    }

    private void sendGymCheckInEvents(User user) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        boolean isFirstCheckInToday = !gymCheckInRepository
                .existsByUserIdAndCheckInTimeBetween(user.getId(), startOfDay, endOfDay);

        Integer streak = 0;
        for (int i = 0; i < STREAK_DAYS_RANGE; i++) {
            startOfDay = startOfDay.minusDays(1);
            endOfDay = endOfDay.minusDays(1);
            boolean hasCheckIn = gymCheckInRepository
                    .existsByUserIdAndCheckInTimeBetween(user.getId(), startOfDay, endOfDay);
            if (hasCheckIn) {
                streak += 1;
            } else {
                break;
            }
        }

        GamificationEventRequestDTO dto = GamificationEventRequestDTO.builder()
                .eventType("check-in")
                .userId(user.getId())
                .details(Map.of("firstCheckInToday", isFirstCheckInToday, "streak", streak))
                .build();
        GamificationEvent event = new GamificationEvent(dto);
        publisher.publishEvent(event);
    }
}