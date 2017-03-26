package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeMbotIRSendcode extends TranslatorBlock {

	public MeMbotIRSendcode(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {

		translator.addHeaderFile("MeMCore.h");

		TranslatorBlock translatorBlock;
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String arg1 = translatorBlock.toCode();

		translator.addDefinitionCommand("MeIR mBotIR;");

		translator.addDefinitionCommand("\n" +
		  "void _mBot_IRSendByte(uint8_t data) {\n" +
		  "  union {\n" +
		  "    struct {\n" +
		  "      byte b3; byte b2; byte b1; byte b0;\n" +
		  "    };\n" +
		  "    long l;\n" +
		  "  } ldata;\n" +
		  "  ldata.b3 = ~data; ldata.b2 = data; ldata.b1 = 0x00; ldata.b0 = 0xff;\n" +
		  "  mBotIR.sendNEC(ldata.l, 32);\n" +
		  "  delay(20);\n" +
		  "};\n" );
		
		return "_mBot_IRSendByte((uint8_t)(" + arg1 + "));";
	}
}