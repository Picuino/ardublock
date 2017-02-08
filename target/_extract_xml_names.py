import os
import re

input_path = 'classes/com/ardublock/block'
input_file = 'ardublock.xml'

output_path = 'new_properties'
output_file = 'xml_names.xml'

name_pattern = 'b[cdg]\.[0-9a-zA-Z_-]*'

def main():
  # Extract names 
  data = open(os.path.join(input_path, input_file), 'rt').read()
  all_names = re.findall(name_pattern, data)
  names = []
  for name in all_names:
    if not name in names:
      names.append(name)

  # Write sorted list of names to disk
  names.sort()
  for name in names: print name
  fo = open(os.path.join(output_path, output_file), 'wt')
  fo.write('\n'.join(names))
  fo.close()

main()
