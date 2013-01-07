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

package com.hazelcast.queue;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import com.hazelcast.spi.EventFilter;

import java.io.IOException;

/**
 * @ali 12/24/12
 */
public class QueueEventFilter implements EventFilter, DataSerializable {

    boolean includeValue;

    public QueueEventFilter() {
    }

    public QueueEventFilter(boolean includeValue) {
        this.includeValue = includeValue;
    }

    public boolean isIncludeValue() {
        return includeValue;
    }

    public boolean eval(Object arg) {
        return false;
    }

    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeBoolean(includeValue);
    }

    public void readData(ObjectDataInput in) throws IOException {
        includeValue = in.readBoolean();
    }
}
