# Termux App

## Overview

Termux is an Android terminal emulator application that provides a Linux command line environment on mobile devices. The app allows users to run shells (Bash, Zsh), use command-line utilities, access servers via SSH, compile code, and run text-based applications. It consists of a core terminal emulator app with several optional plugin apps (API, Boot, Float, Styling, Tasker, Widget).

## User Preferences

Preferred communication style: Simple, everyday language.

## System Architecture

### Project Structure
- **Native Android Application**: Built using Java/Kotlin for Android OS
- **Multi-module Gradle Project**: Core app with a shared library module (`termux-shared`)
- **Plugin Architecture**: Core app designed to work with optional companion apps that extend functionality

### Core Components

1. **Terminal Emulator**
   - Provides terminal emulation functionality on Android
   - Based on code from "Terminal Emulator for Android" (Apache 2.0 licensed)
   - Handles user interface and terminal display

2. **termux-shared Library**
   - Shared utility library used across Termux apps
   - Contains common functionality: crash handling, file utilities, logging, shell utilities, settings management, notifications
   - Published to JitPack for use by plugin apps

3. **Base System**
   - Configures a minimal Linux environment on first start
   - Includes GNU Bash, Coreutils, Findutils, and core utilities out of the box
   - Package management system for installing additional packages (1000+ available)

### Plugin Apps
Optional companion apps that extend Termux functionality:
- **Termux:API** - Access Android device APIs from command line
- **Termux:Boot** - Run scripts on device boot
- **Termux:Float** - Floating terminal window
- **Termux:Styling** - Customize appearance
- **Termux:Tasker** - Tasker integration
- **Termux:Widget** - Home screen widgets

### Licensing
- Main app: GPLv3 only
- Terminal emulator code: Apache License 2.0
- Various shared library components: MIT License

## External Dependencies

### Build & Distribution
- **Gradle**: Android build system
- **JitPack**: Library distribution for termux-shared (`jitpack.io/#termux/termux-app`)
- **GitHub Actions**: CI/CD for builds and unit tests
- **Fastlane**: Android app metadata management for store listings

### Package Repository
- External package repository (`termux/termux-packages`) provides 1000+ installable packages
- Separate from the app repository

### Community Platforms
- Gitter chat integration
- Discord server for community support