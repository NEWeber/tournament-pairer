CREATE TABLE PLAYER(
  ID INT AUTO_INCREMENT,
  FIRST_NAME VARCHAR(255),
  LAST_NAME VARCHAR(255),
  MATCHES OBJECT,
  OPPONENT_IDS OBJECT,
  TOTAL_MATCH_POINTS INT,
  RECORD_THROUGH_ROUND INT,
  MATCHES_PLAYED INT,
  MATCHES_WON INT,
  GAMES_PLAYED INT,
  GAMES_WON INT,
  PLAYER_MATCH_WIN_PERCENT DOUBLE,
  PLAYER_GAME_WIN_PERCENT DOUBLE
);