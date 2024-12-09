package com.pf.mas.mapper;

import com.pf.mas.dto.ProfileDetailResponseData;
import com.pf.mas.model.entity.sheet.ProfileDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileDetailResponseMapper {
    ProfileDetailResponseData convertToProfileDetailResponse(ProfileDetail profileDetail);
}
