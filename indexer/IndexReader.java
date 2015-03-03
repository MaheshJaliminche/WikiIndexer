/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.TreeMap;

/**
 * @author nikhillo This class is used to introspect a given index The
 *         expectation is the class should be able to read the index and all
 *         associated dictionaries.
 */
public class IndexReader {

	private String indexKey = null;
	private Map<String, List<TermIndexValues>> indexMap;
	private Map<Integer, List<TermIndexValues>> indexLinkMap;
	FileInputStream fis = null;
	ObjectInputStream ois = null;

	/**
	 * Constructor to create an instance
	 * 
	 * @param props
	 *            : The properties file
	 * @param field
	 *            : The index field whose index is to be read
	 */
	public IndexReader(Properties props, INDEXFIELD field) {
		// TODO: Implement this method
		indexKey = field.name();
	}

	/**
	 * Method to get the total number of terms in the key dictionary
	 * 
	 * @return The total number of terms as above
	 */
	public int getTotalKeyTerms() {
		// TODO: Implement this method
		readFile();
		if (indexKey.equalsIgnoreCase(INDEXFIELD.LINK.name())) {
			return indexLinkMap.size();
		} else {
			return indexMap.keySet().size();
		}

	}

	/**
	 * Method to get the total number of terms in the value dictionary
	 * 
	 * @return The total number of terms as above
	 */
	public int getTotalValueTerms() {

		Map<String, Integer> dict = new HashMap<String, Integer>();
		try {

			File f = new File("mapdict" + "LINK" + ".ser");
			if (f.exists()) {
				fis = new FileInputStream("mapdict" + "LINK" + ".ser");
				if (null != fis) {
					ois = new ObjectInputStream(fis);
					dict.putAll((Map) ois.readObject());
				}
				fis.close();
				ois.close();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dict.keySet().size();

	}

	/**
	 * Method to retrieve the postings list for a given dictionary term
	 * 
	 * @param key
	 *            : The dictionary term to be queried
	 * @return The postings list with the value term as the key and the number
	 *         of occurrences as value. An ordering is not expected on the map
	 */
	public Map<String, Integer> getPostings(String key) {

		Map<String, Integer> postingsMap = new HashMap<String, Integer>();
		readFile();
		if (indexKey.equalsIgnoreCase(INDEXFIELD.LINK.name())) {
			int key1 = Integer.parseInt(key);
			List<TermIndexValues> postingList = indexLinkMap.get(key1);
			if (null != postingList) {
				for (TermIndexValues value : postingList) {
					postingsMap.put(String.valueOf(value.getDocID()),
							value.getOccurences());
				}
			}
			return postingsMap;
		} else {
			List<TermIndexValues> postingList = indexMap.get(key);
			for (TermIndexValues value : postingList) {
				postingsMap.put(String.valueOf(value.getDocID()),
						value.getOccurences());
			}
			return postingsMap;
		}
	}

	/**
	 * Method to get the top k key terms from the given index The top here
	 * refers to the largest size of postings.
	 * 
	 * @param k
	 *            : The number of postings list requested
	 * @return An ordered collection of dictionary terms that satisfy the
	 *         requirement If k is more than the total size of the index, return
	 *         the full index and don't pad the collection. Return null in case
	 *         of an error or invalid inputs
	 */
	public Collection<String> getTopK(int k) {
		// TODO: Implement this method
		if (k <= 0)
			return null;

		readFile();
		if (indexKey.equalsIgnoreCase(INDEXFIELD.LINK.name())) {

			List<String> topTerms = new ArrayList<String>();
			List<Integer> keyList = new ArrayList<Integer>(
					indexLinkMap.keySet());
			List<Integer> valueList = new ArrayList<Integer>();
			for (List<TermIndexValues> list : indexLinkMap.values()) {
				valueList.add(list.size());
			}

			Collections.sort(valueList);

			Object[] sortedArray = valueList.toArray();
			int size = sortedArray.length;

			if (k >= size)
				size = k;
			for (int i = size - k; i < size; i++) {
				topTerms.add(String.valueOf(keyList.get(valueList
						.indexOf(sortedArray[i]))));
				valueList.set(valueList.indexOf(sortedArray[i]), -1);
			}

			Collections.reverse(topTerms);
			return topTerms;
		} else {

			List<String> topTerms = new ArrayList<String>();
			List<String> keyList = new ArrayList<String>(indexMap.keySet());
			List<Integer> valueList = new ArrayList<Integer>();
			for (List<TermIndexValues> list : indexMap.values()) {
				valueList.add(list.size());
			}

			Collections.sort(valueList);

			Object[] sortedArray = valueList.toArray();
			int size = sortedArray.length;

			if (k >= size)
				size = k;
			for (int i = size - k; i < size; i++) {
				topTerms.add(keyList.get(valueList.indexOf(sortedArray[i])));
				valueList.set(valueList.indexOf(sortedArray[i]), -1);
			}

			Collections.reverse(topTerms);
			return topTerms;
		}

	}

	/**
	 * Method to execute a boolean AND query on the index
	 * 
	 * @param terms
	 *            The terms to be queried on
	 * @return An ordered map containing the results of the query The key is the
	 *         value field of the dictionary and the value is the sum of
	 *         occurrences across the different postings. The value with the
	 *         highest cumulative count should be the first entry in the map.
	 */
	public Map<String, Integer> query(String... terms) {
		// TODO: Implement this method (FOR A BONUS)
		return null;
	}

	public void readFile() {

		indexMap = new TreeMap<String, List<TermIndexValues>>();
		indexLinkMap = new TreeMap<Integer, List<TermIndexValues>>();

		try {
			for (int i = 0; i < 5; i++) {
				File f = new File("map" + indexKey + i + ".ser");
				if (f.exists()) {
					fis = new FileInputStream("map" + indexKey + i + ".ser");
					if (null != fis) {
						ois = new ObjectInputStream(fis);
						if (indexKey.equalsIgnoreCase(INDEXFIELD.LINK.name())) {
							indexLinkMap.putAll((Map) ois.readObject());
						} else {
							indexMap.putAll((Map) ois.readObject());
						}
					}
					fis.close();
					ois.close();
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Properties props = new Properties();
		IndexReader reader = new IndexReader(props, INDEXFIELD.LINK);
		System.out.println(reader.getPostings("25941"));
	}
}
