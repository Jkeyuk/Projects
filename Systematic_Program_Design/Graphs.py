##========================================================================
## data design

class GraphNode:
    def __init__(self, value, exits):
        self.value = value
        self.exits = exits

## GraphNode is GraphNode(string, [ListOf GraphNodes])
## INTERP.
##     - value is a string representing the nodes value
##     - exits is a list of graph nodes lead to by the main graph node

##========================================================================
## Examples

#node 1 leads to node 2
node2 = GraphNode('room 2', [])
node1 = GraphNode('room 1', [node2])

#node a leads to node b which leads back to node a
nodeB = GraphNode('room B', [])
nodeA = GraphNode('room A', [nodeB])
nodeB.exits = [nodeA]

#Node A leads to Node B which leads to Node C which leads back to Node A
GnC = GraphNode('graph node C', [])
GnB = GraphNode('graph node B', [GnC])
GnA = GraphNode('graph node A', [GnB])
GnC.exits = [GnA]

##========================================================================
## Template

def fn_for_Graph(g):
    # todo is worklist accumulator of list of nodes needed to be worked on.
    # visited is a list of strings which is a context perserving accumulator
    # showing which nodes we have visited.
    def fn_for_Node(n, todo, visited):
        if n.value in visited:
            return fn_for_lon(todo, visited)
        else:
            #...fn_for_lon(n.exits + todo, visited + [n.value])..n.value
            pass

    def fn_for_lon(todo, visited):
        if not todo:
            #...
            pass
        else:
            #...fn_for_Node(todo[0], todo[1:], visited)
            pass

    return fn_for_Node(g, [], [])

##========================================================================
## Functions

'''
Design a function that consumes a room and a room name, and produces
true if you can reach the given room name from the given room.
'''
## A room is a graph node
## GraphNode, String -> Boolean
## onsumes a room and a room name, and produces true if you can reach the
## given room name from the given room. 

def isReachable(g, rn):
    # todo is worklist accumulator of list of nodes needed to be worked on.
    # visited is a list of strings which is a context perserving accumulator
    # showing which nodes we have visited.
    def fn_for_Node(n, todo, visited):
        if n.value in visited:
            return fn_for_lon(todo, visited)
        else:
            if rn is n.value:
                return True
            else:
                return fn_for_lon(n.exits + todo, visited + [n.value])
            
    def fn_for_lon(todo, visited):
        if not todo:
            return False
        else:
            return fn_for_Node(todo[0], todo[1:], visited)
            
    return fn_for_Node(g, [], [])

assert isReachable(GnA, 'graph node A') == True
assert isReachable(GnA, 'graph node D') == False
assert isReachable(GnA, 'graph node C') == True
assert isReachable(GnC, 'graph node A') == True
assert isReachable(node2, 'room 1') == False
print('reachable tests passed')

'''
Design a function that produces the number of rooms reachable from a given room.
'''

## GraphNode -> Number
## produces the number of rooms reachable from a given room. Including the first
## room.

def CountRooms(g):
    # todo is worklist accumulator of list of nodes needed to be worked on.
    # visited is a list of strings which is a context perserving accumulator
    # showing which nodes we have visited.
    def fn_for_Node(n, todo, visited):
        if n.value in visited:
            return fn_for_lon(todo, visited)
        else:
            return fn_for_lon(n.exits + todo, visited + [n.value])

    def fn_for_lon(todo, visited):
        if not todo:
            return len(visited)
        else:
            return fn_for_Node(todo[0], todo[1:], visited)

    return fn_for_Node(g, [], [])

assert CountRooms(node2) == 1
assert CountRooms(node1) == 2
assert CountRooms(GnA) == 3

print('count rooms test passed')

'''
Design a function that produces the room with the most exits.
'''

## GraphNode -> GraphNode
## produces GraphNode with the most exits.

def mostExits(g):
    # todo is worklist accumulator of list of nodes needed to be worked on.
    # visited is a list of strings which is a context perserving accumulator
    # showing which nodes we have visited.
    # rsf is a result so far accumulator holding the graph with the most exits.
    def fn_for_Node(n, todo, visited, rsf):
        if n.value in visited:
            return fn_for_lon(todo, visited, rsf)
        else:
            if len(n.exits) > len(rsf.exits):
                return fn_for_lon(n.exits + todo, visited + [n.value], n)
            else:
                return fn_for_lon(n.exits + todo, visited + [n.value], rsf)

    def fn_for_lon(todo, visited, rsf):
        if not todo:
            return rsf
        else:
            return fn_for_Node(todo[0], todo[1:], visited, rsf)

    return fn_for_Node(g, [], [], g)

g5 = GraphNode('room 5', [])
g4 = GraphNode('room 4', [])
g3 = GraphNode('room 3', [g5])
g2 = GraphNode('room 2', [g4])
g1 = GraphNode('room 1', [g2,g3])
g5.exits = [g1,g2,g3,g4]

assert mostExits(g4) == g4
assert mostExits(g1) == g5
assert mostExits(g2) == g2
print('most exits tests passed')


