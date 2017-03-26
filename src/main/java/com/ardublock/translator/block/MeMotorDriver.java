package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeMotorDriver extends TranslatorBlock {

	public MeMotorDriver(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {

		translator.addHeaderFile("MeMCore.h");

		TranslatorBlock translatorBlock;
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String arg1 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		String arg2 = translatorBlock.toCode();

		translator.addDefinitionCommand("MeDCMotor dcMotor" + arg1 + "(M" + arg1 + ");");

		TranslatorBlock block = this.getRequiredTranslatorBlockAtSocket(1);
		if(block instanceof NumberBlock) {
		   int speed = Integer.parseInt(block.toCode());
		   speed = speed > 255 ? 255 : (speed < -255 ? -255 : speed);
		   if (Integer.parseInt(arg1) == 2) speed = -speed;
		   if (speed == 0) {
		      return "dcMotor" + arg1 + ".stop();";
		   } else {
		      return "dcMotor" + arg1 + ".run(" + speed + ");";
		   }
		} else {
		   if (Integer.parseInt(arg1) == 2) {
		      return "dcMotor" + arg1 + ".run(-(" + block.toCode() + "));";
		   }
		   else {
		      return "dcMotor" + arg1 + ".run(" + block.toCode() + ");";
		   }
		}
	}
}