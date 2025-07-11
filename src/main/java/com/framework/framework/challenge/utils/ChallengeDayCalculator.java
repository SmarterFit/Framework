package com.framework.framework.challenge.utils;

import java.time.DayOfWeek;
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
     * @param daysOfWeek  Dias de treino na semana (ex: segunda, sábado, etc.).
     * @return Lista de LocalDate com os dias de treino planejados.
     */
    public List<LocalDate> calculateChallengeDays(LocalDate startDate, LocalDate endDate, List<DayOfWeek> daysOfWeek) {
        List<LocalDate> challengeDays = new ArrayList<>();

        // Aplica limite de duração
        LocalDate limitedEndDate = limitEndDate(startDate, endDate);

        LocalDate current = startDate;
        while (!current.isAfter(limitedEndDate)) {
            // Adiciona os dias de treino da semana
            if(daysOfWeek.contains(current.getDayOfWeek())) {
                challengeDays.add(current);
            }

            // Pula os dias restantes da semana
            current = current.plusDays(1);
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
