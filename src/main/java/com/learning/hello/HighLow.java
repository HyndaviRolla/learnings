package com.learning.hello;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/highlow")
public class HighLow extends HttpServlet {

	public int generateRandom() {
		Random random = new Random();
		 return random.nextInt(30);
	}
	public void reset() {
	}
	private static final long serialVersionUID = 1L;
	@Override
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException {
		PrintWriter out = res.getWriter();
		 boolean flag = false;
		int random = generateRandom();
	    int input = Integer.valueOf(req.getParameter("n"));
		if(random > input)
			out.println("<p> Low </p>");
		else if(random < input)
			out.println("<p> High</p>");
		
				
	}
		
	}
	
	
	
	
	


