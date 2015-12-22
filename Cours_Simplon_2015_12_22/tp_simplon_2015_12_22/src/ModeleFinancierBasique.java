import java.util.Random;


public class ModeleFinancierBasique implements IModeleFinancier {
	private static final double VARIATION_ANNUEL_DE_BASE = 0.02;
	private final Random randomGenerator;
	
	public ModeleFinancierBasique() {
		this.randomGenerator = new Random(0);
	}

	@Override
	public void faireVariationAnnuelle(ActionQuotation actionQuotation) {
		if( actionQuotation.getCoursActuel() < 0.001 ) return;
		
		double coursDeReference = (3*actionQuotation.getCoursActuel() + actionQuotation.getCoursDEquilibre())/4;
		double nouveauCours = coursDeReference*(
				1 
				+ VARIATION_ANNUEL_DE_BASE 
				+ 0.001*actionQuotation.getVolatilite()
				+ 0.01*actionQuotation.getVolatilite()*randomGenerator.nextGaussian() );
		if( nouveauCours < 0.001 ) nouveauCours = 0.0;
		actionQuotation.setCoursActuel( nouveauCours );
		actionQuotation.setCoursDEquilibre(actionQuotation.getCoursDEquilibre()*(1+VARIATION_ANNUEL_DE_BASE));
	}
}
