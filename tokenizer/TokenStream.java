/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.tokenizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class represents a stream of tokens as the name suggests.
 * It wraps the token stream and provides utility methods to manipulate it
 * @author nikhillo
 *
 */
public class TokenStream implements Iterator<String>{
	
	//StringBuilder Token;
	ArrayList<String> tokenList;
	//LinkedList<String> tokenList;
     int itterator=0;
	//Map<String,Integer> wordsWithCount = new HashMap<String, Integer>();
	
	/**
	 * Default constructor
	 * @param bldr: THe stringbuilder to seed the stream
	 */
	public TokenStream(StringBuilder bldr) {
		
		//Token=new StringBuilder();
		//Token=bldr;
		//-------------ArrayList----------
		if(null != bldr){
			tokenList= new ArrayList<String>();
			tokenList.add(bldr.toString());
		}
		//------------LinkedList------------
		//tokenList= new LinkedList<>();
		//tokenList.add(bldr.toString());
		//Sitterator++;
		//TODO: Implement this method
	}
	
	/**
	 * Overloaded constructor
	 * @param bldr: THe stringbuilder to seed the stream
	 */
	public TokenStream(String string) {
		
		//Token=new StringBuilder();
		//Token.append(string);
		//-------------ArrayList----------
		if(null != string && !string.isEmpty()){
			tokenList= new ArrayList<String>();
			tokenList.add(string);
		}
		//------------LinkedList------------
		//tokenList= new LinkedList<>();
		//tokenList.add(string);
		//itterator++;
		//TODO: Implement this method
	}
	
	/**
	 * Method to append tokens to the stream
	 * @param tokens: The tokens to be appended
	 */
	public void append(String... tokens) {
		
		if(null!=tokenList)
			if(tokens!=null)
			{
			for (String word : tokens) 
		    {
				if(word!=null&&!word.isEmpty())
				{
					tokenList.add(word);
					//tokenList.add(word);
					//itterator++;
				}
				
		    }
			}
		//TODO: Implement this method
	}
	
	/**
	 * Method to retrieve a map of token to count mapping
	 * This map should contain the unique set of tokens as keys
	 * The values should be the number of occurrences of the token in the given stream
	 * @return The map as described above, no restrictions on ordering applicable
	 */
	public Map<String, Integer> getTokenMap() {
		
		if(null!=tokenList){
			
		
		String stream= tokenList.toString();
		//String stream= tokenList.toString();
		stream = stream.substring(1,stream.length() - 1);
		if(stream!=null&&!stream.isEmpty()&&!stream.equals("null")&&!stream.equals(""))
		{
	    Map<String,Integer> wordsWithCount = new HashMap<String, Integer>();
		//String[] words = stream.split(" ");
		
		String[] words = stream.split(", ");
	    for (String word : words)
	    {
	    	if(word!=null||word!="")
	    	{
	    		if(wordsWithCount.containsKey(word))
	    		{
	    			wordsWithCount.put(word, wordsWithCount.get(word)+1);
	    		}
	    		else
	    		{
	    			wordsWithCount.put(word, 1);
	    		}
	    	}
	    
	    }
	    
	    return wordsWithCount;
		}
		}
		return null;
		
	}
	
	/**
	 * Method to get the underlying token stream as a collection of tokens
	 * @return A collection containing the ordered tokens as wrapped by this stream
	 * Each token must be a separate element within the collection.
	 * Operations on the returned collection should NOT affect the token stream
	 */
	public Collection<String> getAllTokens() {
		if(null!=tokenList && !tokenList.isEmpty()){
			List<String> CollectionOfTokens= new ArrayList<String>();
		     String stream=	tokenList.toString();
		     stream=stream.substring(1,stream.length() - 1);
			String[] words = stream.split(", ");
			
			 for (String word : words)
			    {
				 CollectionOfTokens.add(word);
			    }
		     //Collections.sort(CollectionOfTokens);
			 return CollectionOfTokens;
		}
		return null;
		 		
	}
	
	/**
	 * Method to query for the given token within the stream
	 * @param token: The token to be queried
	 * @return: THe number of times it occurs within the stream, 0 if not found
	 */
	public int query(String token) {
		if(null!=tokenList){
			//Map<String,Integer> wordsWithCount = new HashMap<String, Integer>();
			String stream= tokenList.toString();
			stream = stream.substring(1,stream.length() - 1);
			String[] words = stream.split(", ");
			int count=0;
			for (String word : words) {
				if (word.equals(token)) {
					count++;
				}
			}
			return count;
					
		    /*for (String word : words)
		    {
		        if(wordsWithCount.containsKey(word))
		        {
		            wordsWithCount.put(word, wordsWithCount.get(word)+1);
		        }
		        else
		        {
		            wordsWithCount.put(word, 1);
		        }
	
		    }
		    if(wordsWithCount.containsKey(token))
		    {
		    	return wordsWithCount.get(token);
		    }
		    else
		    {
		    	return 0;
		    }*/
		}
		return 0;
		
		//TODO: Implement this method
		
	}
	
