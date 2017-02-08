import os
import re

input_path = 'classes/com/ardublock/block'
input_file = 'ardublock.properties'

output_path = 'new_properties'
output_names = 'xml_names.xml'

name_pattern = 'b[cdg]\.[0-9a-zA-Z_-]*'

def main():
  names = open(os.path.join(output_path, output_names), 'rt').read()
  names = names.split('\n')
  
  files = [f for f in os.listdir(input_path) if re.match(input_file, f)]
  for filename in files:
      process(input_path, filename, names)

def process(input_path, filename, xml_names):
    print filename
    all_filename = os.path.join(input_path, filename)
 
    fi = open(all_filename, 'rt')
    code = []
    for li in fi:
      name = re.findall(name_pattern, li)
      if name and not (name[0] in xml_names):
        continue
      code.append(li)

    fi.close()
    fo = open(os.path.join(output_path, filename), 'wt')
    fo.write(''.join(code))
    fo.close()

main()
