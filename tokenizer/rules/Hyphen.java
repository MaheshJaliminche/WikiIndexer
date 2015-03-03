package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.HYPHEN)
public class Hyphen implements TokenizerRule {

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) 
		{
			String token;
			//StringBuilder modifiedStream=new StringBuilder();
			//List<String> MmodifiedStream= new ArrayList<String>();
			//token=stream.toString();
			while (stream.hasNext()) 
			{
				token = stream.next();
				if (token != null) 
				{
					
					stream.previous();
					token = token.replaceAll("^([a-zA-Z]+)(\\-)((([a-zA-Z]+)\\s)|(([a-zA-Z]+)$))", "$1 $3");
					token = token.replaceAll("\\s([a-zA-Z]+)(\\-)((([a-zA-Z]+)\\s)|(([a-zA-Z]+)$))", "$1 $3");
					token = token.replaceAll("([a-zA-Z]+)(\\-\\-+)$", "$1");
					token = token.replaceAll("([a-zA-Z]+)(\\-\\-+)\\s", "$1");
					token = token.replaceAll("^(\\-\\-+)([a-zA-Z]+)", "$2");
					token = token.replaceAll("(\\s)(\\-\\-+)([a-zA-Z]+)", "$2");
					stream.set(token);
					if(token.matches("\\s[-]+\\s")){
						stream.remove();
					} else{
						stream.next();
					}
					
					
					
				}
				
			}
			stream.reset();
			
		}
		
	}

	
}
