package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.Arrays;
import java.util.List;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.STOPWORDS)
public class Stopwords  implements TokenizerRule{
	
	String[] Stopwords={"a","about","above","after","again","their","against","all","into","they","you","also","am","while","an","had","me","and","any","are","as","at","be","by","com","for","from","how","in","it","of","on","or","that","the","this","he","she","his","my","to","was","your","then","him","what","when","where","who","will","with","is","do","not","of","I","i"};
	List<String> stopwordlist=Arrays.asList(Stopwords);
	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		
		if (stream != null) 
		{
			String token;
			while (stream.hasNext()) 
			{
				token = stream.next();
				if (token != null) 
				{
					
					if(stopwordlist.contains(token.toLowerCase()))
					{   
						stream.previous();
						stream.remove();
					} 
				}
					
				}
			stream.reset();
			
		   
			}
		}
		
	}


