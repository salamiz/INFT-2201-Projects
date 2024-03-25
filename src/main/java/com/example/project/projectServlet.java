/**
 * Name: Zulkifli Salami
 * Student ID: 100850581
 * Date Completed: 03/25/2024
 * Description: This is a server side servlet used to implement page routing on the project
 */

package com.example.project;

import java.io.*;
import java.sql.*;

import com.example.project.DatabaseUtility;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name ="projectServlet", urlPatterns = {"/auth"})
public class projectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    /*
    * Function to handle registration
    * */
    private void handleRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Server-side validation
        if (username == null || firstName == null || lastName == null || email == null || password == null ||
                username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() ||
                !email.contains("@") || password.length() < 6) {
            response.sendRedirect("/register.jsp?error=validationFailed");
            return;
        }

        try (Connection con = DatabaseUtility.getDataSource().getConnection()) {
            // Check if username already exists
            try (PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) AS count FROM users WHERE username = ?")) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next() && rs.getInt("count") > 0) {
                        response.sendRedirect("/register.jsp?error=usernameExists");
                        return;
                    }
                }
            }

            // Check if email already exists
            try (PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) AS count FROM users WHERE email = ?")) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next() && rs.getInt("count") > 0) {
                        response.sendRedirect("/register.jsp?error=emailExists");
                        return;
                    }
                }
            }

            // Hashing the password for security
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            // Insert the new user
            try (PreparedStatement stmt = con.prepareStatement("INSERT INTO users (username, firstName, lastName, email, hash_password) VALUES (?, ?, ?, ?, ?)")) {
                stmt.setString(1, username);
                stmt.setString(2, firstName);
                stmt.setString(3, lastName);
                stmt.setString(4, email);
                stmt.setString(5, hashedPassword);

                int result = stmt.executeUpdate();

                if (result > 0) {
                    response.sendRedirect("/login.jsp?success=successfulRegistration");
                } else {
                    response.sendRedirect("/register.jsp?error=unexpectedError");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred.");
        }
    }



    /*
     * Function to handle login
     * */
    private  void handleLogin (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // user input
        String username = request.getParameter("username");
        String pass = request.getParameter("password");

        // Input validation
        if (username == null || pass == null || username.isEmpty() || pass.isEmpty()) {
            response.sendRedirect("login.jsp?error=emptyFields");
            return;
        }

        try (Connection con = DatabaseUtility.getDataSource().getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT hash_password FROM users WHERE username = ?")) {

            // Logging in user
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("hash_password");
                    if (BCrypt.checkpw(pass, storedHash)) {
                        // Authentication successful
                        HttpSession session = request.getSession(true); // Create new session
                        session.setMaxInactiveInterval(120);
                        session.setAttribute("user", username); // Set the username in the session
                        session.setAttribute("isLoggedIn", true);
                        response.sendRedirect("/about.jsp?success=successfulLogin");
                    } else {
                        // Authentication failed
                        response.sendRedirect("/login.jsp?error=invalidCredentials");
                    }
                } else {
                    // User not found
                    response.sendRedirect("/login.jsp?error=userNotFound");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred.");
        }

    }


    /*
    * Function to render session information on client side page
    * */
    private void handleStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute("user") != null;
        String username = isLoggedIn ? session.getAttribute("user").toString() : "";

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("{\"loggedIn\": " + isLoggedIn + ", \"username\": \"" + username + "\"}");
        out.flush();
    }


    /*
    * Function to manage session status
    * */
    private void checkSessionStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute("user") != null;

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("{\"sessionActive\": " + isLoggedIn + "}");
        out.flush();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");

        if ("register".equals(action)) {
            handleRegistration(request, response);
        } else if ("login".equals(action)) {
            handleLogin(request, response);
        } else if ("status".equals(action)) {
            handleStatus(request, response);
        } else if ("checkSession".equals(action)) {
            checkSessionStatus(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action");
        }
    }

}