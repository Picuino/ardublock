package com.ardublock.translator.block.scoop;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SCoopPinEventBlock extends SCoopTaskBlock
{

	public static final String FUNCTION_IS_EVENT_TRIGGERED = 
			"bool isABEventTriggered(int trigFlag, int lastStatus, int currentStatus) {\n" + 
			"  switch (trigFlag) {\n" + 
			"    case (0):  //LOW\n" + 
			"      return !currentStatus;\n" + 
			"    case (1):  //HIGH\n" + 
			"      return (bool)currentStatus;\n" + 
			"    case (2):  //FALLING\n" + 
			"      return (lastStatus!=currentStatus && LOW==currentStatus);\n" + 
			"    case (3):  //RISING\n" + 
			"      return (lastStatus!=currentStatus && HIGH==currentStatus);\n" + 
			"    case (4):  //CHANGE\n" + 
			"      return (lastStatus != currentStatus);\n" + 
			"    default:\n" + 
			"      return false;\n" + 
			"  }\n" + 
			"}\n\n";
	
	public SCoopPinEventBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		//initialize
		StringBuffer taskSetupCommandBuffer = new StringBuffer();
		StringBuffer taskLoopCommandBuffer = new StringBuffer();
		translator.addDefinitionCommand(FUNCTION_IS_EVENT_TRIGGERED);
		
		//read pin number to moniting
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String pinNumber = translatorBlock.toCode().trim();
		
		if (translatorBlock instanceof NumberBlock)
		{
			translator.addInputPin(pinNumber);
		}
		else
		{
			taskSetupCommandBuffer.append("pinMode(" + pinNumber + ", INPUT);\n");
		}
		
		//generate lastStatus variable
		String lastStatusVariableName = translator.buildVariableName("pin_event_" + pinNumber);
		
		//setup setup command
		translator.addDefinitionCommand(String.format("volatile int %s = 0;", lastStatusVariableName));
		taskSetupCommandBuffer.append(String.format("%s = digitalRead(%s);\n", lastStatusVariableName, pinNumber));
		
		
		//setup loop command
		taskLoopCommandBuffer.append(String.format("volatile int abvarCurrentStatus = digitalRead(%s);\n", pinNumber));

		//read trig flag
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		String trigFlag = translatorBlock.toCode();
		
		taskLoopCommandBuffer.append(String.format("if (isABEventTriggered(%s, %s, %s)) {\n", trigFlag, lastStatusVariableName, "abvarCurrentStatus"));

		//insert event action body
		translatorBlock = getTranslatorBlockAtSocket(2);
		while (translatorBlock != null)
		{
			taskLoopCommandBuffer.append(translatorBlock.toCode());
			translatorBlock = translatorBlock.nextTranslatorBlock();
		}
		
		//add enclosing bracket
		taskLoopCommandBuffer.append("}\n");
		taskLoopCommandBuffer.append(String.format("%s = %s;\n", lastStatusVariableName, "abvarCurrentStatus"));
		
		String ret = super.generateScoopTask(taskSetupCommandBuffer.toString(), taskLoopCommandBuffer.toString());
		return ret;
	}
}