package com.ardublock.translator.block.makeblock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeMbotBuzzer extends TranslatorBlock {

	public MeMbotBuzzer(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		translator.addHeaderFile("MeMCore.h");

		translator.addDefinitionCommand("MeBuzzer buzzer;");

		TranslatorBlock freq = this.getTranslatorBlockAtSocket(0);
		TranslatorBlock timing = this.getTranslatorBlockAtSocket(1);
		return "buzzer.tone(" + freq.toCode() + ", " + timing.toCode() + ");";
	}
}
