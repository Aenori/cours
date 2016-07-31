package edu.simplon.data;

import java.util.Set;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


public class Item implements Comparable {
	private int id;
	private String nom;
	private double prix;
	
	public static Item readFromXmlNode( Node node )
	{
		Item item = new Item();
		NamedNodeMap attrMap = node.getAttributes();
		item.setId( Integer.parseInt( attrMap.getNamedItem("id").getNodeValue() ) );
		item.setNom( attrMap.getNamedItem("nom").getNodeValue() );
		item.setPrix( Double.parseDouble( attrMap.getNamedItem("prix").getNodeValue() ) );
		return item;
	}
	
	public Item()
	{
		
	}
	
	public Item(int id)
	{
		this();
		this.id = id;
	}
	
	public Item(int id, String nom)
	{
		this(id);
		this.nom = nom;
	}
	
	public Item(int id, String nom, double prix)
	{
		this(id, nom);
		this.prix = prix;
	}
	
	/*
	 * Region for getter and setter
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	@Override
	public int compareTo(Object arg0) {
		if( !(arg0 instanceof Item ) )
		{
			System.err.println( "Item can only be compared to other item" );
			return 0;
		}
		return (int) (this.prix - ((Item)arg0).prix);
	}
	
	public String toString()
	{
		return "id : " + this.id + ", nom : " + this.nom + ", prix : " + this.prix; 
	}
}
