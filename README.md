# CircleCI demo

Sample of CircleCI parallel workflow.

[![CircleCI](https://circleci.com/gh/varrocen/circleci-demo.svg?style=svg)](https://circleci.com/gh/varrocen/circleci-demo)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=varrocen_circleci-demo&metric=alert_status)](https://sonarcloud.io/dashboard?id=varrocen_circleci-demo)

## Requirements

* JDK 1.8
* Maven
* AWS CLI
* AWS EB CLI
* Docker
* CircleCI Local CLI

## Run

    docker-compose up -d && mvn spring-boot:run

## Beanstalk

Create env and deploy app:

    eb init
    eb create --single --database --database.engine postgres
    eb setenv SPRING_PROFILES_ACTIVE=beanstalk
    
