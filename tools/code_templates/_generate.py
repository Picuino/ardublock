"""
Render code for Ardublock configuration files and java files
from yaml database
"""
import os
import yaml
import codecs
import copy
from jinja2 import Template

#
# Templates
#
templates = {
  'blocks': """\t\t<!-- BlockGenus {{library.name}} -->
{% for block in blocks %}
\t\t<BlockGenus name="{{block.name}}" kind="{{block.kind}}" color="{{library.color}}" initlabel="bg.{{block.name}}"
    {%- if block.editable %} editable-label="{{block.editable}}"{% endif %}>
  {% if block.description %}
\t\t\t<description> <text>{{block.description}}</text> </description>
  {% endif %}
  {% if block.parameter or block.return %}
\t\t\t<BlockConnectors>
    {% if block.return %}
\t\t\t\t<BlockConnector connector-type="{{block.return.type}}" connector-kind="plug"
      {%- if block.return.position %} position-type="{{block.return.position}}"{% endif %} />
    {% endif %}
    {% if block.parameter %}
      {% for parameter in block.parameter %}
        {% if parameter.default %}
\t\t\t\t<BlockConnector connector-type="{{parameter.type}}" connector-kind="socket" label="{{parameter.label}}">
\t\t\t\t\t<DefaultArg genus-name="{{parameter.default[0]}}" label="{{parameter.default[1]}}" />
\t\t\t\t</BlockConnector>
        {% else %}
\t\t\t\t<BlockConnector connector-type="{{parameter.type}}" connector-kind="socket" label="{{parameter.label}}" />
        {% endif %}
      {% endfor %}
    {% endif %}
\t\t\t</BlockConnectors>
  {% endif %}
\t\t</BlockGenus>

{% endfor %}
\t\t<!-- End BlockGenus {{library.name}} -->\n
""",


  'families': """{% if families %}
\t\t<!-- BlockFamily {{library.name}} -->
{% for family in families %}
\t\t<BlockFamily>
  {% for member in family %}
\t\t\t<FamilyMember>{{member}}</FamilyMember>
  {% endfor %}
\t\t</BlockFamily>
{% endfor %}
\t\t<!-- End BlockFamily {{library.name}} -->\n
{% endif %}""",


  'menus': """
\t\t\t<!-- BlockDrawer {{library.name}} -->
\t\t\t<BlockDrawer button-color="{{library.color}}" name="bd.{{library.name}}" type="factory">
{% for member in library.menu %}
\t\t\t\t<BlockGenusMember>{{member}}</BlockGenusMember>
{% endfor %}
\t\t\t</BlockDrawer>
""",


  'properties': """
# Library {{library.name}}
bd.{{library.name}}={{library.description}}
{% for property in properties %}
{{property[0]}}={{property[1]}}
{% endfor %}
""",


  'mapping': """
# Library {{library.name}}
{% for block in blocks %}
  {% if block.name is iterable and block.name is not string %}
    {% for name in block.name -%}
{{name}}=com.ardublock.translator.block.{{block.translator.name}} 
    {% endfor %}
  {% else -%}
{{block.name}}=com.ardublock.translator.block.{{block.translator.name}}
  {% endif %}
{% endfor %}
""",
}


def main():
  """Generate Ardublock sourcecode from *.yaml databases"""
  source_tree = []
  for f in os.listdir('.'):
    if '.yaml' in f[-5:].lower():
      print 'Read ' + f
      source_tree.append(read_yaml(f))
  print
  render_code(source_tree)


def render_code(source_tree):
  # Render code
  blocks, families, menus, properties, mapping = [], [], [], [], []

  for source in source_tree:
    print 'Process ' + source['Library']['name']
    blocks.append(render(templates['blocks'], source, verbose=True))
    families.append(render(templates['families'], source))
    menus.append(render(templates['menus'], source))
    properties.append(render(templates['properties'], source))
    mapping.append(render(templates['mapping'], source))
    render_java(source)

  return

  template_string = read('ardublock.xml.template')
  tmpl = Template(template_string, trim_blocks=True, lstrip_blocks=True)
  xml_code = tmpl.render(blocks=blocks, families=families, menus=menus)
  write('build/ardublock.xml', xml_code)
  write('build/ardublock.properties', properties)
  write('build/block-mapping.properties', mapping)


def render(template_string, source, verbose=False):
  blocks = expand_blocks(source)
  properties = extract_properties(source, verbose)
  tmpl = Template(template_string, trim_blocks=True, lstrip_blocks=True)
  return tmpl.render(library=source['Library'],
                     blocks=blocks,
                     families=source['Families'],
                     properties=properties)


def expand_blocks(source):
  blocks = []
  for block in source['Blocks']:
    if not 'kind' in block:
      if 'return' in block and block['return']:
        block['kind'] = 'data'
      else:
        block['kind'] = 'command'
    if type(block['name']) is list:
      for name in block['name']:
        newblock = copy.copy(block)
        newblock['name'] = name
        blocks.append(newblock)
    else:
      blocks.append(block)
  return  blocks


def extract_properties(source, verbose=False):
  # Extract common properties
  properties = []
  if 'Properties' in source:
    for _property, _description in source['Properties'].iteritems():
      property_append(properties, _property, _description)

  # Extract block properties
  for _block in source['Blocks']:
    if  'properties' in _block:
      for _property, _description in _block['properties'].iteritems():
        property_append(properties, _property, _description)
    if 'parameter' in _block and _block['parameter']:
      for parameter in _block['parameter']:
        if 'label' in parameter:
          property_append(properties, parameter['label'], None)

  # Print undefined properties
  if verbose:
    for _property in properties:
      if not _property[1]:
        print '  Undefined property: %s' % _property[0]

  return properties


def property_append(_properties, _property, _description):
  # Search all saved properties
  for i in range(len(_properties)):
    if _properties[i][0] == _property:
      if _description and _properties[i][1]:
        print '  Duplicated property: %s = %s' % (_property, _description)
        print _properties
        print i
        asdfasdf
      elif _description and not _properties[i][1]:
        _properties[i][1] = _description
      return
  # New property
  _properties.append([_property, _description])


def render_java(source):
  java_template = """package com.ardublock.translator.block;

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
"""
  functions = []
  tmpl = Template(java_template, trim_blocks=True, lstrip_blocks=True)
  for block in source['Blocks']:
    if not ('translator' in block and block['translator']):
      continue
    if not 'name' in block['translator']:
      continue
    code = tmpl.render(block=block)
    filename = block['translator']['name'] + '.java'
    if not filename in functions:
      functions.append(filename)
      write('build/block/' + filename, code, BOM=False)
    else:
      block_name = block['name']
      if isinstance(block_name, list):
        block_name = '[' + block_name[0] + ', ...]'
      print "  Warning!: Duplicated java function '%s' in block '%s'" % (filename, block_name)
    


##################################################
#  Auxiliary functions.
##################################################

def write(filename, data, BOM=False):
  """Write data to disk"""
  with codecs.open(filename, 'w', encoding='utf-8') as fo:
    print '  Writing: ', filename
    if BOM:
       fo.write(u'\uFEFF')
    if isinstance(data, list):
       fo.write('\n'.join(data))
    if isinstance(data, basestring):
       fo.write(data)


def read(filename):
  """Read data from disk"""   
  with codecs.open(filename, 'r', encoding='utf-8') as fi:
     return fi.read()
    
  
def read_yaml(filename):
  """Read YAML data from disk"""
  stream = read(filename)
  return yaml.load(stream)


main()
