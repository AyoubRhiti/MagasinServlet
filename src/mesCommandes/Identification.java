package mesCommandes;

import javax.servlet.http.Cookie;

public class Identification {
	
		 static String chercheNom (Cookie [] cookies) {
			 
			 // cherche dans les cookies la valeur de celui qui se nomme "nom"
		 // retourne la valeur de ce nom au lieu de inconnu
			 String valeur = "inconnue";
			 if (cookies != null) {
		         for (Cookie cookie : cookies) {
		             if (cookie.getName().equals("nom")) {
		            	 valeur = cookie.getValue();            	 
		             }
		         }
		     }
			 return valeur;
		}
		 
static String cherchemdp (Cookie [] cookies) {
			 
			 // cherche dans les cookies la valeur de celui qui se nomme "nom"
		 // retourne la valeur de ce nom au lieu de inconnu
			 String valeur = "inconnue";
			 if (cookies != null) {
		         for (Cookie cookie : cookies) {
		             if (cookie.getName().equals("motdepasse")) {
		            	 valeur = cookie.getValue();            	 
		             }
		         }
		     }
			 return valeur;
		}
}
