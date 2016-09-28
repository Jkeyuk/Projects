''' Data Definition

Design a data definition to represent hockey players, including both their first
and last names.
'''

class Hockey_Player:
    def __init__(self, first, last):
        self.first = first
        self.last = last
    
## Hockey_Player is Hockey_Player(string, string)
## interp. a hockey player with
##        - first as first name
##        - last as last name
 
player1 = Hockey_Player('john', 'smith')

def fn_for_hockey_player(hp):
    #...hp.first # produces string
    #...hp.last  # produces string
    pass

## template rules used:
##     -compound: 2 fields
