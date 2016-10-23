'''
Design a data definition to represent a list of booleans, and a function to
determine if all values in a given list are true.
'''

## ListOfBooleans is one of:
##     - []
##     - [Boolean] + ListOfBooleans
## interp a list of booleans

lob1 = []
lob2 = [True,False]

def fn_for_lob(lob):
    if not lob:
        pass
    else:
        #...lob[0]...fn_for_lob(lob)
        pass
    
## ListOfBooleans -> boolean
## returns true if a given ListOfBooleans has no false values.

##stub
##def all_true(lob):
##    return False

def all_true(lob):
    if not lob:
        return True
    else:
       return lob[0] and all_true(lob[1:])
        

assert all_true([]) == True
assert all_true([True,False]) == False
assert all_true([False,False,False]) == False
assert all_true([True]) == True
assert all_true([True,True]) == True
