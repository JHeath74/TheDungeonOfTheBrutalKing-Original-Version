Silverward Sentinels Static Spell Registry

This folder contains a generated snippet to help you convert the reflection-based
`SpellFactory` back to a typed, static registry for production builds.

Files:
- generated-silverward-registry.txt: imports + Map.entry lines to register Silverward spells into `SpellFactory`.

How to apply:
1. Open `src/Spells/SpellFactory.java` in your editor.
2. At the top with other import lines, paste the `import Guild.SilverwardSentinels.Spells.*` lines from the generated file.
3. Locate the `SILVERWARD_SENTINELS` Map.ofEntries(...) and paste the `Map.entry(...)` lines into the map (replace existing entries if desired).
4. Compile the project: `javac -d bin -sourcepath src @sources.list` or via your IDE.

Notes:
- The generated snippet assumes each spell has a public no-arg constructor. If any spell requires parameters, update the Map.entry to use a lambda that constructs the spell correctly.
- After converting to a static registry, you can remove the runtime auto-discovery from `SpellFactory` if you prefer a fully static approach.
