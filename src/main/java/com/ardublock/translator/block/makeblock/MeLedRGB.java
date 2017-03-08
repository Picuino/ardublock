package com.ardublock.translator.block.makeblock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeLedRGB extends TranslatorBlock {

	public MeLedRGB(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		translator.addHeaderFile("SoftwareSerial.h");
		translator.addHeaderFile("Wire.h");
		translator.addHeaderFile("MeMCore.h");

		
		String ret = "MeRGBLed rgbled(7, 2);";
		translator.addDefinitionCommand(ret);

		TranslatorBlock lednum = this.getTranslatorBlockAtSocket(0);
		TranslatorBlock red = this.getTranslatorBlockAtSocket(1);
		TranslatorBlock green = this.getTranslatorBlockAtSocket(2);
		TranslatorBlock blue = this.getTranslatorBlockAtSocket(3);
		
		ret = "rgbled.setColor(" + lednum.toCode() + ", " + red.toCode() + ", " + green.toCode() + ", " + blue.toCode() + ");\n";
		ret = ret + "rgbled.show();";
		return ret;
	}
}
