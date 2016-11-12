'''
Design a data definition to represent a binary search tree
'''

class Node:
    def __init__(self, key, value, left, right):
        self.value = value
        self.key = key
        self.left = left
        self.right = right

## BST is one of:
##     - false
##     - Node(Integer, String, BST, BST)
## INTERP. false means no BST
##     - Key is integer representing Node key
##     - Value is a string representing Node value
##     - left and right are subtrees
## INVARIANT: for a given node
##     key is greater the all keys in its left subtree, and smaller then
##     all keys in its right subtree. Same key never appears twice in the Tree.

BST0 = False
BST1 = Node(1,'abc',False,False)
BST7 = Node(7,'ruf',False,False)
BST4 = Node(4,'dcj',False, BST7)
BST3 = Node(3,'ilk',BST1,BST4)

def fn_for_BST(bst):
    if not bst:
        #...something
        pass
    else:
        #...bst.key
        #...bst.value
        #...fn_for_BST(bst.left)
        #...fn_for_BST(bst.right)
        pass
        
'''
Design a function called lookup_key to search for a node in a given BST
'''

## BST Natural -> String or false
## return value of node of a given key in a given BST. false if nothing found.

def lookup_key(bst, key):
    if not bst:
        return False
    else:
        if bst.key == key:
            return bst.value
        elif key < bst.key:
            return lookup_key(bst.left, key)
        else:
            return lookup_key(bst.right, key)
        

def lookup_tests():
    BST0 = False
    BST1 = Node(1,'abc',False,False)
    BST7 = Node(7,'ruf',False,False)
    BST4 = Node(4,'dcj',False, BST7)
    root = Node(3,'ilk',BST1,BST4)

    assert lookup_key(root, 5) == False
    assert lookup_key(root, 1) == 'abc'
    assert lookup_key(root, 7) == 'ruf'
    assert lookup_key(root, 4) == 'dcj'
    assert lookup_key(root, 3) == 'ilk'    
    
    print('lookup tests passed')

lookup_tests()
