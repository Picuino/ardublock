package com.ardublock.translator.block.makeblock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeBluetoothCommand extends TranslatorBlock {

	public MeBluetoothCommand(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
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

		translator.addDefinitionCommand("MeBluetooth bluetooth" + arg1 + "(PORT_" + arg1 + ");");

		translator.addSetupCommand("bluetooth" + arg1 + ".begin(9600);");

		return "bluetooth" + arg1 + ".getParamValue(\"" + arg2.replace("\"", "") + "\")";
	}
}