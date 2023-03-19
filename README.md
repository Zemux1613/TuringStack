# TuringStack

## Name History

My programming language is called TuringStack because it should be based on the stack principle but should also become
turing complete at some point. I also love theoretical computer science and wanted to give Alan Turing representation in
the language. Furthermore I am a fan of the Java programming language which is why I will use Java like syntax in some
places.

## Syntax

### Generall Syntax

```
createRegister <NAME> | create a new register
deleteRegister <NAME> | delete a existing register
push <REGISTER_NAME> <VAR | VALUE> | move an element to the top of the stack
pop <REGISTER_NAME> | removes the last element of the stack and returns it
print <VAR | REGISTER_NAME> | print something out
var <NAME> = <VALUE> | Create a variable NAME and assign VALUE to this variable
```

### Other

- A line break is represented by a semicolon.
- Variable names may contain only alpha characters
- A comment is started with '//' and is valid until the end of the line 