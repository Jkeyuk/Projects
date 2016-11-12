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
Design a function that consumes element and produces a list of the names of all
the elements in the tree.
'''

## Element -> ListOfStrings
## ListOfElements -> ListOfStrings
## given a element produces a list of strings of all element names in the tree.

##stubs
##def names_ele(e):
##    return []
##
##def names_loe(loe):
##    return []

def names_ele(e):
    return [e.name] + names_loe(e.subs)

def names_loe(loe):
    if not loe:
        return []
    else:
        return names_ele(loe[0]) + names_loe(loe[1:])
        

def name_tests():
    f1 = Element('file1', 1, [])
    f2 = Element('file2', 2, [])
    f3 = Element('file3', 3, [])
    d1 = Element('directory1', 0, [f1, f2])
    d2 = Element('directory2', 0, [f3])
    d3 = Element('directory3', 0, [d1, d2])

    assert names_loe([]) == []
    assert names_ele(f1) == ['file1']
    assert names_loe([f1]) == ['file1']
    assert names_ele(d1) == ['directory1', 'file1', 'file2']
    assert names_loe([d1]) == ['directory1', 'file1', 'file2']
    assert names_ele(d3) == ['directory3', 'directory1', 'file1', 'file2',
                             'directory2', 'file3']

    print('name tests passed')
    
name_tests()

