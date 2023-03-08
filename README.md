# Mifflin - "Worlds best dating app"

![](https://img.shields.io/github/v/release/Elijah-Dangerfield/Mifflin)

<img src="https://user-images.githubusercontent.com/45648517/223679189-fc8570b0-9b38-42ce-8876-1d34bc327fd9.png"  width="300" height="300">


## Screenshots

![Frame 3](https://user-images.githubusercontent.com/45648517/223679757-65d53f0f-a9f9-4704-bf28-bf620424dcdd.png)

## How to run

To just play with the app you can find apks and aabs attached to the latest [release](https://github.com/Elijah-Dangerfield/Mifflin/releases)
and can sideload them (if youre cool) using `adb install PATH_TO_APK`

Otherwise you can open the code base in android studio and run from there

```
Note: Before running you will be required to install the git hooks by running `./scripts/intsall-git-hooks.sh`. 
Sorry, can't risk any smelly ugly code getting past my code analysis
```

## Architecture

The architecture of this application aims to follow reccomendations outlined in the [Guide To App Architecture](https://developer.android.com/topic/architecture) by: 
- eunsuring unidirectional dataflow via [UdfViewModel]([https://github.com/Elijah-Dangerfield/mifflin/](https://github.com/Elijah-Dangerfield/Mifflin/blob/main/core/ui/src/main/java/com.dangerfield.core.ui/UdfViewModel.kt))
- maintaining immutable state
- maintaining a clear sepearation of concerns between components
- using lifecycle aware state collection
- leveraging dependency injection with Hilt


## Modularization

The modularization followed in this code base aims to encourage low coupling and high cohesion as outlined in the [Guide To App Architecture](https://developer.android.com/topic/modularization)

The code base is separated into `core`, `feature` and `app` modules. The app module acts as the glue, depending on all modules, and building the user experience. 

Both Feature and Core modules may contain a submodule `api` used to encapsulate its intneral logic should another module wish to leverage it. Leveraging an api module will ensure that clients of that api do not need rebuilding if the implementation details change a bit. It also helps create a stronger seperation of concerns between the modules. 

     Note: I've added a bunch of empty modules to give a better picture of the design I had in mind at scale. 

### Rules:
- A feature module should never depend on another feature module directly, only to an api of that module. Preffer to have navigaiton interfaced out and handled in the app module. 
- A feature module may depend on core modules and or core api modules
- A core module may not depend on any modules other than core api modules 


Additionally I leverage a `build-logic` included build with convention plugins to cut down on build time and make the build process easier to understand and update

## Offline Functionality

Mifflin local data as a source of truth enabling users to continue browsing while offline. We do so by leveraging:
- Room : to store user meta data
- Datastore : to store the app config
- Coil : to prefetch and store user images


## CI/CD

This project includes a basic yet opinionated CI/CD system leveraging Github Actions.
The workflows for these can be found under `.github/workflows`
To read more about the CI/CD of this project visit the [documentation](https://github.com/Elijah-Dangerfield/mifflin/blob/main/docs/ci.md)

# Bigger Picture

Here are some things I either wouldve liked to add or that came to mind when thinking about a dating apps functional and technical requirements at scale. 
Some of these are inspired by the book [Mobile Engineering At Scale](https://www.mobileatscale.com/)

### Paging

In this case our backend is static. But in my mind I could see us using cursor paging to continually get the next batch of users. We might do this when there are X users left in the queue. 

### Sever Sent Events and Push Notifications

Absolutely out of scope here but I imagine we would want to notify users of their matches. In my mind I picture push notifications for users with the app not in the for ground and then observing Sever Sent Events to display an in-app notification for users with the app in the foreground. 

Other options for in app messaging include polling (short and long) and maybe web sockets. I don't have any expereince with SSE's but from what I here they fit this use case better than polling for web sockets. 

### Analytics

I've made some fake analytics calls to represent this but in my head some folks are going to want that sweet sweet data. 

### KPI's
I would've liked to consider adding KPI's that inform developers on their work. I think that's an important part of the day to day. Seeing the real impact of your work is important. Perhaps some of these: 

  - app start up time
  - battery usage
  - analytics accuracy
  - code coverage
  - PR landing rate
  - match percentage
  - app deletion lead time

### Localization
This is an underrtated concern with any app that scales. There are lots of ways to handle it. I've considered it out of scope here. 

### Encrypted network communications 
It looks like the server doesnt have an SSL certificate so I had to allow for clear text traffic. I ended up just making a configuration to only allow it for the one addess provided to at least midigate that risk.
  
### Local Image and Data encryption
We dont want to allow other apps or users with rooted devices to peek into our data too easily. It would be best to encrypt the data we receive locally. 

### Linting and Tooling
Assuming we would have a larger developer team it may be worth considering adding tooling and linting to make it easier for developers to create feature and adhere to best practices. 

