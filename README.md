# Mifflin


<img src="https://user-images.githubusercontent.com/45648517/223679189-fc8570b0-9b38-42ce-8876-1d34bc327fd9.png"  width="300" height="300">

Welcome to Mifflin. 
Not a dating app, a loving app. 
The app designed to be removed. 

# Screenshots

![Frame 3](https://user-images.githubusercontent.com/45648517/223679757-65d53f0f-a9f9-4704-bf28-bf620424dcdd.png)

# How to run

To just play with the app you can find apks and aabs attached to the latest [release](https://github.com/Elijah-Dangerfield/Mifflin/releases)
and can sideload them using `adb install PATH_TO_APK`

Otherwise you can open the code base in android studio and run from there
Note: Before running you will be required to install the git hooks by running `./scripts/intsall-git-hooks.sh`


# Architecture

The architecture of this application aims to follow reccomendations outlined in the [Guide To App Architecture](https://developer.android.com/topic/architecture) by: 
- eunsuring unidirectional dataflow via [UdfViewModel](https://github.com/Elijah-Dangerfield/mifflin/)
- maintaining immutable state
- maintaining a clear sepearation of concerns between components
- using lifecycle aware state collection
- leveraging dependency injection with Hilt


# Modularization

The modularization followed in this code base aims to encourage low coupling and high cohesion as outlined in the [Guide To App Architecture](https://developer.android.com/topic/modularization)

The code base is separated into `core`, `feature` and `app` modules. The app module acts as the glue, depending on all modules, and building the user experience. 

Both Feature and Core modules may contain a submodule `api` used to encapsulate its intneral logic should another module wish to leverage it. Leveraging an api module will ensure that clients of that api do not need rebuilding if the implementation details change a bit. It also helps create a stronger seperation of concerns between the modules. 

Rules:
- A feature module should never depend on another feature module directly, only to an api of that module. Preffer to have navigaiton interfaced out and handled in the app module. 
- A feature module may depend on core modules and or core api modules
- A core module may not depend on any modules other than core api modules 
-

Additionally I leverage a `build-logic` included build with convention plugins to cut down on build time and make the build process easier to understand and update

# Offline Functionality

Mifflin local data as a source of truth enabling users to continue browsing while offline. We do so by leveraging:
- Room : to store user meta data
- Datastore : to store the app config
- Coil : to prefetch and store user images


# CI/CD

This project includes a basic yet opinionated CI/CD system leveraging Github Actions.
The workflows for these can be found under `.github/workflows`
To read more about the CI/CD of this project visit the [documentation](https://github.com/Elijah-Dangerfield/mifflin/blob/main/docs/ci.md)

# Bigger Picture

Here are some things I either wouldve liked to add or that came to mind when thinking about a dating apps functional and technical requirements at scale. 
Some of these are ispired by the book [Mobile Engineering At Scale](https://www.mobileatscale.com/)

### Localization
This is an underrtated concern with any app that scales. There are lots of ways to handle it. I've considered it out of scope here. 

### Encrypted network communications 
It looks like the server doesnt have an SSL certificate so I had to allow for clear text traffic. I ended up just making a configuration to only allow it for the one addess provided to at least midigate that risk.
  
### Local Image and Data encryption
We dont want to allow other apps or users with rooted devices to peek into our data too easily. It would be best to encrypt the data we receive locally. 

### Linting and Tooling
Assuming we would have a larger developer team it may be worth considering adding tooling and linting to make it easier for developers to create feature and adhere to best practices. 




