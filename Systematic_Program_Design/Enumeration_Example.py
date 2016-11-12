''' Data Design

Design a data definition to represent the letter grade in a course, which
is one of A,B, or C.
'''
## Grade is one of:
## - 'A'
## - 'B'
## - 'C'
## interp. the grade of a student in a course
## examples redundant for enumerations

# Template
def fn_for_grade(g):
    if g is 'A':
        pass
    elif g is 'B':
        pass
    elif g is 'C':
        pass

## Template Rule:
##    enumeration one of: 3 cases
##    atomic distinct: 'A'
##    atomic distinct: 'B'
##    atomic distinct: 'C'

''' Function Design

Design a function that consumes a letter grade and produces the next highest
letter grade. no change for 'A'
'''

## Grade -> Grade
## Given a grade, produces the next highest grade

'''Stub
def bump_up(g):
    return 'z'
'''

# used template for grade
def bump_up(g):
    if g is 'A':
        return 'A'
    elif g is 'B':
        return 'A'
    elif g is 'C':
        return 'B'
    
def tests():
    assert bump_up('A') == 'A'
    assert bump_up('B') == 'A'
    assert bump_up('C') == 'B'
    print('test passed')

tests()
