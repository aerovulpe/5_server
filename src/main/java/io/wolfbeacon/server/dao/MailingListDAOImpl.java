package io.wolfbeacon.server.dao;

import io.wolfbeacon.server.model.Email;
import org.springframework.stereotype.Component;

/**
 * Created by Aaron on 22/05/2016.
 */
@Component
public class MailingListDAOImpl extends AbstractHibernateDAO<Email, Long> implements MailingListDAO {
}
