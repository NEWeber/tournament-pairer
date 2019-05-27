# Tournament Pairer 

##Todo: 
v 0.1
- ~Create a match model~
- ~Create a match SQL table~
- Create a MatchController to:
    - ~create a match~
    - ~record match results~
- Create a round model
- Create a round SQL table
- Create a round controller
- Create a way to calculate player record
- Create a way to pair players based on record
    - Throw error if there are unreported games and new round pairing is requested
- create a standings service

v 1
- Create a way for players to log in
- Limit access to match results reporting to the players that are in the match and admin
    - Players in the match must agree on results for it to be recorded as final