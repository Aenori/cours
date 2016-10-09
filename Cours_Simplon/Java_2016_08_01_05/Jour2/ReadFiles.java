package edu.simplon.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import edu.simplon.xml_description.DescriptionInfo;
import edu.simplon.xml_price.PriceInfo;

public class ReadFiles {
	private static final String xml_files_directory = "/home/files/Documents/Professionnel/Cours/Simplon/Java_01_05_Aout/data";  
	public JAXBContext getJc_desc() {
		return jc_desc;
	}
	public JAXBContext getJc_price_info() {
		return jc_price_info;
	}
	public Unmarshaller getUnmarshal_desc() {
		return unmarshal_desc;
	}
	public Unmarshaller getUnmarshal_price_info() {
		return unmarshal_price_info;
	}
	public Marshaller getMarshal_desc() {
		return marshal_desc;
	}
	public Marshaller getMarshal_price_info() {
		return marshal_price_info;
	}

	private JAXBContext  jc_desc ;
	private JAXBContext  jc_price_info ;
	private Unmarshaller unmarshal_desc ;
	private Unmarshaller unmarshal_price_info ;
	private Marshaller   marshal_desc ;
	private Marshaller   marshal_price_info ;
	
	private static ReadFiles instance = null;
	
	public static ReadFiles getInstance()
	{
		if( instance == null ) instance = new ReadFiles();
		return instance;
	}
	/*
	 * @param args
	 */
	public ReadFiles()
	{
		try {
			jc_desc        = JAXBContext.newInstance("edu.simplon.xml_description");
			unmarshal_desc = jc_desc.createUnmarshaller();
			marshal_desc   = jc_desc.createMarshaller();
			marshal_desc.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			
			jc_price_info  = JAXBContext.newInstance("edu.simplon.xml_price");
			unmarshal_price_info = jc_price_info.createUnmarshaller();
			marshal_price_info   = jc_price_info.createMarshaller();
			marshal_price_info.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		} catch (JAXBException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void writeXmlFile( String fileName, DescriptionInfo descInfo ) throws FileNotFoundException, JAXBException
	{
		OutputStream os = new FileOutputStream( fileName );
		getInstance().getMarshal_desc().marshal( descInfo, os );
	}
	
	public static void writeXmlFile( String fileName, PriceInfo descInfo ) throws FileNotFoundException, JAXBException
	{
		OutputStream os = new FileOutputStream( fileName );
		getInstance().getMarshal_price_info().marshal( descInfo, os );
	}
	
	public static DescriptionInfo readXmlFileDescriptionInfo( String fileName ) throws FileNotFoundException, JAXBException
	{
		return (DescriptionInfo) getInstance().getUnmarshal_desc().unmarshal( new File(fileName) );
	}
	
	public static PriceInfo readXmlFilePriceInfo( String fileName ) throws FileNotFoundException, JAXBException
	{
		return (PriceInfo) getInstance().getUnmarshal_price_info().unmarshal( new File(fileName) );
	}
	
	public static void main(String[] args) {
	}

}
