package edu.simplon.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.xml.sax.SAXException;

import edu.simplon.xml_input.DescriptionInfo;
import edu.simplon.xml_input.ItemDescType;
import edu.simplon.xml_input.PriceInfo;

import junit.framework.TestCase;


public class TestUnit extends TestCase {
	public static boolean areEquals( ItemDescType o1, ItemDescType o2 )
	{
		return ( o1.getTitle().equals( o2.getTitle() ) && 
				 o1.getBrand().equals( o2.getBrand() ) &&
				 o1.getId().equals( o2.getId() ) &&
				 o1.getCategories().equals( o2.getCategories() ) );
	}			
	
	public static boolean areEquals( DescriptionInfo o1, DescriptionInfo o2 )
	{
		boolean result = true;
		if( o1.getItems().getItem().size() !=  o2.getItems().getItem().size() )
		{
			System.out.println( "La taille des structures XML est différente : " + 
						o1.getItems().getItem().size() + " != " + o2.getItems().getItem().size());
			result = false;
		}
		for( int i = 0; i < o1.getItems().getItem().size() && i < o2.getItems().getItem().size(); ++i )
		{
			if( !areEquals( o1.getItems().getItem().get(i),  o2.getItems().getItem().get(i)) )
			{
				System.out.println( "Les items d'indice " + i + " sont différents !");
				System.out.println( Utils.toStringXmlInputObject(o1.getItems().getItem().get(i)));
				System.out.println( Utils.toStringXmlInputObject(o2.getItems().getItem().get(i)));
				return false;
			}
		}
		return result;
	}
	
	public void testExercice02() throws JAXBException, IOException, SAXException
	{
		String testFileName = "data_xml/test_result/exo02_desc_info_extraits.xml";
		Utils.extrairePartieFichier( "data_xml/description_info_1000.xml", testFileName, 10 );
		String refFileName = "data_xml/test/exo02_desc_info_extraits_ref_10.xml";
		
		assertTrue( 
				TestUnit.areEquals( 
						ReadAndWriteFiles.readXmlFileDescriptionInfo( testFileName ),
						ReadAndWriteFiles.readXmlFileDescriptionInfo( refFileName )
						)
		);
		
		Utils.extrairePartieFichier( "data_xml/description_info_1000.xml", testFileName, 9 );
		refFileName = "data_xml/test/exo02_desc_info_extraits_ref_09.xml";
		
		assertTrue( 
				TestUnit.areEquals( 
						ReadAndWriteFiles.readXmlFileDescriptionInfo( testFileName ),
						ReadAndWriteFiles.readXmlFileDescriptionInfo( refFileName )
						)
		);
	}

	private void assertNonXmlListEquals( List ref, List testResult )
	{
		assertEquals( "Les vecteurs ne sont pas de même taille : " + ref.size() + " != " + testResult.size(), 
	                  ref.size(), testResult.size() );
		for(int i = 0; i < ref.size(); ++i)
		{ 
			assertEquals( "L'élément " + i + " du vecteur diffère : " 
					+ ref.get(i) + " != " + testResult.get(i), ref.get(i), testResult.get(i));
		}
	}
	
	public void testExercice03() throws FileNotFoundException, JAXBException, SAXException
	{
		
		DescriptionInfo dInfo = ReadAndWriteFiles.readXmlFileDescriptionInfo("data_xml/test/exo03_desc_info_04.xml");
		List<Long> ma_liste =  Utils.extraireListeId( dInfo );
		List<Long> ref = new ArrayList<Long>();
		for(int i : new int[]{ 1, 2, 3, 1000})
		{
			ref.add(Long.valueOf(i));
		}
		assertNonXmlListEquals( ref, ma_liste );
		
		dInfo = ReadAndWriteFiles.readXmlFileDescriptionInfo("data_xml/test/exo03_desc_info_05.xml");
		ma_liste =  Utils.extraireListeId( dInfo );
		ref = new ArrayList<Long>();
		for(int i : new int[]{ 37214, 32069, 31909, 32034, 31852 })
		{
			ref.add(Long.valueOf(i));
		}
		assertNonXmlListEquals( ref, ma_liste );
		
	}

	public void testExercice04() throws FileNotFoundException, JAXBException, SAXException
	{
		PriceInfo pInfo = ReadAndWriteFiles.readXmlFilePriceInfo("data_xml/test/exo04_price_info.xml");
		assertEquals( Utils.moyennePrix(pInfo), 42.0, 0.001 );
	}
	
	public void testExercice04_2() throws FileNotFoundException, JAXBException, SAXException
	{
		
		DescriptionInfo dInfo = ReadAndWriteFiles.readXmlFileDescriptionInfo("data_xml/description_info_1000.xml");
		assertEquals( 4  , Utils.compterMarque(dInfo, "BubuBibi") );
		assertEquals( 346, Utils.compterMarque(dInfo, "") );
	}
	
	public void testExercice05() throws FileNotFoundException, JAXBException, SAXException
	{
		DescriptionInfo dInfo = ReadAndWriteFiles.readXmlFileDescriptionInfo( "data_xml/test/exo05_desc_info.xml" );

		for(int i = 0; i < dInfo.getItems().getItem().size() ; ++i )
		{
		    for(int j = i; j < dInfo.getItems().getItem().size() ; ++j )
		    {
		        boolean areItemEquals = areEquals( 
		            dInfo.getItems().getItem().get(i), 
		            dInfo.getItems().getItem().get(j) );
		        if( i == j || (i == 0 && j == dInfo.getItems().getItem().size() - 1) )
		        {
		            assertTrue( areItemEquals );
		        }
		        else
		        {
		            assertFalse( areItemEquals );
		        }
		    }
		}
	}
}
