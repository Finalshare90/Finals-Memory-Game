
# Deck reserved keywords

## deck
> Reserved keyword for tag naming, stores all cards and deck definitions
**Syntax**:
```
[deck
	gap= 10
	rows= 4	

	[card1 end]
	[card2 end]
	[cardN end]
//...
end]
```

## gap
> Defines a gap between each card in pixels
**Syntax**:
```
[deck
	gap= 10
end]
```

## rows
> Defines the quantity of maximum cards the deck can draw in a single row
**Syntax**:
```
[deck
	rows= 4
end]
```

# Card reserved keywords
## label
> Defines a text inside of a flipped card texture.
**Syntax**: 
```
// ...
[cardName
	// For multi word Strings
	label= "Hello world!"
	
	// For single word Strings
	label= Hello!
[end]
```

## pair
> Defines a card pair. *Should not be used in the referenced card*
**Syntax**:
```
// ...
[card1 
	label= myCard
	fontSize= 10

	pair= myPairName
end]

[myPairName
// ...
end]
```

## fontSize
> Defines the card font size. *duh*
**Syntax:**
```
// ...
[card1
	label= myCard
	fontSize= 10
// ... 
end]
```
## img
> Defines a path to a image in FMG (Final's memory game) root directory.
```
[card1
	label= myCard
	fontSize= 10
	img= assets/cat.png
// ... 
end]
```
