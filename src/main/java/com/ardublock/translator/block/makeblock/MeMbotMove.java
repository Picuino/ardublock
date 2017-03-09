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

		String ret = "MeDCMotor dcMotor1(M1);";
		translator.addDefinitionCommand(ret);
		ret = "MeDCMotor dcMotor2(M2);";
		translator.addDefinitionCommand(ret);
		ret =
			"\nvoid __ab_mBlock_moveto(int dir, int speed) {\n" +
			"  speed = (speed > 255) ? 255 : ((speed < -255) ? -255 : speed);\n" +
			"  if (speed == 0) dir = 0;\n" +
			"  if (dir == 1) {        // Forward\n" +
			"    dcMotor1.run(-speed);\n" +
			"    dcMotor2.run(speed);\n" +
			"  } else if (dir == 2) { // Backward\n" +
			"    dcMotor1.run(speed);\n" +
			"    dcMotor2.run(-speed);\n" +
			"  } else if (dir == 3) { // Left\n" +
			"    dcMotor1.run(speed);\n" +
			"    dcMotor2.run(speed);\n" +
			"  } else if (dir == 4) { // Right\n" +
			"    dcMotor1.run(-speed);\n" +
			"    dcMotor2.run(-speed);\n" +
			"  } else {               // Stop\n" +
			"    dcMotor1.stop();\n" +
			"    dcMotor2.stop();\n" +
			"  };\n" +
			"};\n";
		translator.addDefinitionCommand(ret);

		TranslatorBlock moveto = this.getTranslatorBlockAtSocket(0);
		TranslatorBlock speed = this.getRequiredTranslatorBlockAtSocket(1);
		return "__ab_mBlock_moveto(" + moveto.toCode() + ", " + speed.toCode() + ");";
	}
}
