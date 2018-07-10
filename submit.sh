#!/bin/bash

msg="$*"
if [ -z "$msg" ]; then
    msg=$(git status --short)
fi

git status
git add .
git commit -m "$msg"
git push
