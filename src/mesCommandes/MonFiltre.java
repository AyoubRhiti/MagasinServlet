package mesCommandes;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class MonFiltre  implements Filter {
	private FilterConfig filterConfig = null;
	 public void init(FilterConfig filterConfig) throws ServletException {
	 this.filterConfig = filterConfig;
	 }
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
	ServletException {
	String nom = null;
	HttpServletRequest hrequest = (HttpServletRequest) request;
	HttpServletResponse hresponse = (HttpServletResponse) response;
	Cookie[] cookies = hrequest.getCookies();
	if (cookies != null) {
	    for (Cookie cookie : cookies) {
	        if (cookie.getName().equals("nom")) {
	       	    nom=cookie.getName();	 
	        }
	    }
	}
	// test s'il existe un cookie dont l'attribut est "nom"
	if(nom==null) {
		// cas ou il n'existe pas appel de la servlet inscrire
		response.setContentType("text/html");
		 PrintWriter out1 = hresponse.getWriter();
		 out1.println("<html>");
		 out1.println("<body>");
		 out1.println("<head>");
		 out1.println("<title> Acheter </title>");
		 out1.println("</head>");
		 out1.println("<body bgcolor=\"white\">");
		out1.println("<A HREF=sinscrire> vous devez s'inscrire </A><br> ");
		 out1.println("</body>");
		 out1.println("</html>");
	}
	
	 else {
	 chain.doFilter(request, response); }
	}
	
	public void destroy() {
	this.filterConfig = null;
	}
	}