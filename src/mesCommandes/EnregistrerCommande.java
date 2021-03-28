package mesCommandes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
public class EnregistrerCommande extends HttpServlet {
	Connection connexion=null;
	 Statement stmt=null;
	 PreparedStatement pstmt=null;
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	 { String nom = null;
	 int nbreProduit = 0;
	 Cookie[] cookies = request.getCookies();
	 boolean connu = false;
	 nom = Identification. chercheNom (cookies);
	 OuvreBase();
	 
	 AjouteNomBase(nom);

	 response.setContentType("text/html");
	 PrintWriter out = response.getWriter();
	 out.println(nom);
	 out.println("<html>");
	 out.println("<body>");
	 out.println("<head>");
	 out.println("<title> votre commande </title>");
	 out.println("</head>");
	 out.println("<body bgcolor=\"white\">");

	 out.println("<h3>" + "Bonjour " + nom + " voici ta nouvelle commande" + "</h3>");
	 HttpSession session = request.getSession();
	 Enumeration <String> names = session.getAttributeNames();
	 while (names.hasMoreElements()) {
	 nbreProduit++;
	 String name = (String) names.nextElement();
	 String value = session.getAttribute(name).toString();
	 out.println(name + " = " + value + "<br>");
	 }
	 AjouteCommandeBase(nom,session);
	 out.println("<h3>" + "et voici " + nom + " ta commande complete" + "</h3>");
	 MontreCommandeBase(nom, out);
	 out.println("<A HREF=mesCommandes.VidePanier> Vous pouvez commandez un autre disque </A><br> ");
	 out.println("</body>");
	 out.println("</html>");
	 }
	 protected void OuvreBase() {
	 try {
	 Class.forName("com.mysql.cj.jdbc.Driver");
	 connexion = DriverManager.getConnection("jdbc:mysql://localhost/magasin","root","root");
	 connexion.setAutoCommit(true);
	 stmt = connexion.createStatement();
	 }
	 catch (Exception E) {
	 log(" -------- probeme " + E.getClass().getName() );
	 E.printStackTrace();
	 }
	 }
	 protected void fermeBase() {
	 try {
	 stmt.close();
	 connexion.close();
	 }
	 catch (Exception E) {
	 log(" -------- probeme " + E.getClass().getName() );
	 E.printStackTrace();
	 }
	 }
	
	 protected void AjouteNomBase(String nom) {
	 try {
	 ResultSet rset = null;
	 
	 pstmt= connexion.prepareStatement("select numero from personnel where nom=? ");
	 pstmt.setString(1,nom);
	 rset=pstmt.executeQuery();
	 if (!rset.next()) {
	 /*stmt.executeUpdate("INSERT INTO personnel (numero , nom) VALUES (DEFAULT , ?");
	 stmt.setString(2, nom);*/
	 pstmt = connexion.prepareStatement("INSERT INTO personnel (numero , nom) VALUES (DEFAULT , ?)");
	 pstmt.setString(1, nom);
	 pstmt.executeUpdate();
	 }
	 
	 }
	 catch (Exception E) {
	 log(" --- probeme " + E.getClass().getName() );
	 E.printStackTrace();
	 }
	 }
	protected void AjouteCommandeBase(String nom, HttpSession session ) {
		 // ajoute le contenu du panier dans la base
			 try {
				 ResultSet resultSet = null;
				 pstmt= connexion.prepareStatement("select numero from personnel where nom=?");
				 pstmt.setString(1,nom);
				 resultSet=pstmt.executeQuery();
				 int numUser = 0;
				 if(resultSet.next()) {
					 numUser=resultSet.getInt("numero");
				 }
				
				 Enumeration<String> names =session.getAttributeNames();
				 
				
					 log("true");
					 String Disque = (String) names.nextElement();
					 log(Disque);
					 String nomDisque = (String) session.getAttribute(Disque);
					 log(nomDisque);
					 String query = "INSERT INTO commande (num,article,qui) VALUES (DEFAULT,? ,?)";
					 PreparedStatement preparedStatement = connexion.prepareStatement(query);
					 preparedStatement.setString(1, nomDisque);
					 preparedStatement.setInt(2, numUser);
					 preparedStatement.executeUpdate();
				 
				 

				 }catch (Exception E) {
				  log(" -- probeme " + E.getClass().getName() );
				  E.printStackTrace();
				 }
		 }
		 protected void MontreCommandeBase(String nom, PrintWriter out) {
		 // affiche les produits présents dans la base
			 	try {
			 		 ResultSet resultSet = null;
					 pstmt= connexion.prepareStatement("select * from commande");
					 //pstmt.setString(1,nom);
					 resultSet=pstmt.executeQuery();
					 int numUser = 0;
					 if(resultSet.next()) {
						 numUser=resultSet.getInt("numero");
						 out.println(numUser);
						 }

					
		
			 	}catch (Exception E) {
			 		 log(" *- probeme " + E.getClass().getName() );
					 E.printStackTrace();
					
					 }
		 }
		 }
