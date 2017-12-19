# 1.[word-frequency](https://leetcode.com/problems/word-frequency/description/)
Write a bash script to calculate the frequency of each word in a text file words.txt.

For simplicity sake, you may assume:

words.txt contains only lowercase characters and space ' ' characters.
Each word must consist of lowercase characters only.
Words are separated by one or more whitespace characters.
For example, assume that words.txt has the following content:
```
the day is sunny the the
the sunny is is
```
Your script should output the following, sorted by descending frequency:
```
the 4
is 3
sunny 2
day 1
```
Note:
Don't worry about handling ties, it is guaranteed that each word's frequency count is unique.

code 1:

tr -s: truncate the string with target string, but only remaining one instance (e.g. multiple whitespaces)

sort: To make the same string successive so that uniq could count the same string fully and correctly.

uniq -c: uniq is used to filter out the repeated lines which are successive, -c means counting

sort -r: -r means sorting in descending order

awk '{ print $2, $1 }': To format the output
```shell
cat words.txt | tr -s ' ' '\n' | sort | uniq -c | sort -r | awk '{ print $2, $1 }'

[jimo@jimo-pc leetcode-shell]$ cat words.txt 
the day is sunny the the
the sunny is is
[jimo@jimo-pc leetcode-shell]$ cat words.txt | tr -s ' ' '\n'
the
day
is
sunny
the
the
the
sunny
is
is
[jimo@jimo-pc leetcode-shell]$ cat words.txt | tr -s ' ' '\n' | sort 
day
is
is
is
sunny
sunny
the
the
the
the
[jimo@jimo-pc leetcode-shell]$ cat words.txt | tr -s ' ' '\n' | sort | uniq -c
      1 day
      3 is
      2 sunny
      4 the
[jimo@jimo-pc leetcode-shell]$ cat words.txt | tr -s ' ' '\n' | sort | uniq -c | sort -r
      4 the
      3 is
      2 sunny
      1 day
[jimo@jimo-pc leetcode-shell]$ cat words.txt | tr -s ' ' '\n' | sort | uniq -c | sort -r | awk '{print $2, $1}'
the 4
is 3
sunny 2
day 1

```
code2:
```shell
awk '\
{ for (i=1; i<=NF; i++) { ++D[$i]; } }\
END { for (i in D) { print i, D[i] } }\
' words.txt | sort -nr -k 2
```
# 2.[tenth-line](https://leetcode.com/problems/tenth-line/description/)
How would you print just the 10th line of a file?

For example, assume that file.txt has the following content:
```shell
Line 1
Line 2
...
Line 10
```
Your script should output the tenth line, which is:
```shell
Line 10
```
code:
```shell
# Solution 1
cnt=0
while read line && [ $cnt -le 10 ]; do
  let 'cnt = cnt + 1'
  if [ $cnt -eq 10 ]; then
    echo $line
    exit 0
  fi
done < file.txt

# Solution 2
awk 'FNR == 10 {print }'  file.txt
# OR
awk 'NR == 10' file.txt

# Solution 3
sed -n 10p file.txt

# Solution 4
tail -n+10 file.txt|head -1
```
# 3.[valid-phone-numbers](https://leetcode.com/problems/valid-phone-numbers/description/)
Given a text file file.txt that contains list of phone numbers (one per line), write a one liner bash script to print all valid phone numbers.

You may assume that a valid phone number must appear in one of the following two formats: (xxx) xxx-xxxx or xxx-xxx-xxxx. (x means a digit)

You may also assume each line in the text file must not contain leading or trailing white spaces.

For example, assume that file.txt has the following content:
```shell
987-123-4567
123 456 7890
(123) 456-7890
```
Your script should output the following valid phone numbers:
```shell
987-123-4567
(123) 456-7890
```
code:
```shell
#Using grep:
grep -P '^(\d{3}-|\(\d{3}\) )\d{3}-\d{4}$' file.txt
#Using sed:
sed -n -r '/^([0-9]{3}-|\([0-9]{3}\) )[0-9]{3}-[0-9]{4}$/p' file.txt
#Using awk:
awk '/^([0-9]{3}-|\([0-9]{3}\) )[0-9]{3}-[0-9]{4}$/' file.txt
```
# 4.[transpose-file](https://leetcode.com/problems/transpose-file/description/)
Given a text file file.txt, transpose its content.

You may assume that each row has the same number of columns and each field is separated by the ' ' character.

For example, if file.txt has the following content:
```
name age
alice 21
ryan 30
```
Output the following:
```
name alice ryan
age 21 30
```
