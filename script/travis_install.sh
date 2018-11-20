#!/bin/bash

export GRADLE_OPTS=-Xmx1024m

if [[ $TRAVIS_PULL_REQUEST != "false" ]]; then
    echo "Build Pull Request => Branch [$TRAVIS_BRANCH] Pull Request [$TRAVIS_PULL_REQUEST]"
    ./gradlew build
elif [[ $TRAVIS_PULL_REQUEST == "false" && -z $TRAVIS_TAG ]]; then
    echo "Build snapshot => Branch [$TRAVIS_BRANCH]"
    ./gradlew build --stacktrace
elif [[ $TRAVIS_PULL_REQUEST == "false" && ! -z $TRAVIS_TAG ]]; then
    echo "Build release => Branch [$TRAVIS_BRANCH] Tag [$TRAVIS_TAG]"
    ./gradlew install -PreleaseTag=$TRAVIS_TAG -PreleaseMode=release -Pbintray.user=$BINTRAY_USER -Pbintray.apiKey=$BINTRAY_API_KEY --stacktrace
fi
