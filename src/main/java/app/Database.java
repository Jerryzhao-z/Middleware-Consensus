package app;

import java.util.concurrent.ConcurrentHashMap;
import java.lang.NullPointerException;

public class Database
{
	ConcurrentHashMap<String, String> database;
	
	public Database()
	{
		database = new ConcurrentHashMap<String, String>();
	}

	public String insert(String key, String value)
	{
		String res = "Unknown";
		try{
			database.put(key, value);
			res = "OK";
		}catch(NullPointerException e){
			res = "Warning From Server: Key Is Null" + e.toString();
		}finally{
			return res;		
		}
	}

	public String delete(String key)
	{
		String res = "Unknown";
		try{
			database.remove(key);
			res = "OK";
		}catch(NullPointerException e){
			res = "Warning From Server: Key Is Null" + e.toString();
		}finally{
			return res;		
		}
	}

	public String get(String key)
	{
		String res = "Unknown";
		try{
			res = database.get(key);
		}catch(NullPointerException e){
			res = "Warning From Server: Key Is Null" + e.toString();
		}finally{
			return res;		
		}
	}
	
	
	public boolean containsKey(String key)
	{
		return database.containsKey(key);
	}
	
	public String remove(String key)
	{
		return database.remove(key);
	}
}
