package com.example.smarcityv1.GovPolls

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class Poll(val id: Int, val question: String)
data class PollResponse(val poll_id: Int, val question: String, val responses: List<String>)
data class CreatePollRequest(val question: String)
data class ParticipateRequest(val poll_id: Int, val response: String)

interface ApiService {
    @POST("/create_poll")
    suspend fun createPoll(@Body request: CreatePollRequest): retrofit2.Response<Unit>

    @GET("/get_polls")
    suspend fun getPolls(): List<Poll>

    @POST("/participate")
    suspend fun participate(@Body request: ParticipateRequest): retrofit2.Response<Unit>

    @GET("/poll_results/{poll_id}")
    suspend fun getPollResults(@Path("poll_id") pollId: Int): PollResponse
}