''' Data design

Design a data definition to represent the current state of a New Year's Eve
countdown. Which falls into three categories:
- not yet started
- from 10 to 1 seconds before midnight
- Complete (happy new year!)
'''

## Count_Down_State is one of:
##     - 'not started'
##     - Integer [10, 1]
##     - 'complete'
## interp. State of the countdown:
##     - 'not started' means countdown not started
##     - integer [10, 1] is x position in countdown
##     - 'complete' means countdown finished.

CDS = 'not started' # Count_Down_State has not started
CDS = 9     # Countdown has started and is on 9 position
CDS = 'complete'  # Count_Down_State is complete

def fn_for_CountDownState(cds):
    if cds == 'not started':
        pass
    elif cds == 'complete':
        pass
    else:
        pass
    
## Template rules used:
##     one of: 3 cases
##         atomic distinct: 'not started'
##         atomic non-distinct: Integer [10, 1]
##         atomic distinct: 'complete'

''' Function Design

Design a function, when given a count down state, will return what the count
down state is as a string. Integer between 1-10 displays the integer as string
(eg. '3').
'''

## Count_Down_State -> String
## given a Count_Down_State, return a string which explains the Count_Down_State

'''Stub
def Count_Down_Display(cds):
    return 'a'
'''
# used template from Count_Down_State
def Count_Down_Display(cds):
    if cds == 'not started':
        return 'countdown not started'
    elif cds == 'complete':
        return 'happy new year'
    else:
        return str(cds)

def tests():
    assert Count_Down_Display('not started') == 'countdown not started'
    assert Count_Down_Display(1) == '1'
    assert Count_Down_Display(5) == '5'
    assert Count_Down_Display(10) == '10'
    assert Count_Down_Display('complete') == 'happy new year'
    print('test passed')

tests()

