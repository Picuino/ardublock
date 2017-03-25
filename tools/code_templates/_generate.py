"""
Render code for Ardublock configuration files and java files
from yaml database
"""
import os
import yaml
import codecs
import copy
from jinja2 import Template

def main():
  """Generate Ardublock sourcecode from *.yaml databases"""
  sources = [
    'picuino.yaml',
    'makeblock.yaml',
  ]
  source_tree = []
  for f in sources:
    print 'Read ' + f
    source_tree.append(read_yaml(f))
  for f in os.listdir('.'):
    if not f in sources and '.yaml' in f[-5:].lower():
      print 'Read ' + f
      source_tree.append(read_yaml(f))
  print
  render_code(source_tree)


def render_code(source_tree):
  # Render code
  blocks, families, menus, properties, mapping = [], [], [], [], []

  for source in source_tree:
    print 'Process ' + source['Library']['name']
    source['all_properties'] = extract_properties(source)
    source['all_blocks'] = expand_blocks(source)
    render_java(source)

  for filename in ['ardublock.xml',
                   'ardublock.properties',
                   'block-mapping.properties' ]:
     print 'Process ' + filename
     template_string = read('templates/' + filename)
     tmpl = Template(template_string, trim_blocks=True, lstrip_blocks=True)
     code = tmpl.render(source=source_tree )
     write('build/' + filename, code)


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
  java_template = read('templates/' + 'template.java')
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
