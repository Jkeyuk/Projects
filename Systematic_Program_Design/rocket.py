''' Data Design

You are designing a program to track a rockets journey as it descends kilometers
to Earth You are only interested in the descent from kilometers to touchdown
Once the rocket has landed it is done. Design a data definition to represent
the rockets remaining descent Call it Rocket Descent.
'''
## Rocket_Descent is Integer [0, 100]
## iterp. number of kilometers until rocket lands
RD1 = 100 #start of rocket descent
RD2 = 50  #middle of rocket descent
RD3 = 0   #Rocket has Landed

def fn_for_Rocket_Decent(rd):
    pass

## Template rulse used:
##     - atomic non-distinct: Integer[0,100]

''' Function Design
Design a function that will output the rockets remaining descent
distance in a short string that can be broadcast on Twitter When the descent
is over the message should be The rocket has landed Call your function
rocket_descent_to_msg.
'''

## Rocket_Descent -> String
## interp. produces a string to describe remaining descent distance

'''Stub
def rocket_descent_to_msg(rd):
    return 'a'
'''

def rocket_descent_to_msg(rd):
    if rd == 0:
        return 'The rocket has landed'
    else:
        return str(rd) + ' km until touchdown'

def tests():
    assert rocket_descent_to_msg(100) == '100 km until touchdown'
    assert rocket_descent_to_msg(50) == '50 km until touchdown'
    assert rocket_descent_to_msg(0) == 'The rocket has landed'
    print('test passed')

tests()
