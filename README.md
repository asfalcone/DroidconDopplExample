# Overview

This is a trimmed down version of the Droidcon NYC app. The production version had several dependencies on 3rd party services, including our proprietary server. Setup would've taken a lot of effort.
This version does not log in, and does not store anything on the server. As such, the only thing you *might* need to sign up for is a jwplayer trial account to get the video players working.

# Parts

This includes both an Android and iOS native app which allows you to view the Droidcon NYC schedule and select talks you'd like to see. Also a few other minor things.

The UI's are native. Most code below the UI is written in Java and converted to Objective-C with [j2objc](http://j2objc.org/), using [Doppl](http://doppl.co/) tools and frameworks.

Shared code handles storage, networking, and logic (mostly). Libraries include gson, retrofit, and various Android db and threding libraries.

# Setup

Doppl is at a very early stage, so setup may be somewhat brittle, but should work.

## Overview

1. Install Android Studio
2. Install Xcode/Cocoapods
3. Install j2objc runtime (slightly modded version)
4. Run Android
5. Run iOS

## Install Android Studio

Grab the latest stable, currently version 2.2.

## Install Xcode/Cocoapods

Install Xcode and Cocoapods. I would suggest finding and running a simple sample project, to minimize potential issues with the iOS build system.

## Install j2objc runtime

Doppl uses a slightly modified j2objc runtime. This will likely be merged over time to keep the standard j2objc runtime, but for now, grab ours.

[j2objc runtime](http://dopplmaven.s3-website-us-east-1.amazonaws.com/dist.zip)

Extract the zip to a directory (avoid spaces in the path if possible).

Add the following to ~/.bash_profile

export J2OBJC_HOME=[dist dir]

[dist dir] is the absolute path where you installed the j2objc zip

Add the following to local.properties in the project directory

```
sdk.dir=[android sdk dir]
j2objc.home=[dist dir]
baseurl=https://droidcon-server.herokuapp.com/
jwLicenseKey=[get a key from jwplayer.com]
releasePassword=[You make this. Only needed for release builds]
```

Be sure to restart your terminal for the variables to take effect

*Note* The doppl gradle packages are huge. They have compiled object code in them. Downloads will take quite some time.

## Verify build works

### Android

Open the project in Android Studio. Make sure to select the "albany" flavor (this will be merged down to a single flavor soon).
  Run a standard Android build. This should install like any standard android app.

*The hot deploy currently doesn't work, but that has something to do with the code for chrome cast support (not doppl)*.

### iOS

CD into the doppl directory, and run

Run the following

./gradlew lib:j2objcXcode

Note: doppl currently needs gradle 2.8. As a temporary solution, we point 2 different gradle project heads at the same lib dir.

This will update the Podfile, if necessary, and run 'pod install'.

Open the 'ios' folder in your file navigator. Open 'ios.xcworkspace' (not the xcodeproj file).

Pick a simulator and try to run it.

That was a lot of steps, but it should run, and you should see the app at this point.

# Other Stuff

Notes from the original Droidcon README

#################################

# Server

The server isn't open source at this time, but it would be relatively simple to modify the app to
work with local data and seed it with json.  If you'd like to use the server, get in touch (info [at] touchlab.co)

# Code

### Kotlin

Kotlin has been updated to v1!

A large portion of the app is written in [Kotlin](http://kotlinlang.org/).  Android Studio support is pretty good at this point,
although some parts need to be Java.  Specifically anything that runs through annotation processing.

### Squeaky

A fork of ORMLite that is Android specific and uses source generation instead of reflection.  Performance
is significantly improved, and it supports immutable fields as well as views.  Note *quite* open source
yet.  Need to clean things up a bit.  Was going to announce it at the conference, but time ran short.  You can't actually build the app as-is with this, but either wait till the source is up, or just swap out with ORMLite.  Its mostly compatible, after package renames (and some tweaking of a foreign collection).

Also, name isn't final.  See how that goes.

## Open Source Tools

[Picasso](http://square.github.io/picasso/)

[Retrofit](http://square.github.io/retrofit/)

[OKHttp](http://square.github.io/okhttp/)

[EventBus](https://github.com/greenrobot/EventBus)

[SuperToasts](https://github.com/JohnPersano/SuperToasts)

[VectorCompat](https://github.com/wnafee/vector-compat)

[Circle Image View](https://github.com/hdodenhof/CircleImageView)
