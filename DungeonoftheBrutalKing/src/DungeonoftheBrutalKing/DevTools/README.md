Blessing of Restoration — DevTools

This folder contains dev/test utilities for the `Blessing of Restoration` spell.

Files
- TestBlessingStandalone.java — a self-contained, standalone demo that does NOT require compiling the whole project. Use this to quickly validate spell logic.
- TestBlessingofRestoration.java — a harness that uses the real engine classes (left in place). It requires compiling the main project and its dependencies.
- TestBlessingofRestorationTest.java — a JUnit 5 skeleton test file (requires JUnit on the classpath).

Quick run (standalone demo)
1) Compile:
   javac -d G:\temp\dtbk_test_classes src\DevTools\TestBlessingStandalone.java

2) Run:
   java -cp G:\temp\dtbk_test_classes DevTools.TestBlessingStandalone

This prints three scenarios: successful cast by a Silverward caster, cast attempt by wrong guild, and cast with insufficient MP.

Run against the real engine (requires project deps)
1) Ensure you have the external jars in the repo `lib/` folder (e.g., jlayer, junit). If missing, download and place them in `lib/`.

2) Create a sources list and compile all sources (Windows cmd):
   dir /b /s "src\*.java" > sources.list
   javac -cp "lib/*" -d bin @sources.list

   - If your project uses modules (module-info.java present) you may need to use `--module-path` and a proper module layout.
   - If you see "module not found" errors (for jlayer, junit, etc.) make sure the JARs are on the module-path or use the classpath (as above).

3) Run the dev harness that uses engine classes (example):
   java -cp "bin;lib/*" DevTools.TestBlessingofRestoration

Troubleshooting notes
- "major version" errors: the compiled class files (or jars) were produced by a newer JDK than the one you're using to compile/run. Upgrade your JDK to match (or recompile the classes with your JDK).
- Encoding errors (e.g., unmappable character): some source files contain special characters; compile with `-encoding UTF-8` or fix offending characters.
- If `javac` complains about module resolution, try compiling with the classpath approach first (`-cp lib/*`) or set up the module path with `--module-path lib`.

JUnit tests
- There's a JUnit 5 skeleton at `src/DevTools/TestBlessingofRestorationTest.java`. Add JUnit 5 jars to `lib/` and run with your usual test runner or via `java -cp "bin;lib/*" org.junit.platform.console.ConsoleLauncher --scan-class-path` once compiled.

If you'd like I can:
- Help add missing JARs to `lib/` and re-run the real-engine compile and test.
- Fix source files that cause encoding or syntax errors so the full project compiles under a specific JDK.
- Run the real test once we have the project compiling.

