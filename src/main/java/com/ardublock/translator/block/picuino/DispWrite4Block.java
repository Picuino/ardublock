// Picuino Panel Ardublock Library
package com.ardublock.translator.block.picuino;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class DispWrite4Block extends TranslatorBlock {

	public DispWrite4Block(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String digit1 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		String digit2 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
		String digit3 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
		String digit4 = translatorBlock.toCode();
		
		translator.addHeaderFile("PC42.h");
		translator.addHeaderFile("Wire.h");
		translator.addSetupCommand("pc.begin();");
		
		String ret = "pc.dispWrite(" + digit1 + ", " +  digit2 + ", " +  digit3 + ", " +  digit4 + ");";
		return ret;
	}

}
