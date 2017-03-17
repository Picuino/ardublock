import jinja2

write_disk = True    

ircontrols = [
   ['a', 69, 'IR_A'],
   ['b', 70, 'IR_B'],
   ['c', 71, 'IR_C'],
   ['d', 68, 'IR_D'],
   ['e', 67, 'IR_E'],
   ['f', 13, 'IR_F'],
   ['0', 22, 'IR_0'],
   ['1', 12, 'IR_1'],
   ['2', 24, 'IR_2'],
   ['3', 94, 'IR_3'],
   ['4',  8, 'IR_4'],
   ['5', 28, 'IR_5'],
   ['6', 90, 'IR_6'],
   ['7', 66, 'IR_7'],
   ['8', 82, 'IR_8'],
   ['9', 74, 'IR_9'],
   ['up', 64, 'IR_Up'],
   ['down', 25, 'IR_Down'],
   ['left', 7, 'IR_Left'],
   ['right', 9, 'IR_Right'],
   ['ctrl', 21, 'IR_Ctrl'],
]

templates = {
   'blocks_xml': {
      'filename': 'ardublock_blocks.xml',
      'comment': "",
      'elements': ircontrols,
      'template_txt': """{%- for value in data %}\n
		<BlockGenus name="me_ircontrol_{{value[0]}}" kind="data" color="10 134 152" initlabel="bg.me_ircontrol_{{value[0]}}" editable-label="no">
			<BlockConnectors>
				<BlockConnector connector-type="number" connector-kind="plug" position-type="mirror" />
			</BlockConnectors>
		</BlockGenus>
                {%- endfor %}"""
   },

   'family_xml': {
      'filename': 'ardublock_family.xml',
      'comment': "",
      'elements':  ircontrols,
      'template_txt': """\t\t<BlockFamily>
{%- for value in data %}
\t\t\t<FamilyMember>me_ircontrol_{{value[0]}}</FamilyMember>
{%- endfor %}
\t\t</BlockFamily>""",
   },

   'properties': {
      'filename': 'ardublock_properties.xml',
      'comment': "",
      'elements': ircontrols,
      'template_txt': """{%- for value in data %}bg.me_ircontrol_{{value[0]}}={{value[2]}}\n{% endfor %}""",
   },

   'block-mapping': {
      'filename': 'ardublock_block-mapping.xml',
      'comment': "",
      'elements': ircontrols,
      'template_txt': """{% for value in data %}me_ircontrol_{{value[0]}}=com.ardublock.translator.block.makeblock.MeIRControls\n{% endfor %}""",
   },

   'block-mapping': {
      'filename': 'MeIRControls.java',
      'comment': "",
      'elements': ircontrols,
      'template_txt': """{% for value in data %}		if (ir_code == "{{value[0]}}") return "{{value[1]}}";\n{% endfor %}""",
   },
}


def main():
   for name, template in templates.iteritems():
      tmpl = jinja2.Template(template['template_txt'])
      code = []
      code.append(tmpl.render(data=template['elements']))
      code = template['comment'] + '\n'.join(code)
      print code
      if write_disk:
         open(template['filename'], 'wt').write(code)
      raw_input('Press Enter')


main()
