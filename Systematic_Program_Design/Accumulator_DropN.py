'''
Design a function that consumes a list of elements lox and a natural
number n and produces the list formed by dropping every nth element from
lox. dropn list 1 2 3 4 5 6 7 2 should produce list 1 2 4 5 7
'''

## [Listof x], Number -> [Listof x]
## consumes a list of elements lox and a natural
## number n and produces the list formed by dropping every nth element from
## lox. dropn list 1 2 3 4 5 6 7, 2 should produce list 1 2 4 5 7

def dropn (loxx, n):
    def dropn(lox, n, acc):
        if not lox:
            return []
        else:
            if n == acc:
                return dropn(lox[1:], n, 0)
            else:
                return [lox[0]] + dropn(lox[1:], n, acc + 1)
    return dropn(loxx, n, 0)

assert dropn([], 2) == []
assert dropn([1,2,3,4,5,6,7], 1) == [1,3,5,7]
assert dropn([1,2,3,4,5,6,7], 2) == [1,2,4,5,7]
assert dropn([1,2,3,4,5,6,7,8,9,10], 3) == [1,2,3,5,6,7,9,10]

print('dropn test passed')
