package com.ardublock.translator.block.makeblock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeLedRGBReset extends TranslatorBlock {

	public MeLedRGBReset(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		translator.addHeaderFile("SoftwareSerial.h");
		translator.addHeaderFile("Wire.h");
		translator.addHeaderFile("MeMCore.h");
		
		String ret = "MeRGBLed rgbled(7, 2);";
		translator.addDefinitionCommand(ret);
		
		ret = "rgbled.setColor(1, 0, 0, 0);\n" +
		      "rgbled.setColor(2, 0, 0, 0);\n" +
		      "rgbled.show();";
		return ret;
	}
}
