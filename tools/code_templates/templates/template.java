package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class {{block.translator.name}} extends TranslatorBlock {

	public {{block.translator.name}}(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {

		{% if block.translator.headers %}
		  {% for header in block.translator.headers %}
		translator.addHeaderFile("{{header}}");
		  {% endfor %}

		{% endif %}
		{% if block.parameter %}
		  {% set count = 0 %}
		TranslatorBlock translatorBlock;
		  {% for input in block.parameter %}
		    {% if input.type != 'cmd' %}
		translatorBlock = this.getRequiredTranslatorBlockAtSocket({{count}});
		      {% set count = count + 1 %}
		String arg{{count}} = translatorBlock.toCode();
		    {% endif %}
		  {% endfor %}

		{% endif %}
		{% if block.translator.definitions %}
		  {% for definition in block.translator.definitions %}
		translator.addDefinitionCommand("{{definition}}");
		  {% endfor %}

		{% endif %}
		{% if block.translator.setup %}
		  {% for setup in block.translator.setup %}
		translator.addSetupCommand("{{setup}}");
		  {% endfor %}

		{% endif %}
		{% for code in block.translator.code %}
		{{code}}
		{% endfor %}
	}
}