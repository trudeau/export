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

/**
 * Allows users selecting the format of graph serialization.
 *
 * @param <N> the Graph nodes type.
 */
public interface ExportSelector<N, E>
{

    /**
     * Use the given name when exporting the {@link org.apache.commons.graph.Graph} to a resource.
     *
     * @param name the name to identify the {@link org.apache.commons.graph.Graph}
     * @return the graph export format selector
     */
    ExportSelector<N, E> withName( String name );

    /**
     * Export Graphs in <a href="http://en.wikipedia.org/wiki/DOT_language">DOT language</a>.
     *
     * @return {@link DotExporter} instance
     * @throws GraphExportException
     */
    DotExporter<N, E> toDotNotation()
        throws GraphExportException;

    /**
     * Export Graphs in <a href="http://graphml.graphdrawing.org/">GraphML file format</a>.
     *
     * @return {@link GraphMLExporter} instance
     * @throws GraphExportException
     */
    GraphMLExporter<N, E> toGraphMLFormat()
        throws GraphExportException;

}
