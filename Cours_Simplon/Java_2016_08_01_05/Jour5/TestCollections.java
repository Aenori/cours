package edu.simplon.xml;

import java.util.*;

import edu.simplon.xml_input.ItemAllType;

public class TestCollections {

	public static void sortedRetains(List<Integer> liste1, List<Integer> liste2)
	{
		int itPos1 = 0, itPos2 = 0;
		List<Integer> retains = new ArrayList<Integer>();
		while( (itPos1 < liste1.size()) && (itPos2 < liste2.size()) )
		{
			if( liste1.get(itPos1).equals( liste2.get(itPos2) ) )
			{
				retains.add(liste1.get(itPos1));
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
		liste1.clear();
		liste1.addAll(retains);
	}

	public static void testRetains()
	{
		List<Integer> liste1 = new ArrayList();
		List<Integer> liste2 = new ArrayList();
		List<Integer> liste3 = new ArrayList();
		List<Integer> liste4 = new ArrayList();
		Set<Integer>  set1 = new TreeSet();
		Set<Integer>  set2 = new TreeSet();
		Set<Integer>  set3 = new HashSet();
		Set<Integer>  set4 = new HashSet();

		int value1 = 0;
		int value2 = 0;

		for(int i = 0; i < 50000; ++i)
		{
			liste1.add(value1);
			liste2.add(value2);
			liste3.add(value1);
			liste4.add(value2);
			set1.add(value1);
			set2.add(value2);
			set3.add(value1);
			set4.add(value2);
			value1 += 2;
			value2 += 3;
		}

		Collections.shuffle(liste1);
		Collections.shuffle(liste2);

		long debut = System.currentTimeMillis();
		Collections.sort(liste1);
		Collections.sort(liste2);
		sortedRetains(liste1, liste2);
		long time = System.currentTimeMillis() - debut;
		System.out.println( "Retains + tri : " + liste1.size() + " => " + time);

		debut = System.currentTimeMillis();
		set3.retainAll(set4);
		time = System.currentTimeMillis() - debut;
		System.out.println( "HashSet : " + set3.size() + " => " + time);

		debut = System.currentTimeMillis();
		set1.retainAll(set2);
		time = System.currentTimeMillis() - debut;
		System.out.println( "TreeSet : " + set1.size() + " => " + time);


		debut = System.currentTimeMillis();
		liste3.retainAll(liste4);
		time = System.currentTimeMillis() - debut;
		System.out.println( "List : " + liste3.size() + " => " + time);
	}

	public static void iterateOverSet()
	{
		Set<Integer>  set1 = new TreeSet<Integer>();
		Set<Integer>  set2 = new HashSet<Integer>();
		List<Integer>  set3 = new ArrayList<Integer>();
		
		for(int i = 40; i >= 0; i-=10)
		{
			set1.add(i);
			set2.add(i);
			set3.add(i);
		}
		for(int i = 200; i >= 0; i-=10)
		{
			set1.add(i);
			set2.add(i);
			set3.add(i);
		}
		
		for(Integer i: set1)
		{
			System.out.println(i);
		}
		System.out.println("===============");
		for(Integer i: set2)
		{
			System.out.println(i);
		}
		System.out.println("===============");
		for(Integer i: set3)
		{
			System.out.println(i);
		}
	}

	public static void exempleSousListe()
	{
		List<Integer> liste = new ArrayList<Integer>();
		for(int i = 9; i >= 0; --i)
		{
			liste.add(i);
		}
		Collections.sort(liste.subList(0, 4));
		for(Integer i: liste)
		{
			System.out.print(i + ", ");
		}
		System.out.println();
	}
	
	public static List<Long> supprimerDuplicataQuiNeMarchePas(List<Long> listeEntree)
	{
		List<Long> liste = new ArrayList();
		liste.addAll(listeEntree);
		for(int i = 0; i < liste.size() - 1; ++i)
		{
			if( liste.get(i).equals( liste.get(i+1) ) )
			{
				liste.remove(i);
			}
		}
		return liste;
	}
}
