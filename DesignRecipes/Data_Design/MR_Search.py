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
Design a function that consumes String and Element and looks for the data
element with the given name. if it finds that element it produces data,
else produces false.
'''

## String Element -> Integer or False
## search a given tree for an element with given name, produce data if found
## otherwise false.

##stubs
##def find(n, e):
##    return False


def find(n, e):
    def find_e(n, e):
        if e.name == n:
            return e.data
        else:
            return find_loe(n, e.subs)

    def find_loe(n, loe):
        if not loe:
            return False
        else:
            value = find_e(n, loe[0])
            if value:
                return value
            else:
                return find_loe(n, loe[1:])
    return find_e(n, e)

def find_tests():
    f1 = Element('file1', 1, [])
    f2 = Element('file2', 2, [])
    f3 = Element('file3', 3, [])
    d1 = Element('directory1', 0, [f1, f2])
    d2 = Element('directory2', 0, [f3])
    d3 = Element('directory3', 0, [d1, d2])

    assert find('file1', f1) == 1
    assert find('dsfsdfsf', f1) == False
    assert find('file3', d3) == 3
    assert find('dsfdsfs', d3) == False

    print('find tests passed')

find_tests()
