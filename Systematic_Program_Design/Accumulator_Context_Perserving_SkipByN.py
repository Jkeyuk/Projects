'''
Design a function that consumes a list and a natural n, and produces the list
formed by including the first element of the list, then skipping the given n
number of next elements, including the next element, skipping the next n and
so on.
'''

## [list of x], Natural -> [list of x]
## takes a given list and reproduces that list by including the first element
## then skipping the next n number of elements, and including the next, and
## so on.

def skip(loxx,n):
    #acc is natural representing how many elements to skip
    def skipp(lox, n, acc):
        if not lox:
            return []
        else:
            if acc == 0:
                return [lox[0]] + skipp(lox[1:], n, acc + n)
            else:
                return skipp(lox[1:], n, acc - 1)
            
    return skipp(loxx, n, 0)
    
assert skip([], 2) == []
assert skip([1,2,3,4,5], 1) == [1,3,5]
assert skip([1,2,3,4,5,6,7,8,9], 2) == [1,4,7]
assert skip([1,2,3,4,5,6,7,8,9], 3) == [1,5,9]
print('all tests passed')
