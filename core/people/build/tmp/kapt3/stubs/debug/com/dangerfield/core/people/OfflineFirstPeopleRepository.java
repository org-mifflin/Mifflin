package com.dangerfield.core.people;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000b\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\f"}, d2 = {"Lcom/dangerfield/core/people/OfflineFirstPeopleRepository;", "Lcom/dangerfield/core/people/api/PeopleRepository;", "()V", "getNextPeople", "", "Lcom/dangerfield/core/people/api/Person;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setPersonSeen", "", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "people_debug"})
public final class OfflineFirstPeopleRepository implements com.dangerfield.core.people.api.PeopleRepository {
    
    @javax.inject.Inject()
    public OfflineFirstPeopleRepository() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object getNextPeople(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.dangerfield.core.people.api.Person>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public java.lang.Object setPersonSeen(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
}