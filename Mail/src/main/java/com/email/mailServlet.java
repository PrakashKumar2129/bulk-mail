package com.email;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class mailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public mailServlet() {
        super();
    }
    
    private String host;
    private String port;
    private String user;
    private String pass;
 
    public void init() {
        // reads SMTP server setting from web.xml file
        ServletContext context = getServletContext();
        host = context.getInitParameter("host");
        port = context.getInitParameter("port");
        user = context.getInitParameter("user");
        pass = context.getInitParameter("pass");
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String recipient = request.getParameter("toMail");
		String subject = request.getParameter("sub");
        String content = request.getParameter("msg");
        
        String[] toAddress = recipient.split(", ");
        
        PrintWriter pw=response.getWriter();
 
        String resultMessage = "";
 
        try {
            email.sendEmail(host, port, user, pass, toAddress, subject, content);
            resultMessage = "The e-mail was sent successfully";
        } catch (Exception e) {
            e.printStackTrace();
            resultMessage = "There were an error: " + e.getMessage();
        } finally {
            getServletContext().getRequestDispatcher("/index.html").include(
                    request, response);
            pw.print("<script>alert('"+ resultMessage + "');</script>");
        }

	}

}
