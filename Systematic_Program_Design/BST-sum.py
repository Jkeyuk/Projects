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
Design a function that sums the keys in a BST.
'''

## BST -> Number
## return sum of all keys in a given bst.

def bst_sum(bst):
    if not bst:
        return 0
    else:
        return bst.key + bst_sum(bst.left) + bst_sum(bst.right)
        
def sum_tests():
    BST0 = False
    BST1 = Node(1,'abc',False,False)
    BST7 = Node(7,'ruf',False,False)
    BST4 = Node(4,'dcj',False, BST7)
    root = Node(3,'ilk',BST1,BST4)

    assert bst_sum(BST0) == 0
    assert bst_sum(root) == 15
    assert bst_sum(BST7) == 7
    assert bst_sum(BST4) == 11
        
    print('bst_sum tests passed')

sum_tests()
