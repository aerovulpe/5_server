package io.wolfbeacon.server.service;

import io.wolfbeacon.server.model.Email;

/**
 * Created by Aaron on 22/05/2016.
 */
public interface MailingListService {
    void sendEmailToAll();
    void sendEmail(Long id);
    void addEmail(Email email);
    void removeEmail(Long id);
}
