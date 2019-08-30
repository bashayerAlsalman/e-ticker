package net.bashayer.eticket.qr.models

import android.graphics.Bitmap
import java.util.*

class EventAttendees(val attendees: List<EventAttendee>)
class AttendeeTicket(val QRCodeImage: Bitmap, val eventId: String, val eventAttendeeName: String, val eventName: String, val eventDescription: String, val city: String, val eventDate: Date)