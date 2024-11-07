package com.froxengine.booking.data.api.local

import com.froxengine.booking.data.model.ContactDto
import com.froxengine.booking.data.model.DNIDto
import com.froxengine.booking.data.model.RUCDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ContactApi {
    @GET("/api/contacts")
    fun findContacts(@Query("term") term: String): Call<List<ContactDto>>

    @GET("/api/enquiry/dni/{number}")
    fun getDNI(@Path("number") number: String): Call<DNIDto>

    @GET("/api/enquiry/ruc/{number}")
    fun getRUC(@Path("number") number: String): Call<RUCDto>

//    @POST("/api/contacts")
//    fun createContact(@Body payload: pe.synercom.android.data.dto.CreateContactDto): Call<ContactDto>

    @PUT("/api/contacts/{id}")
    fun updateContact(@Path("id") id: String, @Body payload: ContactDto): Call<ContactDto>
}