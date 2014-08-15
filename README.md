#TwitterEmotionTracker
The seismograph for tweets.

##History
TwitterEmotionTracker is a fork of [TwitterSmileTracker](https://github.com/thomasdclark/TwitterSmileTracker) to add additional functionality, while still maintaining TwitterSmileTracker.

##Goal
The goal of TwitterEmotionTracker is to create a tool that can be used to determine trends amongst tweets to catch major events at their infancies.  Emotions are one of the best ways to determine when something noteworthy happens.  People use tweets with emotions to express how they feel when something occurs (ie. the outcome of a sporting event or the death of a celebrity).  It is TwitterEmotonTracker's job to catch deviations from the emotional norm before these events begin trending.

##Current Functionality
Receives a stream of tweets containing key emotion words, records how many occur throughout a certain period, and determines how Twitter is feeling over that period.  The collected information is also displayed graphically in real-time so the user can see the data for the past 60 seconds.  Data is also archived, so that it can be viewed at later times, or viewed in a larger batch than just 60 second time intervals.

##Demo
The left side of the GUI displays the emotion that Twitter is currently feeling.  This is determined based on which emotion was tweeted the most over the past second.  There is also a real-time display steam showing select emotion tweets.  Each emotion is designated a color to help differentiate it when it appears on the screen.  On the right side of the GUI is a real-time graph displaying how often each emotion was tweeted over the past 60 seconds.

![TwitterEmotionTracker](https://raw.githubusercontent.com/thomasdclark/TwitterEmotionTracker/master/resources/TwitterEmotionTracker.gif)

##Dependencies
Need to be downloaded from respective sites prior to use:
* [Twitter4J](http://twitter4j.org)
* [OSU CSE Components](http://web.cse.ohio-state.edu/software/common/doc/)
* [Charts4J](https://code.google.com/p/charts4j/)
* [JMathPlot](https://code.google.com/p/jmathplot/)