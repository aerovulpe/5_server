package io.wolfbeacon.server.service;

import io.wolfbeacon.server.dao.MailingListDAO;
import io.wolfbeacon.server.model.Email;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Aaron on 22/05/2016.
 */
@Service
@Transactional
public class MailingListServiceImpl implements MailingListService {
    @Autowired
    private MailingListDAO mailingListDAO;
    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Override
    public void sendEmailToAll() {

    }

    @Override
    public void sendEmail(Long id) {

    }

    @Override
    public void addEmail(Email email) {
        if (emailValidator.isValid(email.getEmailAddress())) {
            mailingListDAO.create(email);
        }
    }

    @Override
    public void removeEmail(Long id) {
        mailingListDAO.delete(mailingListDAO.retrieve(id));
    }
}
