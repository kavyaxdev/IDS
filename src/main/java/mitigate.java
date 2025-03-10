import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.codejava.Graph.userMitigation;

/**
 * Servlet implementation class mitigate
 */
@WebServlet("/mitigate")
public class mitigate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public mitigate() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname=request.getParameter("data");
		userMitigation.addUser(uname);
	}

}
