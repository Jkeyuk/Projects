'''
Design a data definition to help a teacher organize their next field trip.
On the trip lunch must be provided for all students. For each student track
their name their grade from 1 to 12 and whether or not they have allergies.
PROBLEM To plan for the field trip if students are in grade 6 or below the
teacher is responsible for keeping track of their allergies. If a student has
allergies and is in a qualifying grade their name should be added to a special
list. Design a function to produce true if a student name should be added to
this list.
'''
#---------------------data definition--------------------------------

class Student:
    def __init__(self,name,grade,allergies):
        self.name = name
        self.grade = grade
        self.allergies = allergies

## Student is Student(string, integer[1,12], boolean)
## interp. a student with:
##     - name as a string representing their name
##     - grade as a integer[1,12] representing which grade the student is in
##     - allergies as a boolean representing if a student has food allergies

tommy = Student('tommy',4,True)
ben = Student('ben',4,False)
lena = Student('lena',7,False)
jon = Student('jon',7,True)

def fn_for_Studen(s):
    #... s.name            #produces string
    #... s.grade           #produces integer[1-12]
    #... s.allergies       #produces boolean
    pass

## Template rules used:
##     - compound: 3 fields

#---------------------function design--------------------------------

## Student -> boolean
## produces true if the student is in grade 6 or below and has food allergies.

'''Stub
def special_list(s):
    return False
'''

def special_list(s):
    return s.allergies and s.grade < 7

assert special_list(tommy) == True
assert special_list(ben) == False
assert special_list(jon) == False
assert special_list(lena) == False
