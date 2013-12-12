package org.nnsoft.trudeau.export;

/*
 *   Copyright 2013 The Trudeau Project
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

import static org.nnsoft.trudeau.utils.Assertions.checkNotNull;

import org.nnsoft.trudeau.api.Graph;

/**
 * {@link NamedExportSelector} implementation
 *
 * @param <V> the Graph vertices type.
 * @param <E> the Graph edges type.
 */
final class DefaultExportSelector<V, E>
    implements NamedExportSelector<V, E>
{

    private final Graph<V, E> graph;

    private String name = null;

    /**
     * Creates a new instance of export selector for the given graph
     * @param graph the graph
     */
    public DefaultExportSelector( Graph<V, E> graph )
    {
        this.graph = graph;
    }

    /**
     * {@inheritDoc}
     */
    public DotExporter<V, E> usingDotNotation()
    {
        return new DotExporter<V, E>( graph, name );
    }

    /**
     * {@inheritDoc}
     */
    public GraphMLExporter<V, E> usingGraphMLFormat()
    {
        return new GraphMLExporter<V, E>( graph, name );
    }

    /**
     * {@inheritDoc}
     */
    public ExportSelector<V, E> withName( String name )
    {
        this.name = checkNotNull( name, "Graph name cannot be null." );
        return this;
    }

}
