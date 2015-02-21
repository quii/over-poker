# over poker

Over engineering poker, in an attempt to learn the rules of poker and help to analyse the situations you come across. The name comes from the fact this will be definitely over-engineered.

So far all it does is simulate dealing you some cards, going through the flop, turn and River; telling you what kind of hands you have

## Requires

- sbt

## Sample output

```
hand = Hand(6 of Diamonds,8 of Hearts)

flop = List(9 of Spades, 4 of Hearts, Jack of Diamonds)
After flop you've got = List(HighCard(8,6))

theTurn = List(9 of Spades, 4 of Hearts, Jack of Diamonds, Queen of Spades)
After the turn you've got = List(HighCard(8,6))

theRiver = List(9 of Spades, 4 of Hearts, Jack of Diamonds, Queen of Spades, 10 of Spades)
After the river youve got = List(Straight(Queen), HighCard(8,6))
```
