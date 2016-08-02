package edu.simplon.testunitaire;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import edu.simplon.data.DataJour1;
import edu.simplon.data.Item;



public class TestItemFunctionSimplonJour1 extends TestCaseWithIntrospection {
	
	public TestItemFunctionSimplonJour1()
	{
		super("edu.simplon.datascience.FonctionRecherche");
	}
	
	public void setUp()
	{
		DataJour1.getItemTab100000();
		DataJour1.sort_all(false);
	}
	
	// Test pour l'existence de la classe
	public void testClasseExiste()
	{
		assertNotNull("La classe " + this.getClassName() + " n'existe pas", this.getTestedClass());
	}
	
	// Test exercice 1
	public void testExercice1_AfficheTousLesElements()
	{
		Method m = this.getMethod("affichez_tous_les_elements", new Class[]{ Item[].class });
		try {
			m.invoke( null, new Object[]{ DataJour1.getItemTab10() } );
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			this.fail("L'appel de la méthode affichez_tous_les_elements a échoué");
		}
	}
	
	// test exercice 2
	public void testExercice2_ToString(){
		assertEquals( (new Item(0,"Truc",5)).toString()     , "id : 0, nom : Truc, prix : 5.0");
		assertEquals( (new Item(1,"Chouette",10)).toString(), "id : 1, nom : Chouette, prix : 10.0");
		Item[] tab = DataJour1.getItemTab10();
		assertEquals( tab[0].toString(), "id : 31852, nom : Girls Ballet Tutu Zebra Hot Pink, prix : 3.17" );
		assertEquals( tab[1].toString(), "id : 31909, nom : Girls Ballet Tutu Neon Pink, prix : 7.0");
		assertEquals( tab[2].toString(), "id : 32034, nom : Adult Ballet Tutu Yellow, prix : 7.87");
	}
	
