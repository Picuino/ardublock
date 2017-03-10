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
		String code_name = this.getTranslator().getBlock(blockId).getGenusName();
		String ir_code = code_name.split("_")[2];
		if (ir_code == "a") return "69";
		if (ir_code == "b") return "70";
		if (ir_code == "c") return "71";
		if (ir_code == "d") return "68";
		if (ir_code == "e") return "67";
		if (ir_code == "f") return "13";
		if (ir_code == "0") return "22";
		if (ir_code == "1") return "12";
		if (ir_code == "2") return "24";
		if (ir_code == "3") return "94";
		if (ir_code == "4") return "8";
		if (ir_code == "5") return "28";
		if (ir_code == "6") return "90";
		if (ir_code == "7") return "66";
		if (ir_code == "8") return "82";
		if (ir_code == "9") return "74";
		if (ir_code == "up") return "64";
		if (ir_code == "down") return "25";
		if (ir_code == "left") return "7";
		if (ir_code == "right") return "9";
		if (ir_code == "ctrl") return "21";
		return "0";
	}
}
