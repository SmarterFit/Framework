package com.framework.framework.challenge.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ChallengeDayCalculator {

    private static final int MAX_DAYS = 30;

    /**
     * Calcula os dias de treino da trilha, respeitando o limite de 30 dias e a frequência semanal.
     *
     * @param startDate       Data de início do desafio.
     * @param endDate         Data de fim do desafio.
     * @param weeklyFrequency Dias de treino por semana (ex: 3, 5, etc.).
     * @return Lista de LocalDate com os dias de treino planejados.
     */
    public List<LocalDate> calculateChallengeDays(LocalDate startDate, LocalDate endDate, int weeklyFrequency) {
        List<LocalDate> challengeDays = new ArrayList<>();

        // Aplica limite de duração
        LocalDate limitedEndDate = limitEndDate(startDate, endDate);

        LocalDate current = startDate;
        while (!current.isAfter(limitedEndDate)) {
            // Adiciona os dias de treino da semana
            for (int i = 0; i < weeklyFrequency && !current.isAfter(limitedEndDate); i++) {
                challengeDays.add(current);
                current = current.plusDays(1);
            }

            // Pula os dias restantes da semana
            current = current.plusDays(7 - weeklyFrequency);
        }

        return challengeDays;
    }

    /**
     * Garante que o período entre startDate e endDate não exceda o máximo permitido.
     */
    private LocalDate limitEndDate(LocalDate startDate, LocalDate endDate) {
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        if (daysBetween >= MAX_DAYS) {
            return startDate.plusDays(MAX_DAYS - 1);
        }
        return endDate;
    }
}
