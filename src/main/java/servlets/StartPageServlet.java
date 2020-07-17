package servlets;

import utils.exceptions.ExecuteSqlScriptException;
import utils.StartScriptExecutor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/authorization")
public class StartPageServlet extends HttpServlet {
    /**
     * Path to sql script - {tomcat.directory}/database/script.sql
     */
    private static final String PATH_TO_START_SCRIPT = "database/script.sql";

    /**
     * Execute start script and show start page
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            StartScriptExecutor.executeSqlStartScript(PATH_TO_START_SCRIPT);
        } catch (ExecuteSqlScriptException e) {
            req.setAttribute("error", "Try again later please...");
        }
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

}
