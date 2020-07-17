package services;

import models.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * Save new user to db
     * @param user
     * @throws SameUserLoginException
     */
    void saveUser(User user) throws SameUserLoginException;

    /**
     * Find user by login
     * @param login
     * @return
     */
    User findUserByLogin(String login);

    /**
     * Update old user to new user
     * @param user
     */
    void updateUser(User user);

    /**
     * Add attempts count and update user
     * @param user
     * @param attemptsCount
     */
    void addAttemptsCount(User user, int attemptsCount);

    /**
     * Add games count and update user
     * @param user
     * @param gamesCount
     */
    void addGamesCount(User user, int gamesCount);

    /**
     * Get all users
     * @return
     */
    List<User> getAll();

    /**
     * Get map: key - user's login, value - user's rating
     * @return
     */
    Map<String, Integer> getAllUsersAndRatings();

    /**
     * Check for correctness login and password
     * @param login
     * @param password
     * @return
     */
    boolean checkLoginAndPassword(String login, String password);
}
