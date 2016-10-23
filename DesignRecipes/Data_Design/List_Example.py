'''Data definition
Design a data definition to represent the wieghts of all owls.
'''

## ListOfNumbers is one of:
##     - []
##     - [number] + ListOfNumbers
## interp. a list of integers representing the weight of owls in pounds.

list1 = []
list2 = [1,2,3,4,5]

def fn_for_ListOfNumbers(lon):
    if not lon:
        pass
    else:
        #lon[0].... fn_for_ListOfNumbers(lon)
        pass

'''Function design
A) Design a function that consumes the weights of owls and produces the total
weight of all the owls.

B) Design a function that consumes the wights of owls and produces the total
number of owls.
'''

## ListOfNumbers -> Integer
## produces total sum of numbers in a given list

'''stub
def total_sum(lon):
    return 0
'''

def total_sum(lon):
    if not lon:
        return 0
    else:
        return lon[0] + total_sum(lon[1:])

assert total_sum([]) == 0
assert total_sum([3]) == 3
assert total_sum([1,2,3,4,5]) == 15

## ListOfNumbers -> Integer
## produces the total number of elements in a given list

'''Stub
def total_elements(lon):
    return 0
'''

def total_elements(lon):
    if not lon:
        return 0
    else:
        return 1 + total_elements(lon[1:])

assert total_elements([]) == 0
assert total_elements([1]) == 1
assert total_elements([1,2,3,4,5]) == 5
