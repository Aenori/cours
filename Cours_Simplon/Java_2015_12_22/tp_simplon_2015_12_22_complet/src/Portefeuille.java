import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Portefeuille {
	private List<ActionTitre> listeActionTitre;
	private double budget;
	private final double budgetInitial;
	private Gestionnaire gestionnaire;
	
	public Portefeuille() {
		this(0.0);
	}
	
	public Portefeuille(double budgetInitial)
	{
		this.listeActionTitre = new ArrayList<ActionTitre>();
		this.budget = budgetInitial;
		this.budgetInitial = budgetInitial;
		this.gestionnaire = null;
	}
	
	public double valeurPortefeuille()
	{
		double valeur = 0;
		for(ActionTitre act: this.listeActionTitre)
		{
			valeur += act.getNb()*act.getActionQuotation().getCoursActuel();
		}
		return valeur;
	}

	public void simuler1000(IModeleFinancier modelFinancier, int nbAnnees) throws Exception
	{
		List<Double> resultat = new ArrayList<Double>();
		
		List<ActionTitre> portefeuilleInitial = new ArrayList<ActionTitre>();
		for(ActionTitre act: this.listeActionTitre) portefeuilleInitial.add(new ActionTitre(act));
		double budgetAvantSimu = this.budget;
		for(int i = 0; i < 1000 ; ++i) {
			resultat.add( simuler(modelFinancier,nbAnnees) );
			
			// Reinitialisation du portefeuille
			this.budget = budgetAvantSimu;
			for(ActionQuotation aq: ActionQuotation.getListeActionQuotation())
			{
				aq.reInitialise();
			}
			for(ActionTitre act: portefeuilleInitial)
			{
				this.listeActionTitre.add(new ActionTitre(act));
			}
		}
		
		Collections.sort(resultat);
		
		double pireDixPourcent = 0;
		double moyenne = 0;
		
		for(int i = 0; i < 100  ; ++i) pireDixPourcent += resultat.get(i);
		for(int i = 0; i < 1000 ; ++i) moyenne += resultat.get(i);
		
		System.out.println("Resultat moyen dans les 10% pires cas : " + pireDixPourcent / 100);
		System.out.println("Resultat moyen  : " + moyenne / 1000);
	}
	
	public Double simuler(IModeleFinancier modelFinancier, int nbAnnees) throws Exception 
	{
		return simuler(modelFinancier,nbAnnees,false);
	}
	
	public Double simuler(IModeleFinancier modelFinancier, int nbAnnees, boolean verbose) throws Exception {
		for(int iAnnee = 0; iAnnee < nbAnnees; ++iAnnee) {
			simulerAnnee(modelFinancier, iAnnee);
			if(verbose) {
				System.out.println( "Annee " + iAnnee + " => " + this.valeurPortefeuille());
			}
		}
		vendreTout();
		return this.budget;
	}

	private void simulerAnnee(IModeleFinancier modelFinancier, int iAnnee) {
		for(ActionQuotation aq: ActionQuotation.getListeActionQuotation()) {
			modelFinancier.faireVariationAnnuelle(aq);
		}
		if(gestionnaire != null) gestionnaire.gererPortefeuille(this, iAnnee);
	}

	private void vendreTout() throws Exception {
		while(this.listeActionTitre.size() != 0) {
			vendre(0);
		}
	}
	// Modifie le budget en tenant compte de la vente, et renvoit la plus value
	public double vendre(int i) throws Exception
	{
		if(i < 0 || i >= this.listeActionTitre.size() )
		{
			throw new Exception("Invalid action index");
		}
		ActionTitre aVendre = this.listeActionTitre.get(i);
		this.listeActionTitre.remove(i);
		
		this.budget += aVendre.getNb()*aVendre.getActionQuotation().getCoursActuel();
		return aVendre.getNb()*(aVendre.getActionQuotation().getCoursActuel() - aVendre.getPrixDAchat());
	}
	public double vendre(ActionTitre act) throws Exception
	{
		return vendre( this.listeActionTitre.indexOf(act) );
	}
	
	public void acheter(ActionQuotation actQuotation, int nb) throws Exception {
		if(nb*actQuotation.getCoursActuel() > budget ) {
			throw new Exception("Budget insuffisant");
		}
		this.budget -= nb*actQuotation.getCoursActuel();
		this.listeActionTitre.add(new ActionTitre(actQuotation, nb ));
	}
	
	public List<ActionTitre> getListeActionTitre() { return listeActionTitre; }
	public void setListeActionTitre(List<ActionTitre> listeActionTitre) { 
		this.listeActionTitre = listeActionTitre;
	}
	
	public double getBudget() { return budget; }
	public void setBudget(double budget) { this.budget = budget; }

	public Gestionnaire getGestionnaire() { return gestionnaire; }
	public void setGestionnaire(Gestionnaire gestionnaire) { this.gestionnaire = gestionnaire; }

	public double getBudgetInitial() { return budgetInitial; }

	
}
