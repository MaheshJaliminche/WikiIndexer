/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.awt.List;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import edu.buffalo.cse.ir.wikiindexer.indexer.INDEXFIELD;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.Tokenizer;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument.Section;

/**
 * A Callable document transformer that converts the given WikipediaDocument object
 * into an IndexableDocument object using the given Tokenizer
 * @author nikhillo
 *
 */
public class DocumentTransformer implements Callable<IndexableDocument> {
	/**
	 * Default constructor, DO NOT change
	 * @param tknizerMap: A map mapping a fully initialized tokenizer to a given field type
	 * @param doc: The WikipediaDocument to be processed
	 */
	Map<INDEXFIELD, Tokenizer> tokenizerMap;
    WikipediaDocument wikiDoc= null;
	public DocumentTransformer(Map<INDEXFIELD, Tokenizer> tknizerMap, WikipediaDocument doc) {
		//TODO: Implement this method
		tokenizerMap= new HashMap<INDEXFIELD, Tokenizer>();
		tokenizerMap=tknizerMap;
		wikiDoc=doc;
		
	}
	
	/**
	 * Method to trigger the transformation
	 * @throws TokenizerException Inc ase any tokenization error occurs
	 */
	public IndexableDocument call() throws TokenizerException {
		// TODO Implement this method
		 //String s = wikiDoc.getCategories().toString();
		IndexableDocument indexDoc= new IndexableDocument();
		
		TokenStream authorStream =new TokenStream(wikiDoc.getAuthor());
		Tokenizer tAuthor= tokenizerMap.get(INDEXFIELD.AUTHOR);
		tAuthor.tokenize(authorStream);
		// System.out.println(authorStream.getAllTokens());
		 //System.out.println("*********************");
		TokenStream categoryStream =new TokenStream(wikiDoc.getCategories().toString());
		Tokenizer tCategory= tokenizerMap.get(INDEXFIELD.CATEGORY);
		tCategory.tokenize(categoryStream);
		
		//System.out.println(categoryStream.getAllTokens()); 
		 //System.out.println("*********************");
		
		TokenStream linkStream =new TokenStream(wikiDoc.getLinks().toString());
		Tokenizer tLinks= tokenizerMap.get(INDEXFIELD.LINK);
		tLinks.tokenize(linkStream);
		
		//System.out.println(linkStream.getAllTokens());
		 //System.out.println("*********************");
		
		TokenStream termStream =new TokenStream(wikiDoc.getSections().get(0).getText());
		for(int i=1;i<wikiDoc.getSections().size();i++)
		{
			termStream.append(wikiDoc.getSections().get(i).getText());
		}
		
			
		Tokenizer tTerms= tokenizerMap.get(INDEXFIELD.TERM);
		tTerms.tokenize(termStream);
		
		//System.out.println(termStream.getAllTokens());
		 //System.out.println("*********************");
		
		indexDoc.addField(INDEXFIELD.AUTHOR, authorStream);
		indexDoc.addField(INDEXFIELD.CATEGORY, categoryStream);
		indexDoc.addField(INDEXFIELD.LINK,linkStream);
		indexDoc.addField(INDEXFIELD.TERM, termStream);
	
	    indexDoc.docId= wikiDoc.getId();
		return indexDoc;
		//return null;
	}
	
}
