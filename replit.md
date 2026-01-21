# Termux App

## Overview

Termux is an Android terminal emulator application that provides a Linux command line environment on mobile devices. The app enables users to run a full Linux environment including shells (Bash, Zsh), text editors (nano, vim), development tools (clang, Python, Git), and over 1000 installable packages. This repository contains the core app (UI and terminal emulation), while packages are maintained separately in the termux-packages repository.

The project consists of the main Termux app and several optional plugin apps:
- Termux:API - Access Android device features
- Termux:Boot - Run scripts on device boot
- Termux:Float - Floating terminal window
- Termux:Styling - Customize terminal appearance
- Termux:Tasker - Tasker integration
- Termux:Widget - Home screen widgets

## User Preferences

Preferred communication style: Simple, everyday language.

## System Architecture

### Android Application Structure

**Problem:** Need a full-featured terminal emulator on Android with Linux environment support.

**Solution:** Native Android application built with Gradle, using a modular architecture with a shared library (`termux-shared`) for common functionality.

**Key Components:**
- **Terminal Emulation:** Based on Terminal Emulator for Android (Apache 2.0 licensed) for core terminal functionality
- **Shared Library (`termux-shared`):** Contains reusable components including:
  - Constants and configuration
  - Crash handling
  - File system utilities
  - Shell execution and environment management
  - Settings/preferences management
  - UI utilities (keyboard, views, dialogs)
  - Logging infrastructure
  - Notification handling

### Plugin Architecture

**Problem:** Need extensibility without bloating the core app.

**Solution:** Separate plugin apps that integrate with the main Termux app through Android's intent system, allowing optional features to be installed independently.

### Build System

**Problem:** Need reproducible builds for Android with multiple modules.

**Solution:** Standard Gradle build system with GitHub Actions for CI/CD (build and unit tests). Libraries are published via JitPack.

## External Dependencies

### Third-Party Code
- **Terminal Emulator for Android** - Core terminal emulation (Apache 2.0 license)

### Distribution
- **JitPack** - Library distribution for the termux-app modules

### Package Management
- Termux packages are managed through a separate repository (termux-packages) and installed within the app environment

### Licensing
- Main app: GPLv3 only
- termux-shared library: GPLv3 with MIT exceptions for specific utility files