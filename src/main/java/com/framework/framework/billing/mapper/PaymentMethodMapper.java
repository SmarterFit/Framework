package com.framework.framework.billing.mapper;

import com.framework.common.mapper.GenericMapper;
import com.framework.framework.billing.dto.response.PaymentMethodResponseDTO;
import com.framework.framework.billing.entity.PaymentMethod;

public class PaymentMethodMapper {
   private PaymentMethodMapper() {
      // Private constructor to prevent instantiation
   }

   public static PaymentMethodResponseDTO toResponse(PaymentMethod paymentMethod) {
      if (paymentMethod == null) {
         return null;
      }

      return GenericMapper.map(paymentMethod, PaymentMethodResponseDTO.class);
   }
}
