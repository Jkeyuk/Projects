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

## Wizard is Wizard(String, String, ListOf Strings)
## interp. a Wizard with a name as string, a house as a string, and children as
## a list of Strings.

HarryPotter = Wizard('Harry Potter', 'Gryffindor', ['James Potter',
                                                    'Albus Potter',
                                                    'Lily Potter'])

Dumbledore = Wizard('Albus Percival Wulfric Brian Dumbledore',
                    'Gryffindor', [])
