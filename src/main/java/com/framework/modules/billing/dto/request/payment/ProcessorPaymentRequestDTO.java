package com.framework.modules.billing.dto.request.payment;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProcessorPaymentRequestDTO {
   private Map<String, Object> data;
}
