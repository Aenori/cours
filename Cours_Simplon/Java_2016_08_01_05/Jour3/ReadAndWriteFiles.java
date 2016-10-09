package edu.simplon.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import edu.simplon.xml_input.*;

public class ReadAndWriteFiles {
	private static void writeXmlFile( String fileName, Object descInfo ) throws FileNotFoundException, JAXBException
	{
		JAXBContext jc     = JAXBContext.newInstance("edu.simplon.xml_input");
		Marshaller marshal = jc.createMarshaller();
		marshal.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshal.marshal(descInfo, new FileOutputStream( fileName ));
	}
	
	public static void writeXmlFile( String fileName, DescriptionInfo info ) throws FileNotFoundException, JAXBException
	{
		writeXmlFile( fileName, (Object)info );
	}
	
	public static void writeXmlFile( String fileName, AllInfo info ) throws FileNotFoundException, JAXBException
	{
		writeXmlFile( fileName, (Object)info );
	}
	
	public static void writeXmlFile( String fileName, PriceInfo info ) throws FileNotFoundException, JAXBException
	{
		writeXmlFile( fileName, (Object)info );
	}
	
	private static Object readXmlFile( String fileName ) throws FileNotFoundException, JAXBException
	{
		JAXBContext jc     = JAXBContext.newInstance("edu.simplon.xml_input");
		Unmarshaller unm = jc.createUnmarshaller();
		return unm.unmarshal( new File(fileName) );
	}
	
	public static DescriptionInfo readXmlFileDescriptionInfo( String fileName ) throws FileNotFoundException, JAXBException
	{
		return (DescriptionInfo)readXmlFile( fileName ); 
	}
	
	public static AllInfo readXmlFileAllInfo( String fileName ) throws FileNotFoundException, JAXBException
	{
		return (AllInfo)readXmlFile( fileName );
	}
	
	public static PriceInfo readXmlFilePriceInfo( String fileName ) throws FileNotFoundException, JAXBException
	{
		return (PriceInfo)readXmlFile( fileName );
	}
}
