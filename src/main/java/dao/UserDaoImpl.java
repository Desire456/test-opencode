package dao;

import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public User findByLogin(String login) {
        String hqlQuery = "FROM User WHERE login = :login";
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = (User) session.createQuery(hqlQuery).setParameter("login", login).uniqueResult();
        transaction.commit();
        session.close();
        return user;
    }

    @Override
    public void update(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAll() {
        String hqlQuery = "FROM User";
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<User> users = (List<User>) session.createQuery(hqlQuery).list();
        transaction.commit();
        session.close();
        return users;
    }

    @Override
    public List<Object[]> getAllWithRating() {
        String hqlQuery = "SELECT login, attemptsCount, gamesCount FROM User";
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<Object[]> list = (List<Object[]>) session.createQuery(hqlQuery).list();
        transaction.commit();
        session.close();
        return list;
    }

}
