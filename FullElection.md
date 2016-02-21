## Instructions for a "full" VoteBox election ##

A "full election" is one where all the features of VoteBox are employed.  More precisely, a full election involves a Supervisor, a Tap, a ChallengeWebServer, and at least one VoteBox.  Additionally, the Commit-Challenge model and encryption must be enabled in both the supervisor and votebox configuration files.  The [sample](Configuration.md) configuration files meet these requirments.  The precompiled ballot used in this walkthrough can be found [here](http://votebox.googlecode.com/files/presidential-only.zip) (the origianl .bal can be found [here](http://votebox.googlecode.com/files/presidential-only.bal)).  Note that these images are from a few seperate execution of the VoteBox system, observed jumps in time are not in error.

![http://votebox.cs.rice.edu/images/election-challenge-diagram.png](http://votebox.cs.rice.edu/images/election-challenge-diagram.png)

## Walkthrough ##
  * Place supervisor.jar, votebox.jar, tap.jar, and challenge.jar on four different, networked machines.
    * Note that the simpler the network, the better.  Ideally, the network will be homogenous with respect to IP version as well.
  * Start the Supervisor, VoteBox, Tap, and ChallengeWebServer programs.
    * Instructions for launching each can be found [here](Supervisor.md), [here](VoteBox.md), [here](Tap.md), and [here](ChallengeWebServer.md) respectively.
  * While the machines are discovering each other, you will see these screens on the Supervisor Console and any VoteBoxes.
    * ![http://votebox.googlecode.com/files/Supervisor%20-%20waiting.png](http://votebox.googlecode.com/files/Supervisor%20-%20waiting.png)
    * ![http://votebox.googlecode.com/files/Votebox%20-%20waiting.png](http://votebox.googlecode.com/files/Votebox%20-%20waiting.png)
  * Once the Supervisor Console has found a VoteBox (though not necissarily a Tap), the option to active becomes available.
    * ![http://votebox.googlecode.com/files/Supervisor%20-%20activate.png](http://votebox.googlecode.com/files/Supervisor%20-%20activate.png)
  * Once activated, every VoteBox will be assigned a label and the Supervisor Console will advance to a new screen.
    * ![http://votebox.googlecode.com/files/Votebox%20-%20numbered.png](http://votebox.googlecode.com/files/Votebox%20-%20numbered.png)
    * ![http://votebox.googlecode.com/files/Supervisor%20-%20authorized.png](http://votebox.googlecode.com/files/Supervisor%20-%20authorized.png)
  * Clicking "Select Ballot" will bring up a dialog that will allow you to find a ballot to use.
    * ![http://votebox.googlecode.com/files/Supervisor%20-%20Select%20Ballot.png](http://votebox.googlecode.com/files/Supervisor%20-%20Select%20Ballot.png)
  * Once a ballot is selected, the polls may be opened by clicking "Open Polls".
    * ![http://votebox.googlecode.com/files/Supervisor%20-%20Open%20Polls.png](http://votebox.googlecode.com/files/Supervisor%20-%20Open%20Polls.png)
  * A voter may then be authorized to use any of the connected VoteBoxes, by clicking the corresponding "Authorize" button.
    * ![http://votebox.googlecode.com/files/Supervisor%20-%20Voting.png](http://votebox.googlecode.com/files/Supervisor%20-%20Voting.png)
  * Once authorized, a VoteBox is sent a copy of the ballot and a voting session begins.
    * ![http://votebox.googlecode.com/files/Votebox%20-%20Voting.png](http://votebox.googlecode.com/files/Votebox%20-%20Voting.png)
    * ![http://votebox.googlecode.com/files/VoteBox%20-%20Challenge%20or%20Record.png](http://votebox.googlecode.com/files/VoteBox%20-%20Challenge%20or%20Record.png)
      * A voter is given the option to "challenge" the system in a full election.
    * ![http://votebox.googlecode.com/files/VoteBox%20-%20Done%20Voting.png](http://votebox.googlecode.com/files/VoteBox%20-%20Done%20Voting.png)
  * This authorize/vote cycle continues until an election ends, at which point the "Close Polls" button will be pressed on the Supervisor Console.  This will bring up the results screen.
  * ![http://votebox.googlecode.com/files/Supervisor%20-%20Results.png](http://votebox.googlecode.com/files/Supervisor%20-%20Results.png)
  * At any time during the election a challenged ballot may be viewed via the challenge web server.
    * ![http://votebox.googlecode.com/files/Challenge%20-%20Challenged%20Ballot.png](http://votebox.googlecode.com/files/Challenge%20-%20Challenged%20Ballot.png)
      * These ballots are not included in the final tally.