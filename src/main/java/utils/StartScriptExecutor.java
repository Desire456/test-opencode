package utils;

import org.hibernate.Session;
import org.hibernate.Transaction;
import utils.exceptions.ExecuteSqlScriptException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Utility class which execute sql script by path.
 * !DANGER! Sql script should be on the path : {tomcat-directory}/bin/database/script.sql.
 */
public class StartScriptExecutor {
    private static final String EXECUTE_SQL_SCRIPT_EXCEPTION_MESSAGE = "Can't execute sql start script";

    public static void executeSqlStartScript(String path) throws ExecuteSqlScriptException {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            StringBuilder query = new StringBuilder();
            String ls = System.getProperty("line.separator");
            while ((line = br.readLine()) != null) {
                query.append(line);
                query.append(ls);
            }
            Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(query.toString()).executeUpdate();
            transaction.commit();
            session.close();
        } catch (IOException e) {
            throw new ExecuteSqlScriptException(EXECUTE_SQL_SCRIPT_EXCEPTION_MESSAGE
                    + e.getMessage());
        }
    }
}
