# MC68000 Assembly Language Plugin [![Build Status](https://travis-ci.com/chrisly42/mc68000-asm-plugin.svg?branch=master)](https://travis-ci.com/chrisly42/mc68000-asm-plugin) [![Coverage Status](https://coveralls.io/repos/github/chrisly42/mc68000-asm-plugin/badge.svg?branch=master)](https://coveralls.io/github/chrisly42/mc68000-asm-plugin?branch=master)

_MC68000 Assembly Language Plugin_ is plugin for Jetbrains IDEs (CLion, IntelliJ, etc.).

![Example Syntax Highlighting](docs/syntaxhighlighting.png "Example Syntax Highlighting")

## Purpose

This plugin delivers support for MC68000 assembly language files ([VAsm](http://sun.hasenbraten.de/vasm/) / DevPac-Style).

It adds a language parser with syntax highlighting, referencing and refactoring support, and a few more features.

I'm an Amiga retro democoder, and the lack of a plugin for M68k was the motivation to write one. Also, diving deep into custom language plugins has a steep
learning curve.

When I started the plugin in July 2021, I was not aware of the [M68k plugin efforts by Jetbrains employee Yann Cébron](https://github.com/YannCebron/m68kplugin)
who has been working on the same topic for quite some time. At the time of writing, his plugin however, has not been release yet. Nevertheless, it has a lot of
awesome features and is pretty advanced. Check it out. You can install both plugins at the same time and see what suits you more.

Big kudos to Yann -- a few features were _inspired_ by his code.

My plugin, on the other hand, is still pretty basic and is the result of about a week's effort. I released a really early first version it because I think
it's "good enough" to get started, and I can return to demo coding with its current state.

## Features

- Parser / Lexer for MC68000 (yes, only 68000 right now!) assembly language files in VAsm / DevPac style
- Syntax highlighting and Color Settings Page (you should really modify the color settings to your likings!)
- Mnemonics code completion
- Symbols / Labels code completion
- References / Refactoring support for local and global labels, and symbol assignments.
- Brace matching
- Quote handler
- Goto Symbol support
- Structure view

## Known issues

- Performance is not optimal yet (no stub index).
- No referencing of macro invocations and macro definitions.
- `Find Usages` always shows _"Unclassified"_ though it shouldn't.
- Macro definitions may cause syntax errors when using backslash arguments.
- Macro invocations are not yet evaluated, thus no referencing to symbols defined via macros (e.g. `STRUCT`).
- No support for includes. Scoping is for global symbols and labels is currently the whole project.
- No support for register replacement (e.g. registers replaced by `EQUR` or `EQURL` will cause syntax errors)
- While the Lexer supports the -spaces option (where a space introduces a comment), this cannot be configured yet.
- No support for other processor instructions, FPU or 68020+ address modes.
- No semantic checking for allowed address modes or data widths yet.
- Unit Test coverage is not as good as it could be (ahem).
- Missing but planned features:
    - Macro definition and evaluation on invocation
    - Folding
    - Semantic inspections
    - Quick fixes
    - Formatter + Code Style Settings
    - Register use analysis (but this only makes sense after macro evaluation)

## Development notice

This plugin has been written in Kotlin 1.5.

It is probably the only plugin (besides [Cajon](https://github.com/chrisly42/cajon-plugin) from the same author) that uses JUnit 5 Jupiter for unit testing so
far (or at least the only one I'm aware of ;) ). The IntelliJ framework actually uses the JUnit 3 TestCase for plugin testing, and it took me quite a while to
make it work with JUnit 5. Feel free to use the code (in package ```de.platon42.intellij.jupiter```) for your projects (with attribution).

## Changelog

### V0.1 (20-Jun-21)

- Initial release.