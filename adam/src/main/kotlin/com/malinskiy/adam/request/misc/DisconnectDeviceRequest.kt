/*
 * Copyright (C) 2021 Anton Malinskiy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.malinskiy.adam.request.misc

import com.malinskiy.adam.extension.readProtocolString
import com.malinskiy.adam.request.ComplexRequest
import com.malinskiy.adam.request.HostTarget
import com.malinskiy.adam.transport.Socket

/**
 * Disconnects a device previously connected using ConnectDeviceRequest
 *
 * @param host target device that will be disconnected. if null then disconnects all devices
 * @param port target device's port. can be null in case of locally connected devices, i.e. physical phones and emulators
 */
class DisconnectDeviceRequest(
    private val host: String? = null,
    private val port: Int? = 5555
) : ComplexRequest<String>(target = HostTarget) {

    override fun serialize() = createBaseRequest(
        "disconnect:${
            if (host == null) {
                ""
            } else if (port != null) {
                "$host:$port"
            } else {
                "$host"
            }
        }"
    )

    override suspend fun readElement(socket: Socket) = socket.readProtocolString()
}
