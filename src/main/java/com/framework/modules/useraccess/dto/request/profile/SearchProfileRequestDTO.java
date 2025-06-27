package com.framework.modules.useraccess.dto.request.profile;

import java.time.LocalDate;
import java.util.List;

import com.framework.common.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SearchProfileRequestDTO {
   private String fullNameTerm;
   private String phoneTerm;
   private LocalDate birthDateFrom;
   private LocalDate birthDateTo;
   private List<Gender> gender;
}
