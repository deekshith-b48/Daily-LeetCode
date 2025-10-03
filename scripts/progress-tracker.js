#!/usr/bin/env node

/**
 * 📊 LeetCode Progress Counter
 * Automatically counts solved problems and tracks progress
 */

const fs = require('fs');
const path = require('path');

class LeetCodeTracker {
    constructor() {
        this.contestDir = 'Daily-contest';
        this.startDate = new Date('2025-10-01');
        this.targetDays = 90;
    }

    /**
     * Count all solved problems (.java files)
     */
    countProblems() {
        let problemCount = 0;
        let folderCount = 0;
        
        try {
            if (!fs.existsSync(this.contestDir)) {
                console.log(`📁 Creating ${this.contestDir} directory...`);
                fs.mkdirSync(this.contestDir);
                return { problems: 0, folders: 0 };
            }

            const folders = fs.readdirSync(this.contestDir);
            
            folders.forEach(folder => {
                const folderPath = path.join(this.contestDir, folder);
                
                if (fs.statSync(folderPath).isDirectory()) {
                    folderCount++;
                    const files = fs.readdirSync(folderPath);
                    
                    // Count .java files as solved problems
                    const javaFiles = files.filter(file => file.endsWith('.java'));
                    problemCount += javaFiles.length;
                    
                    console.log(`📂 ${folder}: ${javaFiles.length} problems`);
                }
            });
            
        } catch (error) {
            console.error('❌ Error counting problems:', error.message);
            return { problems: 0, folders: 0 };
        }
        
        return { problems: problemCount, folders: folderCount };
    }

    /**
     * Calculate current challenge day
     */
    getCurrentDay() {
        const today = new Date();
        const diffTime = today.getTime() - this.startDate.getTime();
        const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
        
        // Ensure we stay within challenge bounds
        return Math.max(1, Math.min(diffDays, this.targetDays));
    }

    /**
     * Generate comprehensive stats
     */
    generateStats() {
        const { problems, folders } = this.countProblems();
        const currentDay = this.getCurrentDay();
        const successRate = currentDay > 0 ? Math.round((problems / currentDay) * 100) : 0;
        const daysRemaining = Math.max(0, this.targetDays - currentDay);
        const averageProblemsPerDay = currentDay > 0 ? (problems / currentDay).toFixed(2) : 0;

        const stats = {
            totalProblems: problems,
            foldersCreated: folders,
            currentDay: currentDay,
            successRate: successRate,
            challengeStartDate: this.startDate.toISOString().split('T')[0],
            lastUpdated: new Date().toISOString(),
            daysRemaining: daysRemaining,
            targetProblems: this.targetDays,
            averageProblemsPerDay: parseFloat(averageProblemsPerDay),
            progressPercentage: Math.round((currentDay / this.targetDays) * 100),
            completionStatus: currentDay >= this.targetDays ? 'Completed' : 'In Progress'
        };

        return stats;
    }

    /**
     * Save stats to JSON file
     */
    saveStats() {
        const stats = this.generateStats();
        
        try {
            fs.writeFileSync('stats.json', JSON.stringify(stats, null, 2));
            console.log('✅ Stats saved to stats.json');
            return stats;
        } catch (error) {
            console.error('❌ Error saving stats:', error.message);
            return null;
        }
    }

    /**
     * Display progress summary
     */
    displayProgress() {
        const stats = this.generateStats();
        
        console.log('\n📊 === LeetCode Challenge Progress === 📊');
        console.log(`🗓️  Challenge Day: ${stats.currentDay}/${this.targetDays}`);
        console.log(`✅ Problems Solved: ${stats.totalProblems}`);
        console.log(`📁 Days Documented: ${stats.foldersCreated}`);
        console.log(`📈 Success Rate: ${stats.successRate}%`);
        console.log(`⚡ Average Problems/Day: ${stats.averageProblemsPerDay}`);
        console.log(`⏰ Days Remaining: ${stats.daysRemaining}`);
        console.log(`🎯 Progress: ${stats.progressPercentage}% Complete`);
        console.log(`📊 Status: ${stats.completionStatus}`);
        
        // Progress bar
        const progressBar = '█'.repeat(Math.floor(stats.progressPercentage / 5)) + 
                           '░'.repeat(20 - Math.floor(stats.progressPercentage / 5));
        console.log(`📊 [${progressBar}] ${stats.progressPercentage}%`);
        
        console.log('\n🔥 Keep up the great work! 🔥\n');
        
        return stats;
    }
}

// Main execution
if (require.main === module) {
    const tracker = new LeetCodeTracker();
    
    // Check command line arguments
    const args = process.argv.slice(2);
    
    if (args.includes('--save') || args.includes('-s')) {
        tracker.saveStats();
    }
    
    if (args.includes('--quiet') || args.includes('-q')) {
        // Just generate stats without display
        const stats = tracker.generateStats();
        console.log(JSON.stringify(stats, null, 2));
    } else {
        // Default: display progress
        tracker.displayProgress();
    }
}

module.exports = LeetCodeTracker;