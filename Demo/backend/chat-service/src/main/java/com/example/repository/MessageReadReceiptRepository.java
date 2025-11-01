package com.example.repository;

import com.example.entities.MessageReadReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageReadReceiptRepository extends JpaRepository<MessageReadReceipt, Long> {
    List<MessageReadReceipt> findByMessageId(Long messageId);
}
