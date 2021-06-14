#!/usr/bin/env bash
# run.sh <MainClass> <args...>

args="${@:2}"
export MAVEN_OPTS="-Xmx1G -XX:+UseParallelOldGC -XX:ParallelGCThreads=2 -XX:GCTimeRatio=19" && \
  mvn exec:java -Dexec.classpathScope=compile -Dexec.mainClass="$1" -Dexec.args="$args"
