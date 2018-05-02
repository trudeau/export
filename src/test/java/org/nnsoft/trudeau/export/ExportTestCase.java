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

import static org.nnsoft.trudeau.connector.GraphConnector.on;
import static org.nnsoft.trudeau.export.GraphExporter.export;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nnsoft.trudeau.connector.AbstractValueGraphDescription;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

/**
 * TODO find a way to assert exported graphs
 */
public class ExportTestCase {

    private MutableValueGraph<String, BaseLabeledWeightedEdge<Double>> actual;

    @Before
    public void setUp()
    {
        actual = ValueGraphBuilder.undirected().build();

        on( actual )
        .createConnections( new AbstractValueGraphDescription<String, BaseLabeledWeightedEdge<Double>>()
        {

            @Override
            public void describe()
            {
                String start = addNode( "start" );
                String a = addNode( "a" );
                String b = addNode( "b" );
                String goal = addNode( "goal" );

                connect(start).to( a ).via( new BaseLabeledWeightedEdge<Double>( "start <-> a", 1.5D ) );
                connect(a).to( b ).via( new BaseLabeledWeightedEdge<Double>( "a <-> b", 2D ) );
                connect(a).to( goal ).via( new BaseLabeledWeightedEdge<Double>( "a <-> goal", 2D ) );
                connect( b ).to( goal ).via( new BaseLabeledWeightedEdge<Double>( "b <-> goal", 2D ) );
            }

        } );
    }

    @After
    public void tearDown()
    {
        actual = null;
    }

    @Test
    public void shouldPrintDotFormat()
        throws Exception
    {
        export( actual ).withName( "DotFormatGraph" )
                        .toDotNotation()
                        .withVertexLabels( new NodeLabelMapper() )
                        .withEdgeWeights( new EdgeWeightMapper() )
                        .withEdgeLabels( new EdgeLabelMapper() )
                        .to( System.out );
    }

    @Test
    @Ignore
    public void shouldPrintGraphML()
        throws Exception
    {
        export( actual ).withName( "GraphMLGraph" )
                        .toGraphMLFormat()
                        .withVertexLabels( new NodeLabelMapper() )
                        .withEdgeWeights( new EdgeWeightMapper() )
                        .withEdgeLabels( new EdgeLabelMapper() )
                        .to( System.out );
    }

    @Test
    public void shouldPrintGraphMLFormat()
        throws Exception
    {
        export( actual ).toGraphMLFormat().to( System.out );
    }

}
