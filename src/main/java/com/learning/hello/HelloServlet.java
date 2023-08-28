 
package com.learning.hello;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet{
  
  private static final long serialVersionUID = 1L;

@Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) {
    try {
      Map<String, String[]> parameterMap = req.getParameterMap();
      parameterMap.entrySet().stream().forEach(entry -> {
        System.out.print("key: " + entry.getKey());
        System.out.print(" values: " + Arrays.toString(entry.getValue()) + "\n");
      });
      PrintWriter out = resp.getWriter();
      String Us = parameterMap.get("name")[0];
      out.println(String.format("<h1>Hello! %s</h1>",Us));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}