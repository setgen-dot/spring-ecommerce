package com.example.util;

import com.example.dto.response.PaymentResponse;
import com.example.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentResponse toResponse(Payment payment);
}
