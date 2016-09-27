''' Data Design

Develop a system that will classify buildings in downtown vancouver. Three
different classifications: New, Old, Heritage.
'''

## Building_Class is one of:
##     - 'New'
##     - 'Old'
##     - 'Heritage'
## interp. the classification of a building

## example redundant for enumeration

def fn_for_Building_Class(bc):
    if bc == 'New':
        pass
    elif bc == 'Old':
        pass
    elif bc == 'Heritage':
        pass

## template rules used:
##     - one of: 3 cases
##     - atomic distinct : 'New'
##     - atomic distinct : 'Old'
##     - atomic distinct : 'Heritage'

''' Function Design

The city wants to demolish all buildings classified as old, design a function
called demolish that determines whether a building should be torn down or not.
'''
## Building_Class -> Boolean
## return true if Building_Class is 'Old'

''' Stub
def demolish(bc):
    return False
'''
# used template for Building_Class
def demolish(bc):
    if bc == 'New':
        return False
    elif bc == 'Old':
        return True
    elif bc == 'Heritage':
        return False

def tests():
    assert demolish('New') == False
    assert demolish('Old') == True
    assert demolish('Heritage') == False 
    print('test passed')

tests()
