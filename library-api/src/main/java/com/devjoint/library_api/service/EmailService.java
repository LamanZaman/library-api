package com.devjoint.library_api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Async
    public void sendLoanConfirmationEmail(String toEmail, String bookTitle) {
        log.info("Email göndərmə başladı (thread: {}): {}", Thread.currentThread().getName(), toEmail);

        try {
            Thread.sleep(3000); // real email göndərməni simulyasiya edir (gecikmə)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("Email göndərildi: {} -> '{}' kitabı üçün borc təsdiqi", toEmail, bookTitle);
    }
}