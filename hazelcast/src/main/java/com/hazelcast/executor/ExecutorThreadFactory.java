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

package com.hazelcast.executor;

import com.hazelcast.instance.HazelcastInstanceImpl;
import com.hazelcast.instance.OutOfMemoryErrorDispatcher;
import com.hazelcast.instance.ThreadContext;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorThreadFactory implements ThreadFactory {
    private final AtomicInteger threadNumber;
    private final String namePrefix;
    private final ClassLoader classLoader;
    private final ThreadGroup threadGroup;
    private final HazelcastInstanceImpl hazelcastInstance;

    public ExecutorThreadFactory(ThreadGroup threadGroup, HazelcastInstanceImpl hazelcastInstance, String threadNamePrefix, ClassLoader classLoader) {
        this(threadGroup, hazelcastInstance, threadNamePrefix, new AtomicInteger(0), classLoader);
    }

    public ExecutorThreadFactory(ThreadGroup threadGroup, HazelcastInstanceImpl hazelcastInstance, String threadNamePrefix,
                                 AtomicInteger threadNumber, ClassLoader classLoader) {
        this.threadGroup = threadGroup;
        this.hazelcastInstance = hazelcastInstance;
        this.threadNumber = threadNumber;
        this.classLoader = classLoader;
        this.namePrefix = threadNamePrefix;
    }

    public Thread newThread(Runnable r) {
        final Thread t = new Thread(threadGroup, r, namePrefix + threadNumber.getAndIncrement(), 0) {
            public void run() {
                ThreadContext.get().setCurrentInstance(hazelcastInstance);
                try {
                    super.run();
                } catch (OutOfMemoryError e) {
                    OutOfMemoryErrorDispatcher.onOutOfMemory(e);
                } finally {
                    try {
                        ThreadContext.shutdown(this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.setContextClassLoader(classLoader);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}