#!/bin/bash
# One-click automated testing script for SRMS
# Author: BUDDHA S (Reg No: 25BAI11592)

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SRC_DIR="$SCRIPT_DIR/buh-main/student result management"

echo "Compiling test suite..."
javac "$SRC_DIR"/*.java

if [ $? -eq 0 ]; then
    echo "Running StudentValidationTest suite..."
    cd "$SRC_DIR" && java StudentValidationTest
else
    echo "[!] Compilation failed."
    exit 1
fi
