package com.framework.modules.checkin.mapper;

import com.framework.common.exceptions.ResourceNotFoundException;
import com.framework.common.mapper.GenericMapper;
import com.framework.modules.checkin.dto.response.PresenceSnapshotResponseDTO;
import com.framework.modules.checkin.entity.PresenceSnapshot;

public class PresenceSnapshotMapper {
   private PresenceSnapshotMapper() {
      // Private constructor to prevent instantiation
   }

   public static PresenceSnapshotResponseDTO toResponse(PresenceSnapshot presenceSnapshot) {
      if (presenceSnapshot == null) {
         throw new ResourceNotFoundException("PresenceSnapshot cannot be null");
      }

      return GenericMapper.map(presenceSnapshot, PresenceSnapshotResponseDTO.class);
   }
}
