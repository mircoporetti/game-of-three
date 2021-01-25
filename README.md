<h2>The Game of Three</h2>

<h3>Instructions</h3>

    Start the Game executing the following commands:

    1) Build rabbitmq container from docker folder:
    - docker-compose -f rabbit-compose.yml up -d

    2) Run Player1 using jar file placed in rabbit-amqp/target (example of player in auto mode)::
    - mvn clean install
    - cd rabbit-amqp/target
    - java -jar game-of-three.jar --game-of-three.player-name="Player1" --game-of-three.opponent-name="Player2" --game-of-three.mode=AUTO
    
    3) Run Player2 (example of player in manual mode):
    - java -jar game-of-three.jar --game-of-three.player-name="Player2" --game-of-three.opponent-name="Player1"  --server.port=8081 --game-of-three.mode=MANUAL

<h3>Notes</h3>
    
    - You can configure each player passing to "Java -jar..." command the player name, the opponent name and the game mode;
    - The player name and the opponend name have to be the opposite for each player;
    - The game mode can be MANUAL(The player will play manually his/her games) or AUTO(The player will play automatically her/his turn).

<h3>Have a good game :)</h3>

<h2>To Do</h2>

    - Single docker-compose with rabbitmq and players together