/*
 * KnoxPatch
 * Copyright (C) 2023 BlackMesa123
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.mesalabs.knoxpatch.hooks

import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.log.loggerD
import com.highcapable.yukihookapi.hook.type.java.IntType
import com.highcapable.yukihookapi.hook.type.java.StringClass

object SamsungHealthHooks : YukiBaseHooker() {
    private const val TAG: String = "SamsungHealthHooks"

    override fun onHook() {
        loggerD(msg = "$TAG: onHook: loaded.")

        /* Disable Knox support */
        findClass("com.samsung.android.knox.EnterpriseDeviceManager").hook {
            injectMember {
                method {
                    name = "getAPILevel"
                    emptyParam()
                    returnType = IntType
                }
                replaceTo(-1)
            }
        }

        /* Disable TIMA support */
        findClass("android.os.SystemProperties").hook {
            injectMember {
                method {
                    name = "get"
                    param(String::class.java)
                    returnType = StringClass
                }
                beforeHook {
                    val key: String = args(0).string()

                    if (key == "ro.config.tima"
                        || key == "ro.config.timaversion") {
                        result = "0"
                    }
                }
            }
        }

        /* Disable SAK support */
        findClass("android.os.SemSystemProperties").hook {
            injectMember {
                method {
                    name = "get"
                    param(String::class.java, String::class.java)
                    returnType = StringClass
                }
                beforeHook {
                    val key: String = args(0).string()
                    val def: String = args(1).string()

                    if (key == "ro.security.keystore.keytype") {
                        result = def
                    }
                }
            }
        }
    }

}
