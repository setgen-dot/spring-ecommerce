package com.example.dto.response;

import java.io.Serializable;
import java.math.BigDecimal;

public record ProductResponse(Long id, String name, BigDecimal price) implements Serializable { }
