package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class HexNumberBlock extends TranslatorBlock
{
	public HexNumberBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode()
	{
		return codePrefix + "0x" + label + codeSuffix;
	}

}
