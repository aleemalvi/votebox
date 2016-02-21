# Running a Simple VoteBox election #

A "simple" election is one featuring only [Supervisor](Supervisor.md) and VoteBox booth machines (encryption is optional as well).  Any configuration files _without_ `USE_COMMIT_CHALLENGE_MODEL` set to `true` can be used in a SimpleElection (they must, however, be consistent between Supervisor and VoteBoxes).  The [PreRendered](PRUI.md) `ballot.zip` used in this example can be found [here](http://votebox.cs.rice.edu/images/instrux/small/presidential-only-simple.bal.zip); the [Ballot Creator](PrepToolGuide.md) `.bal` file used to generate it is [here](http://votebox.googlecode.com/files/presidential-only.bal).

![http://votebox.cs.rice.edu/images/simple-election-diagram.png](http://votebox.cs.rice.edu/images/simple-election-diagram.png)

## Instructions ##
  * Place supervisor.jar & votebox.jar on seperate machines.  Any number of each may be in use, provided there is no more than one per machine and at least one of each on the network.
  * Start the supervisor and votebox jars
    * Instructions may be found [here](Supervisor.md) and [here](VoteBox.md), respectively.
  * While the auditorium network is being established and discovery is ongoing, you will see the following screens on supervisors and voteboxes respectively.
    * ![http://votebox.cs.rice.edu/images/instrux/small/Supervisor%20-%20waiting.png](http://votebox.cs.rice.edu/images/instrux/small/Supervisor%20-%20waiting.png)
    * ![http://votebox.cs.rice.edu/images/instrux/small/Votebox%20-%20waiting.png](http://votebox.cs.rice.edu/images/instrux/small/Votebox%20-%20waiting.png)
  * When a Supervisor discovers its first VoteBox the screen changes to one similar to this.
    * ![http://votebox.cs.rice.edu/images/instrux/small/Supervisor%20-%20discovered.png](http://votebox.cs.rice.edu/images/instrux/small/Supervisor%20-%20discovered.png)
  * You then activate a console (which will number all VoteBoxes), select a ballot, and open the polls.
    * ![http://votebox.cs.rice.edu/images/instrux/small/Supervisor%20-%20activate.png](http://votebox.cs.rice.edu/images/instrux/small/Supervisor%20-%20activate.png)
    * ![http://votebox.cs.rice.edu/images/instrux/small/Votebox%20-%20numbered.png](http://votebox.cs.rice.edu/images/instrux/small/Votebox%20-%20numbered.png)
    * ![http://votebox.cs.rice.edu/images/instrux/small/Supervisor%20-%20Select%20Ballot.png](http://votebox.cs.rice.edu/images/instrux/small/Supervisor%20-%20Select%20Ballot.png)
    * ![http://votebox.cs.rice.edu/images/instrux/small/Supervisor%20-%20polls%20open.png](http://votebox.cs.rice.edu/images/instrux/small/Supervisor%20-%20polls%20open.png)
  * Until closing the polls, authorize VoteBoxes to allow a vote to be cast from it.
    * ![http://votebox.cs.rice.edu/images/instrux/small/Votebox%20-%20Voting.png](http://votebox.cs.rice.edu/images/instrux/small/Votebox%20-%20Voting.png)
  * Once polls are closed, a tally will be displayed.
    * ![http://votebox.cs.rice.edu/images/instrux/small/Supervisor%20-%20tally.png](http://votebox.cs.rice.edu/images/instrux/small/Supervisor%20-%20tally.png)