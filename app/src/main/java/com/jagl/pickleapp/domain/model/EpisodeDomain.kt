package com.jagl.pickleapp.domain.model

data class EpisodeDomain(
    val id: Long,
    val name: String,
    val airDate: String,
    val episode: String,
    val charactersInEpisode: List<Long>,
    val url: String,
    val created: String,
    val page: Int
) {
    private fun splitAirDate() = this.airDate.split(",")

    fun getAirMonthAndDay() = splitAirDate().firstOrNull() ?: "N/A"

    fun getAirYear() = splitAirDate().lastOrNull() ?: "N/A"

    fun getEpisodeNumber(): String {
        return episode.substringAfter("E").toIntOrNull()?.toString() ?: "N/A"
    }

    fun getSeasonNumber(): String {
        return episode.substringBefore("E").drop(1).toIntOrNull()?.toString() ?: "N/A"
    }
}
