package servlets;

import models.User;
import services.UserService;
import services.UserServiceImpl;
import utils.FourDigitGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/main")
public class MainPageServlet extends HttpServlet {

    /**
     * Set request attribute ratings : map of logins and ratings; session attribute : generated four-digit number to play.
     * Show "view/main.jsp" page
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = UserServiceImpl.getInstance();
        req.setAttribute("ratings", userService.getAllUsersAndRatings());
        req.getSession().setAttribute("problem", FourDigitGenerator.generate());
        req.getRequestDispatcher("view/main.jsp").forward(req, resp);
    }

    /**
     * User authorization and set session attribute : current user, generated number; request attribute : ratings
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserService userService = UserServiceImpl.getInstance();
        boolean correctLoginAndPassword = userService.checkLoginAndPassword(login, password);
        if (!correctLoginAndPassword) {
            req.setAttribute("error", "Login or password incorrect");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } else {
            User user = userService.findUserByLogin(login);
            req.getSession().setAttribute("currentUser", user);
            req.getSession().setAttribute("problem", FourDigitGenerator.generate());
            req.setAttribute("ratings", userService.getAllUsersAndRatings());
            req.getRequestDispatcher("view/main.jsp").forward(req, resp);
        }
    }
}
