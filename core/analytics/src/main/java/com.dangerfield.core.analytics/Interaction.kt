package com.dangerfield.core.analytics

class Interaction(
    extras: Map<String, Any>,
    val elementId: String,
    val interactionType: InteractionType,
) : Event(extras)

enum class InteractionType {
    Press,
    Focus,
    Scroll
}
