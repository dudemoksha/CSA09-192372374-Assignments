git init
git branch -M main
git remote add origin https://github.com/dudemoksha/CSA09-192372374-Assignments.git
for n in 01 02 03 04 05 06 07 08 09 10 11 12; do
  git add Task$n
  git commit -m "Add Task $n Java code"
done
git push -u origin main
