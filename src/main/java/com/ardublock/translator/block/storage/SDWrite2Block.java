package com.ardublock.translator.block.storage;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SDWrite2Block extends TranslatorBlock
{
	public SDWrite2Block(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String Cs;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
		Cs = translatorBlock.toCode();
		
		translator.addHeaderFile("SD.h");
		translator.addSetupCommand("const int chipSelect = " + Cs + ";\nSD.begin(chipSelect);");
		TranslatorBlock data = this.getRequiredTranslatorBlockAtSocket(0);
		String ret= "File datafile = SD.open(\""+data.toCode()+"\", FILE_WRITE);\n";
		ret += "if (datafile) {\n";
		data = this.getRequiredTranslatorBlockAtSocket(1, "datafile.print(", ");\ndatafile.print(\" \");\n");
		ret += data.toCode();
		data=this.getRequiredTranslatorBlockAtSocket(2);
		String test=data.toCode();
	 
		  
		if(test.equals("true")){
			 ret+="datafile.println(\"\");";
		}
		ret += "datafile.close();\n";
		ret += "};\n";
		return  ret ;
	}
}
