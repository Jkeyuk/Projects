'''Data definition
Data definition to represent list of numbers
'''

## ListOfNumbers is one of:
##     - []
##     - [number] + ListOfNumbers
## interp. a list of integers.

list1 = []
list2 = [1,2,3,4,5]

def fn_for_ListOfNumbers(lon):
    if not lon:
        pass
    else:
        #lon[0].... fn_for_ListOfNumbers(lon[1:])
        pass


'''
Function Design
design a function to order a list from largest to smallest
'''

## ListOfNumbers -> ListOfNumbers
## sort a list of numbers from highest to lowest

##stub
##def sort_nums(lon):
##    return []

def sort_nums(lon):
    if not lon:
        return []
    else:
        return insert(lon[0], sort_nums(lon[1:]))
        

def sort_tests():
    assert sort_nums([]) == []
    assert sort_nums([2]) == [2]
    assert sort_nums([1,2,3]) == [3,2,1]
    assert sort_nums([1,3,2,4,5]) == [5,4,3,2,1]
    assert sort_nums([5,4,3,2,1]) == [5,4,3,2,1]
    print('test passed for sort_nums')

## Number ListOfNumbers -> ListOfNumbers
## inserts a given number into a sorted list in the appropriate position
## (highest to lowest).

##stub
##def insert(n, lon):
##    return []

def insert(n, lon):
    if not lon:
        return [n]
    else:
        if n > lon[0]:
            return [n] + lon
        else:
            return [lon[0]] + insert(n, lon[1:])
        

def insert_tests():
    assert insert(2, []) == [2]
    assert insert(2, [1]) == [2,1]
    assert insert(2, [3]) == [3,2]
    assert insert(2, [5,4,3,1]) == [5,4,3,2,1]
    print('test passed for insert_tests')

insert_tests()
sort_tests()
