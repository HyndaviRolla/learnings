package com.learning.hello;
import com.learning.hello.controller.Notice;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 

@WebServlet("/notice")
public class NoticeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private JakartaServletWebApplication application;
    private TemplateEngine templateEngine;   
    private Notice notice;
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        }

    }
     
    @Override
    public void init(ServletConfig config) throws ServletException {
      super.init(config);
      application = JakartaServletWebApplication.buildApplication(getServletContext());
     final WebApplicationTemplateResolver templateResolver = 
      new WebApplicationTemplateResolver(application);
      templateResolver.setTemplateMode(TemplateMode.HTML);
      templateResolver.setPrefix("/WEB-INF/Template/");
      templateResolver.setSuffix(".html");
      templateEngine = new TemplateEngine();
      notice = new Notice();
    		  templateEngine.setTemplateResolver(templateResolver);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	final IWebExchange webExchange = this.application.buildExchange(request, response);
        final WebContext ctx = new WebContext(webExchange);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String contact = request.getParameter("contact");
        String emailId = request.getParameter("emailId");
        Connection cnx = null;
		 PreparedStatement stmt = null;
        try {
          
    	     Class.forName("com.mysql.cj.jdbc.Driver");
             cnx= DriverManager.getConnection("jdbc:mysql://localhost:3306/Notice","Database","atharva");

    	     String insertQuery = "INSERT INTO Notice(Name, ContactNumber,EmailId) VALUES (?, ?,?)";
    	     PreparedStatement selectCountStatement = cnx.prepareStatement("SELECT COUNT(*) FROM Notice;");
             PreparedStatement deleteLeastUsedStatement = cnx.prepareStatement("DELETE FROM Notice ORDER BY id LIMIT 1;"); 
            stmt = cnx.prepareStatement(insertQuery);
            
            stmt.setString(1,name);
            stmt.setString(2,contact);
            stmt.setString(3,emailId);
            stmt.executeUpdate();
            ResultSet countResult = selectCountStatement.executeQuery();
            if (countResult.next()) {
                int totalCount = countResult.getInt(1);
                if (totalCount > 6) 
                    deleteLeastUsedStatement.executeUpdate();
                }
	         stmt.close();
	         cnx.close();
        } catch (ClassNotFoundException e) {
    	    response.getWriter().println("MySQL JDBC driver not found.");
    	}   catch (Exception e) {
            out.println("An error occurred: " + e.getMessage());
        }
        ctx.setVariable("name", name);
        ctx.setVariable("contact", contact);
        ctx.setVariable("emailId", emailId);
        templateEngine.process("Notice1", ctx, response.getWriter());          
    }
 
      @Override
      protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final IWebExchange webExchange = this.application.buildExchange(request, response);
        final WebContext ctx = new WebContext(webExchange);
        List<Notice> notices = notice.getAllNotices();
        request.setAttribute("notices", notices);
        templateEngine.process("Notice", ctx, response.getWriter());
        
      }

    

}
