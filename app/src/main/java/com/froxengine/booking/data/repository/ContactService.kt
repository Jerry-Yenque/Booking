package com.froxengine.booking.data.repository

import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.froxengine.booking.core.RetrofitProvider
import com.froxengine.booking.data.api.local.ContactApi
import com.froxengine.booking.data.model.ContactDto
import com.froxengine.booking.data.model.DNIDto
import com.froxengine.booking.data.model.RUCDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactService : BaseService() {
    private val mBinder: IBinder = ServiceBinder(this)
    override fun onBind(p0: Intent?): IBinder {
        return mBinder
    }

    fun findContacts(term: String, callback: (List<ContactDto>) -> Unit) {

            val client = RetrofitProvider.getClient(applicationContext, true)
            val contactApi =
                client.create(ContactApi::class.java)
            val contactApiCall = contactApi.findContacts(term)

            contactApiCall.enqueue(object : Callback<List<ContactDto>> {
                override fun onResponse(
                    call: Call<List<ContactDto>>,
                    response: Response<List<ContactDto>>
                ) {
                    val responseDto = response.body()
                    Log.d("Booking", "contact found in local db: $responseDto")
                    if (responseDto != null) {
                        callback(responseDto)
                    }
                }

                override fun onFailure(call: Call<List<ContactDto>>, t: Throwable) {
                    Log.e("Booking", "search for contact failed", t)
                }
            })

    }

    fun getDNI(number: String, callback: (DNIDto?) -> Unit) {
            val client = RetrofitProvider.getClient(applicationContext, true)
            val contactApi =
                client.create(ContactApi::class.java)
            val contactApiCall = contactApi.getDNI(number)

            contactApiCall.enqueue(object : Callback<DNIDto> {
                override fun onResponse(
                    call: Call<DNIDto>,
                    response: Response<DNIDto>
                ) {
                    val responseDto = response.body()
                    Log.d("Booking", "getDNI: $responseDto")
                    callback(responseDto)
                }

                override fun onFailure(call: Call<DNIDto>, t: Throwable) {
                    Log.e("Booking", "search for dni failed", t)
                    callback(null)
                }
            })

    }

    fun getRUC(number: String, callback: (RUCDto?) -> Unit) {
            val client = RetrofitProvider.getClient(applicationContext, true)
            val contactApi =
                client.create(ContactApi::class.java)
            val contactApiCall = contactApi.getRUC(number)

            contactApiCall.enqueue(object : Callback<RUCDto> {
                override fun onResponse(
                    call: Call<RUCDto>,
                    response: Response<RUCDto>
                ) {
                    val responseDto = response.body()
                    Log.d("Booking", "RUC: $responseDto")
                    callback(responseDto)
                }

                override fun onFailure(call: Call<RUCDto>, t: Throwable) {
                    Log.e("Booking", "search for dni failed", t)
                    callback(null)
                }
            })
    }

//    fun createContact(createContactDto: CreateContactDto, callback: (pe.synercom.android.data.dto.ContactDto) -> Unit) {
//        if (isCloud()) {
//            val client = CloudClient.getInstance().getClient(applicationContext)
//            val contactApi =
//                client.create(ContactApi::class.java)
//            val identifier = getString("auth_identifier")!!
//            val contactApiCall = contactApi.createContact(identifier, createContactDto)
//            contactApiCall.enqueue(object : Callback<ContactDto> {
//                override fun onResponse(
//                    call: Call<ContactDto>,
//                    response: Response<ContactDto>
//                ) {
//                    val responseDto = response.body()
//                    Log.d(Util.LOG_TAG, "*Contact: $responseDto")
//                    if (responseDto != null) {
//                        callback(responseDto)
//                    }
//                }
//
//                override fun onFailure(call: Call<ContactDto>, t: Throwable) {
//                    Log.e(Util.LOG_TAG, "create contact failed", t)
//                }
//            })
//        } else {
//            val client = LocalClient.getInstance().getClient(applicationContext)
//            val contactApi =
//                client.create(pe.synercom.android.rest.api.local.ContactApi::class.java)
//            val contactApiCall = contactApi.createContact(createContactDto)
//            contactApiCall.enqueue(object : Callback<ContactDto> {
//                override fun onResponse(
//                    call: Call<ContactDto>,
//                    response: Response<ContactDto>
//                ) {
//                    val responseDto = response.body()
//                    Log.d(Util.LOG_TAG, "Contact: $responseDto")
//                    if (responseDto != null) {
//                        callback(responseDto)
//                    }
//                }
//
//                override fun onFailure(call: Call<ContactDto>, t: Throwable) {
//                    Log.e(Util.LOG_TAG, "create contact failed", t)
//                }
//            })
//        }
//    }

    class ServiceBinder(val service: ContactService) : Binder()
}
