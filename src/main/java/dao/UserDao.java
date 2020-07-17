package dao;

import models.User;

import java.util.List;

public interface UserDao {
    /**
     * Save new user to db
     * @param user
     */
    void save(User user);

    /**
     * Find user by login
     * @param login
     * @return
     */
    User findByLogin(String login);

    /**
     * Update old user to new user
     * @param user new user
     */
    void update(User user);

    /**
     * Get all users
     * @return
     */
    List<User> getAll();

    /**
     * Get all logins, attempts count and games count
     * @return
     */
    List<Object[]> getAllWithRating();
}
