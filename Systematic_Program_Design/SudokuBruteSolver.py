'''
This program solves a sudoku puzzle through brute force
'''

## =================================
## Data definitions

## Value is a Natural [1, 9]
## INTERP. Value is a natural number from one to nine representing a value
## in one of the puzzles squares.

## Board is a [ListOf Values|False] that is 81 elements long.
## INTERP. Board is a list 81 elements long representing all the squares
## on the board, each element in the list is either a Value or a False.

## Pos is a Natural [0, 80]
## INTERP. Pos is a position of a square on the board. ranging from 0 (first
## square) to 80 (last square).
## for a given p:
## - the row is the quotient of p/9,
## - and column is the remainder of p/9.

## Unit is [ListOf Pos] of length 9
## INTERP. Unit is a list of every square in either a row, column, or box.
## 27 in total because there are 9 rows, 9 columns, and 9 boxes.

## =================================
## Constants

ALL_VALUES = [1,2,3,4,5,6,7,8,9]

B = False #B stands for blank square

#empty board
BOARD_1 = [B,B,B,B,B,B,B,B,B,
           B,B,B,B,B,B,B,B,B,
           B,B,B,B,B,B,B,B,B,
           B,B,B,B,B,B,B,B,B,
           B,B,B,B,B,B,B,B,B,
           B,B,B,B,B,B,B,B,B,
           B,B,B,B,B,B,B,B,B,
           B,B,B,B,B,B,B,B,B,
           B,B,B,B,B,B,B,B,B]

# puzzles for testing
EASY_BOARD = [7,3,B,B,B,5,B,B,B,
              B,4,B,B,6,B,B,B,B,
              B,B,1,B,B,9,B,5,B,
              B,5,B,B,B,1,B,9,2,
              B,B,B,B,B,B,B,B,B,
              B,B,B,B,4,7,5,B,8,
              3,B,B,B,7,2,B,B,B,
              6,9,B,B,B,B,2,B,B,
              B,B,B,6,3,B,4,B,B,]

EASY_SOLUTION = [7,3,2,8,1,5,9,4,6,
                 5,4,9,7,6,3,8,2,1,
                 8,6,1,4,2,9,3,5,7,
                 4,5,6,3,8,1,7,9,2,
                 2,7,8,5,9,6,1,3,4,
                 9,1,3,2,4,7,5,6,8,
                 3,8,4,9,7,2,6,1,5,
                 6,9,7,1,5,4,2,8,3,
                 1,2,5,6,3,8,4,7,9]


EASY_BOARD2 = [B,B,B,2,6,B,7,B,1,
               6,8,B,B,7,B,B,9,B,
               1,9,B,B,B,4,5,B,B,
               8,2,B,1,B,B,B,4,B,
               B,B,4,6,B,2,9,B,B,
               B,5,B,B,B,3,B,2,8,
               B,B,9,3,B,B,B,7,4,
               B,4,B,B,5,B,B,3,6,
               7,B,3,B,1,8,B,B,B]

EASY_SOLUTION2 = [4,3,5,2,6,9,7,8,1,
                  6,8,2,5,7,1,4,9,3,
                  1,9,7,8,3,4,5,6,2,
                  8,2,6,1,9,5,3,4,7,
                  3,7,4,6,8,2,9,1,5,
                  9,5,1,7,4,3,6,2,8,
                  5,1,9,3,2,6,8,7,4,
                  2,4,8,9,5,7,1,3,6,
                  7,6,3,4,1,8,2,5,9,]

UNSOLVABLE = [1,2,3,4,5,6,7,8,B,
              B,B,B,B,B,B,B,B,2,
              B,B,B,B,B,B,B,B,3,
              B,B,B,B,B,B,B,B,4,
              B,B,B,B,B,B,B,B,5,
              B,B,B,B,B,B,B,B,6,
              B,B,B,B,B,B,B,B,7,
              B,B,B,B,B,B,B,B,8,
              B,B,B,B,B,B,B,B,9]

INVALID_B = [1,2,3,4,5,6,7,8,4,
             B,B,B,B,B,B,B,B,2,
             B,B,B,B,B,B,B,B,3,
             B,B,B,B,B,B,B,B,4,
             B,B,B,B,B,B,B,B,5,
             B,B,B,B,B,B,B,B,6,
             B,B,B,B,B,B,B,B,7,
             B,B,B,B,B,B,B,B,8,
             B,B,B,B,B,B,B,B,9]

#UNITS. position of all rows, columns, and boxes
ROWS = [[ 0,  1,  2,  3,  4,  5,  6,  7,  8],
        [ 9, 10, 11, 12, 13, 14, 15, 16, 17],
        [18, 19, 20, 21, 22, 23, 24, 25, 26],
        [27, 28, 29, 30, 31, 32, 33, 34, 35],
        [36, 37, 38, 39, 40, 41, 42, 43, 44],
        [45, 46, 47, 48, 49, 50, 51, 52, 53],
        [54, 55, 56, 57, 58, 59, 60, 61, 62],
        [63, 64, 65, 66, 67, 68, 69, 70, 71],
        [72, 73, 74, 75, 76, 77, 78, 79, 80]]


COLS = [[ 0, 9, 18, 27, 36, 45, 54, 63, 72],
        [1, 10, 19, 28, 37, 46, 55, 64, 73],
        [2, 11, 20, 29, 38, 47, 56, 65, 74],
        [3, 12, 21, 30, 39, 48, 57, 66, 75],
        [4, 13, 22, 31, 40, 49, 58, 67, 76],
        [5, 14, 23, 32, 41, 50, 59, 68, 77],
        [6, 15, 24, 33, 42, 51, 60, 69, 78],
        [7, 16, 25, 34, 43, 52, 61, 70, 79],
        [8, 17, 26, 35, 44, 53, 62, 71, 80]]



