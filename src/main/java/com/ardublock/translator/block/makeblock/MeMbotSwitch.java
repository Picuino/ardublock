package com.ardublock.translator.block.makeblock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeMbotSwitch extends TranslatorBlock {

	public MeMbotSwitch(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String setupCode = "pinMode(A7, INPUT);";
		translator.addSetupCommand(setupCode);
		
		String ret = "(analogRead(A7) < 512)";
		return codePrefix + ret + codeSuffix;
	}
}
