package com.eraqi.chatlib.utils


//STOMP Commands
internal const val TERMINATE_SYMBOL = "\u0000"
internal const val COMMAND_SEND = "SEND"
internal const val COMMAND_CONNECT = "CONNECT"
internal const val COMMAND_CONNECTED = "CONNECTED"
internal const val COMMAND_SUBSCRIBE = "SUBSCRIBE"
internal const val COMMAND_SUBSCRIBED = "SUBSCRIBED"
internal const val COMMAND_RECEIVED = "RECEIVED"
internal const val COMMAND_MESSAGE = "MESSAGE"
internal const val HEADER_DESTINATION = "destination:"
internal const val HEADER_ACK = "ack:"
internal const val HEADER_CONTENT_TYPE = "content-type:"
internal const val HEADER_ACCEPT_VERSION = "accept-version:"
internal const val HEADER_ID = "id:"
internal const val HEADER_VALUE_TEXT_PLAIN = "text/plain"
//End of Stomp Commands

internal const val TIME_OUT_TIME:Long = 5000

