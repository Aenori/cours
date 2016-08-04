package edu.simplon.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edu.simplon.xml_input.ItemAllType;

public class Algorithms {

	// Exercice 1 : tri
	public static void trierEntiers(List<Long> liste)
	{
		Collections.sort(liste);
	}

	private static class ArrayComparator implements Comparator<Long[]> {
		@Override
		public int compare(Long[] a, Long[] b) {
			return a[0].compareTo( b[0] );
		}
	}

	private static class ItemIdComparator implements Comparator<ItemAllType> {
		@Override
		public int compare(ItemAllType a, ItemAllType b) {
			return a.getId().compareTo( b.getId() );
		}
	}

	// Exercice 2 : tri
	public static void trierItems(List<ItemAllType> liste)
	{
		Collections.sort(liste, new ItemIdComparator());
	}

	// Exercice 3 : suppression de duplicata
	public static List<Long> supprimerDuplicata(List<Long> liste)
	{
		List<Long> result = new ArrayList<Long>();
		result.add(liste.get(0));

		for(int i = 1; i < liste.size(); ++i)
		{
			if( !liste.get(i-1).equals( liste.get(i) ) )
			{
				result.add( liste.get(i) );
			}
		}
		return result;
	}

	// Exercice 4 : garder les éléments communs
	public static List<Long> _garderLesElementsCommuns(List<Long> liste1, List<Long> liste2)
	{
		int itPos1 = 0, itPos2 = 0;
		List<Long> result = new ArrayList<Long>();
		while( (itPos1 < liste1.size()) && (itPos2 < liste2.size()) )
		{
			if( liste1.get(itPos1).equals( liste2.get(itPos2) ) )
			{
				result.add(liste1.get(itPos1));
				++itPos1;
				++itPos2;
			}
			else if( liste1.get(itPos1) < liste2.get(itPos2) )
			{
				++itPos1;
			}
			else
			{
				++itPos2;
			}
		}
		return result;
	}

	public static List<Long> garderLesElementsCommuns(List<Long> liste1, List<Long> liste2)
	{
		Set<Long> set1 = new TreeSet<Long>(liste1);
		Set<Long> set2 = new TreeSet<Long>(liste2);
		set1.retainAll(set2);
		List<Long> listeResult = new ArrayList<Long>(set1);
		return new ArrayList<Long>(set1);
	}

	// Exercice 5 : fusionnerListeTriees
	public static List<Long> _fusionnerListeTriees(List<Long> liste1, List<Long> liste2)
	{
		int itPos2 = 0;
		List<Long> result = new ArrayList<Long>();

		for(int itPos1 = 0; itPos1 < liste1.size(); ++itPos1 )
		{
			for( ; itPos2 < liste2.size() && liste2.get(itPos2) < liste1.get(itPos1); ++itPos2 )
			{
				result.add(liste2.get(itPos2));
			}
			result.add(liste1.get(itPos1));
		}
		
		for(; itPos2 < liste2.size(); ++itPos2 ) result.add(liste2.get(itPos2));
		return result;
	}

	// Exercice 5 : fusionnerListeTriees
	public static List<Long> fusionnerListeTriees(List<Long> liste1, List<Long> liste2)
	{
		int itPos1 = 0, itPos2 = 0;
		List<Long> result = new ArrayList<Long>();

		while( (itPos1 < liste1.size()) || (itPos2 < liste2.size()) )
		{
			if( (itPos1 != liste1.size()) && ( itPos2 == liste2.size() || liste1.get(itPos1) < liste2.get(itPos2)) )
			{
				result.add(liste1.get(itPos1++));
			}
			else
			{
				result.add(liste2.get(itPos2++));
			}
		}

		return result;
	}

}














