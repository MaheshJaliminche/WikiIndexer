package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.PUNCTUATION)
public class Punctuation implements TokenizerRule   {

	@SuppressWarnings("null")
	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) {
			String token;
			
			while (stream.hasNext()) {
				token = stream.next();
				if (token != null) {
									
					stream.previous();
					token = token.replaceAll("[!.?]+$", "");
					token = token.replaceAll("([!.?]+)(\\s)", "$2");
					stream.set(token);
					token = stream.next();
					 
					 
					
				}
				
			}
			
			stream.reset();
			
			
		}
		
	}

	

}
