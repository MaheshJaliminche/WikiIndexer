package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;
@RuleClass(className = RULENAMES.SPECIALCHARS)
public class SpecialCharacter implements TokenizerRule {

	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		if (stream != null) 
		{
			String token;
			//StringBuilder modifiedStream=new StringBuilder();
		     
			//token=stream.toString();
			
			while (stream.hasNext()) 
			{
				String sb;
				StringBuilder sb1 = new StringBuilder();
				StringBuilder sb2 = new StringBuilder();
				List<String> stringToSet= new ArrayList<String>();
				token = stream.next();
				sb=token;
				boolean flag= false;
				if (token != null) 
				{
					if(token.contains("@"))
					{
						token= token.replaceAll("@", " ");
						String[] s = token.split(" ");
						stream.previous();
						stream.set(s);
						stream.next();
					}
					else if(token.contains(".."))
					{
						token= token.replaceAll("\\.\\.+", " ");
						String[] s = token.split(" ");
						stream.previous();
						stream.set(s);
						stream.next();
					}
					else
					{
					Pattern P0= Pattern.compile("[^a-zA-Z0-9-.]");
					Matcher m0= P0.matcher(token);
					
					if(m0.find())
					{
						
					Pattern P1= Pattern.compile("^([a-zA-Z0-9]+)[\\+\\*\\/\\^]");
					Matcher m1= P1.matcher(token);
					
						
						while(m1.find())
						{		
							stringToSet.add(m1.group(1));
							sb1.append(m1.group());
							flag=true;
							
							sb=sb.replace(sb1, "");
							
							Pattern P3= Pattern.compile("(\\w+)[\\+\\*\\/\\^]");
							Matcher m3= P3.matcher(sb.toString());
							while(m3.find()){
								stringToSet.add(m3.group(1));
								sb2.append(m3.group());
							}
							
						}
						sb=sb.replace(sb2, "");
						
						Pattern P2= Pattern.compile("^[a-zA-Z0-9]+");
						Matcher m2= P2.matcher(sb);
						
						if(m2.find())
					    {
							stringToSet.add(m2.group(0));
							flag=true;
					    }
						//String[] stringToAdd
						stream.previous();
						String[] tokenizedText = stringToSet.toArray(new String[stringToSet.size()]); 
						stream.set(tokenizedText);
						stream.next();
					}
						if(!flag)
						{
						 stream.previous();
						 Pattern p4 = Pattern.compile("(^[.])([.]*)(\\w+)");
						 Matcher m4 = p4.matcher(token);
						 while(m4.find()){
							 token = token.replaceAll(m4.group(1)+m4.group(2),"");
							 stream.set(token);
						 }
						 token = token.replaceAll("[^a-zA-Z0-9-.]","");
						 stream.set(token);
					     
					     if(token.matches("^[^a-zA-Z0-9-.]+$") || token.equals("")){
					    	 stream.remove();
					     }else{
					    	 stream.next();
					     }
					     
						}
				}
				}
			}
			stream.reset();
		}
		
	}

}
