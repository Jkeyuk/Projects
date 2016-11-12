'''
HtDF P2 - Less Than 5

Design a problem to check if the length of a string is less than 5.
'''

# String -> Boolean
# return True if the length of a given string is less then 5

##    stub
##    def Less_Then_5(s):
##        return 'a'

##    template
##    def Less_Then_5(s):
##        ....s

def Less_Then_5(s):
    return len(s) < 5

def tests():
    assert Less_Then_5('cat') == True
    assert Less_Then_5('12345') == False
    assert Less_Then_5('moreThenFive') == False
    print('test passed')

tests()
