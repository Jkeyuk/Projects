''' Data Design

Design a data definition to represent the name of a city
'''

# City_Name is String
# interp. the name of a city represented by a string

city_name_1 = 'vancouver'
city_name_2 = 'boston'

def fn_for_CityName(CN):
    #...CN
    pass

## Template rules Used:
##     atomic non-distinct: string
##

''' Function Design

So the problem says use the CityName data definition above
to design a function that produces true if the given
city is the best in the world.
'''

# CityName -> Boolean
# purpose: returns True if given city is the best city in the world (ottawa)

''' STUB 
def best_city(CN):
    return False
'''

# Took template from City_Name
def best_city(CN):
    return CN == 'ottawa'

def tests():
    assert best_city('ottawa') == True
    assert best_city('vancouver') == False
    assert best_city('detroit') == False
    print('test passed')

tests()
