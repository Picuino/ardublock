package com.ardublock.translator.block.makeblock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeMbotMove extends TranslatorBlock {

	public MeMbotMove(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		translator.addHeaderFile("SoftwareSerial.h");
		translator.addHeaderFile("Wire.h");
		translator.addHeaderFile("MeMCore.h");

		String ret = "MeDCMotor dcMotor_left(M1);";
		translator.addDefinitionCommand(ret);
		ret = "MeDCMotor dcMotor_right(M2);";
		translator.addDefinitionCommand(ret);
		ret =
			"\nvoid __ab_mBlock_moveto(int dir, int speed) {\n" +
			"  dir = (dir > 4) ? 1 : ((dir < 1) ? 1 : dir);\n" +
			"  speed = (speed > 255) ? 255 : ((speed < -255) ? -255 : speed);\n" +
			"  if (speed == 0) {\n" +
			"    dcMotor_left.stop();\n" +
			"    dcMotor_right.stop();\n" +
			"    return;\n" +
			"  }\n" +
			"  if (dir == 1) {        // Forward\n" +
			"    dcMotor_left.run(-speed);\n" +
			"    dcMotor_right.run(speed);\n" +
			"  } else if (dir == 2) { // Backward\n" +
			"    dcMotor_left.run(speed);\n" +
			"    dcMotor_right.run(-speed);\n" +
			"  } else if (dir == 3) { // Left\n" +
			"    dcMotor_left.run(speed);\n" +
			"    dcMotor_right.run(speed);\n" +
			"  } else {               // Right\n" +
			"    dcMotor_left.run(-speed);\n" +
			"    dcMotor_right.run(-speed);\n" +
			"  }\n" +
			"}\n";
		translator.addDefinitionCommand(ret);

		TranslatorBlock moveto = this.getTranslatorBlockAtSocket(0);
		TranslatorBlock speed = this.getRequiredTranslatorBlockAtSocket(1);
		return "__ab_mBlock_moveto(" + moveto.toCode() + ", " + speed.toCode() + ");\n";
	}
}
