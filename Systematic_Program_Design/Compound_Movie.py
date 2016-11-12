'''
data definition to represent a movie including title budget and year released.
To help you to create some examples find some interesting movie facts Titanic
200000000 1997 Avatar 237000000 2009 220000000 2012 However feel free to resarch
more on your PROBLEM You have a list of movies you want to watch but you like
to watch your rentals in chronological order. Design a function that consumes
two movies and produces the title of the most recently released movie. Note that
the rule for templating a function that consumes two compound data parameters is
for the template to include all the selectors for both parameters.
'''
#-----------------Data definition-------------------------------

class Movie:
    def __init__(self, title, budget, year):
        self.title = title
        self.budget = budget
        self.year = year

## Movie is Movie(string, integer, integer)
## interp. a movie with:
##     - title as a string representing the title of the movie
##     - budget as a integer showing the amount of money spent making the movie
##     - year as a integer showing the year the movie was made

titanic = Movie('titanic', 200000000, 1997)

def fn_for_Movie(m):
    #...m.title      #produces string
    #...m.budget     #produces integer
    #...m.year       #produces integer
    pass
    
## Template rules used:
##     - compound: 3 fields

#-----------------Function Design---------------------------------------

## Movie Movie -> string
## produces the title of the movie that was most recently released.

'''stub
def most_recent_movie(m1,m2):
    return 'a'
'''
def most_recent_movie(m1,m2):
    if m1.year > m2.year:
        return m1.title
    else:
        return m2.title

def tests():
    titanic = Movie('titanic', 200000000, 1997)
    avatar = Movie('avatar', 237000000, 2009)
    assert most_recent_movie(titanic, avatar) == 'avatar'
    print('test passed')
tests()

    
