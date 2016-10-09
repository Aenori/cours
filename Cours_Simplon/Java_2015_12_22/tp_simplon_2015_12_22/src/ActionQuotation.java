import java.util.ArrayList;
import java.util.List;


public class ActionQuotation {
	private String nom;
	private double coursActuel;
	private double coursDEquilibre;
	private double volatilite;
	private final double coursInitial;
	private static List<ActionQuotation> listeActionQuotation = new ArrayList<ActionQuotation>();
	
	
	ActionQuotation(String nom, double coursActuel )
	{
		this(nom,coursActuel,0.0);
	}
	
	ActionQuotation(String nom, double coursActuel, double volatilite )
	{
		this.nom = nom;
		this.coursActuel = coursActuel;
		this.coursDEquilibre = coursActuel;
		this.volatilite = volatilite;
		this.coursInitial = coursActuel;
		
		listeActionQuotation.add( this );
	}

	public void reInitialise() {
		/* TODO : modifier ce code de sorte que lorsque la méthode est appelée,
		   les variables coursActuel et coursDEquilibre deviennent égales à la 
		   valeur de coursInitial
		   */ 
	}
	
	public String getNom() { return nom; }
	public void setNom(String nom) { this.nom = nom; }

	public double getCoursActuel() { return coursActuel; }
	public void setCoursActuel(double coursActuel) { this.coursActuel = coursActuel; }

	public double getCoursDEquilibre() { return coursDEquilibre; }
	public void setCoursDEquilibre(double coursDEquilibre) { this.coursDEquilibre = coursDEquilibre; }

	public double getVolatilite() { return volatilite; }
	public void setVolatilite(double volatilite) { this.volatilite = volatilite; }

	public static List<ActionQuotation> getListeActionQuotation() {
		return listeActionQuotation;
	}
}
