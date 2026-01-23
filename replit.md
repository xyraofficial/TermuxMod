# Termux App

## Overview

Termux is an Android terminal emulator application that provides a Linux command line environment on mobile devices. The app enables users to run shell commands, access development tools, compile code, and use a wide range of Linux utilities without requiring root access.

The repository contains the core Termux application (user interface and terminal emulation). Package management and installable packages are maintained in a separate repository (termux/termux-packages).

## User Preferences

Preferred communication style: Simple, everyday language.

## System Architecture

### Android Application Structure

**Multi-Module Gradle Project**
- The app follows a modular architecture with a shared library (`termux-shared`) that contains reusable components
- Main app module handles the terminal UI and emulation
- Shared module provides utilities for file operations, logging, shell execution, settings management, and Android-specific helpers

**Core Components**
- **Terminal Emulation**: Based on the Terminal Emulator for Android project (Apache 2.0 licensed)
- **Shell Management**: Custom shell execution and task management (`TermuxTask`, `ShellUtils`)
- **Settings System**: Dual approach using SharedPreferences and properties files (`SharedProperties`, `SharedPreferenceUtils`)
- **Crash Handling**: Built-in crash handler for debugging and error reporting

**Key Architectural Patterns**
- Separation of terminal emulation logic from UI components
- Shared library approach for code reuse across Termux app and plugins
- Properties-based configuration system for user customization

### Plugin Ecosystem

The app supports multiple optional plugin applications:
- Termux:API - Android API access from terminal
- Termux:Boot - Run scripts on device boot
- Termux:Float - Floating terminal window
- Termux:Styling - Terminal appearance customization
- Termux:Tasker - Tasker automation integration
- Termux:Widget - Home screen widget for scripts

Plugins communicate with the main app through Android Intents and shared components from `termux-shared`.

### File System Design

- Custom file system utilities with native JNI dispatcher for low-level operations
- File type detection and management system
- Designed to work within Android's sandboxed storage model

## External Dependencies

### Build & Distribution
- **Gradle**: Android build system
- **JitPack**: Library distribution for Termux shared components
- **GitHub Actions**: CI/CD for builds and unit tests
- **Fastlane**: App store metadata management

### Licensing
- Main application: GPLv3
- Terminal Emulator components: Apache License 2.0
- Various utility classes in termux-shared: MIT License

### Community
- Gitter chat integration
- Discord server for community support