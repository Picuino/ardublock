package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ConstrainBlock extends TranslatorBlock
{
	public ConstrainBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		String ret = "constrain(" + tb.toCode() + ", ";
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		ret = ret + tb.toCode() + ", ";
		tb = this.getRequiredTranslatorBlockAtSocket(2);
		ret = ret + tb.toCode() + ")";
		return codePrefix + ret + codeSuffix;
	}
	
}
