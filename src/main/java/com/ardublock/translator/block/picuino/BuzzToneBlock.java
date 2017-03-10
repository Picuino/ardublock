// Picuino Panel Ardublock Library
package com.ardublock.translator.block.picuino;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class BuzzToneBlock extends TranslatorBlock {

	public BuzzToneBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String tone = translatorBlock.toCode();
		
		translator.addHeaderFile("PC42.h");
		translator.addHeaderFile("Wire.h");
		translator.addSetupCommand("pc.begin();");
		
		String ret = "pc.buzzTone(" + tone + ");";
		return ret;
	}

}
