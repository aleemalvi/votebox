## Example vb.conf ##

```
# This .conf file passes parameters to the VoteBox.
# The format of this file is:
# KEY
# value
# the value MUST be on the line after the key.
# All keys are contained (along with their defaults) in
# votebox/AuditoriumParams.java

#location of the log file to write to
LOG_LOCATION
log.out

#when the ballot is cast, encrypt the ballot
CAST_BALLOT_ENCRYPTION_ENABLED
true

#must have CAST_BALLOT_ENCRYPTION_ENABLED set to true
#separates the ballot into a COMMIT, and then either a
#CAST or a CHALLENGE
USE_COMMIT_CHALLENGE_MODEL
false

#changes the display mechanism
#current options are SDL (Linux) and AWT (Windows)
VIEW_IMPLEMENTATION
SDL
```

## Example supervisor.conf ##
```
# This .conf file passes parameters to the Supervisor.
# The format of this file is:
# KEY
# value
# the value MUST be on the line after the key.
# All keys are contained (along with their defaults) in
# votebox/AuditoriumParams.java

#location of the log file to write to
LOG_LOCATION
superlog.out

#when the ballot is cast, encrypt the ballot
CAST_BALLOT_ENCRYPTION_ENABLED
true

#must have CAST_BALLOT_ENCRYPTION_ENABLED set to true
#separates the ballot into a COMMIT, and then either a
#CAST or a CHALLENGE
USE_COMMIT_CHALLENGE_MODEL
false
```

## Description ##
Configuration files contain KEY/VALUE pairs, seperated by a new-line, with white-space and characters following a '#' ignored.

Valid keys are:
  * DISCOVER\_TIMEOUT
    * Integer, milliseconds
    * Default: 4000
  * DISCOVER\_PORT
    * Integer, port number
    * 9782
  * DISCOVER\_REPLY\_TIMEOUT = 1000;
    * Integer, milliseconds
    * Default: 1000
  * DISCOVER\_REPLY\_PORT = 9783;
    * Integer, port number
    * Default: 9783
  * LISTEN\_PORT
    * Integer, port number
    * Default: 9700
  * JOIN\_TIMEOUT
    * Integer, millisecond
    * Default: 1000
  * BROADCAST\_ADDRESS
    * String, network address
    * Default: 255.255.255.255
  * LOG\_LOCATION
    * String, file path
    * Default: log.out
  * KEYS\_DIRECTORY
    * String, directory/classloader path
    * Default: "/keys/"
  * VIEW\_IMPLEMENTATION
    * String, one of SDL, AWT
    * Default: SDL
  * RULE\_FILE
    * String, directoy path
    * Default: rules
  * CAST\_BALLOT\_ENCRYPTION\_ENABLED
    * Boolean
    * Default: false
  * USE\_COMMIT\_CHALLENGE\_MODEL
    * Boolean
    * Default: false
  * USE\_ELO\_TOUCH\_SCREEN
    * Boolean
    * Default: false
  * ELO\_TOUCH\_SCREEN\_DEVICE
    * String, path to device
    * Default: null
  * VIEW\_RESTART\_TIMEOUT
    * Integer, milliseconds
    * Default: 5000
  * DEFAULT\_SERIAL\_NUMBER
    * Integer
    * Default: -1 (ignored)
  * DEFAULT\_REPORT\_ADDRESS
    * String, IP, Computer Name, Domain Name, etc.
    * Default: "" (ignored, used exclusively by Tap)
  * DEFAULT\_CHALLENGE\_PORT
    * Integer, port number
    * Default: -1 (ignored, used by Tap and ChallengeWebServer)
  * DEFAULT\_HTTP\_PORT
    * Integer, port number
    * Default: 80 (used exclusively by ChallengeWebServer)
  * DEFAULT\_BALLOT\_FILE
    * String, path to file
    * Default: "" (ignored, used by ChallengeWebServer)
  * ENABLE\_NIZKS
    * Boolean
    * Default: false