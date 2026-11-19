## Git workflow

- Create branch for small feature, so you don't push huge changes **git switch -c BRANCH_NAME**
- After merging its recommended to run **git branch -d BRANCH_NAME**, to delete the branch locally. This keeps your local repo clean.

## Merge guide
- Move into **main** branch by running **git switch main**
- Run **git pull** before merging to get all the changes from the repo
- Run **git merge BRANCH_NAME** with the branch you want to merge with main
- Then push all changes to the remote with **git commit -m "Commit Message"** + **git push origin main**