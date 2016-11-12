'''
HtDF P1 - Double

Write a function to double a given number
'''

# Number -> Number                   <- signature
# produce 2 times the given number   <- purpose

'''
def double(n):                      # <- stub
    return 0
'''

'''
def double(n):                      # <- template
    ...n
'''

def double(n):
    return 2 * n

def tests():
    assert double(2) == 4               # <- examples/unit tests
    assert double(4) == 8
    assert double(0) == 0
    assert double(4.2) == 8.4
    print('Test Passed')

tests()


