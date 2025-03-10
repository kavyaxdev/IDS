

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Algorithms.passwordReset;
import Algorithms.updatePassword;
/**
 * Servlet implementation class PasswordReset
 */
@WebServlet("/passreset")
public class PasswordReset extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PasswordReset() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uname=request.getParameter("Username");
		String pwd= request.getParameter("oldPassword");
		String pwdNew= request.getParameter("newPassword");
		
		String ans=passwordReset.getAuth(uname, pwd);
		System.out.println(uname);
		System.out.println(pwd);
		
		if(!ans.equals("accepted"))
		{
			response.setContentType("text/html");
			PrintWriter pw=response.getWriter();
			pw.println("<script type=\"text/javascript\">");
			pw.println("alert('Invalid Username or Password');");
			pw.println("</script>");
			RequestDispatcher rd=request.getRequestDispatcher("reset.jsp");
			rd.include(request, response);
		}
		
		else
		{
			updatePassword.update(uname, pwdNew);
			RequestDispatcher rd=request.getRequestDispatcher("Login.jsp");
			rd.include(request, response);
		}
	}

}
