# 🚀 Repository Setup Guide

This document explains how to set up and maintain the Daily LeetCode Challenge repository.

## 📋 Prerequisites

- **Node.js** (v14 or higher) - Required for progress tracking scripts
- **Git** - For version control and GitHub integration
- **GitHub Account** - For hosting and GitHub Actions automation

## 🔧 Initial Setup

### 1. Clone Repository
```bash
git clone https://github.com/deekshith-b48/Daily-LeetCode.git
cd Daily-LeetCode
```

### 2. Test Progress Tracker
```bash
# Test the Node.js script
node scripts/progress-tracker.js

# Test with stats save
npm run update
```

### 3. Verify GitHub Actions
The workflow automatically runs when:
- You push to the `main` branch
- Files in `Daily-contest/` or `scripts/` are modified
- Daily at midnight UTC (as backup)

## 📁 Daily Workflow

### Adding New Solutions

1. **Create daily folder:**
   ```bash
   mkdir Daily-contest/oct-X-25
   ```

2. **Add your solution:**
   ```bash
   # Add your Java solution file
   cp solution.java Daily-contest/oct-X-25/problemNumber.java
   
   # Add problem summary (optional but recommended)
   touch Daily-contest/oct-X-25/problemNumber-problem-summary.md
   ```

3. **Commit and push:**
   ```bash
   git add .
   git commit -m "Add solution for day X: Problem #problemNumber"
   git push origin main
   ```

4. **Automatic updates:**
   - GitHub Actions will automatically update `stats.json`
   - README badges will refresh with new data
   - All metrics are calculated automatically

## 🛠️ Manual Operations

### Update Stats Manually
```bash
# Run progress tracker
npm run update

# Or use the shell script
bash update-progress.sh

# View current stats only
npm run stats
```

### Force Workflow Run
You can manually trigger the GitHub Actions workflow:
1. Go to your repository on GitHub
2. Click "Actions" tab
3. Select "Auto Update LeetCode Stats" workflow
4. Click "Run workflow"

## 📊 Understanding the Stats

The `stats.json` file contains:
- `totalProblems`: Number of `.java` files found
- `currentDay`: Days since October 1, 2025
- `successRate`: (Problems / Current Day) * 100
- `daysRemaining`: Days left in 90-day challenge
- `progressPercentage`: (Current Day / 90) * 100

## 🎯 File Structure

```
Daily-LeetCode/
├── Daily-contest/              # Daily solutions
│   ├── oct-1-25/              # Daily folders
│   │   ├── problem.java       # Solution code
│   │   └── problem-summary.md # Problem analysis
├── .github/workflows/          # GitHub Actions
│   └── update-stats.yml       # Auto-update workflow
├── scripts/                    # Progress tracking
│   └── progress-tracker.js    # Main tracking script
├── stats.json                 # Auto-generated stats
├── package.json               # Node.js configuration
├── update-progress.sh         # Manual update script
└── README.md                  # Main documentation
```

## 🚨 Troubleshooting

### GitHub Actions Not Running
1. Check if workflow file is in `.github/workflows/`
2. Verify workflow syntax in GitHub Actions tab
3. Ensure repository has GitHub Actions enabled
4. Check if GITHUB_TOKEN has necessary permissions

### Stats Not Updating
1. Verify Node.js is available: `node --version`
2. Test script locally: `npm run update`
3. Check for JavaScript errors in workflow logs
4. Ensure `.java` files are in correct folder structure

### Badges Not Refreshing
1. Badges update from raw GitHub content
2. May take a few minutes to refresh
3. Try hard refresh (Ctrl+F5) in browser
4. Verify `stats.json` has correct data

## 📝 Customization

### Change Challenge Duration
Edit `scripts/progress-tracker.js`:
```javascript
this.targetDays = 90; // Change to desired number
```

### Modify Start Date
Edit `scripts/progress-tracker.js`:
```javascript
this.startDate = new Date('2025-10-01'); // Change date
```

### Add More File Types
Edit the counting logic in `progress-tracker.js`:
```javascript
// Count different file extensions
const codeFiles = files.filter(file => 
  file.endsWith('.java') || 
  file.endsWith('.py') || 
  file.endsWith('.cpp')
);
```

## 🎉 Success!

Once everything is set up:
- ✅ Commit your daily solutions
- ✅ GitHub Actions handles all tracking
- ✅ README badges update automatically
- ✅ Focus on coding, not statistics!

Happy coding! 🚀