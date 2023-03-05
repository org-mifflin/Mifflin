package com.dangerfield.features.matchmaker;

import java.lang.System;

@dagger.hilt.android.lifecycle.HiltViewModel()
@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0007\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0003#$%B\u0017\b\u0007\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0011J\u0006\u0010\u0018\u001a\u00020\u0016J\u0006\u0010\u0019\u001a\u00020\u0016J\u001c\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00020\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00030\u001bH\u0014J#\u0010\u001d\u001a\u00020\u0016*\b\u0012\u0004\u0012\u00020\u00020\u001e2\u0006\u0010\u0017\u001a\u00020\u0011H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u001fJ\u001b\u0010 \u001a\u00020\u0016*\b\u0012\u0004\u0012\u00020\u00020\u001eH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!J\u001b\u0010\"\u001a\u00020\u0016*\b\u0012\u0004\u0012\u00020\u00020\u001eH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u0002X\u0094\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006&"}, d2 = {"Lcom/dangerfield/features/matchmaker/MatchMakerViewModel;", "Lcom/dangerfield/core/ui/UdfViewModel;", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$State;", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$Action;", "peopleRepository", "Lcom/dangerfield/core/people/api/PeopleRepository;", "profileConfig", "Lcom/dangerfield/features/matchmaker/ProfileConfig;", "(Lcom/dangerfield/core/people/api/PeopleRepository;Lcom/dangerfield/features/matchmaker/ProfileConfig;)V", "initialAction", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$Action$LoadPeople;", "getInitialAction", "()Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$Action$LoadPeople;", "initialState", "getInitialState", "()Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$State;", "loadPeopleTries", "", "potentialMatches", "Ljava/util/Queue;", "Lcom/dangerfield/core/people/api/Person;", "loadNextPerson", "", "previousPersonId", "loadPeople", "onErrorHandled", "transformActionFlow", "Lkotlinx/coroutines/flow/Flow;", "actionFlow", "handleLoadNextPerson", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "handleLoadPeople", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "pollPersonQueue", "Action", "PeopleStatus", "State", "matchmaker_debug"})
public final class MatchMakerViewModel extends com.dangerfield.core.ui.UdfViewModel<com.dangerfield.features.matchmaker.MatchMakerViewModel.State, com.dangerfield.features.matchmaker.MatchMakerViewModel.Action> {
    private final com.dangerfield.core.people.api.PeopleRepository peopleRepository = null;
    private final java.util.Queue<com.dangerfield.core.people.api.Person> potentialMatches = null;
    @org.jetbrains.annotations.NotNull()
    private final com.dangerfield.features.matchmaker.MatchMakerViewModel.State initialState = null;
    @org.jetbrains.annotations.NotNull()
    private final com.dangerfield.features.matchmaker.MatchMakerViewModel.Action.LoadPeople initialAction = null;
    private int loadPeopleTries = 0;
    
    @javax.inject.Inject()
    public MatchMakerViewModel(@org.jetbrains.annotations.NotNull()
    com.dangerfield.core.people.api.PeopleRepository peopleRepository, @org.jetbrains.annotations.NotNull()
    com.dangerfield.features.matchmaker.ProfileConfig profileConfig) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    protected com.dangerfield.features.matchmaker.MatchMakerViewModel.State getInitialState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.dangerfield.features.matchmaker.MatchMakerViewModel.Action.LoadPeople getInitialAction() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    protected kotlinx.coroutines.flow.Flow<com.dangerfield.features.matchmaker.MatchMakerViewModel.State> transformActionFlow(@org.jetbrains.annotations.NotNull()
    kotlinx.coroutines.flow.Flow<? extends com.dangerfield.features.matchmaker.MatchMakerViewModel.Action> actionFlow) {
        return null;
    }
    
    public final void loadNextPerson(int previousPersonId) {
    }
    
    public final void loadPeople() {
    }
    
    public final void onErrorHandled() {
    }
    
    private final java.lang.Object handleLoadPeople(kotlinx.coroutines.flow.FlowCollector<? super com.dangerfield.features.matchmaker.MatchMakerViewModel.State> $this$handleLoadPeople, kotlin.coroutines.Continuation<? super kotlin.Unit> p1) {
        return null;
    }
    
    private final java.lang.Object pollPersonQueue(kotlinx.coroutines.flow.FlowCollector<? super com.dangerfield.features.matchmaker.MatchMakerViewModel.State> $this$pollPersonQueue, kotlin.coroutines.Continuation<? super kotlin.Unit> p1) {
        return null;
    }
    
    private final java.lang.Object handleLoadNextPerson(kotlinx.coroutines.flow.FlowCollector<? super com.dangerfield.features.matchmaker.MatchMakerViewModel.State> $this$handleLoadNextPerson, int previousPersonId, kotlin.coroutines.Continuation<? super kotlin.Unit> p2) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2 = {"Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$Action;", "", "()V", "LoadNextPerson", "LoadPeople", "SetErrorHandled", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$Action$LoadNextPerson;", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$Action$LoadPeople;", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$Action$SetErrorHandled;", "matchmaker_debug"})
    public static abstract class Action {
        
        private Action() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$Action$LoadPeople;", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$Action;", "()V", "matchmaker_debug"})
        public static final class LoadPeople extends com.dangerfield.features.matchmaker.MatchMakerViewModel.Action {
            @org.jetbrains.annotations.NotNull()
            public static final com.dangerfield.features.matchmaker.MatchMakerViewModel.Action.LoadPeople INSTANCE = null;
            
