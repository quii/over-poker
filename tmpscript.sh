mkdir ../data
touch ../data/pre-flop-flop.data
touch ../data/pre-flop-turn.data
touch ../data/pre-flop-river.data
for file in ../data/*; do echo "something" > $file; done
