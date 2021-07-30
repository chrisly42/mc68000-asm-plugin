# MC68000 Assembly Language Plugin [![Build Status](https://travis-ci.com/chrisly42/mc68000-asm-plugin.svg?branch=main)](https://travis-ci.com/chrisly42/mc68000-asm-plugin) [![Coverage Status](https://coveralls.io/repos/github/chrisly42/mc68000-asm-plugin/badge.svg?branch=main&kill_cache=1)](https://coveralls.io/github/chrisly42/mc68000-asm-plugin?branch=main)

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
- Symbols / Labels / Macros code completion
- References / Refactoring support for local and global labels, symbol assignments, and macros.
- Brace matching
- Quote handler
- Goto Symbol support
- Structure view

## Known issues

- `Find Usages` always shows _"Unclassified"_ though it shouldn't (?)
- Macro invocations are not yet evaluated, thus no referencing to symbols defined via macros (e.g. `STRUCT`).
- No support for includes. Scoping is for global symbols and labels is currently the whole project.
- No support for register replacement (e.g. registers replaced by `EQUR` or `EQURL` will cause syntax errors)
- While the Lexer supports the -spaces option (where a space introduces a comment), this cannot be configured yet (default is off).
- No support for other processor instructions, FPU or 68020+ address modes.
- No semantic checking for allowed address modes or data widths yet.
- Unit Test coverage is not as good as it could be (ahem).
- Missing but planned features:
    - Macro evaluation on invocation
    - Folding
        - Semantic inspections
        - Quick fixes
        - Formatter + Code Style Settings
    - Register use analysis (but this only makes sense after macro evaluation)
    - Cycle counting

## Recommendations

Currently, I would suggest using the fabulous [Browse Word at Caret Plugin](https://plugins.jetbrains.com/plugin/201-browsewordatcaret)
to highlight the same address and data registers while editing (see new `View -> Highlight Word at Caret` menu item).

## Development notice

This plugin has been written in Kotlin 1.5 using Grammar-Kit.

It is probably the only plugin (besides [Cajon](https://github.com/chrisly42/cajon-plugin) from the same author) that uses JUnit 5 Jupiter for unit testing so
far (or at least the only one I'm aware of ;) ). The IntelliJ framework actually uses the JUnit 3 TestCase for plugin testing, and it took me quite a while to
make it work with JUnit 5. Feel free to use the code (in package ```de.platon42.intellij.jupiter```) for your projects (with attribution).

## Changelog

### V0.4 (unreleased)

-

### V0.3 (28-Jul-21)

- Enhancement: Macro contents are no longer parsed, added syntax highlighting options for macros.
- Enhancement: Macro definitions are now word and stub indexed, macro calls reference to definition.
- Enhancement: Macro definition refactoring and find usages support.
- Enhancement: Structural View also shows macro definitions.
- Bugfix: Missing REPT and ENDR assembler directives added.
- Cosmetics: Changed or added some icons at various places.
- Performance: Reference search for global labels and symbols now uses stub index.
- Compatibility: Restored compatibility with IDE versions < 2021.1.
- Performance: Optimized lexer.

### V0.2 (27-Jul-21)

- Cosmetics: Added (same) icon for plugin as for file type.
- Performance: Use Word-Index for global labels and symbols instead of iterating over the file.
- Performance: Use Stub-Index for global labels and symbols.
- Bugfix: No longer reports a syntax error when file lacks terminating End-Of-Line.
- Enhancement: Registers are now offered for code completion, making editing less annoying.

### V0.1 (20-Jul-21)

- Initial public release.