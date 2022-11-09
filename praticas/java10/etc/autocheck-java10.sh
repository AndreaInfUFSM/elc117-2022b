#!/bin/bash

err=0
if find . -type d -name "sharedaccount" -exec false {} +; then   
  echo 'sharedaccount folder not found'
  err=1
fi

#file --mime-type sharedaccount/* | grep "image/" | wc -l
images=`find . -type f -exec file {} \; | grep -i -o -E '^.+: \w+ image'`
imagecount=`echo "$images" | wc -l`
echo "$images"
echo "Found $imagecount images"

if find . -type f -name "README.md" -exec false {} +; then
  echo 'README.md not found'
  err=1
else
  find . -type f -name "README.md" -exec wc -l {} +
fi

if [ $err -eq 1 ]; then
  exit 1
fi
