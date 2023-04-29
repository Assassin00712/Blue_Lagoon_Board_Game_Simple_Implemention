## Code Review

Reviewed by: Hank Lin, u7426529

Reviewing code written by: Xinwen Cao, u7305675

Component: Player.playerFromString()

### Comments 

The method can create a player from a string, and the work is well done. Also, I think the method is useful because
we only need to parse in a well-formed string. As a result, the player can be initialised by a string once, and can be called
many times after that.



