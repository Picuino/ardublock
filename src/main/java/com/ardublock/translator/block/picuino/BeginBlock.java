// Picuino Panel Ardublock Library
package com.ardublock.translator.block.picuino;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class BeginBlock extends TranslatorBlock {

	public BeginBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		
		translator.addHeaderFile("PC42.h");
		translator.addHeaderFile("Wire.h");
		translator.addSetupCommand("pc.begin();");
		
		String ret = "pc.begin();";
		return ret;
	}

}
