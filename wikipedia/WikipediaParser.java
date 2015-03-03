/**
 * 
 */
package edu.buffalo.cse.ir.wikiindexer.wikipedia;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author nikhillo This class implements Wikipedia markup processing. Wikipedia
 *         markup details are presented here:
 *         http://en.wikipedia.org/wiki/Help:Wiki_markup It is expected that all
 *         methods marked "todo" will be implemented by students. All methods
 *         are static as the class is not expected to maintain any state.
 */
public class WikipediaParser {
	/* TODO */
	
	private static String parsedLinksText = null;
	/**
	 * Method to parse section titles or headings. Refer:
	 * http://en.wikipedia.org/wiki/Help:Wiki_markup#Sections
	 * 
	 * @param titleStr
	 *            : The string to be parsed
	 * @return The parsed string with the markup removed
	 */
	public static String parseSectionTitle(String titleStr) {

		String parsedTitle = "";
		if(null != titleStr){
			Pattern p = Pattern.compile("(==+\\s*)(.*?)(==+)");
			Matcher m = p.matcher(titleStr);
			while(m.find()){
				parsedTitle = m.group(2).trim();
				//System.out.println("#####Title#######:" + parsedTitle);
			}
			return parsedTitle;
		}
		return null;
	}

	/* TODO */
	/**
	 * Method to parse list items (ordered, unordered and definition lists).
	 * Refer: http://en.wikipedia.org/wiki/Help:Wiki_markup#Lists
	 * 
	 * @param itemText
	 *            : The string to be parsed
	 * @return The parsed string with markup removed
	 */
	public static String parseListItem(String itemText) {
		if( null != itemText){
			itemText = itemText.replaceAll("([#*]|^[;]|^[:]|\\s+[:])\\s*", "");
			//System.out.println("Prsed list :::::::::::::::::::::" + itemText);
			return itemText;
		}
		return null;
	}

	/* TODO */
	/**
	 * Method to parse text formatting: bold and italics. Refer:
	 * http://en.wikipedia.org/wiki/Help:Wiki_markup#Text_formatting first point
	 * 
	 * @param text
	 *            : The text to be parsed
	 * @return The parsed text with the markup removed
	 */
	public static String parseTextFormatting(String text) {
		if( null != text){
			text = text.replaceAll("('{2,5}|(&nbsp[;])|([{]{2}pad[|]\\d*em[}]{2}))", "");
			//System.out.println("Prsed format text :::::::::::::::::::::" + text);
			return text;
		}
		return null;
	}

	/* TODO */
	/**
	 * Method to parse *any* HTML style tags like: <xyz ...> </xyz> For most
	 * cases, simply removing the tags should work.
	 * 
	 * @param text
	 *            : The text to be parsed
	 * @return The parsed text with the markup removed.
	 */
	public static String parseTagFormatting(String text) {
		if( null != text){
			text = text.replaceAll("(\\s*\\<[^>]*/>|\\s*\\</[^>]*>|\\<[^>]*>\\s*|((&lt[;])([^.]*?)(&gt[;]))\\s*|(\\s*(&lt[;])([^.]*?)(/\\w*)(&gt[;])))", "");
			//System.out.println("Prsed format tags :::::::::::::::::::::" + text);
			return text;
		}
		return null;
	}

	/* TODO */
	/**
	 * Method to parse wikipedia templates. These are *any* {{xyz}} tags For
	 * most cases, simply removing the tags should work.
	 * 
	 * @param text
	 *            : The text to be parsed
	 * @return The parsed text with the markup removed
	 */
	public static String parseTemplates(String text) {
		if( null != text){
			Pattern p = Pattern.compile("(\\{\\{)|(\\}\\})");
			Matcher m = p.matcher(text);
			int ctrOpen = 0;
			int ctrClose = 0;
			int start = 0;
			int length = 0;
			StringBuilder subStr = new StringBuilder(text);
			while(m.find()){
				if("{{".equals(m.group())){
					ctrOpen++;
					if(ctrOpen == 1)
						start = m.start();
				}
				if("}}".equals(m.group())){
					ctrClose++;
				}
				
				if(ctrClose == ctrOpen){
					subStr.replace(start - length, m.end() - length, "");
					length += m.end() - start;
					ctrOpen = 0;
					ctrClose = 0;
					//text.replace(subStr, "");
				}
			}
			
			Pattern p1 = Pattern.compile("(\\{)|(\\})");
			Matcher m1 = p1.matcher(subStr.toString());
			int ctrOpen1 = 0;
			int ctrClose1 = 0;
			int start1 = 0;
			length = 0;
			while(m1.find()){
				if("{".equals(m1.group())){
					ctrOpen1++;
					if(ctrOpen1 == 1)
						start1 = m1.start();
				}
				if("}".equals(m1.group())){
					ctrClose1++;
				}
				
				if(ctrClose1 == ctrOpen1){
					subStr.replace(start1 - length, m1.end() - length, "");
					length += m1.end() - start1;
					ctrOpen1 = 0;
					ctrClose1 = 0;
					//text.replace(subStr, "");
				}
			}
			//System.out.println("#####templayte#######:" + subStr);
			return subStr.toString();
		}
		return null;
	}