            private LoadPeople() {
                super();
            }
        }
        
        @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$Action$LoadNextPerson;", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$Action;", "previousPersonId", "", "(I)V", "getPreviousPersonId", "()I", "matchmaker_debug"})
        public static final class LoadNextPerson extends com.dangerfield.features.matchmaker.MatchMakerViewModel.Action {
            private final int previousPersonId = 0;
            
            public LoadNextPerson(int previousPersonId) {
                super();
            }
            
            public final int getPreviousPersonId() {
                return 0;
            }
        }
        
        @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$Action$SetErrorHandled;", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$Action;", "()V", "matchmaker_debug"})
        public static final class SetErrorHandled extends com.dangerfield.features.matchmaker.MatchMakerViewModel.Action {
            @org.jetbrains.annotations.NotNull()
            public static final com.dangerfield.features.matchmaker.MatchMakerViewModel.Action.SetErrorHandled INSTANCE = null;
            
            private SetErrorHandled() {
                super();
            }
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0005\u0003\u0004\u0005\u0006\u0007B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0005\b\t\n\u000b\f\u00a8\u0006\r"}, d2 = {"Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$PeopleStatus;", "", "()V", "Empty", "Failed", "Idle", "Loading", "Showing", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$PeopleStatus$Empty;", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$PeopleStatus$Failed;", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$PeopleStatus$Idle;", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$PeopleStatus$Loading;", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$PeopleStatus$Showing;", "matchmaker_debug"})
    public static abstract class PeopleStatus {
        
        private PeopleStatus() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$PeopleStatus$Idle;", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$PeopleStatus;", "()V", "matchmaker_debug"})
        public static final class Idle extends com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus {
            @org.jetbrains.annotations.NotNull()
            public static final com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus.Idle INSTANCE = null;
            
            private Idle() {
                super();
            }
        }
        
        @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$PeopleStatus$Loading;", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$PeopleStatus;", "()V", "matchmaker_debug"})
        public static final class Loading extends com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus {
            @org.jetbrains.annotations.NotNull()
            public static final com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus.Loading INSTANCE = null;
            
            private Loading() {
                super();
            }
        }
        
        @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$PeopleStatus$Empty;", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$PeopleStatus;", "()V", "matchmaker_debug"})
        public static final class Empty extends com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus {
            @org.jetbrains.annotations.NotNull()
            public static final com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus.Empty INSTANCE = null;
            
            private Empty() {
                super();
            }
        }
        
        @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$PeopleStatus$Failed;", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$PeopleStatus;", "throwable", "", "(Ljava/lang/Throwable;)V", "getThrowable", "()Ljava/lang/Throwable;", "matchmaker_debug"})
        public static final class Failed extends com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus {
            @org.jetbrains.annotations.NotNull()
            private final java.lang.Throwable throwable = null;
            
            public Failed(@org.jetbrains.annotations.NotNull()
            java.lang.Throwable throwable) {
                super();
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.Throwable getThrowable() {
                return null;
            }
        }
        
        @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$PeopleStatus$Showing;", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$PeopleStatus;", "potentialMatch", "Lcom/dangerfield/core/people/api/Person;", "(Lcom/dangerfield/core/people/api/Person;)V", "getPotentialMatch", "()Lcom/dangerfield/core/people/api/Person;", "matchmaker_debug"})
        public static final class Showing extends com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus {
            @org.jetbrains.annotations.NotNull()
            private final com.dangerfield.core.people.api.Person potentialMatch = null;
            
            public Showing(@org.jetbrains.annotations.NotNull()
            com.dangerfield.core.people.api.Person potentialMatch) {
                super();
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.dangerfield.core.people.api.Person getPotentialMatch() {
                return null;
            }
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0016"}, d2 = {"Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$State;", "", "peopleStatus", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$PeopleStatus;", "profileOrder", "", "Lcom/dangerfield/core/people/api/ProfileSection;", "(Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$PeopleStatus;Ljava/util/List;)V", "getPeopleStatus", "()Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$PeopleStatus;", "getProfileOrder", "()Ljava/util/List;", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "matchmaker_debug"})
    public static final class State {
        @org.jetbrains.annotations.NotNull()
        private final com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus peopleStatus = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.dangerfield.core.people.api.ProfileSection> profileOrder = null;
        
        @org.jetbrains.annotations.NotNull()
        public final com.dangerfield.features.matchmaker.MatchMakerViewModel.State copy(@org.jetbrains.annotations.NotNull()
        com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus peopleStatus, @org.jetbrains.annotations.NotNull()
        java.util.List<? extends com.dangerfield.core.people.api.ProfileSection> profileOrder) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public java.lang.String toString() {
            return null;
        }
        
        public State(@org.jetbrains.annotations.NotNull()
        com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus peopleStatus, @org.jetbrains.annotations.NotNull()
        java.util.List<? extends com.dangerfield.core.people.api.ProfileSection> profileOrder) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.dangerfield.features.matchmaker.MatchMakerViewModel.PeopleStatus getPeopleStatus() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.dangerfield.core.people.api.ProfileSection> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.dangerfield.core.people.api.ProfileSection> getProfileOrder() {
            return null;
        }
    }
}