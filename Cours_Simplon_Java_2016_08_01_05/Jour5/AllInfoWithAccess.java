package edu.simplon.xml;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import edu.simplon.xml_input.AllInfo;
import edu.simplon.xml_input.CommandType;
import edu.simplon.xml_input.ItemAllType;
import edu.simplon.xml_input.ItemsNodeAllType;

public class AllInfoWithAccess extends AllInfo {
	private Long                  recommandationProductId;
	private Map<Long,ItemAllType> mapIdToItems;
	private Map<String,ItemAllType> mapStringToItems;
	private List<Set<Long>>       commandeList;
	
	public AllInfoWithAccess( AllInfo allInfo )
	{
		super();
		setItems( allInfo.getItems() );
		this.setPreviousCommands(allInfo.getPreviousCommands());
		this.setRequest(allInfo.getRequest());
	}
	
	public Map<Long,ItemAllType> getMapIdToItems()
	{
		if( mapIdToItems == null )
		{
			mapIdToItems = new TreeMap<Long,ItemAllType>();
			for(ItemAllType item : getItems().getItem())
			{
				mapIdToItems.put(item.getId().longValue(), item);
			}
			
			for(Map.Entry<Long, ItemAllType> e : mapIdToItems. .entrySet())
			{
				System.out.println( e.getKey() );
				System.out.println( e.getValue() );
			}
			
		}
		return mapIdToItems;
	}
	
	public List<ItemAllType> getItemList()
	{
		return getItems().getItem();
	}
	
	public List<String> getAllBrands()
	{
		List<String> result = new ArrayList<String>();
		for( ItemAllType item: getItems().getItem())
		{
			result.add( item.getBrand() );
		}
		return result;
	}
	
	public List<String> getAllCategories()
	{
		List<String> result = new ArrayList<String>();
		for( ItemAllType item: getItems().getItem())
		{
			result.add( item.getCategories() );
		}
		return result;
	}
	
	public List<String> getAllTitle()
	{
		List<String> result = new ArrayList<String>();
		for( ItemAllType item: getItems().getItem())
		{
			result.add( item.getTitle() );
		}
		return result;
	}
	
	public ItemAllType getItemFromId( Long id )
	{
		return getMapIdToItems().get(id);
	}

	public Long getRecommandationProductId()
	{
		if(recommandationProductId == null)
		{
			recommandationProductId = this.getRequest().getRecommandation().getId().get(0).longValue();
		}
		return recommandationProductId;
	}
	
	public List< Set<Long> > getPastCommands()
	{
		List< Set<Long> > liste = new ArrayList< Set<Long> >();
		
		for(CommandType command : this.getPreviousCommands().getCommand())
		{
			Set<Long> set = new HashSet();
			for(BigInteger bigInt : command.getId())
			{
				set.add(bigInt.longValue());
			}
			liste.add( set );
		}
		
		return liste;
	}
}