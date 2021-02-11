package com.bezkoder.spring.security.postgresql.utils;

import com.bezkoder.spring.security.postgresql.models.Status;
import com.bezkoder.spring.security.postgresql.payload.StatusPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UpdateStatus {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void updateTaskState(StatusPayload status) {
        if (status.getStatus().name()=="received"){
            String sql = "update sticker set status = ? where id = ?";
            jdbcTemplate.update(sql,status.getStatus().name(),status.getId());
        }

        if (status.getStatus().name()=="sent"){
            String sql = "update sticker set status = ? where id = ?";
            jdbcTemplate.update(sql,status.getStatus().name(),status.getId());
        }

        if (status.getStatus().name()=="done"){
            String sql = "update sticker set status = ? where id = ?";
            jdbcTemplate.update(sql,status.getStatus().name(),status.getId());
        }
    }

}


