package servlets;

import models.User;
import services.UserService;
import services.UserServiceImpl;
import utils.BullsAndCowsCounter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@WebServlet("/play")
public class PlayGameServlet extends HttpServlet {

    /**
     * Get number from user and init game, if user win update user's rating
     *
     * @param req
     * @param resp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String answer = req.getParameter("answer");
        String problem = (String) req.getSession().getAttribute("problem");
        Map.Entry<Integer, Integer> entry = BullsAndCowsCounter.count(problem, answer);
        int bulls = entry.getKey();
        int cows = entry.getValue();
        resp.addIntHeader("bulls", bulls);
        resp.addIntHeader("cows", cows);
        if (bulls == 4) {
            User user = (User) req.getSession().getAttribute("currentUser");
            int attemptsCount = Integer.parseInt(req.getParameter("attemptsCount"));
            UserService userService = UserServiceImpl.getInstance();
            userService.addAttemptsCount(user, attemptsCount);
            userService.addGamesCount(user, 1);
        }

    }
}
