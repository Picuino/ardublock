package com.ardublock.translator.block.makeblock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeConstant extends TranslatorBlock {

	public MeConstant(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {

		translator.addHeaderFile("MeMCore.h");

		String genusName = this.getTranslator().getBlock(blockId).getGenusName();
		String constant[] = genusName.split("_");
		return codePrefix + constant[constant.length - 1] + codeSuffix;
	}
}