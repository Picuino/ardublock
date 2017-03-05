package com.ardublock.translator.block.makeblock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeMotorDriver extends TranslatorBlock {

	public MeMotorDriver(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		translator.addHeaderFile("SoftwareSerial.h");
		translator.addHeaderFile("Wire.h");
		translator.addHeaderFile("MeMCore.h");

		TranslatorBlock block = this.getTranslatorBlockAtSocket(0);
		
		String motor = block.toCode();
		String ret = "MeDCMotor dcMotor" + motor + "(M" + motor + ");";
		translator.addDefinitionCommand(ret);
		
		block = this.getRequiredTranslatorBlockAtSocket(1);
		if(block instanceof NumberBlock) {
			int speed = Integer.parseInt(block.toCode());
			speed = speed>255 ? 255:(speed<-255 ? -255:speed);
			if(speed==0){
				return motor + ".stop();\n";
			}else{
				return motor + ".run(" + speed + ");\n";
			}
		} else {
			return motor + ".run(" + block.toCode() + ");\n";
		}		
	}

}
