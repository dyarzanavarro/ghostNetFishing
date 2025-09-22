package ghostnetfishing.demo.web.dto;

import java.time.LocalDateTime;

public record NetMapDto(
    Long id,
    double latitude,
    double longitude,
    String sizeEstimate,
    String status,
    String reportedByName,
    String rescuerName,
    LocalDateTime createdAt
) {}