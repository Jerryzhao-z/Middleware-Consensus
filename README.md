# Middleware-Consensus
Middleware TP Consensus (3-shakehands master-slaves mode)

master: ./gradlew run_args -Pmyargs=8000

slaves: ./gradlew run_args -Pmyargs=@port
