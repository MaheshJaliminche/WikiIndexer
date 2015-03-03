package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.CAPITALIZATION)
public class Capitalization implements TokenizerRule {

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		int isFirstWord = 0; 
		if (stream != null) 
		{
			String token;
			while (stream.hasNext()) 
			{
				token = stream.next();
				boolean convert = true;
				if (token != null) 
				{
					int ctr = 0;
					for(Character c:token.toCharArray()){
						if(ctr > 0){
							if(Character.isUpperCase(c)){
								convert = false;
							}
						}
						ctr++;
					}
					if(isFirstWord==0 && !token.equals(token.toUpperCase()) && convert){
						stream.previous();
						stream.set(token.toLowerCase());
						isFirstWord++;
						stream.next();
					}
					else if(!token.equals(token.toUpperCase()) && !Character.isUpperCase(token.charAt(0)) && convert)
					{
						stream.previous();
						stream.set(token.toLowerCase());
						stream.next();
					}
					
			}
		}
			stream.reset();
		
	}

  }
}
