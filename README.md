# Mifflin - "World's best dating app"

![Release Version](https://img.shields.io/github/v/release/Elijah-Dangerfield/Mifflin) ![Main CI/CD](https://img.shields.io/github/actions/workflow/status/Elijah-Dangerfield/Mifflin/on_pr.yml)

<img src="https://user-images.githubusercontent.com/45648517/223679189-fc8570b0-9b38-42ce-8876-1d34bc327fd9.png"  width="300" height="300">


## Overview

Mifflin is "**The world's best dating app**". It is designed with these features in mind: 

- **Offline Functionality** - eagerly fetches and caches data while using that local cache as the source of truth and cleaning it up as the user progresses through the data
- **Beautiful Design** - maybe it's stolen from the app that's meant to be deleted, maybe it's Maybelline
- **Remote Configuration** - provides a configuration stream to features, refreshing every 15 mins, allowing the app experience to be updated on the fly
- **Modularization** - got lots of developers? No problem, the modularization cuts down on build time, maintains a separation of concerns, and allows developers to work in parallel
- **Error handling** - poor network connectivity? Odd edge case? No problem, there's a fallback for that
- **Neato burrito app architecture** - courtesy of the [Guide To App Architecture](https://developer.android.com/topic/modularization), this app aims to follow best practices


## Screenshots

![Frame 3](https://user-images.githubusercontent.com/45648517/223679757-65d53f0f-a9f9-4704-bf28-bf620424dcdd.png)

## How to run

To just play with the app you can find apks and aabs attached to the latest [release](https://github.com/Elijah-Dangerfield/Mifflin/releases)
and can sideload them (if you're cool) using `adb install PATH_TO_APK`

Otherwise, you can open the code base in android studio and run from there

```
Note: Before running you will be required to install the git hooks by running `./scripts/intsall-git-hooks.sh`. 
Sorry, can't risk any smelly ugly code getting past my code analysis
```

## Architecture

The architecture of this application aims to follow recommendations outlined in the [Guide To App Architecture](https://developer.android.com/topic/architecture) by: 
- ensuring unidirectional dataflow via [UdfViewModel]([https://github.com/Elijah-Dangerfield/mifflin/](https://github.com/Elijah-Dangerfield/Mifflin/blob/main/core/ui/src/main/java/com.dangerfield.core.ui/UdfViewModel.kt))
- maintaining an immutable state
- maintaining a clear separation of concerns between components
- using lifecycle-aware state collection
- leveraging dependency injection with Hilt

The view level architecture aims to follow a loose MVI structure without bloat code (reducers, side effect handlers, and stores). So really its just MVVM with actions to state UDF. 

## Tech stack
- compose - because I like to learn new things
- retrofit - because I love jake wharton
- coil - image fetching and caching
- coroutines/flow (flowroutines) - concurrency and reactive programming
- datastore - config caching
- room - user data caching
- Timber - lightweight logging
- Github actions - CI/CD
- Hilt - Dependency injection

## Modularization

The modularization followed in this code base aims to encourage low coupling and high cohesion as outlined in the [Guide To App Architecture](https://developer.android.com/topic/modularization)

The code base is separated into `core`, `feature` and `app` modules. The app module acts as the glue, depending on all modules, and builds the user experience. 

Both Feature and Core modules may contain a submodule `api` used to encapsulate its internal logic should another module wish to leverage it. Leveraging an api module will ensure that clients of that api do not need rebuilding if the implementation details change a bit. It also helps create a stronger separation of concerns between the modules. 

     Note: I've added a bunch of empty modules to give a better picture of the design I had in mind at scale. 

### Rules:
- A feature module should never depend on another feature module directly, only to an api of that module. Preffer to have navigation interfaced out and handled in the app module. 
- A feature module may depend on core modules and or core api modules
- A core module may not depend on any modules other than core api modules 


Additionally, I leverage a `build-logic` included build with convention plugins to cut down on build time and make the build process easier to understand and update

## Offline Functionality

Mifflin local data as a source of truth enabling users to continue browsing while offline. We do so by leveraging:
- Room : to store user metadata
- Datastore : to store the app config
- Coil : to prefetch and store user images


## CI/CD

This project includes a basic yet opinionated CI/CD system leveraging Github Actions.
On every PR we check:

- **build** - ensures the app isnt broken
- **style** - static code analysis that keeps that smelly code out
- **test** - runs all tests to make sure things are smoother than a fresh jar of skippy

The workflows for these can be found under `.github/workflows`
To read more about the CI/CD of this project visit the [documentation](https://github.com/Elijah-Dangerfield/mifflin/blob/main/docs/ci.md)

# Bigger Picture

Here are some things I either would've liked to add or that came to mind when thinking about a dating app's functional and technical requirements at scale. 
Some of these are inspired by the book [Mobile Engineering At Scale](https://www.mobileatscale.com/)

### Paging

In this case, our backend is static. But in my mind, I could see us using cursor paging to continually get the next batch of users. We might do this when there are X users left in the queue. 

### Sever Sent Events and Push Notifications

Absolutely out of scope here but we would want to notify users of their matches. In my mind, I picture push notifications for users with the app not in the foreground and then observe Server-Sent Events to display an in-app message for users with the app in the foreground. 

Other options for in-app messaging include polling (short and long) and maybe web sockets. I don't have any experience with SSE's but from what I hear they fit this use case better than polling or web sockets. 

### Analytics

I've made some fake analytics calls to represent this but in my head, some folks are going to want that sweet sweet data. 

### KPI's
I would've liked to consider adding KPI's that inform developers on their work. I think that's an important part of the day-to-day. Seeing the real impact of your work is important. Perhaps some of these: 

  - app start-up time
  - battery usage
  - analytics accuracy
  - code coverage
  - PR landing rate
  - match percentage
  - app deletion lead time

### Localization
This is an underrated concern with any app that scales. There are lots of ways to handle it. I'm fairly uneducated on this topic to be honest and so I've considered it out of scope here. 

### Encrypted network communications 
It looks like the server doesn't have an SSL certificate so I had to allow for clear text traffic. I ended up just making a configuration to only allow it for the one address provided to at least mitigate that risk.
  
### Local Image and Data encryption
We don't want to allow other apps or users with rooted devices to peek into our data too easily. It would be best to encrypt the data we receive locally. 

### Linting and Tooling
Assuming we would have a larger developer team it may be worth considering adding tooling and linting to make it easier for developers to create features and adhere to best practices. 

### Accessibility (a11y)
This a real concern for any app, especially app with a larger profile. I'm not entirely educated on this, I added basic content descriptions in this app but I did not walk through it with a screen reader or anything. 

### Crash Reporting
A no-brainer, just ran out of time (I'm on vacation for a wedding in Casablanca). I would've liked to put in sentry or firebase crashlytics. The only concern with the latter is that we would lose crash reporting for amazon devices IIRC. 


