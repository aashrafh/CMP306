

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Lab/NameGender")
public class NameGender extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("Name");
        String gender = request.getParameter("Gender");

        String message = "Your name is " + name + ", and your gender is " + gender;

        response.setContentType("text/html");

        String page = "<!doctype html> <html> <body> <h1>" + message +" </h1> </body></html>";
        response.getWriter().println(page);
	}

}
