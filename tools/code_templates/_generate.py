import yaml
import jinja2

def main():
  tree = []
  for source in ['picuino']:
      tree.append(read(source + '.yaml'))
  build(tree)

def read(filename):
  print 'Process ' + filename
  stream = file(filename, 'rt')
  docs = yaml.load(stream)
  return docs


def test():
  print yaml.dump({'uno':1, 'dos':2, 'blocks':[[1, 2], [1, 2]]})
  return


main()
