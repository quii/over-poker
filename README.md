# over poker

[![Build Status](https://travis-ci.org/quii/over-poker.svg?branch=master)](https://travis-ci.org/quii/over-poker)
[![Codacy Badge](https://api.codacy.com/project/badge/d7c7a7be65304876a8fa1bfeebd246e0)](https://www.codacy.com/app/qui666/over-poker)

In an attempt to learn the rules of poker and help to analyse the situations you come across, this models texas hold'em just like a million other projects. The name comes from the fact this will be definitely over-engineered.

So far all it does is simulate dealing you some cards, going through the flop, turn and river; telling you what kind of hands you have

## Vague ambitions

- A lovely website where you can put in the cards currently in play and it will tell you
  - What hands you have
  - What hands you might win
  - What hands your opponent(s) might have
  - The probability of the above

### Other posssibilities

- Some kind of trainer, to walk you through scenarios and test you... on a mathematical level at least

## Requires

- sbt
- Java 8

## Web server

    $ sbt run
    $ follow the prompt...

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
