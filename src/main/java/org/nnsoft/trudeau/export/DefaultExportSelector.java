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

import com.google.common.graph.ValueGraph;

/**
 * {@link NamedExportSelector} implementation
 *
 * @param <N> the Graph nodes type.
 * @param <E> the Graph edges type.
 */
final class DefaultExportSelector<N, E>
    implements ExportSelector<N, E>
{

    private final ValueGraph<N, E> graph;

    private String name = null;

    /**
     * Creates a new instance of export selector for the given graph
     * @param graph the graph
     */
    public DefaultExportSelector( ValueGraph<N, E> graph )
    {
        this.graph = graph;
    }

    /**
     * {@inheritDoc}
     */
    public DotExporter<N, E> toDotNotation()
    {
        return new DotExporter<N, E>( graph, name );
    }

    /**
     * {@inheritDoc}
     */
    public GraphMLExporter<N, E> toGraphMLFormat()
    {
        return new GraphMLExporter<N, E>( graph, name );
    }

    /**
     * {@inheritDoc}
     */
    public ExportSelector<N, E> withName( String name )
    {
        this.name = requireNonNull( name, "Graph name cannot be null." );
        return this;
    }

}
