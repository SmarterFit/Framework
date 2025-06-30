//package com.framework.modules.useraccess.service;
//
//import java.util.List;
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.framework.common.enums.ProfileMetricType;
//import com.framework.modules.useraccess.dto.request.profilemetric.CreateProfileMetricRequestDTO;
//import com.framework.modules.useraccess.dto.response.ProfileMetricResponseDTO;
//import com.framework.modules.useraccess.entity.ProfileMetric;
//import com.framework.modules.useraccess.entity.Profile;
//import com.framework.modules.useraccess.mapper.ProfileMetricMapper;
//import com.framework.modules.useraccess.repository.ProfileMetricRepository;
//import com.framework.modules.useraccess.validation.ProfileValidation;
//
//@Service
//public class ProfileMetricService {
//   private final ProfileMetricRepository profileMetricRepository;
//   private final ProfileValidation profileValidation;
//
//   @Autowired
//   public ProfileMetricService(ProfileMetricRepository profileMetricRepository, ProfileValidation profileValidation) {
//      this.profileMetricRepository = profileMetricRepository;
//      this.profileValidation = profileValidation;
//   }
//
//   @Transactional
//   public ProfileMetricResponseDTO createProfileMetric(UUID profileId, CreateProfileMetricRequestDTO requestDTO) {
//      Profile profile = profileValidation.validateProfileById(profileId);
//
//      ProfileMetric profileMetric = ProfileMetricMapper.toEntity(requestDTO, profile);
//      profileMetricRepository.save(profileMetric);
//
//      return ProfileMetricMapper.toResponse(profileMetric);
//   }
//
//   @Transactional(readOnly = true)
//   public List<ProfileMetricResponseDTO> getLastsProfileMetricByProfileId(UUID profileId) {
//      List<ProfileMetric> profileMetrics = profileMetricRepository.findLastsProfileMetricsByProfileId(profileId);
//
//      return profileMetrics.stream().map(ProfileMetricMapper::toResponse).toList();
//   }
//
//   @Transactional(readOnly = true)
//   public List<ProfileMetricResponseDTO> getProfileMetricsByProfileId(UUID profileId) {
//      List<ProfileMetric> profileMetrics = profileMetricRepository.findByProfileIdOrderByCreatedAtDesc(profileId);
//
//      return profileMetrics.stream().map(ProfileMetricMapper::toResponse).toList();
//   }
//
//   @Transactional(readOnly = true)
//   public List<ProfileMetricResponseDTO> getProfileMetricsByProfileIdAndType(UUID profileId,
//         ProfileMetricType profileMetricType) {
//      List<ProfileMetric> profileMetrics = profileMetricRepository
//            .findByProfileIdAndTypeOrderByCreatedAtDesc(profileId, profileMetricType);
//
//      return profileMetrics.stream().map(ProfileMetricMapper::toResponse).toList();
//   }
//}
