# People

Module with the responsibility of collecting and exposing people other than the current user/meUser. (Who is exposed by the session module)
The idea behind this module is that it would be shared between the match making and conversation modules. To allow
access to the details of a person. 

The api module will handle exposing only what is needed while encapsulating the rest.

The local module will handle the secure storage of person data while the remote module will handle the secure fetching of person data
