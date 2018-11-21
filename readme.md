# Traffic sniffing extension for WebDriver

![Travis (.org)](https://img.shields.io/travis/jozsefkoza/webdriver-traffic-sniffer.svg?logo=travis)

![Bintray - api](https://img.shields.io/bintray/v/jozsefkoza/maven/traffic-sniffer-api.svg?label=api)
![Bintray - browsermob](https://img.shields.io/bintray/v/jozsefkoza/maven/traffic-sniffer-browsermob.svg?label=browsermob)

Utility to sniff network traffic initiated by WebDriver.

## Getting started

To integrate it in your project, you have to add the follow repository to your build

##### Using maven
```xml
<repository>
  <id>bintray-jozsefkoza</id>
  <name>Bintray - jozsefkoza</name>
  <url>https://dl.bintray.com/jozsefkoza/maven</url>
</repository>
```

##### Using gradle
```groovy
repositories {
    maven {
        url "https://dl.bintray.com/jozsefkoza/maven"
    }
}
```


Once the repository is added, you can include any of the below modules in your project. 
  

## Modules

All modules - except `api` - is a standalone distribution, so you just have to include the one which is specific for your project.  


### api

Provides the API for all specific traffic sniffer implementations. Captured traffic can be accessed through `TrafficSource` as a Stream of `TrafficElement`s.  
Can be used as a base for custom traffic sources.

Include 
```xml
<dependency>
  <groupId>com.github.jozsefkoza.webdriver</groupId>
  <artifactId>traffic-sniffer-api</artifactId>
  <version>0.1.0</version>
</dependency>
```
or
```groovy
dependencies {
    api 'com.github.jozsefkoza.webdriver:traffic-sniffer-api:0.1.0'
}
```


### browsemob

Implements [browsermob-proxy](https://github.com/lightbody/browsermob-proxy) specific `TrafficSource`s.  
**Note** that currently only embedded version of the proxy is supported!

This package contains two specific sources: `BrowserMobBasedTrafficSource` and `PollingTrafficSource`.  
The former one is a simple implementation using `net.lightbody.bmp.BrowserMobProxy` to return the traffic flowed trough it until the invocation of `TrafficSource.traffic()`.  
The latter one captures the traffic by another traffic source in regular intervals for elements, and returns once no new elements could be captured. It is useful in tests, when you require to have a deterministic sized collection of traffic elements (e.g. when you have to verify if a request is explicitly triggered by a specific event). 

Include 
```xml
<dependency>
  <groupId>com.github.jozsefkoza.webdriver</groupId>
  <artifactId>traffic-sniffer-browsermob</artifactId>
  <version>0.1.0</version>
</dependency>
```
or
```groovy
dependencies {
    implementation 'com.github.jozsefkoza.webdriver:traffic-sniffer-browsermob:0.1.0'
}
```
