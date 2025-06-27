package com.framework.modules.checkin.util;

import org.springframework.stereotype.Component;

import com.framework.common.util.CryptoUtil;
import com.framework.common.util.SensitiveDataDecryptor;
import com.framework.modules.checkin.dto.response.ClassCheckInResponseDTO;
import com.framework.modules.checkin.dto.response.GymCheckInResponseDTO;

@Component
public class SensitiveCheckInDataDecryptor extends SensitiveDataDecryptor {
   public SensitiveCheckInDataDecryptor(CryptoUtil cryptoUtil) {
      super(cryptoUtil);
   }

   public GymCheckInResponseDTO decrypt(GymCheckInResponseDTO gymCheckIn) {
      if (gymCheckIn != null && gymCheckIn.getUser() != null) {
         decrypt(gymCheckIn.getUser());
      }
      return gymCheckIn;
   }

   public ClassCheckInResponseDTO decrypt(ClassCheckInResponseDTO classCheckIn) {
      if (classCheckIn != null && classCheckIn.getUser() != null) {
         decrypt(classCheckIn.getUser());
      }
      return classCheckIn;
   }
}
