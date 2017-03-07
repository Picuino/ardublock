package com.ardublock.translator.block.makeblock;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeMbotLightSensor extends TranslatorBlock {

	public MeMbotLightSensor(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		translator.addHeaderFile("MeMCore.h");

		String ret = "MeLightSensor mBotLightSensor(6);";
		translator.addDefinitionCommand(ret);

		return "mBotLightSensor.read()";
	}

}
