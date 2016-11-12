## ListOfString is one of:
##  - []
##  - [String] + ListOfString
## interp. a list of strings

'''
Design a function that consumes two lists of strings and produces true if the
first list is a prefix of the second.
'''

## ListOfString ListOfString -> Boolean
## produces true if the first list is a prefix of the second list.
## otherwise False

##stub
##def prefix(lstA, lstB):
##    return False

def prefix(lstA, lstB):
    if not lstA:
        return True
    elif not lstB:
        return False
    else:
        return lstA[0] == lstB[0] and prefix(lstA[1:], lstB[1:])

def prefix_tests():
    l1 = []
    l2 = ['a']
    l3 = ['a', 'b']
    l4 = ['a', 'b', 'c']

    assert prefix(l1,l1) == True   #both lists empty
    assert prefix(l2,l1) == False  #list b is empty, list a is not
    assert prefix(l1,l3) == True   #list a is empty, list b is not
    assert prefix(l2,l2) == True   #both lists not empty
    assert prefix(l2,l3) == True   #both lists not empty
    assert prefix(l4,l2) == False  #both lists not empty
    
    print('prefix tests passed')

prefix_tests()
