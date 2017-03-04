// Picuino Panel Ardublock Library
package com.ardublock.translator.block.picuino;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class BuzzPlayBlock extends TranslatorBlock {

	public BuzzPlayBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String tone = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		String time = translatorBlock.toCode();
		
		translator.addHeaderFile("PC42.h");
		translator.addHeaderFile("Wire.h");
		translator.addSetupCommand("  pc.begin();");
		
		String ret = "  pc.buzzPlay(" + tone + ", " + time + ");\n";
		return ret;
	}

}
