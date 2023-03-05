package com.dangerfield.features.matchmaker;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 2, d1 = {"\u0000F\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0003\u001a$\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0007\u001aU\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2!\u0010\u000b\u001a\u001d\u0012\u0013\u0012\u00110\f\u00a2\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u00112\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u001a\b\u0010\u0012\u001a\u00020\u0001H\u0003\u001a\u0016\u0010\u0013\u001a\u00020\u00012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u0011H\u0003\u001aA\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182!\u0010\u000b\u001a\u001d\u0012\u0013\u0012\u00110\f\u00a2\u0006\f\b\r\u0012\b\b\u000e\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u00a8\u0006\u001a"}, d2 = {"Loading", "", "MatchMakerScreen", "viewModel", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel;", "onError", "Lkotlin/Function1;", "", "MatchMakerScreenContent", "state", "Lcom/dangerfield/features/matchmaker/MatchMakerViewModel$State;", "onNext", "", "Lkotlin/ParameterName;", "name", "prevId", "onReload", "Lkotlin/Function0;", "MatchMakerScreenContentPreview", "NoMorePeople", "PersonProfile", "person", "Lcom/dangerfield/core/people/api/Person;", "profileSectionOrder", "", "Lcom/dangerfield/core/people/api/ProfileSection;", "matchmaker_debug"})
public final class MatchMakerScreenKt {
    
    @androidx.compose.runtime.Composable()
    @kotlin.OptIn(markerClass = {androidx.lifecycle.compose.ExperimentalLifecycleComposeApi.class})
    public static final void MatchMakerScreen(@org.jetbrains.annotations.NotNull()
    com.dangerfield.features.matchmaker.MatchMakerViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Throwable, kotlin.Unit> onError) {
    }
    
    @androidx.compose.runtime.Composable()
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    private static final void MatchMakerScreenContent(com.dangerfield.features.matchmaker.MatchMakerViewModel.State state, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onNext, kotlin.jvm.functions.Function0<kotlin.Unit> onReload, kotlin.jvm.functions.Function1<? super java.lang.Throwable, kotlin.Unit> onError) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PersonProfile(com.dangerfield.core.people.api.Person person, java.util.List<? extends com.dangerfield.core.people.api.ProfileSection> profileSectionOrder, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onNext) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void NoMorePeople(kotlin.jvm.functions.Function0<kotlin.Unit> onReload) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void Loading() {
    }
    
    @androidx.compose.ui.tooling.preview.Preview()
    @androidx.compose.runtime.Composable()
    private static final void MatchMakerScreenContentPreview() {
    }
}