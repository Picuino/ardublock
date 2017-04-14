package com.ardublock.translator.block.makeblock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeIRControls extends TranslatorBlock {

	public MeIRControls(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {

		String genusName[] = this.getTranslator().getBlock(blockId).getGenusName().split("_");
		String ir_code = genusName[genusName.length - 1];
		String codes[] = {
			"a", "69",
			"b", "70",
			"c", "71",
			"d", "68",
			"e", "67",
			"f", "13",
			"0", "22",
			"1", "12",
			"2", "24",
			"3", "94",
			"4",  "8",
			"5", "28",
			"6", "90",
			"7", "66",
			"8", "82",
			"9", "74",
			"up",   "64",
			"down", "25",
			"left",  "7",
			"right", "9",
			"ctrl", "21" };
		for(int i=0; i<codes.length; i+=2)
			if (ir_code.equals(codes[i]))
				return codes[i+1];
		return "0";
	}
}
