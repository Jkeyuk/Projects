## ListOfNumbers is one of:
##     - []
##     - [number] + ListOfNumbers
## interp. a list of integers.

'''
Design the function merge. It consumes two lists of numbers which it assumes
are each sorted in ascending order. It produces a single list of all the numbers
also sorted in ascending order. Your solution should explicitly show the cross
product of type comments table filled in with the values in each case. Your
final function should have a cond with 3 cases. You can do this simplification
using the cross product table by recognizing that there are subtly equal answers. Think carefully about the values of both lists. You might see a way to change a cell content so that 2 cells have the same value.
'''

## ListOfNumbers ListOfNumbers -> ListOfNumbers
## merges two sorted lists of numbers, produces one sorted list of numbers.

##stub
##def merge_list(listA, listB):
##    return []

def merge_list(listA, listB):
    if not listA and not listB:
        return []
    elif not listA or not listB:
        return listA + listB
    else:
        if listA[0] > listB[0]:
            return [listB[0]] + merge_list(listA, listB[1:])
        else:
            return [listA[0]] + merge_list(listA[1:], listB)

def merge_tests():
    list1 = []
    list2 = [2]
    list3 = [3]
    list4 = [4,5,6]
    list5 = [7,8,9]

    assert merge_list(list1, list1) == []                #Both lists empty
    assert merge_list(list1, list4) == [4,5,6]           #Only List A empty
    assert merge_list(list4, list1) == [4,5,6]           #Only List B empty
    assert merge_list(list2, list3) == [2] + [3]         #both lists not empty
    assert merge_list(list4, list5) == [4,5,6] + [7,8,9] #both lists not empty
    assert merge_list(list3, list3) == [3] + [3]         #both lists the same

    print('merge test passed')

merge_tests()
