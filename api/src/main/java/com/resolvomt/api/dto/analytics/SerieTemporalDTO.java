package com.resolvomt.api.dto.analytics;

import java.math.BigDecimal;

public record SerieTemporalDTO(
        String periodo,
        BigDecimal valor
) {}
