import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Algorithms.passwordReset;
import Algorithms.genericFunctions;
import net.codejava.Classes.User;

@WebServlet("/homeLogin")
public class homeLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public homeLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uname=request.getParameter("Username");
		String pwd= request.getParameter("Password");
		
		String ans=passwordReset.getAuth(uname, pwd);
		
		if(ans.equals("accepted"))
		{
			User x=new User(uname);
			String val=genericFunctions.getCompromised(uname, pwd);
			
			if(val.equals("true"))
			{
				genericFunctions.addRisk(x, 5.0,"PasswordRisk");
				response.setContentType("text/html");
				PrintWriter pw=response.getWriter();
				pw.println("<script type=\"text/javascript\">");
				pw.println("alert('Reset password');");
				pw.println("</script>");
				RequestDispatcher rd=request.getRequestDispatcher("reset.jsp");
				rd.include(request, response);
			}
			
			else
			{
				genericFunctions.addRisk(x, 0.0,"PasswordRisk");
				response.sendRedirect("loginSuccess.jsp");
			}
		}
		
		else
		{
			response.setContentType("text/html");
			PrintWriter pw=response.getWriter();
			pw.println("<script type=\"text/javascript\">");
			pw.println("alert('Invalid Username or Password');");
			pw.println("</script>");
			RequestDispatcher rd=request.getRequestDispatcher("Login.jsp");
			rd.include(request, response);
		}

	}

}