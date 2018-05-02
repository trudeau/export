package org.nnsoft.trudeau.export;

/*
 *   Copyright 2013 - 2018 The Trudeau Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import static java.util.Objects.requireNonNull;

import com.google.common.graph.Graph;
import com.google.common.graph.ValueGraph;

public final class GraphExporter
{

    /**
     * Export the graph in DOT or GraphML format.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param <G> the Graph type
     * @param graph the input graph
     * @return an instance of {@link NamedExportSelector}
     */
    public static <N, G extends Graph<N>> ExportSelector<N, ?> export( G graph )
    {
        graph = requireNonNull( graph, "Null graph can not be exported" );
        //return new DefaultExportSelector<N, E>( graph );
        return null;
    }

    /**
     * Export the graph in DOT or GraphML format.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param <G> the Graph type
     * @param graph the input graph
     * @return an instance of {@link NamedExportSelector}
     */
    public static <V, E, G extends ValueGraph<V, E>> ExportSelector<V, E> export( G graph )
    {
        graph = requireNonNull( graph, "Null graph can not be exported" );
        return new DefaultExportSelector<V, E>( graph );
    }

    private GraphExporter()
    {
        // do nothing
    }

}