	public void testExercice3() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Method m = this.getMethod("prix_min_et_max_liste", new Class[]{ Item[].class });
		double[] result = (double[])(m.invoke(null, new Object[]{ DataJour1.getItemTab100() } ));
		assertDoubleArrayEquals( new double[]{2.96,  62.51}, result);
		result = (double[])(m.invoke(null, new Object[]{ DataJour1.getItemTab1000() } ));
		assertDoubleArrayEquals( new double[]{   0, 275   }, result);
		result = (double[])(m.invoke(null, new Object[]{ DataJour1.getItemTab100000() } ));
		assertDoubleArrayEquals( new double[]{   0, 992.75}, result);
		result = (double[])(m.invoke(null, new Object[]{ DataJour1.getItemTab1000000() } ));
		assertDoubleArrayEquals( new double[]{   0, 999}, result);
	}
	
	@SuppressWarnings("unchecked")
	public void testExercice4() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Method m = this.getMethod("extraction_dans_une_fourchette_de_prix", new Class[]{ Item[].class, double.class, double.class });
		List<Item> result = (List<Item>)(m.invoke(null, new Object[]{ DataJour1.getItemTab100000(), 5, 20 } ));
		assertEquals( 50634, result.size() );
		result = (List<Item>)(m.invoke(null, new Object[]{ DataJour1.getItemTab100000(), 1, 200 } ));
		assertEquals( 98316, result.size() );
		result = (List<Item>)(m.invoke(null, new Object[]{ DataJour1.getItemTab100000(), 5, 6 } ));
		assertEquals( 3781, result.size() );
		result = (List<Item>)(m.invoke(null, new Object[]{ DataJour1.getItemTab100000(), 2, 50 } ));
		assertEquals( 83730, result.size() );
		result = (List<Item>)(m.invoke(null, new Object[]{ DataJour1.getItemTab100000(), 0, 1000 } ));
		assertEquals( 100000, result.size() );
	}
	
	public void testExercice5() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Method m = this.getMethod("possede_deux_prix_identiques", new Class[]{ Item[].class });
		boolean result;
		result = (boolean)(m.invoke(null, new Object[]{ DataJour1.getItemTab10() } ));
		assertFalse( result );
		result = (boolean)(m.invoke(null, new Object[]{ DataJour1.getItemTab100() } ));
		assertTrue( result );
		Item[] test = new Item[200];
		
		for(int i = 0; i < 200 ; ++i)
		{
			for(Item item: DataJour1.getItemTab1000000() )
			{
				if( (item.getPrix() >= i) && (item.getPrix() < i + 1) )
				{
					test[i] = item;
					break;
				}
			}
		}

		result = (boolean)(m.invoke(null, new Object[]{ test } ));
		assertFalse( result );
	}

	public void testExercice6() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Method m = this.getMethod("trouvez_items_avec_prix_identiques", new Class[]{ Item[].class });
		List<Item> result = (List<Item>)(m.invoke(null, new Object[]{ DataJour1.getItemTab10() } ));
		assertEquals( 0, result.size() );
		result = (List<Item>)(m.invoke(null, new Object[]{ DataJour1.getItemTab100() } ));
		assertEquals( 24, result.size() );
		result = (List<Item>)(m.invoke(null, new Object[]{ DataJour1.getItemTab1000() } ));
		assertEquals( 571, result.size() );
	}

	public void testExercice7() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		DataJour1.sort_all(false);
		Method m = this.getMethod("recherche_dichotomique_by_id", new Class[]{ Item[].class, int.class });
		assertEquals( DataJour1.getItemTab1000000()[100], 
				    (Item)(m.invoke(null, new Object[]{ DataJour1.getItemTab1000000(),  2115050 } ) ) );
		assertEquals( DataJour1.getItemTab1000000()[500100], 
			    (Item)(m.invoke(null, new Object[]{ DataJour1.getItemTab1000000(),  814407668 } ) ) );
		assertEquals( DataJour1.getItemTab1000000()[999998], 
			    (Item)(m.invoke(null, new Object[]{ DataJour1.getItemTab1000000(),  1585282669 } ) ) );
	}

	public void testExercice8() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		DataJour1.sort_all(true);
		Method m = this.getMethod("prix_min_et_max_liste_trie", new Class[]{ Item[].class });
		double[] result = (double[])(m.invoke(null, new Object[]{ DataJour1.getItemTab100() } ));
		assertDoubleArrayEquals( new double[]{2.96,  62.51}, result);
		result = (double[])(m.invoke(null, new Object[]{ DataJour1.getItemTab1000() } ));
		assertDoubleArrayEquals( new double[]{   0, 275   }, result);
		result = (double[])(m.invoke(null, new Object[]{ DataJour1.getItemTab100000() } ));
		assertDoubleArrayEquals( new double[]{   0, 992.75}, result);
		result = (double[])(m.invoke(null, new Object[]{ DataJour1.getItemTab1000000() } ));
		assertDoubleArrayEquals( new double[]{   0, 999}, result);
	}
	
	@SuppressWarnings("unchecked")
	public void testExercice9() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		DataJour1.sort_all(true);
		Method m = this.getMethod("extraction_dans_une_fourchette_de_prix_trie", new Class[]{ Item[].class, double.class, double.class });
		List<Item> result = (List<Item>)(m.invoke(null, new Object[]{ DataJour1.getItemTab100000(), 5, 20 } ));
		assertEquals( 50634, result.size() );
		result = (List<Item>)(m.invoke(null, new Object[]{ DataJour1.getItemTab100000(), 1, 200 } ));
		assertEquals( 98316, result.size() );
		result = (List<Item>)(m.invoke(null, new Object[]{ DataJour1.getItemTab100000(), 5, 6 } ));
		assertEquals( 3781, result.size() );
		result = (List<Item>)(m.invoke(null, new Object[]{ DataJour1.getItemTab100000(), 2, 50 } ));
		assertEquals( 83730, result.size() );
		result = (List<Item>)(m.invoke(null, new Object[]{ DataJour1.getItemTab100000(), 0, 1000 } ));
		assertEquals( 100000, result.size() );
	}
	
	public void testExercice10() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		DataJour1.sort_all(true);
		Method m = this.getMethod("possede_deux_prix_identiques_trie", new Class[]{ Item[].class });
		boolean result;
		result = (boolean)(m.invoke(null, new Object[]{ DataJour1.getItemTab10() } ));
		assertFalse( result );
		result = (boolean)(m.invoke(null, new Object[]{ DataJour1.getItemTab100() } ));
		assertTrue( result );
		Item[] test = new Item[200];
		
		for(int i = 0; i < 200 ; ++i)
		{
			for(Item item: DataJour1.getItemTab1000000() )
			{
				if( (item.getPrix() >= i) && (item.getPrix() < i + 1) )
				{
					test[i] = item;
					break;
				}
			}
		}

		result = (boolean)(m.invoke(null, new Object[]{ test } ));
		assertFalse( result );
	}

	public void testExercice11() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		DataJour1.sort_all(true);
		Method m = this.getMethod("trouvez_items_avec_prix_identiques_trie", new Class[]{ Item[].class });
		List<Item> result = (List<Item>)(m.invoke(null, new Object[]{ DataJour1.getItemTab10() } ));
		assertEquals( 0, result.size() );
		result = (List<Item>)(m.invoke(null, new Object[]{ DataJour1.getItemTab100() } ));
		assertEquals( 24, result.size() );
		result = (List<Item>)(m.invoke(null, new Object[]{ DataJour1.getItemTab1000() } ));
		assertEquals( 571, result.size() );
	}
}
