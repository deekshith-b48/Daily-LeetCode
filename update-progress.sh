#!/bin/bash

# 📊 LeetCode Progress Tracker Runner
# Run this script anytime to update your progress stats

echo "🚀 LeetCode Progress Tracker"
echo "=============================="

# Check if Node.js is available
if ! command -v node &> /dev/null; then
    echo "❌ Node.js not found. Please install Node.js to run the tracker."
    exit 1
fi

# Run the progress tracker
echo "📊 Calculating progress..."
node scripts/progress-tracker.js --save

echo ""
echo "✅ Stats updated! Your README badges will refresh automatically."
echo "🔥 Keep coding and stay consistent!"

# Optional: Show current stats
echo ""
echo "📈 Current Progress:"
if [ -f "stats.json" ]; then
    echo "$(cat stats.json | grep -E '(totalProblems|currentDay|successRate)' | sed 's/[",]//g' | sed 's/:/: /')"
fi