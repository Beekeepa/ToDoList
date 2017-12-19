import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset = utf-8");
        req.getRequestDispatcher("title.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        RequestDispatcher dispatcher = null;
        BufferedReader reader = new BufferedReader(new FileReader
                ("D:/Учеба/3курс/Программирование/Курсовая/ToDoList/Database.txt"));
        

        Boolean cond = false;
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            String[] arr = line.split(":");
            String logn = arr[0];
            String passwd = arr[1];

            if ((login.equals(logn)) && (password.equals(passwd))) {
                cond = true;
                break;
            }
        }
        if (cond) {
            //dispatcher = getServletContext().getRequestDispatcher("/toDo.jsp");
            //dispatcher.forward(req, resp);
            Cookie getBook = new Cookie("login", login + "|||" + password);
            resp.addCookie(getBook);
            resp.sendRedirect(req.getContextPath() + "/ToDo");
        } else {
            //dispatcher = getServletContext().getRequestDispatcher("/loginError.jsp");
            //dispatcher.forward(req, resp);
            resp.sendRedirect(req.getContextPath() + "/loginError.jsp");
        }
    }

}