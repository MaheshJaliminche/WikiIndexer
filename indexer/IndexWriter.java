/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

/**
 * @author nikhillo This class is used to write an index to the disk
 * 
 */
public class IndexWriter implements Writeable {

	private String indexKey = null;
	private int partitionNum = 0;
	private Map<String, List<TermIndexValues>> indexMap;
	private Map<Integer, List<TermIndexValues>> indexLinkMap;
	FileOutputStream fos = null;
	ObjectOutputStream oos = null;

	/**
	 * Constructor that assumes the underlying index is inverted Every index
	 * (inverted or forward), has a key field and the value field The key field
	 * is the field on which the postings are aggregated The value field is the
	 * field whose postings we are accumulating For term index for example: Key:
	 * Term (or term id) - referenced by TERM INDEXFIELD Value: Document (or
	 * document id) - referenced by LINK INDEXFIELD
	 * 
	 * @param props
	 *            : The Properties file
	 * @param keyField
	 *            : The index field that is the key for this index
	 * @param valueField
	 *            : The index field that is the value for this index
	 */
	public IndexWriter(Properties props, INDEXFIELD keyField,
			INDEXFIELD valueField) {
		this(props, keyField, valueField, false);
	}

	/**
	 * Overloaded constructor that allows specifying the index type as inverted
	 * or forward Every index (inverted or forward), has a key field and the
	 * value field The key field is the field on which the postings are
	 * aggregated The value field is the field whose postings we are
	 * accumulating For term index for example: Key: Term (or term id) -
	 * referenced by TERM INDEXFIELD Value: Document (or document id) -
	 * referenced by LINK INDEXFIELD
	 * 
	 * @param props
	 *            : The Properties file
	 * @param keyField
	 *            : The index field that is the key for this index
	 * @param valueField
	 *            : The index field that is the value for this index
	 * @param isForward
	 *            : true if the index is a forward index, false if inverted
	 */
	public IndexWriter(Properties props, INDEXFIELD keyField,
			INDEXFIELD valueField, boolean isForward) {
		// TODO: Implement this method

		indexKey = keyField.name();
		if (keyField.name().equalsIgnoreCase(INDEXFIELD.AUTHOR.name())) {
			indexMap = new TreeMap<String, List<TermIndexValues>>();

		}

		if (keyField.name().equalsIgnoreCase(INDEXFIELD.CATEGORY.name())) {
			indexMap = new TreeMap<String, List<TermIndexValues>>();

		}

		if (keyField.name().equalsIgnoreCase(INDEXFIELD.TERM.name())) {
			indexMap = new TreeMap<String, List<TermIndexValues>>();

		}

		if (keyField.name().equalsIgnoreCase(INDEXFIELD.LINK.name())) {
			indexLinkMap = new TreeMap<Integer, List<TermIndexValues>>();

		}

	}

	/**
	 * Method to make the writer self aware of the current partition it is
	 * handling Applicable only for distributed indexes.
	 * 
	 * @param pnum
	 *            : The partition number
	 */
	public void setPartitionNumber(int pnum) {
		// TODO: Optionally implement this method
		partitionNum = pnum;
	}

	/**
	 * Method to add a given key - value mapping to the index
	 * 
	 * @param keyId
	 *            : The id for the key field, pre-converted
	 * @param valueId
	 *            : The id for the value field, pre-converted
	 * @param numOccurances
	 *            : Number of times the value field is referenced by the key
	 *            field. Ignore if a forward index
	 * @throws IndexerException
	 *             : If any exception occurs while indexing
	 */
	public void addToIndex(int keyId, int valueId, int numOccurances)
			throws IndexerException {
		// TODO: Implement this method
		TermIndexValues termIndexVal = new TermIndexValues();
		termIndexVal.setDocID(valueId);
		termIndexVal.setOccurences(numOccurances);
		List<TermIndexValues> termIndexVals = new ArrayList<TermIndexValues>();

		if (indexLinkMap.containsKey(keyId)) {
			termIndexVals = indexLinkMap.get(keyId);
			termIndexVals.add(termIndexVal);
			indexLinkMap.put(keyId, termIndexVals);
		} else {
			termIndexVals.add(termIndexVal);
			indexLinkMap.put(keyId, termIndexVals);
		}

	}

	/**
	 * Method to add a given key - value mapping to the index
	 * 
	 * @param keyId
	 *            : The id for the key field, pre-converted
	 * @param value
	 *            : The value for the value field
	 * @param numOccurances
	 *            : Number of times the value field is referenced by the key
	 *            field. Ignore if a forward index
	 * @throws IndexerException
	 *             : If any exception occurs while indexing
	 */
	public void addToIndex(int keyId, String value, int numOccurances)
			throws IndexerException {
		// TODO: Implement this method
	}

	/**
	 * Method to add a given key - value mapping to the index
	 * 
	 * @param key
	 *            : The key for the key field
	 * @param valueId
	 *            : The id for the value field, pre-converted
	 * @param numOccurances
	 *            : Number of times the value field is referenced by the key
	 *            field. Ignore if a forward index
	 * @throws IndexerException
	 *             : If any exception occurs while indexing
	 */
	public void addToIndex(String key, int valueId, int numOccurances)
			throws IndexerException {
		// TODO: Implement this method
		TermIndexValues termIndexVal = new TermIndexValues();
		termIndexVal.setDocID(valueId);
		termIndexVal.setOccurences(numOccurances);
		List<TermIndexValues> termIndexVals = new ArrayList<TermIndexValues>();

		if (indexMap.containsKey(key)) {
			termIndexVals = indexMap.get(key);
			termIndexVals.add(termIndexVal);
			indexMap.put(key, termIndexVals);
		} else {
			termIndexVals.add(termIndexVal);
			indexMap.put(key, termIndexVals);
		}

	}

	/**
	 * Method to add a given key - value mapping to the index
	 * 
	 * @param key
	 *            : The key for the key field
	 * @param value
	 *            : The value for the value field
	 * @param numOccurances
	 *            : Number of times the value field is referenced by the key
	 *            field. Ignore if a forward index
	 * @throws IndexerException
	 *             : If any exception occurs while indexing
	 */
	public void addToIndex(String key, String value, int numOccurances)
			throws IndexerException {
		// TODO: Implement this method
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.cse.ir.wikiindexer.indexer.Writeable#writeToDisk()
	 */
	public void writeToDisk() throws IndexerException {
		// TODO Implement this method

		try {
			fos = new FileOutputStream("map" + indexKey + partitionNum + ".ser");
			oos = new ObjectOutputStream(fos);

			if (indexKey.equalsIgnoreCase(INDEXFIELD.LINK.name())) {
				if (null != indexLinkMap && !indexLinkMap.isEmpty())
					oos.writeObject(indexLinkMap);
			} else {
				if (null != indexMap && !indexMap.isEmpty())
					oos.writeObject(indexMap);
			}
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * FileInputStream fis = new FileInputStream("map.ser");
		 * ObjectInputStream ois = new ObjectInputStream(fis); Map anotherMap =
		 * (Map) ois.readObject(); ois.close();
		 */

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.buffalo.cse.ir.wikiindexer.indexer.Writeable#cleanUp()
	 */
	public void cleanUp() {
		// TODO Implement this method
		try {
			writeToDisk();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndexerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		indexMap = null;
		indexLinkMap = null;
	}

}
