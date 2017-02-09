import os
import re

input_path = '../target/classes/com/ardublock/block'
input_file = 'ardublock.properties'

output_path = 'new_properties'
output_file = 'properties_names.xml'

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
  write_list(names, output_path, output_file)


def write_list(lines, output_path, output_file):
  if not os.path.exists(output_path):
    os.mkdir(output_path)
  fo = open(os.path.join(output_path, output_file), 'wt')
  fo.write('\n'.join(lines))
  fo.close()

	
main()
