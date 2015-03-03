package edu.buffalo.cse.ir.wikiindexer.tokenizer.rules;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenStream;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.TokenizerException;
import edu.buffalo.cse.ir.wikiindexer.tokenizer.rules.TokenizerRule.RULENAMES;

@RuleClass(className = RULENAMES.ACCENTS)
public class Accent implements TokenizerRule {

	Map<String, String> accents = new HashMap<String, String>();
	
	public Accent(){
		accents.put("À", "A");
		accents.put("Á", "A");
		accents.put("Â", "A");
		accents.put("Ã", "A");
		accents.put("Ä", "A");
		accents.put("Å", "A");
		accents.put("Æ", "A");
		accents.put("Ç", "C");
		accents.put("È", "E");
		accents.put("É", "E");
		accents.put("Ê", "E");
		accents.put("Ë", "E");
		accents.put("Ì", "I");
		accents.put("Í", "I");
		accents.put("Î", "I");
		accents.put("Ï", "I");
		accents.put("Ĳ", "I");
		accents.put("Ð", "D");
		accents.put("Ñ", "N");
		accents.put("Ò", "O");
		accents.put("Ó", "O");
		accents.put("Ô", "O");
		accents.put("Õ", "O");
		accents.put("Ö", "O");
		accents.put("Ø", "O");
		accents.put("Œ", "OE");
		accents.put("Þ", "TH");
		accents.put("Ù", "U");
		accents.put("Ú", "U");
		accents.put("Û", "U");
		accents.put("Ü", "U");
		accents.put("Ý", "Y");
		accents.put("Ÿ", "Y");
		accents.put("à", "a");
		accents.put("á", "a");
		accents.put("â", "a");
		accents.put("ã", "a");
		accents.put("ä", "a");
		accents.put("å", "a");
		accents.put("æ", "ae");
		accents.put("ç", "c");
		accents.put("è", "e");
		accents.put("é", "e");
		accents.put("ê", "e");
		accents.put("ë", "e");
		accents.put("ì", "i");
		accents.put("í", "i");
		accents.put("î", "i");
		accents.put("ï", "i");
		accents.put("ĳ", "ij");
		accents.put("ð", "d");
		accents.put("ñ", "n");
		accents.put("ò", "o");
		accents.put("ó", "o");
		accents.put("ô", "o");
		accents.put("õ", "o");
		accents.put("ö", "o");
		accents.put("ø", "o");
		accents.put("œ", "oe");
		accents.put("ß", "ss");
		accents.put("þ", "th");
		accents.put("ù", "u");
		accents.put("ú", "u");
		accents.put("û", "u");
		accents.put("ü", "u");
		accents.put("ý", "y");
		accents.put("ÿ", "y");
		accents.put("ﬁ", "fi");
		accents.put("ﬂ", "fl");
		accents.put("ü", "u");
		accents.put("ü", "u");
		accents.put("ü", "u");
		
	}
	
	@Override
	public void apply(TokenStream stream) throws TokenizerException {
		
		
		// TODO Auto-generated method stub
		if (stream != null) 
		{
			String token = null;
			while (stream.hasNext()) 
			{
				token = stream.next();
				if (token != null) 
				{
					stream.previous();
					String nrml = Normalizer.normalize(token, Normalizer.Form.NFD);
			        StringBuilder stripped = new StringBuilder();
			        for (int i=0;i<nrml.length();++i)
			        {
			            if (Character.getType(nrml.charAt(i)) != Character.NON_SPACING_MARK)
			            {
			            	if(accents.containsKey(nrml.charAt(i))){
			            		stripped.append(accents.get(nrml.charAt(i)));
			            	}else {
			            		stripped.append(nrml.charAt(i));
			            	}
			            }
			        }
					token = stripped.toString();
					
					nrml = Normalizer.normalize(token, Normalizer.Form.NFC);
			        stripped = new StringBuilder();
			        for (int i=0;i<nrml.length();++i)
			        {
			            if (Character.getType(nrml.charAt(i)) != Character.NON_SPACING_MARK)
			            {
			                stripped.append(nrml.charAt(i));
			            }
			        }
					token = stripped.toString();
					
					nrml = Normalizer.normalize(token, Normalizer.Form.NFKC);
			        stripped = new StringBuilder();
			        for (int i=0;i<nrml.length();++i)
			        {
			            if (Character.getType(nrml.charAt(i)) != Character.NON_SPACING_MARK)
			            {
			                stripped.append(nrml.charAt(i));
			            }
			        }
					token = stripped.toString();
					
					nrml = Normalizer.normalize(token, Normalizer.Form.NFKD);
			        stripped = new StringBuilder();
			        for (int i=0;i<nrml.length();++i)
			        {
			            if (Character.getType(nrml.charAt(i)) != Character.NON_SPACING_MARK)
			            {
			                stripped.append(nrml.charAt(i));
			            }
			        }
					token = stripped.toString();
					
			    stream.set(token);
			    stream.next();
				}
			}
			stream.reset();
		}
		
	}

}