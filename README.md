#TwitterSmileTracker
A simple GUI application that tells you when Twitter is feeling happy, and when it is feeling sad.

##Current Functionality
Receives a stream of tweets containing smilely faces and frowny faces, records how many occur throughout a certain period, and determines if Twitter is happy or sad during that period.

##Demo
The simple GUI displays on the top whether Twitter is happy (red) or sad (blue) based on the previous 25 tweets that it received.  When the last 25 tweets are a majority of smiley faces, it determines that Twitter is happy, and when they are a majority of frowny faces, it determines that Twitter is sad.  On the bottom of the GUI, a stream of happy (red) and sad (blue) tweets are displayed.  Because the stream of tweets that the application is receiving is very large, only one out of every 50 tweets is displayed, so that the tweets do not flash by too quickly.

![TwitterSmileTracker](https://raw.githubusercontent.com/thomasdclark/TwitterSmileTracker/master/resources/TwitterSmileTracker.gif)

##Future Goals
* Add additional emotions rather than just happy and sad
* Graph the amount of smiley faces and frowny faces over time
* Save data after analysis rather than discarding it
* Improve the GUI

##Third-Party Libraries
Need to be downloaded from respective sites prior to use:
* [Twitter4J](http://twitter4j.org)
