package com.framework.framework.billing.handler.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.framework.framework.billing.handler.PaymentHandler;
import com.framework.modules.billing.dto.request.payment.ProcessorPaymentRequestDTO;
import com.framework.modules.billing.dto.response.payment.PaymentProcessorResponseDTO;
import com.framework.modules.traininggroup.entity.TrainingGroupUser;
import com.framework.modules.traininggroup.repository.TrainingGroupUserRepository;

import jakarta.transaction.Transactional;

@Component
public class GroupPointsPaymentHandler implements PaymentHandler {
    private final TrainingGroupUserRepository trainingGroupUserRepository;

    public GroupPointsPaymentHandler(TrainingGroupUserRepository trainingGroupUserRepository) {
        this.trainingGroupUserRepository = trainingGroupUserRepository;
    }

    @Override
    @Transactional
    public PaymentProcessorResponseDTO processPayment(ProcessorPaymentRequestDTO processorDTO) {
        Map<String, Object> data = processorDTO.getData();
        if (!data.containsKey("userId") ||
                data.get("userId") == null ||
                !(data.get("userId") instanceof UUID)) {
            return new PaymentProcessorResponseDTO("Invalid User Id.", false);
        } else if (!data.containsKey("amount")
                || data.get("amount") == null
                || !(data.get("amount") instanceof Number)) {
            return new PaymentProcessorResponseDTO("Invalid Amount.", false);
        }

        UUID userId = (UUID) data.get("userId");
        Double amount = ((Number) data.get("amount")).doubleValue();
        int amountWithDiscount = (int) Math.round(amount * 0.9);

        List<TrainingGroupUser> trainingGroupUsers = trainingGroupUserRepository.findByUserId(userId);
        int remaining = amountWithDiscount;

        for (TrainingGroupUser trainingGroupUser : trainingGroupUsers) {
            int usablePoints = Math.min(remaining, trainingGroupUser.getPoints());
            trainingGroupUser.setPoints(trainingGroupUser.getPoints() - usablePoints);
            remaining -= usablePoints;

            if (remaining == 0) {
                break;
            }
        }

        if (remaining != 0) {
            return new PaymentProcessorResponseDTO("Not enough points.", false);
        }

        trainingGroupUserRepository.saveAll(trainingGroupUsers);

        return new PaymentProcessorResponseDTO("Payment Processed.", true);
    }

    @Override
    public String getPaymentMethodId() {
        return "GROUP_POINTS";
    }

    @Override
    public String getPaymentMethodName() {
        return "Pontos de Grupo";
    }
}
