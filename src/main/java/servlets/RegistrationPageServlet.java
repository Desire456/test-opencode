package servlets;

import models.User;
import services.UserService;
import services.UserServiceImpl;
import services.SameUserLoginException;
import utils.HashingClass;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationPageServlet extends HttpServlet {
    /**
     * Show registration page
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("view/registration.jsp").forward(req, resp);
    }

    /**
     * User registration, set session attribute: current user; redirect to main page
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
        try {
            HashingClass hashingClass = HashingClass.getInstance();
            String hashPassword = hashingClass.hashPassword(password);
            userService.saveUser(new User(login, hashPassword));
        } catch (SameUserLoginException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("view/registration.jsp").forward(req, resp);
            return;
        }
        User user = userService.findUserByLogin(login);
        if (user != null) {
            req.getSession().setAttribute("currentUser", user);
            resp.sendRedirect("/main");
        }
    }
}
