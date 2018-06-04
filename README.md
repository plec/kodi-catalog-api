# kodi-catalog-api

[![Build Status](https://travis-ci.org/plec/kodi-catalog-api.svg?branch=master)](https://travis-ci.org/plec/kodi-catalog-api)


## Execute from docker
### Build the project
 
```console
mvn package
```
### Build docker image
 
```console
sudo docker build -t kodi-api-java .
```
### Run
 ```console
 sudo docker run -p 8280:8080 kodi-api-java
 ```
 
### See the result
in your browser : http://localhost:8280/api/