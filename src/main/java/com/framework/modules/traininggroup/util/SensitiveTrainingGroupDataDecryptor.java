package com.framework.modules.traininggroup.util;

import org.springframework.stereotype.Component;

import com.framework.common.util.CryptoUtil;
import com.framework.common.util.SensitiveDataDecryptor;
import com.framework.modules.traininggroup.dto.response.TrainingGroupUserResponseDTO;

@Component
public class SensitiveTrainingGroupDataDecryptor extends SensitiveDataDecryptor {
   public SensitiveTrainingGroupDataDecryptor(CryptoUtil cryptoUtil) {
      super(cryptoUtil);
   }

   public TrainingGroupUserResponseDTO decrypt(TrainingGroupUserResponseDTO trainingGroupUser) {
      if (trainingGroupUser != null && trainingGroupUser.getUser() != null) {
         decrypt(trainingGroupUser.getUser());
      }
      return trainingGroupUser;
   }
}
