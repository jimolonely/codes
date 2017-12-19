import argparse
import sys

parser = argparse.ArgumentParser(description='sum of integers at the command line')
parser.add_argument('integers',metavar='int',nargs='+',type=int,help='an integer to be summed')
parser.add_argument('--log',default=sys.stdout,type=argparse.FileType('w'),help='the file where the sum should be writter')

args = parser.parse_args()
print(args)
args.log.write('%s'%sum(args.integers))
args.log.close()