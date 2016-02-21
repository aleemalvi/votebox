## What is "evil" code in VoteBox? ##

VoteBox contains code meant to facilitate human factors testing.  This code allows for the observation, and subsequent recording, of all user input across multiple voting sessions.  This capability goes against the principle of voter annonminity; in a proper election setting, a user being able to identify themselves via side channels (such as this user input recording) would open the system to voter intimidation and vote buying attacks.

However, the capacity to measure user interactions with new or modified UI models is of great usefulness.  Our compromise is to mark such code as "evil".  Simply searching (case insensitively) for "evil" within the codebase will turn up all the code involved in recording user input.  Additionally, our build process includes both "evil" and "good" targets which invoke a preprocessor so that stripping out the offending code is possible, in an automated manner.

Build Targets:
  * preprocess-good
    * strips out all evil code, copying the new source into the tmp directory
  * preprocess-evil
    * leaves in all evil code, copying the new source into the tmp directory
  * compile-good
    * preprocess-good, then compile tmp into build directory
  * compile-evil
    * preprocess-evil, then compile tmp into build directory
  * jar-good
    * compile-good, then jar build directory as needed
  * jar-evil
    * compile-evil, then jar build directory as needed
  * release-good
    * jar-good, then build platform specific executables for Windows/Mac and bundle them
  * release-evil
    * jar-evil, then build platfrom specific executables for Windows/Mac and bundle them

The preprocessing of VoteBox code is carried out using a custom ant task held in jpp.jar (of the ant directory in the repository).  Definitions in build.xml take the form '

&lt;define name="Evil"/&gt;

', with the source equiavlent being '//#define Evil'.  Supported control statements are '//#ifdef 

&lt;DEFINE&gt;

', '//#ifndef 

&lt;DEFINE&gt;

', and '//#endif'.