package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeShutter extends TranslatorBlock {

	public MeShutter(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {

		translator.addHeaderFile("MeMCore.h");

		TranslatorBlock translatorBlock;
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String arg1 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		String arg2 = translatorBlock.toCode();

		translator.addDefinitionCommand("MeShutter shutter" + arg1 + "(PORT_" + arg1 + ");");

		int stateId = Integer.parseInt(arg2);
		String ret;
		if (stateId == 1) ret = ".shotOn()";
		else if (stateId == 2) ret = ".shotOff()";
		else if (stateId == 2) ret = ".focusOn()";
		else ret = ".focusOff()";
		return "shutter" + arg1 + ret + ";";
	}
}