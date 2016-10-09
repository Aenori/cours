package edu.simplon.datascience;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.simplon.data.DataJour1;
import edu.simplon.data.Item;

public class FonctionRecherche {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Item[] tab = DataJour1.getItemTab100();
		DataJour1.sort_all(true);
		for( Item item : trouvez_items_avec_prix_identiques(tab))
		{
			System.out.println(item);
		}
	}

	public static void test_item_avec_prix_identiques()
	{
		Item[][] all_item_tabs = new Item[][]{ 
				DataJour1.getItemTab10(), 
				DataJour1.getItemTab100(), 
				DataJour1.getItemTab1000() };

		int nb_item = 10; 
		for( int i = 0; i < all_item_tabs.length ; ++i )
		{
			List<Item> result = trouvez_items_avec_prix_identiques( all_item_tabs[i] );
			System.out.println( "nb items : " + nb_item + " nb items avec le même prix " + result.size());
			for(Item item : result)
			{
				//System.out.println(item);
			}
			nb_item *= 10;
		}
	}

	// Exercice 1
	public static void affichez_tous_les_elements( Item[] tab )
	{
		for( Item item : tab )	{ System.out.println( item ); }
	}

	// Exercice 3
	public static double[] prix_min_et_max_liste( Item[] tab  )
	{
		double[] result = new double[]{ tab[0].getPrix(), tab[0].getPrix() };
		for( Item item : tab )
		{
			if( item.getPrix() > result[1] )
			{
				result[1] = item.getPrix();
			}
			else if( item.getPrix() < result[0] )
			{
				result[0] = item.getPrix();
			}
		}
		return result;
	}

	public static void mesurer_temps_min_et_max_listes()
	{
		Item[][] all_item_tabs = new Item[][]{ 
				DataJour1.getItemTab10(), 
				DataJour1.getItemTab100(), 
				DataJour1.getItemTab1000(), 
				DataJour1.getItemTab10000(), 
				DataJour1.getItemTab100000(),
				DataJour1.getItemTab1000000() };

		int nb_item = 10; 
		for( int i = 0; i < all_item_tabs.length ; ++i )
		{
			System.gc();
			long debut = System.nanoTime();
			double[] result = prix_min_et_max_liste( all_item_tabs[i] );
			System.out.println( "Appel avec " + nb_item + " resultats : " + result[0] + " / " + result[1] + " en " + (System.nanoTime() - debut) + " millisecondes");
			nb_item *= 10;
		}
	}

	// Exercice 4 
	public static List<Item> extraction_dans_une_fourchette_de_prix( 
			Item[] item_tab, double prix_min, double prix_max )
	{
		List<Item> result = new ArrayList<Item>();
		for(Item item: item_tab)
		{
			if( (item.getPrix() >= prix_min) && (item.getPrix() <= prix_max) )
			{
				result.add(item);
			}
		}
		return result;
	}

	// Exercice 5
	public static boolean possede_deux_prix_identiques(Item[] item_tab)
	{
		for(int i = 0; i < item_tab.length; ++i)
		{
			for(int j = i+1; j < item_tab.length; ++j)
			{
				if( item_tab[i].getPrix() == item_tab[j].getPrix() )
				{
					System.out.println( "L'élément " + i + " a le même prix que l'élément " + j + " : " + item_tab[i].getPrix());
					return true;
				}
			}
		}
		return false;
	}

	// Exercice 6
	public static List<Item> trouvez_items_avec_prix_identiques(Item[] item_tab)
	{
		List<Item> result = new ArrayList<Item>();
		for(int i = 0; i < item_tab.length - 1; ++i)
		{
			boolean added = false;
			for(int j = 0; j < item_tab.length; ++j)
			{
				if( j == i ) ++j;
				if( item_tab[i].getPrix() == item_tab[j].getPrix() )
				{
					if( j < i ) break;
					if( !added )
					{
						result.add(item_tab[i]);
						added = true;
					}
					result.add(item_tab[j]);
				}
			} 
		}
		return result;
	}

	// Exercice 7
	public static Item recherche_dichotomique_by_id(Item[] item_tab, int id )
	{
		int a = 0, b = item_tab.length;
		while( b != a)
		{
			int c = (a+b)/2;
			if( item_tab[c].getId() == id) return item_tab[c];
			if( item_tab[c].getId() < id ) { a = c + 1; }
			else { b = c; }
		}
		return null;
	}

	// Exercice 8
	public static double[] prix_min_et_max_liste_trie( Item[] tab  )
	{
		return new double[]{ tab[0].getPrix(), tab[tab.length - 1].getPrix() };
	}

	// Exercice 9
		public static List<Item> extraction_dans_une_fourchette_de_prix_trie( 
				Item[] item_tab, double prix_min, double prix_max )
		{
			List<Item> result = new ArrayList<Item>();
			int a = FonctionRecherche.recherche_par_prix(
					item_tab, 
					prix_min, 
					optionEgalite.PLUS_PETIT_INDEX,
					optionAbsent.INDEX_SUPERIEUR);
			int b = FonctionRecherche.recherche_par_prix(
					item_tab, 
					prix_max, 
					optionEgalite.PLUS_GRAND_INDEX,
					optionAbsent.INDEX_INFERIEUR);
			for(int i = a; i <= b && b != -1 && i < item_tab.length ; ++i )
			{
				result.add(item_tab[i]);
			}
			return result;
		}
		
	// Exercice 10
	public static boolean possede_deux_prix_identiques_trie( Item[] tab  )
	{
		for(int i = 0; i < tab.length - 1; ++i)
		{
			if( tab[i].getPrix() == tab[i+1].getPrix()) return true;
		}
		return false;
	}

	// Exercice 11
	public static List<Item> trouvez_items_avec_prix_identiques_trie(Item[] item_tab)
	{
		List<Item> result = new ArrayList<Item>();
		for(int i = 0; i < item_tab.length - 1; ++i)
		{
			if( item_tab[i].getPrix() == item_tab[i+1].getPrix() ) result.add(item_tab[i]);
			while( i+1 < item_tab.length && item_tab[i].getPrix() == item_tab[i+1].getPrix() )
			{
				// Petit plaisir d'informaticien, évitez
				result.add(item_tab[++i]);
			}
		}
		return result;
	}

	// Fonction pour les exercices 8 à 12
	// comportement_egalite :
	//		0 -> renvoyer premier index trouvé
	//		1 -> renvoyer plus petit index
	//		2 -> renvoyer plus grand index
	// comportement_absent :
	//		0 -> renvoit -1
	//		1 -> renvoit plus grand index inférieur
	//		2 -> renvoit plus petit index supérieur
	public static enum optionEgalite
	{
		PREMIER_TROUVE ,
		PLUS_PETIT_INDEX,
		PLUS_GRAND_INDEX
	}

	public static enum optionAbsent
	{
		MINUS_1,
		INDEX_SUPERIEUR,
		INDEX_INFERIEUR
	}

	public static int recherche_par_prix(
			Item[] item_tab, 
			double prix )
	{
		return recherche_par_prix( item_tab, prix, optionEgalite.PREMIER_TROUVE, optionAbsent.MINUS_1);
	}

	public static int recherche_par_prix(
			Item[] item_tab, 
			double prix, 
			optionEgalite comportement_egalite, 
			optionAbsent comportement_absent )
	{
		int a = 0, b = item_tab.length;
		if( item_tab[0].getPrix() > prix ) return comportement_absent == optionAbsent.INDEX_SUPERIEUR ? 0 : -1;
		while( b != a + 1)
		{
			if( item_tab[a].getPrix() == prix ) break;
			int c = (a+b)/2;
			if( item_tab[c].getPrix() <= prix )
			{ 
				a = c;
			}
			else { b = c; }
		}

		if( item_tab[a].getPrix() == prix )
		{
			if( comportement_egalite == optionEgalite.PREMIER_TROUVE) { return a; }
			int sens = (comportement_egalite == optionEgalite.PLUS_PETIT_INDEX) ? -1 : 1;
			while( a > 0 && a < item_tab.length - 1 && 
					(item_tab[a+sens].getPrix() == item_tab[a].getPrix()))
			{
				a += sens;
			}
			return a;
		}
		else
		{
			if( comportement_absent == optionAbsent.MINUS_1 ) return -1;
			if( comportement_absent == optionAbsent.INDEX_INFERIEUR ) return a;
			return b;
		}
	}


}
