package com.example.service;

import com.example.dto.ReadReceiptDto;

public interface ReadReceiptService {
    void markMessageAsRead(ReadReceiptDto readReceiptDto);
}
