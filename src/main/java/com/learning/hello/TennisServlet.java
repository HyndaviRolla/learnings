 package com.learning.hello;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import com.learning.hello.controller.*;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/tennis")

public class TennisServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private JakartaServletWebApplication application;
    private TemplateEngine templateEngine; 
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
      templateEngine.setTemplateResolver(templateResolver);
    }
 
@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	final IWebExchange webExchange = this.application.buildExchange(request, response);
    final WebContext ctx = new WebContext(webExchange);   
    templateEngine.process("Tennis", ctx,response.getWriter());
    }
@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	final IWebExchange webExchange = 
	    this.application.buildExchange(request, response);
        final WebContext ctx = new WebContext(webExchange); 
      
		 var out = response.getWriter();
		  String action = request.getParameter("action");
		  
		    if("start".equals(action)) {
		    	while (!ScoreUpdate.MatchCompleted()) {
		            String player = Players.choosePlayer();
		            ScoreUpdate.updateScore(player);      
	try {	 
     Class.forName("com.mysql.cj.jdbc.Driver");
    
   	 String insertQuery = "INSERT INTO Tennis(FedererScore,NadalScore) VALUES (?, ?)"; 	     
   	 String GameInsertQuery = "INSERT INTO Game(FedererGame,NadalGame) VALUES (?, ?)";
   	 String SetInsertQuery = "INSERT INTO TennisSet(FedererSet,NadalSet) VALUES (?, ?)";
     Connection  cnx= DriverManager.getConnection("jdbc:mysql://localhost:3306/Tennis","Database","atharva");
    
     PreparedStatement stmt = cnx.prepareStatement(insertQuery);
   	 PreparedStatement stmtGame = cnx.prepareStatement(GameInsertQuery);
   	 PreparedStatement stmtSet = cnx.prepareStatement(SetInsertQuery);   
           stmt.setInt(1,ScoreUpdate.currentFedererScore);
           stmt.setInt(2,ScoreUpdate.currentNadalScore);
           stmtGame.setInt(1,Game.currentFedererGame);
           stmtGame.setInt(2,Game.currentNadalGame);
           stmtSet.setInt(1,Set.currentFedererSets);
           stmtSet.setInt(2,Set.currentNadalSets);
           stmtGame.executeUpdate();
           stmtSet.executeUpdate();
           stmt.executeUpdate();
	       stmt.close();
	       stmtGame.close();
	       stmtSet.close();
	       cnx.close();
	    
       } catch (ClassNotFoundException e) {
   	    response.getWriter().println("MySQL JDBC driver not found.");
   	}   catch (Exception e) {
           out.println("An error occurred: " + e.getMessage());
       }
        
        ctx.setVariable("federerScore",ScoreUpdate.currentFedererScore);
        ctx.setVariable("nadalScore",ScoreUpdate.currentNadalScore);
        ctx.setVariable("federerGame",Game.currentFedererGame);
     ctx.setVariable("nadalGame",Game.currentNadalGame);
     ctx.setVariable("federerSet",Set.currentFedererSets);
     ctx.setVariable("nadalSet",Set.currentNadalSets);
     templateEngine.process("Tennis", ctx, response.getWriter());
         if (Set.currentFedererSets >= 2 && Set.currentNadalSets < 2) 
            out.println("Match won by Federer");
        
         else if (Set.currentNadalSets >= 2 && Set.currentFedererSets < 2)
         out.println("Match won by Nadal ");   
		    }
		    }
		    else  if ("saveMatch".equals(action)) {
	            saveMatch();
	           // response.sendRedirect("tennis?message=has been saved");
	            out.println("Game saved");
	        } else if ("resumeMatch".equals(action)) {
	            resumeMatch(response);
	        //    response.sendRedirect("tennis?message=has been resumed");
	            out.println("Game resumed");
	        }else if ("updateScore".equals(action)) {
        String selectedPlayer = request.getParameter("player");
        updateScore(selectedPlayer);
    }
    
}
private void saveMatch() {
    try {
        Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/Tennis", "Database", "atharva");
        String insertQuery = "INSERT INTO GameMatches (FedererScore, NadalScore, FedererGame, NadalGame, FedererSet, NadalSet) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = cnx.prepareStatement(insertQuery);
        stmt.setInt(1, ScoreUpdate.currentFedererScore);
        stmt.setInt(2, ScoreUpdate.currentNadalScore);
        stmt.setInt(3, Game.currentFedererGame);
        stmt.setInt(4, Game.currentNadalGame);
        stmt.setInt(5, Set.currentFedererSets);
        stmt.setInt(6, Set.currentNadalSets);
        stmt.executeUpdate();
        stmt.close();
        cnx.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void resumeMatch(HttpServletResponse response) throws IOException {
    try {
        Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/Tennis", "Database", "atharva");
        String selectQuery = "SELECT * FROM GameMatches ORDER BY MatchId DESC LIMIT 1";
        PreparedStatement stmt = cnx.prepareStatement(selectQuery);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            ScoreUpdate.currentFedererScore = rs.getInt("FedererScore");
            ScoreUpdate.currentNadalScore = rs.getInt("NadalScore");
            Game.currentFedererGame = rs.getInt("FedererGame");
            Game.currentNadalGame = rs.getInt("NadalGame");
            Set.currentFedererSets = rs.getInt("FedererSet");
            Set.currentNadalSets = rs.getInt("NadalSet");
        }
        rs.close();
        stmt.close();
        cnx.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void updateScore(String player) {
    if ("Federer".equals(player)) {
        ScoreUpdate.updateScore("Federer");
    } else if ("Nadal".equals(player)) {
        ScoreUpdate.updateScore("Nadal");
    }
    
}
}