package com.learning.hello;
import java.io.IOException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import com.learning.hello.controller.OdometerController;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/odo")
  public class OdometerServlet extends HttpServlet{
  private static final long serialVersionUID = 1L;
  private OdometerController od;
  private JakartaServletWebApplication application;
  private TemplateEngine templateEngine;
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    od = new OdometerController();
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
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    od.odometer(Integer.parseInt(req.getParameter("input")));
    var out = resp.getWriter();
    final IWebExchange webExchange = 
    this.application.buildExchange(req, resp);
    final WebContext ctx = new WebContext(webExchange);
    String action = req.getParameter("action");
    if ("next".equals(action)) {
    	 ctx.setVariable("output", od.nextReading());
    	    templateEngine.process("odo", ctx, out);
    } else if ("prev".equals(action)) {
    	 ctx.setVariable("outputprev", od.prevReading());
    	    templateEngine.process("odo", ctx, out);
    }
    else if("size".equals(action))
    {
    	 ctx.setVariable("sizeofReading", od.getSize());
 	    templateEngine.process("odo", ctx, out);
    }
 
   
  }
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    final IWebExchange webExchange = this.application.buildExchange(req, resp);
    final WebContext ctx = new WebContext(webExchange);
    templateEngine.process("odo", ctx, resp.getWriter());
  }

}
