import java.util.ArrayList;


public class ActionTitre {
	private double prixDAchat;
	private int nb;
	private ActionQuotation actionQuotation;
	
	// region : constructor
	ActionTitre(ActionTitre at)
	{
		this(at.getActionQuotation(), at.getNb());
	}
	ActionTitre(ActionQuotation aq, int nb) {
		this.actionQuotation = aq;
		this.nb = nb;
	}
	// endregion
	
	// region : Getter and setter 
	public double getPrixDAchat() {
		return prixDAchat;
	}
	public void setPrixDAchat(double prixDAchat) {
		this.prixDAchat = prixDAchat;
	}

	public int getNb() {
		return nb;
	}
	public void setNb(int nb) {
		this.nb = nb;
	}

	public ActionQuotation getActionQuotation() {
		return actionQuotation;
	}
	public void setActionQuotation(ActionQuotation actionQuotation) {
		this.actionQuotation = actionQuotation;
	}
	// endregion
	
	
	
}