	/* TODO */
	/**
	 * Method to parse links and URLs. Refer:
	 * http://en.wikipedia.org/wiki/Help:Wiki_markup#Links_and_URLs
	 * 
	 * @param text
	 *            : The text to be parsed
	 * @return An array containing two elements as follows - The 0th element is
	 *         the parsed text as visible to the user on the page The 1st
	 *         element is the link url
	 */
	public static String[] parseLinks(String text) {
		
		List<String> linksList = new ArrayList<String>();
		if( null != text && !text.isEmpty()){
			StringBuilder subStr = new StringBuilder(text);
			Pattern p;
			Matcher m;
			int length = 0;
			//[[Identity (philosophy)|unique identity]]
			p = Pattern.compile("(\\[\\[)(Wiktionary[:])(.*?)(\\s*[|])(\\]\\])");
			m = p.matcher(subStr.toString());
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(3));
				length += m.end() - m.start() - m.group(3).length();
				//System.out.println("**Link:" + m.group(3) + "----" + "");
				linksList.add(m.group(3));
				linksList.add("");
			}
			
			length = 0;
			p = Pattern.compile("(\\[\\[)(Wiktionary[:])(.*?)(\\s*[|])(.*+)(\\]\\])");
			m = p.matcher(subStr.toString());
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(5));
				length += m.end() - m.start() - m.group(5).length();
				//System.out.println("**Link:" + m.group(5) + "----" + "");
				linksList.add(m.group(5));
				linksList.add("");
			}
			
			p = Pattern.compile("(\\[\\[media:)(.+?)(\\s*[|])(.*?)(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(4));
				length += m.end() - m.start() - m.group(4).length();
				//System.out.println("**Link:" + m.group(5) + "----" + "");
				linksList.add(m.group(4));
				linksList.add("");
			}
			
			p = Pattern.compile("(\\[\\[)(Wiktionary[:].*?)(\\s*)(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(2));
				length += m.end() - m.start() - m.group(2).length();
				//System.out.println("**Link:" + m.group(2) + "----" + "");
				linksList.add(m.group(2));
				linksList.add("");
			}
			
			/*p = Pattern.compile("(\\[\\[)(.*?)([:])(\\s*\\w*)*([.])(\\w*)([|])(.*+)(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(6));
				length += m.end() - m.start();
				System.out.println("**Link:" + m.group(6) + "----" + "");
				linksList.add(m.group(6));
				linksList.add("");
			}*/
			
			p = Pattern.compile("(\\[\\[)(Wikipedia[:])(.*?)([#])(.*?)([|])(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(2)+m.group(3)+m.group(4)+m.group(5));
				length += m.end() - m.start() - m.group(2).length() - m.group(3).length() - m.group(4).length() - m.group(5).length();
				//System.out.println("**Link:" + m.group(3) + "----" + "");
				linksList.add(subStr.toString());
				linksList.add("");
			}
			
			p = Pattern.compile("(\\[\\[)(Wikipedia[:])(.*?)(\\s*)([(])([|])(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(3));
				length += m.end() - m.start() - m.group(3).length();
				//System.out.println("**Link:" + m.group(3) + "----" + "");
				linksList.add(m.group(3));
				linksList.add("");
			}
			
			
			p = Pattern.compile("(\\[\\[)(Wikipedia[:])(.*?)(\\s*[(])(.*?)([|])(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(3));
				length += m.end() - m.start() - m.group(3).length();
				//System.out.println("**Link:" + m.group(3) + "----" + "");
				linksList.add(m.group(3));
				linksList.add("");
			}
			
			p = Pattern.compile("(\\[\\[)(Wikipedia[:])(.*?)([|])(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(3));
				length += m.end() - m.start() - m.group(3).length();
				//System.out.println("**Link:" + m.group(3) + "----" + "");
				linksList.add(m.group(3));
				linksList.add("");
			}
			
			p = Pattern.compile("(\\[\\[)(File[:])(.*?[|])+(.*?)(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(4));
				length += m.end() - m.start() - m.group(4).length();
				//System.out.println("**Link:" + m.group(4) + "----" + "");
				linksList.add(m.group(4));
				linksList.add("");
			}
			
			p = Pattern.compile("(\\[\\[)(File[:])(.*?)([.])(.*?)(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, "");
				length += m.end() - m.start();
				//System.out.println("**Link:" + m.group(2) + "----" + "");
				linksList.add("");
				linksList.add("");
			}
			
			p = Pattern.compile("(\\[\\[)(Image[:])(.*?[|])+(.*?)(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(4));
				length += m.end() - m.start() - m.group(4).length();
				//System.out.println("**Link:" + m.group(4) + "----" + "");
				linksList.add(m.group(4));
				linksList.add("");
			}
			
			p = Pattern.compile("(\\[\\[)(Image[:])(.*?)([.])(.*?)(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, "");
				length += m.end() - m.start();
				//System.out.println("**Link:" + m.group(2) + "----" + "");
				linksList.add("");
				linksList.add("");
			}
			
			p = Pattern.compile("(\\[\\[)(Category[:])(.*?)(\\|)(\\s)*(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(3));
				length += m.end() - m.start() - m.group(3).length();
				//System.out.println("**Link:" + m.group(2) + "----" + "");
				linksList.add(m.group(3));
				linksList.add("");
			}
			
			p = Pattern.compile("(\\[\\[)(Category[:])(.*?)(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(3));
				length += m.end() - m.start() - m.group(3).length();
				//System.out.println("**Link:" + m.group(2) + "----" + "");
				linksList.add(m.group(3));
				linksList.add("");
			}
			
			p = Pattern.compile("(\\[\\[)([:])(Category[:])(.*?)(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(3)+m.group(4));
				length += m.end() - m.start() - m.group(4).length() - m.group(3).length();
				//System.out.println("**Link:" + m.group(2) + "----" + "");
				linksList.add(m.group(3)+m.group(4));
				linksList.add("");
			}
			
			p = Pattern.compile("(\\[\\[)(\\w\\w)([:])(.*?)(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, "");
				length += m.end() - m.start();
				//System.out.println("**Link:" + m.group(2) + "----" + "");
				linksList.add(m.group(2)+m.group(3)+m.group(4));
				linksList.add("");
			}
			
			p = Pattern.compile("(\\[)(http[:][/][/])(.*?)(\\s+)(.*?)(\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(5));
				length += m.end() - m.start() - m.group(5).length();
				//System.out.println("**Link:" + m.group(2) + "----" + "");
				linksList.add(m.group(5));
				linksList.add("");
			}
			
			p = Pattern.compile("(\\[)(http[:][/][/])(.*?)(\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, "");
				length += m.end() - m.start();
				//System.out.println("**Link:" + m.group(2) + "----" + "");
				linksList.add("");
				linksList.add("");
			}
			
			
			p = Pattern.compile("(\\[\\[)(\\w*)(\\s)(\\w*)([|])(.+?)(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(6));
				length += m.end() - m.start() - m.group(6).length();
				String temp = Character.toUpperCase(m.group(2).charAt(0)) + m.group(2).substring(1);
				//System.out.println("**Link:" + temp + "----" + m.group(2));
				linksList.add(subStr.toString());
				linksList.add(temp+"_"+m.group(4));
			}
			
			p = Pattern.compile("(\\[\\[)(\\w+)([|])(.+?)(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(4));
				length += m.end() - m.start() - m.group(4).length();
				String temp = Character.toUpperCase(m.group(2).charAt(0)) + m.group(2).substring(1);
				//System.out.println("**Link:" + temp + "----" + m.group(2));
				linksList.add(subStr.toString());
				linksList.add(temp);
			}
			
			p = Pattern.compile("(\\[\\[)(.*?)([,]\\s)(.*?)([|])(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(2));
				length += m.end() - m.start() - m.group(2).length();
				String temp = Character.toUpperCase(m.group(2).charAt(0)) + m.group(2).substring(1);
				//System.out.println("**Link:" + temp + "----" + m.group(4));
				linksList.add(m.group(2));
				linksList.add(temp+",_"+m.group(4));
			}
			
			p = Pattern.compile("(\\[\\[)(\\w*)([^A-Za-z]*)(\\w*)(\\|)(.+?)(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(6));
				length += m.end() - m.start() - m.group(6).length();
				String temp = Character.toUpperCase(m.group(6).charAt(0)) + m.group(6).substring(1);
				//System.out.println("**Link:" + m.group(2) + "----" + temp);
				linksList.add(temp);
				linksList.add(m.group(2)+m.group(3)+m.group(4));
			}
			
			p = Pattern.compile("(\\[\\[)(.+?)(\\s)([(])(.*?)([)])([|])(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(2));
				length += m.end() - m.start() - m.group(2).length();
				String temp = Character.toUpperCase(m.group(2).charAt(0)) + m.group(2).substring(1);
				//System.out.println("**Link:" + temp + "----" + m.group(5));
				linksList.add(m.group(2));
				linksList.add(temp+"_"+m.group(4) + m.group(5) + m.group(6));
			}			
			
			p = Pattern.compile("(\\[\\[)((\\w+)(\\s+)(\\w+))(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				String temp = m.group().replaceAll("\\[\\[", "");
				temp =temp.replaceAll("\\]\\]", "");
				subStr.replace(m.start() - length, m.end() - length, temp);
				length += m.end() - m.start() - temp.length();
				temp = Character.toUpperCase(temp.charAt(0)) + temp.substring(1);
				temp = temp.replaceAll("\\s", "_");
				//System.out.println("**Link:" + temp + "----" + m.group(4));
				linksList.add(subStr.toString());
				linksList.add(temp);
			}
			
			p = Pattern.compile("(\\[\\[)(.+?)(\\w*)(\\]\\]\\<)(.*?)([>])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				subStr.replace(m.start() - length, m.end() - length, m.group(2)+m.group(3));
				length += m.end() - m.start() - m.group(2).length() - m.group(3).length();
				String temp = Character.toUpperCase(m.group(2).charAt(0)) + m.group(2).substring(1);
				//System.out.println("**Link:" + temp + "----" + m.group(4));
				linksList.add(subStr.toString());
				linksList.add(temp+m.group(3));
			}
			
			p = Pattern.compile("(\\[\\[)(.+?)(\\w*)(\\]\\])");
			m = p.matcher(subStr.toString());
			length = 0;
			while(m.find()){
				//String temp1 = null;
				String temp = m.group(2)+m.group(3);
				temp = temp.replace("[|]", " ");
				temp =temp.replace("\\(", "");
				temp =temp.replace("\\)", "");
				
				subStr.replace(m.start() - length, m.end() - length, temp);
				length += m.end() - m.start() -temp.length();
				temp = Character.toUpperCase(m.group(2).charAt(0)) + m.group(2).substring(1);
				//temp = temp.trim();
				temp =temp.replaceAll(" ", "_");
				
				//System.out.println("**Link:" + temp + "----" + m.group(3));
				linksList.add(subStr.toString());
				linksList.add(temp + m.group(3));
			}
			
			
			parsedLinksText = new String(subStr.toString());
			//System.out.println(subStr);
			String[] links = linksList.toArray(new String[linksList.size()]);
			return links;
			
			//text = text.replaceAll("()", "");
			//System.out.println("Prsed format text :::::::::::::::::::::" + text);
			//return text;
			//links = (String[]) linksList.toArray();
		}
		String[] temp = {"",""};
		return temp;
	}
	
	/**
	 * 
	 * @param text
	 * @return
	 */
	public static List<String> parseCategories(String text) {
		
		List<String> categories = new ArrayList<String>();
		if( null != text){
			Pattern p = Pattern.compile("(\\[\\[)(Category[:])(.*?)(\\]\\])");
			Matcher m = p.matcher(text);
			while(m.find()){
				categories.add(m.group(3));
				//System.out.println("------------Category = " + m.group(3));
			}
			return categories;
		}
		return null;
	}

	public static WikipediaDocument createWikiDocument(int id,
			String timeStamp, String author, String title, StringBuilder str)
			throws ParseException {
		WikipediaDocument wikiDoc = new WikipediaDocument(id, timeStamp,
				author, title);

		int count = 0;
		int end = 0;
		String firstSec = null;
		String sectionTitle = null;
		String sectionText = null;
		String parsedTitle = null;
		boolean isSectionPresent = false;

		String text = str.toString();
		// Pattern p = Pattern.compile("(==*)((\\w*\\s*)*)(==*)"); Correct one
		// Pattern p = Pattern.compile("(==*)(\\w+(\\w*\\s*)*)(==*)");
		Pattern p = Pattern.compile("==+.*==+");
		Matcher m = p.matcher(text);
		while (m.find()) {

			if (isSectionPresent) {
				sectionText = text.substring(end + 1, m.start());
				//System.out.println("Parsed title:" + sectionTitle);
				//System.out.println("Parsed text:" + sectionText);
				
				// TODO parse text and store in wikidoc
				parsedTitle = parseSectionTitle(sectionTitle);
				sectionText = parseTagFormatting(sectionText);
				List<String> categories = parseCategories(sectionText);
				wikiDoc.addCategories(categories);
				String[] links = parseLinks(sectionText);
				sectionText = new String(parsedLinksText);
				int ctr = 1;
				for(String link:links){
					if( ctr %2 == 0)
						if(null!=link && !link.isEmpty())
							wikiDoc.addLink(link);
					ctr++;
				}
				sectionText = parseTemplates(sectionText);
				sectionText = parseListItem(sectionText);
				sectionText = parseTextFormatting(sectionText);
				//System.out.println(sectionText);
				wikiDoc.addSection(parsedTitle, sectionText);
				
				
			}

			if (m.start() == 0) {
				sectionTitle = text.substring(m.start(), m.end());
				end = m.end();
				isSectionPresent = true;
				count++;
				

			} else {
				if (count == 0) {
					firstSec = text.substring(0, m.start());
					if (null != firstSec && !firstSec.isEmpty()) {
						parsedTitle = "Default";
						sectionText = firstSec;
						count++;
						//System.out.println("Parsed title:" + sectionTitle);
						//System.out.println("Parsed text:" + sectionText);
						
						// TODO parse text and store in wikidoc
						//parsedTitle = parseSectionTitle(sectionTitle);
						sectionText = parseTagFormatting(sectionText);
						List<String> categories = parseCategories(sectionText);
						wikiDoc.addCategories(categories);
						String[] links = parseLinks(sectionText);
						sectionText = new String(parsedLinksText);
						int ctr = 1;
						for(String link:links){
							if( ctr %2 == 0)
								if(null!=link && !link.isEmpty())
									wikiDoc.addLink(link);
							ctr++;
						}
						sectionText = parseTemplates(sectionText);
						
						sectionText = parseListItem(sectionText);
						sectionText = parseTextFormatting(sectionText);
						wikiDoc.addSection(parsedTitle, sectionText);
						//System.out.println(sectionText);
						
						sectionTitle = text.substring(m.start(), m.end());
						end = m.end();
						count++;
						isSectionPresent = true;
					}
				} else {
					sectionTitle = text.substring(m.start(), m.end());
					end = m.end();
					count++;
					isSectionPresent = true;
				}
			}
		}
		
		if(isSectionPresent){
			sectionText = text.substring(end + 1, text.length());
			//System.out.println("Parsed title:" + sectionTitle);
			//System.out.println("Parsed text:" + sectionText);
			
			// TODO parse text and store in wikidoc
			parsedTitle = parseSectionTitle(sectionTitle);
			sectionText = parseTagFormatting(sectionText);
			List<String> categories = parseCategories(sectionText);
			wikiDoc.addCategories(categories);
			String[] links = parseLinks(sectionText);
			sectionText = new String(parsedLinksText);
			int ctr = 1;
			for(String link:links){
				if( ctr %2 == 0)
					if(null!=link && !link.isEmpty())
						wikiDoc.addLink(link);
				ctr++;
			}
			sectionText = parseTemplates(sectionText);
			sectionText = parseListItem(sectionText);
			sectionText = parseTextFormatting(sectionText);
			wikiDoc.addSection(parsedTitle, sectionText);
			//System.out.println(sectionText);
		} else {
			parsedTitle = "Default";
			sectionText = text.toString();
			//System.out.println("Parsed title:" + sectionTitle);
			//System.out.println("Parsed text:" + sectionText);
			
			// TODO parse text and store in wikidoc
			//parsedTitle = parseSectionTitle(sectionTitle);
			sectionText = parseTagFormatting(sectionText);
			List<String> categories = parseCategories(sectionText);
			wikiDoc.addCategories(categories);
			String[] links = parseLinks(sectionText);
			sectionText = new String(parsedLinksText);
			int ctr = 1;
			for(String link:links){
				if( ctr %2 == 0)
					if(null!=link && !link.isEmpty())
						wikiDoc.addLink(link);
				ctr++;
			}
			sectionText = parseTemplates(sectionText);
			sectionText = parseListItem(sectionText);
			sectionText = parseTextFormatting(sectionText);
			wikiDoc.addSection(parsedTitle, sectionText);
			//System.out.println(sectionText);
		}

		// parseSectionTitle(str.toString());
		return wikiDoc;
	}
}
