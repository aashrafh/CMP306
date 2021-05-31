

import java.io.IOException;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Servlet implementation class NameGenderForm
 */

@WebServlet(name="NameGenderForm",urlPatterns={"/NameGenderForm"})
public class NameGenderForm extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public NameGenderForm() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("Name");
        String gender = request.getParameter("Gender");

        String message = "Your name is " + name + ", and your gender is " + gender;

        response.setContentType("text/html");

        String page = "<!doctype html> <html> <body> <h1>" + message +" </h1> </body></html>";
        response.getWriter().println(page);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
