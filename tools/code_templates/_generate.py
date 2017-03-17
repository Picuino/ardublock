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
  {% if block.parameter or block.return %}
\t\t\t<BlockConnectors>
    {% if block.return %}
\t\t\t\t<BlockConnector connector-type="{{block.return.type}}" connector-kind="plug"
      {%- if block.return.position %} position-type="{{block.return.position}}"{% endif %} />
    {% endif %}
    {% if block.parameter %}
      {% for parameter in block.parameter %}
\t\t\t\t<BlockConnector connector-type="{{parameter.type}}" connector-kind="socket" label="{{parameter.label}}">
        {% if parameter.default %}
\t\t\t\t\t<DefaultArg genus-name="{{parameter.default[0]}}" label="{{parameter.default[1]}}" />
        {% endif %}
\t\t\t\t</BlockConnector>
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
      print 'Process ' + f
      source_tree.append(read_yaml(f))
  print
  render_code(source_tree)


def render_code(source_tree):
  # Render code
  blocks, families, menus, properties, mapping = [], [], [], [], []

  for source in source_tree:
    blocks.append(render(templates['blocks'], source))
    families.append(render(templates['families'], source))
    menus.append(render(templates['menus'], source))
    properties.append(render(templates['properties'], source))
    mapping.append(render(templates['mapping'], source))
    render_java(source)

  template_string = read('ardublock.xml.template')
  tmpl = Template(template_string, trim_blocks=True, lstrip_blocks=True)
  xml_code = tmpl.render(blocks=blocks, families=families, menus=menus)
  write('build/ardublock.xml', xml_code)
  write('build/ardublock.properties', properties)
  write('build/block-mapping.properties', mapping)


def render(template_string, source):
  tmpl = Template(template_string, trim_blocks=True, lstrip_blocks=True)
  blocks = expand_blocks(source)
  properties = extract_properties(source)
  return tmpl.render(library=source['Library'],
                     blocks=blocks,
                     families=source['Families'],
                     properties=properties)


def expand_blocks(source):
  blocks = []
  for block in source['Blocks']:
    if not 'kind' in block:
      if block['return']:  block['kind'] = 'data'
      else:  block['kind'] = 'command'
    if type(block['name']) is list:
      for name in block['name']:
        newblock = copy.copy(block)
        newblock['name'] = name
        blocks.append(newblock)
    else:
      blocks.append(block)
  return blocks


def extract_properties(source):
  # Extract common properties
  sort = []
  _properties = {}
  if 'Properties' in source:
    for _property, _description in source['Properties'].iteritems():
      if not _property in _properties:
        _properties[_property] = _description
        sort.append([_property, _description])
      else:
        print 'library: %s, Duplicated property: %s' % (_property, source['Library']['name'])

  # Extract block properties
  for _block in source['Blocks']:
    if  'properties' in _block:
      for _property, _description in _block['properties'].iteritems():
        if not _property in _properties:
          _properties[_property] = _description
          sort.append([_property, _description])
        else:
          print '  Warning. Library: %s, Duplicated property: %s' % (source['Library']['name'], _property)
    if type(_block['name']) is list:
      for name in _block['name']:
        _property = 'bg.' + name
        if not _property in _properties:
          _properties[_property] = name
          sort.append([_property, name])

  return sort


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
		{% if block.translator.setup %}
		  {% for setup in block.translator.setup %}
		translator.addSetupCommand("{{setup}}");
		  {% endfor %}

		{% endif %}
		{% if block.parameter %}
		  {% set count = 0 %}
		TranslatorBlock translatorBlock;
		  {% for input in block.parameter %}
		translatorBlock = this.getRequiredTranslatorBlockAtSocket({{count}});
		  {% set count = count + 1 %}
		String arg{{count}} = translatorBlock.toCode();
		  {% endfor %}

		{% endif %}
		{% for code in block.translator.code %}
		{{code}}
		{% endfor %}
	}

}
"""
  
  tmpl = Template(java_template, trim_blocks=True, lstrip_blocks=True)
  for block in source['Blocks']:
    if not 'translator' in block: continue
    code = tmpl.render(block=block)
    filename = 'build/block/' + block['translator']['name'] + '.java'
    write(filename, code, BOM=False)


def write(filename, data, BOM=False):
  """Write data to disk"""
  with codecs.open(filename, 'w', encoding='utf-8') as fo:
    print 'Writing: ', filename
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
