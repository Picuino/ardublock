package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class CharToInt extends TranslatorBlock
{
	public CharToInt(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		
		String Pin;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		Pin = translatorBlock.toCode();
		
		
		String ret = "((int)('" + Pin.replaceAll("\"", "") + "'))";
		return codePrefix + ret + codeSuffix;
	}	
}
