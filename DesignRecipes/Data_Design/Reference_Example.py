'''
Design a data definition to hold an arbitrary amount of schools, each school
has a name and tuition.
'''

class School:
    def __init__(self,name,tuition):
        self.name = name
        self.tuition = tuition
        
## School is School(String, Integer)
## interp. a school with a name as string and tuition as integer.

s1 = School('Uni of BC', 100000)
s2 = School('Uni of Ont', 200000)
s3 = School('Uni of Sask', 230000)

def fn_for_school(s):
    #...s.name
    #...s.tuition
    pass

## ListOfSchools is one of:
##     - []
##     - [School] + ListOfSchools
## interp. a list of schools

list1 = []             #s1, s2, s3 defined above
list2 = [s1]
list3 = [s1, s2]     
list4 = [s1, s2, s3]

def fn_for_ListOfSchools(los):
    if not los:
        pass
    else:
        #...fn_for_school(los[0])...fn_for_ListOfSchools(los[1:])
        pass

'''
Design a function to consume a ListOfSchools and produce information about each
of those schools name and tuition in the form of a string.
'''

## ListOfSchools -> String
## produces a string with all the names and tuitions in a given list of schools

##stub
##def ListOfSchoolInfo(los):
##    return 'a'

def ListOfSchoolInfo(los):
    if not los:
        return ''
    else:
        return (SchoolInfo(los[0]) + ' ' + ListOfSchoolInfo(los[1:])).rstrip()

def tests_for_ListOfSchoolInfo():
    s1 = School('Uni of BC', 100000)
    s2 = School('Uni of Ont', 200000)
    s3 = School('Uni of Sask', 230000)
    list1 = []            
    list2 = [s1]
    list3 = [s1, s2]     
    list4 = [s1, s2, s3]
    assert ListOfSchoolInfo(list1) == ''
    assert ListOfSchoolInfo(list2) == 'Uni of BC costs 100000'
    assert ListOfSchoolInfo(list3) == 'Uni of BC costs 100000 Uni of Ont costs 200000'
    assert ListOfSchoolInfo(list4) == 'Uni of BC costs 100000 Uni of Ont costs 200000 Uni of Sask costs 230000' 
    print('list tests passed')
     
## School -> String
## produces a string with the name and cost of tuition of the school

##stub
##def SchoolInfo(s):
##    return 'a'

def SchoolInfo(s):
    return s.name + ' costs ' + str(s.tuition)

def tests_for_SchoolInfo():
    s1 = School('Uni of BC', 100000)
    s2 = School('Uni of Ont', 200000)
    s3 = School('Uni of Sask', 230000)
    assert SchoolInfo(s1) == 'Uni of BC costs 100000'
    assert SchoolInfo(s2) == 'Uni of Ont costs 200000'
    assert SchoolInfo(s3) == 'Uni of Sask costs 230000'
    print('school info tests passed')
    
tests_for_SchoolInfo()
tests_for_ListOfSchoolInfo()
