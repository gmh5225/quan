# quan
High-level programming language on LLVM with JVM output.

The project has the following goals:
1. Creation of a powerful, compiled, high-level programming language.
2. Providing code support on different platforms, but even within Linux / Windows / Mac OS this will be enough.
3. Integration of C libraries of similar languages into the Java byte code environment. The main goal is to integrate C and Python code.
4. Creating a clear and understandable system for writing code. Over time, the syntax of the language will become both more rigorous and more functional.
5. Ability to support work directly with byte code, as well as memory.
6. As needed, it is possible to create additional garbage collector modules, etc. It is better that such a scavenger rely on AI training.
7. The project will be available for free, developed in your spare time, does not claim to be a leader, and its results will be visible objectively.

To implement the tasks set, the code will be written in Java and use LLVM, LLJVM and, possibly, other libraries. But only in those cases when creating your own modules would be time-consuming and pointless.
Any support for the project, including financial support or in the form of corrections or suggestions, is welcome!

Detailed information on working with the language, its syntax will be in the Wiki section.

### First steps:
1. There is already a ready-made version for the Tokenizer class, it is the starting point when reading the code.
3. Creation of the Parser class for simple processing of tokens. Operations and blah blah blah... ;)
4. Execution of primitive codes on LLVM, testing LLJVM.
5. If the above stages are successful, it will be possible to integrate LLJVM (LLVM) with the Parser class.
6. Thus, it will be possible to obtain a ready-made result of the code.
7. During development, I think it will become common to repeatedly update classes.

### In long-term planning (main direction):
1. The language should at least keep up with Java in terms of work.
2. The language should be freer, but at the same time equally stricter.
3. Establish access to all those libraries that can be integrated using LLJVM (LLVM).
4. Planned integration with PyTorch (torch).
