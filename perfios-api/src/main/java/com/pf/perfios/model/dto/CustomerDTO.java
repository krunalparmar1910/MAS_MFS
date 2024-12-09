package com.pf.perfios.model.dto;

import com.pf.perfios.model.entity.CustomerInfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDTO {

    private String uuid;

    private String name;

    private String address;

    private String email;

    private String pan;

    private String mobileNumber;

    private String landline;

    public static CustomerDTO from(CustomerInfo customerInfo) {
        return CustomerDTO.builder()
                .uuid(customerInfo.getUuid().toString())
                .name(customerInfo.getName())
                .address(customerInfo.getAddress())
                .email(customerInfo.getEmail())
                .pan(customerInfo.getPan())
                .mobileNumber(customerInfo.getMobileNumber())
                .landline(customerInfo.getLandline())
                .build();
    }
}
