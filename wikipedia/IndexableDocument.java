/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.util.HashMap;
import java.util.Map;

import javax.print.Doc;

import edu.buffalo.cse.ir.wikiindexer.indexer.INDEXFIELD;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.Tokenizer;

/**
 * A simple map based token view of the transformed document
 * @author nikhillo
 *
 */
public class IndexableDocument {
	/**
	 * Default constructor
	 */
	Map<INDEXFIELD, TokenStream> transformedDocumentMap ;
	int docId;
	//Integer i;
	
	public IndexableDocument() {
		//TODO: Init state as needed
		transformedDocumentMap= new HashMap<INDEXFIELD, TokenStream>();
	}
	
	/**
	 * MEthod to add a field and stream to the map
	 * If the field already exists in the map, the streams should be merged
	 * @param field: The field to be added
	 * @param stream: The stream to be added.
	 */
	public void addField(INDEXFIELD field, TokenStream stream) {
		//TODO: Implement this method
		//adding in map
		if(transformedDocumentMap.containsKey(field)){
			  TokenStream concatinateStream = transformedDocumentMap.get(field);
			  concatinateStream.merge(stream);
			transformedDocumentMap.put(field, concatinateStream);
		}
		else{
			transformedDocumentMap.put(field, stream);
		}
	}
	
	/**
	 * Method to return the stream for a given field
	 * @param key: The field for which the stream is requested
	 * @return The underlying stream if the key exists, null otherwise
	 */
	public TokenStream getStream(INDEXFIELD key) {
		//TODO: Implement this method
		// retive from map
		return transformedDocumentMap.get(key);
		//return null;
	}
	
	/**
	 * Method to return a unique identifier for the given document.
	 * It is left to the student to identify what this must be
	 * But also look at how it is referenced in the indexing process
	 * @return A unique identifier for the given document
	 */
	public String getDocumentIdentifier() {
		//TODO: Implement this method
		
		return Integer.toString(docId);
		
		
	}
	
}
