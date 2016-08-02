package edu.simplon.xml;

import java.io.FileOutputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

public class Utils {
	public static String toStringXmlInputObject(Object obj)
	{
		try {
			JAXBContext jc     = JAXBContext.newInstance("edu.simplon.xml_input");
			Marshaller marshal = jc.createMarshaller();
			
			marshal.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshal.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			
			StringWriter sWriter = new StringWriter();
			JAXBElement jx = new JAXBElement(new QName(obj.getClass().getSimpleName()), obj.getClass(), obj);
			marshal.marshal(jx, sWriter);
			return sWriter.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj.toString();
	}
}