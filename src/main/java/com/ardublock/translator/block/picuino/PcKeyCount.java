package com.ardublock.translator.block.picuino;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class PcKeyCount extends TranslatorBlock {

	public PcKeyCount(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {

		translator.addHeaderFile("PC42.h");
		translator.addHeaderFile("Wire.h");

		TranslatorBlock translatorBlock;
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String arg1 = translatorBlock.toCode();

		translator.addSetupCommand("pc.begin();");

		String functionName = this.getTranslator().getBlock(blockId).getGenusName();
		String ret = "";
		if (functionName.equals("pc_keyCount"))
			ret = "pc.keyCount";
		else if (functionName.equals("pc_keyTimeOn"))
			ret = "pc.keyTimeOn";
		else if (functionName.equals("pc_keyTimeOff"))
			ret = "pc.keyTimeOff";
		return codePrefix + ret + "(" + arg1 + ")" + codeSuffix;
	}
}