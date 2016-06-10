package io.wolfbeacon.server.dao;

import io.wolfbeacon.server.model.User;
import org.springframework.stereotype.Component;

/**
 * Created by Aaron on 10/06/2016.
 */
@Component
public class UserDAOImpl extends AbstractHibernateDAO<User, Long> implements UserDAO{
}
