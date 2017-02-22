import jinja2

write_disk = True    

elements_digital = [
   'Arduino_D2',   'Arduino_D3',
   'Arduino_D4',   'Arduino_D5',
   'Arduino_D6',   'Arduino_D7',
   'Arduino_D8',   'Arduino_D9',
   'Arduino_D10',  'Arduino_D11',
   'Arduino_D12',  'Arduino_D13',
]
elements_analog = [
   'Arduino_A0',   'Arduino_A1',
   'Arduino_A2',   'Arduino_A3',
   'Arduino_A4',   'Arduino_A5',
]

templates = {
   'blocks_xml': {
      'filename': 'ardublock_blocks.xml',
      'comment': "\n\t\t<!-- Arduino Definitions -->",
      'elements': [elements_digital + elements_analog],
      'template_txt': """{%- for value in data %}
\t\t<BlockGenus name="{{value}}" kind="data" color="238 125 22" initlabel="bg.{{value}}" editable-label="no">
\t\t\t<BlockConnectors>
\t\t\t\t<BlockConnector connector-type="number" connector-kind="plug" position-type="mirror" />
\t\t\t</BlockConnectors>
\t\t</BlockGenus>
{% endfor %}""",
   },

   'family_xml': {
      'filename': 'ardublock_family.xml',
      'comment': "\n\t\t<!-- Arduino Definitions -->\n",
      'elements': [elements_digital + elements_analog, elements_analog],
      'template_txt': """\t\t<BlockFamily>
{%- for value in data %}
\t\t\t<FamilyMember>{{value}}</FamilyMember>
{%- endfor %}
\t\t</BlockFamily>""",
   },

   'properties': {
      'filename': 'ardublock_properties.xml',
      'comment': "\n# Arduino Definitions\n",
      'elements': [elements_digital + elements_analog],
      'template_txt': """{%- for value in data %}bg.{{value}}={{value[8:]}}\n{% endfor %}""",
   },

   'block-mapping': {
      'filename': 'ardublock_block-mapping.xml',
      'comment': "\n# Arduino Definitions\n",
      'elements': [elements_digital + elements_analog],
      'template_txt': """{% for value in data %}
                         {%- if '_D' in value -%}
                         {{value}}=com.ardublock.translator.block.LabelLeftBlock\n{% else -%}
                         {{value}}=com.ardublock.translator.block.LabelBlock\n{% endif -%}
                         {% endfor %}""",
   },
}


def main():
   for name, template in templates.iteritems():
      tmpl = jinja2.Template(template['template_txt'])
      code = []
      for element in template['elements']:
        code.append(tmpl.render(data=element))
      code = template['comment'] + '\n'.join(code)
      print code
      if write_disk:
         open(template['filename'], 'wt').write(code)
      raw_input('Press Enter')


def generate_elements():
   for i in range(2,14):  print "   'Arduino_D" + str(i) + "',"
   for i in range(7):  print "   'Arduino_A" + str(i) + "',"

main()
