## Code Review

Reviewed by: <Wangtao Jia>, <u7370733>

Reviewing code written by: <Hank Lin> <u7426529>

Component: <isStateStringWellFormed from line 293 to 315>

### Comments 

<The best feature: The structure of the codes is clear and regular expressions are used, which is good.>
The code is relative well-documented and describes the job of code in different parts.
There is no method necessary in this simple task and the code have no methods as well.
Variables are properly named and easy to be understood.

The code is generally good and there are few suggestions I can give:
1.Line 300: The variable statestring can never be null even in the most initial stage, so the code "stateString == null" can be ignored or deleted.
2.It would be better if there are some comments around regular expressions to help others understand what it really represent.




