'''
Design a function to find the largest number in a list of numbers.
'''
#-------------------Data design-----------------------------------

## ListOfNumber is one of:
##     - []
##     - [number] + ListOfNumber
## interp, list of numbers

lon1 = []
lon2 = [1]
lon3 = [1,2,3,4,5]

def fn_for_lon(lon):
    if not lon:
        pass
    else:
        #...lon[0]...fn_for_lon(lon)
        pass


#-------------------function design-----------------------------------

## ListOfNumber -> integer
## produces the largest number in a list of numbers

##stub
##def largest(lon):
##    return 0

def largest(lon):
    if not lon:
        return 0
    else:
        if lon[0] > largest(lon[1:]):
            return lon[0]
        else:
            return largest(lon[1:])

assert largest([]) == 0
assert largest([1]) == 1
assert largest([1,2,3,4,5]) == 5
assert largest([3,66,54,32,11]) == 66
