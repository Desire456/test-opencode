package services;

import dao.UserDao;
import dao.UserDaoImpl;
import models.User;
import utils.HibernateSessionFactoryUtil;
import utils.HashingClass;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private static UserServiceImpl instance;

    private UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public static synchronized UserServiceImpl getInstance() {
        if (instance == null) {
            UserDao userDao = new UserDaoImpl(HibernateSessionFactoryUtil.getSessionFactory());
            instance = new UserServiceImpl(userDao);
        }
        return instance;
    }

    @Override
    public void saveUser(User user) throws SameUserLoginException {
        if (findUserByLogin(user.getLogin()) != null) {
            final String CAUSE_SAME_LOGIN_USER_EXCEPTION_MESSAGE = String.format(
                    "User with login %s already exists", user.getLogin());
            throw new SameUserLoginException(CAUSE_SAME_LOGIN_USER_EXCEPTION_MESSAGE);
        } else
            userDao.save(user);
    }

    @Override
    public User findUserByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public void updateUser(User user) {
        this.userDao.update(user);
    }

    @Override
    public void addAttemptsCount(User user, int attemptsCount) {
        user.setAttemptsCount(user.getAttemptsCount() + attemptsCount);
        this.updateUser(user);
    }

    @Override
    public void addGamesCount(User user, int gamesCount) {
        user.setGamesCount(user.getGamesCount() + gamesCount);
        this.updateUser(user);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public Map<String, Integer> getAllUsersAndRatings() {
        List<Object[]> list = userDao.getAllWithRating();
        Map<String, Integer> map = new TreeMap<>();
        for (Object[] data : list) {
            int attemptsCount = (int)data[1];
            int gamesCount = (int)data[2];
            int rating = gamesCount == 0 ? 0 : attemptsCount / gamesCount;
            map.put((String) data[0], rating);
        }
        return map;
    }

    @Override
    public boolean checkLoginAndPassword(String login, String password) {
        User user = findUserByLogin(login);
        if (user != null) {
            HashingClass hashingClass = HashingClass.getInstance();
            return hashingClass.validatePassword(password, user.getPassword());
        }
        return false;
    }
}
