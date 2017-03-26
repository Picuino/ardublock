package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeStepperMotor extends TranslatorBlock {

	public MeStepperMotor(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
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
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
		String arg3 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
		String arg4 = translatorBlock.toCode();

		translator.addDefinitionCommand("MeStepperMotor stepper" + arg1 + "(PORT_" + arg1 + ");");

		translator.addSetupCommand("stepper" + arg1 + ".begin(STP_SIXTEENTH, " + arg2 + ", " + arg3 + ");");

		String ret = "stepper" + arg1 + ".moveTo(" + arg4 + ");\n";
		ret += "int distance = stepper" + arg1 + ".distanceToGo();\n";
		ret += "if (distance==0) {\n";
		TranslatorBlock execBlock = this.getTranslatorBlockAtSocket(4);
		String exec = "";
		while (execBlock != null) {
			exec += "\t" + execBlock.toCode() + "\n";
			execBlock = execBlock.nextTranslatorBlock();
		}
		ret += exec + "\n};";
		return ret;
	}
}