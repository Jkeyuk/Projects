'''Data design
Design a data definition to represent a list of sports teams
'''

## ListOfString is one of:
##     - []
##     - [String]+ ListOfString
## interp. a list of strings

list1 = []
list2 = ['maple leafs', 'red skins']

def fn_for_Los(l):
    if not l:
        #...
        pass
    else:
        #...l[0] ... fn_for_Los(l) 
        pass

'''Function design
design a function that consumes ListOfString and produces true if the list
includes "maple leafs"
'''

## ListOfString -> boolean
## returns true if 'maple leafs' is in the ListOfString

def hasMapleLeafs(l):
    if not l:
        return False
    else:
        if l[0] == 'maple leafs':
            return True
        else:
            return hasMapleLeafs(l[1:])

def tests():
    L1 = []
    L2 = ['maple leafs', 'red skins', 'bruins', 'something']
    L3 = ['ardvarks', 'red skins', 'bruins', 'something']
    L4 = ['ardvarks', 'red skins', 'bruins', 'something', 'maple leafs']
    assert hasMapleLeafs(L1) == False
    assert hasMapleLeafs(L2) == True
    assert hasMapleLeafs(L3) == False
    assert hasMapleLeafs(L4) == True
    print('test passed')
tests()

