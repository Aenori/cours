package edu.simplon.testunitaire;

import java.lang.reflect.Method;

import junit.framework.TestCase;

public abstract class TestCaseWithIntrospection extends TestCase {
	private String className;
	private Class testedClass;
	
	protected String getClassName() {
		return className;
	}

	protected Class getTestedClass() {
		return testedClass;
	}
	
	protected void assertDoubleArrayEquals( double[] arg1, double[] arg2 )
	{
		assertEquals( "Les vecteurs ne sont pas de même taille : "
	                  + arg1.length + " != " + arg2.length, 
	                  arg1.length, 
	                  arg2.length );
		for(int i = 0; i < arg1.length; ++i)
		{ 
			assertEquals( "L'élément " + i + " du vecteur diffère : " 
					+ arg1[i] + " != " + arg2[i], arg1[i], arg2[i], 0.001);
		}
	}

	private static String printArguments(Class[] arguments)
	{
		StringBuilder s = new StringBuilder();
		boolean first = true;
		for(Class c: arguments)
		{
			if(first)
			{
				first = false;
			}
			else
			{
				s.append(", ");
			}
			s.append(c.getName());
		}
		return s.toString();
	}
	
	private String checkAllMethod(String methodName) {
		StringBuilder s = new StringBuilder();
		
		for(Method m : testedClass.getMethods() )
		{
			if( m.getName().equals(methodName) )
			{
				s.append("Il existe une méthode " + methodName + " avec les arguments : [" 
						+ printArguments(m.getParameterTypes()) + "]" );
				s.append(System.lineSeparator());
			}
		}
		return s.toString();
	}
	
	public TestCaseWithIntrospection(String className)
	{
		super();
		this.className = className;
		try
		{
			testedClass = Class.forName(className);
		}
		catch(ClassNotFoundException e)
		{
			testedClass = null;
		}
	}
	
	public Method getMethod(String methodName, Class[] arguments)
	{
		assertNotSame( "La classe " + className + " n'a pas été trouvée !", this.testedClass, null);
		try
		{
			return this.testedClass.getMethod(methodName, arguments);
		}
		catch(NoSuchMethodException e)
		{
			String msg = "La méthode " + methodName + " n'est pas défini pour les arguments : [" + printArguments(arguments) + "]";
			msg += System.lineSeparator();
			msg += checkAllMethod(methodName);
			this.fail(msg);
			
		}
		return null;
	}

	
	
}
