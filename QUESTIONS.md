# Reflection Questions

Answer these questions thoroughly after completing the assignment, using examples from your code. Good answers will be 1-2 paragraphs that cite specific code examples and show a meaningful reflection on how your development went, and how it could be improved in the future.

## Question 1

Compare the difficulty integrating (that is, connecting new code into the existing structure) the new apportionment features (Hamilton and Huntington-Hill) to code in HW3 vs. integrating Excel support to your code in HW1 Part 2. Which change was easier? Justify your answer. Answer this question honestly! If you think Prof. McBurney's design sucks and is hard to work with, we want to know! (If members of your HW3 group was on different teams for HW1, then they should answer this question separately.)

## Answer 1
It was easier to integrate the new apportionment feature Huntington-Hill to code in HW3 than integrating Excel support to my code in HW1 Part2 because in HW3 the Apportionment interface was already created for me so all I had to do was override the Representation method in the HuntingtonHillMethod class to perform apportionment the Huntington-Hill way. 
I also had access to the AdamsMethod, so I could see how  the Apportionment interface was integrated by a different apportionment method. I found this helpful because I wasn't sure how to correctly call methods from the Apportionment interface and how to create a new Representation object. For example, I wrote line 8 in HuntingtonHillMethod based on line 63 in Adams method. This line was "validateInputs(states, targetRepresentatives);". I found integrating Excel in HW1 Part2 to be harder because we didn't have a csvReader class in our code we just handled the reading of the csv file in the main method
so when we had to integrate Excel we had to refactor our code and create new classes for csvReader and excelReader.

## Question 2

What is the benefit of the Factory classes that handle instantiating the relevant objects? How this could make future changes easier? Be specific about what could you could expect to change.

## Answer 2

The factory classes allow the creation of each object, StateSupplier, ApportionmentMethod, and RepresentationFormat to be in one place. Another benefit is that they allow for abstraction, which means any changes needed can be done without changing the client code. Factory classes can also handle exceptions from the initiation of the object, so object creation exceptions are handled right then and there. Say in the future, we need to change the way the objects are created, all we have to do is update the factory classes without digging through the client code. The same can be said for exceptions. If the implementation of the object is change in any way, we can also update in one location of the object's factory class. For example, if we were to add another type of method, we can easily add it into the factory class for Apportionment Method, and add the necessary implementations. The factory classes also help with readability and reduce the amount of copy-and-paste code because the object instantiation is concentrated in the factory classes.