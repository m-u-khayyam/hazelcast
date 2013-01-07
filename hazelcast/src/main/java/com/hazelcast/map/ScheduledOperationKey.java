/*
 * Copyright (c) 2008-2012, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.map;

import com.hazelcast.nio.serialization.Data;

public class ScheduledOperationKey {
    final Data key;
    final String mapName;

    public ScheduledOperationKey(String mapName, Data key) {
        this.key = key;
        this.mapName = mapName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledOperationKey that = (ScheduledOperationKey) o;
        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (mapName != null ? !mapName.equals(that.mapName) : that.mapName != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (mapName != null ? mapName.hashCode() : 0);
        return result;
    }
}
