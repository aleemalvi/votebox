## Usage ##

```
java tap.ChallengeWebServer [ballot] [port] [http port]
```
or
```
java -jar challenge.jar [ballot] [port] [http port]
```

Where `[ballot]` is the ballot file of the current election, `[port]` is the port to listen for [Tap](Tap.md) on, and `[http port]` is the port to service HTTP requests on.

## Description ##

In conjunction with [Tap](Tap.md), the VoteBoxWebServer implements the challenge-commit system.  A voter can validate a machine by challenging a ballot on a VoteBox and then interogating the VoteBoxWebServer.