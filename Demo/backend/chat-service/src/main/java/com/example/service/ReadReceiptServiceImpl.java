package com.example.service;

import com.example.dto.ReadReceiptDto;
import com.example.entities.MessageReadReceipt;
import com.example.repository.MessageReadReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ReadReceiptServiceImpl implements ReadReceiptService {

    @Autowired
    private MessageReadReceiptRepository readReceiptRepository;

    @Override
    public void markMessageAsRead(ReadReceiptDto readReceiptDto) {
        MessageReadReceipt receipt = new MessageReadReceipt();
        receipt.setMessageId(readReceiptDto.getMessageId());
        receipt.setUserId(readReceiptDto.getUserId());
        receipt.setReadAt(new Timestamp(System.currentTimeMillis()));
        readReceiptRepository.save(receipt);
    }
}
