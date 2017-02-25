package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class BoolToInt extends TranslatorBlock
{
	public BoolToInt(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		
		String value;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		value = translatorBlock.toCode();

		String ret = "(int)(" + value + ")";
		return codePrefix + ret + codeSuffix;
	}	
}
