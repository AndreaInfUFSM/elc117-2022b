#!/bin/bash

err=0


function findandrun {
  if find . -type f -name $1 -exec false {} +; then
    echo $1" not found"
    err=1
  else
    srcfile=`find . -type f -name "$1"`
    srcdir=`dirname $srcfile`
    classfile=`basename $srcfile .java`
    wc -l $srcfile
    javac $srcfile
    cd $srcdir
    java $classfile
  fi
}

findandrun BetterThreadRace.java
findandrun AnotherThreadrace.java

if [ $err -eq 1 ]; then
  exit 1
fi
