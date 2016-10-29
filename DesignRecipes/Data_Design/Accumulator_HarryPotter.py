'''
Design a representation of a harry potter wizard family tree that
includes, for each wizard, their name, the house they were placed in at
hogwarts, and their children.
'''

class Wizard:
    def __init__(self, name, house, children):
        self.name = name
        self.house = house
        self.children = children

## Wizard is Wizard(String, String, ListOf Wizards)
## interp. a Wizard with a name as string, a house as a string, and children as
## a list of Wizards.

JamesPotterII = Wizard('James Potter II', 'Gryffindor', [])

AlbusPotter = Wizard('Albus Potter', 'Slytherin', [])

LilyPotterII = Wizard('Lily Potter II', 'Gryffindor', [])

HarryPotter = Wizard('Harry Potter', 'Gryffindor', [JamesPotterII,
                                                    AlbusPotter,
                                                    LilyPotterII])

LilyPotter = Wizard('Lily Potter', 'Gryffindor', [HarryPotter])

JamesPotter = Wizard('James Potter', 'Gryffindor', [HarryPotter])

def fn_for_wizard(wiz):
    def fn_for_wizardd(w):
        #...w.name
        #...w.house
        #...fn_for_low(w.children)
        pass
    def fn_for_low(low):
        if not low:
            pass
        else:
            #...fn_for_wizardd(low[0])
            #...fn_for_low(low[1:])
            pass

    return fn_for_wizardd(wiz)

##===========================================
## Functions

'''
Design a function that consumes a wizard and produces the names of every
wizard in the tree that was placed in the same house as their immediate
parent.
'''

## Wizard -> list of strings
## consumes a wizard and produces the names of every wizard in the tree that
## was placed in the same house as their immediate parent.

def SameHouseAsParent(wiz):
    #parent_house is house of parent as string
    def fn_for_wizardd(w, parent_house):
        if parent_house == w.house:
            return [w.name] + fn_for_low(w.children, w.house)
        else:
            return fn_for_low(w.children, w.house)
        
    def fn_for_low(low, parent_house):
        if not low:
            return []
        else:
            return fn_for_wizardd(low[0], parent_house) + \
                   fn_for_low(low[1:], parent_house)
            
    return fn_for_wizardd(wiz, '')

assert SameHouseAsParent(AlbusPotter) == []
assert SameHouseAsParent(JamesPotter) == ['Harry Potter','James Potter II',
                                          'Lily Potter II']
assert SameHouseAsParent(HarryPotter) == ['James Potter II','Lily Potter II']
print('same house tests passed')

'''
Design a function that consumes a wizard and produces the number of
wizards in that tree. including the root. your function should be tail
recursive.
'''

## Wizard -> Number
## consumes a wizard and produces the number of wizards in that tree.
## including the root.

def NumOfWiz(wiz):
    def fn_for_wizardd(w, num):
        return num + fn_for_low(w.children, num)
        
    def fn_for_low(low, num):
        if not low:
            return 0
        else:
            return fn_for_wizardd(low[0], num + fn_for_low(low[1:], num))
        
    return fn_for_wizardd(wiz, 1)

assert NumOfWiz(AlbusPotter) == 1
assert NumOfWiz(HarryPotter) == 4
assert NumOfWiz(JamesPotter) == 5
print('number of wizards test passed')
