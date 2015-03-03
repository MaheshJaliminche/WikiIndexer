/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.parsers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaDocument;
import edu.buffalo.cse.ir.wikiindexer.wikipedia.WikipediaParser;

/**
 * @author nikhillo
 * 
 */
public class Parser extends DefaultHandler {
	/* */
	private final Properties props;
int cnt = 0;
	/**
	 * 
	 * @param idxConfig
	 * @param parser
	 */
	public Parser(Properties idxProps) {
		props = idxProps;
	}

	/* TODO: Implement this method */
	/**
	 * 
	 * @param filename
	 * @param docs
	 */

	public void parse(String filename, final Collection<WikipediaDocument> docs) {
		// will get the dump file from filename.
		// parse the dump and create WikipediaDocument object for each page
		// add that document to the collection
		// write all the parsing methods in WikipediaParser
		// final ConcurrentLinkedQueue<WikipediaDocument> documents = new
		// ConcurrentLinkedQueue<WikipediaDocument>();
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = parserFactory.newSAXParser();

			DefaultHandler handler = new DefaultHandler() {
				int id;
				boolean isIdCaptured = false;
				String author = null;
				String title = null;
				String timeStamp = null;

				WikipediaDocument wikiDoc;
				StringBuilder str, textStr;

				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {

					str = null;
					str = new StringBuilder();
				}

				public void endElement(String uri, String localName,
						String qName) throws SAXException {

					if (qName.equalsIgnoreCase("title")) {
						title = str.toString();
						// System.out.println("Title :" + str);
					}
					if (qName.equalsIgnoreCase("timestamp")) {

						SimpleDateFormat formatter1 = new SimpleDateFormat(
								"yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
						try {
							Date date = formatter1.parse(str.toString());
							SimpleDateFormat formatter2 = new SimpleDateFormat(
									"yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
							timeStamp = formatter2.format(date);
							// System.out.println(" Timestamp :" + timeStamp);

						} catch (ParseException e) {
							e.printStackTrace();
						}

					}
					if (!isIdCaptured) {
						if (qName.equalsIgnoreCase("id")) {
							id = Integer.parseInt(str.toString());
							// System.out.println("ID :" + id);
							isIdCaptured = true;
						}
					}

					if (qName.equalsIgnoreCase("username")
							|| qName.equalsIgnoreCase("ip")) {
						author = str.toString();
						// System.out.println("Author :" + str);
					}

					if (qName.equalsIgnoreCase("text")) {
						textStr = str;
						// System.out.println("Text : " +str);

					}

					if (qName.equalsIgnoreCase("page")) {
						// count++;
						// System.out.println("page no :" + count);
						isIdCaptured = false;
						try {
							wikiDoc = WikipediaParser.createWikiDocument(id,
									timeStamp, author, title, textStr);
							/*for(int i = 0 ; i < wikiDoc.getSections().size() ; i++)
							  System.out.println(wikiDoc.getSections().get(i).getText()); 
							  */
							//cnt++;
							//System.out.println("****************" + cnt);
							add(wikiDoc, docs);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

					/*
					 * if(qName.equalsIgnoreCase("text")) {
					 * System.out.println("Text  :"+temp); }
					 */
				}

				public void characters(char[] buffer, int start, int length) {
					/*
					 * StringBuffer str = new StringBuffer(); temp =
					 * str.append(buffer, start, length).toString();
					 * System.out.println(str.toString());
					 */
					str = str.append(buffer, start, length);

				}

			};
			
			if(null != filename && !filename.isEmpty())
				try{
					saxParser.parse(filename, handler);
				}catch(FileNotFoundException exception){
					
				}

			// sp.parse("C:/Users/Mahesh/workspace/MaheshIR/Files/five_entries.xml",handler);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Method to add the given document to the collection. PLEASE USE THIS
	 * METHOD TO POPULATE THE COLLECTION AS YOU PARSE DOCUMENTS For better
	 * performance, add the document to the collection only after you have
	 * completely populated it, i.e., parsing is complete for that document.
	 * 
	 * @param doc
	 *            : The WikipediaDocument to be added
	 * @param documents
	 *            : The collection of WikipediaDocuments to be added to
	 */
	private synchronized void add(WikipediaDocument doc,
			Collection<WikipediaDocument> documents) {
		System.out.println(cnt++);
		if(cnt==169){
			System.out.println(doc.getId());
		}
		documents.add(doc);
	}
}
