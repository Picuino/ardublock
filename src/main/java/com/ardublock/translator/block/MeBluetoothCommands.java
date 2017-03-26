package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeBluetoothCommands extends TranslatorBlock {

	public MeBluetoothCommands(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {

		translator.addHeaderFile("MeMCore.h");

		TranslatorBlock translatorBlock;
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String arg1 = translatorBlock.toCode();

		TranslatorBlock execBlock = this.getTranslatorBlockAtSocket(1);
		String exec = "if (bluetooth" + arg1 + ".paramAvailable()) {\n";
		while (execBlock != null) {
			exec += "\t" + execBlock.toCode()+ "\n";
			execBlock = execBlock.nextTranslatorBlock();
		}
		return exec + "};\n";
	}
}