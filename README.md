# 📅 Daily LeetCode Challenge (Oct – Dec 2025)  

<p align="center">
  <img src="https://media.giphy.com/media/f9j3eFQZcMyt2v7zVt/giphy.gif" width="250" alt="Coding Animation"/>
</p>

Welcome to **Daily-LeetCode** 🚀  
This repository documents my journey of solving **LeetCode problems daily from October 1st to December 31st, 2025**.  
The focus is **discipline, algorithms, and consistency** 💪✨  

---

## 🧑‍💻 About This Repository  

✔️ **Daily Commitment:** At least **1 problem/day**  
✔️ **Daily Quests:** Solve LeetCode’s official daily challenge  
✔️ **Extra Practice:** Contests & topic drills  
✔️ **Languages:** 🐍 Python (main), ☕ Java, C++ if needed  
✔️ **Duration:** **Oct 1st → Dec 31st, 2025 (90 Days)** ⏳  

---

## 📊 Streak & Progress Tracker  

<p align="center">
  <img src="https://media.giphy.com/media/QssGEmpkyEOhBCb7e1/giphy.gif" width="200" alt="Progress"/>
</p>

### 🔥 Auto-updated Streak Heatmap  

<p align="center">
  <img src="https://github-readme-streak-stats.herokuapp.com?user=deekshith-b48&theme=radical&date_format=j%20M%5B%20Y%5D" alt="GitHub Streak"/>
</p>

> ✅ This streak calendar **auto-updates daily** using my GitHub commits.  
> 🟢 No need to manually edit progress logs.  

---

## 🚦 How to Use  

🔹 **Browse folders by date** → solutions organized daily.  
🔹 **Problem descriptions** inside file comments.  
🔹 **Run solutions:**  
```bash
python <problem_name>.py
```

🔹 **Tip:** Try solving first before checking my solutions.

---

## 🎯 Goals & Motivation

> *"Small daily wins lead to massive results."*

🌟 **Challenge Goals (90 Days):**

* ✅ Build **Oct–Dec streak** without breaks.
* ✅ Strengthen **Data Structures & Algorithms** knowledge.
* ✅ Stay **interview-ready** through consistency.
* ✅ End 2025 with a **completed challenge milestone**.

🔥 **Motivation Boosters:**

* 🏆 Day 30 → 1-Month Streak 🎉
* 🔥 Day 60 → 2-Month Streak 💯
* 🚀 Day 90 → Completed 2025 Challenge 🌟

> "Success is the sum of small efforts, repeated day in and day out."  
> "Consistency is the key to mastery. Every day is a step closer to your goal."  

Remember:  
**Progress, not perfection. Your future self will thank you! 🚀**

---

## 📌 Repo Layout

```
📦 Daily-LeetCode-Challenge
 ┣ 📂 2025
 ┃ ┣ 📂 October
 ┃ ┣ 📂 November
 ┃ ┗ 📂 December
 ┣ 📂 extras (topic-based solutions)
 ┣ 📜 README.md
 ┗ 📜 progress_log.md (optional)
```

---

## 📂 Auto-update Setup

The streak calendar above uses [**GitHub Readme Streak Stats**](https://github.com/denvercoder1/github-readme-streak-stats).
If you want **daily auto-updates**, add this to your repo’s GitHub Actions workflow:

**.github/workflows/update-readme.yml**

```yaml
name: Update README Streak

on:
  schedule:
    - cron: "0 0 * * *"   # Runs every day at midnight UTC
  workflow_dispatch:

jobs:
  update-readme:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout Repo
        uses: actions/checkout@v3

      - name: Update README.md
        run: |
          git config --global user.name "github-actions"
          git config --global user.email "actions@github.com"
          git add README.md
          git commit -m "♻️ Auto-update streak heatmap"
          git push
```

This ensures the streak graphic stays **fresh daily** without manual edits.

---

## 📬 Contact

* GitHub: [deekshith-b48](https://github.com/deekshith-b48)
* LinkedIn: *(Add profile link)*

---

## ⭐ Support

If this inspires you, please **⭐ Star** the repo & **👨‍💻 Follow** to stay updated!

<p align="center">
  <img src="https://media.giphy.com/media/LmNwrBhejkK9EFP504/giphy.gif" width="350" alt="Keep Coding"/>
</p>
