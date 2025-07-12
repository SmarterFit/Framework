package com.framework.framework.challenge.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SF_CHALLENGE_TYPE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class ChallengeType {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @Column(name = "name", nullable = false, unique = true)
   private String name;

   @Column(name = "enabled", nullable = false)
   @Builder.Default
   private boolean enabled = true;

   @Column(name = "handler_class", nullable = false)
   private String handlerClass;

   @Column(name = "dt_created_at", nullable = false, updatable = false)
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime createdAt;

   @PrePersist
   public void onPrePersist() {
      this.createdAt = LocalDateTime.now();
   }
}
