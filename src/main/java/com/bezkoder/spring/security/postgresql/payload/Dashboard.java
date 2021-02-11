package com.bezkoder.spring.security.postgresql.payload;

import com.bezkoder.spring.security.postgresql.models.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dashboard {
    private Integer orderId;
    private Integer clientId;
    private String fileApi;
    private String name;
    private String phone;
    private String address;
    private String comment;

    @Enumerated(EnumType.STRING)
    private Status status;
}
