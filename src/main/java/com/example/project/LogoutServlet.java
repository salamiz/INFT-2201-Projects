/**
 * Name: Zulkifli Salami
 * Student ID: 100850581
 * Date Completed: 03/25/2024
 * Description: This is a server side logout servlet used to implement logout functionality on the code base
 *
 *    Copyright 2024 Zulkifli Salami
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
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

