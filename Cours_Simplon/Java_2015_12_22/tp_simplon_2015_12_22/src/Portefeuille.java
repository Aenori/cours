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
		/* TODO : faites en sorte que cette méthode renvoit la valeur du portefeuille,
		 * sans le budget. I.e. pour chaque ActionTitre de listeActionTitre, le produit
		 * du nombre d'action possédé par le cours actuel de l'action (obtenu via l'at_ 
		 * tribut actionQuotation)
		   */
		return 0.0;
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
		/* TODO : faites en sorte que cette méthode vende tout le portefeuille
		 * d'action.
		 * Cela se fera probablement par des appels répétés à la méthode Vendre.
		   */
		
	}
	// Modifie le budget en tenant compte de la vente, et renvoit la plus value
	public double vendre(int i) throws Exception
	{
		/* TODO : modifier cette fonction de sorte qu'elle vende l'actionTitre d'indice
		 * i dans la liste listeActionTitre.
		 * Cela se traduira par deux actions :
		 *   - supprimer l'objet de la liste (on ne les possède plus)
		 *   - augmenter le budget de la valeur de la vente
		 * On notera quelques fonctions utiles de l'interface List:
		 *   List.remove(int i) => supprimer l'élément d'indice i
		 *   List.get(int i) => renvoit l'objet d'indice i
		 * */
		return 0.0;
	}
	public double vendre(ActionTitre act) throws Exception
	{
		/* TODO : modifier cette fonction de sorte qu'elle vende l'actionTitre donnée
		 * en argument, exactement comme précédemment.
		 * Cette ActionTitre doit nécessairement se trouver dans la liste listeActionTitre.
		 * On préfèrera un appel à la méthode précédente que de la réécrire.
		 */
		return 0.0;
	}
	
	public void acheter(ActionQuotation actQuotation, int nb) throws Exception {
		/* TODO : ici il s'agira de faire l'inverse évidemment !
		 * On ajoute à la liste listeActionTitre un nouvel ojet ActionTitre,
		 * construit à partir des arguments de cette méthode, et on retire le coût 
		 * (égale au produit du nombre d'action et du cours actuel de l'ActionQuotation)
		 * du budget.
		 */
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
