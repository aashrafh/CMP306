import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class NameGender extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
        String name = request.getParameter("Name");
        String gender = request.getParameter("Gender");

        String message = "Your name is " + name + ", and your gender is " + gender;

        response.setContentType("text/html");

        String page = "<!doctype html> <html> <body> <h1>" + message +" </h1> </body></html>";
        response.getWriter().println(page);
    }

}
