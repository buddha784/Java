#!/bin/bash
# One-click execution script for Student Result Management System (SRMS)
# Author: BUDDHA S (Reg No: 25BAI11592)

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SRC_DIR="$SCRIPT_DIR/buh-main/student result management"

echo "Compiling Student Result Management System..."
javac "$SRC_DIR"/*.java

if [ $? -eq 0 ]; then
    echo "Compilation successful. Starting application..."
    cd "$SRC_DIR" && java Main
else
    echo "[!] Compilation failed. Please ensure JDK 17+ is installed."
    exit 1
fi
