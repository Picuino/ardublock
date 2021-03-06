package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Toggle extends TranslatorBlock
{
	public static final String ARDUBLOCK_DIGITAL_WRITE_DEFINE = "void __ardublockDigitalWrite(int pinNumber, boolean status) {\npinMode(pinNumber, OUTPUT);\ndigitalWrite(pinNumber, status);\n};\n";
	
	public Toggle(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String portNum = translatorBlock.toCode();
		
		
		if (translatorBlock instanceof NumberBlock)
		{
			translator.addOutputPin(portNum.trim());
		}
		else
		{
			String setupCode = "pinMode(" + portNum + ", OUTPUT);";
			translator.addSetupCommand(setupCode);
		}

		String ret = "digitalWrite(" + portNum + ", !digitalRead(" + portNum + "));";
		return ret;
	}

}
