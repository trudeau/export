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

import java.util.Map;
import java.util.function.Function;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.helpers.AttributesImpl;

import com.google.common.graph.ValueGraph;

final class GraphMLExporter<N, E>
    extends AbstractExporter<N, E, GraphMLExporter<N, E>>
{

    private static final SAXTransformerFactory SAX_TRANSFORMER_FACTORY = (SAXTransformerFactory) TransformerFactory.newInstance();

    private static final String GRAPHML = "graphml";

    private static final String XMLNS = "xmlns";

    private static final String GRAPHML_XMLNS = "http://graphml.graphdrawing.org/xmlns";

    private static final String EDGEDEFAULT = "edgedefault";

    private static final String DIRECTED = "directed";

    private static final String KEY = "key";

    private static final String FOR = "for";

    private static final String ID = "id";

    private static final String ATTR_NAME = "attr.name";

    private static final String ATTR_TYPE = "attr.type";

    private static final String GRAPH = "graph";

    private static final String NODE = "node";

    private static final String EDGE = "edge";

    private static final String SOURCE = "source";

    private static final String TARGET = "target";

    private static final String DATA = "data";

    private static final String LABEL = "label";

    private static final String STRING = "string";

    private static final String FLOAT = "float";

    private static final String DOUBLE = "double";

    private static final String LONG = "long";

    private static final String BOOLEAN = "boolean";

    private static final String INT = "int";

    private static final String WEIGHT = "weight";

    private static final String CDATA_TYPE = "CDATA";

    private TransformerHandler transformerHandler;

    GraphMLExporter( ValueGraph<N, E> graph, String name )
    {
        super( graph, name );
    }

    @Override
    protected void startSerialization()
        throws Exception
    {
        transformerHandler = SAX_TRANSFORMER_FACTORY.newTransformerHandler();
        transformerHandler.setResult( new StreamResult( getWriter() ) );
        transformerHandler.startDocument();
    }

    @Override
    protected void endSerialization()
        throws Exception
    {
        transformerHandler.endDocument();
        getWriter().flush();
        getWriter().close();
    }

    @Override
    protected void startGraph( String name )
        throws Exception
    {
        transformerHandler.startElement( GRAPHML_XMLNS, GRAPHML, GRAPHML, new AttributesImpl() );

        AttributesImpl atts = new AttributesImpl();
        atts.addAttribute( GRAPHML_XMLNS, ID, ID, CDATA_TYPE, name );
        atts.addAttribute( GRAPHML_XMLNS, EDGEDEFAULT, EDGEDEFAULT, CDATA_TYPE, DIRECTED );
        transformerHandler.startElement( GRAPHML_XMLNS, GRAPH, GRAPH, atts );
    }

    @Override
    protected void endGraph()
        throws Exception
    {
        transformerHandler.endElement( GRAPHML_XMLNS, GRAPH, GRAPH ); // graph
        transformerHandler.endElement( GRAPHML_XMLNS, GRAPHML, GRAPHML ); // graphml
    }

    @Override
    protected void comment( String text )
        throws Exception
    {
        transformerHandler.comment( text.toCharArray(), 0, text.length() );
    }

    @Override
    protected void enlistNodesProperty( String name, Class<?> type )
        throws Exception
    {
        enlistProperty( name, type, NODE );
    }

    @Override
    protected void enlistEdgesProperty( String name, Class<?> type )
        throws Exception
    {
        enlistProperty( name, type, EDGE );
    }

    private void enlistProperty( String name, Class<?> type, String element )
        throws Exception
    {
        AttributesImpl atts = new AttributesImpl();
        atts.addAttribute( GRAPHML_XMLNS, ID, ID, CDATA_TYPE, name );
        atts.addAttribute( GRAPHML_XMLNS, FOR, FOR, CDATA_TYPE, element );
        atts.addAttribute( GRAPHML_XMLNS, ATTR_NAME, ATTR_NAME, CDATA_TYPE, name );
        atts.addAttribute( GRAPHML_XMLNS, ATTR_TYPE, ATTR_TYPE, CDATA_TYPE, getStringType( type ) );
        transformerHandler.startElement( GRAPHML_XMLNS, KEY, KEY, atts );
        transformerHandler.endElement( GRAPHML_XMLNS, KEY, KEY );
    }

    @Override
    protected void vertex( N node, Map<String, Object> properties )
        throws Exception
    {
        AttributesImpl atts = new AttributesImpl();
        atts.addAttribute( GRAPHML_XMLNS, ID, ID, CDATA_TYPE, String.valueOf( node.hashCode() ) );
        transformerHandler.startElement( GRAPHML_XMLNS, NODE, NODE, atts );

        // TODO print properties

        transformerHandler.endElement( GRAPHML_XMLNS, NODE, NODE );
    }

    @Override
    protected void edge( E edge, N head, N tail, Map<String, Object> properties )
        throws Exception
    {
        AttributesImpl atts = new AttributesImpl();

        if ( edge != null )
        {
            atts.addAttribute( GRAPHML_XMLNS, ID, ID, CDATA_TYPE, String.valueOf( edge.hashCode() ) );
        }

        atts.addAttribute( GRAPHML_XMLNS, SOURCE, SOURCE, CDATA_TYPE, String.valueOf( head.hashCode() ) );
        atts.addAttribute( GRAPHML_XMLNS, TARGET, TARGET, CDATA_TYPE, String.valueOf( tail.hashCode() ) );
        transformerHandler.startElement( GRAPHML_XMLNS, EDGE, EDGE, atts );

        // TODO print properties

        transformerHandler.endElement( GRAPHML_XMLNS, NODE, NODE );
    }

    private static <T> String getStringType( Class<T> type )
    {
        if ( Integer.class == type )
        {
            return INT;
        }
        else if ( Long.class == type )
        {
            return LONG;
        }
        else if ( Float.class == type )
        {
            return FLOAT;
        }
        else if ( Double.class == type )
        {
            return DOUBLE;
        }
        else if ( Boolean.class == type )
        {
            return BOOLEAN;
        }
        return STRING;
    }

    public <V extends Number> GraphMLExporter<N, E> withEdgeWeights( Function<E, V> edgeWeights )
    {
        super.addEdgeProperty( WEIGHT, edgeWeights );
        return this;
    }

    public <V extends Number> GraphMLExporter<N, E> withVertexWeights( Function<N, V> vertexWeights )
    {
        super.addVertexProperty( WEIGHT, vertexWeights );
        return this;
    }

    public GraphMLExporter<N, E> withEdgeLabels( Function<E, String> edgeLabels )
    {
        super.addEdgeProperty( LABEL, edgeLabels );
        return this;
    }

    public GraphMLExporter<N, E> withVertexLabels( Function<N, String> vertexLabels )
    {
        super.addVertexProperty( LABEL, vertexLabels );
        return this;
    }

}
