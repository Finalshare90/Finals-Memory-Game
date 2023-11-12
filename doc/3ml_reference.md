# LunarLatte Markup Language user's Guide

---

## 3ml core fundamentals

The LunarLatte Markup Language(or 3ml) its a simple Parser designed for storing simple data structured in a Tag systems that can be adapted for using in any third-party software that needs a "complex" way for storing: Configuration files, Player data, Players Scoreboards and much more.

As i told above, it can be adapted for third-party software, but the syntax and fundamentals still the same anywhere that uses a official 3ml-core parser, and that topic will teach you about the general syntax and grammar of 3ml.

### Tags

Each tag will contain 2 values:

* Your name

* His data

The name of a tag will be defined by the data followed by reserved character "[", that we can just call by `declare`.

And the data of this tag, will be anything stored after the declare keyword, until the parser reaches the `end` reserved character. The parser also will skip a line when the parser reaches a data that uses "//", marking it as a comment.

In a real 3ml code, we gonna have something like that:

```
[3mlIsCool
sure
// oopsie, i'm a comment!
yes
yeah
// me too!
end]
```
#### Nested tags
Want a complex data structure and, for example... a reference to a player default attributes? you can use nested tags!
```

[player
    hp= 100
    xp= 100
    [inventory
        iron_sword
        wooden_shield
        health_potion
     end]
end]

```

### Calling tags

You can also call tags inside of a 3ml file, just like you can do with variables in programming languages by following the syntax `$tag`. That keyword can be just named as "`CALL`", and it will copy all the values inside of `tag` to the current caller Tag.

Keep in mind that every called tag must to be initializated and if you use a not-yet declared tag, the parser will return a error or crash the current parsing.

* What you should *NOT* to do when calling a tag:

```
[callerTad
$calledTag 
// DON'T DO THAT!!
// The calledTag doesn't have any data
// or mention in the parser *yet*, he can't figure out
// where is the calledTag.
end]

[calledTag
data1
data2
end]
```

- What you *should* to do when calling a tag:

```
[calledTag
data1
data2
data3
end]


[callerTaf
$calledTag
// The tag already declared, so it is fine to call her values:)
end]
```

#### Calling nested tags
The above calling syntax is only applicable to "root tags", which are tags that doesn't have any parents and aren't nested. To call nested tags, you can use the following syntax:

```
[parent
    [nested1 hi! end]
    [nested2 hello! end]
    [nested3 hey:) end]
 end]

[caller
    $parent>nested1
    $parent>nested3
end]
```




