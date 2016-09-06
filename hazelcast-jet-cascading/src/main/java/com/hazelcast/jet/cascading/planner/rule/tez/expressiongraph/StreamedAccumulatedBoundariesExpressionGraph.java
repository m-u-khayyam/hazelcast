/*
 * Copyright (c) 2008-2016, Hazelcast, Inc. All Rights Reserved.
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

package com.hazelcast.jet.cascading.planner.rule.tez.expressiongraph;

import cascading.flow.planner.iso.expression.ElementCapture;
import cascading.flow.planner.iso.expression.ElementExpression;
import cascading.flow.planner.iso.expression.ExpressionGraph;
import cascading.flow.planner.iso.expression.PathScopeExpression;
import cascading.flow.planner.iso.finder.SearchOrder;
import cascading.flow.planner.rule.elementexpression.BoundariesElementExpression;

/**
 *
 */
public class StreamedAccumulatedBoundariesExpressionGraph extends ExpressionGraph {
    public StreamedAccumulatedBoundariesExpressionGraph() {
        super(SearchOrder.Depth);

        ElementExpression sink = new BoundariesElementExpression(ElementCapture.Secondary);

        this.arc(
                new BoundariesElementExpression(ElementCapture.Primary),
                PathScopeExpression.ALL_NON_BLOCKING,
                sink
        );

        this.arc(
                new BoundariesElementExpression(),
                PathScopeExpression.ANY_BLOCKING,
                sink
        );
    }
}
