'''
Data definition to represent a file system
'''

class Element:
    def __init__(self, name, data, subs):
        self.name = name
        self.data = data
        self.subs = subs

## Element is Element(string, integer, ListOfElements)
## INTERP. an element in the file system with:
##     - name as a string.
##     - either data or subs.
##     - if data is 0, then subs is a list of sub elements.
##     - if data is not 0, then subs is ignored.

## ListOfElements is one of:
##     - []
##     - [element] + ListOfElements
## INTERP. a list of file system elements

f1 = Element('file1', 1, [])
f2 = Element('file2', 2, [])
f3 = Element('file3', 3, [])
d1 = Element('directory1', 0, [f1, f2])
d2 = Element('directory2', 0, [f3])
d3 = Element('directory3', 0, [d1, d2])

def fn_for_element(e):
    #...e.name
    #...e.data
    #...fn_for_loe(e.subs)
    pass

def fn_for_loe(loe):
    if not loe:
        pass
    else:
        #...fn_for_element(loe[0])
        #...fn_for_loe(loe[1:])
        pass

'''
Design a function to consume element and produce the sum of all the file data
'''

## Element -> Integer
## ListOfElements -> Integer
## produce the sum of all data in element and its subs.

##stubs
##def sum_data_element(e):
##    return 0
##
##def sum_data_loe(loe):
##    return 0

def sum_data_element(e):
    if e.data == 0:
        return sum_data_loe(e.subs)
    else:
        return e.data

def sum_data_loe(loe):
    if not loe:
        return 0
    else:
        return sum_data_element(loe[0]) + sum_data_loe(loe[1:])
        

def sum_tests():
    f1 = Element('file1', 1, [])
    f2 = Element('file2', 2, [])
    f3 = Element('file3', 3, [])
    d1 = Element('directory1', 0, [f1, f2])
    d2 = Element('directory2', 0, [f3])
    d3 = Element('directory3', 0, [d1, d2])
    
    assert sum_data_element(f1) == 1
    assert sum_data_element(f2) == 2
    assert sum_data_element(f3) == 3
    assert sum_data_element(d1) == 1 + 2
    assert sum_data_element(d2) == 3
    assert sum_data_element(d3) == 3 + 3

    assert sum_data_loe([]) == 0
    
    print('passed sum tests')
sum_tests()
