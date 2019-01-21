## Java URL Shortener

This is an implementation of a microframework that exposes an API to shorten URL links.

There are two main endpoints which are:
```
(GET) /{urlShortened}
```

```
(POST) /
- params:
 [url] -> Received as plaintext
```

----

## Dependencies

This project needs the following to be compiled:

* Gradle (can use the gradle wrapper)
* Docker
* Docker-compose

The rest is downloaded from the build scripts.

## Building the application

To build the application, just run the following commands:

```$xslt
$ gradle clean build
$ docker-compose build
$ docker-compose up
```

Alternatively, there's a `run.sh` script on the main folder that issues those commands for you, so you can simplify to:
```$xslt
sh run.sh
```

## Testing the application

By default, the application runs on the port 1992, you can test against the endpoints using CURL for example:

```$xslt
curl -X POST \
  http://localhost:1992/ \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -d 'https://www.google.com/maps/place/Dom+Bosco,+Belo+Horizonte+-+State+of+Minas+Gerais/@-19.9161693,-44.0052887,16z/data=!3m1!4b1!4m5!3m4!1s0xa696ecd970a721:0xf7bc5ebeb1e7b42b!8m2!3d-19.9135789!4d-43.998373'
```

The server will return a json response, with the following:
```$xslt
{
  "originalUrl" : "https://www.google.com/maps/place/Dom+Bosco,+Belo+Horizonte+-+State+of+Minas+Gerais/@-19.9161693,-44.0052887,16z/data=!3m1!4b1!4m5!3m4!1s0xa696ecd970a721:0xf7bc5ebeb1e7b42b!8m2!3d-19.9135789!4d-43.998373",
  "shortUrl" : "adc7598806e98d12f5aeba5988d1b069"
}
```

The shortUrl string is the address of this URL. You can visit it by doing a GET request (or just use your browser and navigate) to
`http://localhost:1992/adc7598806e98d12f5aeba5988d1b069` and you should be redirected to the original URL. 

## Caching

On receiving a link for the first time, the response could take longer than 100ms, because it saves on a MySQL instance.
Successive calls will be faster, because there's a LRU cache for 150 links. 
