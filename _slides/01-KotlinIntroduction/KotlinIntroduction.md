## Kotlin introduction

--
### Why Kotlin

--
### Building
* Plain
* Gradle
* Maven

--
### build.gradle 1/2
```groovy
buildscript {
    ext.kotlin_version = '1.3.10'
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
}

apply plugin: 'kotlin'
apply plugin: 'application'
apply plugin: 'idea'
```

--
### build.gradle 2/2
```groovy
repositories {
    mavenCentral()
}

dependencies {
    compile "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    testCompile("org.junit.jupiter:junit-jupiter-api:5.3.2")
    testCompile("org.junit.jupiter:junit-jupiter-params:5.3.2")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:5.3.2")
    testCompile "org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version"
}

test {
    useJUnitPlatform()
}
```

--
## Other options
Kotlin also works in 
- Android Studio
- Visual Studio Code (extension)
- CLI

--
<!-- .slide: data-background="url('images/demo.jpg')" --> 
<!-- .slide: class="lab" -->
## Demo time!
Demo 1. Setting up hello world in gradle

--
<!-- .slide: data-background="url('images/lab2.jpg')" --> 
<!-- .slide: class="lab" -->
## Lab time!
