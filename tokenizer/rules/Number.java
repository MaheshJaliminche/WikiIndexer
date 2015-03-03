package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.NUMBERS)
public class Number implements TokenizerRule {

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		StringBuilder sb;
		if (stream != null) 
		{
			String token;
			while (stream.hasNext()) 
			{
				token = stream.next();
				if (token != null) 
				{
					boolean flag= true;
					
					Pattern P1= Pattern.compile("(\\d+)(\\/|\\%)(\\d*)");
					Matcher m1= P1.matcher(token);
					
						sb= new StringBuilder();
						while(m1.find())
						{		
							sb.append(m1.group(2));
							flag=false;
						}
						if(!flag)
						{
						stream.previous();
						stream.set(sb.toString());
						}
						if(flag)
						{
					Pattern P2= Pattern.compile("(\\d+)");
					Matcher m2= P2.matcher(token);
					if(m2.find())
					{
						stream.previous();
						stream.remove();
					
					}
						}
					
					
					
				}
			}
			stream.reset();
		}
		
	}

}
