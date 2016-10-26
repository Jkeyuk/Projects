'''
Design a function that consumes a list and produces a list where every second
element in the given list is skipped. So only keep 1st, 3rd, 5th etc of the
list.
'''

## list of x -> list of x
## takes a given list and reproduces that list with every other element removed.
## only keeps 1st, 3rd, 5th, etc elements of the given list.

def skip1(loxx):
    #acc: Natural
    def skipp1(lox, acc):
        if not lox:
            return []
        else:
            if acc % 2 == 0:
                return [lox[0]] + skipp1(lox[1:], acc + 1)
            else:
                return skipp1(lox[1:], acc + 1)
    return skipp1(loxx, 0)

    
assert skip1([1,2,3,4,5]) == [1,3,5]
assert skip1([1,2,3,4,5,6,7,8,9]) == [1,3,5,7,9]
print('all tests passed')