	/**
	 * Iterator method: Method to check if the stream has any more tokens
	 * @return true if a token exists to iterate over, false otherwise
	 */
	public boolean hasNext() {
		if(null!=tokenList){
			if(itterator == tokenList.size())
				return false;
			if( null != tokenList.get(itterator))
				return true;
		}
		return false;
		// TODO: Implement this method
		//return tokenList.iterator().hasNext();
		
	}
	
	/**
	 * Iterator method: Method to check if the stream has any more tokens
	 * @return true if a token exists to iterate over, false otherwise
	 */
	public boolean hasPrevious() {
		//TODO: Implement this method
		if(null!=tokenList){
			if (itterator == 0)
				return false;
			if( null != tokenList.get(itterator-1))
				return true;
		}
		return false;
	}
	
	/**
	 * Iterator method: Method to get the next token from the stream
	 * Callers must call the set method to modify the token, changing the value
	 * of the token returned by this method must not alter the stream
	 * @return The next token from the stream, null if at the end
	 */
	public String next() {
		// TODO: Implement this method
		if(null!=tokenList){
			if(itterator == tokenList.size())
				return null;
			String token = tokenList.get(itterator);
			itterator++;
			if( null != token)
				return token;
		}
		return null;
	}
	
	/**
	 * Iterator method: Method to get the previous token from the stream
	 * Callers must call the set method to modify the token, changing the value
	 * of the token returned by this method must not alter the stream
	 * @return The next token from the stream, null if at the end
	 */
	public String previous() {
		//TODO: Implement this method
		if(null!=tokenList){
			if(0 == itterator)
				return null;
			String token = tokenList.get(itterator-1);
			itterator--;
			if( null != token)
				return token;
		}
		return null;
	}
	
	/**
	 * Iterator method: Method to remove the current token from the stream
	 */
	public void remove() {
		// TODO: Implement this method
		if(null!=tokenList){
			if(itterator < tokenList.size())
				tokenList.remove(itterator);
			
						
		}
		
		
		
	}
	
	/**
	 * Method to merge the current token with the previous token, assumes whitespace
	 * separator between tokens when merged. The token iterator should now point
	 * to the newly merged token (i.e. the previous one)
	 * @return true if the merge succeeded, false otherwise
	 */
	public boolean mergeWithPrevious() {
		//TODO: Implement this method
		if(null!=tokenList){
			if(itterator == 0 || tokenList.size() == 1)
				return false;
			String token = tokenList.get(itterator);
			tokenList.remove(itterator);
			itterator--;
			tokenList.set(itterator, tokenList.get(itterator) + " " + token);
			return true;
		}
		return false;
	}
	
	/**
	 * Method to merge the current token with the next token, assumes whitespace
	 * separator between tokens when merged. The token iterator should now point
	 * to the newly merged token (i.e. the current one)
	 * @return true if the merge succeeded, false otherwise
	 */
	public boolean mergeWithNext() {
		//TODO: Implement this method
		if(null!=tokenList){
			if(itterator == tokenList.size())
				return false;
			if ((itterator + 1) != tokenList.size()) {
				String token = tokenList.get(itterator + 1);
				tokenList.remove(itterator + 1);
				tokenList
						.set(itterator, tokenList.get(itterator) + " " + token);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method to replace the current token with the given tokens
	 * The stream should be manipulated accordingly based upon the number of tokens set
	 * It is expected that remove will be called to delete a token instead of passing
	 * null or an empty string here.
	 * The iterator should point to the last set token, i.e, last token in the passed array.
	 * @param newValue: The array of new values with every new token as a separate element within the array
	 */
	public void set(String... newValue) {
		//TODO: Implement this method
		if(null!=tokenList){
			int ctr=0;
			for (String newVal : newValue) {
				if (null != newVal && !newVal.isEmpty()) {
					if (ctr == 0)
						if (itterator == tokenList.size())
							tokenList.add(itterator, newVal);
						else
							tokenList.set(itterator, newVal);
					else{
						itterator++;
						tokenList.add(itterator, newVal);
						
					}
					ctr++;
				}
			}
		}
	}
	
	/**
	 * Iterator method: Method to reset the iterator to the start of the stream
	 * next must be called to get a token
	 */
	public void reset() {
		
		itterator=0;
		//TODO: Implement this method
	}
	
	/**
	 * Iterator method: Method to set the iterator to beyond the last token in the stream
	 * previous must be called to get a token
	 */
	public void seekEnd() {
		if(null!=tokenList)
			itterator=tokenList.size();
		
	}
	
	/**
	 * Method to merge this stream with another stream
	 * @param other: The stream to be merged
	 */
	public void merge(TokenStream other) {
		//TODO: Implement this method
		if(null != other)
			if(null != other.tokenList){
				if(null == tokenList){
					tokenList = new ArrayList<String>();
				}
				tokenList.addAll(other.getAllTokens());
			}
	}
}
