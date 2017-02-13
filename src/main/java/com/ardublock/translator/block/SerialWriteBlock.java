package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SerialWriteBlock extends TranslatorBlock
{
	public SerialWriteBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		
		// translator.addHeaderFile("Serial.h");
		translator.addSetupCommand("Serial.begin(9600);");

		String ret = translatorBlock.toCode();

		ret= "Serial.write(" + ret + ");\n";
		return ret;
	}
}
