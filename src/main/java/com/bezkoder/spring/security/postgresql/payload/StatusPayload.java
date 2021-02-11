package com.bezkoder.spring.security.postgresql.payload;

import com.bezkoder.spring.security.postgresql.models.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusPayload {
    private Status status;
    private Integer id;
}
