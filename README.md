# Introduction
This is a repository for a programming assignment in Scala. The problem statement is to analyse your daily log of browser JSON events which represent your article views, and keep a monitor on the biggest context switch for the day.  

# Objective / Tasks TO-DO
1) Read a JSON input file with events and parse it.
2) Find session with the maximum weight.
3) Print session weight and its duration.

# Given
1) Events are on a daily basis.
2) Events are sorted by timestamp.
3) No cycles between events.
4) -1 parent_id means no parent for that event.
5) A session is defined by connected components of an event graph.
6) **Is Event a transition** : _if (event category) not equals (parent category)_, then the event is a transition
7) **Session Weight** : In a session, the maximum transition weight (of 1 event of that session) OR zero

# Assumptions and Inferences

As mentioned clearly in the programming assignment PDF, we can choose a reasonable interpretation if parts of the problem statement are unclear. Since the solution and final answer to the problem statement would completely change depending on the assumptions made, please find below some of the assumptions made :

1) If an event's parent_id is -1, it is assumed that the event is not a transition.
2) If an event is a transition, then only its transition weight exists, else it would be 0.
3) **Transition Weight** : _**It is assumed to be the total number of preceding events whose category is the same as the currently iterated over event.**_
Since the definition could also mean the no. of preceding events in sequence whose category is the same, but different from currently iterated over event. I was not clear on what exactly it meant, hence, chose a reasonable interpretation.
4) **Duration of a Session** : From the given definition, I inferred that session duration can also be the timestamp difference between the 1st and last event of a session, as events are already sorted by timestamps (which is given).
5) **Alternative Solution Methodology** : The problem has been solved using the scala collections, namely, Map. Since this is a typical graph problem, it could have also been solved using the scala graph collection (http://www.scala-graph.org).

# Building the Project : Compiling and Running

**Scala Object to run the program** : _EventsController_

The SessionAnalyzer project has been created using the popular Scala IDE, IntelliJ IDEA. Tto run this given problem assignment, follow either of the two steps :

1) **Using IntelliJ IDEA** : Open the scala object file, named, _EventsController_. Right click inside that file, and select **Run 'EventsController'** from the drop-down menu.
2) **Using Scala command line tool** : Using this method, first compile the scala main class using scala compiler as follows :

scalac EventsController.scala  (Browse to your scala installation folder and specify fully qualified path to scala file, or browse to that location)

Run the program using  : 

scala -cp path-to-the-target-directory -e 'EventsController'