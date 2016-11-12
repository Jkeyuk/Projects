'''
Design a function called average that consumes listof Number and
produces the average of the numbers in the list. Make sure it is tail
recursive.
'''

## [ListOf Numbers] - > Number
## consumes listof Number and produces the average of the numbers in the list.

def averageOfList(lonn):
    #ssf is sum so far as a number showing current some of elements in list
    #num is a number showing number of elements looked at
    def averageOfList(lon,ssf,num):
        if not lon:
            return ssf/num
        else:
            return averageOfList(lon[1:], lon[0] + ssf, num + 1)
    return averageOfList(lonn,0,0)


assert averageOfList([0]) == 0
assert averageOfList([1]) == 1
assert averageOfList([2,2]) == 2
assert averageOfList([1,2,3]) == 2
assert averageOfList([1,2,3,4]) == 2.5
print('average test passed')

