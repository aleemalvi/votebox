**PRUI**, or **Pre-Rendered User Interface**, is a design technique intended to reduce the size of the trusted computing base of a software artifact (e.g. VoteBox).  By generating most of the user interface ahead of time, we reduce the amount of run-time UI code that needs to be trusted for the system to operate correctly.

In VoteBox, we pre-render ballots using the [Ballot Creator](PrepToolGuide.md) (or "ballot prep tool"). The VoteBox booth software uses the pre-rendered ballot, in the form of a ZIP archive containing only PNG graphics and XML files describing how to show those graphics, to present an interface to the user.

An example of a VoteBox screen and its pre-rendered components is shown below:

| ![http://votebox.cs.rice.edu/images/votebox-sample-race-small.png](http://votebox.cs.rice.edu/images/votebox-sample-race-small.png) | ![http://votebox.cs.rice.edu/images/votebox-sample-race-rects-trans-small.png](http://votebox.cs.rice.edu/images/votebox-sample-race-rects-trans-small.png) |
|:------------------------------------------------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **VoteBox screen**                                                                                                                  | **Pre-rendered images and layout**                                                                                                                          |

The PRUI technique as applied to voting was pioneered by Ka-Ping Kee in his [Pvote](http://pvote.org) voting system project.