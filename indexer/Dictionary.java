/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;

/**
 * @author nikhillo An abstract class that represents a dictionary object for a
 *         given index
 */

public abstract class Dictionary implements Writeable {
	Map<String, Integer> dict;
	// String dictionaryName;
	INDEXFIELD indexField;

	public Dictionary(Properties props, INDEXFIELD field) {
		// TODO Implement this method
		dict = new HashMap<String, Integer>();
		// dictionaryName= field.toString();
		indexField = field;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.cse.ir.wikiindexer.indexer.Writeable#writeToDisk()
	 */
	public void writeToDisk() throws IndexerException {
		// TODO Implement this method
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("mapdict" + indexField.name() + System.currentTimeMillis()+ ".ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			if (null != dict && !dict.isEmpty()) {
				oos.writeObject(dict);
			}
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.cse.ir.wikiindexer.indexer.Writeable#cleanUp()
	 */
	public void cleanUp() {
		try {
			writeToDisk();
		} catch (IndexerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dict = null;
		// TODO Implement this method

	}

	/**
	 * Method to check if the given value exists in the dictionary or not Unlike
	 * the subclassed lookup methods, it only checks if the value exists and
	 * does not change the underlying data structure
	 * 
	 * @param value
	 *            : The value to be looked up
	 * @return true if found, false otherwise
	 */
	public boolean exists(String value) {
		// TODO Implement this method
		if (dict.containsKey(value)) {
			return true;

		}
		return false;
	}

	/**
	 * MEthod to lookup a given string from the dictionary. The query string can
	 * be an exact match or have wild cards (* and ?) Must be implemented ONLY
	 * AS A BONUS
	 * 
	 * @param queryStr
	 *            : The query string to be searched
	 * @return A collection of ordered strings enumerating all matches if found
	 *         null if no match is found
	 */
	public Collection<String> query(String queryStr) {
		// TODO: Implement this method (FOR A BONUS)
		// if(dict.containsKey(queryStr))
		ArrayList<String> queryMatch = new ArrayList<String>();
		Set<String> tokens = dict.keySet();

		if (queryStr.contains("*")) {
			if (queryStr.startsWith("*")) {
				queryStr = queryStr.replaceAll("\\*", "");
				for (String string : tokens) {

					if (string.endsWith(queryStr)) {
						queryMatch.add(string);
					}
				}
				if (queryMatch.size() > 0)
					return queryMatch;
				else
					return null;
			} else if (queryStr.endsWith("*")) {
				queryStr = queryStr.replaceAll("\\*", "");
				for (String string : tokens) {

					if (string.startsWith(queryStr)) {
						queryMatch.add(string);
					}
				}
				if (queryMatch.size() > 0)
					return queryMatch;
				else
					return null;
			} else {
				String[] s = queryStr.split("\\*");
				String s0 = s[0].replaceAll("\\*", "");
				String s1 = s[1].replaceAll("\\*", "");
				for (String string : tokens) {

					if (string.startsWith(s0) && string.endsWith(s1)) {
						queryMatch.add(string);
					}
				}

				/*
				 * for (String string : tokens) {
				 * 
				 * if(string.endsWith(s1)) { queryMatch.add(string); } }
				 */

				if (queryMatch.size() > 0)
					return queryMatch;
				else
					return null;
			}

		} else {
			for (String string : tokens) {

				if (string.equals(queryStr)) {
					queryMatch.add(string);
				}
			}
			if (queryMatch.size() > 0)
				return queryMatch;
			else
				return null;
		}

	}

	/**
	 * Method to get the total number of terms in the dictionary
	 * 
	 * @return The size of the dictionary
	 */
	public int getTotalTerms() {
		// TODO: Implement this method
		if (dict != null) {
			return dict.size();
		}
		return -1;
	}
}
