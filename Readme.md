## Overview
There are four main classes. The RecommendationFinder class is the main class, which takes in user input and utilizes
the other classes to generate the list of recommended products. The Review and ReviewCompare class is primarily
separated so that I could use a custom comparator to sort the list of products I received from the ProductRecommendation
API. My criteria for higher ranking involved first checking if reviewStatistics existed. If so, then the average rating
is compared. If the average rating is the same between two, then the number of reviews is compared.

If this solution were to be extended, there should be an additional Product class, that has a Reviews class.
Otherwise, because the solution goal was not too elaborate, I was able to place a lot of the functions of the Product
class into the RecommendationFinder. The HttpRequest class was a lot of boilerplate code.

## Running Solution
To compile and execute the solution, run the following commands:

``` bash
cd src/
javac -classpath ../lib/json-20160212.jar *.java
java -cp .:../lib/json-20160212.jar RecommendationFinder
```


