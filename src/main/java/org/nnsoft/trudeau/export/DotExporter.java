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

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import com.google.common.graph.ValueGraph;

/**
 * This class is NOT thread-safe!
 * 
 * @param <N>
 * @param <E>
 */
final class DotExporter<N, E>
    extends AbstractExporter<N, E, DotExporter<N, E>>
{

    private static final String GRAPH = "graph";

    private static final String DIGRAPH = "digraph";

    private static final String CONNECTOR = "--";

    private static final String DICONNECTOR = "->";

    private static final String WEIGHT = "weight";

    private static final String LABEL = "label";

    private static final String COMMENT_HEADER = "# ";

    private final Map<N, Integer> vertexIdentifiers;

    private PrintWriter printWriter;

    private String connector;

    DotExporter( ValueGraph<N, E> graph, String name )
    {
        super( graph, name );
        this.vertexIdentifiers = generateVertexIdentifiers( graph );
    }

    private Map<N, Integer> generateVertexIdentifiers( ValueGraph<N, E> graph )
    {
        Map<N, Integer> vertexIdentifiers = new HashMap<N, Integer>();
        int count = 1;

        for ( N node : graph.nodes() )
        {
            vertexIdentifiers.put( node, count++ );
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

        if ( getGraph().isDirected() )
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
        printWriter.write( COMMENT_HEADER );
        printWriter.write( text );
    }

    @Override
    protected void enlistNodesProperty( String name, Class<?> type )
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
    protected void vertex( N vertex, Map<String, Object> properties )
        throws Exception
    {
        printWriter.format( "  %s", vertexIdentifiers.get( vertex ) );

        printVertexOrEdgeProperties( properties );
    }

    @Override
    protected void edge( E edge, N head, N tail, Map<String, Object> properties )
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

    public <V extends Number> DotExporter<N, E> withEdgeWeights( Function<E, V> edgeWeights )
    {
        super.addEdgeProperty( WEIGHT, edgeWeights );
        return this;
    }

    public DotExporter<N, E> withEdgeLabels( Function<E, String> edgeLabels )
    {
        super.addEdgeProperty( LABEL, edgeLabels );
        return this;
    }

    public DotExporter<N, E> withVertexLabels( Function<N, String> vertexLabels )
    {
        super.addVertexProperty( LABEL, vertexLabels );
        return this;
    }

}
