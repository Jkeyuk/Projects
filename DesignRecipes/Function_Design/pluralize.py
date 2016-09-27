'''
HtDF P2 - Pluralize

Design a function to pluralize a string byt adding 's' to it
'''

# String  -> String 
# Pluralizes a string by taking a string and adding 's' to it.

''' Stub
def pluralize(word):
    return ''
'''

''' Template
def pluralize(word):
    ...word
'''

def pluralize(word):
    return word + 's'

def tests():
    assert pluralize('jon') == 'jons'
    assert pluralize('duck') == 'ducks'
    assert pluralize('car') == 'cars'
    print('test passed')

tests()
