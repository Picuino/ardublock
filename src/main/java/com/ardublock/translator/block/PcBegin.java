package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class PcBegin extends TranslatorBlock {

	public PcBegin(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {

		translator.addHeaderFile("PC42.h");
		translator.addHeaderFile("Wire.h");

		translator.addSetupCommand("pc.begin();");

		String functionName = this.getTranslator().getBlock(blockId).getGenusName();
		if (functionName == "pc_keyBegin")
			return "pc.keyBegin();";
		else if (functionName == "pc_ledBegin")
			return "pc.ledBegin();";
		else if (functionName == "pc_buzzBegin")
			return "pc.buzzBegin();";
		else if (functionName == "pc_dispBegin")
			return "pc.dispBegin();";
		else
			return "pc.begin();";
	}
}