BOXES = [[ 0, 1, 2, 9,10,11,18,19,20],
         [ 3, 4, 5,12,13,14,21,22,23],
         [ 6, 7, 8,15,16,17,24,25,26],
         [27,28,29,36,37,38,45,46,47],
         [30,31,32,39,40,41,48,49,50],
         [33,34,35,42,43,44,51,52,53],
         [54,55,56,63,64,65,72,73,74],
         [57,58,59,66,67,68,75,76,77],
         [60,61,62,69,70,71,78,79,80]]

UNITS = ROWS + COLS + BOXES

##==================================
## Functions

## Board -> Board or False
## produces the solution to the board or false if no solution is possible.
## Always starts with a valid board.

def solver(b):
    def solve_b(b):
        if b.count(False) == 0:
            return b
        else:
            return solve_lob(nextBoards(b))


    def solve_lob(lob):
        if not lob:
            return False
        else:
            board = solve_b(lob[0])
            if board:
                return board
            else:
                return solve_lob(lob[1:])

    return solve_b(b)

## Board -> listOf Boards
## produces a list of the next set of valid boards.
## finds the first empty square, then produces a list of boards with the empty
## square filled with values ranging from 1 to 9.

def nextBoards(b):
    return filterInvalid(FillValues(b, b.index(False)))

## Board Pos -> listOf Boards
## given a position on a given board, produces a list of boards with given
## position replaced with values 1 to 9 on each copy of a given board.


def FillValues(b, p):
    global ALL_VALUES
    def newBoard(v):
        copy = b[:]
        copy[p] = v
        return copy
    
    return list(map(newBoard, ALL_VALUES))

## ListOf Boards -> ListOf Boards
## given a list of boards, filters out the boards with illegal values


def filterInvalid(lob):
    return list(filter(validBoard, lob))

## Board -> Boolean
## returns True if the board is a valid board, otherwise False
## board is valid if no unit contains a duplicate value
## ?????

def validBoard(b):
    global UNITS
    def valid_Units(lou):              #list of units -> Boolean
        return all(map(valid_U, lou))

    def valid_U(u):                    #unit -> Boolean
        return noDuplicates(keepOnlyValues([b[i] for i in u]))

    def noDuplicates(lov):             #list of values -> Boolean
        return all(map(lambda x: lov.count(x) == 1, lov))

    def keepOnlyValues(lov):           #list of values -> list of values
        return list(filter(None,lov))

    return valid_Units(UNITS)

## ========== Unit Tests ==============================
assert validBoard(EASY_BOARD2) == True
assert validBoard(INVALID_B) == False

assert filterInvalid([INVALID_B]) == []
assert filterInvalid([INVALID_B,
                      INVALID_B,
                      EASY_BOARD2]) == [EASY_BOARD2]

assert FillValues(BOARD_1,0) ==  [[1] + BOARD_1[1:],
                                  [2] + BOARD_1[1:],
                                  [3] + BOARD_1[1:],
                                  [4] + BOARD_1[1:],
                                  [5] + BOARD_1[1:],
                                  [6] + BOARD_1[1:],
                                  [7] + BOARD_1[1:],
                                  [8] + BOARD_1[1:],
                                  [9] + BOARD_1[1:]]
 
assert nextBoards(EASY_BOARD) == [[7,3,2,B,B,5,B,B,B,
                                   B,4,B,B,6,B,B,B,B,
                                   B,B,1,B,B,9,B,5,B,
                                   B,5,B,B,B,1,B,9,2,
                                   B,B,B,B,B,B,B,B,B,
                                   B,B,B,B,4,7,5,B,8,
                                   3,B,B,B,7,2,B,B,B,
                                   6,9,B,B,B,B,2,B,B,
                                   B,B,B,6,3,B,4,B,B,],

                                  [7,3,6,B,B,5,B,B,B,
                                   B,4,B,B,6,B,B,B,B,
                                   B,B,1,B,B,9,B,5,B,
                                   B,5,B,B,B,1,B,9,2,
                                   B,B,B,B,B,B,B,B,B,
                                   B,B,B,B,4,7,5,B,8,
                                   3,B,B,B,7,2,B,B,B,
                                   6,9,B,B,B,B,2,B,B,
                                   B,B,B,6,3,B,4,B,B,],

                                  [7,3,8,B,B,5,B,B,B,
                                   B,4,B,B,6,B,B,B,B,
                                   B,B,1,B,B,9,B,5,B,
                                   B,5,B,B,B,1,B,9,2,
                                   B,B,B,B,B,B,B,B,B,
                                   B,B,B,B,4,7,5,B,8,
                                   3,B,B,B,7,2,B,B,B,
                                   6,9,B,B,B,B,2,B,B,
                                   B,B,B,6,3,B,4,B,B,],

                                  [7,3,9,B,B,5,B,B,B,
                                   B,4,B,B,6,B,B,B,B,
                                   B,B,1,B,B,9,B,5,B,
                                   B,5,B,B,B,1,B,9,2,
                                   B,B,B,B,B,B,B,B,B,
                                   B,B,B,B,4,7,5,B,8,
                                   3,B,B,B,7,2,B,B,B,
                                   6,9,B,B,B,B,2,B,B,
                                   B,B,B,6,3,B,4,B,B,]
    ]

assert solver(EASY_SOLUTION) == EASY_SOLUTION
assert solver(EASY_SOLUTION2) == EASY_SOLUTION2
assert solver(UNSOLVABLE) == False
assert solver(EASY_BOARD) == EASY_SOLUTION
assert solver(EASY_BOARD2) == EASY_SOLUTION2
print('all tests passed')
