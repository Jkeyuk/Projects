''' Data Design

Design a data definition to represent a seat number in a row, where each row
has 32 seats (just the seat number, not the row number).
'''

# Seat_Number is Integer [1, 32]
# interp. the seat number in a row, 1 and 32 are isle seats

Seat_1 = 1 # aisle seat
Seat_2 = 12 # near middle seat

# Template
def fn_for_Seat_number(sn):
    #... SN
    pass

# template Rule -> atomic non distinct: integer[1, 32]

''' Function Design

design a function that produces true if the given seat number is on the aisle
'''

## Seat_Number -> Boolean
## returns true if given seat number is 1 or 32

'''Stub
def on_aisle(sn):
    return False
'''

# used template for Seat_Number
def on_aisle(sn):
    return sn == 1 or sn == 32


def tests():
    assert on_aisle(1) == True
    assert on_aisle(16) == False
    assert on_aisle(32) == True
    print('test passed')

tests()
