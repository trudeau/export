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

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.nnsoft.trudeau.api.DirectedGraph;
import org.nnsoft.trudeau.api.Graph;
import org.nnsoft.trudeau.api.Mapper;

/**
 * This class is NOT thread-safe!
 * 
 * @param <V>
 * @param <E>
 */
final class DotExporter<V, E>
    extends AbstractExporter<V, E, DotExporter<V, E>>
{

    private static final String GRAPH = "graph";

    private static final String DIGRAPH = "digraph";

    private static final String CONNECTOR = "--";

    private static final String DICONNECTOR = "->";

    private static final String WEIGHT = "weight";

    private static final String LABEL = "label";

    private final Map<V, Integer> vertexIdentifiers;

    private PrintWriter printWriter;

    private String connector;

    DotExporter( Graph<V, E> graph, String name )
    {
        super( graph, name );
        this.vertexIdentifiers = generateVertexIdentifiers( graph );
    }

    private Map<V, Integer> generateVertexIdentifiers( Graph<V, E> graph )
    {
        Map<V, Integer> vertexIdentifiers = new HashMap<V, Integer>();
        int count = 1;

        for ( V vertex : graph.getVertices() )
        {
            vertexIdentifiers.put( vertex, count++ );
        }

        return vertexIdentifiers;
    }

    @Override
    protected void startSerialization()
        throws Exception
    {
        printWriter = new PrintWriter( getWriter() );
    }

    @Override
    protected void endSerialization()
        throws Exception
    {
        // do nothing?
    }

    @Override
    protected void startGraph( String name )
        throws Exception
    {
        String graphDeclaration;

        if ( getGraph() instanceof DirectedGraph )
        {
            graphDeclaration = DIGRAPH;
            connector = DICONNECTOR;
        }
        else
        {
            graphDeclaration = GRAPH;
            connector = CONNECTOR;
        }

        printWriter.format( "%s %s {%n", graphDeclaration, name );
    }

    @Override
    protected void endGraph()
        throws Exception
    {
        printWriter.write( '}' );
    }

    @Override
    protected void comment( String text )
        throws Exception
    {
        printWriter.write( text );
    }

    @Override
    protected void enlistVerticesProperty( String name, Class<?> type )
        throws Exception
    {
        // not needed in DOT
    }

    @Override
    protected void enlistEdgesProperty( String name, Class<?> type )
        throws Exception
    {
        // not needed in DOT

    }

    @Override
    protected void vertex( V vertex, Map<String, Object> properties )
        throws Exception
    {
        printWriter.format( "  %s", vertexIdentifiers.get( vertex ) );

        printVertexOrEdgeProperties( properties );
    }

    @Override
    protected void edge( E edge, V head, V tail, Map<String, Object> properties )
        throws Exception
    {
        printWriter.format( "  %s %s %s",
                            vertexIdentifiers.get( head ),
                            connector,
                            vertexIdentifiers.get( tail ) );

        printVertexOrEdgeProperties( properties );
    }

    private void printVertexOrEdgeProperties( Map<String, Object> properties )
    {
        if ( !properties.isEmpty() )
        {
            int countAddedProperties = 0;
            printWriter.write( " [" );

            for ( Entry<String, Object> property : properties.entrySet() )
            {
                String formattedString = countAddedProperties == properties.size() - 1 ? "%s=\"%s\"" : "%s=\"%s\" ";
                printWriter.format( formattedString, property.getKey(), property.getValue() );
                countAddedProperties++;
            }

            printWriter.format( "];%n" );
        }
    }

    public <N extends Number> DotExporter<V, E> withEdgeWeights( Mapper<E, N> edgeWeights )
    {
        super.addEdgeProperty( WEIGHT, edgeWeights );
        return this;
    }

    public DotExporter<V, E> withEdgeLabels( Mapper<E, String> edgeLabels )
    {
        super.addEdgeProperty( LABEL, edgeLabels );
        return this;
    }

    public DotExporter<V, E> withVertexLabels( Mapper<V, String> vertexLabels )
    {
        super.addVertexProperty( LABEL, vertexLabels );
        return this;
    }
}