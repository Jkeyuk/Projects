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

##==================================
## Functions

## Board -> Board
## Given a board, returns the solution to the board or empty array if no solution

def sudokuSolver(b):
   ValidSet = filter_invalid(NextSetOfBoards(b))
   
   while True:
      if not ValidSet:
         return []
      
      newSets = []
      for board in ValidSet:
         if board.count(False)==0:
            return board
         else:
            newSets += filter_invalid(NextSetOfBoards(board))

      ValidSet = newSets


## Board -> ListOf Boards
## Given a Board, produces a list of boards each one with the next empty
## square filled in with each one of the possible 9 Values.

def NextSetOfBoards(b):
    global ALL_VALUES
    for i in range(len(b)):
        if not b[i]:
            boards = []
            for v in ALL_VALUES:
                temp = b[:]
                temp[i] = v
                boards.append(temp)
            return boards

## ListOf Boards -> ListOf Boards
## Given a list of board, produces a new list with invalid boards filtered out.
## If no valid boards are given, produces empty list.

def filter_invalid(lob):
    newList = []
    for b in lob:
        if checkRows(b) and checkCols(b) and checkBoxes(b):
            newList.append(b)
    return newList

## Board -> Boolean
## returns False if a Value occurs more then once in a given boards row.
##   Otherwise returns True.

def checkRows(b):
    global ROWS
    for row in ROWS:
        RowValues = [b[i] for i in row]
        for v in RowValues:
            if v:
                if RowValues.count(v) > 1:
                    return False
    return True

## Board -> Boolean
## returns False if a Value occurs more then once in a given boards collumn.
##   Otherwise returns True.

def checkCols(b):
    global COLS
    for col in COLS:
        ColValues = [b[i] for i in col]
        for v in ColValues:
            if v:
                if ColValues.count(v) > 1:
                    return False
    return True

## Board -> Boolean
## returns False if a Value occurs more then once in a given boards boxes.
##   Otherwise returns True.

def checkBoxes(b):
    global BOXES
    for box in BOXES:
        BoxValues = [b[i] for i in box]
        for v in BoxValues:
            if v:
                if BoxValues.count(v) > 1:
                    return False
    return True


## Board -> displays board to console for testing
## produces a display of a given board to be more readable.

def display(b):
    print()
    if not b:
       return print('no solution')
    for i in range(len(b)):
        if not b[i]:
            x = 'B'
        else:
            x = b[i]
        if i%9 == 0:
            print()
            print(x, end=' ')
        else:
            print(x, end=' ')
    print()

assert sudokuSolver(UNSOLVABLE) == []
assert sudokuSolver(EASY_BOARD) == EASY_SOLUTION
assert sudokuSolver(EASY_BOARD2) == EASY_SOLUTION2

print('all tests passed')

