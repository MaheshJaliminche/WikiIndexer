/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.indexer;

import java.io.Serializable;

/**
 * @author Mayank
 *
 */
public class TermIndexValues implements Serializable {

	public int occurences;

	public int docID;

	public int getOccurences() {
		return occurences;
	}

	public void setOccurences(int occurences) {
		this.occurences = occurences;
	}

	public int getDocID() {
		return docID;
	}

	public void setDocID(int docID) {
		this.docID = docID;
	}
	
}
