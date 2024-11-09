package com.example.smarcityv1.GovPolls

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class PollViewModel @Inject constructor(private val okHttpClient: OkHttpClient) : ViewModel() {

    private val _polls = MutableLiveData<List<Poll>>()
    val polls: LiveData<List<Poll>> = _polls

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _createPollSuccess = MutableLiveData<String>()
    val createPollSuccess: LiveData<String> = _createPollSuccess

    private val _participatePollSuccess = MutableLiveData<String>()
    val participatePollSuccess: LiveData<String> = _participatePollSuccess

    data class Poll(val id: Int, val question: String)

    fun fetchPolls() {
        viewModelScope.launch {
            try {
                val request = Request.Builder()
                    .url("https://3387ab99-c81c-4281-9e98-c00dda210e5f-00-3g7aaaapw29y5.picard.replit.dev/get_polls")
                    .get()
                    .build()

                val response = withContext(Dispatchers.IO) {
                    okHttpClient.newCall(request).execute()
                }

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val jsonArray = JSONArray(responseBody)
                    val pollList = mutableListOf<Poll>()

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val poll = Poll(
                            id = jsonObject.getInt("id"),
                            question = jsonObject.getString("question")
                        )
                        pollList.add(poll)
                    }
                    _polls.value = pollList // Update LiveData with fetched polls
                } else {
                    _errorMessage.value = when (response.code) {
                        400 -> "Bad Request"
                        401 -> "Unauthorized"
                        404 -> "Not Found"
                        500 -> "Internal Server Error"
                        else -> "Unexpected error: ${response.code}"
                    }
                }
            } catch (e: IOException) {
                _errorMessage.value = "Network error: ${e.message}"
                Log.e("PollViewModel", "Error fetching polls", e)
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching polls: ${e.message}"
                Log.e("PollViewModel", "Error fetching polls", e)
            }
        }
    }


    fun createPoll(question: String) {
        viewModelScope.launch {
            try {
                val json = JSONObject().apply {
                    put("question", question)
                }
                val body = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
                val request = Request.Builder()
                    .url("https://3387ab99-c81c-4281-9e98-c00dda210e5f-00-3g7aaaapw29y5.picard.replit.dev/create_poll")
                    .post(body)
                    .build()

                val response = withContext(Dispatchers.IO) {
                    okHttpClient.newCall(request).execute()
                }

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    _createPollSuccess.value = responseBody ?: "Poll created successfully"
                } else {
                    _errorMessage.value = "Error creating poll: ${response.code}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error creating poll: ${e.message}"
                Log.e("PollViewModel", "Error creating poll", e)
            }
        }
    }

    fun participateInPoll(pollId: Int, response: String) {
        viewModelScope.launch {
            try {
                val json = JSONObject().apply {
                    put("pollId", pollId)
                    put("response", response)
                }
                val body = json.toString().toRequestBody("application/json".toMediaTypeOrNull())
                val request = Request.Builder()
                    .url("https://3387ab99-c81c-4281-9e98-c00dda210e5f-00-3g7aaaapw29y5.picard.replit.dev/participate_poll")
                    .post(body)
                    .build()

                val response = withContext(Dispatchers.IO) {
                    okHttpClient.newCall(request).execute()
                }

                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    _participatePollSuccess.value = responseBody ?: "Response submitted successfully"
                } else {
                    _errorMessage.value = "Error participating in poll: ${response.code}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error participating in poll: ${e.message}"
                Log.e("PollViewModel", "Error participating in poll", e)
            }
        }
    }
}