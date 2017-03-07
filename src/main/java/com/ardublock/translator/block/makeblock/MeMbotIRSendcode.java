package com.ardublock.translator.block.makeblock;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeMbotIRSendcode extends TranslatorBlock {

	public MeMbotIRSendcode(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		translator.addHeaderFile("MeMCore.h");
		translator.addDefinitionCommand("MeIR mBotIR;");
		translator.addDefinitionCommand(
			"\nvoid _mBot_IRSendByte(uint8_t data) {\n" +
			"  union { struct { byte b3; byte b2; byte b1; byte b0; }; long l; } ldata;\n" +
			"  ldata.b3 = ~data;\n" +
			"  ldata.b2 = data;\n" +
			"  ldata.b1 = 0x00;\n" +
			"  ldata.b0 = 0xff;\n" +
			"  mBotIR.sendNEC(ldata.l, 32);\n" +
			"  delay(20);\n" +
			"};\n" );

		TranslatorBlock code = this.getTranslatorBlockAtSocket(0);
		return "_mBot_IRSendByte((uint8_t)(" + code.toCode() + "));\n";
	}

}
