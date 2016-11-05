'''
This program accepts a range of numbers to scan from the user, then prints
all the Kaprekar numbers in that range.

Usage Example:
c:\python Kaprekar_numbers.py 0 100

Output:
1
9
45
55
99
'''
import sys

def main(argv):
    if len(argv) is not 3 or not argv[1].isnumeric() or not argv[2].isnumeric():
        print('input range of numbers to scan')
        print('example: 1 100')
    else:
        [print(x) for x in range(int(argv[1]), int(argv[2])) if isKaprekar(x)]

## Number -> Boolean
## Returns true if the number given is a Kaprekar number. Otherwise False

def isKaprekar(n):
    return n == 1 or n in list(map(sum, kapParts(n)))

## Number -> [ListOf [ListOf Numbers]]
## Given a number, return the seperate parts of the square of the number.

## Will not return parts that equal 0 or parts of sqaured numbers that are one
## digit long.

## example given 45 will square to the number to 2025 and return parts [2, 025],
## [20,25], [202, 5].

def kapParts(n):
    num = str(n**2)
    return [[int(num[:i]), int(num[i:])] for i in range(1, len(num))
            if int(num[i:]) > 0]

## Unit Tests ==============================================================
assert kapParts(2) == []
assert kapParts(100) == []
assert kapParts(45) == [[2, 25], [20, 25], [202, 5]]
assert kapParts(99) == [[9, 801], [98, 1], [980, 1]]
assert kapParts(297) == [[8, 8209], [88, 209], [882, 9], [8820, 9]]

assert isKaprekar(2) == False
assert isKaprekar(3) == False
assert isKaprekar(100) == False
assert isKaprekar(1) == True
assert isKaprekar(9) == True
assert isKaprekar(45) == True
assert isKaprekar(99) == True
##===========================================================================
main(sys.argv)
