'''
Design a function to double every number in a list.
'''

## ListOfNumbers is one of:
##     - []
##     - [number] + ListOfNumbers
## inter. a list of integers

list1 = []
list2 = [1,2,3,4,5]

#Template
##def fn_for_lon(lon):
##    if not lon:
##        pass
##    else:
##        #....lon[0]....fn_for_lon(lon)
##        pass
    
## ListOfNumbers -> ListOfNumbers
## doubles every number in a given list of numbers

##Stub
##def double(lon):
##    return []

def double(lon):
    if not lon:
        return []
    else:
        return [2* lon[0]] + double(lon[1:])
        
    
assert double([]) == []
assert double([1]) == [2]
assert double([1,2,3]) == [2,4,6]
