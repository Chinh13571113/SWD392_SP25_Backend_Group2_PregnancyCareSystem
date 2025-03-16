package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.DataMailDTO;
import jakarta.mail.MessagingException;

public interface MailServices {
    void sendHtmlMail(DataMailDTO dataMailDTO,String templateName) throws MessagingException;

}
