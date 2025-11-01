package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean isContact(Long userId, Long contactId) {
        String sql = "SELECT count(*) FROM contacts WHERE user_id = ? AND contact_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{userId, contactId}, Integer.class);
        return count != null && count > 0;
    }
}
