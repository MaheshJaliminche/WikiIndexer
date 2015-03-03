package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.HashMap;
import java.util.Map;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.APOSTROPHE)
public class Apostrophe implements TokenizerRule {

	Map<String, String> apostrophe = new HashMap<String, String>();

	public Apostrophe() {
		apostrophe.put("should've", "should,have");
		apostrophe.put("Should've", "Should,have");
		apostrophe.put("I've", "I,have");
		apostrophe.put("would've", "would,have");
		apostrophe.put("aren't", "are,not");
		apostrophe.put("can't", "cannot");
		apostrophe.put("couldn't", "could,not");
		apostrophe.put("didn't", "did,not");
		apostrophe.put("doesn't", "does,not");
		apostrophe.put("don't", "do,not");
		apostrophe.put("hadn't", "had,not");
		apostrophe.put("hasn't", "has,not");
		apostrophe.put("haven't", "have,not");
		apostrophe.put("he'd", "he,had");
		apostrophe.put("he'll", "he,will");
		apostrophe.put("he's", "he,is");
		apostrophe.put("i'd", "I,had");
		apostrophe.put("i'll", "I,will");
		apostrophe.put("i'm", "I,am");
		apostrophe.put("i've", "I,have");
		apostrophe.put("I'd", "I,had");
		apostrophe.put("I'll", "I,will");
		apostrophe.put("I'm", "I,am");
		apostrophe.put("I've", "I,have");
		apostrophe.put("isn't", "is,not");
		apostrophe.put("it's", "it,is");
		apostrophe.put("let's", "let,us");
		apostrophe.put("mustn't", "must,not");
		apostrophe.put("'em", "them");
		apostrophe.put("shan't", "shall,not");
		apostrophe.put("she'd", "she,had");
		apostrophe.put("she'll", "she,will");
		apostrophe.put("She'll", "She,will");
		apostrophe.put("she's", "she,is");
		apostrophe.put("shouldn't", "should,not");
		apostrophe.put("that's", "that,is");
		apostrophe.put("there's", "there,is");
		apostrophe.put("they'd", "they,would");
		apostrophe.put("They'd", "They,would");
		apostrophe.put("they'll", "they,will");
		apostrophe.put("they're", "they,are");
		apostrophe.put("they've", "they,have");
		apostrophe.put("wasn't", "was,not");
		apostrophe.put("we'd", "we,had");
		apostrophe.put("we'll", "we,will");
		apostrophe.put("we're", "we,are");
		apostrophe.put("we've", "we,have");
		apostrophe.put("weren't", "were,not");
		apostrophe.put("what'll", "what,will");
		apostrophe.put("what're", "what,are");
		apostrophe.put("what's", "what,is");
		apostrophe.put("what've", "what,have");
		apostrophe.put("where's", "where,is");
		apostrophe.put("who'd", "who,had");
		apostrophe.put("who'll", "who,will");
		apostrophe.put("who're", "who,are");
		apostrophe.put("who's", "who,is");
		apostrophe.put("who've", "who,have");
		apostrophe.put("won't", "will,not");
		apostrophe.put("wouldn't", "would,not");
		apostrophe.put("you'd", "you,had");
		apostrophe.put("you'll", "you,will");
		apostrophe.put("you're", "you,are");
		apostrophe.put("you've", "you,have");

	}

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			while (stream.hasNext()) {
				token = stream.next();

				if (token != null) {
					if (apostrophe.containsKey(token)) {
						stream.previous();
						stream.set(apostrophe.get(token).split(","));
						
					} else if(Character.isUpperCase(token.charAt(0)) && token.contains("'s")){
						stream.previous();
						stream.set(token.replaceAll("'s", ""));
						
					} else if (token.contains("'s")) {
					
						stream.previous();
						stream.set(token.replaceAll("'s", "s"));
						
					} else if (token.contains("'")) {
						stream.previous();
						stream.set(token.replaceAll("'", ""));
					}

				}
			}
			stream.reset();
		}

	}

}
