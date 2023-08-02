# Backpack

A plugin for modlike backpacks, primarily meant to be used in survival.

## Changelog

Changelog of every version published on [SpigotMC](https://www.spigotmc.org/resources/backpacks.111152/).

### 1.0.3

- Fixed cooldown setting
- Fixed close event handling
- Added "use-owner-uuid-instead-of-name" configuration setting (somewhat like "cracked support")
- Added "disable-backpack-command" configuration setting
- Fixed "use-permissons" configuration setting
- Cleaned up source code

### 1.0.2

- Fixed several bugs
- Added native spigot support
- Removed "papermc required" check
- Created "/backpack" command
- Created "/backpack" tabcompletion
- Splited config, permissions and messages into separate files
- Added spigotmc update checker (Thanks to https://github.com/JEFF-Media-GbR/Spigot-UpdateChecker)
- Created two new events
    - BackpackOpenEvent
    - BackpackCloseEvent
