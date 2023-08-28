package com.learning.hello;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PasswordServlet extends HttpServlet {
	PasswordStore p = new PasswordStore();
	private static final long serialVersionUID = 1L;

	@Override
    public  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        String enterPassword = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (enterPassword != null && confirmPassword != null && enterPassword.equals(confirmPassword)) {
        	if (PasswordStore.PasswordExist(enterPassword)) {
                p.addPassword(enterPassword);  
                out.println("<p> Password is taken.</p>");
            } else {
                       out.println("<p>The password you entered is already present. Please try again.</p>");
            }
            
           
        } else {
            out.println("<p>Entered and Confirmed passwords donot match</p>");
        }
    }
}
