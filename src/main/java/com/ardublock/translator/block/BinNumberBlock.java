package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class BinNumberBlock extends TranslatorBlock
{
	public BinNumberBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode()
	{
		return codePrefix + "0b" + label + codeSuffix;
	}

}
