/**
 * Name: Zulkifli Salami
 * Student ID: 100850581
 * Date Completed: 03/25/2024
 * Description: This is a server side logout servlet used to implement logout functionality on the code base
 */



package com.example.project;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Attempting to get the current session without creating a new one
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Invalidating session to remove all attributes
        }
        response.sendRedirect("login.jsp"); // Redirecting to login page
    }
}

