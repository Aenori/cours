import java.util.ArrayList;


public class main {
	public static void main(String[] args) throws Exception {
		ActionQuotation act1 = new ActionQuotation("StartUp",100,40);
		ActionQuotation act2 = new ActionQuotation("Total"  ,100,10);
		ActionQuotation act3 = new ActionQuotation("EDF"    ,100, 5);
		ActionQuotation act4 = new ActionQuotation("BNP"    ,100, 0);
		
		ModeleFinancierBasique modeleFinancier1 = new ModeleFinancierBasique();
		
		for(ActionQuotation act: ActionQuotation.getListeActionQuotation())
		{
			Portefeuille portefeuille1 = new Portefeuille(10000);
			portefeuille1.acheter(act, (int)(10000 / act.getCoursActuel()));
			portefeuille1.simuler1000(modeleFinancier1, 10);
		}
		
	}

}
