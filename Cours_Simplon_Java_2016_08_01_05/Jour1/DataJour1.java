package edu.simplon.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class DataJour1 {
	private static final String file_name = "/home/nicolas/workspace_simplon/Simplon_data_science_01_aout/metadata_1000000.json";

	private static Item[] item_tab_1000000;
	private static Item[] item_tab_100000;
	private static Item[] item_tab_10000;
	private static Item[] item_tab_1000;
	private static Item[] item_tab_100;
	private static Item[] item_tab_10;
	
	public static Item[] getItemTab10()
	{
		if( item_tab_10 == null ) item_tab_10 = copie_premiers_elements( 10 );
		return item_tab_10;
	}
	
	public static Item[] getItemTab100()
	{
		if( item_tab_100 == null ) item_tab_100 = copie_premiers_elements( 100 );
		return item_tab_100;
	}

	public static Item[] getItemTab1000()
	{
		if( item_tab_1000 == null ) item_tab_1000 = copie_premiers_elements( 1000 );
		return item_tab_1000;
	}

	public static Item[] getItemTab10000()
	{
		if( item_tab_10000 == null ) item_tab_10000 = copie_premiers_elements( 10000 );
		return item_tab_10000;
	}

	public static Item[] getItemTab100000()
	{
		if( item_tab_100000 == null ) item_tab_100000 = copie_premiers_elements( 100000 );
		return item_tab_100000;
	}
	
	private static Item[] copie_premiers_elements( int nbElt )
	{
		Item[] tab = getItemTab1000000();
		Item[] resultat_tab = new Item[ nbElt ];
		for(int i = 0; i < nbElt ; ++i )
		{
			resultat_tab[i] = tab[i];
		}
		return resultat_tab;
	}

	private static class ItemPriceComparator implements Comparator<Item> {
	    @Override
	    public int compare(Item a, Item b) {
	        return a.getPrix() < b.getPrix() ? -1 : a.getPrix() > b.getPrix() ? 1 : 0;
	    }
	}
	
	private static class ItemIdComparator implements Comparator<Item> {
	    @Override
	    public int compare(Item a, Item b) {
	        return a.getId() < b.getId() ? -1 : a.getId() > b.getId() ? 1 : 0;
	    }
	}
	
	public static void sort_all(boolean by_price_if_not_by_id)
	{
		Comparator<Item> comp = by_price_if_not_by_id ? new ItemPriceComparator() : new ItemIdComparator();
		Arrays.sort(getItemTab10(), comp);
		Arrays.sort(getItemTab100(), comp);
		Arrays.sort(getItemTab1000(), comp);
		Arrays.sort(getItemTab10000(), comp);
		Arrays.sort(getItemTab100000(), comp);
		Arrays.sort(getItemTab1000000(), comp);
	}
	
	public static Item[] getItemTab1000000()
	{
		if( item_tab_1000000 != null ) return item_tab_1000000;
		BufferedReader br = null;
		int i = 0;
		String line = "";
		try
		{
			item_tab_1000000 = new Item[1000000];
			br = new BufferedReader(new FileReader(file_name));
			String[] lineParts;
			for(i = 0; i < 1000000; ++i)
			{
				line = br.readLine();
				lineParts = line.split(";");
				item_tab_1000000[i] = new Item( 
						Integer.parseInt(lineParts[0]),
						lineParts[1],
						Double.parseDouble(lineParts[2]) );
			}
		} catch (Exception e) {
			System.err.println("On line : " + i + " => " + line);
			e.printStackTrace();
		} 
		finally {
			try {
				br.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return item_tab_1000000;
	}
}