package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.DELIM)
public class Delimiter implements TokenizerRule {

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		// TODO Auto-generated method stub
		if (stream != null) 
		{
			String token;
			while (stream.hasNext()) 
			{
				token = stream.next();
				if (token != null) 
				{
					stream.previous();
					stream.set(token.split("[;:,]"));
					stream.next();
				}
			}
			stream.reset();
		}
		
	}

}
