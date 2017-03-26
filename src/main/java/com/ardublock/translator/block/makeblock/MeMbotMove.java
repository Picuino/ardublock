package com.ardublock.translator.block.makeblock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeMbotMove extends TranslatorBlock {

	public MeMbotMove(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
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

		translator.addDefinitionCommand("MeDCMotor dcMotor1(M1);");
		translator.addDefinitionCommand("MeDCMotor dcMotor2(M2);");

		translator.addDefinitionCommand("\n" + 
		  "void _mBot_moveto(int dir, int speed) {\n" +
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
		  "};\n" );
		
		return "_mBot_moveto(" + arg1 + ", " + arg2 + ");";
	}
}