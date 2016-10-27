'''
A) Consider the following function that consumes a list of numbers and produces
the sum of all the numbers in the list. use the stepper to analyze the behavior
of this function as the list gets larger and larger.

b)use and accumulator to design a tail-recursive version of sum
'''

## list of number -> number
## produces the sum of all elements in a list of numbers.

## NON TAIL RECURSIVE
##def sumL(lon):
##    if not lon:
##        return 0
##    else:
##        return lon[0] + sumL(lon[1:])

## TAIL RECURSIVE VERSION
def sumL(lonn):
    #acc. Number representing the sum of the elements seen so far.
    def sumL(lon, acc):
        if not lon:
            return acc
        else:
            return sumL(lon[1:], acc + lon[0])
            
    return sumL(lonn, 0)

assert sumL([]) == 0
assert sumL([1]) == 1
assert sumL([1, 2]) == 3
assert sumL([2, 3, 4]) == 9
assert sumL([1,2,3,4,5]) == 15
print('tests passed')
